package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class WildConfiguredFeatures {
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FALLEN_TREES_MIXED =
            register("fallen_trees_mixed", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of((new RandomFeatureEntry(WildTreePlaced.FALLEN_SPRUCE_CHECKED, 0.4F)),
                            new RandomFeatureEntry(WildTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.3F)), WildTreePlaced.NEW_FALLEN_OAK_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> MIXED_TREES =
            register("mixed_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_SPRUCE_CHECKED, 0.39F),
                            new RandomFeatureEntry(WildTreePlaced.FUNGUS_PINE_CHECKED, 0.09F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.13F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.28F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_OAK_CHECKED, 0.1F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_BIRCH, 0.32F)), WildTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> SAVANNA_TREES =
            register("savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreePlacedFeatures.ACACIA_CHECKED, 0.8F),
                            new RandomFeatureEntry(WildTreePlaced.BAOBAB, 0.09F),
                            new RandomFeatureEntry(WildTreePlaced.BAOBAB_TALL, 0.055F)), WildTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> WINDSWEPT_SAVANNA_TREES =
            register("windswept_savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreePlacedFeatures.ACACIA_CHECKED, 0.8F)), WildTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_MEADOW_TREES =
            register("new_meadow_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_BEES, 0.5F)), WildTreePlaced.NEW_SUPER_BIRCH_BEES));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_DARK_FOREST_VEGETATION =
            register("new_dark_forest_vegetation", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
                            new RandomFeatureEntry(PlacedFeatures.createEntry(TreeConfiguredFeatures.HUGE_RED_MUSHROOM), 0.05F),
                            new RandomFeatureEntry(TreePlacedFeatures.DARK_OAK_CHECKED, 0.6666667F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_BIRCH, 0.2F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_TALL_DARK_OAK_CHECKED, 0.35F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_CHECKED, 0.185F)), WildTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FALLEN_SPRUCE_AND_OAK =
            register("fallen_spruce_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.FALLEN_SPRUCE_CHECKED, 0.55F)), WildTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_WINDSWEPT_HILLS =
            register("new_trees_windswept_hills", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_SPRUCE_CHECKED, 0.666F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_CHECKED, 0.1F)), WildTreePlaced.NEW_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_OLD_GROWTH_PINE_TAIGA =
            register("new_trees_old_growth_pine_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.025641026F),
                            new RandomFeatureEntry(WildTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.30769232F),
                            new RandomFeatureEntry(WildTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WildTreePlaced.NEW_SPRUCE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA =
            register("new_trees_old_growth_spruce_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.33333334F),
                            new RandomFeatureEntry(WildTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WildTreePlaced.NEW_SPRUCE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_GROVE =
            register("new_trees_grove", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.FUNGUS_PINE_ON_SNOW, 0.33333334F)), WildTreePlaced.NEW_SPRUCE_ON_SNOW));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_TAIGA =
            register("new_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WildTreePlaced.NEW_SPRUCE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_SHORT_TREES_TAIGA =
            register("new_short_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.33333334F)), WildTreePlaced.NEW_SPRUCE_SHORT_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<ProbabilityConfig, ?>> CATTAIL =
            register("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityConfig(0.8F));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_FLOWERED_WATERLILY =
            register("patch_flowered_waterlily", Feature.RANDOM_PATCH,
                    new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.FLOWERED_LILY_PAD)))));

    /*public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CYPRESS_ROOTS =
            register("patch_cypress_roots", Feature.RANDOM_PATCH,
                    new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CYPRESS_ROOTS)))));*/

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_FLOWER_FOREST =
            register("new_trees_flower_forest", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F), new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.2F)), WildTreePlaced.NEW_OAK_BEES_0004));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_PLAINS =
            register("new_trees_plains", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(WildTreeConfigured.NEW_FANCY_OAK_BEES_0004), 0.33333334F)), PlacedFeatures.createEntry(WildTreeConfigured.NEW_OAK_BEES_0004)));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_FALLEN_BIRCH_AND_OAK =
            register("new_fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.35F)), WildTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_FALLEN_CYPRESS_AND_OAK =
            register("new_fallen_cypress_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_FALLEN_OAK_CHECKED, 0.35F)), WildTreePlaced.NEW_FALLEN_CYPRESS_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_BIRCH_AND_OAK =
            register("new_trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.27F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_OAK_CHECKED, 0.155F)), WildTreePlaced.NEW_OAK_BEES_0004));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> DATURA =
            register("datura", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.DATURA)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CARNATION =
            register("carnation", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CARNATION)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> WHITE_DANDELION =
            register("white_dandelion", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(48, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.WHITE_DANDELION)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> MILKWEED =
            register("milkweed", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.MILKWEED)))));

    public static final RegistryEntry<ConfiguredFeature<MultifaceGrowthFeatureConfig, ?>> POLLEN_CONFIGURED =
            register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, RegistryEntryList.of(Block::getRegistryEntry, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final RegistryEntry<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED =
            register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, RegistryEntryList.of(Block::getRegistryEntry, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));

    public static final RegistryEntry<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> RED_SHELF_FUNGUS_CONFIGURED =
            register("red_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, RegistryEntryList.of(Block::getRegistryEntry, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));

    public static final RegistryEntry<ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_CONFIGURED =
            register("termite_mound_baobab", WilderWild.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.getDefaultState().with(RegisterProperties.NATURAL, true), UniformIntProvider.create(4, 9), UniformIntProvider.create(3, 7), UniformIntProvider.create(1, 3), RegistryEntryList.of(Block::getRegistryEntry, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), RegistryEntryList.of(Block::getRegistryEntry, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.ROOTED_DIRT)));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CYPRESS_FOREST_TREES =
            register("cypress_forest_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.CYPRESS, 0.5F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_OAK_CHECKED, 0.5F)), WildTreePlaced.FUNGUS_CYPRESS));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CYPRESS_TREES =
            register("cypress_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.CYPRESS, 0.4F),
                            new RandomFeatureEntry(WildTreePlaced.FUNGUS_CYPRESS, 0.6F)), WildTreePlaced.FUNGUS_CYPRESS));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> ALL_CYPRESS_TREES_EQUAL =
            register("all_cypress_trees_equal", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.CYPRESS, 0.25F),
                            new RandomFeatureEntry(WildTreePlaced.FUNGUS_CYPRESS, 0.25F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_CYPRESS, 0.25F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_FUNGUS_CYPRESS, 0.25F)
                    ), WildTreePlaced.CYPRESS));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> SHORT_CYPRESS_TREES =
            register("short_cypress_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.SHORT_CYPRESS, 0.7F),
                            new RandomFeatureEntry(WildTreePlaced.SHORT_FUNGUS_CYPRESS, 0.3F)), WildTreePlaced.SHORT_FUNGUS_CYPRESS));

    public static void registerConfiguredFeatures() {
        WilderWild.logWild("Registering WildConfiguredFeatures for", true);
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
        return (RegistryEntry<V>) BuiltinRegistries.add(registry, new Identifier(WilderWild.MOD_ID, id), value);
    }
}
