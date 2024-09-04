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

package net.frozenblock.wilderwild.mixin.block.fire;

import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class FireBlockMixin {

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 0,
			shift = At.Shift.BEFORE
		)
	)
	public void wilderWild$tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		this.wilderWild$scorchTick(level, pos.below(), random);
	}

	@Unique
	public void wilderWild$scorchTick(ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() <= 0.0125F) {
			ScorchedBlock.scorch(level.getBlockState(pos), level, pos);
		}
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$magmaSmoke(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (WWBlockConfig.FIRE_MAGMA_PARTICLES && level.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK)) {
			if (random.nextFloat() <= 0.05F) {
				level.sendParticles(
					ParticleTypes.LAVA,
					(double) pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					(double) pos.getZ() + 0.5D,
					1,
					random.nextDouble() / 3D,
					random.nextDouble() * 0.5D,
					random.nextDouble() / 3D,
					(random.nextDouble() - 0.5D) * 0.05D
				);
			}

			if (random.nextFloat() <= 0.2F) {
				level.sendParticles(
					ParticleTypes.LARGE_SMOKE,
					(double) pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					(double) pos.getZ() + 0.5D,
					random.nextInt(1, 6),
					random.nextDouble() / 3D,
					random.nextDouble() * 0.5D,
					random.nextDouble() / 3D,
					(random.nextDouble() - 0.5D) * 0.05D
				);
			}
		}
	}

}
