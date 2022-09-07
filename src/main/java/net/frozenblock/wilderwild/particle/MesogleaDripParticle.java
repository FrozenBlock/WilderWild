package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class MesogleaDripParticle extends TextureSheetParticle {

    MesogleaDripParticle(ClientLevel clientLevel, double d, double e, double f) {
        super(clientLevel, d, e, f);
        this.setSize(0.01f, 0.01f);
        this.gravity = 0.06f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (this.removed) {
            return;
        }
        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);
        this.postMoveUpdate();
        if (this.removed) {
            return;
        }
        this.xd *= 0.98f;
        this.yd *= 0.98f;
        this.zd *= 0.98f;
        BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
        FluidState fluidState = this.level.getFluidState(blockPos);
        if (fluidState.getType() == Fluids.WATER && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.level, blockPos))) {
            this.remove();
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
    }

    @Environment(value=EnvType.CLIENT)
    public static class MesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public MesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.LANDING_MESOGLEA_DRIP);
            //dripParticle.setColor(0.2f, 0.3f, 1.0f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class MesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public MesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripHangParticle dripParticle = new MesogleaDripParticle.DripHangParticle(clientLevel, d, e, f, RegisterParticles.FALLING_MESOGLEA_DRIP);
            //dripParticle.setColor(0.2f, 0.3f, 1.0f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class MesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public MesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            //dripParticle.setColor(0.51171875f, 0.03125f, 0.890625f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class DripLandParticle extends MesogleaDripParticle {
        DripLandParticle(ClientLevel clientLevel, double d, double e, double f) {
            super(clientLevel, d, e, f);
            this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class FallingParticle extends MesogleaDripParticle {
        FallingParticle(ClientLevel clientLevel, double d, double e, double f) {
            this(clientLevel, d, e, f, (int)(64.0 / (Math.random() * 0.8 + 0.2)));
        }

        FallingParticle(ClientLevel clientLevel, double d, double e, double f, int i) {
            super(clientLevel, d, e, f);
            this.lifetime = i;
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class FallAndLandParticle extends MesogleaDripParticle.FallingParticle {
        protected final ParticleOptions landParticle;

        FallAndLandParticle(ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions) {
            super(clientLevel, d, e, f);
            this.landParticle = particleOptions;
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class DripHangParticle extends MesogleaDripParticle {
        private final ParticleOptions fallingParticle;

        DripHangParticle(ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions) {
            super(clientLevel, d, e, f);
            this.fallingParticle = particleOptions;
            this.gravity *= 0.02f;
            this.lifetime = 40;
        }

        @Override
        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

        @Override
        protected void postMoveUpdate() {
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }
    }

}
