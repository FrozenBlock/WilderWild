/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.firework_rocket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.MiscConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin {

	@WrapOperation(
		method = "tick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FireworkRocketEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 0),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/projectile/FireworkRocketEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"
			)
		)
	)
	private void wilderWild$moveWithWind(FireworkRocketEntity instance, Vec3 vec3, Operation<Void> operation) {
		Vec3 wind = ClientWindManager.getWindMovement(instance.level(), BlockPos.containing(instance.getX(), instance.getY(), instance.getZ()), 1.5D).scale(MiscConfig.get().getParticleWindIntensity());
		vec3 = vec3.add(wind.x() * 0.001, wind.y() * 0.00005D, wind.z() * 0.001);
		operation.call(instance, vec3);
	}

}
