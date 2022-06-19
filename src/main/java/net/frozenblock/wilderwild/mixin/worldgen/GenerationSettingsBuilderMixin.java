package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;


@Mixin(GenerationSettings.Builder.class)
public class GenerationSettingsBuilderMixin {

    private final ArrayList<RegistryEntry<PlacedFeature>> removedFeatures = new ArrayList<>() {{
        add(VegetationPlacedFeatures.TREES_FLOWER_FOREST);
        add(VegetationPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA);
        add(VegetationPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA);
        add(VegetationPlacedFeatures.TREES_SNOWY);
        add(VegetationPlacedFeatures.TREES_WINDSWEPT_HILLS);
        add(VegetationPlacedFeatures.TREES_WINDSWEPT_FOREST);
        add(VegetationPlacedFeatures.DARK_FOREST_VEGETATION);
        add(VegetationPlacedFeatures.TREES_MEADOW);
    }};

    @Inject(at = @At("HEAD"), method = "feature(Lnet/minecraft/world/gen/GenerationStep$Feature;Lnet/minecraft/util/registry/RegistryEntry;)Lnet/minecraft/world/biome/GenerationSettings$Builder;", cancellable = true)
    public void feature(net.minecraft.world.gen.GenerationStep.Feature featureStep, RegistryEntry<PlacedFeature> feature, CallbackInfoReturnable<GenerationSettings.Builder> info) {
        GenerationSettings.Builder builder = GenerationSettings.Builder.class.cast(this);
        if (removedFeatures.contains(feature)) {
            WilderWild.log("Removing feature " + feature.getKey().toString() + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
            info.setReturnValue(builder);
            info.cancel();
        }
    }

}
