package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.NematocystFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WilderConfiguredFeatures  {
	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_TREES_MIXED = key("fallen_trees_mixed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH = key("fallen_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE = key("fallen_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE_AND_OAK = key("fallen_spruce_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_AND_OAK = key("fallen_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CYPRESS_AND_OAK = key("fallen_cypress_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PLAINS = key("trees_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH = key("trees_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_TALL = key("trees_birch_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FOREST = key("trees_flower_forest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_TREES = key("mixed_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TAIGA = key("trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_TREES_TAIGA = key("short_trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GROVE = key("trees_grove");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEADOW_TREES = key("meadow_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAVANNA_TREES = key("savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_SAPLING = key("cypress_wetlands_trees_sapling");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEEDING_DANDELION = key("seeding_dandelion");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CARNATION = key("carnation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DATURA = key("datura");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PLAIN = key("flower_plain");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MILKWEED = key("milkweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GLORY_OF_THE_SNOW = key("glory_of_the_snow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> POLLEN = key("pollen");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_SHELF_FUNGUS = key("brown_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_SHELF_FUNGUS = key("red_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL = key("cattail");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_ALGAE = key("patch_algae");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TERMITE = key("termite_mound_baobab");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA = key("mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA_POOL = key("mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_BLUE_MESOGLEA = key("jellyfish_caves_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA = key("mesoglea_with_dripleaves");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_POOL = key("purple_mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("jellyfish_caves_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = key("upside_down_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEMATOCYST_UP = key("patch_nematocyst_up");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEMATOCYST_DOWN = key("patch_nematocyst_down");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEMATOCYST_NORTH = key("patch_nematocyst_north");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEMATOCYST_SOUTH = key("patch_nematocyst_south");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEMATOCYST_EAST = key("patch_nematocyst_east");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEMATOCYST_WEST = key("patch_nematocyst_west");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderWild.logWild("Registering WilderConfiguredFeatures for", true);
		HolderGetter<ConfiguredFeature<?, ?>> conf = entries.getLookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		HolderGetter<PlacedFeature> placed = entries.getLookup(Registry.PLACED_FEATURE_REGISTRY);
		register(entries, FALLEN_TREES_MIXED, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_SPRUCE_CHECKED), 0.4F)), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_BIRCH_CHECKED), 0.3F)), placed.getOrThrow(WilderTreePlaced.FALLEN_OAK_CHECKED)));
		register(entries, FALLEN_BIRCH, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_BIRCH_CHECKED), 1.0F)), placed.getOrThrow(WilderTreePlaced.FALLEN_BIRCH_CHECKED)));
		register(entries, FALLEN_SPRUCE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_SPRUCE_CHECKED), 1.0F)), placed.getOrThrow(WilderTreePlaced.FALLEN_SPRUCE_CHECKED)));
		register(entries, FALLEN_SPRUCE_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_SPRUCE_CHECKED), 0.55F)), placed.getOrThrow(WilderTreePlaced.FALLEN_OAK_CHECKED)));
		register(entries, FALLEN_BIRCH_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_BIRCH_CHECKED), 0.35F)), placed.getOrThrow(WilderTreePlaced.FALLEN_OAK_CHECKED)));
		register(entries, FALLEN_CYPRESS_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FALLEN_OAK_CHECKED), 0.35F)), placed.getOrThrow(WilderTreePlaced.FALLEN_CYPRESS_CHECKED)));
		register(entries, TREES_PLAINS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(conf.getOrThrow(WilderTreeConfigured.FANCY_OAK_BEES_0004)), 0.33333334F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(conf.getOrThrow(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004)), 0.035F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(conf.getOrThrow(WilderTreeConfigured.SHORT_OAK)), 0.169F)), PlacementUtils.inlinePlaced(conf.getOrThrow(WilderTreeConfigured.OAK_BEES_0004))));
		register(entries, TREES_BIRCH_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_BIRCH_BEES_0004), 0.2F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH), 0.04F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FANCY_OAK_BEES_0004), 0.26F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004), 0.055F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_OAK_CHECKED), 0.04F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_OAK_CHECKED), 0.155F)), placed.getOrThrow(WilderTreePlaced.OAK_BEES_0004)));
		register(entries, TREES_BIRCH, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_BIRCH_BEES_0004), 0.065F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH), 0.012F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_BIRCH), 0.035F)), placed.getOrThrow(WilderTreePlaced.BIRCH_BEES_0004)));
		register(entries, TREES_BIRCH_TALL, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_BIRCH_BEES_0004), 0.002F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH), 0.0001F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SUPER_BIRCH), 0.032F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.BIRCH_BEES_0004), 0.02F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_BIRCH), 0.017F)), placed.getOrThrow(WilderTreePlaced.SUPER_BIRCH_BEES_0004)));
		register(entries, TREES_FLOWER_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_BIRCH_BEES_0004), 0.2F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH), 0.035F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_OAK_CHECKED), 0.05F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004), 0.063F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FANCY_OAK_BEES_0004), 0.205F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_OAK_CHECKED), 0.095F)), placed.getOrThrow(WilderTreePlaced.OAK_BEES_0004)));
		register(entries, MIXED_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SPRUCE_CHECKED), 0.39F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FUNGUS_PINE_CHECKED), 0.086F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED), 0.02F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SPRUCE_SHORT_CHECKED), 0.13F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FANCY_OAK_BEES_0004), 0.37F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004), 0.025F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_OAK_CHECKED), 0.01F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH), 0.01F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_OAK_CHECKED), 0.13F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_BIRCH), 0.325F)), placed.getOrThrow(WilderTreePlaced.OAK_CHECKED)));
		register(entries, DARK_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F), new WeightedPlacedFeature(FrozenPlacementUtils.getHolder(TreePlacements.DARK_OAK_CHECKED), 0.55F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_DARK_OAK_CHECKED), 0.075F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_BIRCH), 0.2F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH), 0.015F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.TALL_DARK_OAK_CHECKED), 0.35F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED), 0.048F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_CHECKED), 0.02F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_OAK_CHECKED), 0.012F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FANCY_OAK_CHECKED), 0.185F)), placed.getOrThrow(WilderTreePlaced.OAK_CHECKED)));
		register(entries, TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FUNGUS_PINE_CHECKED), 0.33333334F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED), 0.075F)), placed.getOrThrow(WilderTreePlaced.SPRUCE_CHECKED)));
		register(entries, SHORT_TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SPRUCE_SHORT_CHECKED), 0.33333334F)), placed.getOrThrow(WilderTreePlaced.SPRUCE_SHORT_CHECKED)));
		register(entries, TREES_OLD_GROWTH_PINE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED), 0.025641026F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED), 0.028F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED), 0.30769232F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED), 0.045F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FUNGUS_PINE_CHECKED), 0.33333334F)), placed.getOrThrow(WilderTreePlaced.SPRUCE_CHECKED)));
		register(entries, TREES_OLD_GROWTH_SPRUCE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED), 0.33333334F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED), 0.075F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FUNGUS_PINE_CHECKED), 0.33333334F)), placed.getOrThrow(WilderTreePlaced.SPRUCE_CHECKED)));
		register(entries, TREES_GROVE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FUNGUS_PINE_ON_SNOW), 0.33333334F)), placed.getOrThrow(WilderTreePlaced.SPRUCE_ON_SNOW)));
		register(entries, TREES_WINDSWEPT_HILLS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SPRUCE_CHECKED), 0.666F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_CHECKED), 0.01F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.DYING_OAK_CHECKED), 0.02F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FANCY_OAK_CHECKED), 0.1F)), placed.getOrThrow(WilderTreePlaced.OAK_CHECKED)));
		register(entries, MEADOW_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.FANCY_OAK_BEES), 0.5F)), placed.getOrThrow(WilderTreePlaced.SUPER_BIRCH_BEES)));
		register(entries, SAVANNA_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(FrozenPlacementUtils.getHolder(TreePlacements.ACACIA_CHECKED), 0.8F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.BAOBAB), 0.062F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.BAOBAB_TALL), 0.035F)), placed.getOrThrow(WilderTreePlaced.OAK_CHECKED)));
		register(entries, WINDSWEPT_SAVANNA_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(FrozenPlacementUtils.getHolder(TreePlacements.ACACIA_CHECKED), 0.8F)), placed.getOrThrow(WilderTreePlaced.OAK_CHECKED)));
		register(entries, CYPRESS_WETLANDS_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.CYPRESS), 0.37F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_CYPRESS), 0.25F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SWAMP_CYPRESS), 0.81F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.OAK_CHECKED), 0.1F)), placed.getOrThrow(WilderTreePlaced.FUNGUS_CYPRESS)));
		register(entries, CYPRESS_WETLANDS_TREES_SAPLING, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.CYPRESS), 0.4F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_CYPRESS), 0.15F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SWAMP_CYPRESS), 0.81F)), placed.getOrThrow(WilderTreePlaced.FUNGUS_CYPRESS)));
		register(entries, CYPRESS_WETLANDS_TREES_WATER, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.CYPRESS), 0.2F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SHORT_CYPRESS), 0.1F), new WeightedPlacedFeature(placed.getOrThrow(WilderTreePlaced.SWAMP_CYPRESS), 0.85F)), placed.getOrThrow(WilderTreePlaced.FUNGUS_CYPRESS)));
		register(entries, SEEDING_DANDELION, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));
		register(entries, CARNATION, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));
		register(entries, DATURA, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));
		register(entries, FLOWER_PLAIN, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));
		register(entries, MILKWEED, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));
		register(entries, GLORY_OF_THE_SNOW, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL)))));
		register(entries, LARGE_FERN_AND_GRASS, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL)))));
		register(entries, POLLEN, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));
		register(entries, BROWN_SHELF_FUNGUS, WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));
		register(entries, RED_SHELF_FUNGUS, WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));
		register(entries, CATTAIL, WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));
		register(entries, PATCH_FLOWERED_WATERLILY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));
		register(entries, PATCH_ALGAE, WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));
		register(entries, TERMITE, WilderWild.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));
		register(entries, BLUE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 2, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(entries, BLUE_MESOGLEA_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(entries, JELLYFISH_CAVES_BLUE_MESOGLEA, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(conf.getOrThrow(BLUE_MESOGLEA)), PlacementUtils.inlinePlaced(conf.getOrThrow(BLUE_MESOGLEA_POOL))));
		register(entries, UPSIDE_DOWN_BLUE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(conf.getOrThrow(WilderMiscConfigured.DOWNWARDS_MESOGLEA_PILLAR)), CaveSurface.CEILING, ConstantInt.of(3), 0.8F, 2, 0.08F, UniformInt.of(4, 14), 0.7F));
		register(entries, PURPLE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 2, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(entries, PURPLE_MESOGLEA_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(entries, JELLYFISH_CAVES_PURPLE_MESOGLEA, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(conf.getOrThrow(PURPLE_MESOGLEA)), PlacementUtils.inlinePlaced(conf.getOrThrow(PURPLE_MESOGLEA_POOL))));
		register(entries, UPSIDE_DOWN_PURPLE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(conf.getOrThrow(WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_PILLAR)), CaveSurface.CEILING, ConstantInt.of(3), 0.8F, 2, 0.08F, UniformInt.of(4, 14), 0.7F));
		register(entries, PATCH_NEMATOCYST_UP, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP))), 128, 16, 6));
		register(entries, PATCH_NEMATOCYST_DOWN, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN))), 64, 16, 6));
		register(entries, PATCH_NEMATOCYST_NORTH, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH))), 32, 8, 8));
		register(entries, PATCH_NEMATOCYST_SOUTH, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH))), 32, 8, 8));
		register(entries, PATCH_NEMATOCYST_EAST, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))), 32, 8, 8));
		register(entries, PATCH_NEMATOCYST_WEST, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))), 32, 8, 8));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(FabricWorldgenProvider.Entries entries, ResourceKey<ConfiguredFeature<?, ?>> registryKey, F feature, FC featureConfiguration) {
		FrozenConfiguredFeatureUtils.register(entries, registryKey, feature, featureConfiguration);
	}

}
