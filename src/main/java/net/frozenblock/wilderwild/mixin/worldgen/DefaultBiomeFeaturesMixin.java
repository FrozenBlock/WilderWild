package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BiomeDefaultFeatures.class)
public class DefaultBiomeFeaturesMixin {


    /**
     * @author FrozenBlock
     * @reason Replaced with new Birch Trees
     */
    @Overwrite
    public static void addBirchTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Birch Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Grass Patches
     */
    @Overwrite
    public static void addForestGrass(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Forest Grass" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Forest Trees
     */
    @Overwrite
    public static void addOtherBirchTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Forest Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Plains Trees
     */
    @Overwrite
    public static void addPlainVegetation(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Overriding " + "Plains Features" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Swamp Trees
     */
    @Overwrite
    public static void addSwampVegetation(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Overriding " + "Swamp Features" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Birch Trees
     */
    @Overwrite
    public static void addTallBirchTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Tall Birch Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Taiga Trees
     */
    @Overwrite
    public static void addTaigaTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Taiga Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Grove Trees
     */
    @Overwrite
    public static void addGroveTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Grove Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Savanna Trees
     */
    @Overwrite
    public static void addSavannaTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Savanna Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }

    /**
     * @author FrozenBlock
     * @reason Replaced with new Windswept Savanna Trees
     */
    @Overwrite
    public static void addShatteredSavannaTrees(BiomeGenerationSettings.Builder builder) {
        WilderWild.log("Removing " + "Extra Savanna Trees" + " in order to properly update biomes!", WilderWild.UNSTABLE_LOGGING);
    }
}

