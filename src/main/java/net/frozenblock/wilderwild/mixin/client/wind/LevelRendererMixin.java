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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.particle.impl.WilderDripSuspendedParticleInterface;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

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
			target = "Lnet/minecraft/client/renderer/LevelRenderer;addCloudsPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/client/CloudStatus;Lnet/minecraft/world/phys/Vec3;FIF)V"
		)
	)
	public void wilderWild$changeCloudPosition(
		LevelRenderer instance,
		FrameGraphBuilder frameGraphBuilder,
		Matrix4f matrix4f,
		Matrix4f matrix4f2,
		CloudStatus cloudStatus,
		Vec3 vec3,
		float ticks,
		int cloudColor,
		float cloudHeight,
		Operation<Void> original,
		@Local(ordinal = 0) float partialTick
	) {
		if (WWClientWindManager.shouldUseWind()) {
			double cameraX = vec3.x;
			double cameraY = vec3.y;
			double cameraZ = vec3.z;
			cameraX =  (cameraX - WWClientWindManager.getCloudX(partialTick) * 12D) - (double)((ticks) * 0.03F);
			cloudHeight = (float) (cloudHeight - Mth.clamp(WWClientWindManager.getCloudY(partialTick) * 12D, -10D, 10D));
			cameraZ = cameraZ - WWClientWindManager.getCloudZ(partialTick) * 12D;
			vec3 = new Vec3(cameraX, cameraY, cameraZ);
		}

		original.call(instance, frameGraphBuilder, matrix4f, matrix4f2, cloudStatus, vec3, ticks, cloudColor, cloudHeight);
	}

}
