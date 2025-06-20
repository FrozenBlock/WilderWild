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
import net.frozenblock.wilderwild.block.entity.impl.SculkSensorInterface;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SculkSensorRenderer<T extends SculkSensorBlockEntity> implements BlockEntityRenderer<T> {
	private static final float RAD_25 = 25F * Mth.DEG_TO_RAD;
	private static final float TENDRIL_ANGLE = 45F * Mth.DEG_TO_RAD;
	private static final float TENDRIL_ANGLE_SOUTH = 225F * Mth.DEG_TO_RAD;
	private static final RenderType ACTIVE_SENSOR_LAYER = RenderType.entityCutout(WWConstants.id("textures/entity/sculk_sensor/active.png"));
	private final ModelPart root;
	private final ModelPart tendril1;
	private final ModelPart tendril2;
	private final ModelPart tendril3;
	private final ModelPart tendril4;

	public SculkSensorRenderer(@NotNull Context ctx) {
		this.root = ctx.bakeLayer(WWModelLayers.SCULK_SENSOR);
		this.tendril1 = this.root.getChild("tendril1");
		this.tendril2 = this.root.getChild("tendril2");
		this.tendril3 = this.root.getChild("tendril3");
		this.tendril4 = this.root.getChild("tendril4");
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();
		makeTendril(root, "tendril1", false, 3F, 3F, -TENDRIL_ANGLE);
		makeTendril(root, "tendril2", true, 13F, 3F, TENDRIL_ANGLE);
		makeTendril(root, "tendril3", false, 13F, 13F, -TENDRIL_ANGLE_SOUTH);
		makeTendril(root, "tendril4", true, 3F, 13F,  TENDRIL_ANGLE_SOUTH);
		return LayerDefinition.create(modelData, 16, 16);
	}

	private static void makeTendril(
		PartDefinition root, String name, boolean mirror, float xPos, float zPos, float rot
	) {
		CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(0, 0);
		if (mirror) cubeListBuilder.mirror();
		cubeListBuilder.addBox(-4F, -8F, 0F, 8F, 8F, 0.001F);

		root.addOrReplaceChild(
			name,
			cubeListBuilder,
			PartPose.offsetAndRotation(xPos, 8F, zPos, 0F, rot, Mth.PI)
		);
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
		if (!WWConstants.MC_LIVE_TENDRILS || !(entity instanceof SculkSensorInterface sculkSensorInterface) || !sculkSensorInterface.wilderWild$isActive()) return;
		int prevTicks = sculkSensorInterface.wilderWild$getPrevAnimTicks();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-sculkSensorInterface.wilderWild$getFacing().toYRot()));
		poseStack.translate(-0.5F, -0.5F, -0.5F);
		float xRot = (prevTicks + partialTick * (sculkSensorInterface.wilderWild$getAnimTicks() - prevTicks))
			* 0.1F
			* ((float) Math.cos((sculkSensorInterface.wilderWild$getAge() + partialTick) * 2.25F) * RAD_25);
		this.tendril1.xRot = xRot;
		this.tendril2.xRot = xRot;
		this.tendril3.xRot = xRot;
		this.tendril4.xRot = xRot;
		this.root.render(poseStack, buffer.getBuffer(ACTIVE_SENSOR_LAYER), light, overlay);
	}

}
