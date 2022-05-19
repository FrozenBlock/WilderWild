package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;

import java.util.List;

public class WildMiscConfigured {
    public static final RegistryEntry<ConfiguredFeature<DiskFeatureConfig, ?>> DISK_MUD;
    public static final RegistryEntry<ConfiguredFeature<DiskFeatureConfig, ?>> DISK_COARSE_DIRT;

    public WildMiscConfigured() {
    }
    static {
        DISK_COARSE_DIRT = ConfiguredFeatures.register("disk_coarse_dirt", Feature.DISK, new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.COARSE_DIRT), BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)), UniformIntProvider.create(6, 8), 1));
        DISK_MUD = ConfiguredFeatures.register("disk_mud", Feature.DISK, new DiskFeatureConfig(new PredicatedStateProvider(BlockStateProvider.of(Blocks.MUD), List.of(new PredicatedStateProvider.Rule(BlockPredicate.not(BlockPredicate.eitherOf(BlockPredicate.solid(Direction.UP.getVector()), BlockPredicate.matchingFluids(Direction.UP.getVector(), new Fluid[]{Fluids.WATER}))), BlockStateProvider.of(Blocks.MUD)))), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)), UniformIntProvider.create(2, 6), 2));
    }

    public static void registerMiscPlaced() {
    }
}
