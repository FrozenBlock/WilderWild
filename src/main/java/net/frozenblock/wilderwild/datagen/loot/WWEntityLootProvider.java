/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen.loot;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.lib.datagen.api.EntityLootHelper;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public final class WWEntityLootProvider extends SimpleFabricLootTableProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WWEntityLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, LootContextParamSets.ENTITY);
		this.registries = registries;
	}

	@Override
	public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		HolderLookup.Provider registryLookup = this.registries.join();
		output.accept(
			WWEntityTypes.CRAB.getDefaultLootTable(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.CRAB_CLAW)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 1F)))
								.apply(SmeltItemFunction.smelted().when(EntityLootHelper.shouldSmeltLoot(registryLookup)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);

		output.accept(
			WWEntityTypes.OSTRICH.getDefaultLootTable(),
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

		output.accept(
			WWEntityTypes.SCORCHED.getDefaultLootTable(),
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

		output.accept(
			WWEntityTypes.TUMBLEWEED.getDefaultLootTable(),
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

		output.accept(
			WWEntityTypes.MOOBLOOM.getDefaultLootTable(),
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

		output.accept(WWEntityTypes.FIREFLY.getDefaultLootTable(), LootTable.lootTable());
		output.accept(WWEntityTypes.BUTTERFLY.getDefaultLootTable(), LootTable.lootTable());
	}
}
