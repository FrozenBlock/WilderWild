package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilPhase;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class HangingTendrilBlock extends BlockWithEntity implements Waterloggable, SculkSpreadable {
    public static final EnumProperty<HangingTendrilPhase> HANGING_TENDRIL_PHASE;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty TWITCHING;
    public static final BooleanProperty WRINGING_OUT;
    protected static final VoxelShape OUTLINE_SHAPE;
    private final int range;

    public HangingTendrilBlock(Settings settings, int range) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HANGING_TENDRIL_PHASE, HangingTendrilPhase.INACTIVE).with(WATERLOGGED, false).with(TWITCHING, false).with(WRINGING_OUT, false));
        this.range = range;
    }

    public int getRange() {
        return this.range;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public boolean hasRandomTicks(BlockState state) { return true; }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        } else if (getPhase(state)==HangingTendrilPhase.INACTIVE) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity!=null) {
                if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
                    world.setBlockState(pos, state.with(TWITCHING, true));
                    wigglyTendril.ticksToStopTwitching=random.nextBetween(5,12);
                }
            }
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        }
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        if (getPhase(state) != HangingTendrilPhase.ACTIVE) {
            if (getPhase(state) == HangingTendrilPhase.COOLDOWN) {
                world.setBlockState(pos, state.with(HANGING_TENDRIL_PHASE, HangingTendrilPhase.INACTIVE), 3);
            }
        } else if (!isInactive(state)) {
            setCooldown(world, pos, state);
        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient() && !state.isOf(oldState.getBlock())) {
            world.createAndScheduleBlockTick(new BlockPos(pos), state.getBlock(), 1);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (getPhase(state) == HangingTendrilPhase.ACTIVE) {
                updateNeighbors(world, pos);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    private static void updateNeighbors(World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, RegisterBlocks.HANGING_TENDRIL);
        world.updateNeighborsAlways(pos.offset(Direction.UP.getOpposite()), RegisterBlocks.HANGING_TENDRIL);
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HangingTendrilBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return blockEntity instanceof HangingTendrilBlockEntity ? ((HangingTendrilBlockEntity)blockEntity).getEventListener() : null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, RegisterBlockEntityType.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) -> {
            blockEntity.serverTick(worldx, pos, statex);
        }) : null;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return OUTLINE_SHAPE;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    public static HangingTendrilPhase getPhase(BlockState state) {
        return state.get(HANGING_TENDRIL_PHASE);
    }

    public static boolean isInactive(BlockState state) {
        return getPhase(state) == HangingTendrilPhase.INACTIVE;
    }

    public static void setCooldown(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(HANGING_TENDRIL_PHASE, HangingTendrilPhase.COOLDOWN), 3);
        world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        if (!(Boolean)state.get(WATERLOGGED)) {
            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 1.0F);
        }
        updateNeighbors(world, pos);
    }

    public static void setActive(World world, BlockPos pos, BlockState state, int power) {
        world.setBlockState(pos, state.with(HANGING_TENDRIL_PHASE, HangingTendrilPhase.ACTIVE), 3);
        world.createAndScheduleBlockTick(pos, state.getBlock(), 60);
        updateNeighbors(world, pos);

        if (!(Boolean)state.get(WATERLOGGED)) {
            world.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 1.0F);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING_TENDRIL_PHASE, WATERLOGGED, TWITCHING, WRINGING_OUT);
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof HangingTendrilBlockEntity hangingEntity) {
            return getPhase(state) == HangingTendrilPhase.ACTIVE ? hangingEntity.getLastVibrationFrequency() : 0;
        } else { return 0; }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean bl) {
        super.onStacksDropped(state, world, pos, stack, bl);
        this.dropExperienceWhenMined(world, pos, stack, ConstantIntProvider.create(1));
    }

    static {
        HANGING_TENDRIL_PHASE = RegisterProperties.HANGING_TENDRIL_PHASE;
        WATERLOGGED = Properties.WATERLOGGED;
        TWITCHING = RegisterProperties.TWITCHING;
        WRINGING_OUT = RegisterProperties.WRINGING_OUT;
        OUTLINE_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    }

    public static boolean shouldHavePogLighting(BlockState state) {
        return getPhase(state)==HangingTendrilPhase.ACTIVE || state.get(WRINGING_OUT);
    }

    public static HangingTendrilBlockEntity getEntity(World world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity!=null) {
            if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
                return wigglyTendril;
            }
        } return null;
    }
    public static HangingTendrilBlockEntity getEntity(WorldAccess world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity!=null) {
            if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
                return wigglyTendril;
            }
        } return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (isInactive(state) && !state.get(WRINGING_OUT)) {
            if (world.isClient) { return ActionResult.SUCCESS; } else {
                HangingTendrilBlockEntity tendrilEntity = getEntity(world, pos);
                if (tendrilEntity!=null) {
                    if (tendrilEntity.storedXP>0) {
                        world.setBlockState(pos, state.with(WRINGING_OUT, true));
                        world.playSound(
                                null,
                                pos,
                                RegisterSounds.BLOCK_HANGING_TENDRIL_WRING,
                                SoundCategory.BLOCKS,
                                1f,
                                world.random.nextFloat() * 0.1F + 0.9F
                        );
                        tendrilEntity.ringOutTicksLeft=5;
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        HangingTendrilBlockEntity tendrilEntity = getEntity(world, cursor.getPos());
        if (tendrilEntity!=null) {
            if (tendrilEntity.storedXP<900) {
                if (cursor.getCharge()>1) {
                    tendrilEntity.storedXP=tendrilEntity.storedXP+2;
                    return cursor.getCharge()-2;
                } else {
                    ++tendrilEntity.storedXP;
                    return cursor.getCharge()-1;
                }
            }
        }
        return cursor.getCharge();
    }
}
