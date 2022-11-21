package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public final class BlockEntityTypeMixin {

	@Inject(method = "isValid", at = @At("RETURN"), cancellable = true)
	private void isValid(BlockState state, CallbackInfoReturnable<Boolean> info) {
		if (BlockEntityType.SIGN.equals(this) && (state.getBlock() instanceof StandingSignBlock || state.getBlock() instanceof WallSignBlock)) {
			info.setReturnValue(true);
		}
	}

}
