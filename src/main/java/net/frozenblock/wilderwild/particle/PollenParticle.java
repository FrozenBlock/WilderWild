package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PollenParticle extends SpriteBillboardParticle {
    public boolean hasCarryingWind;
    public int boostTicksLeft;
    public boolean alreadyBoosted;
    PollenParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01F, 0.02F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.6F;
        this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.alreadyBoosted && this.age>this.maxAge/5 && !this.onGround && this.hasCarryingWind) {
            if (random.nextFloat() > 0.98) {
                if (random.nextFloat() > 0.55) {
                    this.boostTicksLeft = 5;
                }
                this.alreadyBoosted=true;
            }
        }
        if (this.boostTicksLeft>0) {
            --this.boostTicksLeft;
            if (!this.onGround) {
                this.velocityY += (0.034/(this.boostTicksLeft+1));
                this.velocityX += this.velocityX*(0.15/(this.boostTicksLeft+1));
                this.velocityZ += this.velocityZ*(0.15/(this.boostTicksLeft+1));
            }
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class PollenFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public PollenFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, 0.0D, -0.800000011920929D, 0.0D) {};
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.01F;
            pollenParticle.setColor(250F/255F, 171F/255F, 28F/255F);
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class DandelionFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DandelionFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            double windex = Math.cos((clientWorld.getTimeOfDay()*Math.PI)/12000) * 1.1;
            double windZ = -Math.sin((clientWorld.getTimeOfDay()*Math.PI)/12000) * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, windex, -0.800000011920929D, windZ);
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.01F;
            pollenParticle.velocityX = (windex + clientWorld.random.nextTriangular(0, 0.8))/17;
            pollenParticle.velocityZ = (windZ + clientWorld.random.nextTriangular(0, 0.8))/17;
            pollenParticle.setColor(250F/255F, 250F/255F, 250F/255F);
            pollenParticle.hasCarryingWind = true;
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class MilkweedFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public MilkweedFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            double windex = Math.cos((clientWorld.getTimeOfDay()*Math.PI)/12000) * 1.1;
            double windZ = -Math.sin((clientWorld.getTimeOfDay()*Math.PI)/12000) * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, windex, -0.800000011920929D, windZ);
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.016F;
            pollenParticle.velocityX = (windex + clientWorld.random.nextTriangular(0, 0.8))/20;
            pollenParticle.velocityZ = (windZ + clientWorld.random.nextTriangular(0, 0.8))/20;
            pollenParticle.setColor(250F/255F, 250F/255F, 250F/255F);
            pollenParticle.hasCarryingWind = true;
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ControlledDandelionFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public ControlledDandelionFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            double windex = g * 1.1;
            double windZ = i * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, windex, (h/2) -0.800000011920929D, windZ);
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.01F;
            pollenParticle.velocityX = (windex + clientWorld.random.nextTriangular(0, 0.8))/17;
            pollenParticle.velocityZ = (windZ + clientWorld.random.nextTriangular(0, 0.8))/17;
            pollenParticle.setColor(250F/255F, 250F/255F, 250F/255F);
            pollenParticle.hasCarryingWind = true;
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ControlledMilkweedFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public ControlledMilkweedFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            double windex = g * 1.1;
            double windZ = i * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, windex, (h/2) -0.800000011920929D, windZ);
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.016F;
            pollenParticle.velocityX = (windex + clientWorld.random.nextTriangular(0, 0.8))/20;
            pollenParticle.velocityZ = (windZ + clientWorld.random.nextTriangular(0, 0.8))/20;
            pollenParticle.setColor(250F/255F, 250F/255F, 250F/255F);
            pollenParticle.hasCarryingWind = true;
            return pollenParticle;
        }
    }

}
