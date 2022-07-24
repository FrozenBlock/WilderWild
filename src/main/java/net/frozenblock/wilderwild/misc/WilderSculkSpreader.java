package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class WilderSculkSpreader implements SculkSpreadable {
    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        BlockState placementState = null;
        BlockPos cursorPos = cursor.getPos();
        BlockState currentState = world.getBlockState(cursorPos);
        if (currentState.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_STAIRS.getDefaultState().with(StairsBlock.FACING, currentState.get(StairsBlock.FACING)).with(StairsBlock.HALF, currentState.get(StairsBlock.HALF)).with(StairsBlock.SHAPE, currentState.get(StairsBlock.SHAPE)).with(StairsBlock.WATERLOGGED, currentState.get(StairsBlock.WATERLOGGED));
        } else if (currentState.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_WALL.getDefaultState().with(WallBlock.UP, currentState.get(WallBlock.UP)).with(WallBlock.NORTH_SHAPE, currentState.get(WallBlock.NORTH_SHAPE))
                    .with(WallBlock.EAST_SHAPE, currentState.get(WallBlock.EAST_SHAPE)).with(WallBlock.WEST_SHAPE, currentState.get(WallBlock.WEST_SHAPE))
                    .with(WallBlock.SOUTH_SHAPE, currentState.get(WallBlock.SOUTH_SHAPE)).with(WallBlock.WATERLOGGED, currentState.get(WallBlock.WATERLOGGED));
        } else if (currentState.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_SLAB.getDefaultState().with(SlabBlock.WATERLOGGED, currentState.get(SlabBlock.WATERLOGGED)).with(SlabBlock.TYPE, currentState.get(SlabBlock.TYPE));
        }

        if (placementState != null) {
            world.setBlockState(cursorPos, placementState, 3);
            return cursor.getCharge() - 1;
        }
        return random.nextInt(spreadManager.getSpreadChance()) == 0 ? MathHelper.floor((float) cursor.getCharge() * 0.5F) : cursor.getCharge();
    }

    @Override
    public boolean spread(WorldAccess world, BlockPos pos, BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
        BlockState placementState = null;
        BlockState currentState = world.getBlockState(pos);
        if (currentState.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_STAIRS.getDefaultState().with(StairsBlock.FACING, currentState.get(StairsBlock.FACING)).with(StairsBlock.HALF, currentState.get(StairsBlock.HALF)).with(StairsBlock.SHAPE, currentState.get(StairsBlock.SHAPE)).with(StairsBlock.WATERLOGGED, currentState.get(StairsBlock.WATERLOGGED));
        } else if (currentState.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_WALL.getDefaultState().with(WallBlock.UP, currentState.get(WallBlock.UP)).with(WallBlock.NORTH_SHAPE, currentState.get(WallBlock.NORTH_SHAPE))
                    .with(WallBlock.EAST_SHAPE, currentState.get(WallBlock.EAST_SHAPE)).with(WallBlock.WEST_SHAPE, currentState.get(WallBlock.WEST_SHAPE))
                    .with(WallBlock.SOUTH_SHAPE, currentState.get(WallBlock.SOUTH_SHAPE)).with(WallBlock.WATERLOGGED, currentState.get(WallBlock.WATERLOGGED));
        } else if (currentState.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_SLAB.getDefaultState().with(SlabBlock.WATERLOGGED, currentState.get(SlabBlock.WATERLOGGED)).with(SlabBlock.TYPE, currentState.get(SlabBlock.TYPE));
        }

        if (placementState != null) {
            world.setBlockState(pos, placementState, 3);
            return true;
        }
        return false;
    }
}
