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

package net.frozenblock.wilderwild.mixin.client.wind.fallingleaves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WilderClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
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
public abstract class FallingLeafParticleMixin extends TextureSheetParticle {

	@Shadow
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
		if (WilderClientWindManager.shouldUseWind()) {
			wilderWild$useWind.set(true);
			Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
				.scale(AmbienceAndMiscConfig.getParticleWindIntensity());
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
		if (wilderWild$useWind.get()) {
			return (float) wilderWild$windZ.get() * 0.6F;
		}
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
			.scale(AmbienceAndMiscConfig.getParticleWindIntensity()).scale(0.075D);
		this.xd += (wind.x - this.xd) * (double)this.windCoefficient / 60D;
		this.zd += (wind.z - this.zd) * (double)this.windCoefficient / 60D;
	}

}
