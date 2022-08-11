package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.render.OsmioooWardenFeatureRenderer;
import net.frozenblock.wilderwild.entity.render.WilderWardenModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WardenEntityRenderer;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(WardenEntityRenderer.class)
public abstract class OsmioooWardenRenderer extends MobEntityRenderer<WardenEntity, WardenEntityModel<WardenEntity>> {

    private static final Identifier OSMIOOO_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden.png");
    private static final Identifier OSMIOOO_BIOLUMINESCENT_LAYER_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_bioluminescent_overlay.png");
    private static final Identifier OSMIOOO_HEART_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_heart.png");
    private static final Identifier OSMIOOO_TENDRILS_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_tendrils.png");
    private static final Identifier OSMIOOO_PULSATING_SPOTS_1_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_pulsating_spots_1.png");
    private static final Identifier OSMIOOO_PULSATING_SPOTS_2_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_pulsating_spots_2.png");

    public OsmioooWardenRenderer(EntityRendererFactory.Context context, WardenEntityModel<WardenEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntity(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(
                new OsmioooWardenFeatureRenderer<>(this, OSMIOOO_BIOLUMINESCENT_LAYER_TEXTURE, (warden, tickDelta, animationProgress) -> 1.0F, WardenEntityModel::getHeadAndLimbs)
        );
        this.addFeature(
                new OsmioooWardenFeatureRenderer<>(
                        this,
                        OSMIOOO_PULSATING_SPOTS_1_TEXTURE,
                        (warden, tickDelta, animationProgress) -> Math.max(0.0F, MathHelper.cos(animationProgress * 0.045F) * 0.25F),
                        WardenEntityModel::getBodyHeadAndLimbs
                )
        );
        this.addFeature(
                new OsmioooWardenFeatureRenderer<>(
                        this,
                        OSMIOOO_PULSATING_SPOTS_2_TEXTURE,
                        (warden, tickDelta, animationProgress) -> Math.max(0.0F, MathHelper.cos(animationProgress * 0.045F + (float) Math.PI) * 0.25F),
                        WardenEntityModel::getBodyHeadAndLimbs
                )
        );
        this.addFeature(
                new OsmioooWardenFeatureRenderer<>(
                        this, OSMIOOO_TENDRILS_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getTendrilPitch(tickDelta), model -> ((WilderWardenModel) model).getHeadAndTendrils()
                )
        );
        this.addFeature(
                new OsmioooWardenFeatureRenderer<>(
                        this, OSMIOOO_HEART_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getHeartPitch(tickDelta), WardenEntityModel::getBody
                )
        );
    }

    @Inject(method = "getTexture(Lnet/minecraft/entity/mob/WardenEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(WardenEntity wardenEntity, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(wardenEntity.getName().getString());
        if (string != null && (string.equalsIgnoreCase("Osmiooo") || string.equalsIgnoreCase("Mossmio"))) {
            cir.setReturnValue(OSMIOOO_TEXTURE);
        }
    }
}
