package net.frozenblock.wilderwild.mixin.client.warden;

import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenRenderer.class)
public class WardenRendererMixin {

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/warden/Warden;Lnet/minecraft/client/renderer/entity/state/WardenRenderState;F)V", at = @At("TAIL"))
	private void extractWilderWarden(Warden warden, WardenRenderState renderState, float partialTick, CallbackInfo ci) {
		WilderWarden wilderWarden = (WilderWarden) warden;
		WilderWarden wilderRenderState = (WilderWarden) renderState;

		wilderRenderState.wilderWild$getDyingAnimationState().copyFrom(wilderWarden.wilderWild$getDyingAnimationState());
		wilderRenderState.wilderWild$getSwimmingDyingAnimationState().copyFrom(wilderWarden.wilderWild$getSwimmingDyingAnimationState());
		wilderRenderState.wilderWild$getKirbyDeathAnimationState().copyFrom(wilderWarden.wilderWild$getKirbyDeathAnimationState());
		wilderRenderState.wilderWild$setDeathTicks(wilderWarden.wilderWild$getDeathTicks());
		wilderRenderState.wilderWild$setIsStella(wilderWarden.wilderWild$isStella());
	}
}
