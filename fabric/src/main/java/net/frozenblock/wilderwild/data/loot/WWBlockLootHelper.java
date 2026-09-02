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

import java.util.stream.IntStream;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SegmentableBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class WWBlockLootHelper {

	public static void makeNonSaplingLeavesLoot(
		BlockLootSubProvider lootProvider, Block leavesBlock, HolderGetter<Enchantment> enchantments
	) {
		lootProvider.add(leavesBlock,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.add(
							LootItem.lootTableItem(leavesBlock)
								.when(lootProvider.hasShearsOrSilkTouch())
						)
				).withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.when(lootProvider.doesNotHaveShearsOrSilkTouch())
						.add(
							lootProvider.applyExplosionDecay(
									leavesBlock,
									LootItem.lootTableItem(Items.STICK)
										.apply(
											SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))
										)
								)
								.when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), BlockLootSubProvider.NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);
	}

	public static void makeShearsOrSilkTouchRequiredLoot(BlockLootSubProvider lootProvider, Block block) {
		lootProvider.add(block,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(block).when(lootProvider.hasShearsOrSilkTouch()))
				)
		);
	}

	public static void makeHangingFroglightLoot(BlockLootSubProvider lootProvider, Block bodyBlock, Block headBlock) {
		/*
		final LootTable.Builder builder = lootProvider.createSilkTouchOrShearsDispatchTable(headBlock, LootItem.lootTableItem(headBlock));
		lootProvider.add(bodyBlock, builder);
		lootProvider.add(headBlock, builder);
		 */
		lootProvider.add(bodyBlock, lootProvider.createSilkTouchOrShearsDispatchTable(headBlock, LootItem.lootTableItem(headBlock)));
		lootProvider.add(headBlock, lootProvider.createSilkTouchOrShearsDispatchTable(headBlock, LootItem.lootTableItem(headBlock)));
	}

	public static void createShearsOrSilkTouchRequiredSegmentedBlockDrops(BlockLootSubProvider lootProvider, Block block) {
		lootProvider.add(block,
			block instanceof SegmentableBlock segmentableBlock
				? LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ContextIntProviders.exactly(1))
						.add(
							LootItem.lootTableItem(block)
								.apply(
									IntStream.rangeClosed(1, 4).boxed().toList(),
									integer -> SetItemCountFunction.setCount(ContextIntProviders.exactly(integer))
										.when(
											MatchBlock.blockMatches(
												lootProvider.blocks,
												block,
												StatePropertiesPredicate.Builder.properties()
													.hasProperty(segmentableBlock.getSegmentAmountProperty(), integer)
											)
										)
								)
						).when(lootProvider.hasShearsOrSilkTouch())
				)
				: lootProvider.noDrop()
		);
	}
}
