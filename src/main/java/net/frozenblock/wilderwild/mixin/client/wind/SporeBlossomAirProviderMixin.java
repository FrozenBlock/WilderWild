package net.frozenblock.wilderwild.mixin.client.wind;

import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SuspendedParticle.SporeBlossomAirProvider.class)
public class SporeBlossomAirProviderMixin {

	@Inject(method = "createParticle", at = @At("TAIL"))
	public void wilderWild$createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfoReturnable<Particle> info) {
		if (info.getReturnValue() instanceof SuspendedParticle suspendedParticle) {
			((WilderDripSuspendedParticleInterface)suspendedParticle).wilderWild$setUsesWind(true);
		}
	}
}
