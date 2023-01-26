package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileRenderer<T extends AncientHornProjectile> extends EntityRenderer<T> {
    private static final ResourceLocation TEXTURE = WilderSharedConstants.id("textures/entity/ancient_horn_projectile.png");
    private final AncientHornProjectileModel model;

    public AncientHornProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new AncientHornProjectileModel(context.bakeLayer(WilderWildClient.ANCIENT_HORN_PROJECTILE_LAYER));
    }

    @Override
    public void render(T projectile, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.mulPose(Vector3f.YP.rotationDegrees((projectile.yRotO + tickDelta * (projectile.getYRot() - projectile.yRotO)) - 90.0F));
        matrices.mulPose(Vector3f.ZP.rotationDegrees((projectile.xRotO + tickDelta * (projectile.getXRot() - projectile.xRotO)) + 90.0F));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(FrozenRenderType.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false));

        float multiplier = projectile.boundingBoxMultiplier;
        float scale = multiplier + 1F;
        float alpha = 1.0F - (multiplier / 15F);
        float correctedAlpha = Math.max(alpha, 0.01F);

        matrices.scale(scale, scale, scale);

        this.model.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, correctedAlpha, tickDelta, projectile);

        matrices.popPose();
        super.render(projectile, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos blockPos) {
        return 15;
    }
}
