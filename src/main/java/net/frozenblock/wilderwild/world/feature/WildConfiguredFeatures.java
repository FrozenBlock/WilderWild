package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;


public class WildConfiguredFeatures {
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_FALLEN_BIRCH_AND_OAK =
            ConfiguredFeatures.register("new_fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.35F)), WildTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> NEW_TREES_BIRCH_AND_OAK =
            ConfiguredFeatures.register("new_trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(WildTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new RandomFeatureEntry(WildTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.27F)), WildTreePlaced.NEW_OAK_BEES_0004));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> NEW_GRASS_FOREST =
            ConfiguredFeatures.register("new_grass_forest", Feature.RANDOM_PATCH,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.GRASS)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> DATURA =
            ConfiguredFeatures.register("datura", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.DATURA)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CARNATION =
            ConfiguredFeatures.register("carnation", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(RegisterBlocks.CARNATION)))));

    public static final RegistryEntry<ConfiguredFeature<MultifaceGrowthFeatureConfig, ?>> POLLEN_CONFIGURED =
            ConfiguredFeatures.register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthFeatureConfig(RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, RegistryEntryList.of(Block::getRegistryEntry, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final RegistryEntry<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED =
            ConfiguredFeatures.register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock)RegisterBlocks.BROWN_SHELF_FUNGUS, 20, false, true, true, RegistryEntryList.of(Block::getRegistryEntry, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG)));

    public static void registerConfiguredFeatures() {
        System.out.println("Registering WildConfiguredFeatures for " + WilderWild.MOD_ID);
    }
}

