package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PollenParticle extends TextureSheetParticle {
	public boolean hasCarryingWind;
	public int boostTicksLeft;
	public boolean alreadyBoosted;

	PollenParticle(ClientLevel level, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
		this.setSize(0.01F, 0.02F);
		this.pickSprite(spriteProvider);
		this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
		this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		this.hasPhysics = true;
		this.friction = 1.0F;
		this.gravity = 0.0F;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.alreadyBoosted && this.age > this.lifetime / 5 && !this.onGround && this.hasCarryingWind) {
			if (random.nextFloat() > 0.98) {
				if (random.nextFloat() > 0.55) {
					this.boostTicksLeft = 5;
				}
				this.alreadyBoosted = true;
			}
		}
		if (this.boostTicksLeft > 0) {
			--this.boostTicksLeft;
			if (!this.onGround) {
				this.yd += (0.034 / (this.boostTicksLeft + 1));
				this.xd += this.xd * (0.15 / (this.boostTicksLeft + 1));
				this.zd += this.zd * (0.15 / (this.boostTicksLeft + 1));
			}
		}
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Environment(EnvType.CLIENT)
	public static class PollenFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public PollenFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
			PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, x, y, z, 0.0D, -0.800000011920929D, 0.0D) {
			};
			pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
			pollenParticle.gravity = 0.01F;
			pollenParticle.setColor(250F / 255F, 171F / 255F, 28F / 255F);
			return pollenParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class DandelionFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public DandelionFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			double windex = Math.cos((level.getDayTime() * Math.PI) / 12000) * 1.1;
			double windZ = -Math.sin((level.getDayTime() * Math.PI) / 12000) * 1.1;
			PollenParticle pollenParticle = new PollenParticle(level, this.spriteProvider, x, y, z, windex, -0.800000011920929D, windZ);
			pollenParticle.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
			pollenParticle.gravity = 0.01F;
			pollenParticle.xd = (windex + level.random.triangle(0, 0.8)) / 17;
			pollenParticle.zd = (windZ + level.random.triangle(0, 0.8)) / 17;
			pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			pollenParticle.hasCarryingWind = true;
			return pollenParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class MilkweedFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public MilkweedFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			double windex = Math.cos((level.getDayTime() * Math.PI) / 12000) * 1.1;
			double windZ = -Math.sin((level.getDayTime() * Math.PI) / 12000) * 1.1;
			PollenParticle pollenParticle = new PollenParticle(level, this.spriteProvider, x, y, z, windex, -0.800000011920929D, windZ);
			pollenParticle.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
			pollenParticle.gravity = 0.016F;
			pollenParticle.xd = (windex + level.random.triangle(0, 0.8)) / 20;
			pollenParticle.zd = (windZ + level.random.triangle(0, 0.8)) / 20;
			pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			pollenParticle.hasCarryingWind = true;
			return pollenParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class ControlledDandelionFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public ControlledDandelionFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			double windex = g * 1.1;
			double windZ = i * 1.1;
			PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, d, e, f, windex, (h / 2) - 0.800000011920929D, windZ);
			pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
			pollenParticle.gravity = 0.01F;
			pollenParticle.xd = (windex + clientLevel.random.triangle(0, 0.8)) / 17;
			pollenParticle.zd = (windZ + clientLevel.random.triangle(0, 0.8)) / 17;
			pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			pollenParticle.hasCarryingWind = true;
			return pollenParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class ControlledMilkweedFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public ControlledMilkweedFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			double windex = g * 1.1;
			double windZ = i * 1.1;
			PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, d, e, f, windex, (h / 2) - 0.800000011920929D, windZ);
			pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
			pollenParticle.gravity = 0.016F;
			pollenParticle.xd = (windex + clientLevel.random.triangle(0, 0.8)) / 20;
			pollenParticle.zd = (windZ + clientLevel.random.triangle(0, 0.8)) / 20;
			pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			pollenParticle.hasCarryingWind = true;
			return pollenParticle;
		}
	}

}
