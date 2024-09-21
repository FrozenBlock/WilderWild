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
	private static final RenderType SENSOR_LAYER = RenderType.entityCutout(WWConstants.id("textures/entity/sculk_sensor/inactive.png"));
	private static final RenderType ACTIVE_SENSOR_LAYER = RenderType.entityCutout(WWConstants.id("textures/entity/sculk_sensor/active.png"));
	private final ModelPart root;
	private final ModelPart ne;
	private final ModelPart se;
	private final ModelPart nw;
	private final ModelPart sw;

	public SculkSensorBlockEntityRenderer(@NotNull Context ctx) {
		this.root = ctx.bakeLayer(WWModelLayers.SCULK_SENSOR);
		this.ne = this.root.getChild("ne");
		this.se = this.root.getChild("se");
		this.nw = this.root.getChild("nw");
		this.sw = this.root.getChild("sw");
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		CubeListBuilder tendril = CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, 0F, 8F, 8F, 0.002F);
		modelPartData.addOrReplaceChild("ne", tendril, PartPose.offsetAndRotation(3F, 8F, 3F, 0, -0.7854F, Mth.PI));
		modelPartData.addOrReplaceChild("se", tendril, PartPose.offsetAndRotation(3F, 8F, 13F, 0, 0.7854F, Mth.PI));
		modelPartData.addOrReplaceChild("nw", tendril, PartPose.offsetAndRotation(13F, 8F, 13F, 0, -0.7854F, Mth.PI));
		modelPartData.addOrReplaceChild("sw", tendril, PartPose.offsetAndRotation(13F, 8F, 3F, 0, 0.7854F, Mth.PI));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
		if (WWConstants.MC_LIVE_TENDRILS) {
			SculkSensorTickInterface tickInterface = ((SculkSensorTickInterface) entity);
			if (tickInterface.wilderWild$isActive()) {
				int prevTicks = tickInterface.wilderWild$getPrevAnimTicks();
				float xRot = (prevTicks + partialTick * (tickInterface.wilderWild$getAnimTicks() - prevTicks)) * 0.1F * ((float) Math.cos((tickInterface.wilderWild$getAge() + partialTick) * 2.25F) * RAD_25);
				this.ne.xRot = xRot;
				this.se.xRot = -xRot;
				this.nw.xRot = -xRot;
				this.sw.xRot = xRot;
				this.root.render(poseStack, buffer.getBuffer(ACTIVE_SENSOR_LAYER), light, overlay, 1F, 1F, 1F, 1F);
			} else {
				this.ne.xRot = 0;
				this.se.xRot = 0;
				this.nw.xRot = 0;
				this.sw.xRot = 0;
				this.root.render(poseStack, buffer.getBuffer(SENSOR_LAYER), light, overlay, 1F, 1F, 1F, 1F);
			}
		}
	}

}
