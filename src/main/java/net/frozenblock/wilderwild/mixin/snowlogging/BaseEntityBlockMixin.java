package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseEntityBlock.class)
public class BaseEntityBlockMixin {

	@Inject(method = "getRenderShape", at = @At("RETURN"), cancellable = true)
	protected void wilderWild$getRenderShape(BlockState state, CallbackInfoReturnable<RenderShape> info) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			info.setReturnValue(RenderShape.INVISIBLE);
		}
	}

}
