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
import org.jetbrains.annotations.NotNull;

public class FlowerLichenBlock extends MultifaceBlock {
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public FlowerLichenBlock(Properties settings) {
        super(settings);
    }

	@Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            if (this.isFaceSupported(direction)) {
                builder.add(getFaceProperty(direction));
            }
        }
    }

	@Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, @NotNull BlockPos pos) {
        boolean bl = false;
        if (level.getBlockState(pos).is(Blocks.WATER)) {
            return false;
        }
        for (Direction direction : DIRECTIONS) {
            if (hasFace(state, direction)) {
                BlockPos blockPos = pos.relative(direction);
                if (!canAttachToNoWater(level, direction, blockPos, level.getBlockState(blockPos))) {
                    return false;
                }
                bl = true;
            }
        }
        return bl;
    }

    public static boolean canAttachToNoWater(BlockGetter level, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite()) || Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite()) && !level.getBlockState(pos).is(Blocks.WATER);
    }

	@Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return grower;
    }
}
