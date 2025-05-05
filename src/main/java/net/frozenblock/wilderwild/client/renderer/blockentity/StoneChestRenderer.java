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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.StoneChestModel;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class StoneChestRenderer<T extends StoneChestBlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
	public static final Material STONE = getStoneChestTextureId("stone");
	public static final Material STONE_LEFT = getStoneChestTextureId("stone_left");
	public static final Material STONE_RIGHT = getStoneChestTextureId("stone_right");
	public static final Material STONE_SCULK = getStoneChestTextureId("ancient");
	public static final Material STONE_LEFT_SCULK = getStoneChestTextureId("ancient_left");
	public static final Material STONE_RIGHT_SCULK = getStoneChestTextureId("ancient_right");
	private final StoneChestModel singleModel;
	private final StoneChestModel doubleLeftModel;
	private final StoneChestModel doubleRightModel;

	public StoneChestRenderer(@NotNull BlockEntityRendererProvider.Context context) {
		this.singleModel = new StoneChestModel(context.bakeLayer(WWModelLayers.STONE_CHEST));
		this.doubleLeftModel = new StoneChestModel(context.bakeLayer(WWModelLayers.DOUBLE_STONE_CHEST_LEFT));
		this.doubleRightModel = new StoneChestModel(context.bakeLayer(WWModelLayers.DOUBLE_STONE_CHEST_RIGHT));
	}

	@NotNull
	public static Material getStoneChestTexture(@NotNull ChestType type, boolean sculk) {
		return !sculk ? getStoneChestTexture(type, STONE, STONE_LEFT, STONE_RIGHT) : getStoneChestTexture(type, STONE_SCULK, STONE_LEFT_SCULK, STONE_RIGHT_SCULK);
	}

	@NotNull
	private static Material getStoneChestTexture(@NotNull ChestType type, @NotNull Material single, @NotNull Material left, @NotNull Material right) {
		return switch (type) {
			case LEFT -> left;
			case RIGHT -> right;
			case SINGLE -> single;
		};
	}

	@NotNull
	public static Material getStoneChestTextureId(@NotNull String variant) {
		return new Material(Sheets.CHEST_SHEET, WWConstants.id("entity/stone_chest/" + variant));
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay, Vec3 cameraPos) {
		Level level = entity.getLevel();
		boolean bl = level != null;
		BlockState blockState = bl ? entity.getBlockState() : WWBlocks.STONE_CHEST.defaultBlockState().setValue(StoneChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.hasProperty(StoneChestBlock.TYPE) ? blockState.getValue(StoneChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (block instanceof AbstractChestBlock<?> abstractStoneChestBlock) {
			boolean isDouble = chestType != ChestType.SINGLE;
			poseStack.pushPose();
			float chestRotation = blockState.getValue(StoneChestBlock.FACING).toYRot();
			poseStack.translate(0.5F, 0.5F, 0.5F);
			poseStack.mulPose(Axis.YP.rotationDegrees(-chestRotation));
			poseStack.translate(-0.5F, -0.5F, -0.5F);
			DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> propertySource = bl
				? abstractStoneChestBlock.combine(blockState, level, entity.getBlockPos(), true) : DoubleBlockCombiner.Combiner::acceptNone;

			float openProg = entity.getOpenProgress(partialTick);
			openProg = 1F - openProg;
			openProg = 1F - openProg * openProg * openProg;
			int i = propertySource.apply(new BrightnessCombiner<>()).applyAsInt(light);
			Material spriteIdentifier = getStoneChestTexture(chestType, entity.getBlockState().getValue(WWBlockStateProperties.HAS_SCULK));
			VertexConsumer vertexConsumer = spriteIdentifier.buffer(buffer, RenderType::entityCutout);
			if (isDouble) {
				if (chestType == ChestType.LEFT) {
					this.render(poseStack, vertexConsumer, this.doubleLeftModel, openProg, i, overlay);
				} else {
					this.render(poseStack, vertexConsumer, this.doubleRightModel, openProg, i, overlay);
				}
			} else {
				this.render(poseStack, vertexConsumer, this.singleModel, openProg, i, overlay);
			}

			poseStack.popPose();
		}
	}

	private void render(
		@NotNull PoseStack poseStack, @NotNull VertexConsumer vertices, @NotNull StoneChestModel stoneChestModel, float liftProgress, int light, int overlay
	) {
		stoneChestModel.setupAnim(liftProgress);
		stoneChestModel.renderToBuffer(poseStack, vertices, light, overlay);
	}
}
