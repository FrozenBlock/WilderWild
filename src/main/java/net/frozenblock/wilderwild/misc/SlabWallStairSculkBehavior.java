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

package net.frozenblock.wilderwild.misc;

import java.util.Collection;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlabWallStairSculkBehavior implements SculkBehaviour {

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        BlockPos cursorPos = cursor.getPos();
		level.setBlock(cursorPos, switchBlockStates(level.getBlockState(cursorPos)), 3);
		return cursor.getCharge() - 1;
	}

	@Override
    public boolean attemptSpreadVein(LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
		level.setBlock(pos, switchBlockStates(level.getBlockState(pos)), 3);
		return true;
	}

	private BlockState switchBlockStates(BlockState blockState) {
		if (blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
			blockState = RegisterBlocks.SCULK_STAIRS.withPropertiesOf(blockState);
		} else if (blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
			blockState = RegisterBlocks.SCULK_WALL.withPropertiesOf(blockState);
		} else if (blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
			blockState = RegisterBlocks.SCULK_SLAB.withPropertiesOf(blockState);
		}
		return blockState;
	}

}
