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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.particle.impl.WilderDripSuspendedParticleInterface;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	private int ticks;

	@Inject(method = "addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"))
	private void wilderWild$addParticle(
		ParticleOptions options, boolean force, boolean decreased, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
		CallbackInfoReturnable<Particle> info
	) {
		if (info.getReturnValue() instanceof WilderDripSuspendedParticleInterface dripParticle) {
			dripParticle.wilderWild$setUsesWind(true);
		}
	}

	@WrapOperation(
		method = "renderLevel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/LevelRenderer;renderClouds(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V"
		)
	)
	public void wilderWild$changeCloudPosition(
		LevelRenderer instance, PoseStack matrices, Matrix4f projectionMatrix, Matrix4f matrix4f, float tickDelta, double cameraX, double cameraY, double cameraZ,
		Operation<Void> operation
	) {
		boolean useWind = WWClientWindManager.shouldUseWind();

		cameraX = useWind ? (cameraX - WWClientWindManager.getCloudX(tickDelta) * 12D) - (double)(((float)this.ticks + tickDelta) * 0.03F)
			: cameraX;
		cameraY = useWind ? (float) (cameraY - Mth.clamp(WWClientWindManager.getCloudY(tickDelta) * 12D, -10D, 10D))
			: cameraY;
		cameraZ = useWind ? cameraZ - WWClientWindManager.getCloudZ(tickDelta) * 12D
			: cameraZ;

		operation.call(instance, matrices, projectionMatrix, matrix4f, tickDelta, cameraX, cameraY, cameraZ);
	}

}
