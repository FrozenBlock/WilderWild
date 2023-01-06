package net.frozenblock.wilderwild.world.additions.feature;

import java.util.OptionalInt;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.frozenblock.wilderwild.world.generation.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.treedecorators.HeightBasedVineTrunkDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.LeavesAroundTopLogDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.generation.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.generation.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.StraightTrunkWithLogs;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;


public final class WilderTreeConfigured {
	private WilderTreeConfigured() {
		throw new UnsupportedOperationException("WilderTreeConfigured contains only static declarations.");
	}

    public static final ShelfFungusTreeDecorator SHELF_FUNGUS_007 = new ShelfFungusTreeDecorator(0.074F, 0.3F);
    public static final ShelfFungusTreeDecorator SHELF_FUNGUS_006 = new ShelfFungusTreeDecorator(0.064F, 0.15F);
    public static final ShelfFungusTreeDecorator SHELF_FUNGUS_006_ONLY_BROWN = new ShelfFungusTreeDecorator(0.064F, 0.0F);
    public static final HeightBasedVineTrunkDecorator VINES_012_UNDER_76 = new HeightBasedVineTrunkDecorator(0.12F, 76, 0.25F);
    public static final HeightBasedVineTrunkDecorator VINES_008_UNDER_82 = new HeightBasedVineTrunkDecorator(0.08F, 82, 0.25F);
    public static final HeightBasedVineTrunkDecorator VINES_1_UNDER_260_03 = new HeightBasedVineTrunkDecorator(1F, 260, 0.3F);
    public static final HeightBasedVineTrunkDecorator VINES_1_UNDER_260_05 = new HeightBasedVineTrunkDecorator(1F, 260, 0.5F);
    public static final HeightBasedVineTrunkDecorator VINES_1_UNDER_260_075 = new HeightBasedVineTrunkDecorator(1F, 260, 0.75F);
    public static final HeightBasedVineTrunkDecorator VINES_08_UNDER_260_075 = new HeightBasedVineTrunkDecorator(0.8F, 260, 0.75F);
    public static final BeehiveDecorator NEW_BEES_0004 = new BeehiveDecorator(0.004F);
    public static final BeehiveDecorator NEW_BEES = new BeehiveDecorator(1.0F);

	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH = key("birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_0004 = key("birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_BIRCH = key("dying_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BIRCH_BEES_0004 = key("short_birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES_0004 = key("super_birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_SUPER_BIRCH = key("dying_super_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_TREE = key("fallen_birch_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BIRCH = key("short_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_SHORT_BIRCH = key("dying_short_birch");
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

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

	public static TreeConfiguration.TreeConfigurationBuilder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkWithLogs(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, logHeightFromTop, extraBranchLength),
				BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
				new TwoLayersFeatureSize(1, 0, 1));
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallenTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, float logChance, float mossChance, IntProvider maxLogs, IntProvider maxHeightAboveHole, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FallenTrunkWithLogs(baseHeight, firstRHeight, secondRHeight, logChance, mossChance, maxLogs, maxHeightAboveHole),
				BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
				new TwoLayersFeatureSize(1, 0, 1));
	}

	public static TreeConfiguration.TreeConfigurationBuilder darkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()));
	}

	private static TreeConfiguration.TreeConfigurationBuilder palmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int minRad, int maxRad, int minFronds, int maxFronds) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new PalmTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new PalmFoliagePlacer(UniformInt.of(minRad, maxRad), ConstantInt.of(0), UniformInt.of(minFronds, maxFronds)),
				new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder winePalmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new ShortPalmFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1));
	}

    public static TreeConfiguration.TreeConfigurationBuilder new_birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

	public static TreeConfiguration.TreeConfigurationBuilder new_superBirch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder new_short_birch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 6, 2, 2, 0.12F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallen_birch() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.4F, 0.47F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder new_oak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder short_oak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 1, 0, 0.095F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder new_fancyOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 16, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallen_oak() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.4F, 0.4F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallen_cypress() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RegisterBlocks.CYPRESS_LEAVES, 3, 1, 2, 0.4F, 0.6F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder new_tall_dark_oak() {
		return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 7, 3, 2, 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallen_spruce() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, 0.5F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}
}
