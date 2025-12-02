/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.features.placed;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedTreeFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WWTreePlaced {
	public static final BlockPredicate SNOW_TREE_PREDICATE = BlockPredicate.matchesBlocks(
		Direction.DOWN.getUnitVec3i(),
		Blocks.SNOW_BLOCK,
		Blocks.POWDER_SNOW
	);
	public static final List<PlacementModifier> SNOW_TREE_FILTER_DECORATOR = List.of(
		EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
		BlockPredicateFilter.forPredicate(SNOW_TREE_PREDICATE)
	);

	//BIRCH
	public static final FrozenLibPlacedTreeFeature BIRCH_CHECKED = WWTreeConfigured.BIRCH_TREE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature BIRCH_BEES_0004 = WWTreeConfigured.BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature BIRCH_BEES_025 = WWTreeConfigured.BIRCH_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_BIRCH = WWTreeConfigured.DYING_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_BIRCH = WWTreeConfigured.SHORT_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_SHORT_BIRCH = WWTreeConfigured.DYING_SHORT_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_BIRCH_BEES_0004 = WWTreeConfigured.SHORT_BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEDIUM_BIRCH = WWTreeConfigured.MEDIUM_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_MEDIUM_BIRCH = WWTreeConfigured.DYING_MEDIUM_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEDIUM_BIRCH_BEES_0004 = WWTreeConfigured.MEDIUM_BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEDIUM_BIRCH_BEES_025 = WWTreeConfigured.MEDIUM_BIRCH_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_SUPER_BIRCH = WWTreeConfigured.DYING_SUPER_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SUPER_BIRCH_BEES_0004 = WWTreeConfigured.SUPER_BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SUPER_BIRCH_BEES = WWTreeConfigured.SUPER_BIRCH_BEES.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SUPER_BIRCH = WWTreeConfigured.SUPER_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_BIRCH = WWTreeConfigured.DEAD_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_MEDIUM_BIRCH = WWTreeConfigured.DEAD_MEDIUM_BIRCH.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_BIRCH_CHECKED = WWPlacementUtils.register("fallen_birch_checked");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_BIRCH_CHECKED = WWPlacementUtils.register("mossy_fallen_birch_checked");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_CHECKED = WWPlacementUtils.register("snapped_birch_checked");

	//CHERRY
	public static final FrozenLibPlacedFeature CHERRY_CHECKED = WWPlacementUtils.register("cherry_checked");
	public static final FrozenLibPlacedFeature DYING_CHERRY_CHECKED = WWPlacementUtils.register("dying_cherry_checked");
	public static final FrozenLibPlacedFeature TALL_CHERRY_CHECKED = WWPlacementUtils.register("tall_cherry_checked");
	public static final FrozenLibPlacedFeature TALL_DYING_CHERRY_CHECKED = WWPlacementUtils.register("tall_dying_cherry_checked");
	public static final FrozenLibPlacedFeature CHERRY_BEES_CHECKED = WWPlacementUtils.register("cherry_bees_checked");
	public static final FrozenLibPlacedFeature TALL_CHERRY_BEES_CHECKED = WWPlacementUtils.register("tall_cherry_bees_checked");
	public static final FrozenLibPlacedFeature FALLEN_CHERRY_CHECKED = WWPlacementUtils.register("fallen_cherry_checked");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_CHERRY_CHECKED = WWPlacementUtils.register("mossy_fallen_cherry_checked");
	public static final FrozenLibPlacedFeature SNAPPED_CHERRY_CHECKED = WWPlacementUtils.register("snapped_cherry_checked");

	//MAPLE
	public static final FrozenLibPlacedFeature YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("yellow_maple_checked");
	public static final FrozenLibPlacedFeature DYING_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("dying_yellow_maple_checked");
	public static final FrozenLibPlacedFeature TALL_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("tall_yellow_maple_checked");
	public static final FrozenLibPlacedFeature TALL_DYING_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("tall_dying_yellow_maple_checked");
	public static final FrozenLibPlacedFeature YELLOW_MAPLE_BEES_CHECKED = WWPlacementUtils.register("yellow_maple_bees_checked");
	public static final FrozenLibPlacedFeature TALL_YELLOW_MAPLE_BEES_CHECKED = WWPlacementUtils.register("tall_yellow_maple_bees_checked");
	public static final FrozenLibPlacedFeature SHORT_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("short_yellow_maple_checked");
	public static final FrozenLibPlacedFeature FULL_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("full_yellow_maple_checked");
	public static final FrozenLibPlacedFeature BIG_BUSH_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("big_bush_yellow_maple_checked");

	public static final FrozenLibPlacedFeature ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("orange_maple_checked");
	public static final FrozenLibPlacedFeature DYING_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("dying_orange_maple_checked");
	public static final FrozenLibPlacedFeature TALL_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("tall_orange_maple_checked");
	public static final FrozenLibPlacedFeature TALL_DYING_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("tall_dying_orange_maple_checked");
	public static final FrozenLibPlacedFeature ORANGE_MAPLE_BEES_CHECKED = WWPlacementUtils.register("orange_maple_bees_checked");
	public static final FrozenLibPlacedFeature TALL_ORANGE_MAPLE_BEES_CHECKED = WWPlacementUtils.register("tall_orange_maple_bees_checked");
	public static final FrozenLibPlacedFeature SHORT_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("short_orange_maple_checked");
	public static final FrozenLibPlacedFeature FULL_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("full_orange_maple_checked");
	public static final FrozenLibPlacedFeature BIG_BUSH_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("big_bush_orange_maple_checked");

	public static final FrozenLibPlacedFeature RED_MAPLE_CHECKED = WWPlacementUtils.register("red_maple_checked");
	public static final FrozenLibPlacedFeature DYING_RED_MAPLE_CHECKED = WWPlacementUtils.register("dying_red_maple_checked");
	public static final FrozenLibPlacedFeature TALL_RED_MAPLE_CHECKED = WWPlacementUtils.register("tall_red_maple_checked");
	public static final FrozenLibPlacedFeature TALL_DYING_RED_MAPLE_CHECKED = WWPlacementUtils.register("tall_dying_red_maple_checked");
	public static final FrozenLibPlacedFeature RED_MAPLE_BEES_CHECKED = WWPlacementUtils.register("red_maple_bees_checked");
	public static final FrozenLibPlacedFeature TALL_RED_MAPLE_BEES_CHECKED = WWPlacementUtils.register("tall_red_maple_bees_checked");
	public static final FrozenLibPlacedFeature SHORT_RED_MAPLE_CHECKED = WWPlacementUtils.register("short_red_maple_checked");
	public static final FrozenLibPlacedFeature FULL_RED_MAPLE_CHECKED = WWPlacementUtils.register("full_red_maple_checked");
	public static final FrozenLibPlacedFeature BIG_BUSH_RED_MAPLE_CHECKED = WWPlacementUtils.register("big_bush_red_maple_checked");

	public static final FrozenLibPlacedFeature FALLEN_MAPLE_CHECKED = WWPlacementUtils.register("fallen_maple_checked");
	public static final FrozenLibPlacedFeature SNAPPED_MAPLE_CHECKED = WWPlacementUtils.register("snapped_maple_checked");

	//OAK
	public static final FrozenLibPlacedTreeFeature OAK_CHECKED = WWTreeConfigured.OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature OAK_NO_FUNGI_CHECKED = WWTreeConfigured.OAK_NO_FUNGI.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_OAK_CHECKED = WWTreeConfigured.DYING_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature OAK_BEES_0004 = WWTreeConfigured.OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_OAK_CHECKED = WWTreeConfigured.SHORT_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_CHECKED = WWTreeConfigured.FANCY_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_DYING_OAK_CHECKED = WWTreeConfigured.FANCY_DYING_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_FANCY_OAK_BEES_0004 = WWTreeConfigured.FANCY_DYING_OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_BEES_0004 = WWTreeConfigured.FANCY_OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_DYING_OAK_BEES_025 = WWTreeConfigured.FANCY_DYING_OAK_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_BEES_025 = WWTreeConfigured.FANCY_OAK_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_BEES = WWTreeConfigured.FANCY_OAK_BEES.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature OLD_DYING_FANCY_OAK_BEES_0004 = WWTreeConfigured.OLD_FANCY_DYING_OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_DEAD_OAK_CHECKED = WWTreeConfigured.FANCY_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_SEMI_DEAD_OAK_CHECKED = WWTreeConfigured.FANCY_SEMI_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SMALL_FANCY_DEAD_OAK_CHECKED = WWTreeConfigured.SMALL_FANCY_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SMALL_FANCY_SEMI_DEAD_OAK_CHECKED = WWTreeConfigured.SMALL_FANCY_SEMI_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_OAK_CHECKED = WWTreeConfigured.DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_OAK_BRANCHES_CHECKED = WWTreeConfigured.DEAD_OAK_BRANCHES.toPlacedFeature();

	public static final FrozenLibPlacedFeature SNAPPED_OAK_CHECKED = WWPlacementUtils.register("snapped_oak_checked");
	public static final FrozenLibPlacedFeature FALLEN_OAK_CHECKED = WWPlacementUtils.register("fallen_oak_checked");
	public static final FrozenLibPlacedFeature FALLEN_OAK_NO_MOSS_CHECKED = WWPlacementUtils.register("fallen_oak_no_moss_checked");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_OAK_CHECKED = WWPlacementUtils.register("mossy_fallen_oak_checked");

	//DARK OAK
	public static final FrozenLibPlacedTreeFeature DARK_OAK_CHECKED = WWTreeConfigured.DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature TALL_DARK_OAK_CHECKED = WWTreeConfigured.TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_TALL_DARK_OAK_CHECKED = WWTreeConfigured.FANCY_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_TALL_DARK_OAK_CHECKED = WWTreeConfigured.DYING_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_FANCY_TALL_DARK_OAK_CHECKED = WWTreeConfigured.FANCY_DYING_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_DARK_OAK_CHECKED = WWTreeConfigured.DYING_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature COBWEB_TALL_DARK_OAK_CHECKED = WWTreeConfigured.COBWEB_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature COBWEB_FANCY_TALL_DARK_OAK_CHECKED = WWTreeConfigured.COBWEB_FANCY_TALL_DARK_OAK.toPlacedFeature();

	public static final FrozenLibPlacedFeature LARGE_FALLEN_DARK_OAK_CHECKED = WWPlacementUtils.register("large_fallen_dark_oak_checked");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_DARK_OAK_CHECKED = WWPlacementUtils.register("large_snapped_dark_oak_checked");

	//PALE OAK
	public static final FrozenLibPlacedFeature PALE_OAK_CHECKED = WWPlacementUtils.register("pale_oak_checked");
	public static final FrozenLibPlacedFeature PALE_OAK_CREAKING_CHECKED = WWPlacementUtils.register("pale_oak_creaking_checked");
	public static final FrozenLibPlacedFeature TALL_PALE_OAK_CHECKED = WWPlacementUtils.register("tall_pale_oak_checked");
	public static final FrozenLibPlacedFeature TALL_PALE_OAK_CREAKING_CHECKED = WWPlacementUtils.register("tall_pale_oak_creaking_checked");
	public static final FrozenLibPlacedFeature FANCY_TALL_PALE_OAK_CHECKED = WWPlacementUtils.register("fancy_tall_pale_oak_checked");
	public static final FrozenLibPlacedFeature FANCY_TALL_PALE_OAK_CREAKING_CHECKED = WWPlacementUtils.register("fancy_tall_pale_oak_creaking_checked");
	public static final FrozenLibPlacedFeature COBWEB_TALL_PALE_OAK_CHECKED = WWPlacementUtils.register("cobweb_tall_pale_oak_checked");
	public static final FrozenLibPlacedFeature COBWEB_TALL_PALE_OAK_CREAKING_CHECKED = WWPlacementUtils.register("cobweb_tall_pale_oak_creaking_checked");
	public static final FrozenLibPlacedFeature COBWEB_FANCY_PALE_OAK_CHECKED = WWPlacementUtils.register("cobweb_fancy_tall_pale_oak_checked");
	public static final FrozenLibPlacedFeature COBWEB_FANCY_PALE_OAK_CREAKING_CHECKED = WWPlacementUtils.register("cobweb_fancy_tall_pale_oak_creaking_checked");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_PALE_OAK_CHECKED = WWPlacementUtils.register("large_fallen_pale_oak_checked");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_PALE_OAK_CHECKED = WWPlacementUtils.register("large_snapped_pale_oak_checked");

	//SWAMP TREE
	public static final FrozenLibPlacedTreeFeature WILLOW_CHECKED = WWTreeConfigured.WILLOW.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature WILLOW_TALL_CHECKED = WWTreeConfigured.WILLOW_TALL.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature WILLOW_TALLER_CHECKED = WWTreeConfigured.WILLOW_TALLER.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SWAMP_OAK_CHECKED = WWTreeConfigured.SWAMP_OAK.toPlacedFeature();

	public static final FrozenLibPlacedFeature MOSSY_FALLEN_WILLOW_CHECKED = WWPlacementUtils.register("mossy_fallen_willow_checked");

	//SPRUCE
	public static final FrozenLibPlacedTreeFeature SPRUCE_CHECKED = WWTreeConfigured.SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedFeature SPRUCE_ON_SNOW = WWPlacementUtils.register("spruce_on_snow");
	public static final FrozenLibPlacedTreeFeature SPRUCE_SHORT_CHECKED = WWTreeConfigured.SPRUCE_SHORT.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FUNGUS_PINE_CHECKED = WWTreeConfigured.FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_FUNGUS_PINE_CHECKED = WWTreeConfigured.DYING_FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedFeature FUNGUS_PINE_ON_SNOW = WWPlacementUtils.register("fungus_pine_on_snow");
	public static final FrozenLibPlacedTreeFeature MEGA_FUNGUS_SPRUCE_CHECKED = WWTreeConfigured.MEGA_FUNGUS_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEGA_FUNGUS_PINE_CHECKED = WWTreeConfigured.MEGA_FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_MEGA_FUNGUS_PINE_CHECKED = WWTreeConfigured.DYING_MEGA_FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_SPRUCE_CHECKED = WWTreeConfigured.SHORT_MEGA_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_FUNGUS_SPRUCE_CHECKED = WWTreeConfigured.SHORT_MEGA_FUNGUS_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED = WWTreeConfigured.SHORT_MEGA_DYING_FUNGUS_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_DYING_SPRUCE_CHECKED = WWTreeConfigured.SHORT_MEGA_DYING_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedFeature SHORT_MEGA_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_spruce_on_snow");
	public static final FrozenLibPlacedFeature SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_fungus_spruce_on_snow");
	public static final FrozenLibPlacedFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_dying_fungus_spruce_on_snow");
	public static final FrozenLibPlacedFeature SHORT_MEGA_DYING_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_dying_spruce_on_snow");

	public static final FrozenLibPlacedFeature FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("fallen_spruce_checked");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("mossy_fallen_spruce_checked");
	public static final FrozenLibPlacedFeature CLEAN_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("clean_fallen_spruce_checked");
	public static final FrozenLibPlacedFeature DECORATED_LARGE_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("decorated_large_fallen_spruce_checked");
	public static final FrozenLibPlacedFeature CLEAN_LARGE_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("clean_large_fallen_spruce_checked");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_CHECKED = WWPlacementUtils.register("snapped_spruce_checked");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_ON_SNOW = WWPlacementUtils.register("snapped_spruces_on_snow");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_SPRUCE_CHECKED = WWPlacementUtils.register("large_snapped_spruce_checked");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED = WWPlacementUtils.register("large_snapped_spruces_on_snow");

	//BAOBAB
	public static final FrozenLibPlacedTreeFeature BAOBAB = WWTreeConfigured.BAOBAB.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature BAOBAB_TALL = WWTreeConfigured.BAOBAB_TALL.toPlacedFeature();

	//CYPRESS
	public static final FrozenLibPlacedFeature CYPRESS = WWPlacementUtils.register("cypress");
	public static final FrozenLibPlacedFeature FUNGUS_CYPRESS = WWPlacementUtils.register("fungus_cypress");
	public static final FrozenLibPlacedFeature SHORT_CYPRESS = WWPlacementUtils.register("short_cypress");
	public static final FrozenLibPlacedFeature SHORT_FUNGUS_CYPRESS = WWPlacementUtils.register("short_fungus_cypress");
	public static final FrozenLibPlacedFeature SWAMP_CYPRESS = WWPlacementUtils.register("swamp_cypress");
	public static final FrozenLibPlacedFeature FALLEN_CYPRESS_CHECKED = WWPlacementUtils.register("fallen_cypress_checked");
	public static final FrozenLibPlacedFeature SNAPPED_CYPRESS_CHECKED = WWPlacementUtils.register("snapped_cypress_checked");

	//TREE ON SAND
	public static final BlockPredicate SAND_GRASS_TREE_PREDICATE = BlockPredicate.matchesBlocks(
		Direction.DOWN.getUnitVec3i(),
		Blocks.RED_SAND,
		Blocks.SAND,
		Blocks.GRASS_BLOCK
	);
	public static final List<PlacementModifier> SAND_TREE_FILTER_DECORATOR = List.of(
		EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SANDSTONE)), 8),
		BlockPredicateFilter.forPredicate(SAND_GRASS_TREE_PREDICATE)
	);

	//SHRUB
	public static final FrozenLibPlacedFeature LARGE_BUSH_COARSE_CHECKED = WWPlacementUtils.register("large_bush_coarse_checked");
	public static final FrozenLibPlacedFeature LARGE_BUSH_COARSE_GRASS_CHECKED = WWPlacementUtils.register("large_bush_coarse_grass_checked");
	public static final FrozenLibPlacedFeature LARGE_BUSH_CHECKED = WWPlacementUtils.register("large_bush_checked");
	public static final FrozenLibPlacedFeature BIG_BUSH_CHECKED = WWPlacementUtils.register("big_bush_checked");

	//PALM
	public static final FrozenLibPlacedFeature PALM_CHECKED = WWPlacementUtils.register("palm_checked");
	public static final FrozenLibPlacedFeature TALL_PALM_CHECKED = WWPlacementUtils.register("tall_palm_checked");
	public static final FrozenLibPlacedFeature TALL_WINDMILL_PALM_CHECKED = WWPlacementUtils.register("tall_windmill_palm_checked");
	public static final FrozenLibPlacedFeature SMALL_WINDMILL_PALM_CHECKED = WWPlacementUtils.register("small_windmill_palm_checked");

	public static final FrozenLibPlacedFeature FALLEN_PALM_CHECKED = WWPlacementUtils.register("fallen_palm_checked");

	//JUNIPER
	public static final FrozenLibPlacedTreeFeature JUNIPER = WWTreeConfigured.JUNIPER.toPlacedFeature();

	//JUNGLE
	public static final FrozenLibPlacedTreeFeature JUNGLE_TREE_CHECKED = WWTreeConfigured.JUNGLE_TREE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEGA_JUNGLE_TREE_CHECKED = WWTreeConfigured.MEGA_JUNGLE_TREE.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_JUNGLE_CHECKED = WWPlacementUtils.register("fallen_jungle_checked");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_JUNGLE_CHECKED = WWPlacementUtils.register("large_fallen_jungle_checked");
	public static final FrozenLibPlacedFeature SNAPPED_JUNGLE_CHECKED = WWPlacementUtils.register("snapped_jungle_checked");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_JUNGLE_CHECKED = WWPlacementUtils.register("large_snapped_jungle_checked");

	//ACACIA
	public static final FrozenLibPlacedFeature ACACIA_CHECKED_LEAF_LITTER = WWPlacementUtils.register("acacia_checked_leaf_litter");
	public static final FrozenLibPlacedFeature FALLEN_ACACIA_CHECKED = WWPlacementUtils.register("fallen_acacia_checked");

	public static final FrozenLibPlacedFeature SNAPPED_ACACIA_CHECKED = WWPlacementUtils.register("snapped_acacia_checked");

	//MANGROVE
	public static final FrozenLibPlacedTreeFeature MANGROVE_CHECKED = WWTreeConfigured.MANGROVE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature TALL_MANGROVE_CHECKED = WWTreeConfigured.TALL_MANGROVE.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_MANGROVE_CHECKED = WWPlacementUtils.register("fallen_mangrove_checked");

	//CRIMSON
	public static final FrozenLibPlacedFeature FALLEN_CRIMSON_FUNGI = WWPlacementUtils.register("fallen_crimson_fungi");
	public static final FrozenLibPlacedFeature SNAPPED_CRIMSON_FUNGI = WWPlacementUtils.register("snapped_crimson_fungi");

	//WARPED
	public static final FrozenLibPlacedFeature FALLEN_WARPED_FUNGI = WWPlacementUtils.register("fallen_warped_fungi");
	public static final FrozenLibPlacedFeature SNAPPED_WARPED_FUNGI = WWPlacementUtils.register("snapped_warped_fungi");

	//TREE ON GRASS
	public static final FrozenLibPlacedFeature PALM_CHECKED_DIRT = WWPlacementUtils.register("palm_checked_dirt");
	public static final FrozenLibPlacedFeature PALM_CHECKED_DIRT_LEAF_LITTER = WWPlacementUtils.register("palm_checked_dirt_leaf_litter");
	public static final FrozenLibPlacedFeature TALL_PALM_CHECKED_DIRT = WWPlacementUtils.register("tall_palm_checked_dirt");
	public static final FrozenLibPlacedFeature TALL_PALM_CHECKED_DIRT_LEAF_LITTER = WWPlacementUtils.register("tall_palm_checked_dirt_leaf_litter");
	public static final FrozenLibPlacedFeature TALL_WINE_PALM_CHECKED_DIRT = WWPlacementUtils.register("tall_wine_palm_checked_dirt");
	public static final FrozenLibPlacedFeature TALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER = WWPlacementUtils.register("tall_wine_palm_checked_dirt_leaf_litter");
	public static final FrozenLibPlacedFeature SMALL_WINE_PALM_CHECKED_DIRT = WWPlacementUtils.register("small_wine_palm_checked_dirt");
	public static final FrozenLibPlacedFeature SMALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER = WWPlacementUtils.register("small_wine_palm_checked_dirt_leaf_litter");

	private WWTreePlaced() {
		throw new UnsupportedOperationException("WWTreePlaced contains only static declarations.");
	}

	public static void registerTreePlaced() {
		WWConstants.logWithModId("Registering WWTreePlaced for", true);

		BlockPredicateFilter fallenTreeFilter = BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.FALLEN_TREE_PLACEABLE));

		// BIRCH
		BlockPredicateFilter birchSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING);
		BIRCH_CHECKED.makeAndSetHolders(birchSaplingPlacement);
		BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		BIRCH_BEES_025.makeAndSetHolders(birchSaplingPlacement);
		DYING_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		SHORT_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DYING_SHORT_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		SHORT_BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		MEDIUM_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DYING_MEDIUM_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		MEDIUM_BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		MEDIUM_BIRCH_BEES_025.makeAndSetHolders(birchSaplingPlacement);
		DYING_SUPER_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		SUPER_BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		SUPER_BIRCH_BEES.makeAndSetHolders(birchSaplingPlacement);
		SUPER_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DEAD_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DEAD_MEDIUM_BIRCH.makeAndSetHolders(birchSaplingPlacement);

		FALLEN_BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_BIRCH_TREE, fallenTreeFilter);
		MOSSY_FALLEN_BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_BIRCH_TREE, fallenTreeFilter);
		SNAPPED_BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_BIRCH, birchSaplingPlacement);

		// CHERRY
		BlockPredicateFilter cherrySaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING);
		CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.CHERRY_TREE, cherrySaplingPlacement);
		DYING_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_CHERRY_TREE, cherrySaplingPlacement);
		CHERRY_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.CHERRY_BEES_025, cherrySaplingPlacement);
		TALL_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_CHERRY_TREE, cherrySaplingPlacement);
		TALL_DYING_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_DYING_CHERRY_TREE, cherrySaplingPlacement);
		TALL_CHERRY_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_CHERRY_BEES_025, cherrySaplingPlacement);

		FALLEN_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_CHERRY_TREE, fallenTreeFilter);
		MOSSY_FALLEN_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_CHERRY_TREE, fallenTreeFilter);
		SNAPPED_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_CHERRY_TREE, cherrySaplingPlacement);

		// MAPLE
		final BlockPredicateFilter yellowMapleSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.YELLOW_MAPLE_SAPLING);
		YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.YELLOW_MAPLE_TREE.getHolder(), yellowMapleSaplingPlacement);
		DYING_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_YELLOW_MAPLE_TREE.getHolder(), yellowMapleSaplingPlacement);
		YELLOW_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.YELLOW_MAPLE_BEES_0004.getHolder(), yellowMapleSaplingPlacement);
		TALL_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_YELLOW_MAPLE_TREE.getHolder(), yellowMapleSaplingPlacement);
		TALL_DYING_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_YELLOW_DYING_MAPLE_TREE.getHolder(), yellowMapleSaplingPlacement);
		TALL_YELLOW_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_YELLOW_MAPLE_BEES_0004.getHolder(), yellowMapleSaplingPlacement);
		SHORT_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_YELLOW_MAPLE_TREE.getHolder(), yellowMapleSaplingPlacement);
		FULL_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FULL_YELLOW_MAPLE_TREE.getHolder(), yellowMapleSaplingPlacement);
		BIG_BUSH_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_SHRUB_YELLOW_MAPLE.getHolder(), yellowMapleSaplingPlacement);

		final BlockPredicateFilter orangeMapleSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.ORANGE_MAPLE_SAPLING);
		ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.ORANGE_MAPLE_TREE.getHolder(), orangeMapleSaplingPlacement);
		DYING_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_ORANGE_MAPLE_TREE.getHolder(), orangeMapleSaplingPlacement);
		ORANGE_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.ORANGE_MAPLE_BEES_0004.getHolder(), orangeMapleSaplingPlacement);
		TALL_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_ORANGE_MAPLE_TREE.getHolder(), orangeMapleSaplingPlacement);
		TALL_DYING_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_ORANGE_DYING_MAPLE_TREE.getHolder(), orangeMapleSaplingPlacement);
		TALL_ORANGE_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_ORANGE_MAPLE_BEES_0004.getHolder(), orangeMapleSaplingPlacement);
		SHORT_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_ORANGE_MAPLE_TREE.getHolder(), orangeMapleSaplingPlacement);
		FULL_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FULL_ORANGE_MAPLE_TREE.getHolder(), orangeMapleSaplingPlacement);
		BIG_BUSH_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_SHRUB_ORANGE_MAPLE.getHolder(), orangeMapleSaplingPlacement);

		final BlockPredicateFilter redMapleSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.RED_MAPLE_SAPLING);
		RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.RED_MAPLE_TREE.getHolder(), redMapleSaplingPlacement);
		DYING_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_RED_MAPLE_TREE.getHolder(), redMapleSaplingPlacement);
		RED_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.RED_MAPLE_BEES_0004.getHolder(), redMapleSaplingPlacement);
		TALL_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_RED_MAPLE_TREE.getHolder(), redMapleSaplingPlacement);
		TALL_DYING_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_RED_DYING_MAPLE_TREE.getHolder(), redMapleSaplingPlacement);
		TALL_RED_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_RED_MAPLE_BEES_0004.getHolder(), redMapleSaplingPlacement);
		SHORT_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_RED_MAPLE_TREE.getHolder(), redMapleSaplingPlacement);
		FULL_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FULL_RED_MAPLE_TREE.getHolder(), redMapleSaplingPlacement);
		BIG_BUSH_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_SHRUB_RED_MAPLE.getHolder(), redMapleSaplingPlacement);

		FALLEN_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_MAPLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_MAPLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		// OAK
		BlockPredicateFilter oakSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING);
		OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		OAK_NO_FUNGI_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		DYING_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		SHORT_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		FANCY_DYING_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		DYING_FANCY_OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		FANCY_DYING_OAK_BEES_025.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_BEES_025.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_BEES.makeAndSetHolders(oakSaplingPlacement);
		OLD_DYING_FANCY_OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		FANCY_DEAD_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		FANCY_SEMI_DEAD_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		SMALL_FANCY_DEAD_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		DEAD_OAK_CHECKED.makeAndSetHolders(oakSaplingPlacement);
		DEAD_OAK_BRANCHES_CHECKED.makeAndSetHolders(oakSaplingPlacement);

		FALLEN_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_OAK_TREE, fallenTreeFilter);
		FALLEN_OAK_NO_MOSS_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_OAK_TREE_NO_MOSS, fallenTreeFilter);
		MOSSY_FALLEN_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_OAK_TREE, fallenTreeFilter);
		SNAPPED_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_OAK, oakSaplingPlacement);

		// DARK OAK
		BlockPredicateFilter darkOakSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING);
		DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		TALL_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		FANCY_TALL_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		DYING_TALL_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		DYING_FANCY_TALL_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		DYING_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		COBWEB_TALL_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);
		COBWEB_FANCY_TALL_DARK_OAK_CHECKED.makeAndSetHolders(darkOakSaplingPlacement);

		LARGE_FALLEN_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_FALLEN_DARK_OAK, fallenTreeFilter);
		LARGE_SNAPPED_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_DARK_OAK, darkOakSaplingPlacement);

		// PALE OAK
		BlockPredicateFilter paleOakSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.PALE_OAK_SAPLING);
		PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.PALE_OAK, paleOakSaplingPlacement);
		PALE_OAK_CREAKING_CHECKED.makeAndSetHolder(WWTreeConfigured.PALE_OAK_CREAKING, paleOakSaplingPlacement);
		TALL_PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_PALE_OAK, paleOakSaplingPlacement);
		TALL_PALE_OAK_CREAKING_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		FANCY_TALL_PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_TALL_PALE_OAK, paleOakSaplingPlacement);
		FANCY_TALL_PALE_OAK_CREAKING_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_TALL_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		COBWEB_TALL_PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.COBWEB_TALL_PALE_OAK, paleOakSaplingPlacement);
		COBWEB_TALL_PALE_OAK_CREAKING_CHECKED.makeAndSetHolder(WWTreeConfigured.COBWEB_TALL_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		COBWEB_FANCY_PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.COBWEB_FANCY_PALE_OAK, paleOakSaplingPlacement);
		COBWEB_FANCY_PALE_OAK_CREAKING_CHECKED.makeAndSetHolder(WWTreeConfigured.COBWEB_FANCY_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		LARGE_FALLEN_PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_FALLEN_PALE_OAK, paleOakSaplingPlacement);
		LARGE_SNAPPED_PALE_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_PALE_OAK, paleOakSaplingPlacement);

		// SWAMP TREE
		BlockPredicateFilter willowSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING);
		WILLOW_CHECKED.makeAndSetHolders(willowSaplingPlacement);
		WILLOW_TALL_CHECKED.makeAndSetHolders(willowSaplingPlacement);
		WILLOW_TALLER_CHECKED.makeAndSetHolders(willowSaplingPlacement);
		SWAMP_OAK_CHECKED.makeAndSetHolders(willowSaplingPlacement);

		MOSSY_FALLEN_WILLOW_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_WILLOW_TREE, fallenTreeFilter);

		// SPRUCE
		BlockPredicateFilter spruceSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING);
		SPRUCE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SPRUCE.getHolder(), SNOW_TREE_FILTER_DECORATOR);
		SPRUCE_SHORT_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		FUNGUS_PINE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		DYING_FUNGUS_PINE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		FUNGUS_PINE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.FUNGUS_PINE.getHolder(), SNOW_TREE_FILTER_DECORATOR);
		MEGA_FUNGUS_SPRUCE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		MEGA_FUNGUS_PINE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		DYING_MEGA_FUNGUS_PINE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_SPRUCE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_FUNGUS_SPRUCE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_DYING_SPRUCE_CHECKED.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_SPRUCE.getHolder(), SNOW_TREE_FILTER_DECORATOR);
		SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_FUNGUS_SPRUCE.getHolder(), SNOW_TREE_FILTER_DECORATOR);
		SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_DYING_FUNGUS_SPRUCE.getHolder(), SNOW_TREE_FILTER_DECORATOR);
		SHORT_MEGA_DYING_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_DYING_SPRUCE.getHolder(), SNOW_TREE_FILTER_DECORATOR);

		FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_SPRUCE_TREE, fallenTreeFilter);
		MOSSY_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_SPRUCE_TREE, fallenTreeFilter);
		CLEAN_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.CLEAN_FALLEN_SPRUCE_TREE, fallenTreeFilter);
		DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.DECORATED_LARGE_FALLEN_SPRUCE_TREE, fallenTreeFilter);
		CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.CLEAN_LARGE_FALLEN_SPRUCE_TREE, fallenTreeFilter);
		SNAPPED_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_SPRUCE, spruceSaplingPlacement);
		SNAPPED_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SNAPPED_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
		LARGE_SNAPPED_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_SPRUCE, spruceSaplingPlacement);
		LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_SPRUCE, spruceSaplingPlacement);

		// BAOBAB
		BAOBAB.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT));
		BAOBAB_TALL.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT));

		// CYPRESS
		BlockPredicateFilter cypressSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING);
		CYPRESS.makeAndSetHolder(WWTreeConfigured.CYPRESS, cypressSaplingPlacement);
		FUNGUS_CYPRESS.makeAndSetHolder(WWTreeConfigured.FUNGUS_CYPRESS, cypressSaplingPlacement);
		SHORT_CYPRESS.makeAndSetHolder(WWTreeConfigured.SHORT_CYPRESS, cypressSaplingPlacement);
		SHORT_FUNGUS_CYPRESS.makeAndSetHolder(WWTreeConfigured.SHORT_FUNGUS_CYPRESS, cypressSaplingPlacement);
		SWAMP_CYPRESS.makeAndSetHolder(WWTreeConfigured.SWAMP_CYPRESS, cypressSaplingPlacement);

		FALLEN_CYPRESS_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_CYPRESS_TREE, fallenTreeFilter);
		SNAPPED_CYPRESS_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_CYPRESS, cypressSaplingPlacement);

		// BUSH
		LARGE_BUSH_COARSE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_BUSH_COARSE, SAND_TREE_FILTER_DECORATOR);
		LARGE_BUSH_COARSE_GRASS_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_BUSH_COARSE, oakSaplingPlacement);
		LARGE_BUSH_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_BUSH, oakSaplingPlacement);
		BIG_BUSH_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_BUSH, oakSaplingPlacement);

		// PALM
		BlockPredicateFilter coconutPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.COCONUT);
		PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.PALM.getHolder(), coconutPlacement);
		TALL_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_PALM.getHolder(), coconutPlacement);
		TALL_WINDMILL_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_WINDMILL_PALM.getHolder(), coconutPlacement);
		SMALL_WINDMILL_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_WINDMILL_PALM.getHolder(), coconutPlacement);

		FALLEN_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_PALM, fallenTreeFilter);

		// JUNIPER
		JUNIPER.makeAndSetHolders(oakSaplingPlacement);

		//JUNGLE
		BlockPredicateFilter jungleSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING);
		JUNGLE_TREE_CHECKED.makeAndSetHolders(jungleSaplingPlacement);
		MEGA_JUNGLE_TREE_CHECKED.makeAndSetHolders(jungleSaplingPlacement);

		FALLEN_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_JUNGLE_TREE, fallenTreeFilter);
		LARGE_FALLEN_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_FALLEN_JUNGLE_TREE, fallenTreeFilter);
		SNAPPED_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_JUNGLE, jungleSaplingPlacement);
		LARGE_SNAPPED_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_JUNGLE, jungleSaplingPlacement);

		//ACACIA
		ACACIA_CHECKED_LEAF_LITTER.makeAndSetHolder(WWTreeConfigured.ACACIA_LEAF_LITTER, PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING));

		FALLEN_ACACIA_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_ACACIA_TREE, fallenTreeFilter);
		SNAPPED_ACACIA_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_ACACIA, PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING));

		//MANGROVE
		MANGROVE_CHECKED.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));
		TALL_MANGROVE_CHECKED.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

		FALLEN_MANGROVE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_MANGROVE_TREE, fallenTreeFilter);

		//CRIMSON

		FALLEN_CRIMSON_FUNGI.makeAndSetHolder(WWTreeConfigured.FALLEN_CRIMSON_FUNGI,
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		SNAPPED_CRIMSON_FUNGI.makeAndSetHolder(WWTreeConfigured.SNAPPED_CRIMSON_FUNGI,
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		//WARPED

		FALLEN_WARPED_FUNGI.makeAndSetHolder(WWTreeConfigured.FALLEN_WARPED_FUNGI,
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		SNAPPED_WARPED_FUNGI.makeAndSetHolder(WWTreeConfigured.SNAPPED_WARPED_FUNGI,
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		// TREE ON GRASS
		PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.PALM.getHolder(), oakSaplingPlacement);
		PALM_CHECKED_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeConfigured.PALM.getLitterVariantHolder(), oakSaplingPlacement);
		TALL_PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.TALL_PALM.getHolder(), oakSaplingPlacement);
		TALL_PALM_CHECKED_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeConfigured.TALL_PALM.getLitterVariantHolder(), oakSaplingPlacement);
		TALL_WINE_PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.TALL_WINDMILL_PALM.getHolder(), oakSaplingPlacement);
		TALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeConfigured.TALL_WINDMILL_PALM.getLitterVariantHolder(), oakSaplingPlacement);
		SMALL_WINE_PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.SHORT_WINDMILL_PALM.getHolder(), oakSaplingPlacement);
		SMALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeConfigured.SHORT_WINDMILL_PALM.getLitterVariantHolder(), oakSaplingPlacement);
	}
}
