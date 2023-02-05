package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

	@Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
	public void wilderWild$getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info) {
		if (BlockBehaviour.class.cast(this) instanceof BigDripleafStemBlock bigDripleafStemBlock) {
			info.setReturnValue(bigDripleafStemBlock.defaultBlockState().setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())));
		}
	}

	@Inject(at = @At("HEAD"), method = "neighborChanged", cancellable = true)
	public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos fromPos, boolean isMoving, CallbackInfo info) {
		if (BlockBehaviour.class.cast(this) instanceof BigDripleafStemBlock bigDripleafStemBlock) {
			if (!level.isClientSide) {
				BlockState fromState = level.getBlockState(fromPos);
				if (fromState.is(Blocks.BIG_DRIPLEAF_STEM)) {
					if (pos.getY() > fromPos.getY()) {
						level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, fromState.getValue(BlockStateProperties.POWERED)), 3);
					}
				} else {
					boolean powered = state.getValue(BlockStateProperties.POWERED);
					boolean power = level.hasNeighborSignal(pos);
					if (powered != power) {
						if (powered) {
							level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, false), 3);
						} else {
							level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, true), 3);
						}
					}
				}
			}
			info.cancel();
		}
	}

}
