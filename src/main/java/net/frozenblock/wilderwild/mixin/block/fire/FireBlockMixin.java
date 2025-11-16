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
	public void wilderWild$scorchTick(ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextFloat() <= 0.0125F) ScorchedBlock.scorch(level.getBlockState(pos), level, pos);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$magmaSmoke(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (!WWBlockConfig.FIRE_MAGMA_PARTICLES || !level.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK)) return;
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
