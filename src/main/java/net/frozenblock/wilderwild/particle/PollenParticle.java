package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PollenParticle extends TextureSheetParticle {
    public double windIntensity;

    PollenParticle(ClientLevel level, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setSize(0.01F, 0.02F);
        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int) (16.0D / (RandomSource.create().nextDouble() * 0.8D + 0.2D));
        this.hasPhysics = true;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
		this.windIntensity *= 0.94F;
		boolean onGround = this.onGround;
		double multXZ = (onGround ? 0.0005 : 0.007) * this.windIntensity;
		double multY = (onGround ? 0.0005 : 0.0035) * this.windIntensity;
		this.xd += ClientWindManager.getWindX(1F) * multXZ;
		this.yd += (ClientWindManager.getWindY(1F) + 0.1) * multY;
		this.zd += ClientWindManager.getWindZ(1F) * multXZ;
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

        public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
            PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, x, y, z, 0.0D, -0.800000011920929D, 0.0D) {
            };
            pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
            pollenParticle.gravity = 0.01F;
            pollenParticle.setColor(250F / 255F, 171F / 255F, 28F / 255F);
			pollenParticle.windIntensity = 0.05;
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
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            double windex = ClientWindManager.getWindX(1) * 1.1;
            double windZ = ClientWindManager.getWindZ(1) * 1.1;
            PollenParticle pollenParticle = new PollenParticle(level, this.spriteProvider, x, y, z, windex, -0.800000011920929D, windZ);
            pollenParticle.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
            pollenParticle.gravity = 0.01F;
            pollenParticle.xd = (windex + level.random.triangle(0, 0.8)) / 17;
            pollenParticle.zd = (windZ + level.random.triangle(0, 0.8)) / 17;
            pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
            pollenParticle.windIntensity = 1;
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
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			double windex = ClientWindManager.getWindX(1) * 1.1;
			double windZ = ClientWindManager.getWindZ(1) * 1.1;
            PollenParticle pollenParticle = new PollenParticle(level, this.spriteProvider, x, y, z, windex, -0.800000011920929D, windZ);
            pollenParticle.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
            pollenParticle.gravity = 0.016F;
            pollenParticle.xd = (windex + level.random.triangle(0, 0.8)) / 20;
            pollenParticle.zd = (windZ + level.random.triangle(0, 0.8)) / 20;
            pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
            pollenParticle.windIntensity = 1;
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ControlledDandelionFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public ControlledDandelionFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            double windex = g * 1.1;
            double windZ = i * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, d, e, f, windex, (h / 2) - 0.800000011920929D, windZ);
            pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
            pollenParticle.gravity = 0.01F;
            pollenParticle.xd = (windex + clientLevel.random.triangle(0, 0.8)) / 17;
            pollenParticle.zd = (windZ + clientLevel.random.triangle(0, 0.8)) / 17;
            pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
            pollenParticle.windIntensity = 0.5;
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ControlledMilkweedFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public ControlledMilkweedFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            double windex = g * 1.1;
            double windZ = i * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, d, e, f, windex, (h / 2) - 0.800000011920929D, windZ);
            pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
            pollenParticle.gravity = 0.016F;
            pollenParticle.xd = (windex + clientLevel.random.triangle(0, 0.8)) / 20;
            pollenParticle.zd = (windZ + clientLevel.random.triangle(0, 0.8)) / 20;
            pollenParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
            pollenParticle.windIntensity = 0.5;
            return pollenParticle;
        }
    }
}
