package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BigDripleafBlock.class)
public final class BigDripleafBlockMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void wilderWild$tickStem(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
        BlockState downState = level.getBlockState(pos.below());
        if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
            resetTilt(state, level, pos);
            info.cancel();
        }
    }

    @Inject(method = "neighborChanged", at = @At("HEAD"), cancellable = true)
    public void wilderWild$neighborStemChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving, CallbackInfo info) {
        if (fromPos == pos.below()) {
            BlockState downState = level.getBlockState(pos.below());
            if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
                resetTilt(state, level, pos);
                info.cancel();
            }
        }
    }

	@Inject(method = "updateShape", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
	public void wilderWild$updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
		BlockState downState = level.getBlockState(currentPos.below());
		if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
			info.setReturnValue(Blocks.BIG_DRIPLEAF_STEM.withPropertiesOf(state).setValue(BlockStateProperties.POWERED, true));
		}
	}

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    public void wilderWild$entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo info) {
        if (!level.isClientSide) {
            BlockState downState = level.getBlockState(pos.below());
            if (downState.is(Blocks.BIG_DRIPLEAF_STEM) && downState.getValue(BlockStateProperties.POWERED)) {
                info.cancel();
            }
        }
    }

    @Shadow
    private static void resetTilt(BlockState state, Level level, BlockPos pos) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BigDripleafBlockMixin.");
    }

}
