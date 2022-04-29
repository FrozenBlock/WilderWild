package net.frozenblock.wilderwild.world.gen;

public class WildWorldGen {
    public static void generateWildWorldGen() {
        WildDaturaGeneration.generateFlowers();
        WildCarnationGeneration.generateFlowers();
        WildPollenGeneration.generatePollen();
        WildGrassForestGeneration.generateGrassForest();

        WildBirchGeneration.generateTrees();
        WildMushroomGeneration.generateMushroom();

    }
}

