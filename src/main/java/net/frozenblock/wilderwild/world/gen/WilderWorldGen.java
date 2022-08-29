package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.gen.treedecorators.WilderTreeDecorators;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderWorldGen {
    public static void generateWildWorldGen() {
        WilderFlowersGeneration.generateFlower();
        WilderGrassGeneration.init();
        WilderMiscGeneration.generateMisc();

        WilderTreeDecorators.generateTreeDecorators();
        WilderTreesGeneration.generateTrees();
        WilderMushroomGeneration.generateMushroom();

        WilderMusic.playMusic();
        WilderSpawns.addFireflies();
        WilderSpawns.addJellyfish();

        generatePollen();
    }

    private static void generatePollen() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED.unwrapKey().orElseThrow());
    }
}

