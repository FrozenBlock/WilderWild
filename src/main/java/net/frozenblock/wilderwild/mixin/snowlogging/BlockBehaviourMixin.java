package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

	@Inject(method = "isRandomlyTicking", at = @At("RETURN"), cancellable = true)
	protected void wilderWild$isRandomlyTicking(BlockState state, CallbackInfoReturnable<Boolean> info) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			info.setReturnValue(true);
		}
	}


}
