package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CypressRootsBlock extends Block implements Waterloggable {
    public static final IntProperty ROOTS;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty UPSIDEDOWN;
    protected static final VoxelShape FLOOR_SHAPE;
    protected static final VoxelShape CEILING_SHAPE;

    public CypressRootsBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(ROOTS, 1).with(UPSIDEDOWN, false));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        int i = state.get(ROOTS);
        if (i > 1 && itemStack.isOf(Items.SHEARS)) {
            dropStack(world, pos, new ItemStack(state.getBlock().asItem()));
            world.setBlockState(pos, state.with(ROOTS, i - 1));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
            world.emitGameEvent(player, GameEvent.SHEAR, pos);
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (!canSurvive(world, pos)) {
            world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!canSurvive(world, pos)) {
            world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos, Direction.UP) || world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos, Direction.DOWN);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROOTS, UPSIDEDOWN, WATERLOGGED);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(ROOTS) < 4 || super.canReplace(state, context);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState insideState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (insideState.isOf(this)) {
            return insideState.with(ROOTS, Math.min(4, insideState.get(ROOTS) + 1));
        }
        boolean waterlogged = insideState.contains(Properties.WATERLOGGED) ? insideState.get(Properties.WATERLOGGED) : false;
        if (!waterlogged) {
            waterlogged = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        }
        if (ctx.getSide() == Direction.DOWN) {
            if (canSurvive(ctx.getWorld(), ctx.getBlockPos(), true)) {
                return this.getDefaultState().with(WATERLOGGED, waterlogged);
            }
        }
        return this.getDefaultState().with(WATERLOGGED, waterlogged);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if (!canSurvive(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!canSurvive(world, pos)) {
            world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        }
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public static boolean canSurvive(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof CypressRootsBlock) {
            boolean upsideDown = world.getBlockState(pos).get(UPSIDEDOWN);
            return !upsideDown ? world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) : world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN);
        }
        return false;
    }

    public static boolean canSurvive(WorldAccess world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof CypressRootsBlock) {
            boolean upsideDown = world.getBlockState(pos).get(UPSIDEDOWN);
            return !upsideDown ? world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) : world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN);
        }
        return false;
    }

    public static boolean canSurvive(World world, BlockPos pos, boolean upsideDown) {
        return !upsideDown ? world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) : world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(UPSIDEDOWN)) {
            return CEILING_SHAPE;
        }
        return FLOOR_SHAPE;
    }

    static {
        ROOTS = RegisterProperties.ROOTS;
        WATERLOGGED = Properties.WATERLOGGED;
        UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
        FLOOR_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
        CEILING_SHAPE = Block.createCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
