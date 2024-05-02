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

package net.frozenblock.wilderwild.mixin.client.sodium;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import me.jellysquid.mods.sodium.client.render.immediate.CloudRenderer;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.misc.wind.WilderClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(CloudRenderer.class)
public class CloudRendererMixin {

	@Unique
	private static boolean wilderWild$useWind() {
		return AmbienceAndMiscConfig.get().wind.cloudMovement && ClientWindManager.shouldUseWind();
	}

	@ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 2, require = 0)
	private float wilderWild$modifyY(
		float original,
		@Nullable ClientLevel world,
		LocalPlayer player,
		PoseStack stack,
		Matrix4f modelViewMatrix,
		Matrix4f projectionMatrix,
		float ticks,
		float tickDelta,
		double cameraX,
		double cameraY,
		double cameraZ,
		@Share("wilderWild$useWind")LocalBooleanRef useWind
	) {
		useWind.set(wilderWild$useWind());
		return (useWind.get())
			? (float) (original + 0.33D + Mth.clamp(WilderClientWindManager.getCloudY(tickDelta) * 12, -10, 10))
			: original;
	}

	@ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 4, require = 0)
	private double wilderWild$modifyX(
		double original,
		@Nullable ClientLevel world,
		LocalPlayer player,
		PoseStack stack,
		Matrix4f modelViewMatrix,
		Matrix4f projectionMatrix,
		float ticks,
		float tickDelta,
		double cameraX,
		double cameraY,
		double cameraZ,
		@Share("wilderWild$useWind")LocalBooleanRef useWind
	) {
		return useWind.get()
			? cameraX - WilderClientWindManager.getCloudX(tickDelta) * 12
			: original;
	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 5, require = 0)
	private double wilderWild$modifyZ(
		double original,
		@Nullable ClientLevel world,
		LocalPlayer player,
		PoseStack stack,
		Matrix4f modelViewMatrix,
		Matrix4f projectionMatrix,
		float ticks,
		float tickDelta,
		double cameraX,
		double cameraY,
		double cameraZ,
		@Share("wilderWild$useWind")LocalBooleanRef useWind
	) {
		return useWind.get()
			? (cameraZ + 0.33D) - WilderClientWindManager.getCloudZ(tickDelta) * 12
			: original;
	}
}
