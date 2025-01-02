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

package net.frozenblock.wilderwild.datagen.model;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
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
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public final class WWModelHelper {
	public static final List<Pair<BooleanProperty, Function<ResourceLocation, Variant>>> MULTIFACE_GENERATOR_NO_UV_LOCK = List.of(
		Pair.of(BlockStateProperties.NORTH, model -> Variant.variant().with(VariantProperties.MODEL, model)),
		Pair.of(
			BlockStateProperties.EAST,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
		),
		Pair.of(
			BlockStateProperties.SOUTH,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
		),
		Pair.of(
			BlockStateProperties.WEST,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
		),
		Pair.of(
			BlockStateProperties.UP,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
		),
		Pair.of(
			BlockStateProperties.DOWN,
			resourceLocation -> Variant.variant()
				.with(VariantProperties.MODEL, resourceLocation)
				.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
		)
	);
	private static final ModelTemplate LEAF_LITTER_MODEL = new ModelTemplate(Optional.of(WWConstants.id("block/template_leaf_litter")), Optional.empty(), TextureSlot.TEXTURE);
	private static final TexturedModel.Provider LEAF_LITTER_PROVIDER = TexturedModel.createDefault(TextureMapping::defaultTexture, LEAF_LITTER_MODEL);
	private static final ModelTemplate VERTICAL_HOLLOWED_LOG_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_hollowed_log")),
		Optional.empty(),
		TextureSlot.SIDE, TextureSlot.INSIDE, TextureSlot.END
	);
	private static final ModelTemplate HORIZONTAL_HOLLOWED_LOG_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_hollowed_log_horizontal")),
		Optional.of("_horizontal"),
		TextureSlot.SIDE, TextureSlot.INSIDE, TextureSlot.END
	);
	private static final ModelTemplate MESOGLEA_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_mesoglea")),
		Optional.empty(),
		TextureSlot.TEXTURE
	);
	private static final ModelTemplate WATERLOGGED_MESOGLEA_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_mesoglea")),
		Optional.of("_waterlogged"),
		TextureSlot.TEXTURE
	);
	private static final ModelTemplate NEMATOCYST_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_nematocyst")),
		Optional.empty(),
		TextureSlot.INSIDE, TextureSlot.FAN
	);
	private static final ModelTemplate SHELF_FUNGI_1_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_shelf_fungi_1")),
		Optional.of("_1"),
		TextureSlot.TOP, TextureSlot.BOTTOM
	);
	private static final ModelTemplate SHELF_FUNGI_2_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_shelf_fungi_2")),
		Optional.of("_2"),
		TextureSlot.TOP, TextureSlot.BOTTOM
	);
	private static final ModelTemplate SHELF_FUNGI_3_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_shelf_fungi_3")),
		Optional.of("_3"),
		TextureSlot.TOP, TextureSlot.BOTTOM
	);
	private static final ModelTemplate SHELF_FUNGI_4_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_shelf_fungi_4")),
		Optional.of("_4"),
		TextureSlot.TOP, TextureSlot.BOTTOM
	);
	private static final ModelTemplate MULTIFACE_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_multiface_block")),
		Optional.empty(),
		TextureSlot.TEXTURE
	);

	public static void createLeafLitter(@NotNull BlockModelGenerators generator, Block litter) {
		createLeafLitter(generator, litter, litter);
	}

	public static void createLeafLitter(@NotNull BlockModelGenerators generator, Block litter, Block source) {
		ResourceLocation modelId = LEAF_LITTER_PROVIDER.get(source).create(litter, generator.modelOutput);
		ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(litter.asItem()), TextureMapping.layer0(TextureMapping.getBlockTexture(source)), generator.modelOutput);
		//generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(litter, BlockModelGenerators.createRotatedVariants(modelId)));
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(litter, modelId));
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

		ResourceLocation verticalModelId = VERTICAL_HOLLOWED_LOG_MODEL.create(hollowedLog, hollowedTextureMapping, generator.modelOutput);
		ResourceLocation horizontalModelId = HORIZONTAL_HOLLOWED_LOG_MODEL.create(hollowedLog, hollowedTextureMapping, generator.modelOutput);
		MultiVariantGenerator multiVariantGenerator = MultiVariantGenerator.multiVariant(hollowedLog)
			.with(PropertyDispatch.property(BlockStateProperties.AXIS)
				.select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, verticalModelId))
				.select(
					Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, horizontalModelId)
						.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
				).select(
					Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, horizontalModelId)
						.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
				)
			);
		generator.blockStateOutput.accept(multiVariantGenerator);
		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(hollowedLog.asItem()), new DelegatedModel(verticalModelId));
	}

	public static void createMesoglea(@NotNull BlockModelGenerators generator, Block mesogleaBlock) {
		TextureMapping mesogleaTextureMapping = new TextureMapping();
		mesogleaTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(mesogleaBlock));

		TextureMapping waterloggedMesogleaTextureMapping = new TextureMapping();
		waterloggedMesogleaTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(mesogleaBlock, "_waterlogged"));

		ResourceLocation modelId = MESOGLEA_MODEL.create(mesogleaBlock, mesogleaTextureMapping, generator.modelOutput);
		ResourceLocation waterloggedModelId = WATERLOGGED_MESOGLEA_MODEL.create(mesogleaBlock, waterloggedMesogleaTextureMapping, generator.modelOutput);

		MultiVariantGenerator multiVariantGenerator = MultiVariantGenerator.multiVariant(mesogleaBlock)
			.with(PropertyDispatch.property(BlockStateProperties.WATERLOGGED)
				.select(false, Variant.variant().with(VariantProperties.MODEL, modelId))
				.select(true, Variant.variant().with(VariantProperties.MODEL, waterloggedModelId))
			);
		generator.blockStateOutput.accept(multiVariantGenerator);
		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(mesogleaBlock.asItem()), new DelegatedModel(modelId));
	}

	public static void createNematocyst(@NotNull BlockModelGenerators generator, Block nematocystBlock) {
		MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(nematocystBlock);

		TextureMapping nematocystTextureMapping = new TextureMapping();
		nematocystTextureMapping.put(TextureSlot.INSIDE, TextureMapping.getBlockTexture(nematocystBlock));
		nematocystTextureMapping.put(TextureSlot.FAN, TextureMapping.getBlockTexture(nematocystBlock, "_outer"));
		ResourceLocation nematocystModel = NEMATOCYST_MODEL.create(nematocystBlock, nematocystTextureMapping, generator.modelOutput);

		Condition.TerminalCondition terminalCondition = Util.make(
			Condition.condition(), terminalConditionx -> MULTIFACE_GENERATOR_NO_UV_LOCK.stream().map(Pair::getFirst).forEach(booleanPropertyx -> {
				terminalConditionx.term(booleanPropertyx, false);
			})
		);

		for (Pair<BooleanProperty, Function<ResourceLocation, Variant>> pair : MULTIFACE_GENERATOR_NO_UV_LOCK) {
			BooleanProperty booleanProperty = pair.getFirst();
			Function<ResourceLocation, Variant> function = pair.getSecond();
			if (nematocystBlock.defaultBlockState().hasProperty(booleanProperty)) {
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true), function.apply(nematocystModel));
				multiPartGenerator.with(terminalCondition, function.apply(nematocystModel));
			}
		}

		generator.createSimpleFlatItemModel(nematocystBlock);
		generator.blockStateOutput.accept(multiPartGenerator);
	}

	public static void createShelfFungi(@NotNull BlockModelGenerators generator, @NotNull Block shelfFungiBlock) {
		TextureMapping shelfFungiTextureMapping = new TextureMapping();
		shelfFungiTextureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(shelfFungiBlock));
		shelfFungiTextureMapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(shelfFungiBlock, "_bottom"));

		ResourceLocation model1 = SHELF_FUNGI_1_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput);
		ResourceLocation model2 = SHELF_FUNGI_2_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput);
		ResourceLocation model3 = SHELF_FUNGI_3_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput);
		ResourceLocation model4 = SHELF_FUNGI_4_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput);

		generator.createSimpleFlatItemModel(shelfFungiBlock.asItem());
		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.multiVariant(shelfFungiBlock)
					.with(
						PropertyDispatch.property(ShelfFungiBlock.STAGE)
							.select(1, Variant.variant().with(VariantProperties.MODEL, model1))
							.select(2, Variant.variant().with(VariantProperties.MODEL, model2))
							.select(3, Variant.variant().with(VariantProperties.MODEL, model3))
							.select(4, Variant.variant().with(VariantProperties.MODEL, model4))
					)
					.with(
						PropertyDispatch.properties(ShelfFungiBlock.FACE, ShelfFungiBlock.FACING)
							.select(AttachFace.CEILING, Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
							.select(AttachFace.CEILING, Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
							.select(AttachFace.CEILING, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
							.select(AttachFace.CEILING, Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
							.select(AttachFace.FLOOR, Direction.NORTH, Variant.variant())
							.select(AttachFace.FLOOR, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
							.select(AttachFace.FLOOR, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
							.select(AttachFace.FLOOR, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
							.select(AttachFace.WALL, Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
							.select(AttachFace.WALL, Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
							.select(AttachFace.WALL, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
							.select(AttachFace.WALL, Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
					)
			);
	}

	public static void createMultifaceBlock(@NotNull BlockModelGenerators generator, Block multifaceBlock) {
		TextureMapping multifaceTextureMapping = new TextureMapping();
		multifaceTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(multifaceBlock));
		MULTIFACE_MODEL.create(multifaceBlock, multifaceTextureMapping, generator.modelOutput);

		generator.createMultiface(multifaceBlock);
	}
}
