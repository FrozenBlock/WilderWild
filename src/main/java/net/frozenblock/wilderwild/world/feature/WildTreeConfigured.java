package net.frozenblock.wilderwild.world.feature;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.gen.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

public class WildTreeConfigured {
    private static final BeehiveTreeDecorator NEW_BEES_0002;
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_BEES_0002;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SUPER_BIRCH_BEES_0002;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FALLEN_BIRCH_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_OAK;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_OAK_BEES_0002;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FANCY_OAK;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FANCY_OAK_BEES_0002;

    public WildTreeConfigured() {
    }
    private static TreeFeatureConfig.Builder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log), new StraightTrunkWithLogs(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, logHeightFromTop, extraBranchLength),
                BlockStateProvider.of(leaves), new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }
    private static TreeFeatureConfig.Builder fallenTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, float logChance, float mossChance, IntProvider maxLogs, IntProvider maxHeightAboveHole, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log), new FallenTrunkWithLogs(baseHeight, firstRHeight, secondRHeight, logChance, mossChance, maxLogs, maxHeightAboveHole),
                BlockStateProvider.of(leaves), new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3), //FOILAGE PLACER DOES NOTHING
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeFeatureConfig.Builder new_birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.2F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_superBirch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.2F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2 ).ignoreVines();
    }
    private static TreeFeatureConfig.Builder fallen_birch() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.4F, 0.4F, UniformIntProvider.create(1, 2), UniformIntProvider.create(1, 2), 1).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_oak() {
        return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_fancyOak() {
        return (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(5, 16, 0), BlockStateProvider.of(Blocks.OAK_LEAVES), new LargeOakFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    static {
        SHELF_FUNGUS = new ShelfFungusTreeDecorator(0.08F);
        NEW_BEES_0002 = new BeehiveTreeDecorator(0.004F);
        //BIRCH
        NEW_BIRCH_TREE = ConfiguredFeatures.register("new_birch_tree", Feature.TREE, new_birch().dirtProvider(BlockStateProvider.of(Blocks.DIRT)).decorators(ImmutableList.of(SHELF_FUNGUS)).build());
        NEW_BIRCH_BEES_0002 = ConfiguredFeatures.register("new_birch_bees_0002", Feature.TREE, new_birch().decorators(ImmutableList.of(NEW_BEES_0002, SHELF_FUNGUS)).ignoreVines().build());
        NEW_SUPER_BIRCH_BEES_0002 = ConfiguredFeatures.register("new_super_birch_bees_0002", Feature.TREE, new_superBirch().decorators(ImmutableList.of(NEW_BEES_0002, SHELF_FUNGUS)).build());
        NEW_FALLEN_BIRCH_TREE = ConfiguredFeatures.register("new_fallen_birch_tree", Feature.TREE, fallen_birch().decorators(List.of(new ShelfFungusTreeDecorator(0.08F))).dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());
        //OAK
        NEW_OAK = ConfiguredFeatures.register("new_oak", Feature.TREE, new_oak().build());
        NEW_OAK_BEES_0002 = ConfiguredFeatures.register("new_oak_bees_0002", Feature.TREE, new_oak().decorators(ImmutableList.of(NEW_BEES_0002, new ShelfFungusTreeDecorator(0.07F))).ignoreVines().build());
        NEW_FANCY_OAK = ConfiguredFeatures.register("new_fancy_oak", Feature.TREE, new_fancyOak().build());
        NEW_FANCY_OAK_BEES_0002 = ConfiguredFeatures.register("new_fancy_oak_bees_0002", Feature.TREE, new_fancyOak().decorators(List.of(NEW_BEES_0002)).build());
    }
    public static void registerTreeConfigured() {
    }
}