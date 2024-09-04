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

import java.util.Optional;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
		generator.createTrivialBlock(WWBlocks.MAPLE_LEAVES, TexturedModel.LEAVES);

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

		createLeafLitter(generator, WWBlocks.MAPLE_LEAF_LITTER);
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

	private static final ModelTemplate LEAF_LITTER_MODEL = new ModelTemplate(Optional.of(WilderConstants.id("block/template_leaf_litter")), Optional.empty(), TextureSlot.TEXTURE);
	private static final TexturedModel.Provider LEAF_LITTER_PROVIDER = TexturedModel.createDefault(TextureMapping::defaultTexture, LEAF_LITTER_MODEL);

	public static void createLeafLitter(@NotNull BlockModelGenerators generator, Block carpet) {
		createLeafLitter(generator, carpet, carpet);
	}

	public static void createLeafLitter(@NotNull BlockModelGenerators generator, Block carpet, Block source) {
		ResourceLocation resourceLocation = LEAF_LITTER_PROVIDER.get(source).create(carpet, generator.modelOutput);
		ModelTemplates.FLAT_ITEM
			.create(ModelLocationUtils.getModelLocation(carpet.asItem()), TextureMapping.layer0(TextureMapping.getBlockTexture(source)), generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(carpet, resourceLocation));
	}
}
