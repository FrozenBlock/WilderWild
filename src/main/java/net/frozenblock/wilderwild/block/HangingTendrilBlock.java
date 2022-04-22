package net.frozenblock.wilderwild.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilPhase;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
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
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class HangingTendrilBlock extends BlockWithEntity implements Waterloggable {
    public static final Object2IntMap<GameEvent> FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (map) -> {
        map.put(GameEvent.STEP, 1);
        map.put(GameEvent.FLAP, 2);
        map.put(GameEvent.SWIM, 3);
        map.put(GameEvent.ELYTRA_GLIDE, 4);
        map.put(GameEvent.HIT_GROUND, 5);
        map.put(GameEvent.SPLASH, 6);
        map.put(GameEvent.ENTITY_SHAKE, 6);
        map.put(GameEvent.BLOCK_CHANGE, 6);
        map.put(GameEvent.NOTE_BLOCK_PLAY, 6);
        map.put(GameEvent.PROJECTILE_SHOOT, 7);
        map.put(GameEvent.DRINK, 7);
        map.put(GameEvent.PRIME_FUSE, 7);
        map.put(GameEvent.PROJECTILE_LAND, 8);
        map.put(GameEvent.EAT, 8);
        map.put(GameEvent.ENTITY_INTERACT, 8);
        map.put(GameEvent.ENTITY_DAMAGE, 8);
        map.put(GameEvent.EQUIP, 9);
        map.put(GameEvent.SHEAR, 9);
        map.put(GameEvent.ENTITY_ROAR, 9);
        map.put(GameEvent.BLOCK_CLOSE, 10);
        map.put(GameEvent.BLOCK_DEACTIVATE, 10);
        map.put(GameEvent.BLOCK_DETACH, 10);
        map.put(GameEvent.DISPENSE_FAIL, 10);
        map.put(GameEvent.BLOCK_OPEN, 11);
        map.put(GameEvent.BLOCK_ACTIVATE, 11);
        map.put(GameEvent.BLOCK_ATTACH, 11);
        map.put(GameEvent.ENTITY_PLACE, 12);
        map.put(GameEvent.BLOCK_PLACE, 12);
        map.put(GameEvent.FLUID_PLACE, 12);
        map.put(GameEvent.ENTITY_DIE, 13);
        map.put(GameEvent.BLOCK_DESTROY, 13);
        map.put(GameEvent.FLUID_PICKUP, 13);
        map.put(GameEvent.ITEM_INTERACT_FINISH, 14);
        map.put(GameEvent.CONTAINER_CLOSE, 14);
        map.put(GameEvent.PISTON_CONTRACT, 14);
        map.put(GameEvent.PISTON_EXTEND, 15);
        map.put(GameEvent.CONTAINER_OPEN, 15);
        map.put(GameEvent.ITEM_INTERACT_START, 15);
        map.put(GameEvent.EXPLODE, 15);
        map.put(GameEvent.LIGHTNING_STRIKE, 15);
    }));
    public static final EnumProperty<HangingTendrilPhase> HANGING_TENDRIL_PHASE;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty TWITCHING;
    protected static final VoxelShape OUTLINE_SHAPE;
    private final int range;

    public HangingTendrilBlock(Settings settings, int range) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HANGING_TENDRIL_PHASE, HangingTendrilPhase.INACTIVE).with(WATERLOGGED, false).with(TWITCHING, false));
        this.range = 16;
    }

    public int getRange() {
        return range;
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
                    wigglyTendril.ticksToStopTwitching=random.nextBetween(7,12);
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
            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 0.8F);
        }
        updateNeighbors(world, pos);
    }

    public static void setActive(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int power) {
        world.setBlockState(pos, state.with(HANGING_TENDRIL_PHASE, HangingTendrilPhase.ACTIVE), 3);
        world.createAndScheduleBlockTick(pos, state.getBlock(), 60);
        updateNeighbors(world, pos);
        world.emitGameEvent(entity, WilderWild.SCULK_SENSOR_ACTIVATE, pos);

        if (!(Boolean)state.get(WATERLOGGED)) {
            world.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 0.8F);
        }

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HANGING_TENDRIL_PHASE, WATERLOGGED, TWITCHING});
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof HangingTendrilBlockEntity hangingEntity) {
            return getPhase(state) == HangingTendrilPhase.ACTIVE ? hangingEntity.getLastVibrationFrequency() : 0;
        } else {
            return 0;
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        this.dropExperienceWhenMined(world, pos, stack, ConstantIntProvider.create(1));
    }

    static {
        HANGING_TENDRIL_PHASE = RegisterProperties.HANGING_TENDRIL_PHASE;
        WATERLOGGED = Properties.WATERLOGGED;
        TWITCHING = RegisterProperties.TWITCHING;
        OUTLINE_SHAPE = Block.createCuboidShape(5.0D, 2.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    }
}
