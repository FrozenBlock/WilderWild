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

package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WilderClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pigcart.particlerain.particle.DustMoteParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DustMoteParticle.class)
public abstract class DustMoteParticleMixin extends WeatherParticle {

	protected DustMoteParticleMixin(ClientLevel level, double x, double y, double z, float gravity, SpriteSet provider) {
		super(level, x, y, z, gravity, provider);
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
		if (WilderClientWindManager.shouldUseWind()) {
			Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
				.scale(AmbienceAndMiscConfig.getParticleWindIntensity());
			windZ.set(wind.z);
			return this.xd + (wind.x * 0.005D);
		}
		return constant;
	}

	@ModifyConstant(
		method = "<init>",
		constant = @Constant(doubleValue = 0.20000000298023224, ordinal = 0),
		require = 0
	)
	private double wilderWild$modifyXInit(double constant) {
		if (WilderClientWindManager.shouldUseWind()) {
			return ClientWindManager.windX * constant * constant;
		}
		return constant;
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
		if (WilderClientWindManager.shouldUseWind()) {
			return this.zd + (windZ.get() * 0.005D);
		}
		return constant;
	}

	@ModifyConstant(
		method = "<init>",
		constant = @Constant(doubleValue = 0.20000000298023224, ordinal = 1),
		require = 0
	)
	private double wilderWild$modifyZInit(double constant) {
		if (WilderClientWindManager.shouldUseWind()) {
			return ClientWindManager.windZ * constant * constant;
		}
		return constant;
	}

}
