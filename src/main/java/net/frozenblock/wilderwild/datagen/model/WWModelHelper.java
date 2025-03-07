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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.lib.datagen.api.client.FrozenLibModelHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.jetbrains.annotations.NotNull;

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

		ResourceLocation verticalModelLocation = VERTICAL_HOLLOWED_LOG_MODEL.create(hollowedLog, hollowedTextureMapping, generator.modelOutput);
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
		MultiVariant multiVariant = BlockModelGenerators.plainVariant(modelId);

		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(mesogleaBlock, multiVariant));
		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(mesogleaBlock.asItem()), new DelegatedModel(modelId));
	}

	public static void createNematocyst(@NotNull BlockModelGenerators generator, @NotNull Block nematocystBlock) {
		TextureMapping nematocystTextureMapping = new TextureMapping();
		nematocystTextureMapping.put(TextureSlot.INSIDE, TextureMapping.getBlockTexture(nematocystBlock));
		nematocystTextureMapping.put(TextureSlot.FAN, TextureMapping.getBlockTexture(nematocystBlock, "_outer"));
		MultiVariant multiVariant = BlockModelGenerators.plainVariant(NEMATOCYST_MODEL.create(nematocystBlock, nematocystTextureMapping, generator.modelOutput));

		Map<Property<Boolean>, VariantMutator> map = FrozenLibModelHelper.selectMultifaceNoUvLockProperties(nematocystBlock.defaultBlockState(), MultifaceBlock::getFaceProperty);
		ConditionBuilder conditionBuilder = BlockModelGenerators.condition();
		map.forEach((property, variantMutator) -> conditionBuilder.term(property, false));
		MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(nematocystBlock);
		map.forEach((property, variantMutator) -> {
			multiPartGenerator.with(BlockModelGenerators.condition().term(property, true), multiVariant.with(variantMutator));
			multiPartGenerator.with(conditionBuilder, multiVariant.with(variantMutator));
		});
		generator.registerSimpleFlatItemModel(nematocystBlock);
		generator.blockStateOutput.accept(multiPartGenerator);
	}

	public static void createShelfFungi(@NotNull BlockModelGenerators generator, @NotNull Block shelfFungiBlock) {
		TextureMapping shelfFungiTextureMapping = new TextureMapping();
		shelfFungiTextureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(shelfFungiBlock));
		shelfFungiTextureMapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(shelfFungiBlock, "_bottom"));

		MultiVariant model1 = BlockModelGenerators.plainVariant(SHELF_FUNGI_1_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));
		MultiVariant model2 = BlockModelGenerators.plainVariant(SHELF_FUNGI_2_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));
		MultiVariant model3 = BlockModelGenerators.plainVariant(SHELF_FUNGI_3_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));
		MultiVariant model4 = BlockModelGenerators.plainVariant(SHELF_FUNGI_4_MODEL.create(shelfFungiBlock, shelfFungiTextureMapping, generator.modelOutput));

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

	public static void createMultifaceBlock(@NotNull BlockModelGenerators generator, Block multifaceBlock) {
		TextureMapping multifaceTextureMapping = new TextureMapping();
		multifaceTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(multifaceBlock));
		MULTIFACE_MODEL.create(multifaceBlock, multifaceTextureMapping, generator.modelOutput);

		generator.createMultiface(multifaceBlock);
	}

	public static void generateCopperHorn(@NotNull ItemModelGenerators generator, Item item) {
		ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
		ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(WWConstants.id("item/tooting_copper_horn"));
		generator.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), unbaked2, unbaked);
	}

	public static void generateScorchedSandItem(@NotNull ItemModelGenerators generator, Item item) {
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

	public static void generateEchoGlass(@NotNull ItemModelGenerators generator, Item item) {
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

	public static void createUntintedLeafLitter(@NotNull BlockModelGenerators generator, Block block) {
		MultiVariant multiVariant = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_1.create(block, generator.modelOutput));
		MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_2.create(block, generator.modelOutput));
		MultiVariant multiVariant3 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_3.create(block, generator.modelOutput));
		MultiVariant multiVariant4 = BlockModelGenerators.plainVariant(TexturedModel.LEAF_LITTER_4.create(block, generator.modelOutput));
		generator.registerSimpleFlatItemModel(block);
		generator.createSegmentedBlock(block, BlockStateProperties.SEGMENT_AMOUNT, multiVariant, multiVariant2, multiVariant3, multiVariant4);
	}

	public static void generatePaleMushroomBlock(@NotNull BlockModelGenerators generator) {
		Block block = WWBlocks.PALE_MUSHROOM_BLOCK;
		MultiVariant outside = BlockModelGenerators.plainVariant(ModelTemplates.SINGLE_FACE.create(block, TextureMapping.defaultTexture(block), generator.modelOutput));
		MultiVariant inside = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block, "_inside"));
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

	public static void generateFireflyBottles(@NotNull ItemModelGenerators generator) {
		List<SelectItemModel.SwitchCase<String>> switchCases = new ArrayList<>();

		FireflyColors.getVanillaColors().forEach(fireflyColor -> {
			if (fireflyColor.equals(WWConstants.string("on"))) return;
			String color = ResourceLocation.parse(fireflyColor).getPath();
			ResourceLocation modelLocation = WWConstants.id("item/" + color + "_firefly_bottle");

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

	public static void createHibiscus(@NotNull BlockModelGenerators generator, Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType) {
		generator.createCrossBlockWithDefaultItem(block, plantType);
		TextureMapping textureMapping = TextureMapping.singleSlot(TextureSlot.PLANT, TextureMapping.getBlockTexture(pottedBlock));
		MultiVariant pot = BlockModelGenerators.plainVariant(plantType.getCrossPot().create(pottedBlock, textureMapping, generator.modelOutput));
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, pot));
	}

	public static void createCattail(@NotNull BlockModelGenerators generator, BlockModelGenerators.PlantType plantType) {
		ResourceLocation topModel = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		ResourceLocation bottomModel = generator.createSuffixedVariant(WWBlocks.CATTAIL, "_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
		generator.createDoubleBlock(WWBlocks.CATTAIL, topModel, bottomModel);
	}

	public static void createIcicle(@NotNull BlockModelGenerators generator) {
		PropertyDispatch.C2<MultiVariant, Direction, DripstoneThickness> c2 = PropertyDispatch.initial(
			BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS
		);

		for (DripstoneThickness dripstoneThickness : DripstoneThickness.values()) {
			c2.select(Direction.UP, dripstoneThickness, createIcicleVariant(generator, Direction.UP, dripstoneThickness));
		}

		for (DripstoneThickness dripstoneThickness : DripstoneThickness.values()) {
			c2.select(Direction.DOWN, dripstoneThickness, createIcicleVariant(generator, Direction.DOWN, dripstoneThickness));
		}

		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(WWBlocks.ICICLE).with(c2));
	}

	private static @NotNull MultiVariant createIcicleVariant(@NotNull BlockModelGenerators generator, @NotNull Direction direction, @NotNull DripstoneThickness dripstoneThickness) {
		String string = "_" + direction.getSerializedName() + "_" + dripstoneThickness.getSerializedName();
		TextureMapping textureMapping = TextureMapping.cross(TextureMapping.getBlockTexture(WWBlocks.ICICLE, string));
		return BlockModelGenerators.plainVariant(ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(WWBlocks.ICICLE, string, textureMapping, generator.modelOutput));
	}

	public static void createFragileIce(@NotNull BlockModelGenerators generator) {
		ResourceLocation leastCrackedModelId = generator.createSuffixedVariant(WWBlocks.FRAGILE_ICE, "_0", ModelTemplates.CUBE_ALL, TextureMapping::cube);

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

		generator.modelOutput.accept(ModelLocationUtils.getModelLocation(WWBlocks.FRAGILE_ICE.asItem()), new DelegatedModel(leastCrackedModelId));
	}
}
