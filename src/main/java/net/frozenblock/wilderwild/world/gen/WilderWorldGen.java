package net.frozenblock.wilderwild.world.gen;

import net.frozenblock.wilderwild.world.gen.treedecorators.WildTreeDecorators;

public class WilderWorldGen {
    public static void generateWildWorldGen() {
        WilderFlowersGeneration.generateFlower();
        WilderPollenGeneration.generatePollen();
        WilderGrassGeneration.generateGrassForest();
        WilderGrassGeneration.generateGrassTaiga();
        WilderMiscGeneration.generateMisc();

        WildTreeDecorators.generateTreeDecorators();
        WilderTreesGeneration.generateTrees();
        WilderMushroomGeneration.generateMushroom();

        WilderMusic.playMusic();
        WilderSpawns.addFirefliesSwamps();
        WilderSpawns.addFirefliesOthers();
    }
}

