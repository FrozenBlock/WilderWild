/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.wilderwild.client.renderer.entity.state.HarpoonRenderState;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.phys.Vec3;

@ClientOnly
public class HarpoonRenderer extends ArrowRenderer<Harpoon, HarpoonRenderState> {
	public static final Identifier HARPOON_LOCATION = Identifier.withDefaultNamespace("textures/entity/projectiles/arrow_spectral.png");

	public HarpoonRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	// TODO: expand culling bounding box for third-person players

	@Override
	protected Identifier getTextureLocation(HarpoonRenderState state) {
		return HARPOON_LOCATION;
	}

	public static HumanoidArm getHoldingArm(LivingEntity owner) {
		// TODO: eventually, check contents of the crossbow
		return owner.getMainHandItem().getItem() instanceof CrossbowItem ? owner.getMainArm() : owner.getMainArm().getOpposite();
	}

	private Vec3 getOwnerHandPos(LivingEntity owner, float swing, float partialTicks) {
		final int invert = getHoldingArm(owner) == HumanoidArm.RIGHT ? 1 : -1;
		if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && owner == Minecraft.getInstance().player) {
			float fov = this.entityRenderDispatcher.options.fov().get().intValue();
			final double viewBobbingScale = 960D / fov;
			final Vec3 viewVec = this.entityRenderDispatcher
				.camera
				.getNearPlane(fov)
				.getPointOnPlane(invert * 0.525F, -0.1F)
				.scale(viewBobbingScale)
				.yRot(swing * 0.5F)
				.xRot(-swing * 0.7F);
			return owner.getEyePosition(partialTicks).add(viewVec);
		} else {
			// TODO: check how this appears on skeletons and zombies
			final float ownerYRot = Mth.lerp(partialTicks, owner.yBodyRotO, owner.yBodyRot) * Mth.DEG_TO_RAD;
			final double sin = Mth.sin(ownerYRot);
			final double cos = Mth.cos(ownerYRot);
			final float ownerScale = owner.getScale();
			final double rightOffset = invert * 0.35D * ownerScale;
			final double forwardOffset = 0.8D * ownerScale;
			final float yOffset = owner.isCrouching() ? -0.1875F : 0F;
			return owner.getEyePosition(partialTicks)
				.add(
					-cos * rightOffset - sin * forwardOffset,
					yOffset - 0.45 * ownerScale,
					-sin * rightOffset + cos * forwardOffset
				);
		}
	}

	@Override
	public HarpoonRenderState createRenderState() {
		return new HarpoonRenderState();
	}

	@Override
	public boolean shouldRender(Harpoon harpoon, Frustum culler, double camX, double camY, double camZ, float partialTicks) {
		return super.shouldRender(harpoon, culler, camX, camY, camZ, partialTicks) && harpoon.getOwner() != null;
	}

	@Override
	public void submit(HarpoonRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		super.submit(state, poseStack, submitNodeCollector, camera);

		poseStack.pushPose();
		float xa = (float)state.lineOriginOffset.x;
		float ya = (float)state.lineOriginOffset.y;
		float za = (float)state.lineOriginOffset.z;
		float width = Minecraft.getInstance().gameRenderer.gameRenderState().windowRenderState.appropriateLineWidth;
		submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.lines(), (pose, buffer) -> {
			final int steps = 16;
			for (int step = 0; step < steps; step++) {
				float a0 = fraction(step, steps);
				float a1 = fraction(step + 1, steps);
				stringVertex(xa, ya, za, buffer, pose, a0, a1, width);
				stringVertex(xa, ya, za, buffer, pose, a1, a0, width);
			}
		});
		poseStack.popPose();
	}

	@Override
	public void extractRenderState(Harpoon harpoon, HarpoonRenderState state, float partialTicks) {
		super.extractRenderState(harpoon, state, partialTicks);

		final Entity owner = harpoon.getOwner();
		if (owner == null) {
			state.lineOriginOffset = Vec3.ZERO;
			return;
		}

		final Vec3 harpoonPos = harpoon.getPosition(partialTicks).add(0D, 0.2D, 0D);
		final Vec3 ownerPos;
		if (!(owner instanceof LivingEntity livingOwner)) {
			ownerPos = owner.getEyePosition();
		} else {
			// TODO: check how this appears on skeletons and zombies
			final float swing = livingOwner.getSwingAnimation(partialTicks);
			final float swing2 = Mth.sin(Mth.sqrt(swing) * Mth.PI);
			ownerPos = this.getOwnerHandPos(livingOwner, swing2, partialTicks);
		}

		state.lineOriginOffset = ownerPos.subtract(harpoonPos);
	}

	private static float fraction(int i, int steps) {
		return (float)i / steps;
	}

	private static void stringVertex(float xa, float ya, float za, VertexConsumer stringBuffer, PoseStack.Pose stringPose, float aa, float nexta, float width) {
		float x = xa * aa;
		float y = ya * (aa * aa + aa) * 0.5F + 0.25F;
		float z = za * aa;
		float nx = xa * nexta - x;
		float ny = ya * (nexta * nexta + nexta) * 0.5F + 0.25F - y;
		float nz = za * nexta - z;
		float length = Mth.sqrt(nx * nx + ny * ny + nz * nz);
		nx /= length;
		ny /= length;
		nz /= length;
		stringBuffer.addVertex(stringPose, x, y, z).setColor(-16777216).setNormal(stringPose, nx, ny, nz).setLineWidth(width);
	}
}
