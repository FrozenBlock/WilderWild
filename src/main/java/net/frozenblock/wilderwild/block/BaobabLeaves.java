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

public class BaobabLeaves extends LeavesBlock implements BonemealableBlock {
	public BaobabLeaves(BlockBehaviour.Properties settings) {
		super(settings);
	}

	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return level.getBlockState(pos.below()).isAir();
	}

	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}


	public void performBonemeal(ServerLevel level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos.below(), BaobabNutBlock.getDefaultHangingState(), 2);
	}
}
