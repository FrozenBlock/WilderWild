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
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import org.jetbrains.annotations.NotNull;

public final class WWModelProvider extends FabricModelProvider {

	public WWModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.family(RegisterBlocks.BAOBAB_PLANKS).generateFor(RegisterBlocks.BAOBAB);
		generator.woodProvider(RegisterBlocks.BAOBAB_LOG).logWithHorizontal(RegisterBlocks.BAOBAB_LOG).wood(RegisterBlocks.BAOBAB_WOOD);
		generator.woodProvider(RegisterBlocks.STRIPPED_BAOBAB_LOG).logWithHorizontal(RegisterBlocks.STRIPPED_BAOBAB_LOG).wood(RegisterBlocks.STRIPPED_BAOBAB_WOOD);
		generator.createHangingSign(RegisterBlocks.STRIPPED_BAOBAB_LOG, RegisterBlocks.BAOBAB_HANGING_SIGN, RegisterBlocks.BAOBAB_WALL_HANGING_SIGN);
		generator.createTrivialBlock(RegisterBlocks.BAOBAB_LEAVES, TexturedModel.LEAVES);

		generator.family(RegisterBlocks.CYPRESS_PLANKS).generateFor(RegisterBlocks.CYPRESS);
		generator.woodProvider(RegisterBlocks.CYPRESS_LOG).logWithHorizontal(RegisterBlocks.CYPRESS_LOG).wood(RegisterBlocks.CYPRESS_WOOD);
		generator.woodProvider(RegisterBlocks.STRIPPED_CYPRESS_LOG).logWithHorizontal(RegisterBlocks.STRIPPED_CYPRESS_LOG).wood(RegisterBlocks.STRIPPED_CYPRESS_WOOD);
		generator.createHangingSign(RegisterBlocks.STRIPPED_CYPRESS_LOG, RegisterBlocks.CYPRESS_HANGING_SIGN, RegisterBlocks.CYPRESS_WALL_HANGING_SIGN);
		generator.createPlant(RegisterBlocks.CYPRESS_SAPLING, RegisterBlocks.POTTED_CYPRESS_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
		generator.createTrivialBlock(RegisterBlocks.CYPRESS_LEAVES, TexturedModel.LEAVES);

		generator.createPlant(RegisterBlocks.SEEDING_DANDELION, RegisterBlocks.POTTED_SEEDING_DANDELION, BlockModelGenerators.TintState.NOT_TINTED);
		generator.createPlant(RegisterBlocks.CARNATION, RegisterBlocks.POTTED_CARNATION, BlockModelGenerators.TintState.NOT_TINTED);

		generator.createTrivialBlock(RegisterBlocks.CHISELED_MUD_BRICKS, TexturedModel.CUBE);

		generator.createSimpleFlatItemModel(RegisterBlocks.ALGAE);
		generator.createSimpleFlatItemModel(RegisterBlocks.POLLEN);
		generator.createSimpleFlatItemModel(RegisterBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW);
		generator.createSimpleFlatItemModel(RegisterBlocks.PINK_GIANT_GLORY_OF_THE_SNOW);
		generator.createSimpleFlatItemModel(RegisterBlocks.ALBA_GLORY_OF_THE_SNOW);
		generator.createSimpleFlatItemModel(RegisterBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(RegisterBlocks.BUSH.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterBlocks.DISPLAY_LANTERN.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterBlocks.HANGING_TENDRIL.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterBlocks.CATTAIL.asItem(), ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterBlocks.SMALL_SPONGE.asItem(), ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(RegisterItems.BAOBAB_NUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.COCONUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SPLIT_COCONUT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.MILKWEED_POD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PRICKLY_PEAR, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PEELED_PRICKLY_PEAR, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(RegisterItems.JELLYFISH_BUCKET, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CRAB_BUCKET, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CRAB_CLAW, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.COOKED_CRAB_CLAW, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(RegisterItems.BAOBAB_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BAOBAB_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CYPRESS_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CYPRESS_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PALM_BOAT, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PALM_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
	}
}
