package net.frozenblock.wilderwild.mixin.worldgen;

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

    private final ArrayList<RegistryEntry<PlacedFeature>> removedFeatures = new ArrayList<>(){{
        add(VegetationPlacedFeatures.TREES_FLOWER_FOREST);
        add(VegetationPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA);
        add(VegetationPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA);
        add(VegetationPlacedFeatures.TREES_SNOWY);
        add(VegetationPlacedFeatures.TREES_WINDSWEPT_HILLS);
        add(VegetationPlacedFeatures.TREES_WINDSWEPT_FOREST);
    }};

    @Inject(at = @At("HEAD"), method = "feature", cancellable = true)
    public void feature(net.minecraft.world.gen.GenerationStep.Feature featureStep, RegistryEntry<PlacedFeature> feature, CallbackInfoReturnable<GenerationSettings.Builder> info) {
        GenerationSettings.Builder builder = GenerationSettings.Builder.class.cast(this);
        if (removedFeatures.contains(feature)) {info.setReturnValue(builder); info.cancel(); }
    }

}
