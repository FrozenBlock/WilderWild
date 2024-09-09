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

package net.frozenblock.wilderwild.entity.render.model;

import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.render.renderer.state.JellyfishRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishModel extends EntityModel<JellyfishRenderState> {
	private static final int JELLYFISH_TENTACLES = WWEntityConfig.get().jellyfish.jellyfishTentacles;
	private static final float EIGHT_PI = -8F * Mth.DEG_TO_RAD;
	private final ModelPart body;
	private final ModelPart tentacleBase;
	private final ModelPart[] tentacles = new ModelPart[JELLYFISH_TENTACLES];

	public JellyfishModel(@NotNull ModelPart root) {
		super(root, FrozenRenderType::entityTranslucentEmissiveFixed);
		ModelPart bone = root.getChild("bone");
		this.body = bone.getChild("body");
		this.tentacleBase = this.body.getChild("tentacleBase");
		Arrays.setAll(this.tentacles, i -> tentacleBase.getChild(createTentacleName(i)));
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -2F, -4F, 8F, 5F, 8F)
			.texOffs(4, 13).addBox(-3F, -1F, -3F, 6F, 3F, 6F), PartPose.ZERO);

		PartDefinition tentacleBase = body.addOrReplaceChild("tentacleBase", CubeListBuilder.create(), PartPose.ZERO);
		makeTentacles(tentacleBase, JELLYFISH_TENTACLES);

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	private static void makeTentacles(PartDefinition partDefinition, int amount) {
		CubeListBuilder tentacle = CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0F, 0F, 1F, 10F, 1F);
		for (int i = 0; i < amount; ++i) {
			float rot = i * Mth.PI * 2F /  amount;
			partDefinition.addOrReplaceChild(createTentacleName(i), tentacle, PartPose.offsetAndRotation(
					(float) Math.cos(rot) * 2.5F,
					0F,
                    (float) Math.sin(rot) * 2.5F,
					0F,
                    i * Mth.PI * -2F / amount + 1.5707963267948966F,
					0F
				)
			);
		}
	}

	@NotNull
	private static String createTentacleName(int i) {
		return "tentacle" + i;
	}

	private static float fasterRotLerp(float f, float g, float h) {
		float angleCalc = (h - g) % 360F;
		if (angleCalc >= 180F) {
			angleCalc -= 360F;
		}

		if (angleCalc < -180F) {
			angleCalc += 360F;
		}

		return g + f * angleCalc;
	}

	@Override
	public void setupAnim(@NotNull JellyfishRenderState renderState) {
		super.setupAnim(renderState);
		this.body.xRot = renderState.jellyXRot;
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
		float stretch = Mth.lerp(movementDelta, -sinIdle + 1F, 1.25F + (sin * 0.75F));

		this.body.xScale = squash;
		this.body.zScale = squash;
		this.body.yScale = stretch;

		float antiSquash = 1F / squash;
		this.tentacleBase.xScale = antiSquash;
		this.tentacleBase.zScale = antiSquash;
		this.tentacleBase.yScale = 1F / stretch;


		this.body.y = Mth.lerp(movementDelta, 0F, 3.5F - (squashStretch * 3.5F));
		this.tentacleBase.y -= this.body.y;
		this.tentacleBase.y += Mth.lerp(movementDelta, (-sinIdle * 2F) + 1.8F, (6F - (squashStretch * 5F)) * 1.5F);
		float tentRot = -fasterRotLerp(
			movementDelta,
			(float) (-Math.sin((renderState.ageInTicks - 10) * 0.1F) * 0.2F) + EIGHT_PI,
			(float) (-Math.sin(animation + 5F) * 20F - 7.5F) * Mth.DEG_TO_RAD
		);

		for (ModelPart modelPart : this.tentacles) {
			PartPose initialPose = modelPart.getInitialPose();
			modelPart.x = initialPose.x() * squash;
			modelPart.z = initialPose.z() * squash;
			modelPart.xRot = tentRot;
		}
	}
}
