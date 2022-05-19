package net.frozenblock.wilderwild.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.frozenblock.wilderwild.block.entity.SculkEchoerBlockEntity;
import net.frozenblock.wilderwild.block.entity.SculkEchoerPhase;
import net.frozenblock.wilderwild.registry.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.WardenEntity;
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
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class SculkEchoerBlock extends BlockWithEntity implements Waterloggable {
    public static final Object2IntMap<GameEvent> FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (map) -> {
        map.put(GameEvent.STEP, 1);
        map.put(RegisterGameEvents.TENDRIL_EXTRACT_XP, 1);
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
    public static final EnumProperty<SculkEchoerPhase> SCULK_ECHOER_PHASE = RegisterProperties.SCULK_ECHOER_PHASE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
    private final int range;

    private static final VoxelShape OUTLINE = VoxelShapes.fullCube();
    private static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(1.0D, 4.0D, 1.0D, 15.0D, 10.0D, 15.0D));
    private static final VoxelShape SHAPE_UPSIDEDOWN = VoxelShapes.union(Block.createCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.createCuboidShape(1.0D, 6.0D, 1.0D, 15.0D, 12.0D, 15.0D));

    public SculkEchoerBlock(Settings settings, int range) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SCULK_ECHOER_PHASE, SculkEchoerPhase.INACTIVE).with(WATERLOGGED, false).with(UPSIDEDOWN, false));
        this.range = range;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SCULK_ECHOER_PHASE, WATERLOGGED, UPSIDEDOWN);
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean bl) {
        super.onStacksDropped(state, world, pos, stack, bl);
        if (bl) {
            this.dropExperienceWhenMined(world, pos, stack, ConstantIntProvider.create(5));
        }
    }

    @Override
    public void onSteppedOn( World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient() && isInactive(state) && entity.getType() != EntityType.WARDEN) {
            setActive(entity, world, pos, state, 20);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient() && !state.isOf(oldState.getBlock())) {
            world.createAndScheduleBlockTick(new BlockPos(pos), state.getBlock(), 1);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (getPhase(state) == SculkEchoerPhase.ACTIVE) { updateNeighbors(world, pos); }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        if (getPhase(state)!=SculkEchoerPhase.ACTIVE) {
            if (getPhase(state)==SculkEchoerPhase.COOLDOWN) {
                world.setBlockState(pos, state.with(SCULK_ECHOER_PHASE, SculkEchoerPhase.INACTIVE), 3);
            }
        } else { setCooldown(world, pos, state); }
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(UPSIDEDOWN)) { return SHAPE_UPSIDEDOWN; }
        return SHAPE;
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(UPSIDEDOWN)) { return SHAPE_UPSIDEDOWN; }
        return SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState blockState) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return OUTLINE;
    }

    public static SculkEchoerPhase getPhase(BlockState state) {
        return state.get(SCULK_ECHOER_PHASE);
    }

    public static boolean isInactive(BlockState state) {
        return getPhase(state)==SculkEchoerPhase.INACTIVE;
    }

    public static void setCooldown(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(SCULK_ECHOER_PHASE, SculkEchoerPhase.COOLDOWN), 3);
        world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        if (!state.get(WATERLOGGED)) {
            world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.7F);
            world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F * 0.5F);
            world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 0.25F, world.random.nextFloat() * 0.1F + 0.9F * 0.3F);
        }
        updateNeighbors(world, pos);
    }

    public static void setActive(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int bubbles) {
        boolean canRun = true;
        if (entity!=null) {
            if (entity instanceof WardenEntity) { canRun=false; }
        }
        BlockEntity entity1 = world.getBlockEntity(pos);
        if (entity1 instanceof SculkEchoerBlockEntity echoer) {
            echoer.echoBubblesLeft = bubbles;
            echoer.bigBubble = true;
        }
        if (canRun) {
            int additionalCooldown = state.get(WATERLOGGED) ? 75 : 45;
            world.setBlockState(pos, state.with(SCULK_ECHOER_PHASE, SculkEchoerPhase.ACTIVE), 3);
            world.createAndScheduleBlockTick(pos, state.getBlock(), bubbles+additionalCooldown);
            updateNeighbors(world, pos);
            if (!state.get(WATERLOGGED)) {
                world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, RegisterSounds.BLOCK_SCULK_ECHOER_ECHO, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SculkEchoerBlockEntity echoerEntity) {
            return getPhase(state) == SculkEchoerPhase.ACTIVE ? echoerEntity.getLastVibrationFrequency() : 0;
        } else {
            return 0;
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SculkEchoerBlockEntity(pos, state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private static void updateNeighbors(World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, RegisterBlocks.SCULK_ECHOER);
        world.updateNeighborsAlways(pos.offset(Direction.UP.getOpposite()), RegisterBlocks.SCULK_ECHOER);
    }

    public int getRange() {
        return this.range;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        boolean isUpsideDown = ctx.getSide()==Direction.DOWN;
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(UPSIDEDOWN, isUpsideDown);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return blockEntity instanceof SculkEchoerBlockEntity ? ((SculkEchoerBlockEntity)blockEntity).getEventListener() : null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, RegisterBlockEntityType.SCULK_ECHOER, (worldx, pos, statex, blockEntity) -> {
            blockEntity.tick(worldx, pos, statex);
        }) : null;
    }
}
