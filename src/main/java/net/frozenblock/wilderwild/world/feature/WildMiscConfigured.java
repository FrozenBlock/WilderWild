package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;

import java.util.List;

public class WildMiscConfigured {
    public static final RegistryEntry<ConfiguredFeature<DiskFeatureConfig, ?>> DISK_COARSE_DIRT = WildConfiguredFeatures.register("disk_coarse_dirt", Feature.DISK, new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.COARSE_DIRT), BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)), UniformIntProvider.create(6, 8), 1));
    public static final RegistryEntry<ConfiguredFeature<DiskFeatureConfig, ?>> DISK_MUD = WildConfiguredFeatures.register("disk_mud", Feature.DISK, new DiskFeatureConfig(new PredicatedStateProvider(BlockStateProvider.of(Blocks.MUD), List.of(new PredicatedStateProvider.Rule(BlockPredicate.not(BlockPredicate.eitherOf(BlockPredicate.solid(Direction.UP.getVector()), BlockPredicate.matchingFluids(Direction.UP.getVector(), Fluids.WATER))), BlockStateProvider.of(Blocks.MUD)))), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)), UniformIntProvider.create(2, 6), 2));
    public static final RegistryEntry<ConfiguredFeature<PathFeatureConfig, ?>> MUD_PATH = WildConfiguredFeatures.register("mud_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(Blocks.MUD, 11, 4, 0.1, 0.23, 1, false, false, RegistryEntryList.of(Blocks.DIRT.getRegistryEntry(), Blocks.GRASS_BLOCK.getRegistryEntry(), Blocks.CLAY.getRegistryEntry(), Blocks.SAND.getRegistryEntry())));
    public static final RegistryEntry<ConfiguredFeature<PathFeatureConfig, ?>> COARSE_PATH = WildConfiguredFeatures.register("coarse_dirt_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(Blocks.COARSE_DIRT, 11, 4, 0.12, 0.2, 1, false, false, RegistryEntryList.of(Blocks.DIRT.getRegistryEntry(), Blocks.GRASS_BLOCK.getRegistryEntry(), Blocks.PODZOL.getRegistryEntry())));
    public static final RegistryEntry<ConfiguredFeature<PathFeatureConfig, ?>> MOSS_PATH = WildConfiguredFeatures.register("moss_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(Blocks.MOSS_BLOCK, 9, 4, 0.15, 0.18, 1, true, true, RegistryEntryList.of(Blocks.GRASS_BLOCK.getRegistryEntry(), Blocks.PODZOL.getRegistryEntry())));

    public static final RegistryEntry<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_SAND_PATH = WildConfiguredFeatures.register("under_water_sand_path", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(Blocks.SAND, 16, 4, 0.08, 0.2, 0.58, true, true, RegistryEntryList.of(Blocks.DIRT.getRegistryEntry(), Blocks.GRAVEL.getRegistryEntry(), Blocks.GRASS_BLOCK.getRegistryEntry())));
    public static final RegistryEntry<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_GRAVEL_PATH = WildConfiguredFeatures.register("under_water_gravel_path", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(Blocks.GRAVEL, 16, 1,0.1, -0.9, -0.5, true, true, RegistryEntryList.of(Blocks.DIRT.getRegistryEntry(), Blocks.GRASS_BLOCK.getRegistryEntry())));
    public static final RegistryEntry<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_CLAY_PATH = WildConfiguredFeatures.register("under_water_clay_path", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(Blocks.CLAY, 16, 2,0.16, 0.64, 0.9, true, true, RegistryEntryList.of(Blocks.DIRT.getRegistryEntry(), Blocks.GRAVEL.getRegistryEntry(), Blocks.GRASS_BLOCK.getRegistryEntry())));

    public WildMiscConfigured() {
    }

    public static void registerMiscPlaced() {
        WilderWild.logWild("Registering WildMiscConfigured for", true);
    }
}
