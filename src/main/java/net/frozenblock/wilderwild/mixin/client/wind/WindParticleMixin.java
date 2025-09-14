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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.particle.client.WindParticle;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(WindParticle.class)
public class WindParticleMixin {

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/frozenblock/lib/wind/client/impl/ClientWindManager;getWindMovement(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;DDD)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	public Vec3 wilderWild$fixWindSpeed(Vec3 original) {
		return original.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
	}

}
