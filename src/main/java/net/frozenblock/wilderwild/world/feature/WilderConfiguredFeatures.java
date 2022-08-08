package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class WilderConfiguredFeatures {
    //FALLEN TREES
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FALLEN_TREES_MIXED =
            register("fallen_trees_mixed", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of((new RandomFeatureEntry(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.4F)),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.3F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FALLEN_SPRUCE_AND_OAK =
            register("fallen_spruce_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.55F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_FALLEN_BIRCH_AND_OAK =
            register("new_fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.35F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_FALLEN_CYPRESS_AND_OAK =
            register("new_fallen_cypress_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_FALLEN_OAK_CHECKED, 0.35F)), WilderTreePlaced.NEW_FALLEN_CYPRESS_CHECKED));

    //TREES
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_PLAINS =
            register("new_trees_plains", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004), 0.33333334F),
                            new RandomFeatureEntry(PlacedFeatures.createEntry(WilderTreeConfigured.SHORT_OAK), 0.169F)),
                            PlacedFeatures.createEntry(WilderTreeConfigured.NEW_OAK_BEES_0004)));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_BIRCH_AND_OAK =
            register("new_trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.27F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_OAK_CHECKED, 0.155F)), WilderTreePlaced.NEW_OAK_BEES_0004));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_FLOWER_FOREST =
            register("new_trees_flower_forest", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.2F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_OAK_CHECKED, 0.099F)), WilderTreePlaced.NEW_OAK_BEES_0004));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> MIXED_TREES =
            register("mixed_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_SPRUCE_CHECKED, 0.39F),
                            new RandomFeatureEntry(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.09F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.13F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.28F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_OAK_CHECKED, 0.13F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_BIRCH, 0.32F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_DARK_FOREST_VEGETATION =
            register("new_dark_forest_vegetation", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
                            new RandomFeatureEntry(PlacedFeatures.createEntry(TreeConfiguredFeatures.HUGE_RED_MUSHROOM), 0.05F),
                            new RandomFeatureEntry(TreePlacedFeatures.DARK_OAK_CHECKED, 0.6666667F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_BIRCH, 0.2F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_TALL_DARK_OAK_CHECKED, 0.35F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_FANCY_OAK_CHECKED, 0.185F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_TAIGA =
            register("new_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_SHORT_TREES_TAIGA =
            register("new_short_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_OLD_GROWTH_PINE_TAIGA =
            register("new_trees_old_growth_pine_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.025641026F),
                            new RandomFeatureEntry(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.30769232F),
                            new RandomFeatureEntry(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA =
            register("new_trees_old_growth_spruce_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.33333334F),
                            new RandomFeatureEntry(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_GROVE =
            register("new_trees_grove", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.FUNGUS_PINE_ON_SNOW, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_ON_SNOW));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_WINDSWEPT_HILLS =
            register("new_trees_windswept_hills", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_SPRUCE_CHECKED, 0.666F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_FANCY_OAK_CHECKED, 0.1F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_MEADOW_TREES =
            register("new_meadow_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.NEW_FANCY_OAK_BEES, 0.5F)), WilderTreePlaced.NEW_SUPER_BIRCH_BEES));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> SAVANNA_TREES =
            register("savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreePlacedFeatures.ACACIA_CHECKED, 0.8F),
                            new RandomFeatureEntry(WilderTreePlaced.BAOBAB, 0.082F),
                            new RandomFeatureEntry(WilderTreePlaced.BAOBAB_TALL, 0.045F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> WINDSWEPT_SAVANNA_TREES =
            register("windswept_savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreePlacedFeatures.ACACIA_CHECKED, 0.8F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CYPRESS_WETLANDS_TREES =
            register("cypress_wetlands_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.CYPRESS, 0.37F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_CYPRESS, 0.25F),
                            new RandomFeatureEntry(WilderTreePlaced.SWAMP_CYPRESS, 0.81F),
                            new RandomFeatureEntry(WilderTreePlaced.NEW_OAK_CHECKED, 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CYPRESS_WETLANDS_TREES_SAPLING =
            register("cypress_wetlands_trees_sapling", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.CYPRESS, 0.4F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_CYPRESS, 0.15F),
                            new RandomFeatureEntry(WilderTreePlaced.SWAMP_CYPRESS, 0.81F)),
                            WilderTreePlaced.FUNGUS_CYPRESS));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CYPRESS_WETLANDS_TREES_WATER =
            register("cypress_wetlands_trees_water", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WilderTreePlaced.CYPRESS, 0.2F),
                            new RandomFeatureEntry(WilderTreePlaced.SHORT_CYPRESS, 0.1F),
                            new RandomFeatureEntry(WilderTreePlaced.SWAMP_CYPRESS, 0.85F)), WilderTreePlaced.FUNGUS_CYPRESS));

    //FLOWERS
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> SEEDING_DANDELION =
            register("seeding_dandelion", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(48, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.SEEDING_DANDELION)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CARNATION =
            register("carnation", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(54, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CARNATION)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> DATURA =
            register("datura", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.DATURA)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> NEW_FLOWER_PLAIN =
            register("new_flower_plain", Feature.FLOWER, new RandomPatchFeatureConfig(64, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new NoiseThresholdBlockStateProvider(2345L, new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.getDefaultState(), List.of(Blocks.ORANGE_TULIP.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState()), List.of(RegisterBlocks.SEEDING_DANDELION.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.CORNFLOWER.getDefaultState()))))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> MILKWEED =
            register("milkweed", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.MILKWEED)))));

    public static final DataPool<BlockState> GLORY_OF_THE_SNOW_POOL = DataPool.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.getDefaultState().with(RegisterProperties.FLOWER_COLOR, FlowerColors.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.getDefaultState().with(RegisterProperties.FLOWER_COLOR, FlowerColors.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.getDefaultState().with(RegisterProperties.FLOWER_COLOR, FlowerColors.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.getDefaultState().with(RegisterProperties.FLOWER_COLOR, FlowerColors.WHITE), 1).build();
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> GLORY_OF_THE_SNOW =
            register("glory_of_the_snow", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(GLORY_OF_THE_SNOW_POOL)))));

    //VEGETATION
    public static final RegistryEntry<ConfiguredFeature<MultifaceGrowthFeatureConfig, ?>> POLLEN_CONFIGURED =
            register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, RegistryEntryList.of(Block::getRegistryEntry, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final RegistryEntry<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED =
            register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, RegistryEntryList.of(Block::getRegistryEntry, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));

    public static final RegistryEntry<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> RED_SHELF_FUNGUS_CONFIGURED =
            register("red_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, RegistryEntryList.of(Block::getRegistryEntry, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));

    public static final RegistryEntry<ConfiguredFeature<ProbabilityConfig, ?>> CATTAIL =
            register("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityConfig(0.8F));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_FLOWERED_WATERLILY =
            register("patch_flowered_waterlily", Feature.RANDOM_PATCH,
                    new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.FLOWERING_LILY_PAD)))));

    public static final RegistryEntry<ConfiguredFeature<ProbabilityConfig, ?>> PATCH_ALGAE =
            register("patch_algae", WilderWild.ALGAE_FEATURE, new ProbabilityConfig(0.8F));

    public static final RegistryEntry<ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_CONFIGURED =
            register("termite_mound_baobab", WilderWild.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.getDefaultState().with(RegisterProperties.NATURAL, true), UniformIntProvider.create(4, 9), UniformIntProvider.create(3, 7), UniformIntProvider.create(1, 3), RegistryEntryList.of(Block::getRegistryEntry, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), RegistryEntryList.of(Block::getRegistryEntry, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));

    /*public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CYPRESS_ROOTS =
            register("patch_cypress_roots", Feature.RANDOM_PATCH,
                    new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CYPRESS_ROOTS)))));*/

    public static void registerConfiguredFeatures() {
        WilderWild.logWild("Registering WilderConfiguredFeatures for", true);
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }

    public static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> register(String id, Feature<DefaultFeatureConfig> feature) {
        return register(id, feature, FeatureConfig.DEFAULT);
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
        return addCasted(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, config));
    }

    public static <V extends T, T> RegistryEntry<V> addCasted(Registry<T> registry, String id, V value) {
        return (RegistryEntry<V>) BuiltinRegistries.add(registry, WilderWild.id(id), value);
    }
}
