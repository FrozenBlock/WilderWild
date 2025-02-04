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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.ParticleRainClient;
import pigcart.particlerain.particle.SnowFlakeParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(SnowFlakeParticle.class)
public abstract class SnowFlakeParticleMixin extends WeatherParticle {

	protected SnowFlakeParticleMixin(ClientLevel level, double x, double y, double z, float gravity, SpriteSet provider) {
		super(level, x, y, z, gravity, provider);
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextFloat()F",
			ordinal = 0
		),
		require = 0
	)
	public float wilderWild$windXInit(float original, ClientLevel level) {
		if (WWClientWindManager.shouldUseWind()) {
			return (float) (ClientWindManager.windX * level.random.nextDouble() / ParticleRainClient.config.snowWindDampening);
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextFloat()F",
			ordinal = 1
		),
		require = 0
	)
	public float wilderWild$windZInit(float original, ClientLevel level) {
		if (WWClientWindManager.shouldUseWind()) {
			return (float) (ClientWindManager.windZ * level.random.nextDouble() / ParticleRainClient.config.snowWindDampening);
		}
		return original;
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/Mth;clamp(DDD)D",
			ordinal = 0
		),
		require = 0
	)
	public double wilderWild$windX(double value, double min, double max, Operation<Double> original) {
		if (WWClientWindManager.shouldUseWind()) {
			min = -100D;
		}
		return original.call(value, min, max);
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/Mth;clamp(DDD)D",
			ordinal = 0
		),
		require = 0
	)
	public double wilderWild$windZ(double value, double min, double max, Operation<Double> original) {
		if (WWClientWindManager.shouldUseWind()) {
			min = -100D;
		}
		return original.call(value, min, max);
	}

	@Inject(method = "tick", at = @At("HEAD"), require = 0)
	public void wilderWild$tick(CallbackInfo info) {
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1.5D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
		this.xd += wind.x * 0.05D;
		this.yd += wind.y * 0.005D;
		this.zd += wind.z * 0.05D;
	}

}
