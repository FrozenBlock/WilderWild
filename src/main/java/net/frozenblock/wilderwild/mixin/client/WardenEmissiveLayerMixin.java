package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenEmissiveLayer.class)
public abstract class WardenEmissiveLayerMixin<T extends Warden, M extends WardenModel<T>> extends RenderLayer<T, M> {

    public WardenEmissiveLayerMixin(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Shadow
    @Final
    public ResourceLocation texture;

    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/monster/warden/Warden;FFFFFF)V", cancellable = true)
    public void preventIfOsmiooo(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T wardenEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if (((WilderWarden) wardenEntity).isOsmiooo()) {
            ci.cancel();
        }
    }
}
