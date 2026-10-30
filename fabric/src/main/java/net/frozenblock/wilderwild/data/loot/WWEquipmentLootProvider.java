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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public final class WWEquipmentLootProvider extends SimpleFabricLootTableSubProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public WWEquipmentLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, LootContextParamSets.EQUIPMENT);
		this.registries = registries;
	}

	@Override
	public void run() {}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		final HolderLookup.Provider registryLookup = this.registries.join();

		final HolderLookup.RegistryLookup<Enchantment> enchantments = registryLookup.lookupOrThrow(Registries.ENCHANTMENT);

		final Holder<LootTable> equipmentTrialChamberTable = registryLookup.lookupOrThrow(Registries.LOOT_TABLE).getOrThrow(BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER);

		output.accept(
			WWLootTables.EQUIPMENT_TRIAL_CHAMBER_SUNKEN,
			LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(NestedLootTable.lootTableReference(equipmentTrialChamberTable)))
				.withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.CROSSBOW).setWeight(3))
						.add(
							LootItem.lootTableItem(Items.CROSSBOW)
								.apply(
									new SetEnchantmentsFunction.Builder()
										.withEnchantment(enchantments.getOrThrow(Enchantments.QUICK_CHARGE), ContextIntProviders.between(1, 3))
								)
								.setWeight(2)
						)
						.add(
							LootItem.lootTableItem(Items.CROSSBOW)
								.apply(
									new SetEnchantmentsFunction.Builder()
										.withEnchantment(enchantments.getOrThrow(Enchantments.POWER), ContextIntProviders.exactly(1))
								)
						)
						.add(
							LootItem.lootTableItem(Items.CROSSBOW)
								.apply(
									new SetEnchantmentsFunction.Builder()
										.withEnchantment(enchantments.getOrThrow(Enchantments.PUNCH), ContextIntProviders.exactly(1))
								)
						)
				)
		);
	}
}
