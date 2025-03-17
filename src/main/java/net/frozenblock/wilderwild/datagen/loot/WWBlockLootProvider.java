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
		WWBlockLootHelper.makeNonSaplingLeavesLoot(this, WWBlocks.BAOBAB_LEAVES, registryLookup);

		this.dropSelf(WWBlocks.WILLOW_LOG);
		this.dropSelf(WWBlocks.STRIPPED_WILLOW_LOG);
		this.dropSelf(WWBlocks.WILLOW_WOOD);
		this.dropSelf(WWBlocks.STRIPPED_WILLOW_WOOD);
		this.dropSelf(WWBlocks.HOLLOWED_WILLOW_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		this.dropSelf(WWBlocks.WILLOW_PLANKS);
		this.dropSelf(WWBlocks.WILLOW_BUTTON);
		this.dropSelf(WWBlocks.WILLOW_PRESSURE_PLATE);
		this.dropSelf(WWBlocks.WILLOW_TRAPDOOR);
		this.dropSelf(WWBlocks.WILLOW_STAIRS);
		this.add(WWBlocks.WILLOW_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.WILLOW_FENCE);
		this.dropSelf(WWBlocks.WILLOW_FENCE_GATE);
		this.add(WWBlocks.WILLOW_DOOR, this::createDoorTable);
		this.dropSelf(WWBlocks.WILLOW_SIGN);
		this.dropSelf(WWBlocks.WILLOW_HANGING_SIGN);
		this.dropSelf(WWBlocks.WILLOW_SAPLING);
		this.add(WWBlocks.WILLOW_LEAVES, block -> this.createLeavesDrops(block, WWBlocks.WILLOW_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));

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
		WWBlockLootHelper.makeNonSaplingLeavesLoot(this, WWBlocks.PALM_FRONDS, registryLookup);

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
		this.dropSelf(WWBlocks.HOLLOWED_PALE_OAK_LOG);
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
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

		this.dropSelf(WWBlocks.GABBRO);
		this.dropSelf(WWBlocks.GABBRO_STAIRS);
		this.add(WWBlocks.GABBRO_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.GABBRO_WALL);
		this.dropSelf(WWBlocks.POLISHED_GABBRO);
		this.dropSelf(WWBlocks.POLISHED_GABBRO_STAIRS);
		this.add(WWBlocks.POLISHED_GABBRO_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.POLISHED_GABBRO_WALL);
		this.dropSelf(WWBlocks.CHISELED_GABBRO_BRICKS);
		this.dropSelf(WWBlocks.GABBRO_BRICKS);
		this.dropSelf(WWBlocks.CRACKED_GABBRO_BRICKS);
		this.dropSelf(WWBlocks.GABBRO_BRICK_STAIRS);
		this.add(WWBlocks.GABBRO_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.GABBRO_BRICK_WALL);
		this.dropSelf(WWBlocks.MOSSY_GABBRO_BRICKS);
		this.dropSelf(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);
		this.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.MOSSY_GABBRO_BRICK_WALL);

		this.add(WWBlocks.POLLEN, block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));
		this.dropSelf(WWBlocks.SEEDING_DANDELION);
		this.dropSelf(WWBlocks.CARNATION);
		this.dropSelf(WWBlocks.MARIGOLD);
		this.dropSelf(WWBlocks.PASQUEFLOWER);
		this.dropSelf(WWBlocks.RED_HIBISCUS);
		this.dropSelf(WWBlocks.YELLOW_HIBISCUS);
		this.dropSelf(WWBlocks.WHITE_HIBISCUS);
		this.dropSelf(WWBlocks.PINK_HIBISCUS);
		this.dropSelf(WWBlocks.PURPLE_HIBISCUS);
		this.dropSelf(WWBlocks.FLOWERING_LILY_PAD);
		this.dropSelf(WWBlocks.PRICKLY_PEAR_CACTUS);
		this.add(WWBlocks.MILKWEED, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(WWBlocks.DATURA, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(WWBlocks.CATTAIL, block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

		this.add(WWBlocks.WILDFLOWERS, this.createPetalsDrops(WWBlocks.WILDFLOWERS));
		this.add(WWBlocks.PHLOX, this.createPetalsDrops(WWBlocks.PHLOX));
		this.add(WWBlocks.LANTANAS, this.createPetalsDrops(WWBlocks.LANTANAS));
		WWBlockLootHelper.makeShearsOrSilkTouchRequiredPetalsDrops(this, WWBlocks.CLOVERS);

		this.add(WWBlocks.FROZEN_LARGE_FERN, block -> this.createDoublePlantWithSeedDrops(block, WWBlocks.FROZEN_FERN));
		this.add(WWBlocks.FROZEN_TALL_GRASS, block -> this.createDoublePlantWithSeedDrops(block, WWBlocks.FROZEN_SHORT_GRASS));
		this.add(WWBlocks.FROZEN_FERN, this::createGrassDrops);
		this.add(WWBlocks.FROZEN_SHORT_GRASS, this::createGrassDrops);

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
		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.ALGAE);
		this.dropSelf(WWBlocks.PLANKTON);
		this.add(WWBlocks.BARNACLES, block -> this.createMultifaceBlockDrops(block, ExplosionCondition.survivesExplosion()));
		this.dropSelf(WWBlocks.SEA_ANEMONE);
		this.dropSelf(WWBlocks.SEA_WHIP);
		this.dropSelf(WWBlocks.TUBE_WORMS);

		this.dropSelf(WWBlocks.AUBURN_MOSS_BLOCK);
		this.dropSelf(WWBlocks.AUBURN_MOSS_CARPET);
		this.add(WWBlocks.AUBURN_CREEPING_MOSS, block -> this.createMultifaceBlockDrops(block, ExplosionCondition.survivesExplosion()));

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
		this.dropPottedContents(WWBlocks.POTTED_WILLOW_SAPLING);
		this.dropPottedContents(WWBlocks.POTTED_CYPRESS_SAPLING);
		this.dropPottedContents(WWBlocks.POTTED_MAPLE_SAPLING);
		this.dropPottedContents(WWBlocks.POTTED_CARNATION);
		this.dropPottedContents(WWBlocks.POTTED_MARIGOLD);
		this.dropPottedContents(WWBlocks.POTTED_PASQUEFLOWER);
		this.dropPottedContents(WWBlocks.POTTED_RED_HIBISCUS);
		this.dropPottedContents(WWBlocks.POTTED_YELLOW_HIBISCUS);
		this.dropPottedContents(WWBlocks.POTTED_WHITE_HIBISCUS);
		this.dropPottedContents(WWBlocks.POTTED_PINK_HIBISCUS);
		this.dropPottedContents(WWBlocks.POTTED_PURPLE_HIBISCUS);
		this.dropPottedContents(WWBlocks.POTTED_SEEDING_DANDELION);
		this.dropPottedContents(WWBlocks.POTTED_TUMBLEWEED_PLANT);
		this.dropPottedContents(WWBlocks.POTTED_TUMBLEWEED);
		this.dropPottedContents(WWBlocks.POTTED_PRICKLY_PEAR);
		this.dropPottedContents(WWBlocks.POTTED_BIG_DRIPLEAF);
		this.dropPottedContents(WWBlocks.POTTED_SMALL_DRIPLEAF);
		this.dropPottedContents(WWBlocks.POTTED_MYCELIUM_GROWTH);
		this.dropPottedContents(WWBlocks.POTTED_PINK_PETALS);
		this.dropPottedContents(WWBlocks.POTTED_WILDFLOWERS);
		this.dropPottedContents(WWBlocks.POTTED_PHLOX);
		this.dropPottedContents(WWBlocks.POTTED_LANTANAS);
		this.dropPottedContents(WWBlocks.POTTED_FROZEN_SHORT_GRASS);
		this.dropPottedContents(WWBlocks.POTTED_FROZEN_FERN);

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

		WWBlockLootHelper.makeScorchedSandLoot(this, WWBlocks.SCORCHED_SAND);
		WWBlockLootHelper.makeScorchedSandLoot(this, WWBlocks.SCORCHED_RED_SAND);

		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.BROWN_SHELF_FUNGI, Items.BROWN_MUSHROOM);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.RED_SHELF_FUNGI, Items.RED_MUSHROOM);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.PALE_SHELF_FUNGI, WWBlocks.PALE_MUSHROOM);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.CRIMSON_SHELF_FUNGI, Items.CRIMSON_FUNGUS);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.WARPED_SHELF_FUNGI, Items.WARPED_FUNGUS);

		this.dropSelf(WWBlocks.PALE_MUSHROOM);
		this.dropPottedContents(WWBlocks.POTTED_PALE_MUSHROOM);
		this.add(WWBlocks.PALE_MUSHROOM_BLOCK, block -> this.createMushroomBlockDrop(block, WWBlocks.PALE_MUSHROOM));

		this.dropSelf(WWBlocks.CHISELED_MUD_BRICKS);
		this.dropSelf(WWBlocks.CRACKED_MUD_BRICKS);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICKS);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICK_STAIRS);
		this.add(WWBlocks.MOSSY_MUD_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICK_WALL);

		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.MYCELIUM_GROWTH);
		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.YELLOW_MAPLE_LEAF_LITTER);
		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.ORANGE_MAPLE_LEAF_LITTER);
		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.RED_MAPLE_LEAF_LITTER);

		this.dropWhenSilkTouch(WWBlocks.FRAGILE_ICE);
		this.dropWhenSilkTouch(WWBlocks.ICICLE);
	}

}
