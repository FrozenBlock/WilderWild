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

package net.frozenblock.wilderwild.mixin.client.mesoglea.fog;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.client.MesogleaWaterFogUtil;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin {

	@Shadow
	private Level level;

	@Shadow
	public abstract FogType getFluidInCamera();

	@Shadow
	public abstract BlockPos blockPosition();

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/attribute/EnvironmentAttributeProbe;tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;)V"
		)
	)
	private void wilderWild$tickMesogleaWaterFogHandler(CallbackInfo info) {
		MesogleaWaterFogUtil.tick(this.level, this.blockPosition(), this.getFluidInCamera(), false);
	}

	@Inject(method = "reset", at = @At("HEAD"))
	private void wilderWild$resetMesogleaFog(CallbackInfo info) {
		MesogleaWaterFogUtil.reset();
	}

}
