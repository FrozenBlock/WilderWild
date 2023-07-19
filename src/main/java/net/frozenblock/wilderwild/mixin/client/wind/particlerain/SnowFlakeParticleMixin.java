package net.frozenblock.wilderwild.mixin.client.wind.particlerain;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.MiscConfig;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.particlerain.particle.SnowFlakeParticle;

@Mixin(SnowFlakeParticle.class)
public class SnowFlakeParticleMixin {

	@Unique
	private static boolean wilderWild$useWind() {
		return MiscConfig.get().cloudMovement && ClientWindManager.shouldUseWind();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void wilderWild$modifyWind(CallbackInfo info) {
		if (wilderWild$useWind()) {
			SnowFlakeParticle.class.cast(this).xd += Mth.clamp(ClientWindManager.windX, -0.0015, 0.0015);
			SnowFlakeParticle.class.cast(this).yd += Mth.clamp(ClientWindManager.windY * 0.1, -0.0005, 0.0005);
			SnowFlakeParticle.class.cast(this).zd += Mth.clamp(ClientWindManager.windZ, -0.0015, 0.0015);
		}
	}

}
