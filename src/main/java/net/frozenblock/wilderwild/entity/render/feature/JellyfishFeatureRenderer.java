package net.frozenblock.wilderwild.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.render.JellyfishModel;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JellyfishFeatureRenderer<T extends Jellyfish, M extends JellyfishModel<T>> extends RenderLayer<T, M> {

    private final ResourceLocation TEXTURE;

    public JellyfishFeatureRenderer(RenderLayerParent<T, M> renderLayerParent, ResourceLocation texture) {
        super(renderLayerParent);
        this.TEXTURE = texture;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull T jelly, float f, float g, float tickDelta, float j, float k, float l) {
        if (!jelly.isInvisible()) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(WilderWildClient.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(jelly, 0.0f), 1.0f, 1.0f, 1.0f, this.getParentModel().lightProg);
        }
    }
}
