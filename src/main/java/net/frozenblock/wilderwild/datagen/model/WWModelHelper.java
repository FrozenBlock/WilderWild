/*
 * Copyright 2024 FrozenBlock
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
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public final class WWModelHelper {
	private static final ModelTemplate LEAF_LITTER_MODEL = new ModelTemplate(Optional.of(WWConstants.id("block/template_leaf_litter")), Optional.empty(), TextureSlot.TEXTURE);
	private static final TexturedModel.Provider LEAF_LITTER_PROVIDER = TexturedModel.createDefault(TextureMapping::defaultTexture, LEAF_LITTER_MODEL);
	private static final ModelTemplate HOLLOWED_LOG_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_hollowed_log")),
		Optional.empty(),
		TextureSlot.SIDE, TextureSlot.INSIDE, TextureSlot.END
	);

	public static void createLeafLitter(@NotNull BlockModelGenerators generator, Block litter) {
		createLeafLitter(generator, litter, litter);
	}

	public static void createLeafLitter(@NotNull BlockModelGenerators generator, Block litter, Block source) {
		ResourceLocation modelId = LEAF_LITTER_PROVIDER.get(source).create(litter, generator.modelOutput);
		ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(litter.asItem()), TextureMapping.layer0(TextureMapping.getBlockTexture(source)), generator.modelOutput);
		generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(litter, BlockModelGenerators.createRotatedVariants(modelId)));
	}

	public static void createHollowedLog(@NotNull BlockModelGenerators generator, Block hollowedLog, Block sideAndEndSource, Block insideSource) {
		createHollowedLog(generator, hollowedLog, sideAndEndSource, insideSource, sideAndEndSource);
	}

	public static void createStrippedHollowedLog(@NotNull BlockModelGenerators generator, Block hollowedLog, Block sideSource, Block insideAndEndSource) {
		createHollowedLog(generator, hollowedLog, sideSource, insideAndEndSource, insideAndEndSource);
	}

	public static void createHollowedLog(@NotNull BlockModelGenerators generator, Block hollowedLog, Block sideSource, Block insideSource, Block endSource) {
		TextureMapping sideTextureMapping = TextureMapping.logColumn(sideSource);
		TextureMapping insideTextureMapping = TextureMapping.logColumn(insideSource);
		TextureMapping endTextureMapping = TextureMapping.logColumn(endSource);

		TextureMapping hollowedTextureMapping = new TextureMapping();
		hollowedTextureMapping.put(TextureSlot.SIDE, sideTextureMapping.get(TextureSlot.SIDE));
		hollowedTextureMapping.put(TextureSlot.INSIDE, insideTextureMapping.get(TextureSlot.SIDE));
		hollowedTextureMapping.put(TextureSlot.END, endTextureMapping.get(TextureSlot.END));

		ResourceLocation modelId = HOLLOWED_LOG_MODEL.create(hollowedLog, hollowedTextureMapping, generator.modelOutput);
		MultiVariantGenerator multiVariantGenerator = MultiVariantGenerator.multiVariant(hollowedLog)
			.with(PropertyDispatch.property(BlockStateProperties.AXIS)
				.select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, modelId))
				.select(
					Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, modelId)
						.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
				).select(
					Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, modelId)
						.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
				)
			);
		generator.blockStateOutput.accept(multiVariantGenerator);
		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(hollowedLog.asItem()), new DelegatedModel(modelId));
	}
}
