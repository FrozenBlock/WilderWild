package net.frozenblock.wilderwild.mixin.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.WardenFeatureRenderer;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(WardenFeatureRenderer.class)
public abstract class WardenFeatureRendererMixin<T extends WardenEntity, M extends WardenEntityModel<T>> extends FeatureRenderer<T, M> {

    public WardenFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Shadow protected abstract void updateModelPartVisibility();

    @Shadow @Final
    public Identifier texture;

    @Shadow @Final
    public WardenFeatureRenderer.AnimationAngleAdjuster<T> animationAngleAdjuster;

    @Shadow
    protected abstract void unhideAllModelParts();

    /**
     * @author FrozenBlock
     * @reason dont render existing overlays when its osmio warden
     */
    @Overwrite
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T wardenEntity, float f, float g, float h, float j, float k, float l) {
        String string = Formatting.strip(wardenEntity.getName().getString());
        if (!wardenEntity.isInvisible() && !Objects.equals(string, "Osmiooo")) {
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
}
