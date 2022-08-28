package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.render.feature.JellyfishFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {

    private static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/jellyfish/jellyfish.png");
    private static final ResourceLocation PINK_TEXTURE = WilderWild.id("textures/entity/jellyfish/pink_jellyfish.png");

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.3F);
        this.addLayer(new JellyfishFeatureRenderer<>(this, PINK_TEXTURE));

    }

    @Override
    public void render(@NotNull Jellyfish jelly, float f, float g, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i) {
        float n;
        Direction direction;
        poseStack.pushPose();
        this.model.attackTime = this.getAttackAnim(jelly, g);
        this.model.riding = jelly.isPassenger();
        this.model.young = jelly.isBaby();
        float h = Mth.rotLerp(g, jelly.yBodyRotO, jelly.yBodyRot);
        float j = Mth.rotLerp(g, jelly.yHeadRotO, jelly.yHeadRot);
        float k = j - h;
        if (jelly.isPassenger() && jelly.getVehicle() instanceof LivingEntity) {
            LivingEntity jelly2 = (LivingEntity) jelly.getVehicle();
            h = Mth.rotLerp(g, jelly2.yBodyRotO, jelly2.yBodyRot);
            k = j - h;
            float l = Mth.wrapDegrees(k);
            if (l < -85.0f) {
                l = -85.0f;
            }
            if (l >= 85.0f) {
                l = 85.0f;
            }
            h = j - l;
            if (l * l > 2500.0f) {
                h += l * 0.2f;
            }
            k = j - h;
        }
        float m = Mth.lerp(g, jelly.xRotO, jelly.getXRot());
        if (LivingEntityRenderer.isEntityUpsideDown(jelly)) {
            m *= -1.0f;
            k *= -1.0f;
        }
        if (jelly.hasPose(Pose.SLEEPING) && (direction = jelly.getBedOrientation()) != null) {
            n = jelly.getEyeHeight(Pose.STANDING) - 0.1f;
            poseStack.translate((float)(-direction.getStepX()) * n, 0.0, (float)(-direction.getStepZ()) * n);
        }
        float l = this.getBob(jelly, g);
        this.setupRotations(jelly, poseStack, l, h, g);
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        this.scale(jelly, poseStack, g);
        poseStack.translate(0.0, -1.501f, 0.0);
        n = 0.0f;
        float o = 0.0f;
        if (!jelly.isPassenger() && jelly.isAlive()) {
            n = Mth.lerp(g, jelly.animationSpeedOld, jelly.animationSpeed);
            o = jelly.animationPosition - jelly.animationSpeed * (1.0f - g);
            if (jelly.isBaby()) {
                o *= 3.0f;
            }
            if (n > 1.0f) {
                n = 1.0f;
            }
        }
        this.model.prepareMobModel(jelly, o, n, g);
        this.model.setupAnim(jelly, o, n, l, k, m);
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl = this.isBodyVisible(jelly);
        boolean bl2 = !bl && !jelly.isInvisibleTo(minecraft.player);
        boolean bl3 = minecraft.shouldEntityAppearGlowing(jelly);
        RenderType renderType = this.getRenderType(jelly, bl, bl2, bl3);
        if (renderType != null) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
            int p = LivingEntityRenderer.getOverlayCoords(jelly, this.getWhiteOverlayProgress(jelly, g));
            this.model.lightProg = Mth.lerp(g, jelly.getPrevLight(), jelly.getLight()) / 15;
            this.model.render(poseStack, vertexConsumer, i, p);
        }
        if (!jelly.isSpectator()) {
            for (RenderLayer<Jellyfish, JellyfishModel<Jellyfish>> renderLayer : this.layers) {
                renderLayer.render(poseStack, multiBufferSource, i, jelly, o, n, g, l, k, m);
            }
        }
        poseStack.popPose();
        if (!this.shouldShowName(jelly)) {
            return;
        }
        this.renderNameTag(jelly, jelly.getDisplayName(), poseStack, multiBufferSource, i);
        Entity entity = jelly.getLeashHolder();
        if (entity == null) {
            return;
        }
        this.renderLeash(jelly, g, poseStack, multiBufferSource, entity);
    }

    @Override
    public void setupRotations(@NotNull Jellyfish jelly, PoseStack poseStack, float f, float g, float h) {
        //poseStack.translate(0.0, 0.5, 0.0);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - g));
        poseStack.translate(0.0, -1.2, 0.0);
        poseStack.scale(0.8F, 0.8F, 0.8F);
        JellyfishModel<Jellyfish> model = this.getModel();
        model.xRot = Mth.lerp(h, jelly.xRot1, jelly.xBodyRot);
        model.zRot = Mth.lerp(h, jelly.zRot1, jelly.zBodyRot);
        model.tentXRot = Mth.lerp(h, jelly.xRot3, jelly.xRot2);
        model.tentZRot = Mth.lerp(h, jelly.zRot3, jelly.zRot2);
    }

    @Override
    protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
        return 15;
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull Jellyfish jellyfish, boolean bl, boolean bl2, boolean bl3) {
        return WilderWildClient.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false);
    }


    @Override
    public ResourceLocation getTextureLocation(@NotNull Jellyfish jellyfish) {
        return TEXTURE;
    }

    private <E extends Entity> void renderLeash(Jellyfish mob, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, E entity) {
        int v;
        poseStack.pushPose();
        Vec3 vec3 = entity.getRopeHoldPosition(f);
        double d = (double)(Mth.lerp(f, ((Mob)mob).yBodyRotO, ((Mob)mob).yBodyRot) * ((float)Math.PI / 180)) + 1.5707963267948966;
        Vec3 vec32 = ((Entity)mob).getLeashOffset();
        double e = Math.cos(d) * vec32.z + Math.sin(d) * vec32.x;
        double g = Math.sin(d) * vec32.z - Math.cos(d) * vec32.x;
        double h = Mth.lerp((double)f, ((Mob)mob).xo, ((Entity)mob).getX()) + e;
        double i = Mth.lerp((double)f, ((Mob)mob).yo, ((Entity)mob).getY()) + vec32.y;
        double j = Mth.lerp((double)f, ((Mob)mob).zo, ((Entity)mob).getZ()) + g;
        poseStack.translate(e, vec32.y, g);
        float k = (float)(vec3.x - h);
        float l = (float)(vec3.y - i);
        float m = (float)(vec3.z - j);
        float n = 0.025f;
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.leash());
        Matrix4f matrix4f = poseStack.last().pose();
        float o = Mth.fastInvSqrt(k * k + m * m) * 0.025f / 2.0f;
        float p = m * o;
        float q = k * o;
        BlockPos blockPos = new BlockPos(((Entity)mob).getEyePosition(f));
        BlockPos blockPos2 = new BlockPos(entity.getEyePosition(f));
        int r = this.getBlockLightLevel(mob, blockPos);
        int s = 15;
        int t = mob.level.getBrightness(LightLayer.SKY, blockPos);
        int u = mob.level.getBrightness(LightLayer.SKY, blockPos2);
        for (v = 0; v <= 24; ++v) {
            addVertexPair(vertexConsumer, matrix4f, k, l, m, r, s, t, u, 0.025f, 0.025f, p, q, v, false);
        }
        for (v = 24; v >= 0; --v) {
            addVertexPair(vertexConsumer, matrix4f, k, l, m, r, s, t, u, 0.025f, 0.0f, p, q, v, true);
        }
        poseStack.popPose();
    }

    private static void addVertexPair(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, int i, int j, int k, int l, float m, float n, float o, float p, int q, boolean bl) {
        float r = (float)q / 24.0f;
        int s = (int)Mth.lerp(r, i, j);
        int t = (int)Mth.lerp(r, k, l);
        int u = LightTexture.pack(s, t);
        float v = q % 2 == (bl ? 1 : 0) ? 0.7f : 1.0f;
        float w = 0.5f * v;
        float x = 0.4f * v;
        float y = 0.3f * v;
        float z = f * r;
        float aa = g > 0.0f ? g * r * r : g - g * (1.0f - r) * (1.0f - r);
        float ab = h * r;
        vertexConsumer.vertex(matrix4f, z - o, aa + n, ab + p).color(w, x, y, 1.0f).uv2(u).endVertex();
        vertexConsumer.vertex(matrix4f, z + o, aa + m - n, ab - p).color(w, x, y, 1.0f).uv2(u).endVertex();
    }
}
