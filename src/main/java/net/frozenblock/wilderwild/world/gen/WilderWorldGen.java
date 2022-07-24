package net.frozenblock.wilderwild.world.gen;

import net.frozenblock.wilderwild.world.gen.treedecorators.WilderTreeDecorators;

public class WilderWorldGen {
    public static void generateWildWorldGen() {
        WilderFlowersGeneration.generateFlower();
        WilderPollenGeneration.generatePollen();
        WilderGrassGeneration.generateGrassForest();
        WilderGrassGeneration.generateGrassTaiga();
        WilderMiscGeneration.generateMisc();

        WilderTreeDecorators.generateTreeDecorators();
        WilderTreesGeneration.generateTrees();
        WilderMushroomGeneration.generateMushroom();

        WilderMusic.playMusic();
        WilderSpawns.addFireflies();
    }
}

