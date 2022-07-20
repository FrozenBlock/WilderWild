package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
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

public class ShelfFungusBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty STAGE;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape NORTH_WALL_SHAPE;
    protected static final VoxelShape SOUTH_WALL_SHAPE;
    protected static final VoxelShape WEST_WALL_SHAPE;
    protected static final VoxelShape EAST_WALL_SHAPE;
    protected static final VoxelShape FLOOR_SHAPE;
    protected static final VoxelShape CEILING_SHAPE;

    public ShelfFungusBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(FACE, AttachFace.WALL).setValue(STAGE, 1));
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        int i = state.getValue(STAGE);
        if (i > 1 && itemStack.is(Items.SHEARS)) {
            popResource(world, pos, new ItemStack(state.getBlock().asItem()));
            world.setBlockAndUpdate(pos, state.setValue(STAGE, i - 1));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
            world.gameEvent(player, GameEvent.SHEAR, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hit);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, STAGE, WATERLOGGED);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(STAGE) < 4 || super.canBeReplaced(state, context);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState insideState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (insideState.is(this)) {
            return insideState.setValue(STAGE, Math.min(4, insideState.getValue(STAGE) + 1));
        }
        boolean waterlogged = insideState.hasProperty(BlockStateProperties.WATERLOGGED) ? insideState.getValue(BlockStateProperties.WATERLOGGED) : false;
        if (!waterlogged) {
            waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        }
        for (Direction direction : ctx.getNearestLookingDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, ctx.getHorizontalDirection()).setValue(WATERLOGGED, waterlogged);
            } else {
                blockState = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite()).setValue(WATERLOGGED, waterlogged);
            }
            if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                return blockState;
            }
        }
        return null;
    }

    public static AttachFace getFace(Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
        }
        return AttachFace.WALL;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACE)) {
            case FLOOR -> FLOOR_SHAPE;
            case WALL -> switch (state.getValue(FACING)) {
                case EAST -> EAST_WALL_SHAPE;
                case WEST -> WEST_WALL_SHAPE;
                case SOUTH -> SOUTH_WALL_SHAPE;
                default -> NORTH_WALL_SHAPE;
            };
            case CEILING -> CEILING_SHAPE;
        };
    }

    static {
        STAGE = RegisterProperties.FUNGUS_STAGE;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        NORTH_WALL_SHAPE = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_WALL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
        WEST_WALL_SHAPE = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        EAST_WALL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
        FLOOR_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
        CEILING_SHAPE = Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
