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

package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pigcart.particlerain.particle.DustMoteParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DustMoteParticle.class)
public abstract class DustMoteParticleMixin extends WeatherParticle {

	protected DustMoteParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@ModifyConstant(
		method = "tick",
		constant = @Constant(doubleValue = 0.2, ordinal = 0),
		require = 0
	)
	private double wilderWild$modifyX(
		double constant,
		@Share("wilderWild$windZ")LocalDoubleRef windZ
	) {
		if (WWClientWindManager.shouldUseWind()) {
			Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
				.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
			windZ.set(wind.z);
			return this.xd + (wind.x * 0.005D);
		}
		return constant;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/ModConfig$SandOptions;windStrength:F",
			ordinal = 0
		),
		require = 0
	)
	private float wilderWild$modifyXInit(float original) {
		if (WWClientWindManager.shouldUseWind()) return (float) (ClientWindManager.windX * original * original);
		return original;
	}

	@ModifyConstant(
		method = "tick",
		constant = @Constant(doubleValue = 0.2, ordinal = 1),
		require = 0
	)
	private double wilderWild$modifyZ(
		double constant,
		@Share("wilderWild$windZ")LocalDoubleRef windZ
	) {
		if (WWClientWindManager.shouldUseWind()) return this.zd + (windZ.get() * 0.005D);
		return constant;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/ModConfig$SandOptions;windStrength:F",
			ordinal = 1
		),
		require = 0
	)
	private float wilderWild$modifyZInit(float original) {
		if (WWClientWindManager.shouldUseWind()) return (float) (ClientWindManager.windZ * original * original);
		return original;
	}

}
