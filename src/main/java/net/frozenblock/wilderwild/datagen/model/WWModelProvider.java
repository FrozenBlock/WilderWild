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

package net.frozenblock.wilderwild.datagen.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.lib.datagen.api.client.FrozenLibModelHelper;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWModelProvider extends FabricModelProvider {

	public WWModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.family(WWBlocks.BAOBAB_PLANKS).generateFor(WWBlocks.FAMILY_BAOBAB);
		generator.woodProvider(WWBlocks.BAOBAB_LOG).logWithHorizontal(WWBlocks.BAOBAB_LOG).wood(WWBlocks.BAOBAB_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_BAOBAB_LOG).logWithHorizontal(WWBlocks.STRIPPED_BAOBAB_LOG).wood(WWBlocks.STRIPPED_BAOBAB_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_WALL_HANGING_SIGN);
		generator.createShelf(WWBlocks.BAOBAB_SHELF, WWBlocks.STRIPPED_BAOBAB_LOG);
		generator.createTintedLeaves(WWBlocks.BAOBAB_LEAVES, TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		generator.family(WWBlocks.WILLOW_PLANKS).generateFor(WWBlocks.FAMILY_WILLOW);
		generator.woodProvider(WWBlocks.WILLOW_LOG).logWithHorizontal(WWBlocks.WILLOW_LOG).wood(WWBlocks.WILLOW_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_WILLOW_LOG).logWithHorizontal(WWBlocks.STRIPPED_WILLOW_LOG).wood(WWBlocks.STRIPPED_WILLOW_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_WALL_HANGING_SIGN);
		generator.createShelf(WWBlocks.WILLOW_SHELF, WWBlocks.STRIPPED_WILLOW_LOG);
		generator.createPlantWithDefaultItem(WWBlocks.WILLOW_SAPLING, WWBlocks.POTTED_WILLOW_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createTintedLeaves(WWBlocks.WILLOW_LEAVES, TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		generator.family(WWBlocks.CYPRESS_PLANKS).generateFor(WWBlocks.FAMILY_CYPRESS);
		generator.woodProvider(WWBlocks.CYPRESS_LOG).logWithHorizontal(WWBlocks.CYPRESS_LOG).wood(WWBlocks.CYPRESS_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_CYPRESS_LOG).logWithHorizontal(WWBlocks.STRIPPED_CYPRESS_LOG).wood(WWBlocks.STRIPPED_CYPRESS_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_WALL_HANGING_SIGN);
		generator.createShelf(WWBlocks.CYPRESS_SHELF, WWBlocks.STRIPPED_CYPRESS_LOG);
		generator.createPlantWithDefaultItem(WWBlocks.CYPRESS_SAPLING, WWBlocks.POTTED_CYPRESS_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createTintedLeaves(WWBlocks.CYPRESS_LEAVES, TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		BlockModelGenerators.BlockFamilyProvider palmFamily = generator.family(WWBlocks.PALM_PLANKS);
		palmFamily.skipGeneratingModelsFor.add(WWBlocks.PALM_DOOR);
		palmFamily.skipGeneratingModelsFor.add(WWBlocks.PALM_TRAPDOOR);
		palmFamily.generateFor(WWBlocks.FAMILY_PALM);
		generator.woodProvider(WWBlocks.PALM_LOG).logWithHorizontal(WWBlocks.PALM_LOG).wood(WWBlocks.PALM_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_PALM_LOG).logWithHorizontal(WWBlocks.STRIPPED_PALM_LOG).wood(WWBlocks.STRIPPED_PALM_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_PALM_LOG, WWBlocks.PALM_HANGING_SIGN, WWBlocks.PALM_WALL_HANGING_SIGN);
		generator.createShelf(WWBlocks.PALM_SHELF, WWBlocks.STRIPPED_PALM_LOG);
		generator.createTintedLeaves(WWBlocks.PALM_FRONDS, TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);

		BlockModelGenerators.BlockFamilyProvider mapleFamily = generator.family(WWBlocks.MAPLE_PLANKS);
		mapleFamily.skipGeneratingModelsFor.add(WWBlocks.MAPLE_TRAPDOOR);
		mapleFamily.generateFor(WWBlocks.FAMILY_MAPLE);
		generator.woodProvider(WWBlocks.MAPLE_LOG).logWithHorizontal(WWBlocks.MAPLE_LOG).wood(WWBlocks.MAPLE_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_MAPLE_LOG).logWithHorizontal(WWBlocks.STRIPPED_MAPLE_LOG).wood(WWBlocks.STRIPPED_MAPLE_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_WALL_HANGING_SIGN);
		generator.createShelf(WWBlocks.MAPLE_SHELF, WWBlocks.STRIPPED_MAPLE_LOG);
		generator.createPlantWithDefaultItem(WWBlocks.MAPLE_SAPLING, WWBlocks.POTTED_MAPLE_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createTrivialBlock(WWBlocks.YELLOW_MAPLE_LEAVES, TexturedModel.LEAVES);
		generator.createTrivialBlock(WWBlocks.ORANGE_MAPLE_LEAVES, TexturedModel.LEAVES);
		generator.createTrivialBlock(WWBlocks.RED_MAPLE_LEAVES, TexturedModel.LEAVES);

		generator.createDoublePlantWithDefaultItem(WWBlocks.DATURA, BlockModelGenerators.PlantType.NOT_TINTED);

		generator.createPlantWithDefaultItem(WWBlocks.SEEDING_DANDELION, WWBlocks.POTTED_SEEDING_DANDELION, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.CARNATION, WWBlocks.POTTED_CARNATION, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.MARIGOLD, WWBlocks.POTTED_MARIGOLD, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.PASQUEFLOWER, WWBlocks.POTTED_PASQUEFLOWER, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.MYCELIUM_GROWTH, WWBlocks.POTTED_MYCELIUM_GROWTH, BlockModelGenerators.PlantType.NOT_TINTED);

		WWModelHelper.createHibiscus(generator, WWBlocks.RED_HIBISCUS, WWBlocks.POTTED_RED_HIBISCUS, BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.YELLOW_HIBISCUS, WWBlocks.POTTED_YELLOW_HIBISCUS, BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.WHITE_HIBISCUS, WWBlocks.POTTED_WHITE_HIBISCUS, BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.PINK_HIBISCUS, WWBlocks.POTTED_PINK_HIBISCUS, BlockModelGenerators.PlantType.NOT_TINTED);
		WWModelHelper.createHibiscus(generator, WWBlocks.PURPLE_HIBISCUS, WWBlocks.POTTED_PURPLE_HIBISCUS, BlockModelGenerators.PlantType.NOT_TINTED);

		generator.createFlowerBed(WWBlocks.PHLOX);
		generator.createFlowerBed(WWBlocks.LANTANAS);

		FrozenLibModelHelper.createTintedFlowerBed(generator, WWBlocks.CLOVERS);

		generator.createCrossBlockWithDefaultItem(WWBlocks.FROZEN_SHORT_GRASS, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createDoublePlantWithDefaultItem(WWBlocks.FROZEN_TALL_GRASS, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createPlantWithDefaultItem(WWBlocks.FROZEN_FERN, WWBlocks.POTTED_FROZEN_FERN, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createDoublePlantWithDefaultItem(WWBlocks.FROZEN_LARGE_FERN, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createCrossBlockWithDefaultItem(WWBlocks.FROZEN_BUSH, BlockModelGenerators.PlantType.NOT_TINTED);

		generator.createTrivialCube(WWBlocks.SCORCHED_SAND);
		generator.createTrivialCube(WWBlocks.SCORCHED_RED_SAND);

		generator.createTrivialCube(WWBlocks.CHISELED_MUD_BRICKS);
		generator.createTrivialCube(WWBlocks.CRACKED_MUD_BRICKS);
		generator.family(WWBlocks.MOSSY_MUD_BRICKS).generateFor(WWBlocks.FAMILY_MOSSY_MUD_BRICK);

		WWModelHelper.createAlgae(generator);
		WWModelHelper.createPlankton(generator);
		WWModelHelper.createSeaAnemone(generator, WWBlocks.SEA_ANEMONE);
		WWModelHelper.createSeaWhip(generator);
		WWModelHelper.createMultifaceBlock(generator, WWBlocks.BARNACLES);
		WWModelHelper.createTubeWorms(generator);
		WWModelHelper.createCattail(generator);
		WWModelHelper.createMultifaceBlock(generator, WWBlocks.POLLEN);

		generator.createFullAndCarpetBlocks(WWBlocks.AUBURN_MOSS_BLOCK, WWBlocks.AUBURN_MOSS_CARPET);
		WWModelHelper.createMultifaceBlock(generator, WWBlocks.AUBURN_CREEPING_MOSS);

		WWModelHelper.generatePaleMushroomBlock(generator);
		generator.createPlantWithDefaultItem(WWBlocks.PALE_MUSHROOM, WWBlocks.POTTED_PALE_MUSHROOM, BlockModelGenerators.PlantType.NOT_TINTED);

		WWModelHelper.createShelfFungi(generator, WWBlocks.BROWN_SHELF_FUNGI);
		WWModelHelper.createShelfFungi(generator, WWBlocks.RED_SHELF_FUNGI);
		WWModelHelper.createShelfFungi(generator, WWBlocks.PALE_SHELF_FUNGI);
		WWModelHelper.createShelfFungi(generator, WWBlocks.CRIMSON_SHELF_FUNGI);
		WWModelHelper.createShelfFungi(generator, WWBlocks.WARPED_SHELF_FUNGI);

		generator.createLeafLitter(WWBlocks.ACACIA_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.AZALEA_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.BAOBAB_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.BIRCH_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.CHERRY_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.CYPRESS_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.DARK_OAK_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.JUNGLE_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.MANGROVE_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.PALE_OAK_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.PALM_FROND_LITTER);
		generator.createLeafLitter(WWBlocks.SPRUCE_LEAF_LITTER);
		generator.createLeafLitter(WWBlocks.WILLOW_LEAF_LITTER);
		WWModelHelper.createUntintedLeafLitter(generator, WWBlocks.YELLOW_MAPLE_LEAF_LITTER);
		WWModelHelper.createUntintedLeafLitter(generator, WWBlocks.ORANGE_MAPLE_LEAF_LITTER);
		WWModelHelper.createUntintedLeafLitter(generator, WWBlocks.RED_MAPLE_LEAF_LITTER);

		WWModelHelper.createMesoglea(generator, WWBlocks.BLUE_MESOGLEA);
		WWModelHelper.createMesoglea(generator, WWBlocks.LIME_MESOGLEA);
		WWModelHelper.createMesoglea(generator, WWBlocks.PINK_MESOGLEA);
		WWModelHelper.createMesoglea(generator, WWBlocks.RED_MESOGLEA);
		WWModelHelper.createMesoglea(generator, WWBlocks.YELLOW_MESOGLEA);
		WWModelHelper.createMesoglea(generator, WWBlocks.BLUE_PEARLESCENT_MESOGLEA);
		WWModelHelper.createMesoglea(generator, WWBlocks.PURPLE_PEARLESCENT_MESOGLEA);

		WWModelHelper.createNematocyst(generator, WWBlocks.BLUE_NEMATOCYST);
		WWModelHelper.createNematocyst(generator, WWBlocks.LIME_NEMATOCYST);
		WWModelHelper.createNematocyst(generator, WWBlocks.PINK_NEMATOCYST);
		WWModelHelper.createNematocyst(generator, WWBlocks.RED_NEMATOCYST);
		WWModelHelper.createNematocyst(generator, WWBlocks.YELLOW_NEMATOCYST);
		WWModelHelper.createNematocyst(generator, WWBlocks.BLUE_PEARLESCENT_NEMATOCYST);
		WWModelHelper.createNematocyst(generator, WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST);

		WWModelHelper.createFragileIce(generator);
		WWModelHelper.createIcicle(generator);

		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG, Blocks.PALE_OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.BAOBAB_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.WILLOW_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.CYPRESS_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_PALM_LOG, WWBlocks.PALM_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.MAPLE_LOG);

		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, Blocks.STRIPPED_OAK_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_PALM_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG);

		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);

		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);

		WWModelHelper.createStoneChest(generator, WWBlocks.STONE_CHEST, Blocks.DEEPSLATE, StoneChestSpecialRenderer.STONE_CHEST_TEXTURE);

		generator.family(WWBlocks.GABBRO).generateFor(WWBlocks.FAMILY_GABBRO);
		generator.family(WWBlocks.POLISHED_GABBRO).generateFor(WWBlocks.FAMILY_POLISHED_GABBRO);
		generator.family(WWBlocks.GABBRO_BRICKS).generateFor(WWBlocks.FAMILY_GABBRO_BRICK);
		generator.family(WWBlocks.MOSSY_GABBRO_BRICKS).generateFor(WWBlocks.FAMILY_MOSSY_GABBRO_BRICK);
		WWModelHelper.createGeyser(generator);

		generator.registerSimpleItemModel(WWBlocks.OSSEOUS_SCULK, ModelLocationUtils.getModelLocation(WWBlocks.OSSEOUS_SCULK));
		generator.registerSimpleItemModel(WWBlocks.SCULK_STAIRS, ModelLocationUtils.getModelLocation(WWBlocks.SCULK_STAIRS));
		generator.registerSimpleItemModel(WWBlocks.SCULK_SLAB, ModelLocationUtils.getModelLocation(WWBlocks.SCULK_SLAB));
		generator.registerSimpleItemModel(WWBlocks.SCULK_WALL, ModelLocationUtils.getModelLocation(WWBlocks.SCULK_WALL));
		generator.registerSimpleItemModel(WWBlocks.TERMITE_MOUND, ModelLocationUtils.getModelLocation(WWBlocks.TERMITE_MOUND));
		generator.registerSimpleItemModel(WWBlocks.TUMBLEWEED, ModelLocationUtils.getModelLocation(WWBlocks.TUMBLEWEED));
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(WWBlocks.SHRUB.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.DISPLAY_LANTERN.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.HANGING_TENDRIL.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.SPONGE_BUD.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.ICICLE.asItem(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.BAOBAB_NUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.COCONUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SPLIT_COCONUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PRICKLY_PEAR, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PEELED_PRICKLY_PEAR, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.JELLYFISH_BUCKET, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_BUCKET, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_CLAW, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.COOKED_CRAB_CLAW, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.BAOBAB_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.BAOBAB_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.WILLOW_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.WILLOW_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CYPRESS_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CYPRESS_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PALM_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PALM_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MAPLE_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MAPLE_CHEST_BOAT, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWBlocks.FLOWERING_LILY_PAD.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.OSTRICH_EGG.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.PENGUIN_EGG.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SCORCHED_EYE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.FERMENTED_SCORCHED_EYE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.TUMBLEWEED_PLANT.asItem(), ModelTemplates.FLAT_ITEM);

		generator.declareCustomModelItem(WWBlocks.MAPLE_TRAPDOOR.asItem());
		generator.declareCustomModelItem(WWBlocks.PALM_DOOR.asItem());
		generator.declareCustomModelItem(WWBlocks.PALM_TRAPDOOR.asItem());
		generator.declareCustomModelItem(WWBlocks.MILKWEED.asItem());
		WWModelHelper.generateMilkweedPod(generator, WWItems.MILKWEED_POD);
		generator.declareCustomModelItem(WWBlocks.NULL_BLOCK.asItem());

		WWModelHelper.generateCopperHorn(generator, WWItems.COPPER_HORN);
		WWModelHelper.generateEchoGlass(generator, WWItems.ECHO_GLASS);

		// Spawn Eggs
		generator.generateFlatItem(WWItems.FIREFLY_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.JELLYFISH_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.OSTRICH_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SCORCHED_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.BUTTERFLY_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MOOBLOOM_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PENGUIN_SPAWN_EGG, ModelTemplates.FLAT_ITEM);

		// Firefly Bottles
		WWModelHelper.generateFireflyBottles(generator);
		generator.generateFlatItem(WWItems.BUTTERFLY_BOTTLE, ModelTemplates.FLAT_ITEM);
	}
}
