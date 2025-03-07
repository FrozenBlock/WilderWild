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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.client.renderer.item.properties.FireflyBottleColorProperty;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.Util;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.DelegatedModel;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
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
		generator.registerSimpleFlatItemModel(litter);
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

	public static void createStoneChest(@NotNull BlockModelGenerators generator, Block stoneChest, Block particleTexture, ResourceLocation texture) {
		generator.createParticleOnlyBlock(stoneChest, particleTexture);
		Item item = stoneChest.asItem();
		ResourceLocation resourceLocation2 = ModelTemplates.CHEST_INVENTORY.create(item, TextureMapping.particle(particleTexture), generator.modelOutput);
		ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation2, new StoneChestSpecialRenderer.Unbaked(texture));
		generator.itemModelOutput.accept(item, unbaked);
	}

	public static void createMesoglea(@NotNull BlockModelGenerators generator, Block mesogleaBlock) {
		TextureMapping mesogleaTextureMapping = new TextureMapping();
		mesogleaTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(mesogleaBlock));
		ResourceLocation modelId = MESOGLEA_MODEL.create(mesogleaBlock, mesogleaTextureMapping, generator.modelOutput);

		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(mesogleaBlock, modelId));
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

		generator.registerSimpleFlatItemModel(nematocystBlock);
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

		generator.registerSimpleFlatItemModel(shelfFungiBlock.asItem());
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

	public static void generateCopperHorn(ItemModelGenerators generator, Item item) {
		ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
		ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(WWConstants.id("item/tooting_copper_horn"));
		generator.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), unbaked2, unbaked);
	}

	public static void generateScorchedSandItem(ItemModelGenerators generator, Item item) {
		generator.itemModelOutput.accept(
			item,
			ItemModelUtils.selectBlockItemProperty(
				WWBlockStateProperties.CRACKED,
				ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)),
				Map.of(
					true, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_cracked"))
				)
			)
		);

	}

	public static void generateEchoGlass(ItemModelGenerators generator, Item item) {
		generator.itemModelOutput.accept(
			item,
			ItemModelUtils.selectBlockItemProperty(
				WWBlockStateProperties.DAMAGE,
				ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)),
				Map.of(
					1, ItemModelUtils.plainModel(WWConstants.id("item/echo_glass_1")),
					2, ItemModelUtils.plainModel(WWConstants.id("item/echo_glass_2")),
					3, ItemModelUtils.plainModel(WWConstants.id("item/echo_glass_3"))
				)
			)
		);
	}

	public static void generatePaleMushroomBlock(@NotNull BlockModelGenerators generator) {
		Block block = WWBlocks.PALE_MUSHROOM_BLOCK;
		ResourceLocation resourceLocation = ModelTemplates.SINGLE_FACE.create(block, TextureMapping.defaultTexture(block), generator.modelOutput);
		ResourceLocation insideTexture = TextureMapping.getBlockTexture(block, "_inside");
		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(block)
				.with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation))
				.with(Condition.condition().term(BlockStateProperties.EAST, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation)
					.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					.with(VariantProperties.UV_LOCK, true)
				).with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation)
					.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					.with(VariantProperties.UV_LOCK, true)
				).with(Condition.condition().term(BlockStateProperties.WEST, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation)
					.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					.with(VariantProperties.UV_LOCK, true)
				).with(Condition.condition().term(BlockStateProperties.UP, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation)
					.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
					.with(VariantProperties.UV_LOCK, true)
				).with(Condition.condition().term(BlockStateProperties.DOWN, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation)
					.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
					.with(VariantProperties.UV_LOCK, true)
				).with(Condition.condition().term(BlockStateProperties.NORTH, false), Variant.variant().with(VariantProperties.MODEL, insideTexture))
				.with(Condition.condition().term(BlockStateProperties.EAST, false), Variant.variant().with(VariantProperties.MODEL, insideTexture)
					.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					.with(VariantProperties.UV_LOCK, false)
				).with(Condition.condition().term(BlockStateProperties.SOUTH, false), Variant.variant().with(VariantProperties.MODEL, insideTexture)
					.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					.with(VariantProperties.UV_LOCK, false)
				).with(Condition.condition().term(BlockStateProperties.WEST, false), Variant.variant().with(VariantProperties.MODEL, insideTexture)
					.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					.with(VariantProperties.UV_LOCK, false)
				).with(Condition.condition().term(BlockStateProperties.UP, false), Variant.variant().with(VariantProperties.MODEL, insideTexture)
					.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
					.with(VariantProperties.UV_LOCK, false)
				).with(Condition.condition().term(BlockStateProperties.DOWN, false), Variant.variant().with(VariantProperties.MODEL, insideTexture)
					.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
					.with(VariantProperties.UV_LOCK, false)
				)
		);
		generator.registerSimpleItemModel(block, TexturedModel.CUBE.createWithSuffix(block, "_inventory", generator.modelOutput));
	}

	public static void generateFireflyBottles(@NotNull ItemModelGenerators generator) {
		List<SelectItemModel.SwitchCase<ResourceLocation>> switchCases = new ArrayList<>();

		FireflyColors.getVanillaColors().forEach(fireflyColor -> {
			if (fireflyColor.equals(WWConstants.string("on"))) return;
			ResourceLocation colorKey = ResourceLocation.parse(fireflyColor);
			ResourceLocation location = ResourceLocation.fromNamespaceAndPath(colorKey.getNamespace(), "item/" + colorKey.getPath() + "_firefly_bottle");

			switchCases.add(
				ItemModelUtils.when(
					colorKey,
					ItemModelUtils.plainModel(
						ModelTemplates.FLAT_ITEM.create(location, TextureMapping.layer0(location), generator.modelOutput)
					)
				)
			);
		});

		generator.itemModelOutput.accept(
			WWItems.FIREFLY_BOTTLE,
			ItemModelUtils.select(
				new FireflyBottleColorProperty(),
				ItemModelUtils.plainModel(generator.createFlatItemModel(WWItems.FIREFLY_BOTTLE, ModelTemplates.FLAT_ITEM)),
				switchCases
			)
		);
	}

	public static void createHibiscus(@NotNull BlockModelGenerators generator, Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType) {
		generator.createCrossBlockWithDefaultItem(block, plantType);
		TextureMapping textureMapping = TextureMapping.singleSlot(TextureSlot.PLANT, TextureMapping.getBlockTexture(pottedBlock));
		ResourceLocation resourceLocation = plantType.getCrossPot().create(pottedBlock, textureMapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, resourceLocation));
	}

	public static void createCattail(@NotNull BlockModelGenerators generator) {
		ResourceLocation topModel = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		ResourceLocation bottomModel = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		generator.createDoubleBlock(WWBlocks.CATTAIL, topModel, bottomModel);
	}

	public static void createIcicle(@NotNull BlockModelGenerators generator) {
		PropertyDispatch.C2<Direction, DripstoneThickness> c2 = PropertyDispatch.properties(
			BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS
		);

		for (DripstoneThickness dripstoneThickness : DripstoneThickness.values()) {
			c2.select(Direction.UP, dripstoneThickness, createIcicleVariant(generator, Direction.UP, dripstoneThickness));
		}

		for (DripstoneThickness dripstoneThickness : DripstoneThickness.values()) {
			c2.select(Direction.DOWN, dripstoneThickness, createIcicleVariant(generator, Direction.DOWN, dripstoneThickness));
		}

		generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(WWBlocks.ICICLE).with(c2));
	}

	private static @NotNull Variant createIcicleVariant(@NotNull BlockModelGenerators generator, @NotNull Direction direction, @NotNull DripstoneThickness dripstoneThickness) {
		String string = "_" + direction.getSerializedName() + "_" + dripstoneThickness.getSerializedName();
		TextureMapping textureMapping = TextureMapping.cross(TextureMapping.getBlockTexture(WWBlocks.ICICLE, string));
		return Variant.variant()
			.with(VariantProperties.MODEL, ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(WWBlocks.ICICLE, string, textureMapping, generator.modelOutput));
	}

	public static void createFragileIce(@NotNull BlockModelGenerators generator) {
		ResourceLocation leastCrackedModelId = generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_0", ModelTemplates.CUBE_ALL, TextureMapping::cube);

		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.multiVariant(WWBlocks.FRAGILE_ICE)
					.with(
						PropertyDispatch.property(BlockStateProperties.AGE_2)
							.select(
								0,
								Variant.variant()
									.with(
										VariantProperties.MODEL,
										leastCrackedModelId
									)
							)
							.select(
								1,
								Variant.variant()
									.with(
										VariantProperties.MODEL,
										generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_1", ModelTemplates.CUBE_ALL, TextureMapping::cube)
									)
							)
							.select(
								2,
								Variant.variant()
									.with(
										VariantProperties.MODEL,
										generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_2", ModelTemplates.CUBE_ALL, TextureMapping::cube)
									)
							)
					)
			);

		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(WWBlocks.FRAGILE_ICE.asItem()), new DelegatedModel(leastCrackedModelId));
	}
}
