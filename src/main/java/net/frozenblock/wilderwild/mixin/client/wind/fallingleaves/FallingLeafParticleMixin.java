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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.wind.WilderClientWindManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import randommcsomethin.fallingleaves.particle.FallingLeafParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(FallingLeafParticle.class)
public class FallingLeafParticleMixin {

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
		@Share("wilderWild$useWind")LocalBooleanRef wilderWild$useWind
	) {
		if (WilderClientWindManager.shouldUseWind()) {
			wilderWild$useWind.set(true);
			return (float) ClientWindManager.windX * 0.7F;
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
		@Share("wilderWild$useWind")LocalBooleanRef wilderWild$useWind
	) {
		if (wilderWild$useWind.get()) {
			return (float) ClientWindManager.windZ * 0.7F;
		}
		return original;
	}

}
