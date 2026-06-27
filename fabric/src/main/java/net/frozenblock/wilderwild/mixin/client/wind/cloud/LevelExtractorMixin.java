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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.WWRenderStateDataKeys;
import net.frozenblock.wilderwild.wind.WWWindManagerExtension;
import net.frozenblock.wilderwild.wind.client.CloudWindPositioner;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LevelExtractor.class)
public class LevelExtractorMixin {

	@Shadow
	@Nullable
	private ClientLevel level;

	@Shadow
	@Final
	private LevelRenderState levelRenderState;

	@Inject(
		method = "extract",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/attribute/EnvironmentAttributes;CLOUD_COLOR:Lnet/minecraft/world/attribute/EnvironmentAttribute;",
			opcode = Opcodes.GETSTATIC
		)
	)
	public void wilderWild$extractCloudPositioner(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo info) {
		if (this.level == null || !WWWindManagerExtension.shouldUseWind(this.level)) {
			this.levelRenderState.setData(WWRenderStateDataKeys.CLOUD_WIND_POSITIONER, CloudWindPositioner.PASS);
		} else {
			final CloudWindPositioner.Success positioner = new CloudWindPositioner.Success(
				WWWindManagerExtension.getCloudX(level, deltaPartialTick),
				WWWindManagerExtension.getCloudZ(level, deltaPartialTick)
			);
			this.levelRenderState.setData(WWRenderStateDataKeys.CLOUD_WIND_POSITIONER, positioner);
		}
	}
}
