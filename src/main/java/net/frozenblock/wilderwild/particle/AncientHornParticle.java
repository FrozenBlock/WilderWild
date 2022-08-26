package net.frozenblock.wilderwild.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.item.AncientHorn;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class AncientHornParticle extends TextureSheetParticle {
    private static final Vector3f rotation = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f field_38335 = new Vector3f(-1.0F, -1.0F, 0.0F);
    private static final float PI_THIRDS = 1.0472F;
    private int delay;

    public AncientHornParticle(ClientLevel world, double x, double y, double z, int delay) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.quadSize = 1.0F;
        this.delay = delay;
        this.lifetime = 30;
        this.gravity = 0.0F;
        this.xd = 0.0D; //TODO: make this move in the direction of the projectile
        this.yd = 0.0D;
        this.zd = 0.0D; //TODO: this too
    }


    public float getQuadSize(float tickDelta) {
        return this.quadSize * Mth.clamp(((float) this.age + tickDelta) / (float) this.lifetime * 2.0F, 0.0F, 5.0F);
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta, AncientHornProjectile projectile) {
        if (this.delay <= 0) {
            this.alpha = 1.0F - Mth.clamp(((float) this.age + tickDelta) / (float) this.lifetime * 2.0F, 0.0F, 1.0F);
            this.pushQuad(vertexConsumer, camera, tickDelta, (angle) -> {
                angle.mul(Vector3f.YP.rotation(0.0F));
                angle.mul(Vector3f.XP.rotation(0.0F));
            });
            this.pushQuad(vertexConsumer, camera, tickDelta, (angle) -> {
                angle.mul(Vector3f.YP.rotation(projectile.yRotO)); //why are these making it face the camera
                angle.mul(Vector3f.XP.rotation(projectile.xRotO));
            });
        }
    }

    private void pushQuad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternion> transformer) {
        Vec3 vec3d = camera.getPosition();
        float f = (float) (Mth.lerp(tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float) (Mth.lerp(tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float) (Mth.lerp(tickDelta, this.zo, this.z) - vec3d.z());
        Quaternion quaternion = new Quaternion(rotation, 0.0F, true);
        transformer.accept(quaternion);
        field_38335.transform(quaternion);
        Vector3f[] vec3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float i = this.getQuadSize(tickDelta);

        int j;
        for (j = 0; j < 4; ++j) {
            Vector3f vec3f = vec3fs[j];
            vec3f.transform(quaternion);
            vec3f.mul(i);
            vec3f.add(f, g, h);
        }

        j = this.getLightColor(tickDelta);
        this.pushVertex(vertexConsumer, vec3fs[0], this.getU1(), this.getV1(), j);
        this.pushVertex(vertexConsumer, vec3fs[1], this.getU1(), this.getV0(), j);
        this.pushVertex(vertexConsumer, vec3fs[2], this.getU0(), this.getV0(), j);
        this.pushVertex(vertexConsumer, vec3fs[3], this.getU0(), this.getV1(), j);
    }

    private void pushVertex(VertexConsumer vertexConsumer, Vector3f coordinate, float u, float v, int light) {
        vertexConsumer.vertex(coordinate.x(), coordinate.y(), coordinate.z()).uv(u, v).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    public int getLightColor(float tint) {
        return 240;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        if (this.delay > 0) {
            --this.delay;
        } else {
            super.tick();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<AncientHornParticleEffect> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(AncientHornParticleEffect ancientHornParticleEffect, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            AncientHornParticle ancientHornParticle = new AncientHornParticle(clientWorld, d, e, f, ancientHornParticleEffect.getDelay());
            ancientHornParticle.pickSprite(this.spriteProvider);
            ancientHornParticle.setAlpha(1.0F);
            return ancientHornParticle;
        }
    }
}
