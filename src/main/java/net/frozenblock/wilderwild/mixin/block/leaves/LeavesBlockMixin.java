/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.leaves;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@Inject(method = "animateTick", at = @At("HEAD"))
	public void wilderWild$fallingLeafParticles(
		BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo info,
		@Share("wilderWild$usingCustomFallingLeaves") LocalBooleanRef usingCustomFallingLeaves
	) {
		boolean hasCustomParticles = FallingLeafUtil.addFallingLeafParticles(state, world, pos, random);
		usingCustomFallingLeaves.set(hasCustomParticles);
	}

	@WrapWithCondition(
		method = "animateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/LeavesBlock;makeFallingLeavesParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V"
		)
	)
	public boolean wilderWild$fallingLeafParticles(
		LeavesBlock instance, Level level, BlockPos blockPos, RandomSource randomSource, BlockState blockState, BlockPos blockPos2,
		@Share("wilderWild$usingCustomFallingLeaves") LocalBooleanRef usingCustomFallingLeaves
	) {
		return !usingCustomFallingLeaves.get() || instance.builtInRegistryHolder().is(WWBlockTags.NON_OVERRIDEN_FALLING_LEAVES);
	}

}
