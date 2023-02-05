package net.frozenblock.wilderwild.mixin.client.wind;

import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DripParticle.SporeBlossomFallProvider.class)
public class SporeBlossomFallProviderMixin {

	@Inject(method = "createParticle", at = @At("TAIL"))
	public void wilderWild$createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfoReturnable<Particle> info) {
		if (info.getReturnValue() instanceof DripParticle dripParticle) {
			((WilderDripSuspendedParticleInterface)dripParticle).wilderWild$setUsesWind(true);
		}
	}
}
