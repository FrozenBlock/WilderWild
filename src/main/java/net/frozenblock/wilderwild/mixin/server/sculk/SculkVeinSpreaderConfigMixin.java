/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.sculk;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkVeinBlock.SculkVeinSpreaderConfig.class)
public final class SculkVeinSpreaderConfigMixin {

	@Unique
	private BlockState wilderWild$capturedBlockState;

	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0), method = "stateCanBeReplaced")
	private BlockState wilderWild$newBlocks(BlockState original) {
		this.wilderWild$capturedBlockState = original;
		return original;
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0, shift = At.Shift.BEFORE), method = "stateCanBeReplaced", cancellable = true)
	private void wilderWild$newBlocks(BlockGetter level, BlockPos pos, BlockPos growPos, Direction direction, BlockState state, CallbackInfoReturnable<Boolean> info) {
		if (this.wilderWild$capturedBlockState != null && (this.wilderWild$capturedBlockState.is(RegisterBlocks.OSSEOUS_SCULK) || this.wilderWild$capturedBlockState.is(RegisterBlocks.SCULK_SLAB) || this.wilderWild$capturedBlockState.is(RegisterBlocks.SCULK_STAIRS) || this.wilderWild$capturedBlockState.is(RegisterBlocks.SCULK_WALL))) {
			info.setReturnValue(false);
		}
		this.wilderWild$capturedBlockState = null;
	}

}
