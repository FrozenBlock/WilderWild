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

package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class WWLootTables {
	private WWLootTables() {
		throw new UnsupportedOperationException("WWLootTables only supports static declarations.");
	}

	public static void init() {
		WWConstants.logWithModId("Registering Loot Table Modifications for", WWConstants.UNSTABLE_LOGGING);
		//SHIPWRECK
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(
						LootItem.lootTableItem(WWBlocks.ALGAE.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 3F)))
							.setWeight(4)
							.setQuality(Rarity.COMMON.ordinal() + 1)
					).add(
						LootItem.lootTableItem(WWBlocks.PLANKTON.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
							.setWeight(1)
							.setQuality(Rarity.COMMON.ordinal() + 1)
					)
					.add(
						LootItem.lootTableItem(WWBlocks.BARNACLES.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
							.setWeight(3)
							.setQuality(Rarity.COMMON.ordinal() + 1)
					)
					.add(
						LootItem.lootTableItem(WWBlocks.TUBE_WORMS.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
							.setWeight(1)
							.setQuality(Rarity.UNCOMMON.ordinal() + 1)
					)
					.add(
						LootItem.lootTableItem(WWBlocks.SEA_ANEMONE.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 6F)))
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
					).add(
						LootItem.lootTableItem(WWBlocks.AUBURN_MOSS_BLOCK.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
					).add(
						LootItem.lootTableItem(WWBlocks.AUBURN_MOSS_CARPET.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
					).add(
						LootItem.lootTableItem(WWBlocks.AUBURN_CREEPING_MOSS.asItem())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
					)
					.setRolls(UniformGenerator.between(2, 5));

				tableBuilder.withPool(pool);
			}
		});
		//SAVANNA VILLAGE
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin()) {
				((FabricLootTableBuilder) tableBuilder)
					.modifyPools(builder -> {
						builder.add(
							LootItem.lootTableItem(WWItems.BAOBAB_NUT).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
						).add(
							LootItem.lootTableItem(WWBlocks.BAOBAB_LOG.asItem()).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
						);
					});
			}
		});
		//DESERT VILLAGE
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(id) && source.isBuiltin()) {
				((FabricLootTableBuilder) tableBuilder)
					.modifyPools(builder -> {
						builder.add(
								LootItem.lootTableItem(WWItems.COCONUT).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)
									.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
						).add(
							LootItem.lootTableItem(WWBlocks.PALM_LOG.asItem()).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1))
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F))
						);
					});
			}
		});
		//OSSEOUS SCULK
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(
						LootItem.lootTableItem(WWBlocks.OSSEOUS_SCULK.asItem()).setWeight(1).setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 5F)))
					).add(
						LootItem.lootTableItem(WWBlocks.HANGING_TENDRIL.asItem()).setWeight(1).setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
					).setRolls(UniformGenerator.between(1, 5));

				tableBuilder.withPool(pool);
			}
		});
	}
}
