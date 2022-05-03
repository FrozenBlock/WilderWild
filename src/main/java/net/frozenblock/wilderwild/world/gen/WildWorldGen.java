package net.frozenblock.wilderwild.world.gen;

import net.frozenblock.wilderwild.world.gen.treedecorators.WildTreeDecorators;

public class WildWorldGen {
    public static void generateWildWorldGen() {
        WildFlowersGeneration.generateFlower();
        WildPollenGeneration.generatePollen();
        WildGrassGeneration.generateGrassForest();

        WildTreeDecorators.generateTreeDecorators();
        WildTreesGeneration.generateTrees();
        WildMushroomGeneration.generateMushroom();
    }
}

