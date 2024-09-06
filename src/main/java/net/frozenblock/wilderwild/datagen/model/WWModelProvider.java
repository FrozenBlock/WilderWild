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

package net.frozenblock.wilderwild.datagen.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.wilderwild.datagen.WWModelHelper;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class WWModelProvider extends FabricModelProvider {

	public WWModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.family(WWBlocks.BAOBAB_PLANKS).generateFor(WWBlocks.BAOBAB);
		generator.woodProvider(WWBlocks.BAOBAB_LOG).logWithHorizontal(WWBlocks.BAOBAB_LOG).wood(WWBlocks.BAOBAB_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_BAOBAB_LOG).logWithHorizontal(WWBlocks.STRIPPED_BAOBAB_LOG).wood(WWBlocks.STRIPPED_BAOBAB_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_WALL_HANGING_SIGN);
		generator.createTrivialBlock(WWBlocks.BAOBAB_LEAVES, TexturedModel.LEAVES);

		generator.family(WWBlocks.CYPRESS_PLANKS).generateFor(WWBlocks.CYPRESS);
		generator.woodProvider(WWBlocks.CYPRESS_LOG).logWithHorizontal(WWBlocks.CYPRESS_LOG).wood(WWBlocks.CYPRESS_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_CYPRESS_LOG).logWithHorizontal(WWBlocks.STRIPPED_CYPRESS_LOG).wood(WWBlocks.STRIPPED_CYPRESS_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_WALL_HANGING_SIGN);
		generator.createPlant(WWBlocks.CYPRESS_SAPLING, WWBlocks.POTTED_CYPRESS_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
		generator.createTrivialBlock(WWBlocks.CYPRESS_LEAVES, TexturedModel.LEAVES);

		generator.family(WWBlocks.MAPLE_PLANKS).generateFor(WWBlocks.MAPLE);
		generator.woodProvider(WWBlocks.MAPLE_LOG).logWithHorizontal(WWBlocks.MAPLE_LOG).wood(WWBlocks.MAPLE_WOOD);
		generator.woodProvider(WWBlocks.STRIPPED_MAPLE_LOG).logWithHorizontal(WWBlocks.STRIPPED_MAPLE_LOG).wood(WWBlocks.STRIPPED_MAPLE_WOOD);
		generator.createHangingSign(WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_WALL_HANGING_SIGN);
		generator.createPlant(WWBlocks.MAPLE_SAPLING, WWBlocks.POTTED_MAPLE_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
		generator.createTrivialBlock(WWBlocks.YELLOW_MAPLE_LEAVES, TexturedModel.LEAVES);
		generator.createTrivialBlock(WWBlocks.ORANGE_MAPLE_LEAVES, TexturedModel.LEAVES);
		generator.createTrivialBlock(WWBlocks.RED_MAPLE_LEAVES, TexturedModel.LEAVES);

		generator.createPlant(WWBlocks.SEEDING_DANDELION, WWBlocks.POTTED_SEEDING_DANDELION, BlockModelGenerators.TintState.NOT_TINTED);
		generator.createPlant(WWBlocks.CARNATION, WWBlocks.POTTED_CARNATION, BlockModelGenerators.TintState.NOT_TINTED);
		generator.createPlant(WWBlocks.MARIGOLD, WWBlocks.POTTED_MARIGOLD, BlockModelGenerators.TintState.NOT_TINTED);

		generator.createTrivialBlock(WWBlocks.CHISELED_MUD_BRICKS, TexturedModel.CUBE);
		generator.createTrivialCube(WWBlocks.CRACKED_MUD_BRICKS);
		generator.family(WWBlocks.MOSSY_MUD_BRICKS).generateFor(WWBlocks.FAMILY_MOSSY_MUD_BRICK);

		generator.createSimpleFlatItemModel(WWBlocks.ALGAE);
		generator.createSimpleFlatItemModel(WWBlocks.POLLEN);
		generator.createSimpleFlatItemModel(WWBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW);
		generator.createSimpleFlatItemModel(WWBlocks.PINK_GIANT_GLORY_OF_THE_SNOW);
		generator.createSimpleFlatItemModel(WWBlocks.ALBA_GLORY_OF_THE_SNOW);
		generator.createSimpleFlatItemModel(WWBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW);

		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.OAK_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.SPRUCE_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.BIRCH_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.JUNGLE_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.ACACIA_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.DARK_OAK_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.MANGROVE_LEAF_LITTER);
		WWModelHelper.createLeafLitter(generator, WWBlocks.CHERRY_LEAF_LITTER);
		WWModelHelper.createLeafLitter(generator, WWBlocks.AZALEA_LEAF_LITTER);
		WWModelHelper.createLeafLitter(generator, WWBlocks.FLOWERING_AZALEA_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.BAOBAB_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.CYPRESS_LEAF_LITTER);
		WWModelHelper.createTintedLeafLitter(generator, WWBlocks.PALM_FROND_LITTER);
		WWModelHelper.createLeafLitter(generator, WWBlocks.YELLOW_MAPLE_LEAF_LITTER);
		WWModelHelper.createLeafLitter(generator, WWBlocks.ORANGE_MAPLE_LEAF_LITTER);
		WWModelHelper.createLeafLitter(generator, WWBlocks.RED_MAPLE_LEAF_LITTER);

		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_LOG);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.BAOBAB_LOG);
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
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_PALM_LOG);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG);

		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
		WWModelHelper.createHollowedLog(generator, WWBlocks.HOLLOWED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);

		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
		WWModelHelper.createStrippedHollowedLog(generator, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(WWBlocks.BUSH.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.DISPLAY_LANTERN.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.HANGING_TENDRIL.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.CATTAIL.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWBlocks.SPONGE_BUD.asItem(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.BAOBAB_NUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.COCONUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.SPLIT_COCONUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MILKWEED_POD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PRICKLY_PEAR, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PEELED_PRICKLY_PEAR, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.JELLYFISH_BUCKET, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_BUCKET, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CRAB_CLAW, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.COOKED_CRAB_CLAW, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.BAOBAB_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.BAOBAB_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CYPRESS_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.CYPRESS_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PALM_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.PALM_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MAPLE_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.MAPLE_CHEST_BOAT, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(WWItems.SCORCHED_EYE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(WWItems.FERMENTED_SCORCHED_EYE, ModelTemplates.FLAT_ITEM);
	}
}
