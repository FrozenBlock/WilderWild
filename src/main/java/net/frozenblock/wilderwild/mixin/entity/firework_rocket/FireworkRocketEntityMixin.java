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

package net.frozenblock.wilderwild.mixin.entity.firework_rocket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin {

	@Shadow
	private int life;
	@Shadow
	private int lifetime;

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
		if (FireworkRocketEntity.class.cast(this).level() instanceof ServerLevel level) {
			double intensity = (Math.max(1, (double) (this.lifetime - this.life)) / Math.max(1, this.lifetime)) * 0.5D;
			Vec3 wind = WindManager.getWindManager(level).getWindMovement(BlockPos.containing(instance.getX(), instance.getY(), instance.getZ()), intensity)
				.scale(WWAmbienceAndMiscConfig.getFireworkWindIntensity());
			vec3 = vec3.add(wind.x() * 0.001D, wind.y() * 0.00005D, wind.z() * 0.001D);
		}
		operation.call(instance, vec3);
	}

}
