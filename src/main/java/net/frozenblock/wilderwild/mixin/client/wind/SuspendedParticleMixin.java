package net.frozenblock.wilderwild.mixin.client.wind;

import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.particle.SuspendedParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SuspendedParticle.class)
public class SuspendedParticleMixin  implements WilderDripSuspendedParticleInterface {

	@Unique
	private boolean wilderWild$usesWind;

	@Unique
	@Override
	public void wilderWild$setUsesWind(boolean bl) {
		this.wilderWild$usesWind = bl;
	}

	@Unique
	@Override
	public boolean wilderWild$usesWind() {
		return this.wilderWild$usesWind;
	}
}
