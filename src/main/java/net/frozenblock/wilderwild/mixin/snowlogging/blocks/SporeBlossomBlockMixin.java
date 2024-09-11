package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SporeBlossomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SporeBlossomBlock.class)
public class SporeBlossomBlockMixin {

	@Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
	private void wilderWild$animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (SnowloggingUtils.getSnowLayers(state) >= 7) {
			info.cancel();
		}
	}
}
