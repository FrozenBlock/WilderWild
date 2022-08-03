package net.frozenblock.wilderwild.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(WardenFeatureRenderer.class)
public abstract class WardenFeatureRendererMixin<T extends WardenEntity, M extends WardenEntityModel<T>> extends FeatureRenderer<T, M> {

    public WardenFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Shadow
    @Final
    public Identifier texture;

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/mob/WardenEntity;FFFFFF)V", cancellable = true)
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T wardenEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        String string = Formatting.strip(wardenEntity.getName().getString());
        if (string != null && (string.equalsIgnoreCase("Osmiooo") || string.equalsIgnoreCase("Mossmio"))) {
            ci.cancel();
        }
    }
}
