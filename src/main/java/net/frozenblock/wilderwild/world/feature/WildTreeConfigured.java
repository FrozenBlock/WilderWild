package net.frozenblock.wilderwild.world.feature;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.treedecorators.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.gen.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PropaguleBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.root.MangroveRootPlacement;
import net.minecraft.world.gen.root.MangroveRootPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.AttachedToLeavesTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


public class WildTreeConfigured {
    private static final BeehiveTreeDecorator NEW_BEES_0004;
    private static final BeehiveTreeDecorator NEW_BEES;
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_008;
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_007;
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_007_ONLY_BROWN;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_BEES_0004;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SHORT_BIRCH_BEES_0004;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SUPER_BIRCH_BEES_0004;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FALLEN_BIRCH_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_OAK;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_OAK_BEES_0004;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FANCY_OAK;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FANCY_OAK_BEES_0004;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FALLEN_OAK_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_TALL_DARK_OAK;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SWAMP_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SPRUCE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SPRUCE_SHORT;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FUNGUS_PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FALLEN_SPRUCE_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> MEGA_FUNGUS_SPRUCE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> MEGA_FUNGUS_PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> SHORT_BIRCH;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_SUPER_BIRCH_BEES;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_FANCY_OAK_BEES;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FUCK_TREE;

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

