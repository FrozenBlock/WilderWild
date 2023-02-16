package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.worldgen.biome.api.modifications.FrozenBiomeSelectors;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMiscGeneration {
    public static void generateMisc() {
        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DECORATIVE_MUD),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.DISK_MUD);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DECORATIVE_MUD),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MUD_PATH);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TAIGA_FOREST_ROCK),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.FOREST_ROCK_TAIGA);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PATH),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_PATH),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_ORE),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.PACKED_MUD_PATH);

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CLAY_PATH),
                GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH);

        BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.STONE_POOL);

        BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.DEEPSLATE_POOL);
    }
}
