/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.datagen;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class WWModelTemplates {
	private static final ModelTemplate LEAF_LITTER_MODEL = new ModelTemplate(Optional.of(WWConstants.id("block/template_leaf_litter")), Optional.empty(), TextureSlot.TEXTURE);
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
