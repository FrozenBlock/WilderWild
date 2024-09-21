/*
 * Copyright 2023-2024 FrozenBlock
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

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
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
		//ALGAE
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWBlocks.ALGAE.asItem()).setWeight(5).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

				tableBuilder.withPool(pool);
			}
		});
		//BAOBAB NUT
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWItems.BAOBAB_NUT).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

				tableBuilder.withPool(pool);
			}
		});
		//BAOBAB LOG
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWBlocks.BAOBAB_LOG.asItem()).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

				tableBuilder.withPool(pool);
			}
		});
		//COCONUT
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWItems.COCONUT).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

				tableBuilder.withPool(pool);
			}
		});
		//PALM LOG
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWBlocks.PALM_LOG.asItem()).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

				tableBuilder.withPool(pool);
			}
		});
		//OSSEOUS SCULK
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWBlocks.OSSEOUS_SCULK.asItem()).setWeight(1).setQuality(Rarity.RARE.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)));

				tableBuilder.withPool(pool);
			}
		});
		//HANGING TENDRIL
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(WWBlocks.HANGING_TENDRIL.asItem()).setWeight(1).setQuality(Rarity.RARE.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)));

				tableBuilder.withPool(pool);
			}
		});
	}
}
