package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class BaobabLeaves extends LeavesBlock
        implements BonemealableBlock {
    public BaobabLeaves(final BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public boolean isValidBonemealTarget(
            final BlockGetter level,
            final BlockPos pos,
            final @NotNull BlockState state,
            final boolean isClient
    ) {
        return level.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(
            final @NotNull Level level,
            final @NotNull RandomSource random,
            final @NotNull BlockPos pos,
            final @NotNull BlockState state
    ) {
        return true;
    }

    @Override
    public void performBonemeal(
            final ServerLevel level,
            final @NotNull RandomSource random,
            final BlockPos pos,
            final @NotNull BlockState state
    ) {
        level.setBlock(pos.below(), BaobabNutBlock.getDefaultHangingState(), 2);
    }
}
