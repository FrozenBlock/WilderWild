package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import static net.minecraft.world.gen.feature.VegetationPlacedFeatures.modifiersWithWouldSurvive;

public class WildPlacedFeature {
    public static final RegistryEntry<PlacedFeature> DATURA_BIRCH = PlacedFeatures.register("darura_birch",
            WildConfiguredFeatures.DATURA, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> CARNATION_BIRCH = PlacedFeatures.register("carnation_birch",
            WildConfiguredFeatures.CARNATION, RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_PLACED = PlacedFeatures.register("new_birch_placed",
            WildTreeConfigured.NEW_BIRCH_BEES_0002, modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1), Blocks.BIRCH_SAPLING));

    /*public static final RegistryEntry<PlacedFeature> CONCEPT_BIRCH_1_PLACED = PlacedFeatures.register("concept_birch_1_placed",
            WildConfiguredFeatures.CONCEPT_BIRCH_1_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1f, 1)));*/

    public static final RegistryEntry<PlacedFeature> POLLEN_PLACED = PlacedFeatures.register("pollen",
            WildConfiguredFeatures.POLLEN_CONFIGURED, RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(2), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING, 0, 128), BiomePlacementModifier.of());
}
