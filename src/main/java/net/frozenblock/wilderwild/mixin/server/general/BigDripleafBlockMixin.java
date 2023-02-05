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

package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BigDripleafBlock.class)
public final class BigDripleafBlockMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void wilderWild$tickStem(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
        BlockState downState = level.getBlockState(pos.below());
        if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
            resetTilt(state, level, pos);
            info.cancel();
        }
    }

    @Inject(method = "neighborChanged", at = @At("HEAD"), cancellable = true)
    public void wilderWild$neighborStemChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving, CallbackInfo info) {
        if (fromPos == pos.below()) {
            BlockState downState = level.getBlockState(pos.below());
            if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
                resetTilt(state, level, pos);
                info.cancel();
            }
        } else {
			BlockState downState = level.getBlockState(pos.below());
			boolean neighbor = level.hasNeighborSignal(pos);
			if (!(downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) && state.getValue(BlockStateProperties.POWERED) != neighbor) {
				level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, neighbor), 3);
			}
		}
    }

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    public void wilderWild$entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo info) {
        if (!level.isClientSide) {
            BlockState downState = level.getBlockState(pos.below());
            if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
                info.cancel();
            }
        }
    }

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"), cancellable = true)
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		builder.add(BlockStateProperties.POWERED);
	}

    @Shadow
    private static void resetTilt(BlockState state, Level level, BlockPos pos) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BigDripleafBlockMixin.");
    }

}
