package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileRenderer extends EntityRenderer<AncientHornProjectileEntity> {
    public static final Identifier TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/ancient_horn_projectile.png");
    private final AncientHornProjectileModel model;

    public AncientHornProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new AncientHornProjectileModel(context.getPart(WildClientMod.ANCIENT_HORN_PROJECTILE_LAYER));
    }

    public void render(AncientHornProjectileEntity projectile, float f, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, projectile.prevYaw, projectile.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, projectile.prevPitch, projectile.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(projectile)), false, false);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F, tickDelta, projectile);
        matrixStack.pop();
        super.render(projectile, f, tickDelta, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(AncientHornProjectileEntity entity) {
        return TEXTURE;
    }

    protected int getBlockLight(AncientHornProjectileEntity entity, BlockPos blockPos) {
        return 15;
    }

}
