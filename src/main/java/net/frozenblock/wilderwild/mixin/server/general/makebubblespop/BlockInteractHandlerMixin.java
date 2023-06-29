package net.frozenblock.wilderwild.mixin.server.general.makebubblespop;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.tschipcraft.make_bubbles_pop.event.BlockInteractHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockInteractHandler.class)
public class BlockInteractHandlerMixin {

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void wilderWild$cancelBubbles(Player player, Level world, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> info) {
		info.setReturnValue(InteractionResult.PASS);
	}

}
