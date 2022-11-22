package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.block.PalmLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @Inject(method = "updateDistance", at = @At("HEAD"), cancellable = true)
	private static void updateDistance(BlockState state, LevelAccessor level, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
		if (state.getBlock() instanceof PalmLeavesBlock) {
			int i = 7;
			for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
				i = Math.min(i, PalmLeavesBlock.getDistanceAt(level.getBlockState(blockPos)) + 1);
				if (i == 1) break;
			}
			info.setReturnValue(state.setValue(BlockStateProperties.DISTANCE, i));
		}
	}

}
