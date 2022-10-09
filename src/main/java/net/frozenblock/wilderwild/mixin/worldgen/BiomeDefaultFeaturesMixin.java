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

