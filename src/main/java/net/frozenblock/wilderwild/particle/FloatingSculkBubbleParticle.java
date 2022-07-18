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
import net.minecraft.util.math.MathHelper;

public class FloatingSculkBubbleParticle extends AbstractSlowingParticle {
    private final SpriteProvider spriteProvider;
    private final SoundEvent sound;
    private final int stayInflatedTime;

    private float currentInflation = 0;
    private float targetInflation = 2;

    public int getBrightness(float tint) {
        return 240;
    }

    protected FloatingSculkBubbleParticle(ClientWorld clientWorld, double x, double y, double z, double size, double maxAge, double yVel, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, 0, 0, 0);
        this.velocityX = (Math.random() - 0.5) / 9.5;
        this.velocityZ = (Math.random() - 0.5) / 9.5;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
        this.velocityY = yVel;
        this.sound = size <= 0 ? RegisterSounds.FLOATING_SCULK_BUBBLE_POP : RegisterSounds.FLOATING_SCULK_BUBBLE_BIG_POP;
        if (size >= 1) {
            this.scale((float) (1.4F + size));
            this.velocityX = (Math.random() - 0.5) / 10.5;
            this.velocityZ = (Math.random() - 0.5) / 10.5;
        }
        this.maxAge = (int) Math.max(maxAge, 10);
        this.stayInflatedTime = (4 - this.maxAge) * -1;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            int i = this.age < 3 ? this.age : (this.age < this.stayInflatedTime ? 3 : this.age - (this.stayInflatedTime) + 4);
            this.setSprite(spriteProvider.getSprite(i, 7));
        }
    }

    @Override
    public void tick() {
        super.tick();
        int flateAge = this.age - (this.stayInflatedTime) + 4;
        if (this.age == 1) {
            this.currentInflation = 0;
            this.targetInflation = 2;
        } else if (this.age == 2) {
            this.currentInflation = 1;
            this.targetInflation = 1.4F;
        } else if (this.age == 3) {
            this.currentInflation = 1;
            this.targetInflation = 1.3F;
        } else if (this.age == 4) {
            this.currentInflation = 1.3F;
            this.targetInflation = 0.7F;
        } else if (this.age == 5) {
            this.currentInflation = 0.7F;
            this.targetInflation = 1.2F;
        } else if (this.age == 6) {
            this.currentInflation = 1.2F;
            this.targetInflation = 0.9F;
        } else if (this.age == 7) {
            this.currentInflation = 0.9F;
            this.targetInflation = 1.04F;
        } else if (this.age == 8) {
            this.currentInflation = 1.02F;
            this.targetInflation = 0.95F;
        } else if (this.age == 9) {
            this.currentInflation = 0.95F;
            this.targetInflation = 1;
        } else if (flateAge == 3) {
            this.currentInflation = 1F;
            this.targetInflation = 1.3F;
        } else if (flateAge == 4) {
            this.currentInflation = 1;
            this.targetInflation = 1.2F;
        } else if (flateAge == 5) {
            this.currentInflation = 1;
            this.targetInflation = 1.05F;
        } else if (flateAge == 6) {
            this.currentInflation = 1.05F;
            this.targetInflation = 1.1F;
        } else if (flateAge == 7) {
            this.currentInflation = 1.1F;
            this.targetInflation = 1.4F;
        } else if (flateAge == 8) {
            this.currentInflation = 1.4F;
            this.targetInflation = 1.65F;
        } else {
            this.currentInflation = 1;
            this.targetInflation = 1;
        }
        if (this.age == this.stayInflatedTime + 1) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null) {
                world.playSound(client.player, this.x, this.y, this.z, this.sound, SoundCategory.BLOCKS, 0.4F, world.random.nextFloat() * 0.2F + 0.8F);
                this.setVelocity(0, 0, 0);
            }
        }
        this.setSpriteForAge(this.spriteProvider);
    }

    public float getSize(float tickDelta) {
        return this.scale * MathHelper.lerp(tickDelta, this.currentInflation, this.targetInflation);
    }

    @Environment(EnvType.CLIENT)
    public static class BubbleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public BubbleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double size, double maxAge, double yVel) {
            FloatingSculkBubbleParticle bubble = new FloatingSculkBubbleParticle(clientWorld, x, y, z, size, maxAge, yVel, this.spriteProvider);
            bubble.setAlpha(1.0F);
            return bubble;
        }
    }

}
