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
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
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
		HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		this.dropSelf(WWBlocks.BAOBAB_LOG);
		this.dropSelf(WWBlocks.STRIPPED_BAOBAB_LOG);
		this.dropSelf(WWBlocks.BAOBAB_WOOD);
		this.dropSelf(WWBlocks.STRIPPED_BAOBAB_WOOD);
		this.dropSelf(WWBlocks.HOLLOWED_BAOBAB_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		this.dropSelf(WWBlocks.BAOBAB_PLANKS);
		this.dropSelf(WWBlocks.BAOBAB_BUTTON);
		this.dropSelf(WWBlocks.BAOBAB_PRESSURE_PLATE);
		this.dropSelf(WWBlocks.BAOBAB_TRAPDOOR);
		this.dropSelf(WWBlocks.BAOBAB_STAIRS);
		this.add(WWBlocks.BAOBAB_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.BAOBAB_FENCE);
		this.dropSelf(WWBlocks.BAOBAB_FENCE_GATE);
		this.add(WWBlocks.BAOBAB_DOOR, this::createDoorTable);
		this.dropSelf(WWBlocks.BAOBAB_SIGN);
		this.dropSelf(WWBlocks.BAOBAB_HANGING_SIGN);
		this.add(WWBlocks.BAOBAB_LEAVES,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.BAOBAB_LEAVES)
								.when(this.hasShearsOrSilkTouch())
						)
				).withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.when(this.doesNotHaveShearsOrSilkTouch())
						.add(
							this.applyExplosionDecay(
								WWBlocks.BAOBAB_LEAVES,
									LootItem.lootTableItem(Items.STICK)
										.apply(
											SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))
										)
								)
								.when(BonusLevelTableCondition.bonusLevelFlatChance(registryLookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);

		this.dropSelf(WWBlocks.CYPRESS_LOG);
		this.dropSelf(WWBlocks.STRIPPED_CYPRESS_LOG);
		this.dropSelf(WWBlocks.CYPRESS_WOOD);
		this.dropSelf(WWBlocks.STRIPPED_CYPRESS_WOOD);
		this.dropSelf(WWBlocks.HOLLOWED_CYPRESS_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		this.dropSelf(WWBlocks.CYPRESS_PLANKS);
		this.dropSelf(WWBlocks.CYPRESS_BUTTON);
		this.dropSelf(WWBlocks.CYPRESS_PRESSURE_PLATE);
		this.dropSelf(WWBlocks.CYPRESS_TRAPDOOR);
		this.dropSelf(WWBlocks.CYPRESS_STAIRS);
		this.add(WWBlocks.CYPRESS_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.CYPRESS_FENCE);
		this.dropSelf(WWBlocks.CYPRESS_FENCE_GATE);
		this.add(WWBlocks.CYPRESS_DOOR, this::createDoorTable);
		this.dropSelf(WWBlocks.CYPRESS_SIGN);
		this.dropSelf(WWBlocks.CYPRESS_HANGING_SIGN);
		this.dropSelf(WWBlocks.CYPRESS_SAPLING);
		this.add(WWBlocks.CYPRESS_LEAVES, block -> this.createLeavesDrops(block, WWBlocks.CYPRESS_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));

		this.dropSelf(WWBlocks.PALM_LOG);
		this.dropSelf(WWBlocks.STRIPPED_PALM_LOG);
		this.dropSelf(WWBlocks.PALM_WOOD);
		this.dropSelf(WWBlocks.STRIPPED_PALM_WOOD);
		this.dropSelf(WWBlocks.HOLLOWED_PALM_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		this.dropSelf(WWBlocks.PALM_PLANKS);
		this.dropSelf(WWBlocks.PALM_BUTTON);
		this.dropSelf(WWBlocks.PALM_PRESSURE_PLATE);
		this.dropSelf(WWBlocks.PALM_TRAPDOOR);
		this.dropSelf(WWBlocks.PALM_STAIRS);
		this.add(WWBlocks.PALM_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.PALM_FENCE);
		this.dropSelf(WWBlocks.PALM_FENCE_GATE);
		this.add(WWBlocks.PALM_DOOR, this::createDoorTable);
		this.dropSelf(WWBlocks.PALM_SIGN);
		this.dropSelf(WWBlocks.PALM_HANGING_SIGN);
		this.add(WWBlocks.PALM_FRONDS,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.PALM_FRONDS).when(this.hasShearsOrSilkTouch())
						)
				).withPool(
					LootPool.lootPool()
						.when(this.doesNotHaveShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.PALM_FRONDS,
									LootItem.lootTableItem(Items.STICK)
										.apply(
											SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))
										)
								)
								.when(BonusLevelTableCondition.bonusLevelFlatChance(registryLookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);

		this.dropSelf(WWBlocks.MAPLE_LOG);
		this.dropSelf(WWBlocks.STRIPPED_MAPLE_LOG);
		this.dropSelf(WWBlocks.MAPLE_WOOD);
		this.dropSelf(WWBlocks.STRIPPED_MAPLE_WOOD);
		this.dropSelf(WWBlocks.HOLLOWED_MAPLE_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		this.dropSelf(WWBlocks.MAPLE_PLANKS);
		this.dropSelf(WWBlocks.MAPLE_BUTTON);
		this.dropSelf(WWBlocks.MAPLE_PRESSURE_PLATE);
		this.dropSelf(WWBlocks.MAPLE_TRAPDOOR);
		this.dropSelf(WWBlocks.MAPLE_STAIRS);
		this.add(WWBlocks.MAPLE_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.MAPLE_FENCE);
		this.dropSelf(WWBlocks.MAPLE_FENCE_GATE);
		this.add(WWBlocks.MAPLE_DOOR, this::createDoorTable);
		this.dropSelf(WWBlocks.MAPLE_SIGN);
		this.dropSelf(WWBlocks.MAPLE_HANGING_SIGN);
		this.dropSelf(WWBlocks.MAPLE_SAPLING);
		this.add(WWBlocks.YELLOW_MAPLE_LEAVES, block -> this.createLeavesDrops(block, WWBlocks.MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(WWBlocks.ORANGE_MAPLE_LEAVES, block -> this.createLeavesDrops(block, WWBlocks.MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(WWBlocks.RED_MAPLE_LEAVES, block -> this.createLeavesDrops(block, WWBlocks.MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));

		this.dropSelf(WWBlocks.HOLLOWED_ACACIA_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_BIRCH_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_CHERRY_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_CRIMSON_STEM);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);
		this.dropSelf(WWBlocks.HOLLOWED_DARK_OAK_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_JUNGLE_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_MANGROVE_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_OAK_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_SPRUCE_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
		this.dropSelf(WWBlocks.HOLLOWED_WARPED_STEM);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.add(WWBlocks.ALGAE,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.ALGAE).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.POLLEN, block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));
		this.dropSelf(WWBlocks.SEEDING_DANDELION);
		this.dropSelf(WWBlocks.CARNATION);
		this.dropSelf(WWBlocks.MARIGOLD);
		this.dropSelf(WWBlocks.FLOWERING_LILY_PAD);
		this.dropSelf(WWBlocks.PRICKLY_PEAR_CACTUS);
		this.add(WWBlocks.MILKWEED, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(WWBlocks.DATURA, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(WWBlocks.CATTAIL, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

		this.dropSelf(WWBlocks.GLORY_OF_THE_SNOW);
		this.add(WWBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));
		this.add(WWBlocks.PINK_GIANT_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));
		this.add(WWBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));
		this.add(WWBlocks.ALBA_GLORY_OF_THE_SNOW, block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));

		this.add(WWBlocks.TUMBLEWEED_PLANT,
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.TUMBLEWEED_PLANT,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(ExplosionCondition.survivesExplosion())
							.add(LootItem.lootTableItem(WWBlocks.TUMBLEWEED_PLANT))
					)
				).withPool(
						LootPool.lootPool()
							.when(this.doesNotHaveShearsOrSilkTouch())
							.setRolls(ConstantValue.exactly(1F))
							.add(
								this.applyExplosionDecay(
									WWBlocks.TUMBLEWEED_PLANT,
									LootItem.lootTableItem(Items.STICK).apply(
										SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.TUMBLEWEED_PLANT)
													.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 2))
											)
									).apply(
										SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.TUMBLEWEED_PLANT)
													.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))
											)
									).when(BonusLevelTableCondition.bonusLevelFlatChance(registryLookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))
								)
							)
				).withPool(
					LootPool.lootPool()
						.when(this.hasShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.TUMBLEWEED).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
								.when(
									LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.TUMBLEWEED_PLANT)
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))
								)
						)
				)
		);

		this.add(WWBlocks.SPONGE_BUD,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
									WWBlocks.SPONGE_BUD,
									LootItem.lootTableItem(WWBlocks.SPONGE_BUD).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(1F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SPONGE_BUD)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 0))
											)
									).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SPONGE_BUD)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 1))
										)
									).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(3F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SPONGE_BUD)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 2))
											)
									)
								)
						)
				)
		);

		this.add(WWBlocks.BAOBAB_NUT,
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.BAOBAB_NUT,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BAOBAB_NUT)
									.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 2))
							)
							.add(LootItem.lootTableItem(WWBlocks.BAOBAB_NUT))
					)
				)
		);

		this.add(WWBlocks.COCONUT,
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.COCONUT,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.COCONUT)
									.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.HANGING, false))
							)
							.add(LootItem.lootTableItem(WWBlocks.COCONUT))
					)
				).withPool(
					this.applyExplosionDecay(
						WWBlocks.COCONUT,
						LootPool.lootPool()
							.setRolls(UniformGenerator.between(3F, 4F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.COCONUT)
									.setProperties(
										StatePropertiesPredicate.Builder.properties()
											.hasProperty(BlockStateProperties.HANGING, true)
											.hasProperty(BlockStateProperties.AGE_2, 2)
									)
							)
							.add(LootItem.lootTableItem(WWBlocks.COCONUT))
					)
				)
		);

		this.add(WWBlocks.BUSH,
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.BUSH,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BUSH)
									.setProperties(
										StatePropertiesPredicate.Builder.properties()
											.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
									)
							)
							.add(LootItem.lootTableItem(WWBlocks.BUSH))
					)
				).withPool(
					this.applyExplosionDecay(
						WWBlocks.BUSH,
						LootPool.lootPool()
							.setRolls(UniformGenerator.between(0F, 1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BUSH)
									.setProperties(
										StatePropertiesPredicate.Builder.properties()
											.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
											.hasProperty(BlockStateProperties.AGE_2, 2)
									)
							)
							.add(LootItem.lootTableItem(WWBlocks.BUSH))
					)
				)
		);

		this.dropPottedContents(WWBlocks.POTTED_SHORT_GRASS);
		this.dropPottedContents(WWBlocks.POTTED_BUSH);
		this.dropPottedContents(WWBlocks.POTTED_BAOBAB_NUT);
		this.dropPottedContents(WWBlocks.POTTED_COCONUT);
		this.dropPottedContents(WWBlocks.POTTED_CYPRESS_SAPLING);
		this.dropPottedContents(WWBlocks.POTTED_MAPLE_SAPLING);
		this.dropPottedContents(WWBlocks.POTTED_CARNATION);
		this.dropPottedContents(WWBlocks.POTTED_SEEDING_DANDELION);
		this.dropPottedContents(WWBlocks.POTTED_TUMBLEWEED_PLANT);
		this.dropPottedContents(WWBlocks.POTTED_TUMBLEWEED);
		this.dropPottedContents(WWBlocks.POTTED_PRICKLY_PEAR);
		this.dropPottedContents(WWBlocks.POTTED_BIG_DRIPLEAF);
		this.dropPottedContents(WWBlocks.POTTED_SMALL_DRIPLEAF);

		this.dropSelf(WWBlocks.NULL_BLOCK);
		this.dropSelf(WWBlocks.CHISELED_MUD_BRICKS);
		this.dropSelf(WWBlocks.TERMITE_MOUND);
		this.dropSelf(WWBlocks.BLUE_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.BLUE_NEMATOCYST);
		this.dropSelf(WWBlocks.LIME_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.LIME_NEMATOCYST);
		this.dropSelf(WWBlocks.PINK_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.PINK_NEMATOCYST);
		this.dropSelf(WWBlocks.YELLOW_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.YELLOW_NEMATOCYST);
		this.dropSelf(WWBlocks.RED_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.RED_NEMATOCYST);
		this.dropSelf(WWBlocks.BLUE_PEARLESCENT_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST);
		this.dropSelf(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA);
		this.dropWhenSilkTouch(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST);
		this.dropSelf(WWBlocks.OSTRICH_EGG);
		this.dropSelf(WWBlocks.GEYSER);
		this.dropWhenSilkTouch(WWBlocks.HANGING_TENDRIL);
		this.dropWhenSilkTouch(WWBlocks.OSSEOUS_SCULK);
		this.dropWhenSilkTouch(WWBlocks.SCULK_WALL);
		this.dropWhenSilkTouch(WWBlocks.SCULK_STAIRS);
		this.add(WWBlocks.SCULK_SLAB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(this.hasSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.SCULK_SLAB,
								LootItem.lootTableItem(WWBlocks.SCULK_SLAB)
									.apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(2F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SCULK_SLAB)
													.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))
											)
									)
							)
						)
				)
		);
		this.dropSelf(WWBlocks.STONE_CHEST);

		this.add(
			WWBlocks.DISPLAY_LANTERN,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.DISPLAY_LANTERN).when(ExplosionCondition.survivesExplosion()))
						.apply(
							CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(WWDataComponents.FIREFLIES)
								.when(this.hasSilkTouch())
						)
				)
		);

		this.add(WWBlocks.ECHO_GLASS,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.ECHO_GLASS)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
								.apply(CopyBlockState.copyState(WWBlocks.ECHO_GLASS).copy(WWBlockStateProperties.DAMAGE)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.ECHO_GLASS)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.DAMAGE, 0))
											.invert()
									)
								)
						)
				)
		);

		this.add(WWBlocks.SCORCHED_SAND,
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.SCORCHED_SAND,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(
								LootItem.lootTableItem(WWBlocks.SCORCHED_SAND)
									.apply(CopyBlockState.copyState(WWBlocks.SCORCHED_SAND).copy(WWBlockStateProperties.CRACKED)
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SCORCHED_SAND)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.CRACKED, true))
										)
									)
							)
					)
				)
		);


		this.add(WWBlocks.SCORCHED_RED_SAND,
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.SCORCHED_RED_SAND,
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(
								LootItem.lootTableItem(WWBlocks.SCORCHED_RED_SAND)
									.apply(CopyBlockState.copyState(WWBlocks.SCORCHED_RED_SAND).copy(WWBlockStateProperties.CRACKED)
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SCORCHED_RED_SAND)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.CRACKED, true))
										)
									)
							)
					)
				)
		);

		this.add(WWBlocks.BROWN_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(this.hasShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.BROWN_SHELF_FUNGI,
								LootItem.lootTableItem(WWBlocks.BROWN_SHELF_FUNGI).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(1F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(4F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				).withPool(
					LootPool.lootPool()
						.when(this.doesNotHaveShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.BROWN_SHELF_FUNGI,
								LootItem.lootTableItem(Blocks.BROWN_MUSHROOM).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(2F, 5F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(4F, 7F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(6F, 10F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BROWN_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				)
		);

		this.add(WWBlocks.RED_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(this.hasShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.RED_SHELF_FUNGI,
								LootItem.lootTableItem(WWBlocks.RED_SHELF_FUNGI).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(1F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(4F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				).withPool(
					LootPool.lootPool()
						.when(this.doesNotHaveShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.RED_SHELF_FUNGI,
								LootItem.lootTableItem(Blocks.RED_MUSHROOM).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(2F, 5F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(4F, 7F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(6F, 10F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.RED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				)
		);

		this.add(WWBlocks.CRIMSON_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(this.hasShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.CRIMSON_SHELF_FUNGI,
								LootItem.lootTableItem(WWBlocks.CRIMSON_SHELF_FUNGI).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(1F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(4F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				).withPool(
					LootPool.lootPool()
						.when(this.doesNotHaveShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.CRIMSON_SHELF_FUNGI,
								LootItem.lootTableItem(Blocks.CRIMSON_FUNGUS).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(2F, 5F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(4F, 7F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(6F, 10F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.CRIMSON_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				)
		);

		this.add(WWBlocks.WARPED_SHELF_FUNGI,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(this.hasShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.WARPED_SHELF_FUNGI,
								LootItem.lootTableItem(WWBlocks.WARPED_SHELF_FUNGI).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(1F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(ConstantValue.exactly(4F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				).withPool(
					LootPool.lootPool()
						.when(this.doesNotHaveShearsOrSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.WARPED_SHELF_FUNGI,
								LootItem.lootTableItem(Blocks.WARPED_FUNGUS).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 1))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(2F, 5F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 2))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(4F, 7F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 3))
										)
								).apply(
									SetItemCountFunction.setCount(UniformGenerator.between(6F, 10F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.WARPED_SHELF_FUNGI)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.FUNGUS_STAGE, 4))
										)
								)
							)
						)
				)
		);

		this.dropSelf(WWBlocks.CHISELED_MUD_BRICKS);
		this.dropSelf(WWBlocks.CRACKED_MUD_BRICKS);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICKS);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICK_STAIRS);
		this.add(WWBlocks.MOSSY_MUD_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICK_WALL);

		this.add(WWBlocks.OAK_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.OAK_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.SPRUCE_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.SPRUCE_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.BIRCH_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.BIRCH_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.JUNGLE_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.JUNGLE_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.ACACIA_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.ACACIA_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.DARK_OAK_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.DARK_OAK_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.MANGROVE_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.MANGROVE_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.CHERRY_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.CHERRY_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.AZALEA_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.AZALEA_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.FLOWERING_AZALEA_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.FLOWERING_AZALEA_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.BAOBAB_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.BAOBAB_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.CYPRESS_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.CYPRESS_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.PALM_FROND_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.PALM_FROND_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.YELLOW_MAPLE_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.YELLOW_MAPLE_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.ORANGE_MAPLE_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.ORANGE_MAPLE_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
		this.add(WWBlocks.RED_MAPLE_LEAF_LITTER,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.RED_MAPLE_LEAF_LITTER).when(this.hasShearsOrSilkTouch()))
				)
		);
	}

}
