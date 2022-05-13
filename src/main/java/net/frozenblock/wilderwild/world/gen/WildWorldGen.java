package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.gen.treedecorators.WildTreeDecorators;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;

public class WildWorldGen {
    public static void generateWildWorldGen() {
        WildFlowersGeneration.generateFlower();
        WildPollenGeneration.generatePollen();
        WildGrassGeneration.generateGrassForest();
        WildGrassGeneration.generateGrassTaiga();
        WildMiscGeneration.generateMisc();

        WildTreeDecorators.generateTreeDecorators();
        WildTreesGeneration.generateTrees();
        WildMushroomGeneration.generateMushroom();

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.TAIGA), SpawnGroup.CREATURE, EntityType.GOAT, 2, 3, 6);
    }
}

