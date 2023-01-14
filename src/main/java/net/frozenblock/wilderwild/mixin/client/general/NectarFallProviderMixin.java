package net.frozenblock.wilderwild.mixin.client.general;

import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DripParticle.NectarFallProvider.class)
public class NectarFallProviderMixin {

	@Inject(method = "tick", at = @At("TAIL"))
	public void createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfoReturnable<Particle> info) {
		if (info.getReturnValue() instanceof DripParticle dripParticle) {
			((WilderDripSuspendedParticleInterface)dripParticle).setUsesWind(true);
		}
	}
}
