package net.frozenblock.wilderwild.world.feature;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.util.FrozenConfiguredFeatureUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.treedecorators.HeightBasedVineTrunkDecorator;
import net.frozenblock.wilderwild.world.gen.treedecorators.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.gen.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;


public final class WilderTreeConfigured {
	private WilderTreeConfigured() {
		throw new UnsupportedOperationException("WilderTreeConfigured contains only static declarations.");
	}

    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_007 = new ShelfFungusTreeDecorator(0.074F, 0.3F);
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_006 = new ShelfFungusTreeDecorator(0.064F, 0.15F);
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_006_ONLY_BROWN = new ShelfFungusTreeDecorator(0.064F, 0.0F);
    private static final HeightBasedVineTrunkDecorator VINES_012_UNDER_76 = new HeightBasedVineTrunkDecorator(0.12F, 76, 0.25F);
    private static final HeightBasedVineTrunkDecorator VINES_008_UNDER_82 = new HeightBasedVineTrunkDecorator(0.08F, 82, 0.25F);
    private static final HeightBasedVineTrunkDecorator VINES_1_UNDER_260_03 = new HeightBasedVineTrunkDecorator(1F, 260, 0.3F);
    private static final HeightBasedVineTrunkDecorator VINES_1_UNDER_260_05 = new HeightBasedVineTrunkDecorator(1F, 260, 0.5F);
    private static final HeightBasedVineTrunkDecorator VINES_1_UNDER_260_075 = new HeightBasedVineTrunkDecorator(1F, 260, 0.75F);
    private static final HeightBasedVineTrunkDecorator VINES_08_UNDER_260_075 = new HeightBasedVineTrunkDecorator(0.8F, 260, 0.75F);
    private static final BeehiveDecorator NEW_BEES_0004 = new BeehiveDecorator(0.004F);
    private static final BeehiveDecorator NEW_BEES = new BeehiveDecorator(1.0F);

	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH = key("birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_0004 = key("birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_BIRCH = key("dying_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BIRCH_BEES_0004 = key("short_birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES_0004 = key("super_birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_SUPER_BIRCH = key("dying_super_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_TREE = key("fallen_birch_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BIRCH = key("short_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_DYING_BIRCH = key("short_dying_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES = key("super_birch_bees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OAK = key("oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_OAK = key("short_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_0004 = key("oak_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_OAK = key("dying_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK = key("fancy_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DYING_OAK = key("fancy_dying_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DYING_OAK_BEES_0004 = key("fancy_dying_oak_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_0004 = key("fancy_oak_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_OAK_TREE = key("fallen_oak_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES = key("fancy_oak_bees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_DARK_OAK = key("dying_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_DARK_OAK = key("tall_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_TALL_DARK_OAK = key("dying_tall_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_TREE = key("swamp_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE = key("spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_SHORT = key("spruce_short");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGUS_PINE = key("fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_FUNGUS_PINE = key("dying_fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGUS_SPRUCE = key("mega_fungus_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGUS_PINE = key("mega_fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_MEGA_FUNGUS_PINE = key("dying_mega_fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE_TREE = key("fallen_spruce_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BAOBAB = key("baobab");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BAOBAB_TALL = key("baobab_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS = key("cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CYPRESS_TREE = key("fallen_cypress_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGUS_CYPRESS = key("fungus_cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_CYPRESS = key("short_cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_FUNGUS_CYPRESS = key("short_fungus_cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_CYPRESS = key("swamp_cypress");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderWild.logWild("Registering WilderTreeConfigured for", true);
		HolderGetter<ConfiguredFeature<?, ?>> conf = entries.getLookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		register(entries, BIRCH, Feature.TREE, new_birch().dirt(BlockStateProvider.simple(Blocks.DIRT)).decorators(ImmutableList.of(SHELF_FUNGUS_007)).build());
		register(entries, BIRCH_BEES_0004, Feature.TREE, new_birch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_007)).ignoreVines().build());
		register(entries, DYING_BIRCH, Feature.TREE, new_birch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_007)).ignoreVines().build());
		register(entries, SHORT_BIRCH_BEES_0004, Feature.TREE, new_short_birch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_006)).ignoreVines().build());
		register(entries, SUPER_BIRCH_BEES_0004, Feature.TREE, new_superBirch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_007)).build());
		register(entries, DYING_SUPER_BIRCH, Feature.TREE, new_superBirch().decorators(ImmutableList.of(VINES_1_UNDER_260_05, SHELF_FUNGUS_007)).build());
		register(entries, FALLEN_BIRCH_TREE, Feature.TREE, fallen_birch().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		register(entries, SHORT_BIRCH, Feature.TREE, new_short_birch().decorators(ImmutableList.of(SHELF_FUNGUS_006)).ignoreVines().build());
		register(entries, SHORT_DYING_BIRCH, Feature.TREE, new_short_birch().decorators(ImmutableList.of(SHELF_FUNGUS_006, VINES_1_UNDER_260_03)).ignoreVines().build());
		register(entries, SUPER_BIRCH_BEES, Feature.TREE, new_superBirch().decorators(ImmutableList.of(NEW_BEES)).build());
		register(entries, OAK, Feature.TREE, new_oak().build());
		register(entries, SHORT_OAK, Feature.TREE, short_oak().build());
		register(entries, OAK_BEES_0004, Feature.TREE, new_oak().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_006)).ignoreVines().build());
		register(entries, DYING_OAK, Feature.TREE, new_oak().decorators(ImmutableList.of(VINES_1_UNDER_260_03, SHELF_FUNGUS_006)).ignoreVines().build());
		register(entries, FANCY_OAK, Feature.TREE, new_fancyOak().build());
		register(entries, FANCY_DYING_OAK, Feature.TREE, new_fancyOak().decorators(List.of(VINES_1_UNDER_260_05)).build());
		register(entries, FANCY_DYING_OAK_BEES_0004, Feature.TREE, new_fancyOak().decorators(List.of(NEW_BEES_0004, VINES_1_UNDER_260_05)).build());
		register(entries, FANCY_OAK_BEES_0004, Feature.TREE, new_fancyOak().decorators(List.of(NEW_BEES_0004)).build());
		register(entries, FALLEN_OAK_TREE, Feature.TREE, fallen_oak().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		register(entries, FANCY_OAK_BEES, Feature.TREE, new_fancyOak().decorators(List.of(NEW_BEES)).build());
		register(entries, DYING_DARK_OAK, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG), new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).decorators(List.of(VINES_1_UNDER_260_05)).ignoreVines().build());
		register(entries, TALL_DARK_OAK, Feature.TREE, new_tall_dark_oak().ignoreVines().build());
		register(entries, DYING_TALL_DARK_OAK, Feature.TREE, new_tall_dark_oak().decorators(List.of(VINES_1_UNDER_260_05)).ignoreVines().build());
		register(entries, SWAMP_TREE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.MANGROVE_LOG), new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(Blocks.MANGROVE_LEAVES), new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3), Optional.of(new MangroveRootPlacer(UniformInt.of(1, 1), BlockStateProvider.simple(Blocks.MANGROVE_ROOTS), Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.45F)), new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS), BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS), 8, 15, 0.2F))), new TwoLayersFeatureSize(2, 0, 2))).decorators(List.of(new LeaveVineDecorator(0.125F), new AttachedToLeavesDecorator(0.12F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), MangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)))).ignoreVines().dirt(BlockStateProvider.simple(Blocks.MANGROVE_ROOTS)).build());
		register(entries, SPRUCE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(8, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(2, 3)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN)).ignoreVines().build());
		register(entries, SPRUCE_SHORT, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(3, 1, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(1, 2), UniformInt.of(0, 2), UniformInt.of(2, 3)), new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());
		register(entries, FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN)).ignoreVines().build());
		register(entries, DYING_FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_1_UNDER_260_05)).ignoreVines().build());
		register(entries, MEGA_FUNGUS_SPRUCE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN)).build());
		register(entries, MEGA_FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN)).build());
		register(entries, DYING_MEGA_FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN, VINES_1_UNDER_260_075)).build());
		register(entries, FALLEN_SPRUCE_TREE, Feature.TREE, fallen_spruce().decorators(List.of(VINES_1_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		register(entries, BAOBAB, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(13, 3, 2), BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).decorators(List.of(new AttachedToLeavesDecorator(0.04F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), BaobabNutBlock.AGE, UniformInt.of(0, 2)), 4, List.of(Direction.DOWN)))).ignoreVines().build());
		register(entries, BAOBAB_TALL, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(16, 4, 2), BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).decorators(List.of(new AttachedToLeavesDecorator(0.04F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), BaobabNutBlock.AGE, UniformInt.of(0, 2)), 4, List.of(Direction.DOWN)))).ignoreVines().build());
		register(entries, CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(6, 2, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(VINES_012_UNDER_76)).ignoreVines().build());
		register(entries, FALLEN_CYPRESS_TREE, Feature.TREE, fallen_cypress().decorators(List.of(VINES_008_UNDER_82)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		register(entries, FUNGUS_CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(8, 4, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_008_UNDER_82)).ignoreVines().build());
		register(entries, SHORT_CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(3, 2, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(VINES_012_UNDER_76)).ignoreVines().build());
		register(entries, SHORT_FUNGUS_CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(4, 3, 1), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_008_UNDER_82)).ignoreVines().build());
		register(entries, SWAMP_CYPRESS, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new UpwardsBranchingTrunkPlacer(15, 5, 2, UniformInt.of(4, 5), 0.2F, UniformInt.of(1, 3), Registry.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 14), new TwoLayersFeatureSize(1, 0, 1))).decorators(ImmutableList.of(new LeaveVineDecorator(0.1F), SHELF_FUNGUS_006_ONLY_BROWN, VINES_008_UNDER_82)).build());
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(FabricWorldgenProvider.Entries entries, ResourceKey<ConfiguredFeature<?, ?>> registryKey, F feature, FC featureConfiguration) {
		FrozenConfiguredFeatureUtils.register(entries, registryKey, feature, featureConfiguration);
	}

	private static TreeConfiguration.TreeConfigurationBuilder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkWithLogs(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, logHeightFromTop, extraBranchLength),
				BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
				new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder fallenTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, float logChance, float mossChance, IntProvider maxLogs, IntProvider maxHeightAboveHole, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FallenTrunkWithLogs(baseHeight, firstRHeight, secondRHeight, logChance, mossChance, maxLogs, maxHeightAboveHole),
				BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
				new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder darkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()));
	}

	private static TreeConfiguration.TreeConfigurationBuilder new_birch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder new_superBirch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder new_short_birch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 6, 2, 2, 0.12F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder fallen_birch() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.4F, 0.47F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder new_oak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder short_oak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 1, 0, 0.095F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder new_fancyOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 16, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder fallen_oak() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.4F, 0.4F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder fallen_cypress() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RegisterBlocks.CYPRESS_LEAVES, 3, 1, 2, 0.4F, 0.6F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder new_tall_dark_oak() {
		return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 7, 3, 2, 1).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder fallen_spruce() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, 0.5F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}
}
