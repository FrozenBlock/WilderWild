package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BiomeDefaultFeatures.class, priority = 69420)
public class BiomeDefaultFeaturesMixin {

    @Inject(method = "addForestGrass", at = @At("HEAD"), cancellable = true)
    private static void addForestGrass(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildGrass()) {
            WilderWild.log("Removing " + "Forest Grass" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.cancel();
        }
    }

    @Inject(method = "addOtherBirchTrees", at = @At("HEAD"), cancellable = true)
    private static void addOtherBirchTrees(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Removing " + "Forest Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.cancel();
        }
    }

    @Inject(method = "addPlainVegetation", at = @At("HEAD"), cancellable = true)
    private static void addPlainsVegetation(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Overriding " + "Plains Features" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
            info.cancel();
        }
    }

    @Inject(method = "addSwampVegetation", at = @At("HEAD"), cancellable = true)
    private static void addSwampVegetation(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Overriding " + "Swamp Features" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);
            info.cancel();
        }
    }

    @Inject(method = "addTaigaTrees", at = @At("HEAD"), cancellable = true)
    private static void addTaigaTrees(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Removing " + "Taiga Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.cancel();
        }
    }

    @Inject(method = "addGroveTrees", at = @At("HEAD"), cancellable = true)
    private static void addGroveTrees(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Removing " + "Grove Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.cancel();
        }
    }

    @Inject(method = "addSavannaTrees", at = @At("HEAD"), cancellable = true)
    private static void addSavannaTrees(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Removing " + "Savanna Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.cancel();
        }
    }

    @Inject(method = "addShatteredSavannaTrees", at = @At("HEAD"), cancellable = true)
    private static void addShatteredSavannaTrees(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
        if (ClothConfigInteractionHandler.wildTrees()) {
            WilderWild.log("Removing " + "Extra Savanna Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.cancel();
        }
    }

}

