/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WalkNodeEvaluator.class)
public class WalkNodeEvaluatorMixin {

	@Inject(
		method = "checkNeighbourBlocks",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0, shift = At.Shift.BEFORE),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void wilderWild$checkNeighbourBlocksWithPricklyPear(BlockGetter level, BlockPos.MutableBlockPos centerPos, BlockPathTypes nodeType, CallbackInfoReturnable<BlockPathTypes> info, int i, int j, int k, int l, int m, int n, BlockState blockState) {
		if (blockState.is(RegisterBlocks.PRICKLY_PEAR_CACTUS)) {
			info.setReturnValue(BlockPathTypes.DAMAGE_OTHER);
		}
	}

	@Inject(
		method = "getBlockPathTypeRaw",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 3, shift = At.Shift.BEFORE),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void wilderWild$getBlockPathTypeRawWithPricklyPear(BlockGetter level, BlockPos pos, CallbackInfoReturnable<BlockPathTypes> info, BlockState blockState, Block block) {
		if (blockState.is(RegisterBlocks.PRICKLY_PEAR_CACTUS)) {
			info.setReturnValue(BlockPathTypes.DAMAGE_OTHER);
		}
	}

}
