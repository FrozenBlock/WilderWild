package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

import java.util.function.BiFunction;

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
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((projectile.prevYaw + tickDelta * (projectile.getYaw() - projectile.prevYaw)) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((projectile.prevPitch + tickDelta * (projectile.getPitch() - projectile.prevPitch)) + 90.0F));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ANCIENT_HORN_LAYER.apply(TEXTURE, false));
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

    private static final BiFunction<Identifier, Boolean, RenderLayer> ANCIENT_HORN_LAYER = Util.memoize(
            ((identifier, affectsOutline) -> {
                RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                        .shader(RenderPhase.ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                        .texture(new RenderPhase.Texture(identifier, false, false))
                        .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                        .cull(RenderPhase.DISABLE_CULLING)
                        .writeMaskState(RenderPhase.ALL_MASK)
                        .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                        .build(affectsOutline);
                return of(
                        "ancient_horn_layer",
                        VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        true,
                        true,
                        multiPhaseParameters
                );
            })
    );

    private static RenderLayer.MultiPhase of(
            String name,
            VertexFormat vertexFormat,
            VertexFormat.DrawMode drawMode,
            int expectedBufferSize,
            boolean hasCrumbling,
            boolean translucent,
            RenderLayer.MultiPhaseParameters phases
    ) {
        return new RenderLayer.MultiPhase(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
    }
}
