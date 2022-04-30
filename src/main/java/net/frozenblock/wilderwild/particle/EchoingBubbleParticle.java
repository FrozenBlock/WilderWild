package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class EchoingBubbleParticle extends AbstractSlowingParticle {
    private final SpriteProvider spriteProvider;
    public int getBrightness(float f) {
        return 240;
    }

    protected EchoingBubbleParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, g, h, i);
        this.velocityX = (Math.random()-0.5)/9.5;
        this.velocityZ = (Math.random()-0.5)/9.5;
        this.spriteProvider = spriteProvider;
        this.maxAge=30;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT; }

    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            int i = Math.max(1, this.age-(this.maxAge-4));
            this.setSprite(spriteProvider.getSprite(i, 6));
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public static class BubbleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public BubbleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            EchoingBubbleParticle bubble = new EchoingBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            bubble.setAlpha(1.0F);
            bubble.velocityY = Math.max((Math.random())*0.06, 0.035);
            return bubble;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class DownwardsBubbleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public DownwardsBubbleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            EchoingBubbleParticle bubble = new EchoingBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            bubble.setAlpha(1.0F);
            bubble.velocityY = Math.max((Math.random())*0.06, 0.035)*-1;
            return bubble;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class BigBubbleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public BigBubbleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            EchoingBubbleParticle bubble = new EchoingBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            bubble.setAlpha(1.0F);
            bubble.velocityY = Math.max((Math.random())*0.065, 0.04);
            bubble.velocityX = (Math.random()-0.5)/10.5;
            bubble.velocityZ = (Math.random()-0.5)/10.5;
            bubble.scale(2.4F);
            return bubble;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class BigDownwardsBubbleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public BigDownwardsBubbleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            EchoingBubbleParticle bubble = new EchoingBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            bubble.setAlpha(1.0F);
            bubble.velocityY = Math.max((Math.random())*0.065, 0.04)*-1;
            bubble.velocityX = (Math.random()-0.5)/10.5;
            bubble.velocityZ = (Math.random()-0.5)/10.5;
            bubble.scale(2.4F);
            return bubble;
        }
    }
}
