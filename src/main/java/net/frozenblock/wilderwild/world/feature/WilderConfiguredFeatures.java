package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public final class WilderConfiguredFeatures {
    //FALLEN TREES
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_TREES_MIXED =
            register("fallen_trees_mixed", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.4F)),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.3F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE_AND_OAK =
            register("fallen_spruce_and_oak", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.55F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_FALLEN_BIRCH_AND_OAK =
            register("new_fallen_birch_and_oak", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.35F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_FALLEN_CYPRESS_AND_OAK =
            register("new_fallen_cypress_and_oak", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_OAK_CHECKED, 0.35F)), WilderTreePlaced.NEW_FALLEN_CYPRESS_CHECKED));

    //TREES
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_PLAINS =
            register("new_trees_plains", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004), 0.33333334F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK), 0.169F)),
                            PlacementUtils.inlinePlaced(WilderTreeConfigured.NEW_OAK_BEES_0004)));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_BIRCH_AND_OAK =
            register("new_trees_birch_and_oak", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.27F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.155F)), WilderTreePlaced.NEW_OAK_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_FLOWER_FOREST =
            register("new_trees_flower_forest", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.099F)), WilderTreePlaced.NEW_OAK_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> MIXED_TREES =
            register("mixed_trees", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_CHECKED, 0.39F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.09F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.28F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.32F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_DARK_FOREST_VEGETATION =
            register("new_dark_forest_vegetation", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.05F),
                            new WeightedPlacedFeature(TreePlacements.DARK_OAK_CHECKED, 0.6666667F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_TALL_DARK_OAK_CHECKED, 0.35F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_CHECKED, 0.185F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_TAIGA =
            register("new_trees_taiga", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_SHORT_TREES_TAIGA =
            register("new_short_trees_taiga", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_OLD_GROWTH_PINE_TAIGA =
            register("new_trees_old_growth_pine_taiga", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.025641026F),
                            new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.30769232F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA =
            register("new_trees_old_growth_spruce_taiga", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_GROVE =
            register("new_trees_grove", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_ON_SNOW, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_ON_SNOW));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_WINDSWEPT_HILLS =
            register("new_trees_windswept_hills", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_CHECKED, 0.666F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_CHECKED, 0.1F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_MEADOW_TREES =
            register("new_meadow_trees", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES, 0.5F)), WilderTreePlaced.NEW_SUPER_BIRCH_BEES));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SAVANNA_TREES =
            register("savanna_trees", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB, 0.082F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL, 0.045F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WINDSWEPT_SAVANNA_TREES =
            register("windswept_savanna_trees", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES =
            register("cypress_wetlands_trees", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.37F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.25F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_OAK_CHECKED, 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_SAPLING =
            register("cypress_wetlands_trees_sapling", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.4F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.15F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F)),
                            WilderTreePlaced.FUNGUS_CYPRESS));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_WATER =
            register("cypress_wetlands_trees_water", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.1F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.85F)), WilderTreePlaced.FUNGUS_CYPRESS));

    //FLOWERS
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SEEDING_DANDELION =
            register("seeding_dandelion", net.minecraft.world.level.levelgen.feature.Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CARNATION =
            register("carnation", net.minecraft.world.level.levelgen.feature.Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DATURA =
            register("datura", net.minecraft.world.level.levelgen.feature.Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> NEW_FLOWER_PLAIN =
            register("new_flower_plain", net.minecraft.world.level.levelgen.feature.Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MILKWEED =
            register("milkweed", net.minecraft.world.level.levelgen.feature.Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));

    public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColors.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColors.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColors.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColors.WHITE), 1).build();
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW =
            register("glory_of_the_snow", net.minecraft.world.level.levelgen.feature.Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL)))));

    //VEGETATION
    public static final Holder<ConfiguredFeature<MultifaceGrowthConfiguration, ?>> POLLEN_CONFIGURED =
            register("pollen", net.minecraft.world.level.levelgen.feature.Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final Holder<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED =
            register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));

    public static final Holder<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> RED_SHELF_FUNGUS_CONFIGURED =
            register("red_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));

    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> CATTAIL =
            register("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FLOWERED_WATERLILY =
            register("patch_flowered_waterlily", net.minecraft.world.level.levelgen.feature.Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));

    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> PATCH_ALGAE =
            register("patch_algae", WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final Holder<ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_CONFIGURED =
            register("termite_mound_baobab", WilderWild.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));

    /*public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CYPRESS_ROOTS =
            register("patch_cypress_roots", Feature.RANDOM_PATCH,
                    new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CYPRESS_ROOTS)))));*/

    public static void registerConfiguredFeatures() {
        WilderWild.logWild("Registering WilderConfiguredFeatures for", true);
    }

    private static RandomPatchConfiguration createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> register(String id, Feature<NoneFeatureConfiguration> feature) {
        return register(id, feature, FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
        return registerExact(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, config));
    }

    public static <V extends T, T> Holder<V> registerExact(Registry<T> registry, String id, V value) {
        return (Holder<V>) BuiltinRegistries.register(registry, WilderWild.id(id), value);
    }
}
