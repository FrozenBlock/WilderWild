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

import net.frozenblock.wilderwild.block.PalmFrondsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @Inject(method = "updateDistance", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$updateDistance(BlockState state, LevelAccessor level, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
		if (state.getBlock() instanceof PalmFrondsBlock) {
			int i = 7;
			for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
				i = Math.min(i, PalmFrondsBlock.getDistanceAt(level.getBlockState(blockPos)) + 1);
				if (i == 1) break;
			}
			info.setReturnValue(state.setValue(BlockStateProperties.DISTANCE, i));
		}
	}

}
