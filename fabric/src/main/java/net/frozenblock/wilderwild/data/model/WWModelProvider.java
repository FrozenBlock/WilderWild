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

package net.frozenblock.wilderwild.data.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.frozenblock.lib.data.api.client.FrozenLibModelHelper;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.registry.WWBlockFamilies;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;

@Environment(EnvType.CLIENT)
public final class WWModelProvider extends FabricModelProvider {

	public WWModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {
		generator.family(WWBlocks.BAOBAB_PLANKS.get()).generateFor(WWBlockFamilies.BAOBAB_PLANKS);
		generator.woodProvider(WWBlocks.BAOBAB_LOG.get()).logWithHorizontal(WWBlocks.BAOBAB_LOG.get()).wood(WWBlocks.BAOBAB_WOOD.get());
		generator.woodProvider(WWBlocks.STRIPPED_BAOBAB_LOG.get()).logWithHorizontal(WWBlocks.STRIPPED_BAOBAB_LOG.get()).wood(WWBlocks.STRIPPED_BAOBAB_WOOD.get());
		generator.createShelf(WWBlocks.BAOBAB_SHELF.get(), WWBlocks.STRIPPED_BAOBAB_LOG.get());
		generator.createTintedLeaves(WWBlocks.BAOBAB_LEAVES.get(), TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		generator.family(WWBlocks.WILLOW_PLANKS.get()).generateFor(WWBlockFamilies.WILLOW_PLANKS);
		generator.woodProvider(WWBlocks.WILLOW_LOG.get()).logWithHorizontal(WWBlocks.WILLOW_LOG.get()).wood(WWBlocks.WILLOW_WOOD.get());
		generator.woodProvider(WWBlocks.STRIPPED_WILLOW_LOG.get()).logWithHorizontal(WWBlocks.STRIPPED_WILLOW_LOG.get()).wood(WWBlocks.STRIPPED_WILLOW_WOOD.get());
		generator.createShelf(WWBlocks.WILLOW_SHELF.get(), WWBlocks.STRIPPED_WILLOW_LOG.get());
		generator.createPlantWithDefaultItem(WWBlocks.WILLOW_SAPLING.get(), WWBlocks.POTTED_WILLOW_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createTintedLeaves(WWBlocks.WILLOW_LEAVES.get(), TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		generator.family(WWBlocks.CYPRESS_PLANKS.get()).generateFor(WWBlockFamilies.CYPRESS_PLANKS);
		generator.woodProvider(WWBlocks.CYPRESS_LOG.get()).logWithHorizontal(WWBlocks.CYPRESS_LOG.get()).wood(WWBlocks.CYPRESS_WOOD.get());
		generator.woodProvider(WWBlocks.STRIPPED_CYPRESS_LOG.get()).logWithHorizontal(WWBlocks.STRIPPED_CYPRESS_LOG.get()).wood(WWBlocks.STRIPPED_CYPRESS_WOOD.get());
		generator.createShelf(WWBlocks.CYPRESS_SHELF.get(), WWBlocks.STRIPPED_CYPRESS_LOG.get());
		generator.createPlantWithDefaultItem(WWBlocks.CYPRESS_SAPLING.get(), WWBlocks.POTTED_CYPRESS_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createTintedLeaves(WWBlocks.CYPRESS_LEAVES.get(), TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		final BlockModelGenerators.BlockFamilyProvider palmFamily = generator.family(WWBlocks.PALM_PLANKS.get());
		palmFamily.skipGeneratingModelsFor.add(WWBlocks.PALM_DOOR.get());
		palmFamily.skipGeneratingModelsFor.add(WWBlocks.PALM_TRAPDOOR.get());
		palmFamily.generateFor(WWBlockFamilies.PALM_PLANKS);
		generator.woodProvider(WWBlocks.PALM_LOG.get()).logWithHorizontal(WWBlocks.PALM_LOG.get()).wood(WWBlocks.PALM_WOOD.get());
		generator.woodProvider(WWBlocks.STRIPPED_PALM_LOG.get()).logWithHorizontal(WWBlocks.STRIPPED_PALM_LOG.get()).wood(WWBlocks.STRIPPED_PALM_WOOD.get());
		generator.createShelf(WWBlocks.PALM_SHELF.get(), WWBlocks.STRIPPED_PALM_LOG.get());
		generator.createTintedLeaves(WWBlocks.PALM_FRONDS.get(), TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		final BlockModelGenerators.BlockFamilyProvider mapleFamily = generator.family(WWBlocks.MAPLE_PLANKS.get());
		mapleFamily.skipGeneratingModelsFor.add(WWBlocks.MAPLE_TRAPDOOR.get());
		mapleFamily.generateFor(WWBlockFamilies.MAPLE_PLANKS);
		generator.woodProvider(WWBlocks.MAPLE_LOG.get()).logWithHorizontal(WWBlocks.MAPLE_LOG.get()).wood(WWBlocks.MAPLE_WOOD.get());
		generator.woodProvider(WWBlocks.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(WWBlocks.STRIPPED_MAPLE_LOG.get()).wood(WWBlocks.STRIPPED_MAPLE_WOOD.get());
		generator.createShelf(WWBlocks.MAPLE_SHELF.get(), WWBlocks.STRIPPED_MAPLE_LOG.get());
		MapleCollection.zipApply(WWBlocks.MAPLE_SAPLING, WWBlocks.POTTED_MAPLE_SAPLING, (sapling, potted) -> {
			generator.createPlantWithDefaultItem(sapling.get(), potted.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		});
		WWBlocks.MAPLE_LEAVES.forEach(block -> generator.createTrivialBlock(block.get(), TexturedModel.LEAVES));

		generator.createDoublePlantWithDefaultItem(WWBlocks.DATURA.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		generator.createPlantWithDefaultItem(WWBlocks.SEEDING_DANDELION.get(), WWBlocks.POTTED_SEEDING_DANDELION.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.CARNATION.get(), WWBlocks.POTTED_CARNATION.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.MARIGOLD.get(), WWBlocks.POTTED_MARIGOLD.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.PASQUEFLOWER.get(), WWBlocks.POTTED_PASQUEFLOWER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.MYCELIUM_GROWTH.get(), WWBlocks.POTTED_MYCELIUM_GROWTH.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		WWModelHelper.createHibiscus(generator, WWBlocks.RED_HIBISCUS.get(), WWBlocks.POTTED_RED_HIBISCUS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.YELLOW_HIBISCUS.get(), WWBlocks.POTTED_YELLOW_HIBISCUS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.WHITE_HIBISCUS.get(), WWBlocks.POTTED_WHITE_HIBISCUS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.PINK_HIBISCUS.get(), WWBlocks.POTTED_PINK_HIBISCUS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.PURPLE_HIBISCUS.get(), WWBlocks.POTTED_PURPLE_HIBISCUS.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		WWModelHelper.createShrub(generator);

		generator.createFlowerBed(WWBlocks.PHLOX.get());
		generator.createFlowerBed(WWBlocks.LANTANAS.get());

		FrozenLibModelHelper.createTintedFlowerBed(generator, WWBlocks.CLOVERS.get());

		generator.createCrossBlockWithDefaultItem(WWBlocks.FROZEN_SHORT_GRASS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createDoublePlantWithDefaultItem(WWBlocks.FROZEN_TALL_GRASS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.FROZEN_FERN.get(), WWBlocks.POTTED_FROZEN_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createDoublePlantWithDefaultItem(WWBlocks.FROZEN_LARGE_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createCrossBlockWithDefaultItem(WWBlocks.FROZEN_BUSH.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		generator.createTrivialCube(WWBlocks.SCORCHED_SAND.get());
		generator.createTrivialCube(WWBlocks.SCORCHED_RED_SAND.get());

		generator.createTrivialCube(WWBlocks.CHISELED_MUD_BRICKS.get());
		generator.createTrivialCube(WWBlocks.CRACKED_MUD_BRICKS.get());
		generator.family(WWBlocks.MOSSY_MUD_BRICKS.get()).generateFor(WWBlockFamilies.MOSSY_MUD_BRICK);

		WWModelHelper.createAlgae(generator);
		WWModelHelper.createPlankton(generator);
		WWModelHelper.createSeaAnemone(generator, WWBlocks.SEA_ANEMONE.get());
		WWModelHelper.createSeaWhip(generator);
		WWModelHelper.createMultifaceBlock(generator, WWBlocks.BARNACLES.get());
		WWModelHelper.createTubeWorms(generator);
		WWModelHelper.createCattail(generator);
		WWModelHelper.createMultifaceBlock(generator, WWBlocks.POLLEN.get());

		generator.createFullAndCarpetBlocks(WWBlocks.AUBURN_MOSS_BLOCK.get(), WWBlocks.AUBURN_MOSS_CARPET.get());
		WWModelHelper.createMultifaceBlock(generator, WWBlocks.AUBURN_CREEPING_MOSS.get());

		WWModelHelper.generatePaleMushroomBlock(generator);
		generator.createPlantWithDefaultItem(WWBlocks.PALE_MUSHROOM.get(), WWBlocks.POTTED_PALE_MUSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		WWModelHelper.createShelfFungi(generator, WWBlocks.BROWN_SHELF_FUNGI.get());
		WWModelHelper.createShelfFungi(generator, WWBlocks.RED_SHELF_FUNGI.get());
		WWModelHelper.createShelfFungi(generator, WWBlocks.PALE_SHELF_FUNGI.get());
		WWModelHelper.createShelfFungi(generator, WWBlocks.CRIMSON_SHELF_FUNGI.get());
		WWModelHelper.createShelfFungi(generator, WWBlocks.WARPED_SHELF_FUNGI.get());

		generator.createLeafLitter(WWBlocks.ACACIA_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.AZALEA_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.BAOBAB_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.BIRCH_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.CHERRY_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.CYPRESS_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.DARK_OAK_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.JUNGLE_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.MANGROVE_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.PALE_OAK_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.PALM_FROND_LITTER.get());
		generator.createLeafLitter(WWBlocks.SPRUCE_LEAF_LITTER.get());
		generator.createLeafLitter(WWBlocks.WILLOW_LEAF_LITTER.get());
		WWBlocks.MAPLE_LEAF_LITTER.forEach(leafLitter -> WWModelHelper.createUntintedLeafLitter(generator, leafLitter.get()));

		WWModelHelper.createMesoglea(generator, WWBlocks.BLUE_MESOGLEA.get());
		WWModelHelper.createMesoglea(generator, WWBlocks.LIME_MESOGLEA.get());
		WWModelHelper.createMesoglea(generator, WWBlocks.PINK_MESOGLEA.get());
		WWModelHelper.createMesoglea(generator, WWBlocks.RED_MESOGLEA.get());
		WWModelHelper.createMesoglea(generator, WWBlocks.YELLOW_MESOGLEA.get());
		WWModelHelper.createMesoglea(generator, WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get());
		WWModelHelper.createMesoglea(generator, WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get());

		WWModelHelper.createNematocyst(generator, WWBlocks.BLUE_NEMATOCYST.get());
		WWModelHelper.createNematocyst(generator, WWBlocks.LIME_NEMATOCYST.get());
		WWModelHelper.createNematocyst(generator, WWBlocks.PINK_NEMATOCYST.get());
		WWModelHelper.createNematocyst(generator, WWBlocks.RED_NEMATOCYST.get());
		WWModelHelper.createNematocyst(generator, WWBlocks.YELLOW_NEMATOCYST.get());
		WWModelHelper.createNematocyst(generator, WWBlocks.PEARLESCENT_BLUE_NEMATOCYST.get());
		WWModelHelper.createNematocyst(generator, WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST.get());

		WWModelHelper.createFroglightGoop(generator, WWBlocks.PEARLESCENT_FROGLIGHT_GOOP_BODY.get(), WWBlocks.PEARLESCENT_FROGLIGHT_GOOP.get());
		WWModelHelper.createFroglightGoop(generator, WWBlocks.VERDANT_FROGLIGHT_GOOP_BODY.get(), WWBlocks.VERDANT_FROGLIGHT_GOOP.get());
		WWModelHelper.createFroglightGoop(generator, WWBlocks.OCHRE_FROGLIGHT_GOOP_BODY.get(), WWBlocks.OCHRE_FROGLIGHT_GOOP.get());

		WWModelHelper.createFragileIce(generator);
		WWModelHelper.createIcicle(generator);

		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_OAK_LOG.get(), WWBlocks.HOLLOWED_OAK_LOG.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_SPRUCE_LOG.get(), WWBlocks.HOLLOWED_SPRUCE_LOG.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_BIRCH_LOG.get(), WWBlocks.HOLLOWED_BIRCH_LOG.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_JUNGLE_LOG.get(), WWBlocks.HOLLOWED_JUNGLE_LOG.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_ACACIA_LOG.get(), WWBlocks.HOLLOWED_ACACIA_LOG.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_DARK_OAK_LOG.get(), WWBlocks.HOLLOWED_DARK_OAK_LOG.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_MANGROVE_LOG.get(), WWBlocks.HOLLOWED_MANGROVE_LOG.get(), Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CHERRY_LOG.get(), WWBlocks.HOLLOWED_CHERRY_LOG.get(), Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_PALE_OAK_LOG.get(), WWBlocks.HOLLOWED_PALE_OAK_LOG.get(), Blocks.STRIPPED_PALE_OAK_LOG, Blocks.PALE_OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_BAOBAB_LOG.get(), WWBlocks.HOLLOWED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_BAOBAB_LOG.get(), WWBlocks.BAOBAB_LOG.get());
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_WILLOW_LOG.get(), WWBlocks.HOLLOWED_WILLOW_LOG.get(), WWBlocks.STRIPPED_WILLOW_LOG.get(), WWBlocks.WILLOW_LOG.get());
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CYPRESS_LOG.get(), WWBlocks.HOLLOWED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_CYPRESS_LOG.get(), WWBlocks.CYPRESS_LOG.get());
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_PALM_LOG.get(), WWBlocks.HOLLOWED_PALM_LOG.get(), WWBlocks.STRIPPED_PALM_LOG.get(), WWBlocks.PALM_LOG.get());
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_MAPLE_LOG.get(), WWBlocks.HOLLOWED_MAPLE_LOG.get(), WWBlocks.STRIPPED_MAPLE_LOG.get(), WWBlocks.MAPLE_LOG.get());

		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get(), Blocks.STRIPPED_OAK_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get(), Blocks.STRIPPED_SPRUCE_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get(), Blocks.STRIPPED_BIRCH_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get(), Blocks.STRIPPED_JUNGLE_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get(), Blocks.STRIPPED_ACACIA_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get(), Blocks.STRIPPED_DARK_OAK_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get(), Blocks.STRIPPED_MANGROVE_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get(), Blocks.STRIPPED_CHERRY_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get(), Blocks.STRIPPED_PALE_OAK_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_BAOBAB_LOG.get());
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get(), WWBlocks.STRIPPED_WILLOW_LOG.get());
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_CYPRESS_LOG.get());
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get(), WWBlocks.STRIPPED_PALM_LOG.get());
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get(), WWBlocks.STRIPPED_MAPLE_LOG.get());

		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CRIMSON_STEM.get(), WWBlocks.HOLLOWED_CRIMSON_STEM.get(), Blocks.STRIPPED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM.get());
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_WARPED_STEM.get(), WWBlocks.HOLLOWED_WARPED_STEM.get(), Blocks.STRIPPED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM.get());

		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get(), WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get(), Blocks.STRIPPED_CRIMSON_STEM);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get(), WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get(), Blocks.STRIPPED_WARPED_STEM);

		WWModelHelper.createStoneChest(generator, WWBlocks.STONE_CHEST.get(), Blocks.DEEPSLATE, StoneChestSpecialRenderer.STONE.single());

		generator.family(WWBlocks.GABBRO.get()).generateFor(WWBlockFamilies.GABBRO);
		generator.family(WWBlocks.POLISHED_GABBRO.get()).generateFor(WWBlockFamilies.POLISHED_GABBRO);
		generator.family(WWBlocks.GABBRO_BRICKS.get()).generateFor(WWBlockFamilies.GABBRO_BRICK);
		generator.family(WWBlocks.MOSSY_GABBRO_BRICKS.get()).generateFor(WWBlockFamilies.MOSSY_GABBRO_BRICK);
		generator.createRotatableColumn(WWBlocks.GEOTHERMAL_VENT.get());

		generator.registerSimpleItemModel(WWBlocks.OSSEOUS_SCULK.get(), ModelLocationUtils.getModelLocation(WWBlocks.OSSEOUS_SCULK.get()));
		generator.registerSimpleItemModel(WWBlocks.SCULK_STAIRS.get(), ModelLocationUtils.getModelLocation(WWBlocks.SCULK_STAIRS.get()));
		generator.registerSimpleItemModel(WWBlocks.SCULK_SLAB.get(), ModelLocationUtils.getModelLocation(WWBlocks.SCULK_SLAB.get()));
		generator.registerSimpleItemModel(WWBlocks.SCULK_WALL.get(), ModelLocationUtils.getModelLocation(WWBlocks.SCULK_WALL.get()));
		generator.registerSimpleItemModel(WWBlocks.TERMITE_MOUND.get(), ModelLocationUtils.getModelLocation(WWBlocks.TERMITE_MOUND.get()));
		generator.registerSimpleItemModel(WWBlocks.TUMBLEWEED.get(), ModelLocationUtils.getModelLocation(WWBlocks.TUMBLEWEED.get()));
	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
		generator.generateFlatItem(WWBlocks.DISPLAY_LANTERN.get().asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.HANGING_TENDRIL.get().asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.SPONGE_BUD.get().asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.ICICLE.get().asItem(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.BAOBAB_NUT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.COCONUT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SPLIT_COCONUT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PRICKLY_PEAR.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PEELED_PRICKLY_PEAR.get(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.JELLYFISH_BUCKET.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_BUCKET.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_CLAW.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.COOKED_CRAB_CLAW.get(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.BAOBAB_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.BAOBAB_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.WILLOW_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.WILLOW_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CYPRESS_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CYPRESS_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PALM_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PALM_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MAPLE_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWBlocks.FLOWERING_LILY_PAD.get().asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.OSTRICH_EGG.get().asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.PENGUIN_EGG.get().asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SCORCHED_EYE.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.FERMENTED_SCORCHED_EYE.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.TUMBLEWEED_PLANT.get().asItem(), ModelTemplates.FLAT_ITEM);

		generator.declareCustomModelItem(WWBlocks.MAPLE_TRAPDOOR.get().asItem());
		generator.declareCustomModelItem(WWBlocks.PALM_DOOR.get().asItem());
		generator.declareCustomModelItem(WWBlocks.PALM_TRAPDOOR.get().asItem());
		generator.declareCustomModelItem(WWBlocks.MILKWEED.get().asItem());
		WWModelHelper.generateMilkweedPod(generator, WWItems.MILKWEED_POD.get());
		generator.declareCustomModelItem(WWBlocks.NULL_BLOCK.get().asItem());

		WWModelHelper.generateEchoGlass(generator, WWItems.ECHO_GLASS.get());

		// Spawn Eggs
		generator.generateFlatItem(WWItems.FIREFLY_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.JELLYFISH_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.OSTRICH_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.ZOMBIE_OSTRICH_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SCORCHED_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.BUTTERFLY_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MOOBLOOM_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PENGUIN_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

		// Firefly Bottles
		WWModelHelper.generateFireflyBottles(generator);
		generator.generateFlatItem(WWItems.BUTTERFLY_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
	}
}
