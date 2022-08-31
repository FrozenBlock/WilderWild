package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class JellyParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    protected JellyParticle(ClientLevel clientWorld, double x, double y, double z, double xd, double yd, double za, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z, 0, 0, 0);
        this.spriteProvider = spriteProvider;
        this.setSpriteFromAge(spriteProvider);
        this.lifetime = 9;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class JellyFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public JellyFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double x, double y, double z, double xd, double yd, double zd) {
            JellyParticle jelly = new JellyParticle(clientWorld, x, y, z, xd, yd, zd, this.spriteProvider);
            jelly.setAlpha(1.0F);
            return jelly;
        }
    }

}
