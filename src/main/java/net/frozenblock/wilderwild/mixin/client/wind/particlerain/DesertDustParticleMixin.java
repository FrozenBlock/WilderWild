package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.particle.DesertDustParticle;

@Mixin(DesertDustParticle.class)
public class DesertDustParticleMixin {

	@Unique
	private static boolean wilderWild$useWind() {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void wilderWild$modifyWind(CallbackInfo info) {
		if (wilderWild$useWind()) {
			DesertDustParticle.class.cast(this).xd = Mth.clamp(ClientWindManager.windX * 3, -0.4, 0.4);
			DesertDustParticle.class.cast(this).yd += Mth.clamp(ClientWindManager.windY * 0.1, -0.01, 0.01);
			DesertDustParticle.class.cast(this).zd = Mth.clamp(ClientWindManager.windZ * 3, -0.4, 0.4);
		}
	}

}
