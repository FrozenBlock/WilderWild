/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.datagen.loot;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.frozenblock.lib.datagen.api.EntityLootHelper;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class WWEntityLootProvider extends FabricEntityLootTableProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WWEntityLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
		this.registries = registries;
	}

	@Override
	public void generate() {
		final HolderLookup.Provider registryLookup = this.registries.join();

		registryLookup.lookupOrThrow(WilderWildRegistries.JELLYFISH_VARIANT)
			.listElements()
			.forEach(reference -> {
				final Identifier id = reference.key().identifier();
				final String path = id.getPath();
				final Identifier lootTableId = Identifier.fromNamespaceAndPath(
					id.getNamespace(),
					"entities/" + BuiltInRegistries.ENTITY_TYPE.getKey(WWEntityTypes.JELLYFISH).getPath() + '_' + path
				);
				Item item = registryLookup.lookupOrThrow(Registries.ITEM).getOrThrow(ResourceKey.create(Registries.ITEM, id.withPath(path + "_nematocyst"))).value();
				this.add(
					WWEntityTypes.JELLYFISH,
					ResourceKey.create(Registries.LOOT_TABLE, lootTableId),
					LootTable.lootTable()
						.withPool(
							LootPool.lootPool()
								.setRolls(ConstantValue.exactly(1F))
								.setBonusRolls(ConstantValue.exactly(0F))
								.add(
									LootItem.lootTableItem(item)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 3F)))
										.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
								)
						)
						.setRandomSequence(lootTableId)
				);
			});

		this.add(
			WWEntityTypes.CRAB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.CRAB_CLAW)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 1F)))
								.apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.OSTRICH,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.FEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 4F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.SCORCHED,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.STRING)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.SCORCHED_EYE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
				)
		);

		this.add(
			WWEntityTypes.TUMBLEWEED,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.STICK)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 3F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.MOOBLOOM,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.BEEF)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
								.apply(SmeltItemFunction.smelted().when(EntityLootHelper.shouldSmeltLoot(registryLookup)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(
			WWEntityTypes.PENGUIN,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(Items.FEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		this.add(WWEntityTypes.FIREFLY, LootTable.lootTable());
		this.add(WWEntityTypes.BUTTERFLY, LootTable.lootTable());
	}
}
