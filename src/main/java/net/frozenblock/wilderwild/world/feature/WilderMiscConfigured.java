package net.frozenblock.wilderwild.world.feature;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
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
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WilderMiscConfigured {
	private WilderMiscConfigured() {
		throw new UnsupportedOperationException("WilderMiscConfigured contains only static declarations.");
	}

    public static final FrozenConfiguredFeature DISK_COARSE_DIRT = feature("disk_coarse_dirt", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.COARSE_DIRT), BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)), UniformInt.of(6, 8), 1));
    public static final FrozenConfiguredFeature DISK_MUD = feature("disk_mud", Feature.DISK, new DiskConfiguration(new RuleBasedBlockStateProvider(BlockStateProvider.simple(Blocks.MUD), List.of(new RuleBasedBlockStateProvider.Rule(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.solid(Direction.UP.getNormal()), BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.WATER))), BlockStateProvider.simple(Blocks.MUD)))), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)), UniformInt.of(2, 6), 2));
    public static final FrozenConfiguredFeature MUD_PATH = feature("mud_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MUD), 11, 4, 0.1, 0.23, 1, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.CLAY.builtInRegistryHolder(), Blocks.SAND.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature COARSE_PATH = feature("coarse_dirt_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.COARSE_DIRT), 11, 3, 0.12, -0.2, 0.3, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature MOSS_PATH = feature("moss_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MOSS_BLOCK), 9, 1, 0.15, 0.18, 1, true, true, HolderSet.direct(Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature SAND_PATH = feature("sand_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 11, 3, 0.12, -0.2, 0.3, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature PACKED_MUD_PATH = feature("packed_mud_path", WilderWild.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.PACKED_MUD), 9, 1, 0.12, 0.20, 1, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.COARSE_DIRT.builtInRegistryHolder())));

    public static final FrozenConfiguredFeature UNDER_WATER_SAND_PATH = feature("under_water_sand_path", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 16, 4, 0.05, 0.2, 0.54, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature UNDER_WATER_GRAVEL_PATH = feature("under_water_gravel_path", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.GRAVEL), 16, 1, 0.07, -0.7, -0.3, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature UNDER_WATER_CLAY_PATH = feature("under_water_clay_path", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 16, 3, 0.07, 0.5, 0.85, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));

    public static final FrozenConfiguredFeature UNDER_WATER_CLAY_PATH_BEACH = feature("under_water_clay_path_beach", WilderWild.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 14, 2, 0.10, 0.5, 0.85, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));

    public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WilderBlockTags.PACKED_MUD_REPLACEABLE);
    public static final FrozenConfiguredFeature ORE_PACKED_MUD = feature("ore_packed_mud", Feature.ORE, new OreConfiguration(PACKED_MUD_REPLACEABLE, Blocks.PACKED_MUD.defaultBlockState(), 40));

    public static final FrozenConfiguredFeature ORE_CALCITE = feature("ore_calcite", Feature.ORE, new OreConfiguration(new BlockMatchTest(Blocks.STONE), Blocks.CALCITE.defaultBlockState(), 64));
    public static final FrozenConfiguredFeature BLANK_SHUT_UP = feature("blank_shut_up", Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
            PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new SimpleStateProvider(Blocks.WATER.defaultBlockState())))))
    );

    public static final FrozenConfiguredFeature UPWARDS_MESOGLEA_PILLAR = feature("blue_mesoglea_pillar", WilderWild.UPWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature PURPLE_MESOGLEA_PILLAR = feature("purple_mesoglea_pillar", WilderWild.UPWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature DOWNWARDS_MESOGLEA_PILLAR = feature("downwards_blue_mesoglea_pillar", WilderWild.DOWNWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
    public static final FrozenConfiguredFeature DOWNWARDS_PURPLE_MESOGLEA_PILLAR = feature("downwards_purple_mesoglea_pillar", WilderWild.DOWNWARDS_PILLAR_FEATURE, new WilderPillarConfig(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));

	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_POOL = FrozenConfiguredFeatureUtils.createKey(WilderSharedConstants.MOD_ID, "deepslate_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_POOL = FrozenConfiguredFeatureUtils.createKey(WilderSharedConstants.MOD_ID, "stone_pool");

    public static void registerMiscConfigured() {
        WilderWild.logWild("Registering WilderMiscConfigured for", true);
    }

	public static void bootstap(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext) throws IllegalAccessException {
		HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstapContext.lookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		for (Field field : Arrays.stream(WilderMiscConfigured.class.getDeclaredFields()).sorted().toList()) {
			Object whatIsThis = field.get(WilderMiscConfigured.class);
			if (whatIsThis instanceof FrozenConfiguredFeature feature) {
				FrozenConfiguredFeatureUtils.register(bootstapContext, feature.resourceKey, feature.feature, feature.featureConfiguration);
			}
		}
		FrozenConfiguredFeatureUtils.register(bootstapContext, DEEPSLATE_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.DEEPSLATE), PlacementUtils.inlinePlaced(holderGetter.getOrThrow(BLANK_SHUT_UP.resourceKey)), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
		FrozenConfiguredFeatureUtils.register(bootstapContext, STONE_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.STONE), PlacementUtils.inlinePlaced(holderGetter.getOrThrow(BLANK_SHUT_UP.resourceKey)), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
	}

	private static FrozenConfiguredFeature feature(String id, Feature feature, FeatureConfiguration featureConfiguration) {
		return FrozenConfiguredFeatureUtils.feature(WilderSharedConstants.MOD_ID, id, feature, featureConfiguration);
	}
}
