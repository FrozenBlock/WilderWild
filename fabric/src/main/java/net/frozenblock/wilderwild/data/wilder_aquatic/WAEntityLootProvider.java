/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.data.wilder_aquatic;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public final class WAEntityLootProvider extends FabricEntityLootSubProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WAEntityLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
		this.registries = registries;
	}

	@Override
	public void generate() {
		final HolderLookup.Provider registryLookup = this.registries.join();

		this.add(
			WWEntityTypes.SUNKEN.get(),
			LootTable.lootTable().withPool(
				LootPool.lootPool()
					.setRolls(ContextIntProviders.exactly(1))
					.add(
						LootItem.lootTableItem(WWItems.HARPOON.get())
							.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
							.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0F, 1F)))
					)
				).withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.add(
							LootItem.lootTableItem(Items.BONE)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0F, 1F)))
						)
				).withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.11F, 0.02F))
				)
		);
	}
}
