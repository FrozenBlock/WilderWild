/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.leaves;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import java.util.Optional;
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.block.leaves.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingParticlesLeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FallingParticlesLeavesBlock.class)
public class FallingParticlesLeavesBlockMixin {

	@WrapWithCondition(
		method = "animateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/FallingParticlesLeavesBlock;makeFallingLeavesParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V"
		)
	)
	public boolean wilderWild$fallingLeafParticles(FallingParticlesLeavesBlock instance, Level level, BlockPos pos, RandomSource random) {
		final Optional<FallingLeafData> fallingLeafData = FallingLeafUtil.tryGetFallingLeafData(instance.defaultBlockState(), level);
		return fallingLeafData.isEmpty()
			|| fallingLeafData.get().leafParticleData().isEmpty()
			|| !fallingLeafData.get().leafParticleData().get().cancelsVanillaParticles();
	}
}
