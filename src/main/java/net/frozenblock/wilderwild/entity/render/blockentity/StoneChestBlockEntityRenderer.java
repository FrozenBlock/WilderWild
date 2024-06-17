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

package net.frozenblock.wilderwild.entity.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
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
import org.jetbrains.annotations.NotNull;

public class StoneChestBlockEntityRenderer<T extends StoneChestBlockEntity & LidBlockEntity> extends ChestRenderer<T> {
	public static final Material STONE = getChestTextureId("stone");
	public static final Material STONE_LEFT = getChestTextureId("stone_left");
	public static final Material STONE_RIGHT = getChestTextureId("stone_right");
	public static final Material STONE_SCULK = getChestTextureId("ancient");
	public static final Material STONE_LEFT_SCULK = getChestTextureId("ancient_left");
	public static final Material STONE_RIGHT_SCULK = getChestTextureId("ancient_right");
	private static final String BASE = "bottom";
	private static final String LID = "lid";
	private final ModelPart singleChestLid;
	private final ModelPart singleChestBase;
	private final ModelPart doubleChestLeftLid;
	private final ModelPart doubleChestLeftBase;
	private final ModelPart doubleChestRightLid;
	private final ModelPart doubleChestRightBase;

	public StoneChestBlockEntityRenderer(@NotNull BlockEntityRendererProvider.Context ctx) {
		super(ctx);

		ModelPart modelPart = ctx.bakeLayer(WilderWildClient.STONE_CHEST);
		this.singleChestBase = modelPart.getChild(BASE);
		this.singleChestLid = modelPart.getChild(LID);
		ModelPart modelPart2 = ctx.bakeLayer(WilderWildClient.DOUBLE_STONE_CHEST_LEFT);
		this.doubleChestLeftBase = modelPart2.getChild(BASE);
		this.doubleChestLeftLid = modelPart2.getChild(LID);
		ModelPart modelPart3 = ctx.bakeLayer(WilderWildClient.DOUBLE_STONE_CHEST_RIGHT);
		this.doubleChestRightBase = modelPart3.getChild(BASE);
		this.doubleChestRightLid = modelPart3.getChild(LID);
	}

	@NotNull
	public static LayerDefinition createSingleBodyLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(1.0F, 0.0F, 1.0F, 14.0F, 12.0F, 14.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 11.0F, 1.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@NotNull
	public static LayerDefinition createDoubleBodyRightLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(1.0F, 0.0F, 1.0F, 15.0F, 12.0F, 14.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 15.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 11.0F, 1.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@NotNull
	public static LayerDefinition createDoubleBodyLeftLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, 0.0F, 1.0F, 15.0F, 12.0F, 14.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 15.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 11.0F, 1.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@NotNull
	public static Material getChestTexture(@NotNull ChestType type, boolean sculk) {
		return !sculk ? getChestTexture(type, STONE, STONE_LEFT, STONE_RIGHT) : getChestTexture(type, STONE_SCULK, STONE_LEFT_SCULK, STONE_RIGHT_SCULK);
	}

	@NotNull
	private static Material getChestTexture(@NotNull ChestType type, @NotNull Material single, @NotNull Material left, @NotNull Material right) {
		return switch (type) {
			case LEFT -> left;
			case RIGHT -> right;
			case SINGLE -> single;
		};
	}

	@NotNull
	public static Material getChestTextureId(@NotNull String variant) {
		return new Material(Sheets.CHEST_SHEET, WilderSharedConstants.id("entity/stone_chest/" + variant));
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
		Level level = entity.getLevel();
		boolean bl = level != null;
		BlockState blockState = bl ? entity.getBlockState() : RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(StoneChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.hasProperty(StoneChestBlock.TYPE) ? blockState.getValue(StoneChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (block instanceof AbstractChestBlock<?> abstractStoneChestBlock) {
			boolean isDouble = chestType != ChestType.SINGLE;
			poseStack.pushPose();
			float f = blockState.getValue(StoneChestBlock.FACING).toYRot();
			poseStack.translate(0.5F, 0.5F, 0.5F);
			poseStack.mulPose(Axis.YP.rotationDegrees(-f));
			poseStack.translate(-0.5F, -0.5F, -0.5F);
			DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> propertySource;
			if (bl) {
				propertySource = abstractStoneChestBlock.combine(blockState, level, entity.getBlockPos(), true);
			} else {
				propertySource = DoubleBlockCombiner.Combiner::acceptNone;
			}

			float openProg = entity.getOpenProgress(partialTick);
			openProg = 1F - openProg;
			openProg = 1F - openProg * openProg * openProg;
			int i = propertySource.apply(new BrightnessCombiner<>()).applyAsInt(light);
			Material spriteIdentifier = getChestTexture(chestType, entity.getBlockState().getValue(RegisterProperties.HAS_SCULK));
			VertexConsumer vertexConsumer = spriteIdentifier.buffer(buffer, RenderType::entityCutout);
			if (isDouble) {
				if (chestType == ChestType.LEFT) {
					this.render(poseStack, vertexConsumer, this.doubleChestLeftLid, this.doubleChestLeftBase, openProg, i, overlay);
				} else {
					this.render(poseStack, vertexConsumer, this.doubleChestRightLid, this.doubleChestRightBase, openProg, i, overlay);
				}
			} else {
				this.render(poseStack, vertexConsumer, this.singleChestLid, this.singleChestBase, openProg, i, overlay);
			}

			poseStack.popPose();
		}
	}

	private void render(@NotNull PoseStack matrices, @NotNull VertexConsumer vertices, @NotNull ModelPart lid, @NotNull ModelPart base, float openFactor, int light, int overlay) {
		lid.xRot = -(openFactor * 1.5707964F);
		lid.render(matrices, vertices, light, overlay);
		base.render(matrices, vertices, light, overlay);
	}
}
