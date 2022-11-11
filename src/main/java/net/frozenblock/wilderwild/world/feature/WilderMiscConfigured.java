package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.util.FrozenConfiguredFeatureUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.WilderPillarConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
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

	public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
	public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WilderBlockTags.PACKED_MUD_REPLACEABLE);

	public static final ResourceKey<ConfiguredFeature<?, ?>> BLANK_SHUT_UP = key("blank_shut_up");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_COARSE_DIRT = key("disk_coarse_dirt");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_MUD = key("disk_mud");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MUD_PATH = key("mud_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_PATH = key("coarse_dirt_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATH = key("moss_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_PATH = key("sand_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PACKED_MUD_PATH = key("packed_mud_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_SAND_PATH = key("under_water_sand_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_GRAVEL_PATH = key("under_water_gravel_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_CLAY_PATH = key("under_water_clay_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_CLAY_PATH_BEACH = key("under_water_clay_path_beach");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PACKED_MUD = key("ore_packed_mud");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CALCITE = key("ore_calcite");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_POOL = key("deepslate_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_POOL = key("stone_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPWARDS_MESOGLEA_PILLAR = key("blue_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_PILLAR = key("purple_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DOWNWARDS_MESOGLEA_PILLAR = key("downwards_blue_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DOWNWARDS_PURPLE_MESOGLEA_PILLAR = key("downwards_purple_mesoglea_pillar");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderWild.logWild("Registering WilderMiscConfigured for", true);
		HolderGetter<ConfiguredFeature<?, ?>> conf = entries.getLookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		register(entries, BLANK_SHUT_UP, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new SimpleStateProvider(Blocks.WATER.defaultBlockState()))))));
		register(entries, DISK_COARSE_DIRT, Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.COARSE_DIRT), BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)), UniformInt.of(6, 8), 1));
		register(entries, DISK_MUD, Feature.DISK, new DiskConfiguration(new RuleBasedBlockStateProvider(BlockStateProvider.simple(Blocks.MUD), List.of(new RuleBasedBlockStateProvider.Rule(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.solid(Direction.UP.getNormal()), BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.WATER))), BlockStateProvider.simple(Blocks.MUD)))), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)), UniformInt.of(2, 6), 2));
		register(entries, MUD_PATH, WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MUD), 11, 4, 0.1, 0.23, 1, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.CLAY.builtInRegistryHolder(), Blocks.SAND.builtInRegistryHolder())));
		register(entries, COARSE_PATH, WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.COARSE_DIRT), 11, 3, 0.12, -0.2, 0.3, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
		register(entries, MOSS_PATH, WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MOSS_BLOCK), 9, 1, 0.15, 0.18, 1, true, true, HolderSet.direct(Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
		register(entries, SAND_PATH, WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 11, 3, 0.12, -0.2, 0.3, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
		register(entries, PACKED_MUD_PATH, WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.PACKED_MUD), 9, 1, 0.12, 0.20, 1, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.COARSE_DIRT.builtInRegistryHolder())));
		register(entries, UNDER_WATER_SAND_PATH, WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 16, 4, 0.05, 0.2, 0.54, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
		register(entries, UNDER_WATER_GRAVEL_PATH, WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.GRAVEL), 16, 1, 0.07, -0.7, -0.3, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));
		register(entries, UNDER_WATER_CLAY_PATH, WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 16, 3, 0.07, 0.5, 0.85, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));
		register(entries, UNDER_WATER_CLAY_PATH_BEACH, WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 14, 2, 0.10, 0.5, 0.85, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));
		register(entries, ORE_PACKED_MUD, Feature.ORE, new OreConfiguration(PACKED_MUD_REPLACEABLE, Blocks.PACKED_MUD.defaultBlockState(), 40));
		register(entries, ORE_CALCITE, Feature.ORE, new OreConfiguration(NATURAL_STONE, Blocks.CALCITE.defaultBlockState(), 64));
		register(entries, DEEPSLATE_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.DEEPSLATE), PlacementUtils.inlinePlaced(conf.getOrThrow(BLANK_SHUT_UP)), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
		register(entries, STONE_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.STONE), PlacementUtils.inlinePlaced(conf.getOrThrow(BLANK_SHUT_UP)), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
		register(entries, UPWARDS_MESOGLEA_PILLAR, WilderWild.UPWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		register(entries, PURPLE_MESOGLEA_PILLAR, WilderWild.UPWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		register(entries, DOWNWARDS_MESOGLEA_PILLAR, WilderWild.DOWNWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		register(entries, DOWNWARDS_PURPLE_MESOGLEA_PILLAR, WilderWild.DOWNWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(FabricWorldgenProvider.Entries entries, ResourceKey<ConfiguredFeature<?, ?>> registryKey, F feature, FC featureConfiguration) {
		FrozenConfiguredFeatureUtils.register(entries, registryKey, feature, featureConfiguration);
	}
}
