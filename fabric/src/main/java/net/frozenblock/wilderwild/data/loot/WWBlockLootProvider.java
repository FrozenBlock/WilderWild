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
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFabricBlocks;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
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
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class WWBlockLootProvider extends FabricBlockLootSubProvider {

	public WWBlockLootProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	public void generate() {
		final HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		this.dropSelf(WWBlocks.BAOBAB_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_BAOBAB_LOG.get());
		this.dropSelf(WWBlocks.BAOBAB_WOOD.get());
		this.dropSelf(WWBlocks.STRIPPED_BAOBAB_WOOD.get());
		this.dropSelf(WWBlocks.HOLLOWED_BAOBAB_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get());
		this.dropSelf(WWBlocks.BAOBAB_PLANKS.get());
		this.dropSelf(WWBlocks.BAOBAB_BUTTON.get());
		this.dropSelf(WWBlocks.BAOBAB_PRESSURE_PLATE.get());
		this.dropSelf(WWBlocks.BAOBAB_TRAPDOOR.get());
		this.dropSelf(WWBlocks.BAOBAB_STAIRS.get());
		this.add(WWBlocks.BAOBAB_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.BAOBAB_FENCE.get());
		this.dropSelf(WWBlocks.BAOBAB_FENCE_GATE.get());
		this.add(WWBlocks.BAOBAB_DOOR.get(), this::createDoorTable);
		this.dropSelf(WWBlocks.BAOBAB_SIGN.get());
		this.dropSelf(WWBlocks.BAOBAB_HANGING_SIGN.get());
		this.dropSelf(WWBlocks.BAOBAB_SHELF.get());
		WWBlockLootHelper.makeNonSaplingLeavesLoot(this, WWBlocks.BAOBAB_LEAVES.get(), registryLookup);

		this.dropSelf(WWBlocks.WILLOW_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_WILLOW_LOG.get());
		this.dropSelf(WWBlocks.WILLOW_WOOD.get());
		this.dropSelf(WWBlocks.STRIPPED_WILLOW_WOOD.get());
		this.dropSelf(WWBlocks.HOLLOWED_WILLOW_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get());
		this.dropSelf(WWBlocks.WILLOW_PLANKS.get());
		this.dropSelf(WWBlocks.WILLOW_BUTTON.get());
		this.dropSelf(WWBlocks.WILLOW_PRESSURE_PLATE.get());
		this.dropSelf(WWBlocks.WILLOW_TRAPDOOR.get());
		this.dropSelf(WWBlocks.WILLOW_STAIRS.get());
		this.add(WWBlocks.WILLOW_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.WILLOW_FENCE.get());
		this.dropSelf(WWBlocks.WILLOW_FENCE_GATE.get());
		this.add(WWBlocks.WILLOW_DOOR.get(), this::createDoorTable);
		this.dropSelf(WWBlocks.WILLOW_SIGN.get());
		this.dropSelf(WWBlocks.WILLOW_HANGING_SIGN.get());
		this.dropSelf(WWBlocks.WILLOW_SHELF.get());
		this.dropSelf(WWBlocks.WILLOW_SAPLING.get());
		this.add(WWBlocks.WILLOW_LEAVES.get(), block -> this.createLeavesDrops(block, WWBlocks.WILLOW_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

		this.dropSelf(WWBlocks.CYPRESS_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_CYPRESS_LOG.get());
		this.dropSelf(WWBlocks.CYPRESS_WOOD.get());
		this.dropSelf(WWBlocks.STRIPPED_CYPRESS_WOOD.get());
		this.dropSelf(WWBlocks.HOLLOWED_CYPRESS_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get());
		this.dropSelf(WWBlocks.CYPRESS_PLANKS.get());
		this.dropSelf(WWBlocks.CYPRESS_BUTTON.get());
		this.dropSelf(WWBlocks.CYPRESS_PRESSURE_PLATE.get());
		this.dropSelf(WWBlocks.CYPRESS_TRAPDOOR.get());
		this.dropSelf(WWBlocks.CYPRESS_STAIRS.get());
		this.add(WWBlocks.CYPRESS_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.CYPRESS_FENCE.get());
		this.dropSelf(WWBlocks.CYPRESS_FENCE_GATE.get());
		this.add(WWBlocks.CYPRESS_DOOR.get(), this::createDoorTable);
		this.dropSelf(WWBlocks.CYPRESS_SIGN.get());
		this.dropSelf(WWBlocks.CYPRESS_HANGING_SIGN.get());
		this.dropSelf(WWBlocks.CYPRESS_SHELF.get());
		this.dropSelf(WWBlocks.CYPRESS_SAPLING.get());
		this.add(WWBlocks.CYPRESS_LEAVES.get(), block -> this.createLeavesDrops(block, WWBlocks.CYPRESS_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

		this.dropSelf(WWBlocks.PALM_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_PALM_LOG.get());
		this.dropSelf(WWBlocks.PALM_WOOD.get());
		this.dropSelf(WWBlocks.STRIPPED_PALM_WOOD.get());
		this.dropSelf(WWBlocks.HOLLOWED_PALM_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get());
		this.dropSelf(WWBlocks.PALM_PLANKS.get());
		this.dropSelf(WWBlocks.PALM_BUTTON.get());
		this.dropSelf(WWBlocks.PALM_PRESSURE_PLATE.get());
		this.dropSelf(WWBlocks.PALM_TRAPDOOR.get());
		this.dropSelf(WWBlocks.PALM_STAIRS.get());
		this.add(WWBlocks.PALM_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.PALM_FENCE.get());
		this.dropSelf(WWBlocks.PALM_FENCE_GATE.get());
		this.add(WWBlocks.PALM_DOOR.get(), this::createDoorTable);
		this.dropSelf(WWBlocks.PALM_SIGN.get());
		this.dropSelf(WWBlocks.PALM_HANGING_SIGN.get());
		this.dropSelf(WWBlocks.PALM_SHELF.get());
		WWBlockLootHelper.makeNonSaplingLeavesLoot(this, WWBlocks.PALM_FRONDS.get(), registryLookup);

		this.dropSelf(WWBlocks.MAPLE_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_MAPLE_LOG.get());
		this.dropSelf(WWBlocks.MAPLE_WOOD.get());
		this.dropSelf(WWBlocks.STRIPPED_MAPLE_WOOD.get());
		this.dropSelf(WWBlocks.HOLLOWED_MAPLE_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get());
		this.dropSelf(WWBlocks.MAPLE_PLANKS.get());
		this.dropSelf(WWBlocks.MAPLE_BUTTON.get());
		this.dropSelf(WWBlocks.MAPLE_PRESSURE_PLATE.get());
		this.dropSelf(WWBlocks.MAPLE_TRAPDOOR.get());
		this.dropSelf(WWBlocks.MAPLE_STAIRS.get());
		this.add(WWBlocks.MAPLE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.MAPLE_FENCE.get());
		this.dropSelf(WWBlocks.MAPLE_FENCE_GATE.get());
		this.add(WWBlocks.MAPLE_DOOR.get(), this::createDoorTable);
		this.dropSelf(WWBlocks.MAPLE_SIGN.get());
		this.dropSelf(WWBlocks.MAPLE_HANGING_SIGN.get());
		this.dropSelf(WWBlocks.MAPLE_SHELF.get());
		this.dropSelf(WWBlocks.YELLOW_MAPLE_SAPLING.get());
		this.dropSelf(WWBlocks.ORANGE_MAPLE_SAPLING.get());
		this.dropSelf(WWBlocks.RED_MAPLE_SAPLING.get());
		this.add(WWBlocks.YELLOW_MAPLE_LEAVES.get(), block -> this.createLeavesDrops(block, WWBlocks.YELLOW_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(WWBlocks.ORANGE_MAPLE_LEAVES.get(), block -> this.createLeavesDrops(block, WWBlocks.ORANGE_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(WWBlocks.RED_MAPLE_LEAVES.get(), block -> this.createLeavesDrops(block, WWBlocks.RED_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

		this.dropSelf(WWBlocks.HOLLOWED_ACACIA_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_BIRCH_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_CHERRY_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_CRIMSON_STEM.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get());
		this.dropSelf(WWBlocks.HOLLOWED_DARK_OAK_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_PALE_OAK_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_JUNGLE_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_MANGROVE_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_OAK_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_SPRUCE_LOG.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get());
		this.dropSelf(WWBlocks.HOLLOWED_WARPED_STEM.get());
		this.dropSelf(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get());

		this.add(WWBlocks.ACACIA_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.ACACIA_LEAF_LITTER.get()));
		this.add(WWBlocks.AZALEA_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.AZALEA_LEAF_LITTER.get()));
		this.add(WWBlocks.BAOBAB_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.BAOBAB_LEAF_LITTER.get()));
		this.add(WWBlocks.BIRCH_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.BIRCH_LEAF_LITTER.get()));
		this.add(WWBlocks.CHERRY_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.CHERRY_LEAF_LITTER.get()));
		this.add(WWBlocks.CYPRESS_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.CYPRESS_LEAF_LITTER.get()));
		this.add(WWBlocks.DARK_OAK_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.DARK_OAK_LEAF_LITTER.get()));
		this.add(WWBlocks.JUNGLE_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.JUNGLE_LEAF_LITTER.get()));
		this.add(WWBlocks.MANGROVE_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.MANGROVE_LEAF_LITTER.get()));
		this.add(WWBlocks.PALE_OAK_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.PALE_OAK_LEAF_LITTER.get()));
		this.add(WWBlocks.PALM_FROND_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.PALM_FROND_LITTER.get()));
		this.add(WWBlocks.SPRUCE_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.SPRUCE_LEAF_LITTER.get()));
		this.add(WWBlocks.WILLOW_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.WILLOW_LEAF_LITTER.get()));
		this.add(WWBlocks.YELLOW_MAPLE_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.YELLOW_MAPLE_LEAF_LITTER.get()));
		this.add(WWBlocks.ORANGE_MAPLE_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.ORANGE_MAPLE_LEAF_LITTER.get()));
		this.add(WWBlocks.RED_MAPLE_LEAF_LITTER.get(), this.createSegmentedBlockDrops(WWBlocks.RED_MAPLE_LEAF_LITTER.get()));

		this.dropSelf(WWBlocks.GABBRO.get());
		this.dropSelf(WWBlocks.GABBRO_STAIRS.get());
		this.add(WWBlocks.GABBRO_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.GABBRO_WALL.get());
		this.dropSelf(WWBlocks.POLISHED_GABBRO.get());
		this.dropSelf(WWBlocks.POLISHED_GABBRO_STAIRS.get());
		this.add(WWBlocks.POLISHED_GABBRO_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.POLISHED_GABBRO_WALL.get());
		this.dropSelf(WWBlocks.CHISELED_GABBRO_BRICKS.get());
		this.dropSelf(WWBlocks.GABBRO_BRICKS.get());
		this.dropSelf(WWBlocks.CRACKED_GABBRO_BRICKS.get());
		this.dropSelf(WWBlocks.GABBRO_BRICK_STAIRS.get());
		this.add(WWBlocks.GABBRO_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.GABBRO_BRICK_WALL.get());
		this.dropSelf(WWBlocks.MOSSY_GABBRO_BRICKS.get());
		this.dropSelf(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS.get());
		this.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.MOSSY_GABBRO_BRICK_WALL.get());

		this.add(WWBlocks.POLLEN.get(), block -> this.createMultifaceBlockDrops(block, this.hasShearsOrSilkTouch()));
		this.dropSelf(WWFabricBlocks.SEEDING_DANDELION.get());
		this.dropSelf(WWBlocks.CARNATION.get());
		this.dropSelf(WWBlocks.MARIGOLD.get());
		this.dropSelf(WWBlocks.PASQUEFLOWER.get());
		this.dropSelf(WWBlocks.RED_HIBISCUS.get());
		this.dropSelf(WWBlocks.YELLOW_HIBISCUS.get());
		this.dropSelf(WWBlocks.WHITE_HIBISCUS.get());
		this.dropSelf(WWBlocks.PINK_HIBISCUS.get());
		this.dropSelf(WWBlocks.PURPLE_HIBISCUS.get());
		this.dropSelf(WWBlocks.FLOWERING_LILY_PAD.get());
		this.dropSelf(WWBlocks.PRICKLY_PEAR.get());
		this.add(WWBlocks.MILKWEED.get(), block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(WWBlocks.DATURA.get(), block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		this.add(WWBlocks.CATTAIL.get(), block -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

		this.add(WWBlocks.PHLOX.get(), this.createSegmentedBlockDrops(WWBlocks.PHLOX.get()));
		this.add(WWBlocks.LANTANAS.get(), this.createSegmentedBlockDrops(WWBlocks.LANTANAS.get()));
		WWBlockLootHelper.createShearsOrSilkTouchRequiredSegmentedBlockDrops(this, WWBlocks.CLOVERS.get());

		this.add(WWBlocks.FROZEN_LARGE_FERN.get(), block -> this.createDoublePlantWithSeedDrops(block, WWBlocks.FROZEN_FERN.get()));
		this.add(WWBlocks.FROZEN_TALL_GRASS.get(), block -> this.createDoublePlantWithSeedDrops(block, WWBlocks.FROZEN_SHORT_GRASS.get()));
		this.add(WWBlocks.FROZEN_FERN.get(), this::createGrassDrops);
		this.add(WWBlocks.FROZEN_SHORT_GRASS.get(), this::createGrassDrops);
		this.add(WWBlocks.FROZEN_BUSH.get(), this::createShearsOrSilkTouchOnlyDrop);

		this.add(WWFabricBlocks.TUMBLEWEED_PLANT.get(),
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWFabricBlocks.TUMBLEWEED_PLANT.get(),
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(ExplosionCondition.survivesExplosion())
							.add(LootItem.lootTableItem(WWFabricBlocks.TUMBLEWEED_PLANT.get()))
					)
				).withPool(
						LootPool.lootPool()
							.when(this.doesNotHaveShearsOrSilkTouch())
							.setRolls(ConstantValue.exactly(1F))
							.add(
								this.applyExplosionDecay(
									WWFabricBlocks.TUMBLEWEED_PLANT.get(),
									LootItem.lootTableItem(Items.STICK).apply(
										SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWFabricBlocks.TUMBLEWEED_PLANT.get())
													.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 2))
											)
									).apply(
										SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWFabricBlocks.TUMBLEWEED_PLANT.get())
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
							LootItem.lootTableItem(WWFabricBlocks.TUMBLEWEED.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
								.when(
									LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWFabricBlocks.TUMBLEWEED_PLANT.get())
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))
								)
						)
				)
		);

		this.add(WWBlocks.SPONGE_BUD.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
									WWBlocks.SPONGE_BUD.get(),
									LootItem.lootTableItem(WWBlocks.SPONGE_BUD.get()).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(1F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SPONGE_BUD.get())
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 0))
											)
									).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(2F))
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SPONGE_BUD.get())
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 1))
										)
									).apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(3F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SPONGE_BUD.get())
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 2))
											)
									)
								)
						)
				)
		);
		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.ALGAE.get());
		this.dropSelf(WWBlocks.PLANKTON.get());
		this.add(WWBlocks.BARNACLES.get(), block -> this.createMultifaceBlockDrops(block, ExplosionCondition.survivesExplosion()));
		this.dropSelf(WWBlocks.SEA_ANEMONE.get());
		this.dropSelf(WWBlocks.SEA_WHIP.get());
		this.dropSelf(WWBlocks.TUBE_WORMS.get());

		this.dropSelf(WWBlocks.AUBURN_MOSS_BLOCK.get());
		this.dropSelf(WWBlocks.AUBURN_MOSS_CARPET.get());
		this.add(WWBlocks.AUBURN_CREEPING_MOSS.get(), block -> this.createMultifaceBlockDrops(block, ExplosionCondition.survivesExplosion()));

		this.add(WWBlocks.BAOBAB_NUT.get(),
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.BAOBAB_NUT.get(),
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.BAOBAB_NUT.get())
									.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_2, 2))
							)
							.add(LootItem.lootTableItem(WWBlocks.BAOBAB_NUT.get()))
					)
				)
		);

		this.add(WWBlocks.COCONUT.get(),
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.COCONUT.get(),
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.COCONUT.get())
									.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.HANGING, false))
							)
							.add(LootItem.lootTableItem(WWBlocks.COCONUT.get()))
					)
				).withPool(
					this.applyExplosionDecay(
						WWBlocks.COCONUT.get(),
						LootPool.lootPool()
							.setRolls(UniformGenerator.between(1F, 4F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.COCONUT.get())
									.setProperties(
										StatePropertiesPredicate.Builder.properties()
											.hasProperty(BlockStateProperties.HANGING, true)
											.hasProperty(BlockStateProperties.AGE_2, 2)
									)
							)
							.add(LootItem.lootTableItem(WWBlocks.COCONUT.get()))
					)
				)
		);

		this.add(WWBlocks.SHRUB.get(),
			LootTable.lootTable()
				.withPool(
					this.applyExplosionCondition(
						WWBlocks.SHRUB.get(),
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SHRUB.get())
									.setProperties(
										StatePropertiesPredicate.Builder.properties()
											.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
									)
							)
							.add(LootItem.lootTableItem(WWBlocks.SHRUB.get()))
					)
				).withPool(
					this.applyExplosionDecay(
						WWBlocks.SHRUB.get(),
						LootPool.lootPool()
							.setRolls(UniformGenerator.between(0F, 1F))
							.when(
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SHRUB.get())
									.setProperties(
										StatePropertiesPredicate.Builder.properties()
											.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
											.hasProperty(BlockStateProperties.AGE_2, 2)
									)
							)
							.add(LootItem.lootTableItem(WWBlocks.SHRUB.get()))
					)
				)
		);

		this.dropPottedContents(WWBlocks.POTTED_SHORT_GRASS.get());
		this.dropPottedContents(WWBlocks.POTTED_SHRUB.get());
		this.dropPottedContents(WWBlocks.POTTED_BAOBAB_NUT.get());
		this.dropPottedContents(WWBlocks.POTTED_COCONUT.get());
		this.dropPottedContents(WWBlocks.POTTED_WILLOW_SAPLING.get());
		this.dropPottedContents(WWBlocks.POTTED_CYPRESS_SAPLING.get());
		this.dropPottedContents(WWBlocks.POTTED_YELLOW_MAPLE_SAPLING.get());
		this.dropPottedContents(WWBlocks.POTTED_ORANGE_MAPLE_SAPLING.get());
		this.dropPottedContents(WWBlocks.POTTED_RED_MAPLE_SAPLING.get());
		this.dropPottedContents(WWBlocks.POTTED_CARNATION.get());
		this.dropPottedContents(WWBlocks.POTTED_MARIGOLD.get());
		this.dropPottedContents(WWBlocks.POTTED_PASQUEFLOWER.get());
		this.dropPottedContents(WWBlocks.POTTED_RED_HIBISCUS.get());
		this.dropPottedContents(WWBlocks.POTTED_YELLOW_HIBISCUS.get());
		this.dropPottedContents(WWBlocks.POTTED_WHITE_HIBISCUS.get());
		this.dropPottedContents(WWBlocks.POTTED_PINK_HIBISCUS.get());
		this.dropPottedContents(WWBlocks.POTTED_PURPLE_HIBISCUS.get());
		this.dropPottedContents(WWFabricBlocks.POTTED_SEEDING_DANDELION.get());
		this.dropPottedContents(WWFabricBlocks.POTTED_TUMBLEWEED_PLANT.get());
		this.dropPottedContents(WWFabricBlocks.POTTED_TUMBLEWEED.get());
		this.dropPottedContents(WWBlocks.POTTED_PRICKLY_PEAR.get());
		this.dropPottedContents(WWBlocks.POTTED_BIG_DRIPLEAF.get());
		this.dropPottedContents(WWBlocks.POTTED_SMALL_DRIPLEAF.get());
		this.dropPottedContents(WWBlocks.POTTED_MYCELIUM_GROWTH.get());
		this.dropPottedContents(WWBlocks.POTTED_PINK_PETALS.get());
		this.dropPottedContents(WWBlocks.POTTED_BUSH.get());
		this.dropPottedContents(WWBlocks.POTTED_FIREFLY_BUSH.get());
		this.dropPottedContents(WWBlocks.POTTED_SHORT_DRY_GRASS.get());
		this.dropPottedContents(WWBlocks.POTTED_TALL_DRY_GRASS.get());
		this.dropPottedContents(WWBlocks.POTTED_CACTUS_FLOWER.get());
		this.dropPottedContents(WWBlocks.POTTED_WILDFLOWERS.get());
		this.dropPottedContents(WWBlocks.POTTED_PHLOX.get());
		this.dropPottedContents(WWBlocks.POTTED_LANTANAS.get());
		this.dropPottedContents(WWBlocks.POTTED_FROZEN_SHORT_GRASS.get());
		this.dropPottedContents(WWBlocks.POTTED_FROZEN_FERN.get());
		this.dropPottedContents(WWBlocks.POTTED_FROZEN_BUSH.get());

		this.dropSelf(WWBlocks.NULL_BLOCK.get());
		this.dropSelf(WWBlocks.CHISELED_MUD_BRICKS.get());
		this.dropSelf(WWBlocks.TERMITE_MOUND.get());
		this.dropSelf(WWBlocks.BLUE_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.BLUE_NEMATOCYST.get());
		this.dropSelf(WWBlocks.LIME_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.LIME_NEMATOCYST.get());
		this.dropSelf(WWBlocks.PINK_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.PINK_NEMATOCYST.get());
		this.dropSelf(WWBlocks.YELLOW_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.YELLOW_NEMATOCYST.get());
		this.dropSelf(WWBlocks.RED_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.RED_NEMATOCYST.get());
		this.dropSelf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.PEARLESCENT_BLUE_NEMATOCYST.get());
		this.dropSelf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get());
		this.dropWhenSilkTouch(WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST.get());

		WWBlockLootHelper.makeHangingFroglightLoot(this, WWBlocks.PEARLESCENT_FROGLIGHT_GOOP_BODY.get(), WWBlocks.PEARLESCENT_FROGLIGHT_GOOP.get());
		WWBlockLootHelper.makeHangingFroglightLoot(this, WWBlocks.VERDANT_FROGLIGHT_GOOP_BODY.get(), WWBlocks.VERDANT_FROGLIGHT_GOOP.get());
		WWBlockLootHelper.makeHangingFroglightLoot(this, WWBlocks.OCHRE_FROGLIGHT_GOOP_BODY.get(), WWBlocks.OCHRE_FROGLIGHT_GOOP.get());

		this.dropSelf(WWFabricBlocks.OSTRICH_EGG.get());
		this.dropSelf(WWFabricBlocks.PENGUIN_EGG.get());
		this.dropSelf(WWBlocks.GEOTHERMAL_VENT.get());
		this.dropWhenSilkTouch(WWBlocks.HANGING_TENDRIL.get());
		this.dropWhenSilkTouch(WWBlocks.OSSEOUS_SCULK.get());
		this.dropWhenSilkTouch(WWBlocks.SCULK_WALL.get());
		this.dropWhenSilkTouch(WWBlocks.SCULK_STAIRS.get());
		this.add(WWBlocks.SCULK_SLAB.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.when(this.hasSilkTouch())
						.setRolls(ConstantValue.exactly(1F))
						.add(
							this.applyExplosionDecay(
								WWBlocks.SCULK_SLAB.get(),
								LootItem.lootTableItem(WWBlocks.SCULK_SLAB.get())
									.apply(
										SetItemCountFunction.setCount(ConstantValue.exactly(2F))
											.when(
												LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.SCULK_SLAB.get())
													.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))
											)
									)
							)
						)
				)
		);
		this.dropSelf(WWBlocks.STONE_CHEST.get());

		this.add(
			WWBlocks.DISPLAY_LANTERN.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(WWBlocks.DISPLAY_LANTERN.get()).when(ExplosionCondition.survivesExplosion()))
				)
		);

		this.add(WWBlocks.ECHO_GLASS.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(WWBlocks.ECHO_GLASS.get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))
								.apply(CopyBlockState.copyState(WWBlocks.ECHO_GLASS.get()).copy(WWBlockStateProperties.DAMAGE)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(WWBlocks.ECHO_GLASS.get())
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WWBlockStateProperties.DAMAGE, 0))
											.invert()
									)
								)
						)
				)
		);

		this.dropSelf(WWBlocks.SCORCHED_SAND.get());
		this.dropSelf(WWBlocks.SCORCHED_RED_SAND.get());

		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.BROWN_SHELF_FUNGI.get(), Items.BROWN_MUSHROOM);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.RED_SHELF_FUNGI.get(), Items.RED_MUSHROOM);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.PALE_SHELF_FUNGI.get(), WWBlocks.PALE_MUSHROOM.get());
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.CRIMSON_SHELF_FUNGI.get(), Items.CRIMSON_FUNGUS);
		WWBlockLootHelper.makeShelfFungiLoot(this, WWBlocks.WARPED_SHELF_FUNGI.get(), Items.WARPED_FUNGUS);

		this.dropSelf(WWBlocks.PALE_MUSHROOM.get());
		this.dropPottedContents(WWBlocks.POTTED_PALE_MUSHROOM.get());
		this.add(WWFabricBlocks.PALE_MUSHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, WWBlocks.PALE_MUSHROOM.get()));

		this.dropSelf(WWBlocks.CHISELED_MUD_BRICKS.get());
		this.dropSelf(WWBlocks.CRACKED_MUD_BRICKS.get());
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICKS.get());
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICK_STAIRS.get());
		this.add(WWBlocks.MOSSY_MUD_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(WWBlocks.MOSSY_MUD_BRICK_WALL.get());

		WWBlockLootHelper.makeShearsOrSilkTouchRequiredLoot(this, WWBlocks.MYCELIUM_GROWTH.get());

		this.dropWhenSilkTouch(WWBlocks.FRAGILE_ICE.get());
		this.dropWhenSilkTouch(WWBlocks.ICICLE.get());
	}

}
