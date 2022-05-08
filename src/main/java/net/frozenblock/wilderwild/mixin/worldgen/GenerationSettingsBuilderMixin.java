package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GenerationSettings.Builder.class)
public class GenerationSettingsBuilderMixin {

    @Inject(at = @At("HEAD"), method = "feature", cancellable = true)
    public void feature(net.minecraft.world.gen.GenerationStep.Feature featureStep, RegistryEntry<PlacedFeature> feature, CallbackInfoReturnable<GenerationSettings.Builder> info) {
        GenerationSettings.Builder builder = GenerationSettings.Builder.class.cast(this);
        if (feature==VegetationPlacedFeatures.TREES_FLOWER_FOREST) {info.setReturnValue(builder); info.cancel(); }
    }

}
