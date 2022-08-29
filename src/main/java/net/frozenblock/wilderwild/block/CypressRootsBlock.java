package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CypressRootsBlock extends Block implements SimpleWaterloggedBlock {
    public static final IntegerProperty ROOTS = RegisterProperties.ROOTS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
    protected static final VoxelShape FLOOR_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    protected static final VoxelShape CEILING_SHAPE = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public CypressRootsBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(ROOTS, 1).setValue(UPSIDEDOWN, false));
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        int i = state.getValue(ROOTS);
        if (i > 1 && itemStack.is(Items.SHEARS)) {
            popResource(world, pos, new ItemStack(state.getBlock().asItem()));
            world.setBlockAndUpdate(pos, state.setValue(ROOTS, i - 1));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
            world.gameEvent(player, GameEvent.SHEAR, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
        if (!canSurvive(world, pos)) {
            world.scheduleTick(pos, state.getBlock(), 1);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        if (!canSurvive(world, pos)) {
            world.scheduleTick(pos, state.getBlock(), 1);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isFaceSturdy(world, pos, Direction.UP) || world.getBlockState(pos.above()).isFaceSturdy(world, pos, Direction.DOWN);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROOTS, UPSIDEDOWN, WATERLOGGED);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(ROOTS) < 4 || super.canBeReplaced(state, context);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState insideState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (insideState.is(this)) {
            return insideState.setValue(ROOTS, Math.min(4, insideState.getValue(ROOTS) + 1));
        }
        boolean waterlogged = insideState.hasProperty(BlockStateProperties.WATERLOGGED) ? insideState.getValue(BlockStateProperties.WATERLOGGED) : false;
        if (!waterlogged) {
            waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        }
        if (ctx.getClickedFace() == Direction.DOWN) {
            if (canSurvive(ctx.getLevel(), ctx.getClickedPos(), true)) {
                return this.defaultBlockState().setValue(WATERLOGGED, waterlogged);
            }
        }
        return this.defaultBlockState().setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        if (!canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!canSurvive(world, pos)) {
            world.scheduleTick(pos, state.getBlock(), 1);
        }
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static boolean canSurvive(Level world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof CypressRootsBlock) {
            boolean upsideDown = world.getBlockState(pos).getValue(UPSIDEDOWN);
            return !upsideDown ? world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP) : world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.DOWN);
        }
        return false;
    }

    public static boolean canSurvive(LevelAccessor world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof CypressRootsBlock) {
            boolean upsideDown = world.getBlockState(pos).getValue(UPSIDEDOWN);
            return !upsideDown ? world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP) : world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.DOWN);
        }
        return false;
    }

    public static boolean canSurvive(Level world, BlockPos pos, boolean upsideDown) {
        return !upsideDown ? world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP) : world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.DOWN);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(UPSIDEDOWN)) {
            return CEILING_SHAPE;
        }
        return FLOOR_SHAPE;
    }
}
