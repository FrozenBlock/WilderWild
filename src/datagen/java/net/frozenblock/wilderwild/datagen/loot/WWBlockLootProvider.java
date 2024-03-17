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

package net.frozenblock.wilderwild.datagen.loot;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterDataComponents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public final class WWBlockLootProvider extends FabricBlockLootTableProvider {

	public WWBlockLootProvider(@NotNull FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	public void generate() {
		this.dropSelf(RegisterBlocks.BAOBAB_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_BAOBAB_LOG);
		this.dropSelf(RegisterBlocks.BAOBAB_WOOD);
		this.dropSelf(RegisterBlocks.STRIPPED_BAOBAB_WOOD);
		this.dropSelf(RegisterBlocks.HOLLOWED_BAOBAB_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		this.dropSelf(RegisterBlocks.BAOBAB_PLANKS);
		this.dropSelf(RegisterBlocks.BAOBAB_BUTTON);
		this.dropSelf(RegisterBlocks.BAOBAB_PRESSURE_PLATE);
		this.dropSelf(RegisterBlocks.BAOBAB_TRAPDOOR);
		this.dropSelf(RegisterBlocks.BAOBAB_STAIRS);
		this.add(RegisterBlocks.BAOBAB_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.BAOBAB_FENCE);
		this.dropSelf(RegisterBlocks.BAOBAB_FENCE_GATE);
		this.add(RegisterBlocks.BAOBAB_DOOR, this::createDoorTable);
		this.dropSelf(RegisterBlocks.BAOBAB_SIGN);
		this.dropSelf(RegisterBlocks.BAOBAB_HANGING_SIGN);
		this.add(RegisterBlocks.BAOBAB_LEAVES,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add((LootItem.lootTableItem(RegisterBlocks.BAOBAB_LEAVES).when(BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH)))
				).withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(HAS_NO_SHEARS_OR_SILK_TOUCH)
						.add(
							this.applyExplosionDecay(RegisterBlocks.BAOBAB_LEAVES, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))))
								.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);

		this.dropSelf(RegisterBlocks.CYPRESS_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_CYPRESS_LOG);
		this.dropSelf(RegisterBlocks.CYPRESS_WOOD);
		this.dropSelf(RegisterBlocks.STRIPPED_CYPRESS_WOOD);
		this.dropSelf(RegisterBlocks.HOLLOWED_CYPRESS_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		this.dropSelf(RegisterBlocks.CYPRESS_PLANKS);
		this.dropSelf(RegisterBlocks.CYPRESS_BUTTON);
		this.dropSelf(RegisterBlocks.CYPRESS_PRESSURE_PLATE);
		this.dropSelf(RegisterBlocks.CYPRESS_TRAPDOOR);
		this.dropSelf(RegisterBlocks.CYPRESS_STAIRS);
		this.add(RegisterBlocks.CYPRESS_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.CYPRESS_FENCE);
		this.dropSelf(RegisterBlocks.CYPRESS_FENCE_GATE);
		this.add(RegisterBlocks.CYPRESS_DOOR, this::createDoorTable);
		this.dropSelf(RegisterBlocks.CYPRESS_SIGN);
		this.dropSelf(RegisterBlocks.CYPRESS_HANGING_SIGN);
		this.dropSelf(RegisterBlocks.CYPRESS_SAPLING);
		this.add(RegisterBlocks.CYPRESS_LEAVES, block -> this.createLeavesDrops(block, RegisterBlocks.CYPRESS_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));

		this.dropSelf(RegisterBlocks.PALM_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_PALM_LOG);
		this.dropSelf(RegisterBlocks.PALM_WOOD);
		this.dropSelf(RegisterBlocks.STRIPPED_PALM_WOOD);
		this.dropSelf(RegisterBlocks.HOLLOWED_PALM_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		this.dropSelf(RegisterBlocks.PALM_CROWN);
		this.dropSelf(RegisterBlocks.PALM_PLANKS);
		this.dropSelf(RegisterBlocks.PALM_BUTTON);
		this.dropSelf(RegisterBlocks.PALM_PRESSURE_PLATE);
		this.dropSelf(RegisterBlocks.PALM_TRAPDOOR);
		this.dropSelf(RegisterBlocks.PALM_STAIRS);
		this.add(RegisterBlocks.PALM_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.PALM_FENCE);
		this.dropSelf(RegisterBlocks.PALM_FENCE_GATE);
		this.add(RegisterBlocks.PALM_DOOR, this::createDoorTable);
		this.dropSelf(RegisterBlocks.PALM_SIGN);
		this.dropSelf(RegisterBlocks.PALM_HANGING_SIGN);
		this.add(RegisterBlocks.PALM_FRONDS,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add((LootItem.lootTableItem(RegisterBlocks.PALM_FRONDS).when(BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH)))
				).withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(HAS_NO_SHEARS_OR_SILK_TOUCH)
						.add(
							this.applyExplosionDecay(RegisterBlocks.PALM_FRONDS, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))))
								.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);

		this.dropSelf(RegisterBlocks.HOLLOWED_ACACIA_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_BIRCH_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_CHERRY_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_CRIMSON_STEM);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);
		this.dropSelf(RegisterBlocks.HOLLOWED_DARK_OAK_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_JUNGLE_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_MANGROVE_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_OAK_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_SPRUCE_LOG);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
		this.dropSelf(RegisterBlocks.HOLLOWED_WARPED_STEM);
		this.dropSelf(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.add(RegisterBlocks.ALGAE,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add((LootItem.lootTableItem(RegisterBlocks.ALGAE).when(BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH)))
				)
		);
		this.add(RegisterBlocks.POLLEN, block -> this.createMultifaceBlockDrops(block, BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH));
		this.dropSelf(RegisterBlocks.SEEDING_DANDELION);
		this.dropSelf(RegisterBlocks.CARNATION);
		this.dropSelf(RegisterBlocks.FLOWERING_LILY_PAD);
		this.dropSelf(RegisterBlocks.PRICKLY_PEAR_CACTUS);
		this.add(RegisterBlocks.MILKWEED, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(RegisterBlocks.DATURA, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(RegisterBlocks.CATTAIL, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

		this.dropSelf(RegisterBlocks.GLORY_OF_THE_SNOW);
		this.add(RegisterBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH));
		this.add(RegisterBlocks.PINK_GIANT_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH));
		this.add(RegisterBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH));
		this.add(RegisterBlocks.ALBA_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, BlockLootSubProvider.HAS_SHEARS_OR_SILK_TOUCH));

		this.add(RegisterBlocks.TUMBLEWEED_PLANT,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(RegisterBlocks.TUMBLEWEED_PLANT))
				).withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(HAS_NO_SHEARS_OR_SILK_TOUCH)
						.add(
							this.applyExplosionDecay(
								RegisterBlocks.TUMBLEWEED_PLANT,
									LootItem.lootTableItem(Items.STICK).apply(
											SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))
												.when(
													LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.TUMBLEWEED_PLANT)
														.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 2))
												)
										).apply(
											SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))
												.when(
													LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.TUMBLEWEED_PLANT)
														.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))
												)
										)
								)
								.when(
									BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, NORMAL_LEAVES_STICK_CHANCES)
								)
						)
				).withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(HAS_SHEARS_OR_SILK_TOUCH)
						.add(
							this.applyExplosionDecay(
								RegisterBlocks.TUMBLEWEED_PLANT,
									LootItem.lootTableItem(RegisterBlocks.TUMBLEWEED).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
								)
								.when(
									LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.TUMBLEWEED_PLANT)
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))
								)
						)
				)
		);

		this.add(RegisterBlocks.SMALL_SPONGE,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
									RegisterBlocks.SMALL_SPONGE,
									LootItem.lootTableItem(RegisterBlocks.SMALL_SPONGE).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(1F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.SMALL_SPONGE)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 0))
											)
									).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.SMALL_SPONGE)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 1))
										)
									).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(3F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.SMALL_SPONGE)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 2))
											)
									)
								)
						)
				)
		);

		this.add(RegisterBlocks.BAOBAB_NUT,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(
							LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.BAOBAB_NUT)
								.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 2))
								.invert()
						)
						.add(LootItem.lootTableItem(RegisterBlocks.BAOBAB_NUT))
				)
		);

		this.dropPottedContents(RegisterBlocks.POTTED_SHORT_GRASS);
		this.dropPottedContents(RegisterBlocks.POTTED_BUSH);
		this.dropPottedContents(RegisterBlocks.POTTED_BAOBAB_NUT);
		this.dropPottedContents(RegisterBlocks.POTTED_COCONUT);
		this.dropPottedContents(RegisterBlocks.POTTED_CYPRESS_SAPLING);
		this.dropPottedContents(RegisterBlocks.POTTED_CARNATION);
		this.dropPottedContents(RegisterBlocks.POTTED_SEEDING_DANDELION);
		this.dropPottedContents(RegisterBlocks.POTTED_TUMBLEWEED_PLANT);
		this.dropPottedContents(RegisterBlocks.POTTED_TUMBLEWEED);
		this.dropPottedContents(RegisterBlocks.POTTED_PRICKLY_PEAR);
		this.dropPottedContents(RegisterBlocks.POTTED_BIG_DRIPLEAF);
		this.dropPottedContents(RegisterBlocks.POTTED_SMALL_DRIPLEAF);

		this.dropSelf(RegisterBlocks.NULL_BLOCK);
		this.dropSelf(RegisterBlocks.CHISELED_MUD_BRICKS);
		this.dropSelf(RegisterBlocks.TERMITE_MOUND);
		this.dropSelf(RegisterBlocks.BLUE_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.BLUE_NEMATOCYST);
		this.dropSelf(RegisterBlocks.LIME_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.LIME_NEMATOCYST);
		this.dropSelf(RegisterBlocks.PINK_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.PINK_NEMATOCYST);
		this.dropSelf(RegisterBlocks.YELLOW_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.YELLOW_NEMATOCYST);
		this.dropSelf(RegisterBlocks.RED_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.RED_NEMATOCYST);
		this.dropSelf(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST);
		this.dropSelf(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA);
		this.dropWhenSilkTouch(RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST);
		this.dropSelf(RegisterBlocks.OSTRICH_EGG);
		this.dropSelf(RegisterBlocks.GEYSER);
		this.dropWhenSilkTouch(RegisterBlocks.HANGING_TENDRIL);
		this.dropWhenSilkTouch(RegisterBlocks.OSSEOUS_SCULK);
		this.dropWhenSilkTouch(RegisterBlocks.SCULK_WALL);
		this.dropWhenSilkTouch(RegisterBlocks.SCULK_STAIRS);
		this.dropSelf(RegisterBlocks.STONE_CHEST);

		this.add(
			RegisterBlocks.DISPLAY_LANTERN,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(RegisterBlocks.DISPLAY_LANTERN))
						.apply(
							CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).copy(RegisterDataComponents.FIREFLIES)
								.when(
									BlockLootSubProvider.HAS_SILK_TOUCH
								)
						)
				)
		);

		this.add(RegisterBlocks.ECHO_GLASS,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(RegisterBlocks.ECHO_GLASS)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
								.apply(CopyBlockState.copyState(RegisterBlocks.ECHO_GLASS).copy(RegisterProperties.DAMAGE)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.ECHO_GLASS)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RegisterProperties.DAMAGE, 0))
											.invert()
									)
								)
						)
				)
		);

	}

}
