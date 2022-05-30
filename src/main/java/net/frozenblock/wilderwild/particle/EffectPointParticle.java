package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class EffectPointParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    EffectPointParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.velocityMultiplier = 0.96F;
        this.spriteProvider = spriteProvider;
        this.collidesWithWorld = false;
        this.setSpriteForAge(this.spriteProvider);
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
    }

    @Environment(EnvType.CLIENT)
    public static record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            EffectPointParticle termite = new EffectPointParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            termite.setVelocity(g, h, i);
            termite.setMaxAge(clientWorld.random.nextInt(4) + 20);
            termite.scale(1.2F);
            return termite;
        }

        public SpriteProvider spriteProvider() {
            return this.spriteProvider;
        }
    }
}
