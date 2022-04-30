package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class EchoingBubbleParticle extends AbstractSlowingParticle {
    private final SpriteProvider spriteProvider;
    private SoundEvent sound;
    public int getBrightness(float f) {
        return 240;
    }

    protected EchoingBubbleParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, g, h, i);
        this.velocityX = (Math.random()-0.5)/9.5;
        this.velocityZ = (Math.random()-0.5)/9.5;
        this.spriteProvider = spriteProvider;
        this.maxAge = 30;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT; }

    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            int i = this.age;
            if (this.age<=26 && this.age>3) { i=3; }
            if (this.age>=27) { i=this.age-23;}
            this.setSprite(spriteProvider.getSprite(i, 7));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age==27) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client!=null) {
                world.playSound(client.player, this.x, this.y, this.z, this.sound, SoundCategory.BLOCKS, 0.4F, world.random.nextFloat() * 0.2F + 0.8F);
            }
        }
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
            bubble.sound = RegisterSounds.BLOCK_SCULK_ECHOER_BUBBLE_POP;
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
            bubble.sound = RegisterSounds.BLOCK_SCULK_ECHOER_BUBBLE_POP;
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
            bubble.sound = RegisterSounds.BLOCK_SCULK_ECHOER_BUBBLE_POP_BIG;
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
            bubble.sound = RegisterSounds.BLOCK_SCULK_ECHOER_BUBBLE_POP_BIG;
            return bubble;
        }
    }
}
