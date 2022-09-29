package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;


@Mixin(BiomeGenerationSettings.Builder.class)
public class GenerationSettingsBuilderMixin {

    private final ArrayList<Holder<PlacedFeature>> removedFeatures = new ArrayList<>() {{
        add(VegetationPlacements.TREES_FLOWER_FOREST);
        add(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
        add(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
        add(VegetationPlacements.TREES_SNOWY);
        add(VegetationPlacements.TREES_WINDSWEPT_HILLS);
        add(VegetationPlacements.TREES_WINDSWEPT_FOREST);
        add(VegetationPlacements.DARK_FOREST_VEGETATION);
        add(VegetationPlacements.TREES_MEADOW);
    }};

    @Inject(at = @At("HEAD"), method = "addFeature(Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/biome/BiomeGenerationSettings$Builder;", cancellable = true)
    public void addFeature(GenerationStep.Decoration featureStep, Holder<PlacedFeature> feature, CallbackInfoReturnable<BiomeGenerationSettings.Builder> info) {
        if (!WilderWild.hasTerralith) {
            BiomeGenerationSettings.Builder builder = BiomeGenerationSettings.Builder.class.cast(this);
            if (removedFeatures.contains(feature)) {
                WilderWild.log("Removing feature " + feature.unwrapKey().toString() + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
                info.setReturnValue(builder);
                info.cancel();
            }
        }
    }

}
