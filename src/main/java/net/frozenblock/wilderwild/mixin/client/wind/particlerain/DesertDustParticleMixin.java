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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.particle.DesertDustParticle;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DesertDustParticle.class)
public class DesertDustParticleMixin {

	@Unique
	private static boolean wilderWild$useWind() {
		return AmbienceAndMiscConfig.CLOUD_MOVEMENT && ClientWindManager.shouldUseWind();
	}

	@Inject(method = "tick", at = @At("TAIL"), require = 0)
	private void wilderWild$modifyWind(CallbackInfo info) {
		if (wilderWild$useWind()) {
			DesertDustParticle.class.cast(this).xd = Mth.clamp(ClientWindManager.windX * 3, -0.4, 0.4);
			DesertDustParticle.class.cast(this).yd += Mth.clamp(ClientWindManager.windY * 0.1, -0.01, 0.01);
			DesertDustParticle.class.cast(this).zd = Mth.clamp(ClientWindManager.windZ * 3, -0.4, 0.4);
		}
	}

}
