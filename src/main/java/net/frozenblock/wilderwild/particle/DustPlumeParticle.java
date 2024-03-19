package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;

@Environment(EnvType.CLIENT)
public class DustPlumeParticle extends BaseAshSmokeParticle {
	private static final int COLOR_RGB24 = 12235202;

	protected DustPlumeParticle(ClientLevel world, double d, double e, double f, double g, double h, double i, float j, SpriteSet spriteSet) {
		super(world, d, e, f, 0.7F, 0.6F, 0.7F, g, h + 0.15F, i, j, spriteSet, 0.5F, 7, 0.5F, false);
		float k = (float)Math.random() * 0.2F;
		this.rCol = (float) FastColor.ARGB32.red(12235202) / 255.0F - k;
		this.gCol = (float)FastColor.ARGB32.green(12235202) / 255.0F - k;
		this.bCol = (float)FastColor.ARGB32.blue(12235202) / 255.0F - k;
	}

	@Override
	public void tick() {
		this.gravity = 0.88F * this.gravity;
		this.friction = 0.92F * this.friction;
		super.tick();
	}

	@Environment(EnvType.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DustPlumeParticle(clientLevel, d, e, f, g, h, i, 1.0F, this.sprites);
		}
	}
}
