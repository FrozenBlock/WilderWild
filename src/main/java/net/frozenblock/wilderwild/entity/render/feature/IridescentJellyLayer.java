package net.frozenblock.wilderwild.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.render.JellyfishModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IridescentJellyLayer<T extends Jellyfish, M extends JellyfishModel<T>> extends RenderLayer<T, M> {
    private static final float MAX_ROT = (float) (360 * (Math.PI/180F));

    private final ResourceLocation TEXTURE;
    private final boolean inverse;

    public IridescentJellyLayer(RenderLayerParent<T, M> renderLayerParent, boolean inverse, ResourceLocation texture) {
        super(renderLayerParent);
        this.inverse = inverse;
        this.TEXTURE = texture;
    }

    protected ResourceLocation getTextureLocation(@NotNull T entity) {
        return this.TEXTURE;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull T entity, float f, float g, float h, float j, float k, float l) {
        if (entity.getVariant().equals("pearlescent")) {
            JellyfishModel<T> model = this.getParentModel();
            float rotation = ((model.xRot + model.zRot) / 2);
            float alpha = inverse ? 1 - rotation : rotation;
            this.getParentModel().renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(this.TEXTURE)), 0xF00000, getOverlay(entity, 0), h, j, k, alpha);
        }
    }

    public static int getOverlay(Jellyfish entity, float whiteOverlayProgress) {
        return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
}
