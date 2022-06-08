package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.mixin.worldgen.DefaultBiomeFeaturesMixin;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WildMiscPlaced;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class WildMiscGeneration {
    public static void generateMisc() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildMiscPlaced.DISK_MUD.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildMiscPlaced.MUD_PATH.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildMiscPlaced.FOREST_ROCK_TAIGA.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.WINDSWEPT_FOREST),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildMiscPlaced.COARSE_PATH.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildMiscPlaced.MOSS_PATH.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildMiscPlaced.COARSE_PATH_5.getKey().get());
    }
}
