package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.util.FrozenConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.util.FrozenPlacementUtils;
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
import net.minecraft.data.worldgen.BootstapContext;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WilderConfiguredFeatures  {
	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

    //FALLEN TREES
	public static final FrozenConfiguredFeature FALLEN_TREES_MIXED = feature("fallen_trees_mixed", Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.4F)),
					new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED.getHolder(), 0.3F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED.getHolder()));
	public static final FrozenConfiguredFeature FALLEN_BIRCH = feature("fallen_birch", Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED.getHolder(), 1.0F)), WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED.getHolder()));
	public static final FrozenConfiguredFeature FALLEN_SPRUCE = feature("fallen_spruce", Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 1.0F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()));

    public static final FrozenConfiguredFeature FALLEN_SPRUCE_AND_OAK =
            feature("fallen_spruce_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.55F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED.getHolder()));

    public static final FrozenConfiguredFeature NEW_FALLEN_BIRCH_AND_OAK =
            feature("fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED.getHolder(), 0.35F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED.getHolder()));

    public static final FrozenConfiguredFeature NEW_FALLEN_CYPRESS_AND_OAK =
            feature("fallen_cypress_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_OAK_CHECKED.getHolder(), 0.35F)), WilderTreePlaced.NEW_FALLEN_CYPRESS_CHECKED.getHolder()));

    //TREES
    public static final FrozenConfiguredFeature NEW_TREES_PLAINS =
            feature("trees_plains", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004.getHolder()), 0.33333334F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004.getHolder()), 0.035F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK.getHolder()), 0.169F)),
                            PlacementUtils.inlinePlaced(WilderTreeConfigured.NEW_OAK_BEES_0004.getHolder())));

    public static final FrozenConfiguredFeature NEW_TREES_BIRCH_AND_OAK =
            feature("trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004.getHolder(), 0.26F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.055F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.155F)), WilderTreePlaced.NEW_OAK_BEES_0004.getHolder()));

    public static final FrozenConfiguredFeature NEW_TREES_BIRCH =
            feature("trees_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004.getHolder(), 0.065F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.035F)), WilderTreePlaced.NEW_BIRCH_BEES_0004.getHolder()));

    public static final FrozenConfiguredFeature NEW_TREES_BIRCH_TALL =
            feature("trees_birch_tall", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004.getHolder(), 0.002F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.0001F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.032F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_BIRCH_BEES_0004.getHolder(), 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.017F)), WilderTreePlaced.NEW_SUPER_BIRCH_BEES_0004.getHolder()));

    public static final FrozenConfiguredFeature NEW_TREES_FLOWER_FOREST =
            feature("trees_flower_forest", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.035F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.05F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.063F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004.getHolder(), 0.205F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.095F)), WilderTreePlaced.NEW_OAK_BEES_0004.getHolder()));

    public static final FrozenConfiguredFeature MIXED_TREES =
            feature("mixed_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_CHECKED.getHolder(), 0.39F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED.getHolder(), 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004.getHolder(), 0.37F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.025F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.325F)), WilderTreePlaced.NEW_OAK_CHECKED.getHolder()));

    public static final FrozenConfiguredFeature NEW_DARK_FOREST_VEGETATION =
            feature("dark_forest_vegetation", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
                            new WeightedPlacedFeature(FrozenPlacementUtils.getHolder(TreePlacements.DARK_OAK_CHECKED), 0.55F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.015F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_TALL_DARK_OAK_CHECKED.getHolder(), 0.35F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.048F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_CHECKED.getHolder(), 0.185F)), WilderTreePlaced.NEW_OAK_CHECKED.getHolder()));


	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TAIGA = key("trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_TREES_TAIGA = key("short_trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GROVE = key("trees_grove");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext) {
		HolderGetter<ConfiguredFeature<?, ?>> conf = bootstapContext.lookup(Registry.CONFIGURED_FEATURE_REGISTRY);

		register(bootstapContext, TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F), new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F)), WilderTreePlaced.NEW_SPRUCE_CHECKED.getHolder()));
		register(bootstapContext, SHORT_TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED.getHolder(), 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED.getHolder()));
		register(bootstapContext, TREES_OLD_GROWTH_PINE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.025641026F), new WeightedPlacedFeature(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.028F), new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.30769232F), new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.045F), new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED.getHolder()));
		register(bootstapContext, TREES_OLD_GROWTH_SPRUCE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.33333334F), new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F), new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED.getHolder()));
		register(bootstapContext, TREES_GROVE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_ON_SNOW.getHolder(), 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_ON_SNOW.getHolder()));
		register(bootstapContext, TREES_WINDSWEPT_HILLS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_CHECKED.getHolder(), 0.666F), new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.01F), new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.02F), new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_CHECKED.getHolder(), 0.1F)), WilderTreePlaced.NEW_OAK_CHECKED.getHolder()));
		register(bootstapContext, NEW_MEADOW_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES.getHolder(), 0.5F)), WilderTreePlaced.NEW_SUPER_BIRCH_BEES.getHolder()));
		register(bootstapContext, SAVANNA_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(FrozenPlacementUtils.getHolder(TreePlacements.ACACIA_CHECKED), 0.8F), new WeightedPlacedFeature(WilderTreePlaced.BAOBAB.getHolder(), 0.062F), new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL.getHolder(), 0.035F)), WilderTreePlaced.NEW_OAK_CHECKED.getHolder()));
		register(bootstapContext, WINDSWEPT_SAVANNA_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(FrozenPlacementUtils.getHolder(TreePlacements.ACACIA_CHECKED), 0.8F)), WilderTreePlaced.NEW_OAK_CHECKED.getHolder()));
		register(bootstapContext, CYPRESS_WETLANDS_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS.getHolder(), 0.37F), new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS.getHolder(), 0.25F), new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F), new WeightedPlacedFeature(WilderTreePlaced.NEW_OAK_CHECKED.getHolder(), 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS.getHolder()));
		register(bootstapContext, CYPRESS_WETLANDS_TREES_SAPLING, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS.getHolder(), 0.4F), new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS.getHolder(), 0.15F), new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F)), WilderTreePlaced.FUNGUS_CYPRESS.getHolder()));
		register(bootstapContext, CYPRESS_WETLANDS_TREES_WATER, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS.getHolder(), 0.2F), new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS.getHolder(), 0.1F), new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS.getHolder(), 0.85F)), WilderTreePlaced.FUNGUS_CYPRESS.getHolder()));
		register(bootstapContext, SEEDING_DANDELION, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));
		register(bootstapContext, CARNATION, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));
		register(bootstapContext, DATURA, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));
		register(bootstapContext, NEW_FLOWER_PLAIN, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));
		register(bootstapContext, MILKWEED, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));
		register(bootstapContext, GLORY_OF_THE_SNOW, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL)))));
		register(bootstapContext, LARGE_FERN_AND_GRASS, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL)))));
		register(bootstapContext, POLLEN, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));
		register(bootstapContext, BROWN_SHELF_FUNGUS, WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));
		register(bootstapContext, RED_SHELF_FUNGUS, WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));
		register(bootstapContext, CATTAIL, WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));
		register(bootstapContext, PATCH_FLOWERED_WATERLILY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));
		register(bootstapContext, PATCH_ALGAE, WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));
		register(bootstapContext, TERMITE, WilderWild.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));
		register(bootstapContext, BLUE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 2, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(bootstapContext, BLUE_MESOGLEA_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(bootstapContext, JELLYFISH_CAVES_BLUE_MESOGLEA, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(conf.getOrThrow(BLUE_MESOGLEA)), PlacementUtils.inlinePlaced(conf.getOrThrow(BLUE_MESOGLEA_POOL))));
		register(bootstapContext, UPSIDE_DOWN_BLUE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_MESOGLEA_PILLAR.getFeature(), WilderMiscConfigured.DOWNWARDS_MESOGLEA_PILLAR.getFeatureConfiguration()), CaveSurface.CEILING, ConstantInt.of(3), 0.8F, 2, 0.08F, UniformInt.of(4, 14), 0.7F));
		register(bootstapContext, PURPLE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 2, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(bootstapContext, PURPLE_MESOGLEA_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(FrozenConfiguredFeatureUtils.getHolder(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.04F, UniformInt.of(4, 14), 0.7F));
		register(bootstapContext, JELLYFISH_CAVES_PURPLE_MESOGLEA, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(conf.getOrThrow(PURPLE_MESOGLEA)), PlacementUtils.inlinePlaced(conf.getOrThrow(PURPLE_MESOGLEA_POOL))));
		register(bootstapContext, UPSIDE_DOWN_PURPLE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_PILLAR.getFeature(), WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_PILLAR.getFeatureConfiguration()), CaveSurface.CEILING, ConstantInt.of(3), 0.8F, 2, 0.08F, UniformInt.of(4, 14), 0.7F));
		register(bootstapContext, PATCH_NEMATOCYST_UP, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP))), 128, 16, 6));
		register(bootstapContext, PATCH_NEMATOCYST_DOWN, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN))), 64, 16, 6));
		register(bootstapContext, PATCH_NEMATOCYST_NORTH, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH))), 32, 8, 8));
		register(bootstapContext, PATCH_NEMATOCYST_SOUTH, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH))), 32, 8, 8));
		register(bootstapContext, PATCH_NEMATOCYST_EAST, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))), 32, 8, 8));
		register(bootstapContext, PATCH_NEMATOCYST_WEST, WilderWild.NEMATOCYST_FEATURE, new NematocystFeatureConfig(new NoiseProvider(10L, new NormalNoise.NoiseParameters(0, 1.0), 0.3F, List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST), RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))), 32, 8, 8));
	}

	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEW_MEADOW_TREES = key("meadow_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAVANNA_TREES = key("savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_SAPLING = key("cypress_wetlands_trees_sapling");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEEDING_DANDELION = key("seeding_dandelion");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CARNATION = key("carnation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DATURA = key("datura");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEW_FLOWER_PLAIN = key("flower_plain");
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

    public static void registerConfiguredFeatures() {
        WilderWild.logWild("Registering WilderConfiguredFeatures for", true);
    }

	private static RandomPatchConfiguration createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext, ResourceKey<ConfiguredFeature<?, ?>> registryKey, F feature, FC featureConfiguration) {
		FrozenConfiguredFeatureUtils.register(bootstapContext, registryKey, feature, featureConfiguration);
	}

	private static FrozenConfiguredFeature feature(String id, Feature feature, FeatureConfiguration featureConfiguration) {
		return FrozenConfiguredFeatureUtils.feature(WilderSharedConstants.MOD_ID, id, feature, featureConfiguration);
	}

	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();

}
