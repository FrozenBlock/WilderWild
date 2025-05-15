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

package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.config.ModConfig;
import pigcart.particlerain.particle.ShrubParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(ShrubParticle.class)
public abstract class ShrubParticleMixin extends WeatherParticle {

	protected ShrubParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@Inject(
		method = "<init>",
		at = @At("TAIL"),
		require = 0
	)
	private void wilderWild$modifyXInit(ClientLevel level, double x, double y, double z, CallbackInfo info) {
		if (WWClientWindManager.shouldUseWind()) {
			this.xd = ClientWindManager.windX * ModConfig.CONFIG.shrub.windStrength;
			this.zd = ClientWindManager.windZ * ModConfig.CONFIG.shrub.windStrength;
		}
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/particle/ShrubParticle;zd:D",
			shift = At.Shift.AFTER
		),
		remap = false,
		require = 0
	)
	private void wilderWild$modifyMovementWithWind(CallbackInfo info) {
		if (WWClientWindManager.shouldUseWind()) {
			this.xd = ClientWindManager.windX * ModConfig.CONFIG.shrub.windStrength;
			this.zd = ClientWindManager.windZ * ModConfig.CONFIG.shrub.windStrength;
		}
	}

}
