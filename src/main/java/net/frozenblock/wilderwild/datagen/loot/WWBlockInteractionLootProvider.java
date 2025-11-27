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
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class WWBlockInteractionLootProvider extends SimpleFabricLootTableProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WWBlockInteractionLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, LootContextParamSets.BLOCK_INTERACT);
		this.registries = registries;
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		final HolderLookup.Provider registryLookup = this.registries.join();

		output.accept(
			WWLootTables.SHEAR_MILKWEED,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.MILKWEED_POD)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_PRICKLY_PEAR,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWItems.PRICKLY_PEAR)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_RED_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.RED_SHELF_FUNGI.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_BROWN_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.BROWN_SHELF_FUNGI.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_PALE_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.PALE_SHELF_FUNGI.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_CRIMSON_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.CRIMSON_SHELF_FUNGI.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_WARPED_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.WARPED_SHELF_FUNGI.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_SPONGE_BUD,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.SPONGE_BUD.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);

		output.accept(
			WWLootTables.SHEAR_SHRUB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.SHRUB.asItem())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
						)
				)
		);
	}
}
