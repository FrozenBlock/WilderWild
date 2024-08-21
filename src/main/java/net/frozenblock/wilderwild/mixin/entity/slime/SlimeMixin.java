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

package net.frozenblock.wilderwild.mixin.entity.slime;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public class SlimeMixin {

	@Inject(
		method = "checkSlimeSpawnRules",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private static void wilderWild$spawnInAlgae(
		EntityType<Slime> type, @NotNull LevelAccessor level, MobSpawnType spawnReason, BlockPos pos, @NotNull RandomSource random, CallbackInfoReturnable<Boolean> info
	) {
		if (level.getBrightness(LightLayer.BLOCK, pos) < random.nextInt(8)) {
			boolean test = spawnReason == MobSpawnType.SPAWNER || random.nextInt(5) == 0;
			if (test && RegisterBlocks.ALGAE.hasAmountNearby(level, pos, 1, 3)) {
				info.setReturnValue(true);
			}
		}
	}

}
