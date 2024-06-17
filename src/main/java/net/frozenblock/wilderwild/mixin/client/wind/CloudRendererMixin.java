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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WilderClientWindManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class CloudRendererMixin {

	@Unique
	private boolean wilderWild$useWind;

	@Unique
	private static boolean wilderWild$useWind() {
		return AmbienceAndMiscConfig.CLOUD_MOVEMENT && ClientWindManager.shouldUseWind();
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 4, require = 0)
	private double wilderWild$modifyXScroll(double original) {
		return (this.wilderWild$useWind = wilderWild$useWind())
			? 0
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5, require = 0)
	private double wilderWild$modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return this.wilderWild$useWind
			? original - WilderClientWindManager.getCloudX(partialTick)
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6, require = 0)
	private double wilderWild$modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return this.wilderWild$useWind
			? original + Mth.clamp(WilderClientWindManager.getCloudY(partialTick), -10D, 10D)
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7, require = 0)
	private double wilderWild$modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return this.wilderWild$useWind
			? original - WilderClientWindManager.getCloudZ(partialTick)
			: original;
	}
}
