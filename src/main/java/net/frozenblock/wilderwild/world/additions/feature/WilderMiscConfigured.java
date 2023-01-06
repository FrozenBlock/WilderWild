package net.frozenblock.wilderwild.world.additions.feature;

import net.frozenblock.lib.feature.FrozenFeatures;
import net.frozenblock.lib.feature.features.config.PathFeatureConfig;
import net.frozenblock.lib.feature.features.config.PathSwapUnderWaterFeatureConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

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
	public static final ResourceKey<ConfiguredFeature<?, ?>> SANDSTONE_PATH = key("sandstone_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PACKED_MUD_PATH = key("packed_mud_path");

	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_SAND_PATH = key("under_water_sand_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_GRAVEL_PATH = key("under_water_gravel_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_CLAY_PATH = key("under_water_clay_path");

	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_CLAY_PATH_BEACH = key("under_water_clay_path_beach");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_GRAVEL_PATH_RIVER = key("under_water_gravel_path_river");

	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PACKED_MUD = key("ore_packed_mud");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CALCITE = key("ore_calcite");

	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_POOL = key("deepslate_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_POOL = key("stone_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPWARDS_MESOGLEA_PILLAR = key("blue_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_PILLAR = key("purple_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DOWNWARDS_MESOGLEA_PILLAR = key("downwards_blue_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DOWNWARDS_PURPLE_MESOGLEA_PILLAR = key("downwards_purple_mesoglea_pillar");
	// OASIS
	public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> SAND_POOL = WilderConfiguredFeatures.register("sand_pool", FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(WilderBlockTags.SAND_POOL_REPLACEABLE, BlockStateProvider.simple(Blocks.SAND), PlacementUtils.inlinePlaced(BLANK_SHUT_UP), CaveSurface.FLOOR, ConstantInt.of(5), 0.8F, 1, 0.000F, UniformInt.of(8, 14), 0.7F));
	public static final Holder<ConfiguredFeature<PathSwapUnderWaterFeatureConfig, ?>> GRASS_PATH = WilderConfiguredFeatures.register("grass_path", FrozenFeatures.NOISE_PATH_SWAP_UNDER_WATER_FEATURE, new PathSwapUnderWaterFeatureConfig(BlockStateProvider.simple(Blocks.GRASS_BLOCK), BlockStateProvider.simple(Blocks.DIRT), 11, 4, 0.15, 0.4, 1.0, false, false, HolderSet.direct(Blocks.SAND.builtInRegistryHolder(), Blocks.SANDSTONE.builtInRegistryHolder())));
	public static final Holder<ConfiguredFeature<PathFeatureConfig, ?>> MOSS_PATH_OASIS = WilderConfiguredFeatures.register("moss_path_oasis", FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MOSS_BLOCK), 9, 2, 0.10, 0.12, 1, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, WilderSharedConstants.id(path));
	}
}
