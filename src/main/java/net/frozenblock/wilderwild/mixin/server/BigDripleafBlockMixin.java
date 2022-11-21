package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BigDripleafBlock.class)
public final class BigDripleafBlockMixin {

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tickStem(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		BlockState downState = level.getBlockState(pos.below());
		if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
			resetTilt(state, level, pos);
			info.cancel();
		}
	}

	@Inject(method = "neighborChanged", at = @At("HEAD"), cancellable = true)
	public void neighborStemChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving, CallbackInfo info) {
		if (fromPos == pos.below()) {
			BlockState downState = level.getBlockState(pos.below());
			if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
				resetTilt(state, level, pos);
				info.cancel();
			}
		}
	}

	@Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo info) {
		if (!level.isClientSide) {
			BlockState downState = level.getBlockState(pos.below());
			if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
				info.cancel();
			}
		}
	}

	@Shadow
	private static void resetTilt(BlockState state, Level level, BlockPos pos) {
		throw new AssertionError("Mixin injection failed - WilderWild BigDripleafBlockMixin.");
	}

}
