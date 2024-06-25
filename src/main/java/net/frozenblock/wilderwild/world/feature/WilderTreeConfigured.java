/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import static net.frozenblock.wilderwild.world.feature.WilderFeatureUtils.register;
import net.frozenblock.wilderwild.world.impl.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.impl.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.impl.treedecorators.HeightBasedCobwebTreeDecorator;
import net.frozenblock.wilderwild.world.impl.treedecorators.HeightBasedVineTreeDecorator;
import net.frozenblock.wilderwild.world.impl.treedecorators.MossCarpetTreeDecorator;
import net.frozenblock.wilderwild.world.impl.treedecorators.PollenTreeDecorator;
import net.frozenblock.wilderwild.world.impl.treedecorators.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.impl.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.FallenLargeTrunk;
import net.frozenblock.wilderwild.world.impl.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.impl.trunk.FancyDarkOakTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.LargeSnappedTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.SnappedTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.StraightTrunkWithBranches;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
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
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WilderTreeConfigured {
	//BIRCH
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIRCH_TREE = register("birch_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIRCH_BEES_0004 = register("birch_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIRCH_BEES_025 = register("birch_bees_025");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_BIRCH = register("dying_birch");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_BIRCH_BEES_0004 = register("short_birch_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SUPER_BIRCH_BEES_0004 = register("super_birch_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_SUPER_BIRCH = register("dying_super_birch");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_BIRCH_TREE = register("fallen_birch_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_BIRCH_TREE = register("mossy_fallen_birch_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_BIRCH = register("short_birch");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_DYING_BIRCH = register("short_dying_birch");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SUPER_BIRCH_BEES = register("super_birch_bees");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SUPER_BIRCH = register("super_birch");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_BIRCH = register("snapped_birch_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_BIRCH = register("dead_birch");
	//CHERRY
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE = register("cherry");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_CHERRY_TREE = register("dying_cherry");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_CHERRY_TREE = register("tall_cherry");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_DYING_CHERRY_TREE = register("tall_dying_cherry");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BEES_025 = register("cherry_bees_025");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_CHERRY_BEES_025 = register("tall_cherry_bees_025");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_CHERRY_TREE = register("mossy_fallen_cherry_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_CHERRY_TREE = register("fallen_cherry_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_CHERRY_TREE = register("snapped_cherry_tree");
	//OAK
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK = register("oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_OAK = register("short_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OAK_BEES_0004 = register("oak_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_OAK = register("dying_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK = register("fancy_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK = register("fancy_dying_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_BEES_0004 = register("fancy_dying_oak_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_0004 = register("fancy_oak_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DYING_OAK_BEES_025 = register("fancy_dying_oak_bees_025");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES_025 = register("fancy_oak_bees_025");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_OAK_TREE = register("fallen_oak_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_OAK_TREE_NO_MOSS = register("fallen_oak_tree_no_moss");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_OAK_TREE = register("mossy_fallen_oak_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_STRAIGHT_OAK_TREE = register("mossy_fallen_straight_oak_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_BEES = register("fancy_oak_bees");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> OLD_FANCY_DYING_OAK_BEES_0004 = register("old_fancy_dying_oak_bees_0004");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_OAK = register("snapped_oak_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_DEAD_OAK = register("fancy_dead_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_SEMI_DEAD_OAK = register("fancy_semi_dead_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_FANCY_DEAD_OAK = register("small_fancy_dead_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_FANCY_SEMI_DEAD_OAK = register("small_fancy_semi_dead_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK = register("dead_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK_BRANCHES = register("dead_oak_branches");
	//DARK OAK
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_DARK_OAK = register("dying_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_DARK_OAK = register("tall_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FANCY_TALL_DARK_OAK = register("fancy_tall_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_TALL_DARK_OAK = register("dying_tall_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_FANCY_TALL_DARK_OAK = register("dying_fancy_tall_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_TALL_DARK_OAK = register("cobweb_tall_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> COBWEB_FANCY_TALL_DARK_OAK = register("cobweb_fancy_tall_dark_oak");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_FALLEN_DARK_OAK = register("large_fallen_dark_oak_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_DARK_OAK = register("large_snapped_dark_oak_tree");

	//SWAMP TREE
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SWAMP_TREE = register("swamp_tree");
	//SPRUCE
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SPRUCE = register("spruce");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SPRUCE_SHORT = register("spruce_short");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FUNGUS_PINE = register("fungus_pine");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_FUNGUS_PINE = register("dying_fungus_pine");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEGA_FUNGUS_SPRUCE = register("mega_fungus_spruce");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MEGA_FUNGUS_PINE = register("mega_fungus_pine");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DYING_MEGA_FUNGUS_PINE = register("dying_mega_fungus_pine");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_SPRUCE_TREE = register("fallen_spruce_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> MOSSY_FALLEN_SPRUCE_TREE = register("mossy_fallen_spruce_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CLEAN_FALLEN_SPRUCE_TREE = register("clean_fallen_spruce_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_SPRUCE = register("short_mega_spruce");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_FUNGUS_SPRUCE = register("short_mega_fungus_spruce");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_DYING_FUNGUS_SPRUCE = register("short_mega_dying_fungus_spruce");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_MEGA_DYING_SPRUCE = register("short_mega_dying_spruce");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_SPRUCE = register("snapped_spruce_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_SPRUCE = register("large_snapped_spruce_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> DECORATED_LARGE_FALLEN_SPRUCE_TREE = register("decorated_large_fallen_spruce_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CLEAN_LARGE_FALLEN_SPRUCE_TREE = register("clean_large_snapped_spruce_tree");
	//BAOBAB
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BAOBAB = register("baobab");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BAOBAB_TALL = register("baobab_tall");
	//CYPRESS
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> CYPRESS = register("cypress");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_CYPRESS_TREE = register("fallen_cypress_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FUNGUS_CYPRESS = register("fungus_cypress");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_CYPRESS = register("short_cypress");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHORT_FUNGUS_CYPRESS = register("short_fungus_cypress");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SWAMP_CYPRESS = register("swamp_cypress");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_CYPRESS = register("snapped_cypress_tree");
	//SHRUBS
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_SHRUB_COARSE = register("big_shrub_coarse");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> BIG_SHRUB = register("big_shrub");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SHRUB = register("shrub");
	//PALM
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> PALM = register("palm");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_PALM = register("tall_palm");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SMALL_WINE_PALM = register("small_wine_palm");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> TALL_WINE_PALM = register("tall_wine_palm");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_PALM = register("fallen_palm");
	//JUNIPER
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> JUNIPER = register("juniper");
	//JUNGLE
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_JUNGLE_TREE = register("fallen_jungle_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_JUNGLE = register("snapped_jungle_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_FALLEN_JUNGLE_TREE = register("large_fallen_jungle_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> LARGE_SNAPPED_JUNGLE = register("large_snapped_jungle_tree");
	//ACACIA
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_ACACIA_TREE = register("fallen_acacia_tree");
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> SNAPPED_ACACIA = register("snapped_acacia_tree");
	//MANGROVE
	public static final FrozenConfiguredFeature<TreeConfiguration, ConfiguredFeature<TreeConfiguration, ?>> FALLEN_MANGROVE_TREE = register("fallen_mangrove_tree");
	private static final ShelfFungusTreeDecorator SHELF_FUNGUS_007 = new ShelfFungusTreeDecorator(0.074F, 0.25F, 0.3F);
	private static final ShelfFungusTreeDecorator SHELF_FUNGUS_006 = new ShelfFungusTreeDecorator(0.064F, 0.25F, 0.15F);
	private static final ShelfFungusTreeDecorator SHELF_FUNGUS_002 = new ShelfFungusTreeDecorator(0.02F, 0.25F, 0.4F);
	private static final ShelfFungusTreeDecorator SHELF_FUNGUS_006_ONLY_BROWN = new ShelfFungusTreeDecorator(0.064F, 0.25F, 0.0F);
	private static final ShelfFungusTreeDecorator SHELF_FUNGUS_00875_ONLY_RED = new ShelfFungusTreeDecorator(0.0875F, 0.25F, 1.0F);
	private static final HeightBasedVineTreeDecorator VINES_012_UNDER_76 = new HeightBasedVineTreeDecorator(0.12F, 76, 0.25F);
	private static final HeightBasedVineTreeDecorator VINES_012_UNDER_260 = new HeightBasedVineTreeDecorator(0.12F, 260, 0.25F);
	private static final HeightBasedVineTreeDecorator VINES_008_UNDER_82 = new HeightBasedVineTreeDecorator(0.08F, 82, 0.25F);
	private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_03 = new HeightBasedVineTreeDecorator(1F, 260, 0.3F);
	private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_05 = new HeightBasedVineTreeDecorator(1F, 260, 0.5F);
	private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_075 = new HeightBasedVineTreeDecorator(1F, 260, 0.75F);
	private static final HeightBasedVineTreeDecorator VINES_08_UNDER_260_075 = new HeightBasedVineTreeDecorator(0.8F, 260, 0.75F);
	private static final HeightBasedCobwebTreeDecorator COBWEB_1_UNDER_260_025 = new HeightBasedCobwebTreeDecorator(1F, 260, 0.17F);
	private static final MossCarpetTreeDecorator MOSS_CYPRESS = new MossCarpetTreeDecorator(0.6F, 0.24F);
	private static final MossCarpetTreeDecorator MOSS_SPRUCE_PALM = new MossCarpetTreeDecorator(0.5F, 0.2F);
	private static final MossCarpetTreeDecorator MOSS_BIRCH = new MossCarpetTreeDecorator(0.6F, 0.2F);
	private static final MossCarpetTreeDecorator MOSS_OAK = new MossCarpetTreeDecorator(0.4F, 0.2F);
	private static final MossCarpetTreeDecorator MOSS_JUNGLE_DARK_OAK = new MossCarpetTreeDecorator(0.6F, 0.35F);
	private static final MossCarpetTreeDecorator MOSS_CHERRY = new MossCarpetTreeDecorator(0.47F, 0.28F);
	private static final MossCarpetTreeDecorator MOSS_MOSSY = new MossCarpetTreeDecorator(1F, 0.3F);
	private static final BeehiveDecorator BEES_0004 = new BeehiveDecorator(0.004F);
	private static final BeehiveDecorator BEES_025 = new BeehiveDecorator(0.25F);
	private static final BeehiveDecorator BEES = new BeehiveDecorator(1.0F);
	private static final PollenTreeDecorator POLLEN_01 = new PollenTreeDecorator(0.1F, 0.025F, 3);
	private static final PollenTreeDecorator POLLEN_025 = new PollenTreeDecorator(0.25F, 0.025F, 5);
	private static final PollenTreeDecorator POLLEN = new PollenTreeDecorator(1.0F, 0.035F, 5);

	private WilderTreeConfigured() {
		throw new UnsupportedOperationException("WilderTreeConfigured contains only static declarations.");
	}

	public static void registerTreeConfigured() {

		WilderSharedConstants.logWithModId("Registering WilderTreeConfigured for", true);

		// BIRCH

		BIRCH_TREE.makeAndSetHolder(Feature.TREE,
			birch().dirt(BlockStateProvider.simple(Blocks.DIRT)).decorators(
				List.of(
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			birch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_007,
					POLLEN_01
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		BIRCH_BEES_025.makeAndSetHolder(Feature.TREE,
			birch().decorators(
				List.of(
					BEES_025,
					SHELF_FUNGUS_007,
					POLLEN_025
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_BIRCH.makeAndSetHolder(Feature.TREE,
			birch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_007
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			shortBirch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_006,
					POLLEN_01)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SUPER_BIRCH_BEES_0004.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_007,
					POLLEN_01)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_SUPER_BIRCH.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_BIRCH_TREE.makeAndSetHolder(Feature.TREE,
			fallenBirch().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_BIRCH
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_BIRCH_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.BIRCH_LOG,
				RegisterBlocks.HOLLOWED_BIRCH_LOG,
				Blocks.BIRCH_LEAVES,
				3,
				1,
				2,
				0.185F,
				UniformInt.of(1, 2),
				1
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY
				)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_BIRCH.makeAndSetHolder(Feature.TREE,
			shortBirch().decorators(
				List.of(
					SHELF_FUNGUS_006
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_DYING_BIRCH.makeAndSetHolder(Feature.TREE,
			shortBirch().decorators(
				List.of(
					SHELF_FUNGUS_006,
					VINES_1_UNDER_260_03
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SUPER_BIRCH_BEES.makeAndSetHolder(Feature.TREE,
			superBirch().decorators(
				List.of(
					BEES,
					POLLEN_025
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SUPER_BIRCH.makeAndSetHolder(Feature.TREE,
			superBirch().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_BIRCH.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.BIRCH_LOG,
				Blocks.BIRCH_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_BIRCH,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_BIRCH.makeAndSetHolder(Feature.TREE,
			deadbirch().decorators(
				List.of(
					SHELF_FUNGUS_006,
					VINES_1_UNDER_260_03
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// CHERRY

		CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			cherry().decorators(
				List.of(
					SHELF_FUNGUS_00875_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			cherry().decorators(
				List.of(
					SHELF_FUNGUS_00875_ONLY_RED,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			tallCherry().decorators(
				List.of(
					SHELF_FUNGUS_00875_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_DYING_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			tallCherry().decorators(
				List.of(
					SHELF_FUNGUS_00875_ONLY_RED,
					VINES_1_UNDER_260_03
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		CHERRY_BEES_025.makeAndSetHolder(Feature.TREE,
			cherry().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_00875_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_CHERRY_BEES_025.makeAndSetHolder(Feature.TREE,
			tallCherry().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_00875_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			fallenCherry().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_CHERRY
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.CHERRY_LOG,
				RegisterBlocks.HOLLOWED_CHERRY_LOG,
				Blocks.CHERRY_LEAVES,
				3,
				1,
				2,
				0.075F,
				UniformInt.of(1, 2),
				0.075F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_CHERRY_TREE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.BIRCH_LOG,
				Blocks.BIRCH_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_CHERRY,
					SHELF_FUNGUS_00875_ONLY_RED
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// OAK

		OAK.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_OAK.makeAndSetHolder(Feature.TREE,
			shortOak().decorators(
				List.of(
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					BEES_0004,
					SHELF_FUNGUS_006,
					POLLEN_01
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		DYING_OAK.makeAndSetHolder(Feature.TREE,
			oak().decorators(
				List.of(
					VINES_1_UNDER_260_03,
					SHELF_FUNGUS_006
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build()
		);

		FANCY_OAK.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_0004,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_0004,
					POLLEN_01,
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DYING_OAK_BEES_025.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_025,
					VINES_1_UNDER_260_05,
					POLLEN_01,
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES_025.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES_025,
					POLLEN_01,
					SHELF_FUNGUS_002
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
				List.of(
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_OAK_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.OAK_LOG,
				RegisterBlocks.HOLLOWED_OAK_LOG,
				Blocks.OAK_LEAVES,
				3,
				1,
				2,
				0.175F,
				UniformInt.of(1, 2),
				0.075F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_STRAIGHT_OAK_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.OAK_LOG,
				RegisterBlocks.HOLLOWED_OAK_LOG,
				Blocks.OAK_LEAVES,
				3,
				1,
				1,
				0.0F,
				UniformInt.of(1, 2),
				0.075F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_OAK_BEES.makeAndSetHolder(Feature.TREE,
			fancyOak().decorators(
				List.of(
					BEES,
					POLLEN_025,
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		OLD_FANCY_DYING_OAK_BEES_0004.makeAndSetHolder(Feature.TREE,
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
					POLLEN_01
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_OAK.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.OAK_LOG,
				Blocks.OAK_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_OAK,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			fancyDeadOak().decorators(
				List.of(
					SHELF_FUNGUS_002,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_SEMI_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			fancySemiDeadOak().decorators(
				List.of(
					SHELF_FUNGUS_002,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_FANCY_SEMI_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			smallFancySemiDeadOak().decorators(
				List.of(
					SHELF_FUNGUS_002,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_FANCY_DEAD_OAK.makeAndSetHolder(Feature.TREE,
			smallFancySemiDeadOak().decorators(
				List.of(
					SHELF_FUNGUS_002,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_OAK.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkWithBranches(
					6,
					2,
					1,
					0.225F,
					UniformInt.of(1, 2),
					UniformInt.of(0, 2),
					ConstantInt.of(1)
				),
				BlockStateProvider.simple(Blocks.AIR),
				new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_002,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DEAD_OAK_BRANCHES.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkWithBranches(
					7,
					2,
					1,
					0.235F,
					UniformInt.of(2, 3),
					UniformInt.of(0, 2),
					UniformInt.of(1, 2)
				),
				BlockStateProvider.simple(Blocks.AIR),
				new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_006,
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// DARK OAK

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
					SHELF_FUNGUS_006
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					SHELF_FUNGUS_002
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FANCY_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					SHELF_FUNGUS_002
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_007
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DYING_FANCY_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					VINES_1_UNDER_260_05,
					SHELF_FUNGUS_007
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		COBWEB_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			tallDarkOak().decorators(
				List.of(
					COBWEB_1_UNDER_260_025,
					SHELF_FUNGUS_002
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		COBWEB_FANCY_TALL_DARK_OAK.makeAndSetHolder(Feature.TREE,
			fancyTallDarkOak().decorators(
				List.of(
					COBWEB_1_UNDER_260_025,
					SHELF_FUNGUS_002
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_FALLEN_DARK_OAK.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 4, 2, 1).decorators(
				List.of(
					VINES_1_UNDER_260_05,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_DARK_OAK.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.DARK_OAK_LOG,
				Blocks.DARK_OAK_LEAVES,
				1,
				1,
				1,
				2
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_OAK,
					SHELF_FUNGUS_002
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// SWAMP TREE

		SWAMP_TREE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(5, 2, 1),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new BlobFoliagePlacer(
					ConstantInt.of(3),
					ConstantInt.of(0), 3
				),
				Optional.of(
					new MangroveRootPlacer(
						UniformInt.of(1, 1),
						BlockStateProvider.simple(Blocks.OAK_LOG),
						Optional.of(
							new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.45F)
						),
						new MangroveRootPlacement(
							BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH),
							HolderSet.direct(
								Block::builtInRegistryHolder,
								Blocks.MUD
							),
							BlockStateProvider.simple(Blocks.MUD),
							3,
							5,
							0.2F
						)
					)
				),
				new TwoLayersFeatureSize(2, 0, 2)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.125F),
					SHELF_FUNGUS_007
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.AIR)).build()
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
			).decorators(List.of(SHELF_FUNGUS_006_ONLY_BROWN))
				.ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
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
				new TwoLayersFeatureSize(2, 0, 2))
				.ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
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
				new TwoLayersFeatureSize(2, 0, 2))
				.decorators(
					List.of(
						SHELF_FUNGUS_006_ONLY_BROWN
					)
				).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
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
					SHELF_FUNGUS_006_ONLY_BROWN,
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
				new TwoLayersFeatureSize(1, 1, 2))
				.decorators(
					List.of(
						new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN
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
				.decorators(List.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN))
				.dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
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
					SHELF_FUNGUS_006_ONLY_BROWN,
					VINES_1_UNDER_260_075
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			fallenSpruce().decorators(
				List.of(
					VINES_1_UNDER_260_075,
					MOSS_SPRUCE_PALM
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		MOSSY_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			fallenTrunkBuilder(
				Blocks.SPRUCE_LOG,
				RegisterBlocks.HOLLOWED_SPRUCE_LOG,
				Blocks.SPRUCE_LEAVES,
				5,
				1,
				2,
				0.0F,
				UniformInt.of(1, 2),
				0.075F
			).ignoreVines().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_MOSSY
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
				List.of(
					SHELF_FUNGUS_006_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_MEGA_FUNGUS_SPRUCE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.SPRUCE_LOG),
				new GiantTrunkPlacer(12, 2, 2),
				BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
				new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)),
				new TwoLayersFeatureSize(1, 1, 2))
				.decorators(
					List.of(
						SHELF_FUNGUS_006_ONLY_BROWN,
						SHELF_FUNGUS_006_ONLY_BROWN
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
					SHELF_FUNGUS_006_ONLY_BROWN,
					SHELF_FUNGUS_006_ONLY_BROWN,
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
					SHELF_FUNGUS_006_ONLY_BROWN,
					VINES_1_UNDER_260_075
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_SPRUCE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.SPRUCE_LOG,
				Blocks.SPRUCE_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_006_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_SPRUCE.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.SPRUCE_LOG,
				Blocks.SPRUCE_LEAVES,
				2,
				2,
				1,
				2
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_006_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		DECORATED_LARGE_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(
				Blocks.SPRUCE_LOG,
				Blocks.SPRUCE_LEAVES,
				5,
				2,
				3
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_SPRUCE_PALM,
					SHELF_FUNGUS_006_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		CLEAN_LARGE_FALLEN_SPRUCE_TREE.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(
				Blocks.SPRUCE_LOG,
				Blocks.SPRUCE_LEAVES,
				5,
				2,
				3
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// BAOBAB

		BAOBAB.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG),
				new BaobabTrunkPlacer(13, 3, 2, BlockStateProvider.simple(RegisterBlocks.STRIPPED_BAOBAB_LOG)),
				BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES),
				new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 2)
			).decorators(
				List.of(
					new AttachedToLeavesDecorator(
						0.065F,
						1,
						0,
						new RandomizedIntStateProvider(
							BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
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
				BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG),
				new BaobabTrunkPlacer(16, 4, 2, BlockStateProvider.simple(RegisterBlocks.STRIPPED_BAOBAB_LOG)),
				BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES),
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
								RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
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
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(6, 2, 3),
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(
					VINES_012_UNDER_76
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FALLEN_CYPRESS_TREE.makeAndSetHolder(Feature.TREE,
			fallenCypress().decorators(
				List.of(
					VINES_008_UNDER_82,
					MOSS_CYPRESS
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		FUNGUS_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(8, 4, 3),
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_006_ONLY_BROWN,
					VINES_008_UNDER_82
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(3, 2, 3),
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(
					VINES_012_UNDER_76
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHORT_FUNGUS_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG),
				new StraightTrunkPlacer(4, 3, 1),
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES),
				new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)),
				new TwoLayersFeatureSize(2, 1, 2)
			).decorators(
				List.of(
					SHELF_FUNGUS_006_ONLY_BROWN,
					VINES_008_UNDER_82
				)
			).ignoreVines().dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SWAMP_CYPRESS.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG),
				new UpwardsBranchingTrunkPlacer(
					15,
					5,
					2,
					UniformInt.of(4, 5),
					0.2F,
					UniformInt.of(1, 3),
					BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
				),
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES),
				new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 14),
				new TwoLayersFeatureSize(1, 0, 1)
			).decorators(
				List.of(
					new LeaveVineDecorator(0.1F),
					SHELF_FUNGUS_006_ONLY_BROWN,
					VINES_008_UNDER_82
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_CYPRESS.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				RegisterBlocks.CYPRESS_LOG,
				RegisterBlocks.CYPRESS_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_CYPRESS,
					SHELF_FUNGUS_006_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// SHRUBS

		BIG_SHRUB_COARSE.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.COARSE_DIRT)).build()
		);

		BIG_SHRUB.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SHRUB.makeAndSetHolder(Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(Blocks.OAK_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.simple(Blocks.OAK_LEAVES),
				new BushFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 2),
				new TwoLayersFeatureSize(0, 0, 0)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		// PALM

		PALM.makeAndSetHolder(RegisterFeatures.PALM_TREE_FEATURE,
			palmBuilder(
				RegisterBlocks.PALM_LOG,
				RegisterBlocks.PALM_FRONDS,
				6, 2,
				1,
				1,
				3,
				4,
				9
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_PALM.makeAndSetHolder(RegisterFeatures.PALM_TREE_FEATURE,
			palmBuilder(
				RegisterBlocks.PALM_LOG,
				RegisterBlocks.PALM_FRONDS,
				8,
				3,
				2,
				1,
				3,
				5,
				10
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SMALL_WINE_PALM.makeAndSetHolder(RegisterFeatures.PALM_TREE_FEATURE,
			winePalmBuilder(
				RegisterBlocks.PALM_LOG,
				RegisterBlocks.PALM_FRONDS,
				5,
				1,
				2,
				2
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		TALL_WINE_PALM.makeAndSetHolder(RegisterFeatures.PALM_TREE_FEATURE,
			winePalmBuilder(
				RegisterBlocks.PALM_LOG,
				RegisterBlocks.PALM_FRONDS,
				10,
				3,
				3,
				2
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
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG),
				new JuniperTrunkPlacer(
					2,
					1,
					1,
					UniformInt.of(1, 3),
					UniformInt.of(2, 4),
					UniformInt.of(-8, -5),
					UniformInt.of(-3, 2)
				),
				BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES),
				new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2), 32),
				new TwoLayersFeatureSize(1, 0, 2)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		//JUNGLE
		FALLEN_JUNGLE_TREE.makeAndSetHolder(Feature.TREE,
			fallenJungle().decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_JUNGLE_DARK_OAK
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_JUNGLE.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.JUNGLE_LOG,
				Blocks.JUNGLE_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_FALLEN_JUNGLE_TREE.makeAndSetHolder(Feature.TREE,
			largeFallenBuilder(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES, 5, 2, 4).decorators(
				List.of(
					VINES_08_UNDER_260_075,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		LARGE_SNAPPED_JUNGLE.makeAndSetHolder(Feature.TREE,
			largeSnappedTrunkBuilder(
				Blocks.JUNGLE_LOG,
				Blocks.JUNGLE_LEAVES,
				3,
				1,
				2,
				3
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_JUNGLE_DARK_OAK,
					SHELF_FUNGUS_007
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		//ACACIA
		FALLEN_ACACIA_TREE.makeAndSetHolder(Feature.TREE,
			fallenAcacia().decorators(
				List.of(
					VINES_012_UNDER_260
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		SNAPPED_ACACIA.makeAndSetHolder(Feature.TREE,
			snappedTrunkBuilder(
				Blocks.ACACIA_LOG,
				Blocks.ACACIA_LEAVES,
				2,
				1,
				1
			).decorators(
				List.of(
					VINES_012_UNDER_260,
					SHELF_FUNGUS_006_ONLY_BROWN
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);

		//MANGROVE
		FALLEN_MANGROVE_TREE.makeAndSetHolder(Feature.TREE,
			fallenMangrove().decorators(
				List.of(
					VINES_012_UNDER_260,
					MOSS_MOSSY
				)
			).dirt(BlockStateProvider.simple(Blocks.DIRT)).build()
		);
	}

	@Contract("_, _, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkWithBranches(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, logHeightFromTop, extraBranchLength),
			BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder fallenTrunkBuilder(Block log, Block hollowedLog, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, float logChance, IntProvider maxLogs, float hollowedChance) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FallenTrunkWithLogs(baseHeight, firstRHeight, secondRHeight, BlockStateProvider.simple(hollowedLog), logChance, 0.8F, hollowedChance, maxLogs),
			BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder darkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
			BlockStateProvider.simple(leaves), new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
			new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()));
	}

	@Contract("_, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder fancyDarkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider extraBranchLength, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FancyDarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, extraBranchLength),
			BlockStateProvider.simple(leaves), new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
			new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()));
	}

	@Contract("_, _, _, _, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder palmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int minRad, int maxRad, int minFronds, int maxFronds) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new PalmTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
			BlockStateProvider.simple(leaves), new PalmFoliagePlacer(UniformInt.of(minRad, maxRad), ConstantInt.of(0), UniformInt.of(minFronds, maxFronds), BlockStateProvider.simple(RegisterBlocks.PALM_LOG)),
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder winePalmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
			BlockStateProvider.simple(leaves), new ShortPalmFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), BlockStateProvider.simple(RegisterBlocks.PALM_LOG)),
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder snappedTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new SnappedTrunkPlacer(baseHeight, firstRHeight, secondRHeight),
			BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
			new TwoLayersFeatureSize(1, 0, 1));
	}

	@Contract("_, _, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder largeSnappedTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, int maxAdditionalHeight) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new LargeSnappedTrunkPlacer(baseHeight, firstRHeight, secondRHeight, UniformInt.of(0, maxAdditionalHeight)),
			BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
			new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder birch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder superBirch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder shortBirch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 6, 2, 2, 0.12F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	private static TreeConfiguration.@NotNull TreeConfigurationBuilder deadbirch() {
		return builder(Blocks.BIRCH_LOG, Blocks.AIR, 7, 4, 2, 0.355F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenBirch() {
		return fallenTrunkBuilder(Blocks.BIRCH_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.15F, UniformInt.of(1, 2), 0.075F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenCherry() {
		return fallenTrunkBuilder(Blocks.CHERRY_LOG, RegisterBlocks.HOLLOWED_CHERRY_LOG, Blocks.CHERRY_LEAVES, 3, 1, 2, 0.05F, UniformInt.of(1, 2), 0.075F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder oak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder shortOak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 1, 0, 0.095F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 16, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyDeadOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 16, 0), BlockStateProvider.simple(Blocks.AIR), new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5)))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancySemiDeadOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new UpwardsBranchingTrunkPlacer(10, 6, 1, UniformInt.of(3, 5), 0.3F, UniformInt.of(3, 5), BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(Blocks.OAK_LEAVES), new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5)))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder smallFancyDeadOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 8, 2), BlockStateProvider.simple(Blocks.AIR), new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 1), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5)))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder smallFancySemiDeadOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new UpwardsBranchingTrunkPlacer(7, 3, 1, UniformInt.of(2, 4), 0.3F, UniformInt.of(2, 5), BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(Blocks.OAK_LEAVES), new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2), 1), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5)))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenOak() {
		return fallenTrunkBuilder(Blocks.OAK_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.15F, UniformInt.of(1, 2), 0.075F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenCypress() {
		return fallenTrunkBuilder(RegisterBlocks.CYPRESS_LOG, RegisterBlocks.CYPRESS_LOG, RegisterBlocks.CYPRESS_LEAVES, 3, 2, 2, 0.0F, UniformInt.of(1, 2), 0.125F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallDarkOak() {
		return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 8, 3, 4, 1).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fancyTallDarkOak() {
		return fancyDarkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 10, 3, 4, 1.0F, UniformInt.of(1, 2), UniformInt.of(1, 4), 1).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder cherry() {
		return cherryBuilder(Blocks.CHERRY_LOG, Blocks.CHERRY_LEAVES, 7, 1, 2, UniformInt.of(2, 4), UniformInt.of(-4, -3), UniformInt.of(-1, 0));
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder tallCherry() {
		return cherryBuilder(Blocks.CHERRY_LOG, Blocks.CHERRY_LEAVES, 9, 3, 2, UniformInt.of(3, 5), UniformInt.of(-6, -4), UniformInt.of(-2, 0));
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder cherryBuilder(Block log, Block leaves, int baseHeight, int randomHeight1, int randomHeight2, IntProvider branchLength, UniformInt branchStartOffsetFromTop, UniformInt branchEndOffsetFromTop) {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
			new CherryTrunkPlacer(baseHeight, randomHeight1, randomHeight2, UniformInt.of(1, 3), branchLength, branchStartOffsetFromTop, branchEndOffsetFromTop), BlockStateProvider.simple(leaves), new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
			new TwoLayersFeatureSize(1, 0, 2))).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenSpruce() {
		return fallenTrunkBuilder(Blocks.SPRUCE_LOG, RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, UniformInt.of(1, 2), 0.075F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenPalm() {
		return fallenTrunkBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.HOLLOWED_PALM_LOG, RegisterBlocks.PALM_FRONDS, 5, 1, 2, 0.0F, UniformInt.of(1, 2), 0.045F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenAcacia() {
		return fallenTrunkBuilder(Blocks.ACACIA_LOG, RegisterBlocks.HOLLOWED_ACACIA_LOG, Blocks.ACACIA_LEAVES, 3, 1, 1, 0.0F, ConstantInt.of(1), 0.055F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenJungle() {
		return fallenTrunkBuilder(Blocks.JUNGLE_LOG, RegisterBlocks.HOLLOWED_JUNGLE_LOG, Blocks.JUNGLE_LEAVES, 4, 2, 1, 0.0F, UniformInt.of(1, 2), 0F).ignoreVines();
	}

	@NotNull
	private static TreeConfiguration.TreeConfigurationBuilder fallenMangrove() {
		return fallenTrunkBuilder(Blocks.MANGROVE_LOG, RegisterBlocks.HOLLOWED_MANGROVE_LOG, Blocks.MANGROVE_LEAVES, 4, 2, 1, 0.0F, ConstantInt.of(1), 0.1F).ignoreVines();
	}

	@Contract("_, _, _, _, _ -> new")
	private static TreeConfiguration.@NotNull TreeConfigurationBuilder largeFallenBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FallenLargeTrunk(baseHeight, firstRHeight, secondRHeight, 0.8F),
			BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
			new TwoLayersFeatureSize(1, 0, 1));
	}
}
