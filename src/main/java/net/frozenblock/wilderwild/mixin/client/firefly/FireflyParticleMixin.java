package net.frozenblock.wilderwild.mixin.client.firefly;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.client.particle.FireflyParticle;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(FireflyParticle.FireflyProvider.class)
public class FireflyParticleMixin {

	@ModifyReturnValue(
		method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;",
		at = @At("RETURN")
	)
	public Particle wilderWild$adjustFireflyParticleColor(Particle original) {
		if (WWConstants.ORIGINAL_FIREFLIES) {
			original.setColor(170F / 255F, 246F / 255F, 68F / 255F);
		}
		return original;
	}

}
