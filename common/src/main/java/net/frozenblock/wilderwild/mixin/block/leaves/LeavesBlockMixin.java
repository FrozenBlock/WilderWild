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
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.Optional;
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.block.leaves.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {

	public LeavesBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "animateTick", at = @At("HEAD"))
	public void wilderWild$fallingLeafParticles(
		BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo info,
		@Share("wilderWild$fallingLeafData") LocalRef<Optional<FallingLeafData>> optionalFallingLeafData
	) {
		optionalFallingLeafData.set(FallingLeafUtil.tryAnimateTickAndGetFallingLeafData(state, level, pos, random));
	}

	@WrapWithCondition(
		method = "animateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/LeavesBlock;makeFallingLeavesParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V"
		)
	)
	public boolean wilderWild$fallingLeafParticles(
		LeavesBlock instance, Level level, BlockPos pos, RandomSource random, BlockState belowState, BlockPos below,
		@Share("wilderWild$fallingLeafData") LocalRef<Optional<FallingLeafData>> optionalFallingLeafData
	) {
		final Optional<FallingLeafData> fallingLeafData = optionalFallingLeafData.get();
		return fallingLeafData == null
			|| fallingLeafData.isEmpty()
			|| fallingLeafData.get().leafParticleData().isEmpty()
			|| !fallingLeafData.get().leafParticleData().get().cancelsVanillaParticles();
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		super.stepOn(level, pos, state, entity);
		FallingLeafUtil.trySpawnWalkParticles(state, level, pos, entity, false);
	}
}
