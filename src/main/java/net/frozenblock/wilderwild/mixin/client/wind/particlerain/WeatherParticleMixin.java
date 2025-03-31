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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pigcart.particlerain.particle.WeatherParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(WeatherParticle.class)
public abstract class WeatherParticleMixin extends TextureSheetParticle {

	protected WeatherParticleMixin(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f);
	}

	@Inject(
		method = "removeIfObstructed",
		at = @At(
			value = "INVOKE",
			target = "Lpigcart/particlerain/particle/WeatherParticle;remove()V",
			shift = At.Shift.BEFORE
		),
		cancellable = true,
		require = 0
	)
	public void wilderWild$cancelIncorrectRemoval(CallbackInfoReturnable<Boolean> info) {
		if (this.x == this.xo && Math.abs(ClientWindManager.windX) < 0.075D) info.setReturnValue(false);
		if (this.z == this.zo && Math.abs(ClientWindManager.windZ) < 0.075D) info.setReturnValue(false);
	}

}
