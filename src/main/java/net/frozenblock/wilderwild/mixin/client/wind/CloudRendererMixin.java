/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class CloudRendererMixin {

	@Unique
	private float wilderWild$cloudHeight;

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 1)
	private float wilderWild$getCloudHeight(float original) {
		this.wilderWild$cloudHeight = original;
		return original;
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5)
	private double wilderWild$modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
				? (camX / 12.0) - ClientWindManager.getCloudX(partialTick)
				: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6)
	private double wilderWild$modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
				? this.wilderWild$cloudHeight - camY + 0.33D + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10, 10)
				: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7)
	private double wilderWild$modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
				? (camZ / 12.0D + 0.33D) - ClientWindManager.getCloudZ(partialTick)
				: original;
	}
}
