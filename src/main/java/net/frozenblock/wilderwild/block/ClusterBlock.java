package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public interface ClusterBlock {

    @Nullable
    default BlockState getStateForPlacement(Block block, BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (!this.isValidStateForPlacement(block, world, state, pos, direction)) {
            return null;
        } else {
            BlockState blockState;
            if (state.is(block)) {
                blockState = state;
            } else if (this.isWaterloggable(block) && state.getFluidState().isSourceOfType(Fluids.WATER)) {
                blockState = block.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
            } else {
                blockState = block.defaultBlockState();
            }

            return blockState.setValue(MultifaceBlock.getFaceProperty(direction), true);
        }
    }

    default boolean isValidStateForPlacement(Block block, BlockGetter view, BlockState state, BlockPos pos, Direction dir) {
        if ((!state.is(block) || !MultifaceBlock.hasFace(state, dir))) {
            BlockPos blockPos = pos.relative(dir);
            return MultifaceBlock.canAttachTo(view, dir, blockPos, view.getBlockState(blockPos));
        } else {
            return false;
        }
    }

    private boolean isWaterloggable(Block block) {
        return block.getStateDefinition().getProperties().contains(BlockStateProperties.WATERLOGGED);
    }
}
