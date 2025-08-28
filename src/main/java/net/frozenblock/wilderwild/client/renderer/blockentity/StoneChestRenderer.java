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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class StoneChestRenderer<T extends StoneChestBlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
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

	public StoneChestRenderer(@NotNull BlockEntityRendererProvider.Context context) {
		this.materials = context.materials();
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
	public void submit(
		@NotNull T entity,
		float partialTick,
		@NotNull PoseStack poseStack,
		int light,
		int overlay,
		@NotNull Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
		@NotNull SubmitNodeCollector submitNodeCollector
	) {
		Level level = entity.getLevel();
		final boolean hasLevel = level != null;
		BlockState blockState = hasLevel ? entity.getBlockState() : WWBlocks.STONE_CHEST.defaultBlockState().setValue(StoneChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.hasProperty(StoneChestBlock.TYPE) ? blockState.getValue(StoneChestBlock.TYPE) : ChestType.SINGLE;

		if (!(blockState.getBlock() instanceof AbstractChestBlock<?> abstractChestBlock)) return;

		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-blockState.getValue(StoneChestBlock.FACING).toYRot()));
		poseStack.translate(-0.5F, -0.5F, -0.5F);

		DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combineResult = hasLevel
			? abstractChestBlock.combine(blockState, level, entity.getBlockPos(), true)
			: DoubleBlockCombiner.Combiner::acceptNone;
		float openProgess = combineResult.apply(ChestBlock.opennessCombiner(entity)).get(partialTick);
		openProgess = 1F - openProgess;
		openProgess = 1F - openProgess * openProgess * openProgess;

		final Material texture = getStoneChestTexture(chestType, entity.getBlockState().getValue(WWBlockStateProperties.HAS_SCULK));
		final RenderType renderType = texture.renderType(RenderType::entityCutout);
		final TextureAtlasSprite sprite = this.materials.get(texture);

		if (chestType != ChestType.SINGLE) {
			submitNodeCollector.submitModel(
				chestType == ChestType.LEFT ? this.doubleLeftModel : doubleRightModel,
				openProgess,
				poseStack,
				renderType,
				light,
				overlay,
				-1,
				sprite,
				0,
				crumblingOverlay
			);
		} else {
			final int combinedLight = combineResult.apply(new BrightnessCombiner<>()).applyAsInt(light);
			submitNodeCollector.submitModel(
				this.singleModel,
				openProgess,
				poseStack,
				renderType,
				combinedLight,
				overlay,
				-1,
				sprite,
				0,
				crumblingOverlay
			);
		}

		poseStack.popPose();
	}

	private void render(
		@NotNull PoseStack poseStack, @NotNull VertexConsumer vertices, @NotNull StoneChestModel stoneChestModel, float liftProgress, int light, int overlay
	) {
		stoneChestModel.setupAnim(liftProgress);
		stoneChestModel.renderToBuffer(poseStack, vertices, light, overlay);
	}
}
