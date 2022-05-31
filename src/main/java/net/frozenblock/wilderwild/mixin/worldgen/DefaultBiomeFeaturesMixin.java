package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {


    /** @author FrozenBlock
     * @reason Replaced with new Birch Trees */
    @Overwrite
    public static void addBirchTrees(GenerationSettings.Builder builder) {
    }

    /** @author FrozenBlock
     * @reason Replaced with new Grass Patches */
    @Overwrite
    public static void addForestGrass(GenerationSettings.Builder builder) {
    }
    /** @author FrozenBlock
     * @reason Replaced with new Forest Trees */
    @Overwrite
    public static void addForestTrees(GenerationSettings.Builder builder) {
    }
    /** @author FrozenBlock
     * @reason Replaced with new Plains Trees */
    @Overwrite
    public static void addPlainsFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_PLAIN);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_PLAIN);
    }
    /** @author FrozenBlock
     * @reason Replaced with new Swamp Trees */
    @Overwrite
    public static void addSwampFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_SWAMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_DEAD_BUSH);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.BROWN_MUSHROOM_SWAMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.RED_MUSHROOM_SWAMP);
    }
    /** @author FrozenBlock
     * @reason Replaced with new Birch Trees */
    @Overwrite
    public static void addTallBirchTrees(GenerationSettings.Builder builder) {
    }
    /** @author FrozenBlock
     * @reason Replaced with new Taiga Trees */
    @Overwrite
    public static void addTaigaTrees(GenerationSettings.Builder builder) {
    }
    /** @author FrozenBlock
     * @reason Replaced with new Grove Trees */
    @Overwrite
    public static void addGroveTrees(GenerationSettings.Builder builder) {
    }
    /** @author FrozenBlock
     * @reason Replaced with new Savanna Trees */
    @Overwrite
    public static void addSavannaTrees(GenerationSettings.Builder builder) {
    }
    /** @author FrozenBlock
     * @reason Replaced with new Windswept Savanna Trees */
    @Overwrite
    public static void addExtraSavannaTrees(GenerationSettings.Builder builder) {
    }
}

