package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {

	@Inject(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void wilderWild$getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info, BlockState blockState) {
		if (blockState.hasProperty(RegisterProperties.SNOWLOGGED)) {
			if (!blockState.getValue(RegisterProperties.SNOWLOGGED)) {
				info.setReturnValue(blockState.setValue(RegisterProperties.SNOWLOGGED, true));
			} else {
				info.setReturnValue(SnowLayerBlock.class.cast(this).defaultBlockState().setValue(BlockStateProperties.LAYERS, 2));
			}
		}
	}

}
