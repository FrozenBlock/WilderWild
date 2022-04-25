package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PollenParticle extends SpriteBillboardParticle {
    PollenParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z) {
        super(world, x, y - 0.125D, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.2F;
        this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.0F;
    }

    PollenParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.6F;
        this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.0F;
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
}
