package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class FireflyFeatureRenderer<T extends FireflyEntity, M extends FireflyEntityModel<T>> extends FeatureRenderer<T, M> {
    private final Identifier texture;
    private final AnimationAngleAdjuster<T> animationAngleAdjuster;
    private final ModelPartVisibility<T, M> modelPartVisibility;

    public FireflyFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture, AnimationAngleAdjuster<T> animationAngleAdjuster, ModelPartVisibility<T, M> modelPartVisibility) {
        super(context);
        this.texture = texture;
        this.animationAngleAdjuster = animationAngleAdjuster;
        this.modelPartVisibility = modelPartVisibility;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
    }

    private void updateModelPartVisibility() {
        List<ModelPart> list = this.modelPartVisibility.getPartsToDraw((M) this.getContextModel());
        this.getContextModel().getPart().traverse().forEach((part) -> {
            part.hidden = true;
        });
        list.forEach((part) -> {
            part.hidden = false;
        });
    }

    private void unhideAllModelParts() {
        (this.getContextModel()).getPart().traverse().forEach((part) -> {
            part.hidden = false;
        });
    }

    @Environment(EnvType.CLIENT)
    public interface AnimationAngleAdjuster<T extends FireflyEntity> {
        float apply(T firefly, float tickDelta, float animationProgress);
    }

    @Environment(EnvType.CLIENT)
    public interface ModelPartVisibility<T extends FireflyEntity, M extends EntityModel<T>> {
        List<ModelPart> getPartsToDraw(M model);
    }
}
