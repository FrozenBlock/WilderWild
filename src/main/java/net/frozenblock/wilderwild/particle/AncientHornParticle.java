//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.frozenblock.wilderwild.particle;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class AncientHornParticle extends SpriteBillboardParticle {
    private static final Vec3f rotation = (Vec3f)Util.make(new Vec3f(0.5F, 0.5F, 0.5F), Vec3f::normalize);
    private static final Vec3f field_38335 = new Vec3f(-1.0F, -1.0F, 0.0F);
    private static final float PI_THIRDS = 1.0472F;
    private int delay;

    AncientHornParticle(ClientWorld world, double x, double y, double z, double xv, double yv, double zv, int delay) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.scale = 0.85F;
        this.delay = delay;
        this.maxAge = 30;
        this.gravityStrength = 0.0F;
        this.velocityX = 0.0D;
        this.velocityY = 0.1D;
        this.velocityZ = 0.0D;
    }


    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 0.75F, 0.0F, 1.0F);
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        if (this.delay <= 0) {
            this.alpha = 1.0F - MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge, 0.0F, 1.0F);
            this.pushQuad(vertexConsumer, camera, tickDelta, (angle) -> {
                angle.hamiltonProduct(Vec3f.POSITIVE_Y.getRadialQuaternion(0.0F));
                angle.hamiltonProduct(Vec3f.POSITIVE_X.getRadialQuaternion(-1.0472F));
            });
            this.pushQuad(vertexConsumer, camera, tickDelta, (angle) -> {
                angle.hamiltonProduct(Vec3f.POSITIVE_Y.getRadialQuaternion(-3.1415927F));
                angle.hamiltonProduct(Vec3f.POSITIVE_X.getRadialQuaternion(1.0472F));
            });
        }
    }

    private void pushQuad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternion> transformer) {
        Vec3d vec3d = camera.getPos();
        float f = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternion quaternion = new Quaternion(rotation, 0.0F, true);
        transformer.accept(quaternion);
        field_38335.rotate(quaternion);
        Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        float i = this.getSize(tickDelta);

        int j;
        for(j = 0; j < 4; ++j) {
            Vec3f vec3f = vec3fs[j];
            vec3f.rotate(quaternion);
            vec3f.scale(i);
            vec3f.add(f, g, h);
        }

        j = this.getBrightness(tickDelta);
        this.pushVertex(vertexConsumer, vec3fs[0], this.getMaxU(), this.getMaxV(), j);
        this.pushVertex(vertexConsumer, vec3fs[1], this.getMaxU(), this.getMinV(), j);
        this.pushVertex(vertexConsumer, vec3fs[2], this.getMinU(), this.getMinV(), j);
        this.pushVertex(vertexConsumer, vec3fs[3], this.getMinU(), this.getMaxV(), j);
    }

    private void pushVertex(VertexConsumer vertexConsumer, Vec3f coordinate, float u, float v, int light) {
        vertexConsumer.vertex((double)coordinate.getX(), (double)coordinate.getY(), (double)coordinate.getZ()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light).next();
    }

    public int getBrightness(float tint) {
        return 240;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        if (this.delay > 0) {
            --this.delay;
        } else {
            super.tick();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<AncientHornParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(AncientHornParticleEffect ancientHornParticleEffect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            AncientHornParticle ancientHornParticle = new AncientHornParticle(clientWorld, d, e, f, ancientHornParticleEffect.getDelay());
            ancientHornParticle.setSprite(this.spriteProvider);
            ancientHornParticle.setAlpha(1.0F);
            return ancientHornParticle;
        }
    }
}
