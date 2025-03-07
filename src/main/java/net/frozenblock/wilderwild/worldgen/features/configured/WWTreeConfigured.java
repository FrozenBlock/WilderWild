/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.features.configured;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils;
import static net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils.register;
import net.frozenblock.wilderwild.worldgen.impl.foliage.MapleFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.NoOpFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.RoundMapleFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.SmallBushFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.WillowFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.WindmillPalmFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.rootplacers.WillowRootPlacement;
import net.frozenblock.wilderwild.worldgen.impl.rootplacers.WillowRootPlacer;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.HeightBasedCobwebTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.HeightBasedVineTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.MossCarpetTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.NetherShelfFungiTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.PaleShelfFungiTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.PollenTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.ShelfFungiTreeDecorator;
import net.frozenblock.wilderwild.worldgen.impl.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.FallenLargeTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.FallenWithBranchesTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.FancyDarkOakTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.LargeSnappedTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.SnappedTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.StraightWithBranchesTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.WillowTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.branch.TrunkBranchPlacement;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CreakingHeartDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.PaleMossDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WWTreeConfigured {
	//BIRCH
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIRCH_TREE = register("birch_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIRCH_BEES_0004 = register("birch_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIRCH_BEES_025 = register("birch_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_BIRCH = register("dying_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SUPER_BIRCH_BEES_0004 = register("super_birch_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_SUPER_BIRCH = register("dying_super_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_BIRCH_TREE = register("fallen_birch_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_BIRCH_TREE = register("mossy_fallen_birch_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_BIRCH_BEES_0004 = register("short_birch_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_BIRCH = register("short_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_DYING_BIRCH = register("short_dying_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEDIUM_BIRCH_BEES_0004 = register("medium_birch_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEDIUM_BIRCH_BEES_025 = register("medium_birch_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEDIUM_BIRCH = register("medium_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEDIUM_DYING_BIRCH = register("medium_dying_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SUPER_BIRCH_BEES = register("super_birch_bees");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SUPER_BIRCH = register("super_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_BIRCH = register("snapped_birch_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_BIRCH = register("dead_birch");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_MEDIUM_BIRCH = register("dead_medium_birch");
	//CHERRY
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE = register("cherry");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_CHERRY_TREE = register("dying_cherry");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_CHERRY_TREE = register("tall_cherry");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_DYING_CHERRY_TREE = register("tall_dying_cherry");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BEES_025 = register("cherry_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_CHERRY_BEES_025 = register("tall_cherry_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_CHERRY_TREE = register("mossy_fallen_cherry_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_CHERRY_TREE = register("fallen_cherry_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_CHERRY_TREE = register("snapped_cherry_tree");
	//MAPLE
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> YELLOW_MAPLE_TREE = register("yellow_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> ROUND_YELLOW_MAPLE_TREE = register("round_yellow_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_YELLOW_MAPLE_TREE = register("dying_yellow_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_YELLOW_MAPLE_TREE = register("tall_yellow_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_YELLOW_DYING_MAPLE_TREE = register("tall_dying_yellow_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> YELLOW_MAPLE_BEES_0004 = register("yellow_maple_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_YELLOW_MAPLE_BEES_0004 = register("tall_yellow_maple_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_YELLOW_MAPLE_TREE = register("short_yellow_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_SHRUB_YELLOW_MAPLE = register("big_shrub_yellow_maple");

	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> ORANGE_MAPLE_TREE = register("orange_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> ROUND_ORANGE_MAPLE_TREE = register("round_orange_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_ORANGE_MAPLE_TREE = register("dying_orange_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_ORANGE_MAPLE_TREE = register("tall_orange_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_ORANGE_DYING_MAPLE_TREE = register("tall_dying_orange_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> ORANGE_MAPLE_BEES_0004 = register("orange_maple_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_ORANGE_MAPLE_BEES_0004 = register("tall_orange_maple_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_ORANGE_MAPLE_TREE = register("short_orange_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_SHRUB_ORANGE_MAPLE = register("big_shrub_orange_maple");

	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE_TREE = register("red_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> ROUND_RED_MAPLE_TREE = register("round_red_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_RED_MAPLE_TREE = register("dying_red_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_RED_MAPLE_TREE = register("tall_red_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_RED_DYING_MAPLE_TREE = register("tall_dying_red_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE_BEES_0004 = register("red_maple_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_RED_MAPLE_BEES_0004 = register("tall_red_maple_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_RED_MAPLE_TREE = register("short_red_maple");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_SHRUB_RED_MAPLE = register("big_shrub_red_maple");

	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_MAPLE_TREE = register("fallen_maple_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_MAPLE_TREE = register("snapped_maple_tree");
	//OAK
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK = register("oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK_LEAF_LITTER = register("oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK_NO_FUNGI = register("oak_no_fungi");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_OAK = register("short_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_OAK_LEAF_LITTER = register("short_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK_BEES_0004 = register("oak_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK_BEES_0004_LEAF_LITTER = register("oak_bees_0004_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_OAK = register("dying_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_OAK_LEAF_LITTER = register("dying_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK = register("fancy_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_LEAF_LITTER = register("fancy_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK = register("fancy_dying_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_LEAF_LITTER = register("fancy_dying_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_BEES_0004 = register("fancy_dying_oak_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_BEES_0004_LEAF_LITTER = register("fancy_dying_oak_bees_0004_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_0004 = register("fancy_oak_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_0004_LEAF_LITTER = register("fancy_oak_bees_0004_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_BEES_025 = register("fancy_dying_oak_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_BEES_025_LEAF_LITTER = register("fancy_dying_oak_bees_025_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_025 = register("fancy_oak_bees_025");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_025_LEAF_LITTER = register("fancy_oak_bees_025_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_OAK_TREE = register("fallen_oak_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_OAK_TREE_NO_MOSS = register("fallen_oak_tree_no_moss");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_OAK_TREE = register("mossy_fallen_oak_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES = register("fancy_oak_bees");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_LEAF_LITTER = register("fancy_oak_bees_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OLD_FANCY_DYING_OAK_BEES_0004 = register("old_fancy_dying_oak_bees_0004");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OLD_FANCY_DYING_OAK_BEES_0004_LEAF_LITTER = register("old_fancy_dying_oak_bees_0004_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_OAK = register("snapped_oak_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DEAD_OAK = register("fancy_dead_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DEAD_OAK_LEAF_LITTER = register("fancy_dead_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_SEMI_DEAD_OAK = register("fancy_semi_dead_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_SEMI_DEAD_OAK_LEAF_LITTER = register("fancy_semi_dead_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_FANCY_DEAD_OAK = register("small_fancy_dead_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_FANCY_DEAD_OAK_LEAF_LITTER = register("small_fancy_dead_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_FANCY_SEMI_DEAD_OAK = register("small_fancy_semi_dead_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_FANCY_SEMI_DEAD_OAK_LEAF_LITTER = register("small_fancy_semi_dead_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK = register("dead_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK_LEAF_LITTER = register("dead_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK_BRANCHES = register("dead_oak_branches");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK_BRANCHES_LEAF_LITTER = register("dead_oak_branches_leaf_litter");
	//DARK OAK
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DARK_OAK = register("dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_DARK_OAK = register("dying_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_DARK_OAK_LEAF_LITTER = register("dying_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_DARK_OAK = register("tall_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_DARK_OAK_LEAF_LITTER = register("tall_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_TALL_DARK_OAK = register("fancy_tall_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_TALL_DARK_OAK_LEAF_LITTER = register("fancy_tall_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_TALL_DARK_OAK = register("dying_tall_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_TALL_DARK_OAK_LEAF_LITTER = register("dying_tall_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_FANCY_TALL_DARK_OAK = register("dying_fancy_tall_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_FANCY_TALL_DARK_OAK_LEAF_LITTER = register("dying_fancy_tall_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_TALL_DARK_OAK = register("cobweb_tall_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_TALL_DARK_OAK_LEAF_LITTER = register("cobweb_tall_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_FANCY_TALL_DARK_OAK = register("cobweb_fancy_tall_dark_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_FANCY_TALL_DARK_OAK_LEAF_LITTER = register("cobweb_fancy_tall_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_FALLEN_DARK_OAK = register("large_fallen_dark_oak_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_DARK_OAK = register("large_snapped_dark_oak_tree");
	//PALE OAK
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> PALE_OAK = register("pale_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> PALE_OAK_BONEMEAL = register("pale_oak_bonemeal");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> PALE_OAK_CREAKING = register("pale_oak_creaking");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_PALE_OAK = register("tall_pale_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_PALE_OAK_BONEMEAL = register("tall_pale_oak_bonemeal");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_PALE_OAK_CREAKING = register("tall_pale_oak_creaking");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_TALL_PALE_OAK = register("fancy_tall_pale_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_TALL_PALE_OAK_BONEMEAL = register("fancy_tall_pale_oak_bonemeal");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_TALL_PALE_OAK_CREAKING = register("fancy_tall_pale_oak_creaking");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_TALL_PALE_OAK = register("cobweb_tall_pale_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_TALL_PALE_OAK_CREAKING = register("cobweb_tall_pale_oak_creaking");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_FANCY_PALE_OAK = register("cobweb_fancy_pale_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_FANCY_PALE_OAK_CREAKING = register("cobweb_fancy_pale_oak_creaking");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_FALLEN_PALE_OAK = register("large_fallen_pale_oak_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_PALE_OAK = register("large_snapped_pale_oak_tree");
	public static final FrozenLibConfiguredFeature<HugeMushroomFeatureConfiguration, ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> HUGE_PALE_MUSHROOM = WWFeatureUtils.register("huge_pale_mushroom");
	//SWAMP TREE
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> WILLOW = register("willow");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> WILLOW_TALL = register("willow_tall");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> WILLOW_TALLER = register("willow_taller");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SWAMP_OAK = register("swamp_oak");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_WILLOW_TREE = register("mossy_fallen_willow_tree");
	//SPRUCE
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SPRUCE = register("spruce");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SPRUCE_SHORT = register("spruce_short");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FUNGUS_PINE = register("fungus_pine");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_FUNGUS_PINE = register("dying_fungus_pine");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEGA_FUNGUS_SPRUCE = register("mega_fungus_spruce");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEGA_FUNGUS_PINE = register("mega_fungus_pine");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_MEGA_FUNGUS_PINE = register("dying_mega_fungus_pine");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_SPRUCE_TREE = register("fallen_spruce_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_SPRUCE_TREE = register("mossy_fallen_spruce_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CLEAN_FALLEN_SPRUCE_TREE = register("clean_fallen_spruce_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_SPRUCE = register("short_mega_spruce");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_FUNGUS_SPRUCE = register("short_mega_fungus_spruce");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_DYING_FUNGUS_SPRUCE = register("short_mega_dying_fungus_spruce");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_DYING_SPRUCE = register("short_mega_dying_spruce");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_SPRUCE = register("snapped_spruce_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_SPRUCE = register("large_snapped_spruce_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DECORATED_LARGE_FALLEN_SPRUCE_TREE = register("decorated_large_fallen_spruce_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CLEAN_LARGE_FALLEN_SPRUCE_TREE = register("clean_large_snapped_spruce_tree");
	//BAOBAB
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BAOBAB = register("baobab");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BAOBAB_TALL = register("baobab_tall");
	//CYPRESS
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CYPRESS = register("cypress");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_CYPRESS_TREE = register("fallen_cypress_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FUNGUS_CYPRESS = register("fungus_cypress");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_CYPRESS = register("short_cypress");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_FUNGUS_CYPRESS = register("short_fungus_cypress");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SWAMP_CYPRESS = register("swamp_cypress");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_CYPRESS = register("snapped_cypress_tree");
	//BUSHES
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_BUSH_COARSE = register("big_bush_coarse");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_BUSH = register("big_bush");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BUSH = register("bush");
	//PALM
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> PALM = register("palm");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_PALM = register("tall_palm");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_WINDMILL_PALM = register("small_windmill_palm");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_WINDMILL_PALM = register("tall_windmill_palm");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_PALM = register("fallen_palm");
	//JUNIPER
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> JUNIPER = register("juniper");
	//JUNGLE
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> JUNGLE_TREE = register("jungle_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> JUNGLE_TREE_NO_VINE = register("jungle_tree_no_vine");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEGA_JUNGLE_TREE = register("mega_jungle_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_JUNGLE_TREE = register("fallen_jungle_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_JUNGLE = register("snapped_jungle_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_FALLEN_JUNGLE_TREE = register("large_fallen_jungle_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_JUNGLE = register("large_snapped_jungle_tree");
	//ACACIA
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_ACACIA_TREE = register("fallen_acacia_tree");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_ACACIA = register("snapped_acacia_tree");
	//MANGROVE
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MANGROVE = register("mangrove");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_MANGROVE = register("tall_mangrove");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_MANGROVE_TREE = register("fallen_mangrove_tree");
	//CRIMSON
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_CRIMSON_FUNGI = register("fallen_crimson_fungi");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_CRIMSON_FUNGI = register("snapped_crimson_fungi");
	//WARPED
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_WARPED_FUNGI = register("fallen_warped_fungi");
	public static final FrozenLibConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_WARPED_FUNGI = register("snapped_warped_fungi");
	//DECORATOR

	private static final ShelfFungiTreeDecorator SHELF_FUNGUS_009 = new ShelfFungiTreeDecorator(0.09F, 0.25F, 0.3F);
	private static final ShelfFungiTreeDecorator SHELF_FUNGUS_0074 = new ShelfFungiTreeDecorator(0.074F, 0.25F, 0.15F);
	private static final ShelfFungiTreeDecorator SHELF_FUNGUS_0054 = new ShelfFungiTreeDecorator(0.054F, 0.25F, 0.15F);
	private static final ShelfFungiTreeDecorator SHELF_FUNGUS_003 = new ShelfFungiTreeDecorator(0.03F, 0.25F, 0.4F);
	private static final ShelfFungiTreeDecorator SHELF_FUNGUS_0074_ONLY_BROWN = new ShelfFungiTreeDecorator(0.074F, 0.25F, 0F);
	private static final ShelfFungiTreeDecorator SHELF_FUNGUS_00975_ONLY_RED = new ShelfFungiTreeDecorator(0.0975F, 0.25F, 1F);
	private static final PaleShelfFungiTreeDecorator PALE_SHELF_FUNGI_00875 = new PaleShelfFungiTreeDecorator(0.0875F, 0.25F);
	private static final NetherShelfFungiTreeDecorator NETHER_FUNGI_LEANING_CRIMSON = new NetherShelfFungiTreeDecorator(0.0875F, 0.25F, 0.1F);
	private static final NetherShelfFungiTreeDecorator NETHER_FUNGI_LEANING_WARPED = new NetherShelfFungiTreeDecorator(0.0875F, 0.25F, 0.9F);
	private static final HeightBasedVineTreeDecorator VINES_012_UNDER_76 = new HeightBasedVineTreeDecorator(0.12F, 76, 0.25F);
	private static final HeightBasedVineTreeDecorator VINES_012_UNDER_260 = new HeightBasedVineTreeDecorator(0.12F, 260, 0.25F);
	private static final HeightBasedVineTreeDecorator VINES_008_UNDER_82 = new HeightBasedVineTreeDecorator(0.08F, 82, 0.25F);
	private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_03 = new HeightBasedVineTreeDecorator(1F, 260, 0.3F);
	private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_05 = new HeightBasedVineTreeDecorator(1F, 260, 0.5F);
	private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_075 = new HeightBasedVineTreeDecorator(1F, 260, 0.75F);
	private static final HeightBasedVineTreeDecorator VINES_08_UNDER_260_075 = new HeightBasedVineTreeDecorator(0.8F, 260, 0.75F);
	public static final PaleMossDecorator PALE_MOSS_DECORATOR = new PaleMossDecorator(0.15F, 0.4F, 0.8F);
	public static final CreakingHeartDecorator CREAKING_HEARTS = new CreakingHeartDecorator(1F);
	private static final HeightBasedCobwebTreeDecorator COBWEB_1_UNDER_260_025 = new HeightBasedCobwebTreeDecorator(1F, 260, 0.17F);
	private static final MossCarpetTreeDecorator MOSS_CYPRESS = new MossCarpetTreeDecorator(0.6F, 0.24F, false);
	private static final MossCarpetTreeDecorator MOSS_SPRUCE_PALM = new MossCarpetTreeDecorator(0.5F, 0.2F, false);
	private static final MossCarpetTreeDecorator MOSS_BIRCH = new MossCarpetTreeDecorator(0.6F, 0.2F, false);
	private static final MossCarpetTreeDecorator MOSS_OAK = new MossCarpetTreeDecorator(0.4F, 0.2F, false);
	private static final MossCarpetTreeDecorator MOSS_JUNGLE_DARK_OAK = new MossCarpetTreeDecorator(0.6F, 0.35F, false);
	private static final MossCarpetTreeDecorator MOSS_PALE_OAK = new MossCarpetTreeDecorator(1F, 0.35F, true);
	private static final MossCarpetTreeDecorator MOSS_CHERRY = new MossCarpetTreeDecorator(0.47F, 0.28F, false);
	private static final MossCarpetTreeDecorator MOSS_MOSSY = new MossCarpetTreeDecorator(1F, 0.3F, false);
	private static final BeehiveDecorator BEES_0004 = new BeehiveDecorator(0.004F);
	private static final BeehiveDecorator BEES_001 = new BeehiveDecorator(0.01F);
	private static final BeehiveDecorator BEES_025 = new BeehiveDecorator(0.25F);
	private static final BeehiveDecorator BEES = new BeehiveDecorator(1.0F);
	private static final PollenTreeDecorator POLLEN_01 = new PollenTreeDecorator(0.1F, 0.025F, 3);
	private static final PollenTreeDecorator POLLEN_025 = new PollenTreeDecorator(0.25F, 0.025F, 5);
	private static final PollenTreeDecorator POLLEN = new PollenTreeDecorator(1F, 0.035F, 5);
	private static final PlaceOnGroundDecorator LEAF_LITTERS_A = new PlaceOnGroundDecorator(
		96, 4, 2, new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 3))
	);
	private static final PlaceOnGroundDecorator LEAF_LITTERS_B = new PlaceOnGroundDecorator(
		150, 2, 2, new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 4))
	);

	private WWTreeConfigured() {
		throw new UnsupportedOperationException("WilderTreeConfigured contains only static declarations.");
	}

	public static void registerTreeConfigured(BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		WWConstants.logWithModId("Registering WilderTreeConfigured for", true);

		// BIRCH

		BIRCH_TREE.makeAndSetHolder(Feature.TREE,
			birch().dirt(BlockStateProvider.simple(Blocks.DIRT)).decorators(
				List.of(SHELF_FUNGUS_009)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			birch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_009,
					POLLEN_01
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIRCH_BEES_025.makeAndSetHolder(Feature.TREE,
			birch().decorators(
				List.of(
					BEES_025,
					SHELF_FUNGUS_009,
					POLLEN_025
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_BIRCH.makeAndSetHolder(Feature.TREE,
			birch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_009
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SUPER_BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_009,
					POLLEN_01
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_SUPER_BIRCH.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_BIRCH_TREE.makeAndSetHolder(Feature.TREE,
			fallenBirch().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_BIRCH,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_BIRCH_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.BIRCH_LOG,
				WWBlocks.HOLLOWED_BIRCH_LOG,
				3,
				1,
				2,
				0.185F,
				UniformInt.of(1, 2),
				1F,
				0.7F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			shortBirch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_0074,
					POLLEN_01
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_BIRCH.makeAndSetHolder(Feature.TREE,
			shortBirch().decorators(
				List.of(SHELF_FUNGUS_0074)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_DYING_BIRCH.makeAndSetHolder(Feature.TREE,
			shortBirch().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MEDIUM_BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			mediumBirch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_0074,
					POLLEN_01
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MEDIUM_BIRCH_BEES_025.makeAndSetHolder(Feature.TREE,
			mediumBirch().decorators(
				List.of(
					BEES_025,
					SHELF_FUNGUS_0074,
					POLLEN_01
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MEDIUM_BIRCH.makeAndSetHolder(Feature.TREE,
			mediumBirch().decorators(
				List.of(SHELF_FUNGUS_0074)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MEDIUM_DYING_BIRCH.makeAndSetHolder(Feature.TREE,
			mediumBirch().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SUPER_BIRCH_BEES.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(
					BEES,
					POLLEN_025,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SUPER_BIRCH.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(SHELF_FUNGUS_009)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_BIRCH.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.BIRCH_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_BIRCH,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_BIRCH.makeAndSetHolder(Feature.TREE,
			deadBirch().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_MEDIUM_BIRCH.makeAndSetHolder(Feature.TREE,
			deadMediumBirch().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// CHERRY

		CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			cherry().decorators(
				List.of(SHELF_FUNGUS_00975_ONLY_RED)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			cherry().decorators(
				List.of(
					SHELF_FUNGUS_00975_ONLY_RED,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			tallCherry().decorators(
				List.of(SHELF_FUNGUS_00975_ONLY_RED)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_DYING_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			tallCherry().decorators(
				List.of(
					SHELF_FUNGUS_00975_ONLY_RED,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		CHERRY_BEES_025.makeAndSetHolder(Feature.TREE,
			cherry().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_00975_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_CHERRY_BEES_025.makeAndSetHolder(Feature.TREE,
			tallCherry().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_00975_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			fallenCherry().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_CHERRY,
					SHELF_FUNGUS_00975_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.CHERRY_LOG,
				WWBlocks.HOLLOWED_CHERRY_LOG,
				3,
				1,
				2,
				0.075F,
				UniformInt.of(1, 2),
				0.075F,
				0.5F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY,
					SHELF_FUNGUS_00975_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.CHERRY_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_CHERRY,
					SHELF_FUNGUS_00975_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// MAPLE

		YELLOW_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			yellowMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		ROUND_YELLOW_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			roundYellowMaple().decorators(List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_YELLOW_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			yellowMaple().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_YELLOW_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			tallYellowMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_YELLOW_DYING_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			tallYellowMaple().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		YELLOW_MAPLE_BEES_0004.makeAndSetHolder(Feature.TREE,
			yellowMaple().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_YELLOW_MAPLE_BEES_0004.makeAndSetHolder(Feature.TREE,
			tallYellowMaple().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_YELLOW_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			shortYellowMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIG_SHRUB_YELLOW_MAPLE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.MAPLE_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(WWBlocks.YELLOW_MAPLE_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		ORANGE_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			orangeMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		ROUND_ORANGE_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			roundOrangeMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_ORANGE_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			orangeMaple().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_ORANGE_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			tallOrangeMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_ORANGE_DYING_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			tallOrangeMaple().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		ORANGE_MAPLE_BEES_0004.makeAndSetHolder(Feature.TREE,
			orangeMaple().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_ORANGE_MAPLE_BEES_0004.makeAndSetHolder(Feature.TREE,
			tallOrangeMaple().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_ORANGE_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			shortOrangeMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIG_SHRUB_ORANGE_MAPLE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.MAPLE_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(WWBlocks.ORANGE_MAPLE_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		RED_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			redMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		ROUND_RED_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			roundRedMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_RED_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			redMaple().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_RED_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			tallRedMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_RED_DYING_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			tallRedMaple().decorators(
				List.of(
					SHELF_FUNGUS_0074,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		RED_MAPLE_BEES_0004.makeAndSetHolder(Feature.TREE,
			redMaple().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_RED_MAPLE_BEES_0004.makeAndSetHolder(Feature.TREE,
			tallRedMaple().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_RED_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			shortRedMaple().decorators(
				List.of(SHELF_FUNGUS_0074)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIG_SHRUB_RED_MAPLE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.MAPLE_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(WWBlocks.RED_MAPLE_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			fallenMaple().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_MAPLE_TREE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				WWBlocks.MAPLE_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// OAK

		OAK.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(SHELF_FUNGUS_003)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);


		OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		OAK_NO_FUNGI.makeAndSetHolder(Feature.TREE,
			oak().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_OAK.makeAndSetHolder(Feature.TREE,
			shortOak().decorators(
				List.of(SHELF_FUNGUS_003)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			shortOak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_0054,
					POLLEN_01
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		OAK_BEES_0004_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_0074,
					POLLEN_01,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		DYING_OAK.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					VINES_1_UNDER_260_03,
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		DYING_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					VINES_1_UNDER_260_03,
					SHELF_FUNGUS_0074,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		FANCY_OAK.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(SHELF_FUNGUS_0054)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_0074,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_0004,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_BEES_0004_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_0004,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					SHELF_FUNGUS_0074,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_0004_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_BEES_025.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_025,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_BEES_025_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_025,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_025.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_025_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_OAK_TREE.makeAndSetHolder(Feature.TREE,
			fallenOak().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_OAK
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_OAK_TREE_NO_MOSS.makeAndSetHolder(Feature.TREE,
			fallenOak().decorators(
				List.of(VINES_012_UNDER_260)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_OAK_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.OAK_LOG,
				WWBlocks.HOLLOWED_OAK_LOG,
				3,
				1,
				2,
				0.175F,
				UniformInt.of(1, 2),
				0.075F,
				0.4F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES,
					POLLEN_025,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES,
					POLLEN_025,
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		OLD_FANCY_DYING_OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new FancyTrunkPlacer(5, 12, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
			).ignoreVines().decorators(
				List.of(
					BEES_0004,
					VINES_1_UNDER_260_05,
					POLLEN_01
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		OLD_FANCY_DYING_OAK_BEES_0004_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new FancyTrunkPlacer(5, 12, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4
				),
				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
			).ignoreVines().decorators(
				List.of(
					BEES_0004,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_OAK.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.OAK_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_OAK,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			fancyDeadOak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DEAD_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyDeadOak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_SEMI_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			fancySemiDeadOak(blocks).decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_SEMI_DEAD_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancySemiDeadOak(blocks).decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_FANCY_SEMI_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			smallFancySemiDeadOak(blocks).decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_FANCY_SEMI_DEAD_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			smallFancySemiDeadOak(blocks).decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_FANCY_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			smallFancySemiDeadOak(blocks).decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_FANCY_DEAD_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			smallFancySemiDeadOak(blocks).decorators(
				List.of(
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_OAK.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightWithBranchesTrunkPlacer(
					6,
					2,
					1,
					new TrunkBranchPlacement.Builder()
						.branchChance(0.225F)
						.maxBranchCount(UniformInt.of(1, 2))
						.branchCutoffFromTop(UniformInt.of(0, 2))
						.branchLength(ConstantInt.of(1))
						.build()
				),
				BlockStateProvider.simple(Blocks.AIR),
				new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightWithBranchesTrunkPlacer(
					6,
					2,
					1,
					new TrunkBranchPlacement.Builder()
						.branchChance(0.225F)
						.maxBranchCount(UniformInt.of(1, 2))
						.branchCutoffFromTop(UniformInt.of(0, 2))
						.branchLength(ConstantInt.of(1))
						.build()
				),
				BlockStateProvider.simple(Blocks.AIR),
				new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_003,
					VINES_012_UNDER_260,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_OAK_BRANCHES.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightWithBranchesTrunkPlacer(
					7,
					2,
					1,
					new TrunkBranchPlacement.Builder()
						.branchChance(0.235F)
						.maxBranchCount(UniformInt.of(2, 3))
						.branchCutoffFromTop(UniformInt.of(0, 2))
						.branchLength(UniformInt.of(1, 2))
						.build()
				),
				BlockStateProvider.simple(Blocks.AIR),
				new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_0074,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_OAK_BRANCHES_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightWithBranchesTrunkPlacer(
					7,
					2,
					1,
					new TrunkBranchPlacement.Builder()
						.branchChance(0.235F)
						.maxBranchCount(UniformInt.of(2, 3))
						.branchCutoffFromTop(UniformInt.of(0, 2))
						.branchLength(UniformInt.of(1, 2))
						.build()
				),
				BlockStateProvider.simple(Blocks.AIR),
				new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_0074,
					VINES_012_UNDER_260,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// DARK OAK

		DARK_OAK.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
				new DarkOakTrunkPlacer(6, 2, 1),
				BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
				new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
			).decorators(
				List.of(SHELF_FUNGUS_0074)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_DARK_OAK.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
				new DarkOakTrunkPlacer(6, 2, 1),
				BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
				new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
			).decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_0074
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
				new DarkOakTrunkPlacer(6, 2, 1),
				BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
				new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
			).decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_0074,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(SHELF_FUNGUS_003)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(SHELF_FUNGUS_003)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_TALL_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_009
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_TALL_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_0074,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_FANCY_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_009
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_FANCY_TALL_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_0074,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		COBWEB_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					COBWEB_1_UNDER_260_025,
					SHELF_FUNGUS_003
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		COBWEB_TALL_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					COBWEB_1_UNDER_260_025,
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		COBWEB_FANCY_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					COBWEB_1_UNDER_260_025,
					SHELF_FUNGUS_003
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		COBWEB_FANCY_TALL_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					COBWEB_1_UNDER_260_025,
					SHELF_FUNGUS_003,
					LEAF_LITTERS_A,
					LEAF_LITTERS_B
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_FALLEN_DARK_OAK.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(Blocks.DARK_OAK_LOG, 4, 2, 1).decorators(
				List.of(
					VINES_1_UNDER_260_05,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_DARK_OAK.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.DARK_OAK_LOG,
				1,
				1,
				1,
				2
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_OAK,
					SHELF_FUNGUS_003
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// PALE OAK

		PALE_OAK.makeAndSetHolder(Feature.TREE,
			paleOak(true, false, false, true).build()
		);

		PALE_OAK_BONEMEAL.makeAndSetHolder(Feature.TREE,
			paleOak(false, false, false, false).build()
		);

		PALE_OAK_CREAKING.makeAndSetHolder(Feature.TREE,
			paleOak(true, true, false, true).build()
		);

		TALL_PALE_OAK.makeAndSetHolder(Feature.TREE,
			tallPaleOak(true, false, false, true).build()
		);

		TALL_PALE_OAK_BONEMEAL.makeAndSetHolder(Feature.TREE,
			tallPaleOak(false, false, false, false).build()
		);

		TALL_PALE_OAK_CREAKING.makeAndSetHolder(Feature.TREE,
			tallPaleOak(true, true, false, true).build()
		);

		FANCY_TALL_PALE_OAK.makeAndSetHolder(Feature.TREE,
			fancyPaleOak(true, false, false, true).build()
		);

		FANCY_TALL_PALE_OAK_BONEMEAL.makeAndSetHolder(Feature.TREE,
			fancyPaleOak(false, false, false, false).build()
		);

		FANCY_TALL_PALE_OAK_CREAKING.makeAndSetHolder(Feature.TREE,
			fancyPaleOak(true, true, false, true).build()
		);

		COBWEB_TALL_PALE_OAK.makeAndSetHolder(Feature.TREE,
			tallPaleOak(true, false, true, true).build()
		);

		COBWEB_TALL_PALE_OAK_CREAKING.makeAndSetHolder(Feature.TREE,
			tallPaleOak(true, true, true, true).build()
		);

		COBWEB_FANCY_PALE_OAK.makeAndSetHolder(Feature.TREE,
			fancyPaleOak(true, false, true, true).build()
		);

		COBWEB_FANCY_PALE_OAK_CREAKING.makeAndSetHolder(Feature.TREE,
			fancyPaleOak(true, true, true, true).build()
		);

		LARGE_FALLEN_PALE_OAK.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(Blocks.PALE_OAK_LOG, 4, 2, 1).decorators(
				List.of(
					MOSS_PALE_OAK,
					PALE_SHELF_FUNGI_00875
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_PALE_OAK.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.PALE_OAK_LOG,
				1,
				1,
				1,
				2
			).decorators(
				List.of(
					MOSS_PALE_OAK,
					PALE_SHELF_FUNGI_00875
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		HUGE_PALE_MUSHROOM.makeAndSetHolder(WWFeatures.HUGE_PALE_MUSHROOM_FEATURE,
			new HugeMushroomFeatureConfiguration(
				BlockStateProvider.simple(
					WWBlocks.PALE_MUSHROOM_BLOCK.defaultBlockState()
						.setValue(HugeMushroomBlock.DOWN, false)
				),
				BlockStateProvider.simple(
					Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)
				),
				2
			)
		);

		// SWAMP TREE

		WILLOW.makeAndSetHolder(Feature.TREE,
			willow(5, 2, 1, blocks).decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					SHELF_FUNGUS_009
				)
			).ignoreVines().build()
		);

		WILLOW_TALL.makeAndSetHolder(Feature.TREE,
			willow(7, 2, 2, blocks).decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					SHELF_FUNGUS_009
				)
			).ignoreVines().build()
		);

		WILLOW_TALLER.makeAndSetHolder(Feature.TREE,
			willow(9, 2, 2, blocks).decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					SHELF_FUNGUS_009
				)
			).ignoreVines().build()
		);

		SWAMP_OAK.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_WILLOW_TREE.makeAndSetHolder(Feature.TREE,
			fallenWillowTrunkBuilder(
				WWBlocks.WILLOW_LOG,
				WWBlocks.HOLLOWED_WILLOW_LOG,
				3,
				1,
				2,
				0.3F,
				UniformInt.of(1, 2),
				UniformInt.of(1, 3),
				1F,
				0.075F,
				0.75F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY,
					SHELF_FUNGUS_0074
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// SPRUCE

		SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new StraightTrunkPlacer(8, 4, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new SpruceFoliagePlacer(
					UniformInt.of(2, 3),
					UniformInt.of(0, 2),
					UniformInt.of(2, 3)
				),
				new TwoLayersFeatureSize(2, 0, 2)
			).decorators(List.of(SHELF_FUNGUS_0074_ONLY_BROWN)).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SPRUCE_SHORT.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new StraightTrunkPlacer(3, 1, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new SpruceFoliagePlacer(
					UniformInt.of(1, 2),
					UniformInt.of(0, 2),
					UniformInt.of(2, 3)
				),
				new TwoLayersFeatureSize(2, 0, 2)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FUNGUS_PINE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new StraightTrunkPlacer(6, 4, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new PineFoliagePlacer(
					ConstantInt.of(1),
					ConstantInt.of(1),
					UniformInt.of(3, 4)
				),
				new TwoLayersFeatureSize(2, 0, 2)
			).decorators(List.of(SHELF_FUNGUS_0074_ONLY_BROWN)).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_FUNGUS_PINE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new StraightTrunkPlacer(6, 4, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new PineFoliagePlacer(
					ConstantInt.of(1),
					ConstantInt.of(1),
					UniformInt.of(3, 4)
				),
				new TwoLayersFeatureSize(2, 0, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_1_UNDER_260_05
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MEGA_FUNGUS_SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(13, 2, 14),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(
					ConstantInt.of(0),
					ConstantInt.of(0),
					UniformInt.of(13, 17)
				),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(
					new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)),
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MEGA_FUNGUS_PINE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(13, 2, 14),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(
					ConstantInt.of(0),
					ConstantInt.of(0),
					UniformInt.of(3, 7)
				),
				new TwoLayersFeatureSize(1, 1, 2))
				.decorators(
					List.of(
						new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)),
						SHELF_FUNGUS_0074_ONLY_BROWN
					)
				).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_MEGA_FUNGUS_PINE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(13, 2, 14),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(
					new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)),
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_1_UNDER_260_075
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			fallenSpruce().decorators(
				List.of(
					VINES_1_UNDER_260_075,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.SPRUCE_LOG,
				WWBlocks.HOLLOWED_SPRUCE_LOG,
				5,
				1,
				2,
				0F,
				UniformInt.of(1, 2),
				0.075F,
				0.6F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		CLEAN_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			fallenSpruce().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_MEGA_SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(12, 2, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(SHELF_FUNGUS_0074_ONLY_BROWN)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_MEGA_FUNGUS_SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(12, 2, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_0074_ONLY_BROWN,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_MEGA_DYING_FUNGUS_SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(12, 2, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_0074_ONLY_BROWN,
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_1_UNDER_260_075
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_MEGA_DYING_SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(12, 2, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_1_UNDER_260_075
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_SPRUCE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.SPRUCE_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_SPRUCE.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.SPRUCE_LOG,
				2,
				2,
				1,
				2
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DECORATED_LARGE_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(
				Blocks.SPRUCE_LOG,
				5,
				2,
				3
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		CLEAN_LARGE_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(
				Blocks.SPRUCE_LOG,
				5,
				2,
				3
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// BAOBAB

		BAOBAB.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.BAOBAB_LOG),
				new BaobabTrunkPlacer(13, 3, 2, BlockStateProvider.simple(WWBlocks.STRIPPED_BAOBAB_LOG)),
				BlockStateProvider.simple(WWBlocks.BAOBAB_LEAVES),
				new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 2)
			).decorators(
				List.of(
					new AttachedToLeavesDecorator(
						0.065F,
						1,
						0,
						new RandomizedIntStateProvider(
							BlockStateProvider.simple(WWBlocks.BAOBAB_NUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
							BaobabNutBlock.AGE,
							UniformInt.of(0, 2)
						),
						4,
						List.of(
							Direction.DOWN
						)
					)
				)
			).ignoreVines().build()
		);

		BAOBAB_TALL.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.BAOBAB_LOG),
				new BaobabTrunkPlacer(16, 4, 2, BlockStateProvider.simple(WWBlocks.STRIPPED_BAOBAB_LOG)),
				BlockStateProvider.simple(WWBlocks.BAOBAB_LEAVES),
				new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 2)
			).decorators(
				List.of(
					new AttachedToLeavesDecorator(
						0.065F,
						1,
						0,
						new RandomizedIntStateProvider(
							BlockStateProvider.simple(
								WWBlocks.BAOBAB_NUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
							BaobabNutBlock.AGE,
							UniformInt.of(0, 2)
						),
						4,
						List.of(
							Direction.DOWN
						)
					)
				)
			).ignoreVines().build()
		);

		// CYPRESS

		CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(6, 2, 3),
				BlockStateProvider.simple(WWBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(VINES_012_UNDER_76)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_CYPRESS_TREE.makeAndSetHolder(Feature.TREE,
			fallenCypress().decorators(
				List.of(
					VINES_008_UNDER_82,
					MOSS_CYPRESS,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FUNGUS_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(8, 4, 3),
				BlockStateProvider.simple(WWBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_008_UNDER_82
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(3, 2, 3),
				BlockStateProvider.simple(WWBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(VINES_012_UNDER_76)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_FUNGUS_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(4, 3, 1),
				BlockStateProvider.simple(WWBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_008_UNDER_82
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SWAMP_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.CYPRESS_LOG),
				new UpwardsBranchingTrunkPlacer(
					15,
					5,
					2,
					UniformInt.of(4, 5),
					0.2F,
					UniformInt.of(1, 3),
					blocks.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
				),
				BlockStateProvider.simple(WWBlocks.CYPRESS_LEAVES),
				new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 14),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_0074_ONLY_BROWN,
					VINES_008_UNDER_82
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_CYPRESS.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				WWBlocks.CYPRESS_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_CYPRESS,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// SHRUBS

		BIG_BUSH_COARSE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.COARSE_DIRT)).build()
		);

		BIG_BUSH.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BUSH.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new SmallBushFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// PALM

		PALM.makeAndSetHolder(WWFeatures.PALM_TREE_FEATURE,
			palmBuilder(
				WWBlocks.PALM_LOG,
				WWBlocks.PALM_FRONDS,
				6, 2, 1,
				1, 2,
				1, 2
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_PALM.makeAndSetHolder(WWFeatures.PALM_TREE_FEATURE,
			palmBuilder(
				WWBlocks.PALM_LOG,
				WWBlocks.PALM_FRONDS,
				8, 3, 2,
				2, 2,
				1, 2
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_WINDMILL_PALM.makeAndSetHolder(WWFeatures.PALM_TREE_FEATURE,
			windmillPalmBuilder(
				WWBlocks.PALM_LOG,
				WWBlocks.PALM_FRONDS,
				10, 3, 3
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_WINDMILL_PALM.makeAndSetHolder(WWFeatures.PALM_TREE_FEATURE,
			windmillPalmBuilder(
				WWBlocks.PALM_LOG,
				WWBlocks.PALM_FRONDS,
				5, 1, 2
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_PALM.makeAndSetHolder(Feature.TREE,
			fallenPalm().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_SPRUCE_PALM
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// JUNIPER

		JUNIPER.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(WWBlocks.CYPRESS_LOG),
				new JuniperTrunkPlacer(
					2,
					1,
					1,
					UniformInt.of(1, 3),
					UniformInt.of(2, 4),
					UniformInt.of(-8, -5),
					UniformInt.of(-3, 2)
				),
				BlockStateProvider.simple(WWBlocks.CYPRESS_LEAVES),
				new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2), 32),
				new TwoLayersFeatureSize(1, 0, 2)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		//JUNGLE

		JUNGLE_TREE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.JUNGLE_LOG),
				new StraightTrunkPlacer(4, 8, 0),
				BlockStateProvider.simple(Blocks.JUNGLE_LEAVES),
				new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new CocoaDecorator(0.2F),
					TrunkVineDecorator.INSTANCE,
					new LeaveVineDecorator(0.25F),
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		JUNGLE_TREE_NO_VINE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.JUNGLE_LOG),
				new StraightTrunkPlacer(4, 8, 0),
				BlockStateProvider.simple(Blocks.JUNGLE_LEAVES),
				new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(List.of(SHELF_FUNGUS_0054)).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		MEGA_JUNGLE_TREE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.JUNGLE_LOG),
				new MegaJungleTrunkPlacer(10, 2, 19),
				BlockStateProvider.simple(Blocks.JUNGLE_LEAVES),
				new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
				new TwoLayersFeatureSize(1, 1, 2)
			).decorators(
				List.of(
					TrunkVineDecorator.INSTANCE,
					new LeaveVineDecorator(0.25F),
					SHELF_FUNGUS_0054
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		FALLEN_JUNGLE_TREE.makeAndSetHolder(Feature.TREE,
			fallenJungle().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_JUNGLE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.JUNGLE_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_FALLEN_JUNGLE_TREE.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(Blocks.JUNGLE_LOG, 5, 2, 4).decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_JUNGLE.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.JUNGLE_LOG,
				3,
				1,
				2,
				3
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_009
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		//ACACIA
		FALLEN_ACACIA_TREE.makeAndSetHolder(Feature.TREE,
			fallenAcacia().decorators(
				List.of(VINES_012_UNDER_260)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_ACACIA.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.ACACIA_LOG,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);


		//MANGROVE
		MANGROVE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.MANGROVE_LOG),
				new UpwardsBranchingTrunkPlacer(
					2,
					1,
					4,
					UniformInt.of(1, 4),
					0.5F,
					UniformInt.of(0, 1),
					blocks.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
				),
				BlockStateProvider.simple(Blocks.MANGROVE_LEAVES),
				new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70),
				Optional.of(
					new MangroveRootPlacer(
						UniformInt.of(1, 3),
						BlockStateProvider.simple(Blocks.MANGROVE_ROOTS),
						Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)),
						new MangroveRootPlacement(
							blocks.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH),
							HolderSet.direct(Block::builtInRegistryHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS),
							BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS),
							8,
							15,
							0.2F
						)
					)
				),
				new TwoLayersFeatureSize(2, 0, 2)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					new AttachedToLeavesDecorator(
						0.14F,
						1,
						0,
						new RandomizedIntStateProvider(
							BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)),
							MangrovePropaguleBlock.AGE,
							UniformInt.of(0, 4)
						),
						2,
						List.of(Direction.DOWN)
					),
					BEES_001,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)).ignoreVines().build()
		);

		TALL_MANGROVE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.MANGROVE_LOG),
				new UpwardsBranchingTrunkPlacer(
					4,
					1,
					9,
					UniformInt.of(1, 6),
					0.5F,
					UniformInt.of(0, 1),
					blocks.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
				),
				BlockStateProvider.simple(Blocks.MANGROVE_LEAVES),
				new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70),
				Optional.of(
					new MangroveRootPlacer(
						UniformInt.of(3, 7),
						BlockStateProvider.simple(Blocks.MANGROVE_ROOTS),
						Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)),
						new MangroveRootPlacement(
							blocks.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH),
							HolderSet.direct(Block::builtInRegistryHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS),
							BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS),
							8,
							15,
							0.2F
						)
					)
				),
				new TwoLayersFeatureSize(3, 0, 2)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					new AttachedToLeavesDecorator(
						0.14F,
						1,
						0,
						new RandomizedIntStateProvider(
							BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)),
							MangrovePropaguleBlock.AGE,
							UniformInt.of(0, 4)
						),
						2,
						List.of(Direction.DOWN)
					),
					BEES_001,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)).ignoreVines().build()
		);

		FALLEN_MANGROVE_TREE.makeAndSetHolder(Feature.TREE,
			fallenMangrove().decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_MOSSY,
					SHELF_FUNGUS_0074_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		//CRIMSON
		FALLEN_CRIMSON_FUNGI.makeAndSetHolder(Feature.TREE,
			fallenCrimson().decorators(
				List.of(NETHER_FUNGI_LEANING_CRIMSON)
			).dirt(BlockStateProvider.simple(Blocks.CRIMSON_NYLIUM)).build()
		);

		SNAPPED_CRIMSON_FUNGI.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.CRIMSON_STEM,
				2,
				1,
				1
			).decorators(
				List.of(NETHER_FUNGI_LEANING_CRIMSON)
			).dirt(BlockStateProvider.simple(Blocks.CRIMSON_NYLIUM)).build()
		);

		//WARPED
		FALLEN_WARPED_FUNGI.makeAndSetHolder(Feature.TREE,
			fallenWarped().decorators(
				List.of(NETHER_FUNGI_LEANING_WARPED)
			).dirt(BlockStateProvider.simple(Blocks.WARPED_NYLIUM)).build()
		);

		SNAPPED_WARPED_FUNGI.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.WARPED_STEM,
				2,
				1,
				1
			).decorators(
				List.of(NETHER_FUNGI_LEANING_WARPED)
			).dirt(BlockStateProvider.simple(Blocks.WARPED_NYLIUM)).build()
		);

	}

	@Contract("_, _, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder builder(
		Block log,
		Block leaves,
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		float branchChance,
		IntProvider maxBranchCount,
		IntProvider branchCutoffFromTop,
		IntProvider branchLength,
		int radius
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new StraightWithBranchesTrunkPlacer(
				baseHeight,
				firstRandomHeight,
				secondRandomHeight,
				new TrunkBranchPlacement.Builder()
					.branchChance(branchChance)
					.maxBranchCount(maxBranchCount)
					.branchCutoffFromTop(branchCutoffFromTop)
					.branchLength(branchLength)
					.build()
			),
			BlockStateProvider.simple(leaves),
			new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	@Contract("_, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder deadBuilder(
		Block log,
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		float branchChance,
		IntProvider maxBranches,
		IntProvider branchCutoffFromTop,
		IntProvider branchLength
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new StraightWithBranchesTrunkPlacer(
				baseHeight, firstRandomHeight,
				secondRandomHeight,
				new TrunkBranchPlacement.Builder()
					.branchChance(branchChance)
					.maxBranchCount(maxBranches)
					.branchCutoffFromTop(branchCutoffFromTop)
					.branchLength(branchLength)
					.build()
			),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	@Contract("_, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder fallenTrunkBuilder(
		Block log,
		Block hollowedLog,
		int baseHeight,
		int firstRHeight,
		int secondRHeight,
		float branchPlacementChance,
		IntProvider maxBranchCount,
		float hollowedChance,
		float stumpPlacementChance
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new FallenWithBranchesTrunkPlacer(
				baseHeight,
				firstRHeight,
				secondRHeight,
				0.8F,
				BlockStateProvider.simple(hollowedLog),
				hollowedChance,
				new TrunkBranchPlacement.Builder()
					.branchChance(branchPlacementChance)
					.maxBranchCount(maxBranchCount)
					.branchLength(ConstantInt.of(1))
					.build(),
				stumpPlacementChance
			),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder fallenWillowTrunkBuilder(
		Block log,
		Block hollowedLog,
		int baseHeight,
		int firstRHeight,
		int secondRHeight,
		float branchPlacementChance,
		IntProvider maxBranchCount,
		IntProvider branchLength,
		float offsetLastLogChance,
		float hollowedChance,
		float stumpPlacementChance
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new FallenWithBranchesTrunkPlacer(
				baseHeight,
				firstRHeight,
				secondRHeight,
				0.8F,
				BlockStateProvider.simple(hollowedLog),
				hollowedChance,
				new TrunkBranchPlacement.Builder()
					.branchChance(branchPlacementChance)
					.maxBranchCount(maxBranchCount)
					.branchLength(branchLength)
					.offsetLastLogChance(offsetLastLogChance)
					.build(),
				stumpPlacementChance
			),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder darkOakBuilder(
		Block log, Block leaves,
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		int radius
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
			BlockStateProvider.simple(leaves),
			new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
			new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		);
	}

	@Contract("_, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder fancyDarkOakBuilder(
		Block log,
		Block leaves,
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		float branchChance,
		IntProvider maxBranchCount,
		IntProvider branchLength,
		int radius
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new FancyDarkOakTrunkPlacer(
				baseHeight,
				firstRandomHeight,
				secondRandomHeight,
				new TrunkBranchPlacement.Builder()
					.branchChance(branchChance)
					.maxBranchCount(maxBranchCount)
					.branchLength(branchLength)
					.foliagePlacementChance(1F)
					.offsetLastLogChance(1F)
					.build()
			),
			BlockStateProvider.simple(leaves),
			new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
			new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		);
	}

	@Contract("_, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder palmBuilder(
		Block log,
		Block leaves,
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		int minRad,
		int maxRad,
		int minFrondLength,
		int maxFrondLength
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new PalmTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
			BlockStateProvider.simple(leaves),
			new PalmFoliagePlacer(UniformInt.of(minRad, maxRad), ConstantInt.of(0), BiasedToBottomInt.of(minFrondLength + 1, maxFrondLength + 1)),
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	@Contract("_, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder windmillPalmBuilder(
		Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new PalmTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
			BlockStateProvider.simple(leaves),
			new WindmillPalmFoliagePlacer(ConstantInt.of(2)),
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	@Contract("_, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder snappedTrunkBuilder(
		Block log, int baseHeight, int firstRHeight, int secondRHeight
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new SnappedTrunkPlacer(baseHeight, firstRHeight, secondRHeight),
			BlockStateProvider.simple(Blocks.AIR), NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	@Contract("_, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder largeSnappedTrunkBuilder(
		Block log, int baseHeight, int firstRHeight, int secondRHeight, int maxAdditionalHeight
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new LargeSnappedTrunkPlacer(baseHeight, firstRHeight, secondRHeight, UniformInt.of(0, maxAdditionalHeight)),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder birch() {
		return builder(
			Blocks.BIRCH_LOG,
			Blocks.BIRCH_LEAVES,
			8,
			5,
			4,
			0.15F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1),
			2
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder superBirch() {
		return builder(
			Blocks.BIRCH_LOG,
			Blocks.BIRCH_LEAVES,
			8,
			6,
			6,
			0.15F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1),
			2
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder shortBirch() {
		return builder(
			Blocks.BIRCH_LOG,
			Blocks.BIRCH_LEAVES,
			6,
			2,
			2,
			0.12F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1),
			2
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder mediumBirch() {
		return builder(
			Blocks.BIRCH_LOG,
			Blocks.BIRCH_LEAVES,
			8,
			2,
			2,
			0.12F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1),
			2
		).ignoreVines();
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder deadBirch() {
		return deadBuilder(
			Blocks.BIRCH_LOG,
			7,
			4,
			2,
			0.355F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1)
		).ignoreVines();
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder deadMediumBirch() {
		return deadBuilder(
			Blocks.BIRCH_LOG,
			6,
			2,
			2,
			0.355F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1)
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenBirch() {
		return fallenTrunkBuilder(
			Blocks.BIRCH_LOG,
			WWBlocks.HOLLOWED_BIRCH_LOG,
			3,
			1,
			2,
			0.15F,
			UniformInt.of(1, 2),
			0.075F,
			0.7F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenCherry() {
		return fallenTrunkBuilder(
			Blocks.CHERRY_LOG,
			WWBlocks.HOLLOWED_CHERRY_LOG,
			3,
			1,
			2,
			0.05F,
			UniformInt.of(1, 2),
			0.075F,
			0.5F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenMaple() {
		return fallenTrunkBuilder(
			WWBlocks.MAPLE_LOG,
			WWBlocks.HOLLOWED_MAPLE_LOG,
			3,
			2, 2,
			0F,
			ConstantInt.of(0),
			0.075F,
			0.7F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder oak() {
		return builder(
			Blocks.OAK_LOG,
			Blocks.OAK_LEAVES,
			6,
			2,
			1,
			0.1F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1),
			2
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder shortOak() {
		return builder(
			Blocks.OAK_LOG,
			Blocks.OAK_LEAVES,
			4,
			1,
			0,
			0.095F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 3),
			ConstantInt.of(1),
			2
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyOak() {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(Blocks.OAK_LOG),
			new FancyTrunkPlacer(5, 16, 0),
			BlockStateProvider.simple(Blocks.OAK_LEAVES),
			new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 4),
			new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyDeadOak() {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(Blocks.OAK_LOG),
			new FancyTrunkPlacer(5, 16, 0),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5))
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancySemiDeadOak(HolderGetter<Block> blocks) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(Blocks.OAK_LOG),
			new UpwardsBranchingTrunkPlacer(
				10,
				6,
				1,
				UniformInt.of(3, 5),
				0.3F,
				UniformInt.of(3, 5),
				blocks.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
			),
			BlockStateProvider.simple(Blocks.OAK_LEAVES),
			new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 4),
			new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5))
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder smallFancyDeadOak() {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(Blocks.OAK_LOG),
			new FancyTrunkPlacer(5, 8, 2),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5))
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder smallFancySemiDeadOak(HolderGetter<Block> blocks) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(Blocks.OAK_LOG),
			new UpwardsBranchingTrunkPlacer(
				7,
				3,
				1,
				UniformInt.of(2, 4),
				0.3F,
				UniformInt.of(2, 5),
				blocks.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
			),
			BlockStateProvider.simple(Blocks.OAK_LEAVES),
			new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2), 1),
			new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5))
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenOak() {
		return fallenTrunkBuilder(
			Blocks.OAK_LOG,
			WWBlocks.HOLLOWED_OAK_LOG,
			3,
			1,
			2,
			0.15F,
			UniformInt.of(1, 2),
			0.075F,
			0.4F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenCypress() {
		return fallenTrunkBuilder(
			WWBlocks.CYPRESS_LOG,
			WWBlocks.CYPRESS_LOG,
			3,
			2,
			2,
			0F,
			UniformInt.of(1, 2),
			0.125F,
			0.7F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallDarkOak() {
		return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 8, 3, 4, 1).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyTallDarkOak() {
		return fancyDarkOakBuilder(
			Blocks.DARK_OAK_LOG,
			Blocks.DARK_OAK_LEAVES,
			10,
			3,
			4,
			1F,
			UniformInt.of(1, 2),
			UniformInt.of(1, 4),
			1
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder cherry() {
		return cherryBuilder(
			Blocks.CHERRY_LOG,
			Blocks.CHERRY_LEAVES,
			7,
			1,
			2,
			UniformInt.of(2, 4),
			UniformInt.of(-4, -3),
			UniformInt.of(-1, 0)
		);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallCherry() {
		return cherryBuilder(
			Blocks.CHERRY_LOG,
			Blocks.CHERRY_LEAVES,
			9,
			3,
			2,
			UniformInt.of(3, 5),
			UniformInt.of(-6, -4),
			UniformInt.of(-2, 0)
		);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder cherryBuilder(
		Block log,
		Block leaves,
		int baseHeight,
		int randomHeight1,
		int randomHeight2,
		IntProvider branchLength,
		UniformInt branchStartOffsetFromTop,
		UniformInt branchEndOffsetFromTop
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new CherryTrunkPlacer(baseHeight, randomHeight1, randomHeight2, UniformInt.of(1, 3), branchLength, branchStartOffsetFromTop, branchEndOffsetFromTop),
			BlockStateProvider.simple(leaves),
			new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
			new TwoLayersFeatureSize(1, 0, 2)).ignoreVines();
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder paleOak(boolean paleMoss, boolean creaking, boolean cobweb, boolean leafLitter) {
		TreeConfiguration.TreeConfigurationBuilder builder = paleOakBuilder(Blocks.PALE_OAK_LOG, Blocks.PALE_OAK_LEAVES, 6, 2, 1);
		appendPaleOakDecorators(builder, paleMoss, creaking, cobweb, leafLitter);
		return builder;
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder tallPaleOak(boolean paleMoss, boolean creaking, boolean cobweb, boolean leafLitter) {
		TreeConfiguration.TreeConfigurationBuilder builder = paleOakBuilder(Blocks.PALE_OAK_LOG, Blocks.PALE_OAK_LEAVES, 8, 3, 4);
		appendPaleOakDecorators(builder, paleMoss, creaking, cobweb, leafLitter);
		return builder;
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder fancyPaleOak(boolean paleMoss, boolean creaking, boolean cobweb, boolean leafLitter) {
		TreeConfiguration.TreeConfigurationBuilder builder = fancyPaleOakBuilder(
			Blocks.PALE_OAK_LOG, Blocks.PALE_OAK_LEAVES, 8, 3, 4, 1F, UniformInt.of(1, 2), UniformInt.of(1, 4)
		);
		appendPaleOakDecorators(builder, paleMoss, creaking, cobweb, leafLitter);
		return builder;
	}

	private static void appendPaleOakDecorators(TreeConfiguration.TreeConfigurationBuilder builder, boolean paleMoss, boolean creaking, boolean cobweb, boolean leafLitter) {
		List<TreeDecorator> treeDecorators = new ArrayList<>();
		treeDecorators.add(PALE_SHELF_FUNGI_00875);
		if (paleMoss) treeDecorators.add(PALE_MOSS_DECORATOR);
		if (creaking) treeDecorators.add(CREAKING_HEARTS);
		if (cobweb) treeDecorators.add(COBWEB_1_UNDER_260_025);
		if (leafLitter) treeDecorators.add(LEAF_LITTERS_A);
		if (leafLitter) treeDecorators.add(LEAF_LITTERS_B);
		builder.decorators(ImmutableList.copyOf(treeDecorators));
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder paleOakBuilder(Block log, Block leaves, int baseHeight, int randomHeight1, int randomHeight2) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new DarkOakTrunkPlacer(baseHeight, randomHeight1, randomHeight2),
			BlockStateProvider.simple(leaves),
			new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
			new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		).decorators(ImmutableList.of(new PaleMossDecorator(0.15F, 0.4F, 0.8F))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyPaleOakBuilder(
		Block log, Block leaves, int baseHeight, int randomHeight1, int randomHeight2, float branchChance, IntProvider maxBranchCount, IntProvider branchLength
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new FancyDarkOakTrunkPlacer(
				baseHeight,
				randomHeight1,
				randomHeight2,
				new TrunkBranchPlacement.Builder()
					.branchChance(branchChance)
					.maxBranchCount(maxBranchCount)
					.branchLength(branchLength)
					.foliagePlacementChance(1F)
					.offsetLastLogChance(1F)
					.build()
			),
			BlockStateProvider.simple(leaves),
			new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
			new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		).decorators(ImmutableList.of(new PaleMossDecorator(0.15F, 0.4F, 0.8F))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder yellowMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.YELLOW_MAPLE_LEAVES, 10, 1, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallYellowMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.YELLOW_MAPLE_LEAVES, 14, 1, 1);
	}

	private static TreeConfiguration.TreeConfigurationBuilder shortYellowMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.YELLOW_MAPLE_LEAVES, 6, 1, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder roundYellowMaple() {
		return roundMapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.YELLOW_MAPLE_LEAVES, 7, 2, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder orangeMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.ORANGE_MAPLE_LEAVES, 10, 1, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallOrangeMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.ORANGE_MAPLE_LEAVES, 14, 1, 1);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder shortOrangeMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.ORANGE_MAPLE_LEAVES, 6, 1, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder roundOrangeMaple() {
		return roundMapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.ORANGE_MAPLE_LEAVES, 7, 2, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder redMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.RED_MAPLE_LEAVES, 10, 1, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallRedMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.RED_MAPLE_LEAVES, 6, 1, 1);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder shortRedMaple() {
		return mapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.RED_MAPLE_LEAVES, 6, 1, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder roundRedMaple() {
		return roundMapleBuilder(WWBlocks.MAPLE_LOG, WWBlocks.RED_MAPLE_LEAVES, 7, 2, 2);
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder mapleBuilder(Block log, Block leaves, int baseHeight, int randomHeight1, int randomHeight2) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new StraightTrunkPlacer(baseHeight, randomHeight1, randomHeight2),
			BlockStateProvider.simple(leaves),
			new MapleFoliagePlacer(UniformInt.of(3, 4), UniformInt.of(0, 2), UniformInt.of(baseHeight - 6, baseHeight - 3)),
			new TwoLayersFeatureSize(1, 0, 0)
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder roundMapleBuilder(Block log, Block leaves, int baseHeight, int randomHeight1, int randomHeight2) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new StraightTrunkPlacer(baseHeight, randomHeight1, randomHeight2),
			BlockStateProvider.simple(leaves),
			new RoundMapleFoliagePlacer(UniformInt.of(2, 4), UniformInt.of(0, 3), UniformInt.of(baseHeight - 6, baseHeight - 3)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenSpruce() {
		return fallenTrunkBuilder(
			Blocks.SPRUCE_LOG,
			WWBlocks.HOLLOWED_SPRUCE_LOG,
			5,
			1,
			2,
			0F,
			UniformInt.of(1, 2),
			0.075F,
			0.6F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenPalm() {
		return fallenTrunkBuilder(
			WWBlocks.PALM_LOG,
			WWBlocks.HOLLOWED_PALM_LOG,
			5,
			1,
			2,
			0F,
			UniformInt.of(1, 2),
			0.045F,
			0.6F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenAcacia() {
		return fallenTrunkBuilder(
			Blocks.ACACIA_LOG,
			WWBlocks.HOLLOWED_ACACIA_LOG,
			3,
			1,
			1,
			0F,
			ConstantInt.of(1),
			0.055F,
			0.4F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenJungle() {
		return fallenTrunkBuilder(
			Blocks.JUNGLE_LOG,
			WWBlocks.HOLLOWED_JUNGLE_LOG,
			4,
			2,
			1,
			0F,
			UniformInt.of(1, 2),
			0F,
			0.5F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenMangrove() {
		return fallenTrunkBuilder(
			Blocks.MANGROVE_LOG,
			WWBlocks.HOLLOWED_MANGROVE_LOG,
			4,
			2,
			1,
			0F,
			ConstantInt.of(1),
			0.1F,
			0.4F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenCrimson() {
		return fallenTrunkBuilder(
			Blocks.CRIMSON_STEM,
			WWBlocks.HOLLOWED_CRIMSON_STEM,
			4,
			2,
			1,
			0F,
			ConstantInt.of(1),
			0.1F,
			0.6F
		).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenWarped() {
		return fallenTrunkBuilder(
			Blocks.WARPED_STEM,
			WWBlocks.HOLLOWED_WARPED_STEM,
			4,
			2,
			1,
			0F,
			ConstantInt.of(1),
			0.1F,
			0.6F
		).ignoreVines();
	}

	@Contract("_, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder largeFallenBuilder(
		Block log, int baseHeight, int firstRHeight, int secondRHeight
	) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(log),
			new FallenLargeTrunkPlacer(baseHeight, firstRHeight, secondRHeight, 0.8F, 0.8F),
			BlockStateProvider.simple(Blocks.AIR),
			NoOpFoliagePlacer.INSTANCE,
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	@Contract("_, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder willow(int height, int randomHeight1, int randomHeight2, HolderGetter<Block> blocks) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(WWBlocks.WILLOW_LOG),
			new WillowTrunkPlacer(
				height,
				randomHeight1,
				randomHeight2,
				UniformInt.of(2, 5),
				0.35F,
				new TrunkBranchPlacement.Builder()
					.branchChance(0.5F)
					.branchLength(UniformInt.of(2, 3))
					.offsetLastLogChance(1F)
					.foliagePlacementChance(1F)
					.foliageRadiusShrink(1)
					.build()
			),
			BlockStateProvider.simple(WWBlocks.WILLOW_LEAVES),
			new WillowFoliagePlacer(
				ConstantInt.of(3),
				ConstantInt.of(1),
				2,
				0.5F,
				0.5F
			),
			Optional.of(
				new WillowRootPlacer(
					UniformInt.of(1, 1),
					BlockStateProvider.simple(WWBlocks.WILLOW_LOG),
					Optional.of(
						new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.45F)
					),
					new WillowRootPlacement(
						blocks.getOrThrow(WWBlockTags.WILLOW_ROOTS_CAN_GROW_THROUGH),
						3,
						5,
						0.2F
					)
				)
			),
			new TwoLayersFeatureSize(2, 0, 2)
		);
	}
}
