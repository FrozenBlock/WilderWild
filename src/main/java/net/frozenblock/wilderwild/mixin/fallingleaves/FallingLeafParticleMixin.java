package net.frozenblock.wilderwild.mixin.fallingleaves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import randommcsomethin.fallingleaves.particle.FallingLeafParticle;

@Mixin(FallingLeafParticle.class)
public class FallingLeafParticleMixin {

	@Unique
	private static boolean wilderWild$useWind() {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind();
	}

	@ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lrandommcsomethin/fallingleaves/util/Wind;windX:F"))
	private float wilderWild$modifyWindX(float original) {
		if (wilderWild$useWind()) {
			return (float) ClientWindManager.windX * 0.7F;
		}
		return original;
	}

	@ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lrandommcsomethin/fallingleaves/util/Wind;windZ:F"))
	private float wilderWild$modifyWindZ(float original) {
		if (wilderWild$useWind()) {
			return (float) ClientWindManager.windZ * 0.7F;
		}
		return original;
	}

}
