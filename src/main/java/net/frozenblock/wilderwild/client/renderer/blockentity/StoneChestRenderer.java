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

package net.frozenblock.wilderwild.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.StoneChestModel;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.StoneChestRenderState;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class StoneChestRenderer<T extends StoneChestBlockEntity & LidBlockEntity> implements BlockEntityRenderer<T, StoneChestRenderState> {
	public static final Material STONE = getStoneChestTextureId("stone");
	public static final Material STONE_LEFT = getStoneChestTextureId("stone_left");
	public static final Material STONE_RIGHT = getStoneChestTextureId("stone_right");
	public static final Material STONE_SCULK = getStoneChestTextureId("ancient");
	public static final Material STONE_LEFT_SCULK = getStoneChestTextureId("ancient_left");
	public static final Material STONE_RIGHT_SCULK = getStoneChestTextureId("ancient_right");
	private final MaterialSet materials;
	private final StoneChestModel singleModel;
	private final StoneChestModel doubleLeftModel;
	private final StoneChestModel doubleRightModel;

	public StoneChestRenderer(BlockEntityRendererProvider.Context context) {
		this.materials = context.materials();
		this.singleModel = new StoneChestModel(context.bakeLayer(WWModelLayers.STONE_CHEST));
		this.doubleLeftModel = new StoneChestModel(context.bakeLayer(WWModelLayers.DOUBLE_STONE_CHEST_LEFT));
		this.doubleRightModel = new StoneChestModel(context.bakeLayer(WWModelLayers.DOUBLE_STONE_CHEST_RIGHT));
	}

	public static Material getStoneChestTexture(boolean sculk, ChestType type) {
		return !sculk ? getStoneChestTexture(type, STONE, STONE_LEFT, STONE_RIGHT) : getStoneChestTexture(type, STONE_SCULK, STONE_LEFT_SCULK, STONE_RIGHT_SCULK);
	}

	private static Material getStoneChestTexture(ChestType type, Material single, Material left, Material right) {
		return switch (type) {
			case LEFT -> left;
			case RIGHT -> right;
			case SINGLE -> single;
		};
	}

	public static Material getStoneChestTextureId(String variant) {
		return new Material(Sheets.CHEST_SHEET, WWConstants.id("entity/stone_chest/" + variant));
	}

	@Override
	public void submit(
		StoneChestRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState cameraRenderState
	) {
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.angle));
		poseStack.translate(-0.5F, -0.5F, -0.5F);

		float openProgress = renderState.open;
		openProgress = 1F - openProgress;
		openProgress = 1F - openProgress * openProgress * openProgress;

		final Material material = getStoneChestTexture(renderState.hasSculk, renderState.type);
		final RenderType renderType = material.renderType(RenderTypes::entityCutout);
		final TextureAtlasSprite sprite = this.materials.get(material);

		if (renderState.type != ChestType.SINGLE) {
			collector.submitModel(
				renderState.type == ChestType.LEFT ? this.doubleLeftModel : this.doubleRightModel,
				openProgress,
				poseStack,
				renderType,
				renderState.lightCoords,
				OverlayTexture.NO_OVERLAY,
				-1,
				sprite,
				0,
				renderState.breakProgress
			);
		} else {
			collector.submitModel(
				this.singleModel,
				openProgress,
				poseStack,
				renderType,
				renderState.lightCoords,
				OverlayTexture.NO_OVERLAY,
				-1,
				sprite,
				0,
				renderState.breakProgress
			);
		}

		poseStack.popPose();
	}

	@Override
	public StoneChestRenderState createRenderState() {
		return new StoneChestRenderState();
	}

	@Override
	public void extractRenderState(
		T stoneChest,
		StoneChestRenderState renderState,
		float partialTicks,
		Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
	) {
		BlockEntityRenderer.super.extractRenderState(stoneChest, renderState, partialTicks, cameraPos, crumblingOverlay);

		final boolean levelExists = stoneChest.getLevel() != null;
		final BlockState state = levelExists ? stoneChest.getBlockState() : WWBlocks.STONE_CHEST.defaultBlockState().setValue(StoneChestBlock.FACING, Direction.SOUTH);
		renderState.type = state.hasProperty(StoneChestBlock.TYPE) ? state.getValue(StoneChestBlock.TYPE) : ChestType.SINGLE;
		renderState.angle = state.getValue(StoneChestBlock.FACING).toYRot();
		renderState.hasSculk = state.getOptionalValue(WWBlockStateProperties.HAS_SCULK).orElse(false);

		DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> neighborCombineResult;
		if (levelExists && state.getBlock() instanceof ChestBlock chestBlock) {
			neighborCombineResult = chestBlock.combine(state, stoneChest.getLevel(), stoneChest.getBlockPos(), true);
		} else {
			neighborCombineResult = DoubleBlockCombiner.Combiner::acceptNone;
		}

		renderState.open = neighborCombineResult.apply(StoneChestBlock.opennessCombiner(stoneChest)).get(partialTicks);
		if (renderState.type != ChestType.SINGLE) renderState.lightCoords = neighborCombineResult.apply(new BrightnessCombiner<>()).applyAsInt(renderState.lightCoords);
	}

}
