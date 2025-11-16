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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.lib.datagen.api.client.FrozenLibModelHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.block.state.properties.TubeWormsPart;
import net.frozenblock.wilderwild.client.renderer.item.properties.FireflyBottleColorProperty;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.DelegatedModel;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.Property;

public final class WWModelHelper {
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
	private static final ModelTemplate ALGAE_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_algae")),
		Optional.empty(),
		TextureSlot.TEXTURE
	);
	private static final ModelTemplate PLANKTON_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_plankton")),
		Optional.empty(),
		TextureSlot.TEXTURE
	);
	private static final ModelTemplate SEA_ANEMONE_MODEL = new ModelTemplate(
		Optional.of(WWConstants.id("block/template_sea_anemone")),
		Optional.empty(),
		TextureSlot.STEM,
		TextureSlot.TOP
	);

	public static void createHollowedLog(BlockModelGenerators generator, Block hollowedLog, Block sideAndEndSource, Block insideSource) {
		createHollowedLog(generator, hollowedLog, sideAndEndSource, insideSource, sideAndEndSource);
	}

	public static void createStrippedHollowedLog(BlockModelGenerators generator, Block hollowedLog, Block sideSource, Block insideAndEndSource) {
		createHollowedLog(generator, hollowedLog, sideSource, insideAndEndSource, insideAndEndSource);
	}

	public static void createHollowedLog(BlockModelGenerators generator, Block hollowedLog, Block sideSource, Block insideSource, Block endSource) {
		TextureMapping sideTextureMapping = TextureMapping.logColumn(sideSource);
		TextureMapping insideTextureMapping = TextureMapping.logColumn(insideSource);
		TextureMapping endTextureMapping = TextureMapping.logColumn(endSource);

		TextureMapping hollowedTextureMapping = new TextureMapping();
		hollowedTextureMapping.put(TextureSlot.SIDE, sideTextureMapping.get(TextureSlot.SIDE));
		hollowedTextureMapping.put(TextureSlot.INSIDE, insideTextureMapping.get(TextureSlot.SIDE));
		hollowedTextureMapping.put(TextureSlot.END, endTextureMapping.get(TextureSlot.END));

		Identifier verticalModelLocation = VERTICAL_HOLLOWED_LOG_MODEL.create(hollowedLog, hollowedTextureMapping, generator.modelOutput);
		MultiVariant verticalModel = BlockModelGenerators.plainVariant(verticalModelLocation);
		MultiVariant horizontalModel = BlockModelGenerators.plainVariant(HORIZONTAL_HOLLOWED_LOG_MODEL.create(hollowedLog, hollowedTextureMapping, generator.modelOutput));

		BlockModelDefinitionGenerator blockModelDefinitionGenerator = MultiVariantGenerator.dispatch(hollowedLog)
			.with(
				PropertyDispatch.initial(BlockStateProperties.AXIS)
					.select(Direction.Axis.Y, verticalModel)
					.select(Direction.Axis.Z, horizontalModel.with(BlockModelGenerators.X_ROT_90))
					.select(Direction.Axis.X, horizontalModel.with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_90))
			);
		generator.blockStateOutput.accept(blockModelDefinitionGenerator);
		generator.registerSimpleItemModel(hollowedLog, verticalModelLocation);
	}

	public static void createStoneChest(BlockModelGenerators generator, Block stoneChest, Block particleTexture, Identifier texture) {
		generator.createParticleOnlyBlock(stoneChest, particleTexture);
		final Item item = stoneChest.asItem();
		final Identifier model = ModelTemplates.CHEST_INVENTORY.create(item, TextureMapping.particle(particleTexture), generator.modelOutput);
		final ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(model, new StoneChestSpecialRenderer.Unbaked(texture));
		generator.itemModelOutput.accept(item, unbaked);
	}

	public static void createMesoglea(BlockModelGenerators generator, Block mesogleaBlock) {
		final TextureMapping mesogleaTextureMapping = new TextureMapping();
		mesogleaTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(mesogleaBlock));
		final Identifier modelId = MESOGLEA_MODEL.create(mesogleaBlock, mesogleaTextureMapping, generator.modelOutput);
		final MultiVariant multiVariant = BlockModelGenerators.plainVariant(modelId);

		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(mesogleaBlock, multiVariant));
		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(mesogleaBlock.asItem()), new DelegatedModel(modelId));
	}

	public static void createNematocyst(BlockModelGenerators generator, Block nematocystBlock) {
		final TextureMapping nematocystTextureMapping = new TextureMapping();
		nematocystTextureMapping.put(TextureSlot.INSIDE, TextureMapping.getBlockTexture(nematocystBlock));
		nematocystTextureMapping.put(TextureSlot.FAN, TextureMapping.getBlockTexture(nematocystBlock, "_outer"));
		final MultiVariant multiVariant = BlockModelGenerators.plainVariant(NEMATOCYST_MODEL.create(nematocystBlock, nematocystTextureMapping, generator.modelOutput));

		final Map<Property<Boolean>, VariantMutator> map = FrozenLibModelHelper.selectMultifaceNoUvLockProperties(nematocystBlock.defaultBlockState(), MultifaceBlock::getFaceProperty);
		final ConditionBuilder conditionBuilder = BlockModelGenerators.condition();
		map.forEach((property, variantMutator) -> conditionBuilder.term(property, false));

		final MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(nematocystBlock);
		map.forEach((property, variantMutator) -> {
			multiPartGenerator.with(BlockModelGenerators.condition().term(property, true), multiVariant.with(variantMutator));
			multiPartGenerator.with(conditionBuilder, multiVariant.with(variantMutator));
		});
		generator.registerSimpleFlatItemModel(nematocystBlock);
		generator.blockStateOutput.accept(multiPartGenerator);
	}

	public static void createFroglightGoop(BlockModelGenerators generator, Block bodyBlock, Block headBlock) {
		generator.registerSimpleFlatItemModel(headBlock.asItem());
		generator.createCrossBlock(headBlock, BlockModelGenerators.PlantType.NOT_TINTED);
		generator.createCrossBlock(bodyBlock, BlockModelGenerators.PlantType.NOT_TINTED);
	}

	public static void createShelfFungi(BlockModelGenerators generator, Block shelfFungiBlock) {
		final TextureMapping shelfFungiTextureMapping = new TextureMapping();
		shelfFungiTextureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(shelfFungiBlock));
		shelfFungiTextureMapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(shelfFungiBlock, "_bottom"));

		final MultiVariant model1 = BlockModelGenerators.plainVariant(SHELF_FUNGI_1_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));
		final MultiVariant model2 = BlockModelGenerators.plainVariant(SHELF_FUNGI_2_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));
		final MultiVariant model3 = BlockModelGenerators.plainVariant(SHELF_FUNGI_3_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));
		final MultiVariant model4 = BlockModelGenerators.plainVariant(SHELF_FUNGI_4_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));

		generator.registerSimpleFlatItemModel(shelfFungiBlock.asItem());
		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(shelfFungiBlock)
					.with(
						PropertyDispatch.initial(ShelfFungiBlock.STAGE)
							.select(1, model1)
							.select(2, model2)
							.select(3, model3)
							.select(4, model4)
					)
					.with(
						PropertyDispatch.modify(ShelfFungiBlock.FACE, ShelfFungiBlock.FACING)
							.select(AttachFace.CEILING, Direction.NORTH, BlockModelGenerators.X_ROT_180.then(BlockModelGenerators.Y_ROT_180))
							.select(AttachFace.CEILING, Direction.EAST, BlockModelGenerators.X_ROT_180.then(BlockModelGenerators.Y_ROT_270))
							.select(AttachFace.CEILING, Direction.SOUTH, BlockModelGenerators.X_ROT_180)
							.select(AttachFace.CEILING, Direction.WEST, BlockModelGenerators.X_ROT_180.then(BlockModelGenerators.Y_ROT_90))
							.select(AttachFace.FLOOR, Direction.NORTH, BlockModelGenerators.NOP)
							.select(AttachFace.FLOOR, Direction.EAST, BlockModelGenerators.Y_ROT_90)
							.select(AttachFace.FLOOR, Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
							.select(AttachFace.FLOOR, Direction.WEST, BlockModelGenerators.Y_ROT_270)
							.select(AttachFace.WALL, Direction.NORTH, BlockModelGenerators.X_ROT_90)
							.select(AttachFace.WALL, Direction.EAST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90))
							.select(AttachFace.WALL, Direction.SOUTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
							.select(AttachFace.WALL, Direction.WEST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
					)
			);
	}

	public static void createMultifaceBlock(BlockModelGenerators generator, Block multifaceBlock) {
		final TextureMapping multifaceTextureMapping = new TextureMapping();
		multifaceTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(multifaceBlock));
		MULTIFACE_MODEL.create(multifaceBlock, multifaceTextureMapping, generator.modelOutput);

		generator.createMultiface(multifaceBlock);
	}

	public static void createGeyser(BlockModelGenerators generator) {
		generator.createRotatableColumn(WWBlocks.GEYSER);
	}

	public static void generateMilkweedPod(ItemModelGenerators generator, Item item) {
		final ItemModel.Unbaked model = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
		final ItemModel.Unbaked blowing = ItemModelUtils.plainModel(WWConstants.id("item/milkweed_pod_blowing"));
		generator.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), blowing, model);
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

	public static void createUntintedLeafLitter(BlockModelGenerators generator, Block block) {
		final MultiVariant litter1 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_1.create(block, generator.modelOutput));
		final MultiVariant litter2 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_2.create(block, generator.modelOutput));
		final MultiVariant litter3 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_3.create(block, generator.modelOutput));
		final MultiVariant litter4 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_4.create(block, generator.modelOutput));
		generator.registerSimpleFlatItemModel(block);
		generator.createSegmentedBlock(
			block,
			litter1,
			BlockModelGenerators.LEAF_LITTER_MODEL_1_SEGMENT_CONDITION,
			litter2,
			BlockModelGenerators.LEAF_LITTER_MODEL_2_SEGMENT_CONDITION,
			litter3,
			BlockModelGenerators.LEAF_LITTER_MODEL_3_SEGMENT_CONDITION,
			litter4,
			BlockModelGenerators.LEAF_LITTER_MODEL_4_SEGMENT_CONDITION
		);
	}

	public static void generatePaleMushroomBlock(BlockModelGenerators generator) {
		final Block block = WWBlocks.PALE_MUSHROOM_BLOCK;
		final MultiVariant outside = BlockModelGenerators.plainVariant(ModelTemplates.SINGLE_FACE.create(block, TextureMapping.defaultTexture(block), generator.modelOutput));
		final MultiVariant inside = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block, "_inside"));
		generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
			.with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, true), outside)
			.with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, true), outside.with(BlockModelGenerators.Y_ROT_90).with(BlockModelGenerators.UV_LOCK))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, true), outside.with(BlockModelGenerators.Y_ROT_180).with(BlockModelGenerators.UV_LOCK))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, true), outside.with(BlockModelGenerators.Y_ROT_270).with(BlockModelGenerators.UV_LOCK))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.UP, true), outside.with(BlockModelGenerators.X_ROT_270).with(BlockModelGenerators.UV_LOCK))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.DOWN, true), outside.with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.UV_LOCK))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false), inside)
			.with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, false), inside.with(BlockModelGenerators.Y_ROT_90))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, false), inside.with(BlockModelGenerators.Y_ROT_180))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, false), inside.with(BlockModelGenerators.Y_ROT_270))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.UP, false), inside.with(BlockModelGenerators.X_ROT_270))
			.with(BlockModelGenerators.condition().term(BlockStateProperties.DOWN, false), inside.with(BlockModelGenerators.X_ROT_90)));
		generator.registerSimpleItemModel(block, TexturedModel.CUBE.createWithSuffix(block, "_inventory", generator.modelOutput));
	}

	public static void generateFireflyBottles(ItemModelGenerators generator) {
		final List<SelectItemModel.SwitchCase<String>> switchCases = new ArrayList<>();

		FireflyColors.getVanillaColors().forEach(fireflyColor -> {
			if (fireflyColor.equals(WWConstants.string("on"))) return;
			String color = Identifier.parse(fireflyColor).getPath();
			Identifier modelLocation = WWConstants.id("item/" + color + "_firefly_bottle");

			switchCases.add(
				ItemModelUtils.when(
					color,
					ItemModelUtils.plainModel(
						ModelTemplates.FLAT_ITEM.create(modelLocation, TextureMapping.layer0(modelLocation), generator.modelOutput)
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

	public static void createHibiscus(BlockModelGenerators generator, Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType) {
		generator.createCrossBlockWithDefaultItem(block, plantType);
		final TextureMapping textureMapping = TextureMapping.singleSlot(TextureSlot.PLANT, TextureMapping.getBlockTexture(pottedBlock));
		final MultiVariant pot = BlockModelGenerators.plainVariant(plantType.getCrossPot().create(pottedBlock, textureMapping, generator.modelOutput));
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, pot));
	}

	public static void createIcicle(BlockModelGenerators generator) {
		final PropertyDispatch.C2<MultiVariant, Direction, DripstoneThickness> dispatch = PropertyDispatch.initial(
			BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS
		);

		for (DripstoneThickness dripstoneThickness : DripstoneThickness.values()) {
			dispatch.select(Direction.UP, dripstoneThickness, createIcicleVariant(generator, Direction.UP, dripstoneThickness));
		}

		for (DripstoneThickness dripstoneThickness : DripstoneThickness.values()) {
			dispatch.select(Direction.DOWN, dripstoneThickness, createIcicleVariant(generator, Direction.DOWN, dripstoneThickness));
		}

		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(WWBlocks.ICICLE).with(dispatch));
	}

	private static MultiVariant createIcicleVariant(BlockModelGenerators generator, Direction direction, DripstoneThickness dripstoneThickness) {
		String string = "_" + direction.getSerializedName() + "_" + dripstoneThickness.getSerializedName();
		TextureMapping textureMapping = TextureMapping.cross(TextureMapping.getBlockTexture(WWBlocks.ICICLE, string));
		return BlockModelGenerators.plainVariant(ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(WWBlocks.ICICLE, string, textureMapping, generator.modelOutput));
	}

	public static void createFragileIce(BlockModelGenerators generator) {
		Identifier leastCrackedModelId = generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_0", ModelTemplates.CUBE_ALL, TextureMapping::cube);

		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(WWBlocks.FRAGILE_ICE)
					.with(
						PropertyDispatch.initial(BlockStateProperties.AGE_2)
							.select(
								0,
								BlockModelGenerators.plainVariant(leastCrackedModelId)
							)
							.select(
								1,
								BlockModelGenerators.plainVariant(generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))
							)
							.select(
								2,
								BlockModelGenerators.plainVariant(generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))
							)
					)
			);

		generator.itemModelOutput.accept(WWBlocks.FRAGILE_ICE.asItem(), ItemModelUtils.plainModel(leastCrackedModelId));
	}

	public static void createSeaAnemone(BlockModelGenerators generator, Block seaAnemoneBlock) {
		TextureMapping seaAnemoneTextureMapping = new TextureMapping();
		seaAnemoneTextureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(seaAnemoneBlock, "_stem"));
		seaAnemoneTextureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(seaAnemoneBlock, "_top"));
		Identifier modelId = SEA_ANEMONE_MODEL.create(seaAnemoneBlock, seaAnemoneTextureMapping, generator.modelOutput);

		MultiVariant variant = BlockModelGenerators.plainVariant(modelId);
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(seaAnemoneBlock, variant));
		generator.registerSimpleFlatItemModel(seaAnemoneBlock.asItem());
	}

	public static void createSeaWhip(BlockModelGenerators generator) {
		generator.registerSimpleFlatItemModel(WWBlocks.SEA_WHIP.asItem());
		generator.createCrossBlock(WWBlocks.SEA_WHIP, BlockModelGenerators.PlantType.NOT_TINTED);
	}

	public static void createAlgae(BlockModelGenerators generator) {
		generator.registerSimpleFlatItemModel(WWBlocks.ALGAE);
		Identifier model = generator.createSuffixedVariant(WWBlocks.ALGAE, "", ALGAE_MODEL, TextureMapping::defaultTexture);
		MultiVariant variant = BlockModelGenerators.plainVariant(model);
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(WWBlocks.ALGAE, variant));
	}

	public static void createPlankton(BlockModelGenerators generator) {
		generator.registerSimpleFlatItemModel(WWBlocks.PLANKTON);
		Identifier model = generator.createSuffixedVariant(WWBlocks.PLANKTON, "", ALGAE_MODEL, TextureMapping::defaultTexture);
		Identifier glowingModel = generator.createSuffixedVariant(WWBlocks.PLANKTON, "_glowing", PLANKTON_MODEL, TextureMapping::defaultTexture);

		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(WWBlocks.PLANKTON)
					.with(
						PropertyDispatch.initial(WWBlockStateProperties.GLOWING)
							.select(
								false,
								BlockModelGenerators.plainVariant(model)
							)
							.select(
								true,
								BlockModelGenerators.plainVariant(glowingModel)
							)
					)
			);
	}

	public static void createTubeWorms(BlockModelGenerators generator) {
		generator.registerSimpleFlatItemModel(WWBlocks.TUBE_WORMS);
		Identifier singleModel = generator.createSuffixedVariant(WWBlocks.TUBE_WORMS, "", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		Identifier topModel = generator.createSuffixedVariant(WWBlocks.TUBE_WORMS, "_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		Identifier middleModel = generator.createSuffixedVariant(WWBlocks.TUBE_WORMS, "_middle", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		Identifier bottomModel = generator.createSuffixedVariant(WWBlocks.TUBE_WORMS, "_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(WWBlocks.TUBE_WORMS)
					.with(
						PropertyDispatch.initial(WWBlockStateProperties.TUBE_WORMS_PART)
							.select(TubeWormsPart.SINGLE, BlockModelGenerators.plainVariant(singleModel))
							.select(TubeWormsPart.TOP, BlockModelGenerators.plainVariant(topModel))
							.select(TubeWormsPart.MIDDLE, BlockModelGenerators.plainVariant(middleModel))
							.select(TubeWormsPart.BOTTOM, BlockModelGenerators.plainVariant(bottomModel))
					)
			);
	}

	public static void createCattail(BlockModelGenerators generator) {
		generator.registerSimpleFlatItemModel(WWBlocks.CATTAIL.asItem());

		Identifier topModel = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		Identifier bottomModel = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);

		Identifier swayingTopStrong = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_swaying_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		Identifier swayingTopWeak = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_swaying_top_weak", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		Identifier swayingBottom = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_swaying_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);

		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(WWBlocks.CATTAIL)
					.with(
						PropertyDispatch.initial(BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.WATERLOGGED , WWBlockStateProperties.SWAYING)
							.select(DoubleBlockHalf.LOWER, false, false, BlockModelGenerators.plainVariant(bottomModel))
							.select(DoubleBlockHalf.LOWER, false, true, BlockModelGenerators.plainVariant(bottomModel))
							.select(DoubleBlockHalf.LOWER, true, false, BlockModelGenerators.plainVariant(bottomModel))
							.select(DoubleBlockHalf.LOWER, true, true, BlockModelGenerators.plainVariant(swayingBottom))

							.select(DoubleBlockHalf.UPPER, false, false, BlockModelGenerators.plainVariant(topModel))
							.select(DoubleBlockHalf.UPPER, false, true, BlockModelGenerators.plainVariant(swayingTopWeak))
							.select(DoubleBlockHalf.UPPER, true, false, BlockModelGenerators.plainVariant(topModel))
							.select(DoubleBlockHalf.UPPER, true, true, BlockModelGenerators.plainVariant(swayingTopStrong))
					)
			);
	}
}
