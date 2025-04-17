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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.block.ice;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VibrationSystem.Ticker.class)
public interface VibrationSystemTickerMixin {

	@WrapOperation(
		method = "method_51408",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
		)
	)
	private static int wilderWild$removeParticleIfIcicle(
		ServerLevel usedLevel,
		ParticleOptions vibrationParticleOption,
		double x,
		double y,
		double z,
		int count,
		double xOffset,
		double yOffset,
		double zOffset,
		double speed,
		Operation<Integer> operation,
		VibrationSystem.Data data,
		VibrationSystem.User user,
		ServerLevel level,
		VibrationInfo vibrationInfo
	) {
		if (user instanceof IcicleBlockEntity.VibrationUser) return 0;
		return operation.call(usedLevel, vibrationParticleOption, x, y, z, count, xOffset, yOffset, zOffset, speed);
	}

	@WrapOperation(
		method = "tryReloadVibrationParticle",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
		)
	)
	private static int wilderWild$removeParticleFromReloadIfIcicle(
		ServerLevel usedLevel,
		ParticleOptions vibrationParticleOption,
		double x,
		double y,
		double z,
		int count,
		double xOffset,
		double yOffset,
		double zOffset,
		double speed,
		Operation<Integer> operation,
		ServerLevel level,
		VibrationSystem.Data data,
		VibrationSystem.User user
	) {
		if (user instanceof IcicleBlockEntity.VibrationUser) return 1;
		return operation.call(usedLevel, vibrationParticleOption, x, y, z, count, xOffset, yOffset, zOffset, speed);
	}

}
