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
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(RegisterItems.CRAB_CLAW, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.COOKED_CRAB_CLAW, ModelTemplates.FLAT_ITEM);
	}
}
