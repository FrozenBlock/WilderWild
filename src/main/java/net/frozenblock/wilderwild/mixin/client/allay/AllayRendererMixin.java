package net.frozenblock.wilderwild.mixin.client.allay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.render.animation.WilderAllay;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.client.renderer.entity.state.AllayRenderState;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AllayRenderer.class)
public class AllayRendererMixin {

	@ModifyExpressionValue(
		method = "extractRenderState(Lnet/minecraft/world/entity/animal/allay/Allay;Lnet/minecraft/client/renderer/entity/state/AllayRenderState;F)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/allay/Allay;isDancing()Z",
			ordinal = 0
		),
		require = 0
	)
	private boolean wilderWild$alterDanceCheck(boolean original) {
		return original && !WWEntityConfig.Client.KEYFRAME_ALLAY_DANCE;
	}

	@Inject(
		method = "extractRenderState(Lnet/minecraft/world/entity/animal/allay/Allay;Lnet/minecraft/client/renderer/entity/state/AllayRenderState;F)V",
		at = @At("TAIL")
	)
	private void extractAnimation(Allay allay, AllayRenderState renderState, float partialTick, CallbackInfo ci) {
		WilderAllay wilderAllay = (WilderAllay) allay;
		WilderAllay wilderRenderState = (WilderAllay) renderState;

		wilderRenderState.wilderWild$getDancingAnimationState().copyFrom(wilderAllay.wilderWild$getDancingAnimationState());
	}
}
