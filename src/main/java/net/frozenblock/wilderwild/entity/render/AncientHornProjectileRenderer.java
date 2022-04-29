package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class AncientHornProjectileRenderer extends ProjectileEntityRenderer<AncientHornProjectileEntity> {
    private static final Identifier PROJECTILE_TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/ancient_horn_projectile.png");

    public AncientHornProjectileRenderer(Object context) {
        super((EntityRendererFactory.Context) context);
    }
    public void render(AncientHornProjectileEntity projectile, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, projectile.prevYaw, projectile.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, projectile.prevPitch, projectile.getPitch())));
        float h = 0.0F;
        float k = 0.5F;
        float l = 0.0F;
        float m = 0.15625F;
        float n = 0.0F;
        float o = 0.15625F;
        float p = 0.15625F;
        float q = 0.3125F;
        float r = 0.05625F;
        float s = (float)projectile.shake - g;
        if (s > 0.0F) {
            float t = -MathHelper.sin(s * 3.0F) * s;
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(t));
        }

        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(45.0F));
        matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
        matrixStack.translate(-4.0D, 0.0D, 0.0D);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(projectile)));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();

        for(int u = 0; u < 4; ++u) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            this.vertex(matrix4f, matrix3f, vertexConsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, vertexConsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, vertexConsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, vertexConsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i);
        }

        matrixStack.pop();
    }
    @Override
    public Identifier getTexture(AncientHornProjectileEntity entity) {
        return PROJECTILE_TEXTURE;
    }

}
