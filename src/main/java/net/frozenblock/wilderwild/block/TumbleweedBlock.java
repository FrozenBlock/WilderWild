package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class TumbleweedBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED;

	public TumbleweedBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction direction) {
		return adjacentBlockState.is(RegisterBlocks.TUMBLEWEED) && direction.getAxis() == Direction.Axis.Y;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		boolean bl = fluidState.getType() == Fluids.WATER;
		return (BlockState) super.getStateForPlacement(context).setValue(WATERLOGGED, bl);
	}

	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
		if ((Boolean) state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	public FluidState getFluidState(BlockState state) {
		return (Boolean) state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{WATERLOGGED});
	}

	static {
		WATERLOGGED = BlockStateProperties.WATERLOGGED;
	}
}
