package net.frozenblock.wilderwild.mixin.snowlogging.blocks.candle;

import net.frozenblock.wilderwild.block.impl.BlockBehaviourSnowloggingInterface;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

	@Inject(method = "onPlace", at = @At("HEAD"))
	protected void wilderWild$onCandlePlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo info) {
		BlockBehaviour blockBehaviour = BlockBehaviour.class.cast(this);
		if (blockBehaviour instanceof BlockBehaviourSnowloggingInterface snowloggingInterface) {
			if (snowloggingInterface.wilderWild$hasSnowlogging() && blockBehaviour instanceof CandleBlock) {
				if (state.getValue(CandleBlock.LIT) && (SnowloggingUtils.getSnowLayers(state) >= 3)) {
					CandleBlock.extinguish(null, state, world, pos);
				}
			}
		}
	}
}
