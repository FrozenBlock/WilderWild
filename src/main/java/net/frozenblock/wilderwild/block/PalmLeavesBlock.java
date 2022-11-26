package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class PalmLeavesBlock extends LeavesBlock implements BonemealableBlock {

    public PalmLeavesBlock(Properties settings) {
        super(settings);
    }

    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return level.getBlockState(pos.below()).isAir() && state.getValue(BlockStateProperties.DISTANCE) < 2;
    }

    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
        level.setBlock(pos.below(), CoconutBlock.getDefaultHangingState(), 2);
    }

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		BlockState newState = updateDistance(state, level, pos);
		if (newState != state) {
			level.setBlockAndUpdate(pos, newState);
			state = newState;
		}
		if (this.decaying(state)) {
			LeavesBlock.dropResources(state, level, pos);
			level.removeBlock(pos, false);
		}
	}

	@Override
	public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		level.setBlock(pos, updateDistance(state, level, pos), 3);
	}

	@Override
	public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		int i;
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		if ((i = getDistanceAt(neighborState) + 1) != 1 || state.getValue(DISTANCE) != i) {
			level.scheduleTick(currentPos, this, 1);
		}
		return state;
	}

	private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int dist = (int) PalmCrownBlockEntity.PalmCrownPositions.distanceToClosestPalmCrown(level, pos, 7);
		int i = 7;
		for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
			i = Math.min(i, getDistanceAt(level.getBlockState(blockPos)) + 1);
			if (i == 1) break;
		}
		return state.setValue(DISTANCE, Math.min(dist, i));
	}

	public static int getDistanceAt(BlockState neighbor) {
		if (neighbor.is(BlockTags.LOGS)) {
			return 0;
		}
		if (neighbor.getBlock() instanceof LeavesBlock) {
			return neighbor.getValue(DISTANCE);
		}
		return 7;
	}
}
