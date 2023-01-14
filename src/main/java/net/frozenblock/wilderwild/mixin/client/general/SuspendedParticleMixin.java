package net.frozenblock.wilderwild.mixin.client.general;

import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.particle.SuspendedParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SuspendedParticle.class)
public class SuspendedParticleMixin  implements WilderDripSuspendedParticleInterface {

	@Unique
	private boolean usesWind;

	@Unique
	@Override
	public void setUsesWind(boolean bl) {
		this.usesWind = bl;
	}

	@Unique
	@Override
	public boolean usesWind() {
		return this.usesWind;
	}
}
