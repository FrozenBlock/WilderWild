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

package net.frozenblock.wilderwild.mixin.entity.slime;

import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
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
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private static void wilderWild$spawnInAlgae(
		EntityType<Slime> type, @NotNull LevelAccessor level, EntitySpawnReason reason, BlockPos pos, @NotNull RandomSource random, CallbackInfoReturnable<Boolean> info
	) {
		if (level.getBrightness(LightLayer.BLOCK, pos) > random.nextInt(7)) return;
		if (!(EntitySpawnReason.ignoresLightRequirements(reason) || random.nextInt(5) == 0) || !AlgaeBlock.hasNearbyAlgae(level, pos, 1, 3)) return;
		info.setReturnValue(true);
	}

}
