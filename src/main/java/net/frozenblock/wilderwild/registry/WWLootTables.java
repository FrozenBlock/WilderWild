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

package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
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
				final LootPool.Builder pool = LootPool.lootPool();
				final WWWorldgenConfig worldgenConfig = WWWorldgenConfig.get();

				boolean modified = false;
				if (worldgenConfig.aquaticGeneration.algae) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.ALGAE.asItem())
							.setWeight(4)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 3F)))
					);
					modified = true;
				}

				if (worldgenConfig.aquaticGeneration.plankton) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.PLANKTON.asItem())
							.setWeight(1)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
					);
					modified = true;
				}

				if (worldgenConfig.aquaticGeneration.barnacle) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.BARNACLES.asItem())
							.setWeight(3)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
					);
					modified = true;
				}

				if (worldgenConfig.aquaticGeneration.tubeWorm) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.TUBE_WORMS.asItem())
							.setWeight(1)
							.setQuality(Rarity.UNCOMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);
					modified = true;
				}

				if (worldgenConfig.aquaticGeneration.seaAnemone) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.SEA_ANEMONE.asItem())
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 6F)))
					);
					modified = true;
				}

				if (worldgenConfig.aquaticGeneration.oceanAuburnMossGeneration) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.AUBURN_MOSS_BLOCK.asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);

					pool.add(
						LootItem.lootTableItem(WWBlocks.AUBURN_MOSS_CARPET.asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);

					pool.add(
						LootItem.lootTableItem(WWBlocks.AUBURN_CREEPING_MOSS.asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);

					modified = true;
				}

				if (modified) {
					pool.setRolls(UniformGenerator.between(2, 5));
					tableBuilder.withPool(pool);
				}
			}
		});
		//SAVANNA VILLAGE
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin() && WWWorldgenConfig.get().treeGeneration.baobab) {
				tableBuilder.modifyPools(builder -> {
					builder.add(
						LootItem.lootTableItem(WWItems.BAOBAB_NUT)
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
					).add(
						LootItem.lootTableItem(WWBlocks.BAOBAB_LOG.asItem())
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
					);
				});
			}
		});
		//DESERT VILLAGE
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(id) && source.isBuiltin() && WWWorldgenConfig.get().structure.newDesertVillages) {
				tableBuilder.modifyPools(builder -> {
					builder.add(
						LootItem.lootTableItem(WWItems.COCONUT)
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
					).add(
						LootItem.lootTableItem(WWBlocks.PALM_LOG.asItem())
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
					);
				});
			}
		});
		//OSSEOUS SCULK
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
				final WWBlockConfig.SculkConfig sculkConfig = WWBlockConfig.get().sculk;
				if (!sculkConfig.osseousSculkGeneration && !sculkConfig.tendrilGeneration) return;

				final LootPool.Builder pool = LootPool.lootPool();
				if (sculkConfig.osseousSculkGeneration) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.OSSEOUS_SCULK.asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 5F)))
					);
				}
				if (sculkConfig.tendrilGeneration) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.HANGING_TENDRIL.asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
					);
				}

				pool.setRolls(UniformGenerator.between(1, 5));
				tableBuilder.withPool(pool);
			}
		});
	}
}
