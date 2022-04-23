package net.frozenblock.wilderwild.world.feature;

import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

public class WildPlacedFeature {
    public static final RegistryEntry<PlacedFeature> DATURA_BIRCH = PlacedFeatures.register("darura_birch",
            WildConfiguredFeatures.DATURA, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> CARNATION_BIRCH = PlacedFeatures.register("carnation_birch",
            WildConfiguredFeatures.CARNATION, RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_PLACED = PlacedFeatures.register("new_birch_placed",
            WildConfiguredFeatures.NEW_BIRCH_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1f, 2)));

    public static final RegistryEntry<PlacedFeature> POLLEN_PLACED = PlacedFeatures.register("pollen",
            WildConfiguredFeatures.POLLEN_CONFIGURED, CountPlacementModifier.of(UniformIntProvider.create(104, 157)), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG, 60, 128), BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> PATCH_FLOWERED_WATERLILY =
            PlacedFeatures.register("patch_flowered_waterlily", WildConfiguredFeatures.PATCH_FLOWERED_WATERLILY, VegetationPlacedFeatures.modifiers(3));
}
