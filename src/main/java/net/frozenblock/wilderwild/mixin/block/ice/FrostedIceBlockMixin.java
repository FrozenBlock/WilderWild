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

package net.frozenblock.wilderwild.mixin.block.ice;

import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrostedIceBlock.class)
public class FrostedIceBlockMixin {

	@Inject(
		method = "slightlyMelt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
			shift = At.Shift.AFTER
		)
	)
	private void wilderWild$slightlyMelt(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (!WWBlockConfig.get().frostedIceCracking) return;
		final SoundType soundType = FrostedIceBlock.class.cast(this).getSoundType(state);
		level.playSound(null, pos, soundType.getBreakSound(), SoundSource.BLOCKS, 0.075F, (soundType.getPitch() + 0.2F) + level.getRandom().nextFloat() * 0.2F);
	}

	@Inject(
		method = "slightlyMelt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/FrostedIceBlock;melt(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
			shift = At.Shift.AFTER
		)
	)
	private void wildWilder$melt(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (!WWBlockConfig.get().frostedIceCracking) return;
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, state),
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				level.random.nextInt(20, 30),
				0.3F,
				0.3F,
				0.3F,
				0.05D
			);
		}
		final SoundType soundType = FrostedIceBlock.class.cast(this).getSoundType(state);
		level.playSound(null, pos, soundType.getBreakSound(), SoundSource.BLOCKS, 0.15F, soundType.getPitch());
	}

}
