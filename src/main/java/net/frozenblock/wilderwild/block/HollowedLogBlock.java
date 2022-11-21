package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HollowedLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape X_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 16, 3), Block.box(0, 13, 0, 16, 16, 16), Block.box(0, 0, 13, 16, 16, 16), Block.box(0, 0, 0, 16, 3, 16));
	protected static final VoxelShape Y_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 16, 3), Block.box(0, 0, 0, 3, 16, 16), Block.box(0, 0, 13, 16, 16, 16), Block.box(13, 0, 0, 16, 16, 16));
	protected static final VoxelShape Z_SHAPE = Shapes.or(Block.box(13, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 3, 16, 16), Block.box(0, 13, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 3, 16));
	protected static final VoxelShape RAYCAST_SHAPE = Shapes.block();
	//public static final IntProperty LEVEL = IntProperty.of("level", 0, 9);
	//public static final DirectionProperty FACING = Properties.FACING;

	// CLASS's BASE METHODS
	public HollowedLogBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(AXIS)) {
			default -> X_SHAPE;
			case Y -> Y_SHAPE;
			case Z -> Z_SHAPE;
		};
	}

	public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
		return RAYCAST_SHAPE;
	}

    /*@Override
    public void onPlaced(World level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateWaterCompatibility(level, state, pos);
        world.createAndScheduleBlockTick(pos, this, 1);
        super.onPlaced(level, pos, state, placer, itemStack);
    }*/

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis()).setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		level.scheduleTick(currentPos, this, 1);
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}


    /*public void scheduledTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        updateWaterCompatibility(level, level.getBlockState(pos), pos);
    }

    private void updateWaterCompatibility(WorldAccess level, BlockState state, BlockPos pos) {
        Direction dir = state.get(FACING);
        if (state.get(LEVEL) != 9) { // Check for not persistent
            Block getBlock = level.getBlockState(pos.offset(dir.getOpposite())).getBlock();
            Fluid getFluid = level.getFluidState(pos.offset(dir.getOpposite())).getFluid();
            if (dir != Direction.UP) { // Up can not transport water
                if (getBlock instanceof HollowedLogBlock) {
                    if (level.getBlockState(pos.offset(dir.getOpposite())).get(FACING) == dir
                            && level.getBlockState(pos.offset(dir.getOpposite())).get(WATERLOGGED)) {
                        if (level.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) < 8) {
                            int waterlevel = level.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) + 1;
                            tryFillWithFluid(level, pos, state, Fluids.WATER.getDefaultState(), waterlevel);
                        } else if (level.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) == 9) {
                            tryFillWithFluid(level, pos, state, Fluids.WATER.getDefaultState(), 1);
                        }
                    } else {
                        tryDrainFluid(level, pos, state);
                    }
                } else if (getFluid == Fluids.WATER || getFluid == Fluids.FLOWING_WATER) {
                    int waterlevel;
                    if (getBlock == Blocks.WATER) {
                        if (level.getBlockState(pos.offset(dir.getOpposite())).get(FluidBlock.LEVEL) < 8) {
                            waterlevel = level.getBlockState(pos.offset(dir.getOpposite())).get(FluidBlock.LEVEL) + 1;
                            tryFillWithFluid(level, pos, state, Fluids.WATER.getDefaultState(), waterlevel);
                        }
                    } else {
                        tryFillWithFluid(level, pos, state, Fluids.WATER.getDefaultState(), 1);
                    }
                } else {
                    tryDrainFluid(level, pos, state);
                }
            }
        }
    }

    private boolean tryFillWithFluid(WorldAccess level, BlockPos pos, BlockState state, FluidState fluidState, int level) {
        if (!(Boolean) state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!level.isClient()) {
                level.setBlockState(pos, state.with(Properties.WATERLOGGED, true).with(LEVEL, level), 3);
                world.createAndScheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(level));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean tryFillWithFluid(WorldAccess level, BlockPos pos, BlockState state, FluidState defaultState) {
        return tryFillWithFluid(level, pos, state, defaultState, 9);
    }

    public ItemStack tryDrainFluid(WorldAccess level, BlockPos pos, BlockState state, int level) {
        if (state.get(Properties.WATERLOGGED)) {
            int getLevel = level.getBlockState(pos).get(LEVEL);
            if (getLevel != 9) {
                level.setBlockState(pos, state.with(Properties.WATERLOGGED, false).with(LEVEL, level), 3);
                if (!state.canPlaceAt(level, pos)) {
                    world.breakBlock(pos, true);
                }
            }

            if (getLevel != 9) {
                return ItemStack.EMPTY;
            } else {
                return new ItemStack(Items.WATER_BUCKET);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public ItemStack tryDrainFluid(WorldAccess level, BlockPos pos, BlockState state) {
        return tryDrainFluid(level, pos, state, 0);
    }*/

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	// RENDERING
	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return !(Boolean) state.getValue(WATERLOGGED);
	}

	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

}
