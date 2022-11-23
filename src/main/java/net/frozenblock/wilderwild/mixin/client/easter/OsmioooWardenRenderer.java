package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.render.WilderWardenModel;
import net.frozenblock.wilderwild.entity.render.feature.OsmioooWardenFeatureRenderer;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenRenderer.class)
public abstract class OsmioooWardenRenderer extends MobRenderer<Warden, WardenModel<Warden>> {

    @Unique
    private static final ResourceLocation WILDERWILD$OSMIOOO_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden.png");
    @Unique
    private static final ResourceLocation WILDERWILD$OSMIOOO_BIOLUMINESCENT_LAYER_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_bioluminescent_overlay.png");
    @Unique
    private static final ResourceLocation WILDERWILD$OSMIOOO_HEART_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_heart.png");
    @Unique
    private static final ResourceLocation WILDERWILD$OSMIOOO_TENDRILS_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_tendrils.png");
    @Unique
    private static final ResourceLocation WILDERWILD$OSMIOOO_PULSATING_SPOTS_1_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_pulsating_spots_1.png");
    @Unique
    private static final ResourceLocation WILDERWILD$OSMIOOO_PULSATING_SPOTS_2_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_pulsating_spots_2.png");

    public OsmioooWardenRenderer(EntityRendererProvider.Context context, WardenModel<Warden> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void adOsmioLayers(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(this, WILDERWILD$OSMIOOO_BIOLUMINESCENT_LAYER_TEXTURE, (warden, tickDelta, animationProgress) -> 1.0F, WardenModel::getBioluminescentLayerModelParts)
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this,
						WILDERWILD$OSMIOOO_PULSATING_SPOTS_1_TEXTURE,
                        (warden, tickDelta, animationProgress) -> Math.max(0.0F, Mth.cos(animationProgress * 0.045F) * 0.25F),
                        WardenModel::getPulsatingSpotsLayerModelParts
                )
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this,
						WILDERWILD$OSMIOOO_PULSATING_SPOTS_2_TEXTURE,
                        (warden, tickDelta, animationProgress) -> Math.max(0.0F, Mth.cos(animationProgress * 0.045F + (float) Math.PI) * 0.25F),
                        WardenModel::getPulsatingSpotsLayerModelParts
                )
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this, WILDERWILD$OSMIOOO_TENDRILS_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getTendrilAnimation(tickDelta), model -> ((WilderWardenModel) model).getHeadAndTendrils()
                )
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this, WILDERWILD$OSMIOOO_HEART_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getHeartAnimation(tickDelta), WardenModel::getHeartLayerModelParts
                )
        );
    }
}
