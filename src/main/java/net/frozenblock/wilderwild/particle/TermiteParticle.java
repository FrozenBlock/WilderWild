package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class TermiteParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    TermiteParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.velocityMultiplier = 0.96F;
        this.spriteProvider = spriteProvider;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        if (!this.dead) {
            this.setSprite(spriteProvider.getSprite(this.random.nextInt(this.maxAge), this.maxAge));
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double g, double h, double i) {
            TermiteParticle termite = new TermiteParticle(clientWorld, x, y, z, g, h, i, this.spriteProvider);
            termite.setAlpha(1.0F);
            termite.setVelocity(g, h, i);
            termite.setMaxAge(clientWorld.random.nextInt(4) + 6);
            termite.scale(2.0F);
            return termite;
        }

        public SpriteProvider spriteProvider() {
            return this.spriteProvider;
        }
    }
}
