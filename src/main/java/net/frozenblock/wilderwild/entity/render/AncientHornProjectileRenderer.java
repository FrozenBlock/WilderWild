package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.util.function.BiFunction;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileRenderer<T extends AncientHornProjectile> extends EntityRenderer<T> {
    public static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/ancient_horn_projectile.png");
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
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ANCIENT_HORN_LAYER.apply(TEXTURE, false));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, tickDelta, projectile);

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

    private static final BiFunction<ResourceLocation, Boolean, RenderType> ANCIENT_HORN_LAYER = Util.memoize(
            ((identifier, affectsOutline) -> {
                RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(identifier, false, false))
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(affectsOutline);
                return of(
                        "ancient_horn_layer",
                        DefaultVertexFormat.NEW_ENTITY,
                        VertexFormat.Mode.QUADS,
                        256,
                        true,
                        true,
                        multiPhaseParameters
                );
            })
    );

    private static RenderType.CompositeRenderType of(
            String name,
            VertexFormat vertexFormat,
            VertexFormat.Mode drawMode,
            int expectedBufferSize,
            boolean hasCrumbling,
            boolean translucent,
            RenderType.CompositeState phases
    ) {
        return new RenderType.CompositeRenderType(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
    }
}
