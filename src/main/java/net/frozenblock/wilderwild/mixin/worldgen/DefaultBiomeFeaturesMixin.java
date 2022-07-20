package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {


    @Inject(method = "addBirchTrees", at = @At("INVOKE"), cancellable = true)
    private static void addBirchTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Birch Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addForestGrass", at = @At("INVOKE"), cancellable = true)
    private static void addForestGrass(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Forest Grass" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addForestTrees", at = @At("INVOKE"), cancellable = true)
    private static void addForestTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Forest Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addPlainsFeatures", at = @At("INVOKE"), cancellable = true)
    private static void addPlainsFeatures(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Overriding " + "Plains Features" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_PLAIN);
        info.cancel();
    }

    @Inject(method = "addSwampFeatures", at = @At("INVOKE"), cancellable = true)
    private static void addSwampFeatures(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Overriding " + "Swamp Features" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_SWAMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_DEAD_BUSH);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.BROWN_MUSHROOM_SWAMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.RED_MUSHROOM_SWAMP);
        info.cancel();
    }

    @Inject(method = "addTallBirchTrees", at = @At("INVOKE"), cancellable = true)
    private static void addTallBirchTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Tall Birch Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addTaigaTrees", at = @At("INVOKE"), cancellable = true)
    private static void addTaigaTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Taiga Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addGroveTrees", at = @At("INVOKE"), cancellable = true)
    private static void addGroveTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Grove Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addSavannaTrees", at = @At("INVOKE"), cancellable = true)
    private static void addSavannaTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Savanna Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }

    @Inject(method = "addExtraSavannaTrees", at = @At("INVOKE"), cancellable = true)
    private static void addExtraSavannaTrees(GenerationSettings.Builder builder, CallbackInfo info) {
        WilderWild.log("Removing " + "Extra Savanna Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        info.cancel();
    }
}

