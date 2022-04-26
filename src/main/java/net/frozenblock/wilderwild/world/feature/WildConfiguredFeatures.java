package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.UpwardsBranchingTrunkPlacer;

import java.util.List;


public class WildConfiguredFeatures {
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> DATURA =
            ConfiguredFeatures.register("datura", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.DATURA)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CARNATION =
            ConfiguredFeatures.register("carnation", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CARNATION)))));

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_TREE =
            ConfiguredFeatures.register("new_birch_tree", Feature.TREE, new TreeFeatureConfig.Builder(

                    BlockStateProvider.of(Blocks.BIRCH_LOG),
                    new StraightTrunkPlacer(8,6,4),
                    BlockStateProvider.of(Blocks.BIRCH_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());

    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_CHECKED =
            PlacedFeatures.register("new_birch_checked", NEW_BIRCH_TREE,
                    PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_BIRCH_SPAWN =
            ConfiguredFeatures.register("new_birch_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(NEW_BIRCH_CHECKED, 0.5f)),
                            NEW_BIRCH_CHECKED));

    /*public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> CONCEPT_ART_BIRCH_TREE_1 =
            ConfiguredFeatures.register("concept_birch_1", Feature.TREE, new TreeFeatureConfig.Builder(

                    BlockStateProvider.of(Blocks.BIRCH_LOG),
                    new ForkingTrunkPlacer(8, 6, 4),
                    BlockStateProvider.of(Blocks.BIRCH_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());

    public static final RegistryEntry<PlacedFeature> CONCEPT_BIRCH_1_CHECKED =
            PlacedFeatures.register("concept_birch_1_checked", CONCEPT_ART_BIRCH_TREE_1,
                    PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CONCEPT_BIRCH_1_SPAWN =
            ConfiguredFeatures.register("concept_birch_1_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(CONCEPT_BIRCH_1_CHECKED, 0.5f)),
                            CONCEPT_BIRCH_1_CHECKED));*/

    public static final RegistryEntry<ConfiguredFeature<MultifaceGrowthFeatureConfig, ?>> POLLEN_CONFIGURED =
            ConfiguredFeatures.register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthFeatureConfig(RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, RegistryEntryList.of(Block::getRegistryEntry, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));




    public static void registerConfiguredFeatures() {
        System.out.println("Registering WildConfiguredFeatures for " + WilderWild.MOD_ID);
    }
}

