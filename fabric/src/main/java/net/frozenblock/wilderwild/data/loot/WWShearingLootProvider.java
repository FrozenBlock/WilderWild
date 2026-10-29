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

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.frozenblock.wilderwild.registry.WWLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public final class WWShearingLootProvider extends SimpleFabricLootTableSubProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WWShearingLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, LootContextParamSets.SHEARING);
		this.registries = registries;
	}

	@Override
	public void run() {}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		final HolderLookup.Provider registryLookup = this.registries.join();

		// SUNKEN CORAL
		output.accept(WWLootTables.SHEAR_SUNKEN_BRAIN_CORAL, createSunkenCoralLootTable(Items.BRAIN_CORAL, Items.BRAIN_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_BUBBLE_CORAL, createSunkenCoralLootTable(Items.BUBBLE_CORAL, Items.BUBBLE_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_FIRE_CORAL, createSunkenCoralLootTable(Items.FIRE_CORAL, Items.FIRE_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_HORN_CORAL, createSunkenCoralLootTable(Items.HORN_CORAL, Items.HORN_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_TUBE_CORAL, createSunkenCoralLootTable(Items.TUBE_CORAL, Items.TUBE_CORAL_FAN));
		// SUNKEN DEAD CORAL
		output.accept(WWLootTables.SHEAR_SUNKEN_DEAD_BRAIN_CORAL, createSunkenCoralLootTable(Items.DEAD_BRAIN_CORAL, Items.DEAD_BRAIN_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_DEAD_BUBBLE_CORAL, createSunkenCoralLootTable(Items.DEAD_BUBBLE_CORAL, Items.DEAD_BUBBLE_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_DEAD_FIRE_CORAL, createSunkenCoralLootTable(Items.DEAD_FIRE_CORAL, Items.DEAD_FIRE_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_DEAD_HORN_CORAL, createSunkenCoralLootTable(Items.DEAD_HORN_CORAL, Items.DEAD_HORN_CORAL_FAN));
		output.accept(WWLootTables.SHEAR_SUNKEN_DEAD_TUBE_CORAL, createSunkenCoralLootTable(Items.DEAD_TUBE_CORAL, Items.DEAD_TUBE_CORAL_FAN));
	}

	private static LootTable.Builder createSunkenCoralLootTable(ItemLike coralItem, ItemLike coralFanItem) {
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.setRolls(ContextIntProviders.exactly(2))
					.add(LootItem.lootTableItem(coralItem).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
					.add(LootItem.lootTableItem(coralFanItem).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
			);
	}
}
