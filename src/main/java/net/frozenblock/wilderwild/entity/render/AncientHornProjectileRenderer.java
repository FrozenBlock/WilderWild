package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
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
public class AncientHornProjectileRenderer<T extends AncientHornProjectile> extends EntityRenderer<T> {
    public static final Identifier TEXTURE = WilderWild.id("textures/entity/ancient_horn_projectile.png");
    private final AncientHornProjectileModel model;

    public AncientHornProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new AncientHornProjectileModel(context.getPart(WilderWildClient.ANCIENT_HORN_PROJECTILE_LAYER));
    }

    @Override
    public void render(T projectile, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, projectile.prevYaw, projectile.getYaw()) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, projectile.prevPitch, projectile.getPitch()) + 90.0F));

        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(projectile)), false, false);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F, tickDelta, projectile);

        matrices.pop();
        super.render(projectile, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLight(T entity, BlockPos blockPos) {
        return 15;
    }

}
