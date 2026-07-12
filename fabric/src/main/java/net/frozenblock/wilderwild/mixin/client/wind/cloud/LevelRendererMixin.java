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

package net.frozenblock.wilderwild.mixin.client.wind.cloud;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import net.frozenblock.lib.renderer.FrozenLibRenderState;
import net.frozenblock.wilderwild.client.WWRenderStateDataKeys;
import net.frozenblock.wilderwild.wind.client.CloudWindPositioner;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(LevelRenderer.class)
public class LevelRendererMixin { // in common mixins.json

	@Shadow
	@Final
	private LevelRenderState levelRenderState;

	/**
	 * NeoForge uses a modified method
	 */
	@WrapOperation(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/LevelRenderer;addCloudsPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/CloudStatus;Lnet/minecraft/world/phys/Vec3;JFIFI)V"
		)
	)
	public void wilderWild$changeCloudPosition(
		LevelRenderer instance,
		FrameGraphBuilder frame,
		CloudStatus cloudStatus,
		Vec3 cameraPosition,
		long gameTime,
		float partialTicks,
		int cloudColor,
		float cloudHeight,
		int cloudRange,
		Operation<Void> original
	) {
		final CloudWindPositioner positioner = ((FrozenLibRenderState) this.levelRenderState).frozenLib$getDataOrDefault(
			WWRenderStateDataKeys.CLOUD_WIND_POSITIONER,
			CloudWindPositioner.PASS
		);
		if (positioner instanceof CloudWindPositioner.Success success) {
			cameraPosition = new Vec3(
				success.modifyCloudX(cameraPosition.x),
				cameraPosition.y,
				success.modifyCloudZ(cameraPosition.z)
			);
			gameTime = 0L;
			partialTicks = 0L;
		}

		original.call(instance, frame, cloudStatus, cameraPosition, gameTime, partialTicks, cloudColor, cloudHeight, cloudRange);
	}
}
