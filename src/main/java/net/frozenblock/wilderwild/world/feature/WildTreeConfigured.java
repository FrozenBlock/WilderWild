package net.frozenblock.wilderwild.world.feature;

import com.sun.jna.platform.win32.WinUser;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;

import java.awt.*;
import java.util.List;
import java.util.OptionalInt;

public class WildTreeConfigured {
    private static final BeehiveTreeDecorator NEW_BEES_0002;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_TREE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NEW_BIRCH_BEES_0002;

    public WildTreeConfigured() {
    }
    private static TreeFeatureConfig.Builder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider extraBranchLength, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log), new StraightTrunkWithLogs(baseHeight, firstRandomHeight, secondRandomHeight, logChance, extraBranchLength), BlockStateProvider.of(leaves), new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeFeatureConfig.Builder new_birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 4, 0.2F, ConstantIntProvider.create(1),2).ignoreVines();
    }

    static {
        NEW_BEES_0002 = new BeehiveTreeDecorator(0.002F);
        NEW_BIRCH_TREE = ConfiguredFeatures.register("new_birch_tree", Feature.TREE, new_birch().dirtProvider(BlockStateProvider.of(Blocks.DIRT)).build());
        NEW_BIRCH_BEES_0002 = ConfiguredFeatures.register("new_birch_bees_0002", Feature.TREE, new_birch().decorators(List.of(NEW_BEES_0002)).build());
    }
    public static void registerTreeConfigured() {
    }
}