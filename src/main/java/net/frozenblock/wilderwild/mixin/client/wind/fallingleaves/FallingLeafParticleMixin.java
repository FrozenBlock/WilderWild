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

package net.frozenblock.wilderwild.mixin.client.wind.fallingleaves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import randommcsomethin.fallingleaves.particle.FallingLeafParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(FallingLeafParticle.class)
public abstract class FallingLeafParticleMixin {

	// TODO: Fix

	/*
	extends SingleQuadParticle {

	@Shadow(remap = false)
	@Final
	protected float windCoefficient;

	protected FallingLeafParticleMixin(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f);
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lrandommcsomethin/fallingleaves/util/Wind;windX:F"
		),
		require = 0,
		remap = false
	)
	private float wilderWild$modifyWindX(
		float original,
		@Share("wilderWild$useWind") LocalBooleanRef wilderWild$useWind,
		@Share("wilderWild$windZ") LocalDoubleRef wilderWild$windZ
	) {
		if (WWClientWindManager.shouldUseWind()) {
			wilderWild$useWind.set(true);
			Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
				.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
			wilderWild$windZ.set(wind.z);
			return (float) wind.x * 0.6F;
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lrandommcsomethin/fallingleaves/util/Wind;windZ:F"
		),
		require = 0,
		remap = false
	)
	private float wilderWild$modifyWindZ(
		float original,
		@Share("wilderWild$useWind")LocalBooleanRef wilderWild$useWind,
		@Share("wilderWild$windZ") LocalDoubleRef wilderWild$windZ
	) {
		if (wilderWild$useWind.get()) return (float) wilderWild$windZ.get() * 0.6F;
		return original;
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(DD)D",
			shift = At.Shift.AFTER
		),
		require = 0
	)
	public void wilderWild$continueInWater(CallbackInfo info) {
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity()).scale(0.075D);
		this.xd += (wind.x - this.xd) * (double)this.windCoefficient / 60D;
		this.zd += (wind.z - this.zd) * (double)this.windCoefficient / 60D;
	}
	 */

}
