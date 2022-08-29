package net.frozenblock.wilderwild.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.render.JellyfishModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class JellyfishFeatureRenderer<T extends Jellyfish, M extends JellyfishModel<T>> extends RenderLayer<T, M> {

    private final ResourceLocation TEXTURE;

    public JellyfishFeatureRenderer(RenderLayerParent<T, M> renderLayerParent, ResourceLocation texture) {
        super(renderLayerParent);
        this.TEXTURE = texture;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull T jelly, float f, float g, float tickDelta, float j, float k, float l) {
        /*if (!jelly.isInvisible()) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(WilderWildClient.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false));
            this.getParentModel().renderDeez(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(jelly, 0.0f));
        }*/
    }
}
