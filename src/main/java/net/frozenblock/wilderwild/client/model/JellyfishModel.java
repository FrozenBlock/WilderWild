/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.model;

import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenLibRenderTypes;
import net.frozenblock.wilderwild.client.renderer.entity.state.JellyfishRenderState;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishModel extends EntityModel<JellyfishRenderState> {
	private static final int JELLYFISH_TENTACLES = WWEntityConfig.get().jellyfish.jellyfishTentacles;
	private static final float EIGHT_PI = -8F * Mth.DEG_TO_RAD;
	private final ModelPart bone;
	private final ModelPart body;
	private final ModelPart tentacleBase;
	private final ModelPart[] tentacles = new ModelPart[JELLYFISH_TENTACLES];
	private final ModelPart[] planeTentacles = new ModelPart[JELLYFISH_TENTACLES];

	public JellyfishModel(@NotNull ModelPart root) {
		super(root, FrozenLibRenderTypes::entityTranslucentEmissiveFixed);
		this.bone = root.getChild("bone");
		this.body = this.bone.getChild("body");
		this.tentacleBase = this.bone.getChild("tentacleBase");
		Arrays.setAll(this.tentacles, i -> tentacleBase.getChild(createTentacleName(i, false)));
		Arrays.setAll(this.planeTentacles, i -> tentacleBase.getChild(createTentacleName(i, true)));
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.ZERO);

		bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -2F, -4F, 8F, 5F, 8F)
			.texOffs(4, 13).addBox(-3F, -1F, -3F, 6F, 3F, 6F), PartPose.ZERO);

		PartDefinition tentacleBase = bone.addOrReplaceChild("tentacleBase", CubeListBuilder.create(), PartPose.ZERO);
		makeTentacles(tentacleBase, JELLYFISH_TENTACLES);
		makePlaneTentacles(tentacleBase, JELLYFISH_TENTACLES);

		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	private static void makeTentacles(PartDefinition partDefinition, int amount) {
		CubeListBuilder tentacle = CubeListBuilder.create().texOffs(0, 13)
			.addBox(-0.5F, 0F, 0F, 1F, 10F, 1F);
		for (int i = 0; i < amount; ++i) {
			float rot = i * Mth.PI * 2F /  amount;
			partDefinition.addOrReplaceChild(createTentacleName(i, false), tentacle, PartPose.offsetAndRotation(
					(float) Math.cos(rot) * 2.5F,
					0F,
                    (float) Math.sin(rot) * 2.5F,
					0F,
                    i * Mth.PI * -2F / amount + Mth.HALF_PI,
					0F
				)
			);
		}
	}

	private static void makePlaneTentacles(PartDefinition partDefinition, int amount) {
		CubeListBuilder tentacle = CubeListBuilder.create().texOffs(0, 14)
			.addBox(-0.5F, 0F, 1F, 1F, 10F, 0F, new CubeDeformation(0.001F));
		CubeListBuilder altTentacle = CubeListBuilder.create().texOffs(2, 14)
			.addBox(-0.5F, 0F, 1F, 1F, 10F, 0F, new CubeDeformation(0.001F));
		for (int i = 0; i < amount; ++i) {
			float rot = i * Mth.PI * 2F /  amount;
			partDefinition.addOrReplaceChild(createTentacleName(i, true), i % 2 == 0 ? altTentacle : tentacle, PartPose.offsetAndRotation(
					(float) Math.cos(rot) * 2.75F,
					0F,
					(float) Math.sin(rot) * 2.75F,
					0F,
					i * Mth.PI * -2F / amount + Mth.HALF_PI,
					0F
				)
			);
		}
	}

	@NotNull
	private static String createTentacleName(int number, boolean plane) {
		String tentacle = !plane ? "tentacle" : "tentacle_plane";
		return tentacle + number;
	}

	@Override
	public void setupAnim(@NotNull JellyfishRenderState renderState) {
		super.setupAnim(renderState);
		this.bone.xRot = renderState.jellyXRot;
		this.tentacleBase.xRot = renderState.jellyXRot - renderState.tentXRot;

		float walkPos = renderState.walkAnimationPos;
		float walkSpeed = renderState.walkAnimationSpeed;

		float animation = walkPos * 2F;
		float movementDelta = Math.min(walkSpeed * 26.6666667F, 1F);

		//SQUASH & STRETCH
		float sin = (float) -Math.sin(animation);
		float sinIdle = (float) (Math.sin(renderState.ageInTicks * 0.1F) * 0.2F);
		float squashStretch = 1F + (-sin * 0.25F);
		float squash = Mth.lerp(movementDelta, sinIdle + 1F, squashStretch);

		this.body.xScale = squash;
		this.body.zScale = squash;
		this.body.yScale = Mth.lerp(movementDelta, -sinIdle + 1F, 1.25F + (sin * 0.75F));

		this.body.y = Mth.lerp(movementDelta, 0F, 3.5F - (squashStretch * 3.5F));
		this.tentacleBase.y = Mth.lerp(movementDelta, (-sinIdle * 2F) + 1.8F, (6F - (squashStretch * 5F)) * 1.5F) * 1.5F;

		float tentRot = -Mth.rotLerp(
			movementDelta,
			(float) (-Math.sin((renderState.ageInTicks - 10) * 0.1F) * 0.2F) + EIGHT_PI,
			(float) (-Math.sin(animation + 5F) * 20F - 7.5F) * Mth.DEG_TO_RAD
		);
		ModelPart[] visibleTentacles = !WWEntityConfig.Client.JELLYFISH_PLANE_TENTACLES ? this.tentacles : this.planeTentacles;
		ModelPart[] invisibleTentacles = !WWEntityConfig.Client.JELLYFISH_PLANE_TENTACLES ? this.planeTentacles : this.tentacles;

		for (ModelPart modelPart : visibleTentacles) {
			modelPart.visible = true;
			modelPart.x *= squash;
			modelPart.z *= squash;
			modelPart.xRot = tentRot;
		}

		for (ModelPart modelPart : invisibleTentacles) {
			modelPart.visible = false;
		}
	}
}
