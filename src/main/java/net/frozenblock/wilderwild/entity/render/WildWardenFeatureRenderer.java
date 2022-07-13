package net.frozenblock.wilderwild.entity.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.WardenFeatureRenderer;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

public class WildWardenFeatureRenderer<T extends WardenEntity, M extends WardenEntityModel<T>> extends WardenFeatureRenderer<T, M> {
    public WildWardenFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture, AnimationAngleAdjuster<T> animationAngleAdjuster, ModelPartVisibility<T, M> modelPartVisibility) {
        super(context, texture, animationAngleAdjuster, modelPartVisibility);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T wardenEntity, float f, float g, float h, float j, float k, float l) {
        String string = Formatting.strip(wardenEntity.getName().getString());
        if (!wardenEntity.isInvisible() && Objects.equals(string, "Osmiooo")) {
            this.updateModelPartVisibility();
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucentEmissive(this.texture));
            this.getContextModel()
                    .render(
                            matrixStack,
                            vertexConsumer,
                            i,
                            LivingEntityRenderer.getOverlay(wardenEntity, 0.0F),
                            1.0F,
                            1.0F,
                            1.0F,
                            this.animationAngleAdjuster.apply(wardenEntity, h, j)
                    );
            this.unhideAllModelParts();
        }
    }

    private void updateModelPartVisibility() {
        List<ModelPart> list = this.modelPartVisibility.getPartsToDraw(this.getContextModel());
        this.getContextModel().getPart().traverse().forEach(part -> part.hidden = true);
        list.forEach(part -> part.hidden = false);
    }

    private void unhideAllModelParts() {
        this.getContextModel().getPart().traverse().forEach(part -> part.hidden = false);
    }
}
