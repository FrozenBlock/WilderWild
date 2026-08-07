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
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.frozenblock.wilderwild.block.leaves.FallingLeafUtil;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingParticlesLeavesBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingParticlesLeavesBlock.class)
public abstract class FallingParticlesLeavesBlockMixin extends LeavesBlock {

	public FallingParticlesLeavesBlockMixin(AmbientLeavesBlockSoundPlayer ambientLeavesBlockSoundPlayer, Properties properties) {
		super(ambientLeavesBlockSoundPlayer, properties);
	}

	@Inject(method = "animateTick", at = @At("HEAD"))
	public void wilderWild$checkFallingLeafParticlesAndSpawnWW(
		BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo info,
		@Share("wilderWild$usingCustomFallingLeaves") LocalBooleanRef usingCustomFallingLeaves
	) {
		final boolean hasCustomParticles = FallingLeafUtil.tryAnimateTick(state, level, pos, random);
		usingCustomFallingLeaves.set(hasCustomParticles);
	}

	@WrapWithCondition(
		method = "animateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/FallingParticlesLeavesBlock;makeFallingLeavesParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V"
		)
	)
	public boolean wilderWild$cancelVanillaFallingLeafParticles(
		FallingParticlesLeavesBlock instance, Level level, BlockPos pos, RandomSource random,
		@Share("wilderWild$usingCustomFallingLeaves") LocalBooleanRef usingCustomFallingLeaves
	) {
		return !usingCustomFallingLeaves.get() || instance.builtInRegistryHolder().is(WWBlockTags.NON_OVERRIDEN_FALLING_LEAVES);
	}
}
