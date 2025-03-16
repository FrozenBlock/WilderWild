/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.config.ModConfig;
import pigcart.particlerain.particle.RainParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(RainParticle.class)
public abstract class RainParticleMixin extends WeatherParticle {
	@Unique
	private static final Vector3f WILDER_WILD$NORMALIZED_QUAT_VECTOR = new Vector3f(0.5F, 0.5F, 0.5F).normalize();
	@Unique
	private float wilderWild$prevYRot;
	@Unique
	private float wilderWild$yRot;
	@Unique
	private float wilderWild$prevXRot;
	@Unique
	private float wilderWild$xRot;
	@Unique
	private float wilderWild$prevRotMultiplier;
	@Unique
	private float wilderWild$rotMultiplier;

	protected RainParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/config/ModConfig$RainOptions;stormWindStrength:F"
		),
		require = 0
	)
	public float wilderWild$modifyStormWindStrength(float original) {
		if (WWClientWindManager.shouldUseWind()) return (float) ClientWindManager.windX;
		return original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/config/ModConfig$RainOptions;windStrength:F"
		),
		require = 0
	)
	public float wilderWild$modifyWindStrength(float original) {
		if (WWClientWindManager.shouldUseWind()) return (float) ClientWindManager.windX;
		return original;
	}

	@Inject(
		method = "<init>",
		at = @At(value = "TAIL"),
		require = 0
	)
	public void wilderWild$modifyWindZ(ClientLevel level, double x, double y, double z, CallbackInfo info) {
		if (WWClientWindManager.shouldUseWind()) this.zd = this.gravity * (float) ClientWindManager.windZ;
		if (ModConfig.CONFIG.compat.yLevelWindAdjustment) {
			this.zd *= yLevelWindAdjustment(y);
		}
	}

	@Inject(
		method = "tick",
		at = @At("TAIL"),
		require = 0
	)
	public void wilderWild$captureRotationData(CallbackInfo info) {
		this.wilderWild$prevYRot = this.wilderWild$yRot;
		this.wilderWild$prevXRot = this.wilderWild$xRot;

		double horizontalDistance = Math.sqrt((this.xd * this.xd) + (this.zd * this.zd));

		double newYRot = (Mth.atan2(this.xd, this.zd)) * Mth.RAD_TO_DEG;
		double newXRot = (Mth.atan2(horizontalDistance, this.yd)) * Mth.RAD_TO_DEG;

		if (Math.abs(newYRot - this.wilderWild$prevYRot) > 180D) {
			newYRot += 360D;
		}

		if (Math.abs(newXRot - this.wilderWild$prevXRot) > 180D) {
			newXRot += 360D;
		}

		double newYRotDifference = newYRot - this.wilderWild$yRot;
		double newXRotDifference = newXRot - this.wilderWild$xRot;

		this.wilderWild$yRot += (float)newYRotDifference * 0.25F;
		this.wilderWild$xRot += (float)newXRotDifference * 0.25F;

		if (this.wilderWild$yRot > 360F) {
			this.wilderWild$yRot -= 360F;
			this.wilderWild$prevYRot -= 360F;
		} else if (this.wilderWild$yRot < 0F) {
			this.wilderWild$yRot += 360F;
			this.wilderWild$prevYRot += 360F;
		}

		if (this.wilderWild$xRot > 360F) {
			this.wilderWild$xRot -= 360F;
			this.wilderWild$prevXRot -= 360F;
		} else if (this.wilderWild$xRot < 0F) {
			this.wilderWild$xRot += 360F;
			this.wilderWild$prevXRot += 360F;
		}

		this.wilderWild$prevRotMultiplier = this.wilderWild$rotMultiplier;
		this.wilderWild$rotMultiplier = (float) Math.sin((this.wilderWild$xRot * Mth.PI) / 180F);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
	public void wilderWild$renderWithNewRotation(VertexConsumer buffer, Camera renderInfo, float partialTicks, CallbackInfo info) {
		info.cancel();
		float yRot = Mth.lerp(partialTicks, this.wilderWild$prevYRot, this.wilderWild$yRot) * Mth.DEG_TO_RAD;
		float xRot = Mth.lerp(partialTicks, this.wilderWild$prevXRot, this.wilderWild$xRot) * -Mth.DEG_TO_RAD;
		float cameraRotWhileVertical = ((-renderInfo.getYRot()) * (1F - Mth.lerp(partialTicks, this.wilderWild$prevRotMultiplier, this.wilderWild$rotMultiplier))) * Mth.DEG_TO_RAD;
		float cameraXRot = renderInfo.getXRot();

		float x = (float)(Mth.lerp(partialTicks, this.xo, this.x));
		float y = (float)(Mth.lerp(partialTicks, this.yo, this.y));
		float z = (float)(Mth.lerp(partialTicks, this.zo, this.z));
		Vec3 particlePos = new Vec3(x, y, z);

		double particleRotation = AdvancedMath.getAngleFromOriginXZ(new Vec3(this.xd, 0D, this.zd));
		double angleToParticle = AdvancedMath.getAngleBetweenXZ(particlePos, renderInfo.getPosition());
		double relativeParticleAngle = (360D + (angleToParticle - particleRotation)) % 360D;

		float fixedRotation = 90F + cameraXRot;
		if (relativeParticleAngle > 0D && relativeParticleAngle < 180D) fixedRotation *= -1F;

		float cameraRotWhileSideways = ((fixedRotation) * (Mth.lerp(partialTicks, this.wilderWild$prevRotMultiplier, this.wilderWild$rotMultiplier))) * Mth.DEG_TO_RAD;

		this.wilderWild$renderParticle(buffer, renderInfo, partialTicks, false, transforms -> transforms.rotateY(yRot)
			.rotateX(-xRot)
			.rotateY(cameraRotWhileSideways)
			.rotateY(cameraRotWhileVertical)
		);
		this.wilderWild$renderParticle(buffer, renderInfo, partialTicks, true, transforms -> transforms.rotateY((float) -Math.PI + yRot)
			.rotateX(xRot)
			.rotateY(cameraRotWhileSideways)
			.rotateY(cameraRotWhileVertical));
	}

	@Unique
	private void wilderWild$renderParticle(VertexConsumer buffer, @NotNull Camera renderInfo, float partialTicks, boolean flipped, @NotNull Consumer<Quaternionf> quaternionConsumer) {
		Vec3 vec3 = renderInfo.getPosition();
		float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
		float g = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
		float h = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
		Quaternionf quaternionf = new Quaternionf().setAngleAxis(0F, WILDER_WILD$NORMALIZED_QUAT_VECTOR.x(), WILDER_WILD$NORMALIZED_QUAT_VECTOR.y(), WILDER_WILD$NORMALIZED_QUAT_VECTOR.z());
		quaternionConsumer.accept(quaternionf);
		Vector3f[] vector3fs = new Vector3f[]{
			new Vector3f(-1F, -1F, 0F), new Vector3f(-1F, 1F, 0F), new Vector3f(1F, 1F, 0F), new Vector3f(1F, -1F, 0F)
		};
		float i = this.getQuadSize(partialTicks);

		for(int j = 0; j < 4; ++j) {
			Vector3f vector3f2 = vector3fs[j];
			vector3f2.rotate(quaternionf);
			vector3f2.mul(i);
			vector3f2.add(f, g, h);
		}

		float k = !flipped ? this.getU0() : this.getU1();
		float l = !flipped ? this.getU1() : this.getU0();
		float m = this.getV0();
		float n = this.getV1();
		int light = this.getLightColor(partialTicks);
		buffer.addVertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z())
			.setUv(l, n)
			.setColor(this.rCol, this.gCol, this.bCol, this.alpha)
			.setLight(light);
		buffer.addVertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z())
			.setUv(l, m)
			.setColor(this.rCol, this.gCol, this.bCol, this.alpha)
			.setLight(light);
		buffer.addVertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z())
			.setUv(k, m)
			.setColor(this.rCol, this.gCol, this.bCol, this.alpha)
			.setLight(light);
		buffer.addVertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z())
			.setUv(k, n)
			.setColor(this.rCol, this.gCol, this.bCol, this.alpha)
			.setLight(light);
	}

}
