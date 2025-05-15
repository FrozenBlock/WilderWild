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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.ParticleRainClient;
import pigcart.particlerain.particle.SnowParticle;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(SnowParticle.class)
public abstract class SnowParticleMixin extends WeatherParticle {

	protected SnowParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/ModConfig$SnowOptions;stormWindStrength:F"
		),
		remap = false,
		require = 0
	)
	public float wilderWild$modifyStormWindStrength(float original) {
		if (WWClientWindManager.shouldUseWind()) return (float) ClientWindManager.windX;
		return original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lpigcart/particlerain/ModConfig$SnowOptions;windStrength:F"
		),
		remap = false,
		require = 0
	)
	public float wilderWild$modifyWindStrength(float original) {
		if (WWClientWindManager.shouldUseWind()) return (float) ClientWindManager.windX;
		return original;
	}

	@Inject(
		method = "<init>",
		at = @At(value = "TAIL"),
		require = 0
	)
	public void wilderWild$modifyWindZ(ClientLevel level, double x, double y, double z, CallbackInfo info) {
		if (WWClientWindManager.shouldUseWind()) this.zd = this.gravity * (float) ClientWindManager.windZ;
		if (ParticleRainClient.config.yLevelWindAdjustment) {
			this.zd *= ParticleRainClient.yLevelWindAdjustment(y);
		}
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
