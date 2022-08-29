package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class FlowerLichenBlock extends MultifaceBlock {
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public FlowerLichenBlock(Properties settings) {
        super(settings);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            if (this.isFaceSupported(direction)) {
                builder.add(getFaceProperty(direction));
            }
        }
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        boolean bl = false;
        if (world.getBlockState(pos).is(Blocks.WATER)) {
            return false;
        }
        for (Direction direction : DIRECTIONS) {
            if (hasFace(state, direction)) {
                BlockPos blockPos = pos.relative(direction);
                if (!canAttachTo(world, direction, blockPos, world.getBlockState(blockPos))) {
                    return false;
                }
                bl = true;
            }
        }
        return bl;
    }

    public static boolean canAttachTo(BlockGetter world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFull(state.getBlockSupportShape(world, pos), direction.getOpposite()) || Block.isFaceFull(state.getCollisionShape(world, pos), direction.getOpposite()) && !world.getBlockState(pos).is(Blocks.WATER);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return grower;
    }
}
