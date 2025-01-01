/*
 * Copyright 2024-2025 FrozenBlock
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

import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public class WWBlockLootHelper {

	public static void makeNonSaplingLeavesLoot(
		@NotNull BlockLootSubProvider lootProvider, Block leavesBlock, HolderLookup.@NotNull RegistryLookup<Enchantment> registryLookup
	) {
		lootProvider.add(leavesBlock,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(leavesBlock)
								.when(lootProvider.hasShearsOrSilkTouch())
						)
				).withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(lootProvider.doesNotHaveShearsOrSilkTouch())
						.add(
							lootProvider.applyExplosionDecay(
									leavesBlock,
									LootItem.lootTableItem(Items.STICK)
										.apply(
											SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))
										)
								)
								.when(BonusLevelTableCondition.bonusLevelFlatChance(registryLookup.getOrThrow(Enchantments.FORTUNE), BlockLootSubProvider.NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);
	}

	public static void makeShelfFungiLoot(@NotNull BlockLootSubProvider lootProvider, Block shelfFungiBlock, ItemLike dropWithoutShearsOrSilkTouch) {
		lootProvider.add(shelfFungiBlock,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(lootProvider.hasShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							lootProvider.applyExplosionDecay(
								shelfFungiBlock,
								LootItem.lootTableItem(shelfFungiBlock).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(1F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(4F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				).withPool(
					LootPool.lootPool()
						.when(lootProvider.doesNotHaveShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							lootProvider.applyExplosionDecay(
								shelfFungiBlock,
								LootItem.lootTableItem(dropWithoutShearsOrSilkTouch).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(2F, 5F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(4F, 7F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(6F, 10F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(shelfFungiBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				)
		);
	}

	public static void makeScorchedSandLoot(@NotNull BlockLootSubProvider lootProvider, Block scorchedSandBlock) {
		lootProvider.add(scorchedSandBlock,
			LootTable.lootTable()
				.withPool(
					lootProvider.applyExplosionCondition(
						scorchedSandBlock,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(
								LootItem.lootTableItem(scorchedSandBlock)
									.apply(CopyBlockState.copyState(scorchedSandBlock).copy(WWBlockStateProperties.CRACKED)
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(scorchedSandBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.CRACKED, true))
										)
									)
							)
					)
				)
		);
	}

	public static void makeShearsOrSilkTouchRequiredLoot(@NotNull BlockLootSubProvider lootProvider, Block block) {
		lootProvider.add(block,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(block).when(lootProvider.hasShearsOrSilkTouch()))
				)
		);
	}
}
