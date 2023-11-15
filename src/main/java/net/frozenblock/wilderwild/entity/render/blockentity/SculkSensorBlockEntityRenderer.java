/*
 * Copyright 2023 FrozenBlock
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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.SculkSensorTickInterface;
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
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SculkSensorBlockEntityRenderer<T extends SculkSensorBlockEntity> implements BlockEntityRenderer<T> {
	private static final float PI = (float) Math.PI;
	private static final float RADIANS_25 = 25F * (PI / 180F);
	public static final Material INACTIVE_MATERIAL = new Material(TextureAtlas.LOCATION_BLOCKS, WilderSharedConstants.id("entity/sculk_sensor/inactive.png"));
	public static final Material ACTIVE_MATERIAL = new Material(TextureAtlas.LOCATION_BLOCKS, WilderSharedConstants.id("entity/sculk_sensor/active.png"));
	private final ModelPart root;
	private final ModelPart ne;
	private final ModelPart se;
	private final ModelPart nw;
	private final ModelPart sw;

	public SculkSensorBlockEntityRenderer(@NotNull Context ctx) {
		this.root = ctx.bakeLayer(WilderWildClient.SCULK_SENSOR);
		this.ne = this.root.getChild("ne");
		this.se = this.root.getChild("se");
		this.nw = this.root.getChild("nw");
		this.sw = this.root.getChild("sw");
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild("ne", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), PartPose.offsetAndRotation(3.0F, 8.0F, 3.0F, 0, -0.7854F, PI));
		modelPartData.addOrReplaceChild("se", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), PartPose.offsetAndRotation(3.0F, 8.0F, 13.0F, 0, 0.7854F, PI));
		modelPartData.addOrReplaceChild("nw", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), PartPose.offsetAndRotation(13.0F, 8.0F, 13.0F, 0, -0.7854F, PI));
		modelPartData.addOrReplaceChild("sw", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), PartPose.offsetAndRotation(13.0F, 8.0F, 3.0F, 0, 0.7854F, PI));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void render(@NotNull T sculkSensorBlockEntity, float partialTick, @NotNull PoseStack matrices, @NotNull MultiBufferSource buffer, int light, int overlay) {
		if (WilderSharedConstants.MC_LIVE_TENDRILS) {
			SculkSensorTickInterface sculkSensorTickInterface = ((SculkSensorTickInterface) sculkSensorBlockEntity);
			VertexConsumer vertexConsumer;
			if (sculkSensorTickInterface.wilderWild$isActive()) {
				int prevTicks = sculkSensorTickInterface.wilderWild$getPrevAnimTicks();
				float xRot = (prevTicks + partialTick * (sculkSensorTickInterface.wilderWild$getAnimTicks() - prevTicks)) * 0.1F * ((float) Math.cos((sculkSensorTickInterface.wilderWild$getAge() + partialTick) * 2.25F) * RADIANS_25);
				this.ne.xRot = xRot;
				this.se.xRot = -xRot;
				this.nw.xRot = -xRot;
				this.sw.xRot = xRot;
				vertexConsumer = ACTIVE_MATERIAL.buffer(buffer, RenderType::entityCutout);
			} else {
				this.ne.xRot = 0;
				this.se.xRot = 0;
				this.nw.xRot = 0;
				this.sw.xRot = 0;
				vertexConsumer = INACTIVE_MATERIAL.buffer(buffer, RenderType::entityCutout);
			}
			this.root.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

}
