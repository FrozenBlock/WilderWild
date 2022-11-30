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
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TermiteParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    public TermiteParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.friction = 0.96F;
        this.spriteProvider = spriteProvider;
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteProvider);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(spriteProvider.get(this.random.nextInt(this.lifetime), this.lifetime));
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
            TermiteParticle termite = new TermiteParticle(clientLevel, x, y, z, g, h, i, this.spriteProvider);
            termite.setAlpha(1.0F);
            termite.setParticleSpeed(g * 1.3, h * 1.3, i * 1.3);
            termite.setLifetime(clientLevel.random.nextInt(4) + 6);
            termite.scale(2.5F);
            return termite;
        }

        public SpriteSet spriteProvider() {
            return this.spriteProvider;
        }
    }
}