    private static TreeFeatureConfig.Builder darkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log), new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
                BlockStateProvider.of(leaves), new DarkOakFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0)),
                new ThreeLayersFeatureSize(1, 1,0,1,2, OptionalInt.empty()));
    }

    private static TreeFeatureConfig.Builder new_birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.2F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_superBirch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.2F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2 ).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_short_birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 6, 2, 2, 0.15F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2).ignoreVines();
    }
    private static TreeFeatureConfig.Builder fallen_birch() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.4F, 0.47F, UniformIntProvider.create(1, 2), UniformIntProvider.create(1, 2), 1).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_oak() {
        return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformIntProvider.create(1,2), UniformIntProvider.create(1,3), ConstantIntProvider.create(1),2).ignoreVines();
    }
    private static TreeFeatureConfig.Builder new_fancyOak() {
        return (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(5, 16, 0), BlockStateProvider.of(Blocks.OAK_LEAVES), new LargeOakFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(3), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }
    private static TreeFeatureConfig.Builder fallen_oak() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.4F, 0.4F, UniformIntProvider.create(1, 2), UniformIntProvider.create(1, 2), 1).ignoreVines();
    }

    private static TreeFeatureConfig.Builder new_tall_dark_oak() {
        return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 7, 3, 2, 1).ignoreVines();
    }
    private static TreeFeatureConfig.Builder fallen_spruce() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, 0.5F, UniformIntProvider.create(1, 2), UniformIntProvider.create(1, 2), 1).ignoreVines();
    }

    static {
        SHELF_FUNGUS_008 = new ShelfFungusTreeDecorator(0.08F, 0.3F);
        SHELF_FUNGUS_007 = new ShelfFungusTreeDecorator(0.07F, 0.15F);
        SHELF_FUNGUS_007_ONLY_BROWN = new ShelfFungusTreeDecorator(0.07F, 0.0F);
        NEW_BEES_0004 = new BeehiveTreeDecorator(0.004F);
        NEW_BEES = new BeehiveTreeDecorator(1.0F);
        //BIRCH
        NEW_BIRCH_TREE = ConfiguredFeatures.register("new_birch_tree", Feature.TREE, new_birch().dirtProvider(BlockStateProvider.of(Blocks.DIRT)).decorators(ImmutableList.of(SHELF_FUNGUS_008)).build());
        NEW_BIRCH_BEES_0004 = ConfiguredFeatures.register("new_birch_bees_0002", Feature.TREE, new_birch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_008)).ignoreVines().build());
        NEW_SHORT_BIRCH_BEES_0004 = ConfiguredFeatures.register("new_short_birch_bees_0004", Feature.TREE, new_short_birch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_007)).ignoreVines().build());
        NEW_SUPER_BIRCH_BEES_0004 = ConfiguredFeatures.register("new_super_birch_bees_0004", Feature.TREE, new_superBirch().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_008)).build());
        NEW_FALLEN_BIRCH_TREE = ConfiguredFeatures.register("new_fallen_birch_tree", Feature.TREE, fallen_birch().dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());
        SHORT_BIRCH = ConfiguredFeatures.register("short_birch", Feature.TREE, new_short_birch().decorators(ImmutableList.of(SHELF_FUNGUS_007)).ignoreVines().build());
        NEW_SUPER_BIRCH_BEES = ConfiguredFeatures.register("new_super_birch_bees", Feature.TREE, new_superBirch().decorators(ImmutableList.of(NEW_BEES)).build());
        //OAK
        NEW_OAK = ConfiguredFeatures.register("new_oak", Feature.TREE, new_oak().build());
        NEW_OAK_BEES_0004 = ConfiguredFeatures.register("new_oak_bees_0004", Feature.TREE, new_oak().decorators(ImmutableList.of(NEW_BEES_0004, SHELF_FUNGUS_007)).ignoreVines().build());
        NEW_FANCY_OAK = ConfiguredFeatures.register("new_fancy_oak", Feature.TREE, new_fancyOak().build());
        NEW_FANCY_OAK_BEES_0004 = ConfiguredFeatures.register("new_fancy_oak_bees_0004", Feature.TREE, new_fancyOak().decorators(List.of(NEW_BEES_0004)).build());
        NEW_FALLEN_OAK_TREE = ConfiguredFeatures.register("new_fallen_oak_tree", Feature.TREE, fallen_oak().dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());
        NEW_FANCY_OAK_BEES = ConfiguredFeatures.register("new_fancy_oak_bees", Feature.TREE, new_fancyOak().decorators(List.of(NEW_BEES)).build());
        //DARK OAK
        NEW_TALL_DARK_OAK = ConfiguredFeatures.register("new_tall_dark_oak", Feature.TREE, new_tall_dark_oak().ignoreVines().build());
        //SWAMP TREE
        NEW_SWAMP_TREE = ConfiguredFeatures.register("new_swamp_tree", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.MANGROVE_LOG),
                new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.of(Blocks.MANGROVE_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), 3), Optional.of(new MangroveRootPlacer(UniformIntProvider.create(1, 3), BlockStateProvider.of(Blocks.MANGROVE_ROOTS), Optional.of(new AboveRootPlacement(BlockStateProvider.of(Blocks.MOSS_CARPET), 0.5F)),
                new MangroveRootPlacement(Registry.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), RegistryEntryList.of(Block::getRegistryEntry, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS), BlockStateProvider.of(Blocks.MUD), 8, 15, 0.2F))),
                new TwoLayersFeatureSize(2, 0, 2))).decorators(List.of(new LeavesVineTreeDecorator(0.125F),
                new AttachedToLeavesTreeDecorator(0.14F, 1, 0,
                        new RandomizedIntBlockStateProvider(BlockStateProvider.of(Blocks.MANGROVE_PROPAGULE.getDefaultState().with(PropaguleBlock.HANGING, true)), PropaguleBlock.AGE, UniformIntProvider.create(0, 4)), 2, List.of(Direction.DOWN)))).ignoreVines().dirtProvider(BlockStateProvider.of(Blocks.MANGROVE_ROOTS)).build());
        //SPRUCE
        NEW_SPRUCE = ConfiguredFeatures.register("new_spruce", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(8, 4, 2), BlockStateProvider.of(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(2, 3)), new TwoLayersFeatureSize(2, 0, 2))).decorators(ImmutableList.of(SHELF_FUNGUS_007_ONLY_BROWN)).ignoreVines().build());
        NEW_SPRUCE_SHORT = ConfiguredFeatures.register("new_spruce_short", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(3, 1, 2), BlockStateProvider.of(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformIntProvider.create(1, 2), UniformIntProvider.create(0, 2), UniformIntProvider.create(2, 3)), new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build());
        FUNGUS_PINE = ConfiguredFeatures.register("fungus_pine", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.of(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), UniformIntProvider.create(3, 4)), new TwoLayersFeatureSize(2, 0, 2))).decorators(ImmutableList.of(SHELF_FUNGUS_007_ONLY_BROWN)).ignoreVines().build());
        MEGA_FUNGUS_SPRUCE = ConfiguredFeatures.register("mega_fungus_spruce", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.of(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)), SHELF_FUNGUS_007_ONLY_BROWN)).build());
        MEGA_FUNGUS_PINE = ConfiguredFeatures.register("mega_fungus_pine", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.of(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(3, 7)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)), SHELF_FUNGUS_007_ONLY_BROWN)).build());
        FALLEN_SPRUCE_TREE = ConfiguredFeatures.register("fallen_spruce_tree", Feature.TREE, fallen_spruce().dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());
        //FUCK
        FUCK_TREE = ConfiguredFeatures.register("fuck_tree", Feature.TREE, (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.ACACIA_LOG), new BaobabTrunkPlacer(8, 1, 2), BlockStateProvider.of(Blocks.ACACIA_LEAVES), new AcaciaFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of()).build());
    }
    public static void registerTreeConfigured() {
        WilderWild.logWild("Registering WildTreeConfigured for", true);
    }
}