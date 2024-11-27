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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.impl.SculkSensorTickInterface;
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
public class SculkSensorBlockEntityRenderer<T extends SculkSensorBlockEntity> implements BlockEntityRenderer<T> {
	private static final float RAD_25 = 25F * Mth.DEG_TO_RAD;
	private static final float TENDRIL_ANGLE = 45F * Mth.DEG_TO_RAD;
	private static final float TENDRIL_ANGLE_SOUTH = 225F * Mth.DEG_TO_RAD;
	private static final RenderType ACTIVE_SENSOR_LAYER = RenderType.entityCutout(WWConstants.id("textures/entity/sculk_sensor/active.png"));
	private final ModelPart root;
	private final ModelPart tendril1;
	private final ModelPart tendril2;
	private final ModelPart tendril3;
	private final ModelPart tendril4;

	public SculkSensorBlockEntityRenderer(@NotNull Context ctx) {
		this.root = ctx.bakeLayer(WWModelLayers.SCULK_SENSOR);
		this.tendril1 = this.root.getChild("tendril1");
		this.tendril2 = this.root.getChild("tendril2");
		this.tendril3 = this.root.getChild("tendril3");
		this.tendril4 = this.root.getChild("tendril4");
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(
			"tendril1",
			CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, 0F, 8F, 8F, 0.001F),
			PartPose.offsetAndRotation(3F, 8F, 3F, 0, -TENDRIL_ANGLE, Mth.PI)
		);
		modelPartData.addOrReplaceChild(
			"tendril2",
			CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4F, -8F, 0F, 8F, 8F, 0.001F),
			PartPose.offsetAndRotation(13F, 8F, 3F, 0, TENDRIL_ANGLE, Mth.PI)
		);
		modelPartData.addOrReplaceChild(
			"tendril3",
			CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, 0F, 8F, 8F, 0.001F),
			PartPose.offsetAndRotation(13F, 8F, 13F, 0, -TENDRIL_ANGLE_SOUTH, Mth.PI)
		);
		modelPartData.addOrReplaceChild(
			"tendril4",
			CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4F, -8F, 0F, 8F, 8F, 0.001F),
			PartPose.offsetAndRotation(3F, 8F, 13F, 0, TENDRIL_ANGLE_SOUTH, Mth.PI)
		);
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
		if (WWConstants.MC_LIVE_TENDRILS && entity instanceof SculkSensorTickInterface sculkSensorTickInterface) {
			if (sculkSensorTickInterface.wilderWild$isActive()) {
				int prevTicks = sculkSensorTickInterface.wilderWild$getPrevAnimTicks();
				float rotation = sculkSensorTickInterface.wilderWild$getFacing().toYRot();
				poseStack.translate(0.5F, 0.5F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));
				poseStack.translate(-0.5F, -0.5F, -0.5F);
				float xRot = (prevTicks + partialTick * (sculkSensorTickInterface.wilderWild$getAnimTicks() - prevTicks))
					* 0.1F
					* ((float) Math.cos((sculkSensorTickInterface.wilderWild$getAge() + partialTick) * 2.25F) * RAD_25);
				this.tendril1.xRot = xRot;
				this.tendril2.xRot = xRot;
				this.tendril3.xRot = xRot;
				this.tendril4.xRot = xRot;
				this.root.render(poseStack, buffer.getBuffer(ACTIVE_SENSOR_LAYER), light, overlay);
			}
		}
	}

}
