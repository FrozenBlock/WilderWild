package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class WhiteSmokeParticle extends BaseAshSmokeParticle {
	private static final int COLOR_RGB24 = 12235202;

	protected WhiteSmokeParticle(ClientLevel world, double d, double e, double f, double g, double h, double i, float j, SpriteSet spriteSet) {
		super(world, d, e, f, 0.1F, 0.1F, 0.1F, g, h, i, j, spriteSet, 0.3F, 8, -0.1F, true);
		this.rCol = 0.7294118F;
		this.gCol = 0.69411767F;
		this.bCol = 0.7607843F;
	}

	@Environment(EnvType.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new WhiteSmokeParticle(clientLevel, d, e, f, g, h, i, 1.0F, this.sprites);
		}
	}
}
