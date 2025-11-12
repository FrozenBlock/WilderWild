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

package net.frozenblock.wilderwild.mixin.client.wind.cloud;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@WrapOperation(
		method = "renderLevel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/LevelRenderer;addCloudsPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/CloudStatus;Lnet/minecraft/world/phys/Vec3;JFIF)V"
		)
	)
	public void wilderWild$changeCloudPosition(
		LevelRenderer instance,
		FrameGraphBuilder frameGraphBuilder,
		CloudStatus cloudStatus,
		Vec3 vec3,
		long gameTime,
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
			cameraZ = cameraZ - WWClientWindManager.getCloudZ(partialTick) * 12D;
			vec3 = new Vec3(cameraX, cameraY, cameraZ);
		}

		original.call(instance, frameGraphBuilder, cloudStatus, vec3, gameTime, ticks, cloudColor, cloudHeight);
	}

}
