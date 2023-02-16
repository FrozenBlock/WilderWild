package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.frozenblock.lib.feature.FrozenFeatures;
import net.frozenblock.lib.feature.features.config.PathFeatureConfig;
import net.frozenblock.lib.feature.features.config.PillarFeatureConfig;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import static net.minecraft.data.worldgen.features.OreFeatures.NATURAL_STONE;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WilderMiscConfigured {
	private WilderMiscConfigured() {
		throw new UnsupportedOperationException("WilderMiscConfigured contains only static declarations.");
	}

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_COARSE_DIRT = WilderConfiguredFeatures.register("disk_coarse_dirt", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.COARSE_DIRT), BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)), UniformInt.of(6, 8), 1));
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_MUD = WilderConfiguredFeatures.register("disk_mud", Feature.DISK, new DiskConfiguration(new RuleBasedBlockStateProvider(BlockStateProvider.simple(Blocks.MUD), List.of(new RuleBasedBlockStateProvider.Rule(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.solid(Direction.UP.getNormal()), BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.WATER))), BlockStateProvider.simple(Blocks.MUD)))), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)), UniformInt.of(2, 6), 2));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> MUD_PATH = WilderConfiguredFeatures.register("mud_path", FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MUD), 11, 4, 0.1, 0.23, 1, false, false, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.CLAY.builtInRegistryHolder(), Blocks.SAND.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> COARSE_PATH = WilderConfiguredFeatures.register("coarse_dirt_path", FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.COARSE_DIRT), 11, 3, 0.12, -0.2, 0.3, false, false, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> MOSS_PATH = WilderConfiguredFeatures.register("moss_path", FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MOSS_BLOCK), 9, 1, 0.15, 0.18, 1, true, true, false, false, HolderSet.direct(Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> SAND_PATH = WilderConfiguredFeatures.register("sand_path", FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 11, 3, 0.12, -0.2, 0.3, false, false, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> PACKED_MUD_PATH = WilderConfiguredFeatures.register("packed_mud_path", FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.PACKED_MUD), 9, 1, 0.12, 0.20, 1, true, true, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.COARSE_DIRT.builtInRegistryHolder())));

    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_SAND_PATH = WilderConfiguredFeatures.register("under_water_sand_path", FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 16, 4, 0.05, 0.2, 0.54, true, true, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_GRAVEL_PATH = WilderConfiguredFeatures.register("under_water_gravel_path", FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.GRAVEL), 16, 1, 0.07, -0.7, -0.3, true, true, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_CLAY_PATH = WilderConfiguredFeatures.register("under_water_clay_path", FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 16, 3, 0.07, 0.5, 0.85, true, true, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));

    public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_CLAY_PATH_BEACH = WilderConfiguredFeatures.register("under_water_clay_path_beach", FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 14, 2, 0.10, 0.5, 0.85, true, true, false, false, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));
	public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> UNDER_WATER_GRAVEL_PATH_RIVER = WilderConfiguredFeatures.register("under_water_gravel_path_river", FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.GRAVEL), 14, 2, 0.10, 0.5, 0.85, true, true, false, false, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));

    public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WilderBlockTags.PACKED_MUD_REPLACEABLE);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PACKED_MUD = WilderConfiguredFeatures.register("ore_packed_mud", Feature.ORE, new OreConfiguration(PACKED_MUD_REPLACEABLE, Blocks.PACKED_MUD.defaultBlockState(), 40));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_CALCITE = WilderConfiguredFeatures.register("ore_calcite", Feature.ORE, new OreConfiguration(NATURAL_STONE, Blocks.CALCITE.defaultBlockState(), 64));
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> BLANK_SHUT_UP = WilderConfiguredFeatures.register("blank_shut_up", Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
            PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new SimpleStateProvider(Blocks.WATER.defaultBlockState())))))
    );
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> DEEPSLATE_POOL = WilderConfiguredFeatures.register("deepslate_pool", Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.DEEPSLATE), PlacementUtils.inlinePlaced(BLANK_SHUT_UP), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> STONE_POOL = WilderConfiguredFeatures.register("stone_pool", Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.STONE), PlacementUtils.inlinePlaced(BLANK_SHUT_UP), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
    public static final Holder<ConfiguredFeature<PillarFeatureConfig, ?>> UPWARDS_MESOGLEA_PILLAR = WilderConfiguredFeatures.register("blue_mesoglea_pillar", FrozenFeatures.UPWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PillarFeatureConfig, ?>> PURPLE_MESOGLEA_PILLAR = WilderConfiguredFeatures.register("purple_mesoglea_pillar", FrozenFeatures.UPWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PillarFeatureConfig, ?>> DOWNWARDS_MESOGLEA_PILLAR = WilderConfiguredFeatures.register("downwards_blue_mesoglea_pillar", FrozenFeatures.DOWNWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
    public static final Holder<ConfiguredFeature<PillarFeatureConfig, ?>> DOWNWARDS_PURPLE_MESOGLEA_PILLAR = WilderConfiguredFeatures.register("downwards_purple_mesoglea_pillar", FrozenFeatures.DOWNWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));

    public static void registerMiscPlaced() {
        WilderSharedConstants.logWild("Registering WilderMiscConfigured for", true);
    }
}
