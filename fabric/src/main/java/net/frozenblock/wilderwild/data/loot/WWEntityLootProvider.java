/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.data.loot;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.frozenblock.lib.data.api.EntityLootHelper;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariant;
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class WWEntityLootProvider extends FabricEntityLootSubProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WWEntityLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
		this.registries = registries;
	}

	@Override
	public void generate() {
		final HolderLookup.Provider registryLookup = this.registries.join();

		final Map<Holder<JellyfishVariant>, LootTable> jellyfishVariantToLootTables = new Object2ObjectLinkedOpenHashMap<>();
		registryLookup.lookupOrThrow(WilderWildRegistries.JELLYFISH_VARIANT)
			.listElements()
			.sorted(Comparator.comparing(holder -> holder.key().identifier().getPath()))
			.forEach(jellyfishVariant -> {
				final Identifier id = jellyfishVariant.key().identifier();
				final String path = id.getPath();
				final Identifier lootTableId = Identifier.fromNamespaceAndPath(
					id.getNamespace(),
					"entities/" + WWEntityTypeIds.JELLYFISH.identifier().getPath() + '_' + path
				);
				final ResourceKey<LootTable> lootTableName = ResourceKey.create(Registries.LOOT_TABLE, lootTableId);

				final Item item = registryLookup.lookupOrThrow(Registries.ITEM).getOrThrow(ResourceKey.create(Registries.ITEM, id.withPath(path + "_nematocyst"))).value();
				final LootTable.Builder builder = LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.setBonusRolls(ConstantValue.exactly(0F))
							.add(
								LootItem.lootTableItem(item)
									.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 3F)))
									.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
							)
					);
				this.add(WWEntityTypes.JELLYFISH.get(), lootTableName, builder);
				jellyfishVariantToLootTables.put(jellyfishVariant, builder.build());
			});
		this.add(
			WWEntityTypes.JELLYFISH.get(),
			LootTable.lootTable().withPool(createJellyfishDispatchPool(jellyfishVariantToLootTables))
		);

		this.add(
			WWEntityTypes.CRAB.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.CRAB_CLAW)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 1F)))
								.apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.OSTRICH.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.FEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 4F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.ZOMBIE_OSTRICH.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.ROTTEN_FLESH)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 4F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.SCORCHED.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.STRING)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.SCORCHED_EYE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
				)
		);

		this.add(
			WWEntityTypes.TUMBLEWEED.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.STICK)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 3F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.MOOBLOOM.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.BEEF)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
								.apply(SmeltItemFunction.smelted().when(EntityLootHelper.shouldSmeltLoot(registryLookup)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.PENGUIN.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.FEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(WWEntityTypes.FIREFLY.get(), LootTable.lootTable());
		this.add(WWEntityTypes.BUTTERFLY.get(), LootTable.lootTable());
	}

	public LootPool.Builder createJellyfishDispatchPool(Map<Holder<JellyfishVariant>, LootTable> variantToTables) {
		AlternativesEntry.Builder variants = AlternativesEntry.alternatives();

		for (Map.Entry<Holder<JellyfishVariant>, LootTable> entry : variantToTables.entrySet()) {
			final Holder<JellyfishVariant> variant = entry.getKey();
			final LootTable lootTable = entry.getValue();

			variants = variants.otherwise(
				NestedLootTable.inlineLootTable(lootTable)
					.when(
						LootItemEntityPropertyCondition.hasProperties(
							LootContext.EntityTarget.THIS,
							EntityPredicate.Builder.entity().components(DataComponentExactPredicate.expect(WWDataComponents.JELLYFISH_VARIANT.get(), variant))
						)
					)
			);
		}

		return LootPool.lootPool().add(variants);
	}
}
