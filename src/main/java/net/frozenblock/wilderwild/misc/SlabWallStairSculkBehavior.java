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
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlabWallStairSculkBehavior implements SculkBehaviour {

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        BlockState placementState = null;
        BlockPos cursorPos = cursor.getPos();
        BlockState currentState = level.getBlockState(cursorPos);
        if (currentState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || currentState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
            placementState = RegisterBlocks.SCULK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, currentState.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, currentState.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, currentState.getValue(StairBlock.SHAPE)).setValue(StairBlock.WATERLOGGED, currentState.getValue(StairBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || currentState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
            placementState = RegisterBlocks.SCULK_WALL.defaultBlockState().setValue(WallBlock.UP, currentState.getValue(WallBlock.UP)).setValue(WallBlock.NORTH_WALL, currentState.getValue(WallBlock.NORTH_WALL))
                    .setValue(WallBlock.EAST_WALL, currentState.getValue(WallBlock.EAST_WALL)).setValue(WallBlock.WEST_WALL, currentState.getValue(WallBlock.WEST_WALL))
                    .setValue(WallBlock.SOUTH_WALL, currentState.getValue(WallBlock.SOUTH_WALL)).setValue(WallBlock.WATERLOGGED, currentState.getValue(WallBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || currentState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
            placementState = RegisterBlocks.SCULK_SLAB.defaultBlockState().setValue(SlabBlock.WATERLOGGED, currentState.getValue(SlabBlock.WATERLOGGED)).setValue(SlabBlock.TYPE, currentState.getValue(SlabBlock.TYPE));
        }

        if (placementState != null) {
            level.setBlock(cursorPos, placementState, 3);
            return cursor.getCharge() - 1;
        }
        return random.nextInt(spreadManager.chargeDecayRate()) == 0 ? Mth.floor((float) cursor.getCharge() * 0.5F) : cursor.getCharge();
    }

    @Override
    public boolean attemptSpreadVein(LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
        BlockState placementState = null;
        BlockState currentState = level.getBlockState(pos);
        if (currentState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || currentState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
            placementState = RegisterBlocks.SCULK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, currentState.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, currentState.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, currentState.getValue(StairBlock.SHAPE)).setValue(StairBlock.WATERLOGGED, currentState.getValue(StairBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || currentState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
            placementState = RegisterBlocks.SCULK_WALL.defaultBlockState().setValue(WallBlock.UP, currentState.getValue(WallBlock.UP)).setValue(WallBlock.NORTH_WALL, currentState.getValue(WallBlock.NORTH_WALL))
                    .setValue(WallBlock.EAST_WALL, currentState.getValue(WallBlock.EAST_WALL)).setValue(WallBlock.WEST_WALL, currentState.getValue(WallBlock.WEST_WALL))
                    .setValue(WallBlock.SOUTH_WALL, currentState.getValue(WallBlock.SOUTH_WALL)).setValue(WallBlock.WATERLOGGED, currentState.getValue(WallBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || currentState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
            placementState = RegisterBlocks.SCULK_SLAB.defaultBlockState().setValue(SlabBlock.WATERLOGGED, currentState.getValue(SlabBlock.WATERLOGGED)).setValue(SlabBlock.TYPE, currentState.getValue(SlabBlock.TYPE));
        }

        if (placementState != null) {
            level.setBlock(pos, placementState, 3);
            return true;
        }
        return false;
    }

}
