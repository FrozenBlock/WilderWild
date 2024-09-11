package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.block.impl.BlockBehaviourSnowloggingInterface;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin implements BlockBehaviourSnowloggingInterface {

	@Inject(method = "isRandomlyTicking", at = @At("RETURN"), cancellable = true)
	protected void wilderWild$isRandomlyTicking(BlockState state, CallbackInfoReturnable<Boolean> info) {
		if (this.wilderWild$snowlogging && SnowloggingUtils.isSnowlogged(state)) {
			info.setReturnValue(true);
		}
	}

	@Unique
	private boolean wilderWild$snowlogging = false;

	@Override
	public void wilderWild$enableSnowlogging() {
		this.wilderWild$snowlogging = true;
	}

	@Override
	public boolean wilderWild$hasSnowlogging() {
		return this.wilderWild$snowlogging;
	}
}
