/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.config.ModConfig;
import pigcart.particlerain.particle.DustMoteParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DustMoteParticle.class)
public abstract class DustMoteParticleMixin extends WeatherParticle {

	protected DustMoteParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@Inject(
		method = "<init>",
		at = @At("TAIL"),
		require = 0
	)
	private void wilderWild$modifyXInit(ClientLevel level, double x, double y, double z, SpriteSet provider, CallbackInfo info) {
		if (WWClientWindManager.shouldUseWind()) {
			this.xd = ClientWindManager.windX * ModConfig.CONFIG.dust.windStrength;
			this.zd = ClientWindManager.windZ * ModConfig.CONFIG.dust.windStrength;
		}
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/particle/DustMoteParticle;zd:D",
			shift = At.Shift.AFTER
		),
		require = 0
	)
	private void wilderWild$modifyMovementWithWind(CallbackInfo info) {
		if (WWClientWindManager.shouldUseWind()) {
			this.xd = ClientWindManager.windX * ModConfig.CONFIG.dust.windStrength;
			this.zd = ClientWindManager.windZ * ModConfig.CONFIG.dust.windStrength;
		}
	}

}
