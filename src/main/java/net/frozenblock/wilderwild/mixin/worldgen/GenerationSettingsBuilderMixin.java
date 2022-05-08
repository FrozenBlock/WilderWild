package net.frozenblock.wilderwild.mixin.worldgen;

import com.google.common.collect.Lists;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;


@Mixin(GenerationSettings.Builder.class)
public class GenerationSettingsBuilderMixin {
    @Final
    @Shadow
    private final List<List<RegistryEntry<PlacedFeature>>> features = Lists.newArrayList();

    public GenerationSettings.Builder feature(net.minecraft.world.gen.GenerationStep.Feature featureStep, RegistryEntry<PlacedFeature> feature) {
        if (feature==VegetationPlacedFeatures.TREES_FLOWER_FOREST) {return null;}
        return this.feature(featureStep.ordinal(), feature);
    }

    @Shadow
    public GenerationSettings.Builder feature(int stepIndex, RegistryEntry<PlacedFeature> featureEntry) {
        GenerationSettings.Builder builder = GenerationSettings.Builder.class.cast(this);
        this.addFeatureStep(stepIndex);
        this.features.get(stepIndex).add(featureEntry);
        return builder;
    }

    @Shadow
    private void addFeatureStep(int stepIndex) {}
}
