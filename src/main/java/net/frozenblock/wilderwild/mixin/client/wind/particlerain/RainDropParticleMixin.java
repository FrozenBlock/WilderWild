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

package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pigcart.particlerain.ParticleRainClient;
import pigcart.particlerain.particle.RainDropParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(RainDropParticle.class)
public abstract class RainDropParticleMixin extends WeatherParticle {
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

	protected RainDropParticleMixin(ClientLevel level, double x, double y, double z, float gravity, SpriteSet provider) {
		super(level, x, y, z, gravity, provider);
	}

	@ModifyExpressionValue(
		method = {"tick", "<init>"},
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/particle/RainDropParticle;gravity:F",
			ordinal = 0
		),
		require = 0
	)
	public float wilderWild$windX(float original) {
		if (WWClientWindManager.shouldUseWind()) {
			return (float) (ClientWindManager.windX * ParticleRainClient.config.rainDropGravity);
		}
		return original;
	}

	@ModifyExpressionValue(
		method = {"tick", "<init>"},
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/particle/RainDropParticle;gravity:F",
			ordinal = 1
		),
		require = 0
	)
	public float wilderWild$windZ(float original) {
		if (WWClientWindManager.shouldUseWind()) {
			return (float) (ClientWindManager.windZ * ParticleRainClient.config.rainDropGravity);
		}
		return original;
	}

	@Inject(
		method = "tick",
		at = @At("TAIL"),
		require = 0
	)
	public void wilderWild$windZ(CallbackInfo info) {
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

	@ModifyArgs(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lorg/joml/AxisAngle4f;<init>(FFFF)V",
			remap = false
		),
		require = 0
	)
	public void wilderWild$applyCorrectRotation(
		Args args,
		VertexConsumer vertexConsumer, Camera camera, float tickPercentage
	) {
		float xMovement = (float) Mth.lerp(tickPercentage, this.xo, this.x);
		float zMovement = (float) Mth.lerp(tickPercentage, this.zo, this.z);
		float newWRot = (xMovement * 0.5F + zMovement * 0.5F);
		args.set(3, newWRot);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
	public void wilderWild$render(VertexConsumer buffer, Camera renderInfo, float partialTicks, CallbackInfo info) {
		info.cancel();
		float yRot = Mth.lerp(partialTicks, this.wilderWild$prevYRot, this.wilderWild$yRot) * Mth.DEG_TO_RAD;
		float xRot = Mth.lerp(partialTicks, this.wilderWild$prevXRot, this.wilderWild$xRot) * -Mth.DEG_TO_RAD;
		float cameraRotWhileVertical = ((-renderInfo.getYRot()) * (1F - Mth.lerp(partialTicks, this.wilderWild$prevRotMultiplier, this.wilderWild$rotMultiplier))) * Mth.DEG_TO_RAD;

		float x = (float)(Mth.lerp(partialTicks, this.xo, this.x));
		float y = (float)(Mth.lerp(partialTicks, this.yo, this.y));
		float z = (float)(Mth.lerp(partialTicks, this.zo, this.z));
		Vec3 relativePos = new Vec3(x, y, z).subtract(renderInfo.getPosition());
		relativePos = new Vec3(
			relativePos.x > 0 ? 1D : -1D,
			relativePos.y > 0 ? 1D : -1D,
			relativePos.z > 0 ? 1D : -1D
		);
		Vec3 particleMovement = new Vec3(this.xd, 0D, this.zd).normalize();
		float xDifference = (float) Math.abs(particleMovement.x + relativePos.z);
		xDifference = xDifference >= 1F ? 0 : (Math.abs(xDifference - 1F));
		boolean shouldDoubleClampXRot = xDifference == 0D;
		float cameraXRot = renderInfo.getXRot();
		xDifference = Mth.sin((-cameraXRot * Mth.PI) / 180F) * Mth.PI * xDifference;

		float zDifference = (float) Math.abs(particleMovement.z - relativePos.x);
		zDifference = zDifference >= 1F ? 0 : (Math.abs(zDifference - 1F));
		shouldDoubleClampXRot = shouldDoubleClampXRot || zDifference == 0D;
		zDifference = Mth.sin((-cameraXRot * Mth.PI) / 180F) * Mth.PI * zDifference;

		if (shouldDoubleClampXRot) {
			cameraXRot = Mth.clamp(cameraXRot * 1.5F, -90, 90);
		}

		float cameraRotWhileSideways = ((90F + cameraXRot) * (Mth.lerp(partialTicks, this.wilderWild$prevRotMultiplier, this.wilderWild$rotMultiplier))) * Mth.DEG_TO_RAD + xDifference + zDifference;
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
		buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z())
			.uv(l, n)
			.color(this.rCol, this.gCol, this.bCol, this.alpha)
			.uv2(light)
			.endVertex();
		buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z())
			.uv(l, m)
			.color(this.rCol, this.gCol, this.bCol, this.alpha)
			.uv2(light)
			.endVertex();
		buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z())
			.uv(k, m)
			.color(this.rCol, this.gCol, this.bCol, this.alpha)
			.uv2(light)
			.endVertex();
		buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z())
			.uv(k, n)
			.color(this.rCol, this.gCol, this.bCol, this.alpha)
			.uv2(light)
			.endVertex();
	}

}
