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

package net.frozenblock.wilderwild.worldgen.feature.placed;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.feature.WWPlacementUtils;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWTreeConfigured;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WWTreePlaced {
	public static final BlockPredicate SNOW_TREE_PREDICATE = BlockPredicate.matchesBlocks(
		Direction.DOWN.getNormal(),
		Blocks.SNOW_BLOCK,
		Blocks.POWDER_SNOW
	);
	public static final List<PlacementModifier> SNOW_TREE_FILTER_DECORATOR = List.of(
		EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
		BlockPredicateFilter.forPredicate(SNOW_TREE_PREDICATE)
	);
	//BIRCH
	public static final FrozenPlacedFeature BIRCH_CHECKED = WWPlacementUtils.register("birch_checked");
	public static final FrozenPlacedFeature BIRCH_BEES_0004 = WWPlacementUtils.register("birch_bees_0004");
	public static final FrozenPlacedFeature BIRCH_BEES_025 = WWPlacementUtils.register("birch_bees_025");
	public static final FrozenPlacedFeature DYING_BIRCH = WWPlacementUtils.register("dying_birch");
	public static final FrozenPlacedFeature SHORT_BIRCH = WWPlacementUtils.register("short_birch");
	public static final FrozenPlacedFeature DYING_SHORT_BIRCH = WWPlacementUtils.register("dying_short_birch");
	public static final FrozenPlacedFeature SHORT_BIRCH_BEES_0004 = WWPlacementUtils.register("short_birch_bees_0004");
	public static final FrozenPlacedFeature MEDIUM_BIRCH = WWPlacementUtils.register("medium_birch");
	public static final FrozenPlacedFeature DYING_MEDIUM_BIRCH = WWPlacementUtils.register("dying_medium_birch");
	public static final FrozenPlacedFeature MEDIUM_BIRCH_BEES_0004 = WWPlacementUtils.register("medium_birch_bees_0004");
	public static final FrozenPlacedFeature DYING_SUPER_BIRCH = WWPlacementUtils.register("dying_super_birch");
	public static final FrozenPlacedFeature SUPER_BIRCH_BEES_0004 = WWPlacementUtils.register("super_birch_bees_0004");
	public static final FrozenPlacedFeature SUPER_BIRCH_BEES = WWPlacementUtils.register("super_birch_bees");
	public static final FrozenPlacedFeature SUPER_BIRCH = WWPlacementUtils.register("super_birch");
	public static final FrozenPlacedFeature FALLEN_BIRCH_CHECKED = WWPlacementUtils.register("fallen_birch_checked");
	public static final FrozenPlacedFeature MOSSY_FALLEN_BIRCH_CHECKED = WWPlacementUtils.register("mossy_fallen_birch_checked");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_CHECKED = WWPlacementUtils.register("snapped_birch_checked");
	public static final FrozenPlacedFeature DEAD_BIRCH = WWPlacementUtils.register("dead_birch");
	public static final FrozenPlacedFeature DEAD_MEDIUM_BIRCH = WWPlacementUtils.register("dead_medium_birch");
	//CHERRY
	public static final FrozenPlacedFeature CHERRY_CHECKED = WWPlacementUtils.register("cherry_checked");
	public static final FrozenPlacedFeature DYING_CHERRY_CHECKED = WWPlacementUtils.register("dying_cherry_checked");
	public static final FrozenPlacedFeature TALL_CHERRY_CHECKED = WWPlacementUtils.register("tall_cherry_checked");
	public static final FrozenPlacedFeature TALL_DYING_CHERRY_CHECKED = WWPlacementUtils.register("tall_dying_cherry_checked");
	public static final FrozenPlacedFeature CHERRY_BEES_CHECKED = WWPlacementUtils.register("cherry_bees_checked");
	public static final FrozenPlacedFeature TALL_CHERRY_BEES_CHECKED = WWPlacementUtils.register("tall_cherry_bees_checked");
	public static final FrozenPlacedFeature FALLEN_CHERRY_CHECKED = WWPlacementUtils.register("fallen_cherry_checked");
	public static final FrozenPlacedFeature MOSSY_FALLEN_CHERRY_CHECKED = WWPlacementUtils.register("mossy_fallen_cherry_checked");
	public static final FrozenPlacedFeature SNAPPED_CHERRY_CHECKED = WWPlacementUtils.register("snapped_cherry_checked");
	//MAPLE
	public static final FrozenPlacedFeature YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("yellow_maple_checked");
	public static final FrozenPlacedFeature DYING_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("dying_yellow_maple_checked");
	public static final FrozenPlacedFeature TALL_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("tall_yellow_maple_checked");
	public static final FrozenPlacedFeature TALL_DYING_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("tall_dying_yellow_maple_checked");
	public static final FrozenPlacedFeature YELLOW_MAPLE_BEES_CHECKED = WWPlacementUtils.register("yellow_maple_bees_checked");
	public static final FrozenPlacedFeature TALL_YELLOW_MAPLE_BEES_CHECKED = WWPlacementUtils.register("tall_yellow_maple_bees_checked");
	public static final FrozenPlacedFeature SHORT_YELLOW_MAPLE_CHECKED = WWPlacementUtils.register("short_yellow_maple_checked");

	public static final FrozenPlacedFeature ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("orange_maple_checked");
	public static final FrozenPlacedFeature DYING_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("dying_orange_maple_checked");
	public static final FrozenPlacedFeature TALL_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("tall_orange_maple_checked");
	public static final FrozenPlacedFeature TALL_DYING_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("tall_dying_orange_maple_checked");
	public static final FrozenPlacedFeature ORANGE_MAPLE_BEES_CHECKED = WWPlacementUtils.register("orange_maple_bees_checked");
	public static final FrozenPlacedFeature TALL_ORANGE_MAPLE_BEES_CHECKED = WWPlacementUtils.register("tall_orange_maple_bees_checked");
	public static final FrozenPlacedFeature SHORT_ORANGE_MAPLE_CHECKED = WWPlacementUtils.register("short_orange_maple_checked");

	public static final FrozenPlacedFeature RED_MAPLE_CHECKED = WWPlacementUtils.register("red_maple_checked");
	public static final FrozenPlacedFeature DYING_RED_MAPLE_CHECKED = WWPlacementUtils.register("dying_red_maple_checked");
	public static final FrozenPlacedFeature TALL_RED_MAPLE_CHECKED = WWPlacementUtils.register("tall_red_maple_checked");
	public static final FrozenPlacedFeature TALL_DYING_RED_MAPLE_CHECKED = WWPlacementUtils.register("tall_dying_red_maple_checked");
	public static final FrozenPlacedFeature RED_MAPLE_BEES_CHECKED = WWPlacementUtils.register("red_maple_bees_checked");
	public static final FrozenPlacedFeature TALL_RED_MAPLE_BEES_CHECKED = WWPlacementUtils.register("tall_red_maple_bees_checked");
	public static final FrozenPlacedFeature SHORT_RED_MAPLE_CHECKED = WWPlacementUtils.register("short_red_maple_checked");

	public static final FrozenPlacedFeature FALLEN_MAPLE_CHECKED = WWPlacementUtils.register("fallen_maple_checked");
	public static final FrozenPlacedFeature MOSSY_FALLEN_MAPLE_CHECKED = WWPlacementUtils.register("mossy_fallen_maple_checked");
	public static final FrozenPlacedFeature SNAPPED_MAPLE_CHECKED = WWPlacementUtils.register("snapped_maple_checked");
	//OAK
	public static final FrozenPlacedFeature OAK_CHECKED = WWPlacementUtils.register("oak_checked");
	public static final FrozenPlacedFeature DYING_OAK_CHECKED = WWPlacementUtils.register("dying_oak_checked");
	public static final FrozenPlacedFeature OAK_BEES_0004 = WWPlacementUtils.register("oak_bees_00004");
	public static final FrozenPlacedFeature SHORT_OAK_CHECKED = WWPlacementUtils.register("short_oak_checked");
	public static final FrozenPlacedFeature FANCY_OAK_CHECKED = WWPlacementUtils.register("fancy_oak_checked");
	public static final FrozenPlacedFeature DYING_FANCY_OAK_CHECKED = WWPlacementUtils.register("dying_fancy_oak_checked");
	public static final FrozenPlacedFeature DYING_FANCY_OAK_BEES_0004 = WWPlacementUtils.register("dying_fancy_oak_bees_0004");
	public static final FrozenPlacedFeature FANCY_OAK_BEES_0004 = WWPlacementUtils.register("fancy_oak_bees_0004");
	public static final FrozenPlacedFeature DYING_FANCY_OAK_BEES_025 = WWPlacementUtils.register("dying_fancy_oak_bees_025");
	public static final FrozenPlacedFeature FANCY_OAK_BEES_025 = WWPlacementUtils.register("fancy_oak_bees_025");
	public static final FrozenPlacedFeature FANCY_OAK_BEES = WWPlacementUtils.register("fancy_oak_bees");
	public static final FrozenPlacedFeature FALLEN_OAK_CHECKED = WWPlacementUtils.register("fallen_oak_checked");
	public static final FrozenPlacedFeature FALLEN_OAK_NO_MOSS_CHECKED = WWPlacementUtils.register("fallen_oak_no_moss_checked");
	public static final FrozenPlacedFeature MOSSY_FALLEN_OAK_CHECKED = WWPlacementUtils.register("mossy_fallen_oak_checked");
	public static final FrozenPlacedFeature MOSSY_FALLEN_STRAIGHT_OAK_CHECKED = WWPlacementUtils.register("mossy_fallen_straight_oak_checked");
	public static final FrozenPlacedFeature MOSSY_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("mossy_fallen_spruce_checked");
	public static final FrozenPlacedFeature CLEAN_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("clean_fallen_spruce_checked");
	public static final FrozenPlacedFeature OLD_DYING_FANCY_OAK_BEES_0004 = WWPlacementUtils.register("old_dying_fancy_oak_bees_0004");
	public static final FrozenPlacedFeature SNAPPED_OAK_CHECKED = WWPlacementUtils.register("snapped_oak_checked");
	public static final FrozenPlacedFeature FANCY_DEAD_OAK_CHECKED = WWPlacementUtils.register("fancy_dead_oak_checked");
	public static final FrozenPlacedFeature FANCY_SEMI_DEAD_OAK_CHECKED = WWPlacementUtils.register("fancy_semi_dead_oak_checked");
	public static final FrozenPlacedFeature SMALL_FANCY_DEAD_OAK_CHECKED = WWPlacementUtils.register("small_fancy_dead_oak_checked");
	public static final FrozenPlacedFeature SMALL_FANCY_SEMI_DEAD_OAK_CHECKED = WWPlacementUtils.register("small_fancy_semi_dead_oak_checked");
	public static final FrozenPlacedFeature DEAD_OAK_CHECKED = WWPlacementUtils.register("dead_oak_checked");
	public static final FrozenPlacedFeature DEAD_OAK_BRANCHES_CHECKED = WWPlacementUtils.register("dead_oak_branches_checked");
	//DARK OAK
	public static final FrozenPlacedFeature TALL_DARK_OAK_CHECKED = WWPlacementUtils.register("tall_dark_oak_checked");
	public static final FrozenPlacedFeature FANCY_TALL_DARK_OAK_CHECKED = WWPlacementUtils.register("fancy_tall_dark_oak_checked");
	public static final FrozenPlacedFeature DYING_TALL_DARK_OAK_CHECKED = WWPlacementUtils.register("dying_tall_dark_oak_checked");
	public static final FrozenPlacedFeature DYING_FANCY_TALL_DARK_OAK_CHECKED = WWPlacementUtils.register("dying_fancy_tall_dark_oak_checked");
	public static final FrozenPlacedFeature DYING_DARK_OAK_CHECKED = WWPlacementUtils.register("dying_dark_oak_checked");
	public static final FrozenPlacedFeature COBWEB_TALL_DARK_OAK_CHECKED = WWPlacementUtils.register("cobweb_tall_dark_oak_checked");
	public static final FrozenPlacedFeature COBWEB_FANCY_TALL_DARK_OAK_CHECKED = WWPlacementUtils.register("cobweb_fancy_tall_dark_oak_checked");
	public static final FrozenPlacedFeature LARGE_FALLEN_DARK_OAK_CHECKED = WWPlacementUtils.register("large_fallen_dark_oak_checked");
	public static final FrozenPlacedFeature LARGE_SNAPPED_DARK_OAK_CHECKED = WWPlacementUtils.register("large_snapped_dark_oak_checked");
	//SWAMP TREE
	public static final FrozenPlacedFeature SWAMP_TREE_CHECKED = WWPlacementUtils.register("swamp_tree_checked");
	//SPRUCE
	public static final FrozenPlacedFeature SPRUCE_CHECKED = WWPlacementUtils.register("spruce_checked");
	public static final FrozenPlacedFeature SPRUCE_ON_SNOW = WWPlacementUtils.register("spruce_on_snow");
	public static final FrozenPlacedFeature SPRUCE_SHORT_CHECKED = WWPlacementUtils.register("spruce_short_checked");
	public static final FrozenPlacedFeature FUNGUS_PINE_CHECKED = WWPlacementUtils.register("fungus_pine_checked");
	public static final FrozenPlacedFeature DYING_FUNGUS_PINE_CHECKED = WWPlacementUtils.register("dying_fungus_pine_checked");
	public static final FrozenPlacedFeature FUNGUS_PINE_ON_SNOW = WWPlacementUtils.register("fungus_pine_on_snow");
	public static final FrozenPlacedFeature MEGA_FUNGUS_SPRUCE_CHECKED = WWPlacementUtils.register("mega_fungus_spruce_checked");
	public static final FrozenPlacedFeature MEGA_FUNGUS_PINE_CHECKED = WWPlacementUtils.register("mega_fungus_pine_checked");
	public static final FrozenPlacedFeature DYING_MEGA_FUNGUS_PINE_CHECKED = WWPlacementUtils.register("dying_mega_fungus_pine_checked");
	public static final FrozenPlacedFeature FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("fallen_spruce_checked");
	public static final FrozenPlacedFeature SHORT_MEGA_SPRUCE_CHECKED = WWPlacementUtils.register("short_mega_spruce_checked");
	public static final FrozenPlacedFeature SHORT_MEGA_FUNGUS_SPRUCE_CHECKED = WWPlacementUtils.register("short_mega_fungus_spruce_checked");
	public static final FrozenPlacedFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED = WWPlacementUtils.register("short_mega_dying_fungus_spruce_checked");
	public static final FrozenPlacedFeature SHORT_MEGA_DYING_SPRUCE_CHECKED = WWPlacementUtils.register("short_mega_dying_spruce_checked");
	public static final FrozenPlacedFeature SHORT_MEGA_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_spruce_on_snow");
	public static final FrozenPlacedFeature SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_fungus_spruce_on_snow");
	public static final FrozenPlacedFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_dying_fungus_spruce_on_snow");
	public static final FrozenPlacedFeature SHORT_MEGA_DYING_SPRUCE_ON_SNOW = WWPlacementUtils.register("short_mega_dying_spruce_on_snow");
	public static final FrozenPlacedFeature SNAPPED_SPRUCE_CHECKED = WWPlacementUtils.register("snapped_spruce_checked");
	public static final FrozenPlacedFeature SNAPPED_SPRUCE_ON_SNOW = WWPlacementUtils.register("snapped_spruces_on_snow");
	public static final FrozenPlacedFeature LARGE_SNAPPED_SPRUCE_CHECKED = WWPlacementUtils.register("large_snapped_spruce_checked");
	public static final FrozenPlacedFeature LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED = WWPlacementUtils.register("large_snapped_spruces_on_snow");
	public static final FrozenPlacedFeature DECORATED_LARGE_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("decorated_large_fallen_spruce_checked");
	public static final FrozenPlacedFeature CLEAN_LARGE_FALLEN_SPRUCE_CHECKED = WWPlacementUtils.register("clean_large_fallen_spruce_checked");
	//BAOBAB
	public static final FrozenPlacedFeature BAOBAB = WWPlacementUtils.register("baobab");
	public static final FrozenPlacedFeature BAOBAB_TALL = WWPlacementUtils.register("baobab_tall");
	//CYPRESS
	public static final FrozenPlacedFeature CYPRESS = WWPlacementUtils.register("cypress");
	public static final FrozenPlacedFeature FUNGUS_CYPRESS = WWPlacementUtils.register("fungus_cypress");
	public static final FrozenPlacedFeature SHORT_CYPRESS = WWPlacementUtils.register("short_cypress");
	public static final FrozenPlacedFeature SHORT_FUNGUS_CYPRESS = WWPlacementUtils.register("short_fungus_cypress");
	public static final FrozenPlacedFeature SWAMP_CYPRESS = WWPlacementUtils.register("swamp_cypress");
	public static final FrozenPlacedFeature FALLEN_CYPRESS_CHECKED = WWPlacementUtils.register("fallen_cypress_checked");
	public static final FrozenPlacedFeature SNAPPED_CYPRESS_CHECKED = WWPlacementUtils.register("snapped_cypress_checked");
	//TREE ON SAND
	public static final BlockPredicate SAND_GRASS_TREE_PREDICATE = BlockPredicate.matchesBlocks(
		Direction.DOWN.getNormal(),
		Blocks.RED_SAND,
		Blocks.SAND,
		Blocks.GRASS_BLOCK
	);
	public static final List<PlacementModifier> SAND_TREE_FILTER_DECORATOR = List.of(
		EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SANDSTONE)), 8),
		BlockPredicateFilter.forPredicate(SAND_GRASS_TREE_PREDICATE)
	);
	//SHRUB
	public static final FrozenPlacedFeature BIG_SHRUB_COARSE_CHECKED = WWPlacementUtils.register("big_shrub_coarse_checked");
	public static final FrozenPlacedFeature BIG_SHRUB_COARSE_GRASS_CHECKED = WWPlacementUtils.register("big_shrub_coarse_grass_checked");
	public static final FrozenPlacedFeature BIG_SHRUB_CHECKED = WWPlacementUtils.register("big_shrub_checked");
	public static final FrozenPlacedFeature SHRUB_CHECKED = WWPlacementUtils.register("shrub_checked");
	//PALM
	public static final FrozenPlacedFeature PALM_CHECKED = WWPlacementUtils.register("palm_checked");
	public static final FrozenPlacedFeature TALL_PALM_CHECKED = WWPlacementUtils.register("tall_palm_checked");
	public static final FrozenPlacedFeature TALL_WINDMILL_PALM_CHECKED = WWPlacementUtils.register("tall_windmill_palm_checked");
	public static final FrozenPlacedFeature SMALL_WINDMILL_PALM_CHECKED = WWPlacementUtils.register("small_windmill_palm_checked");
	public static final FrozenPlacedFeature FALLEN_PALM_CHECKED = WWPlacementUtils.register("fallen_palm_checked");
	//JUNIPER
	public static final FrozenPlacedFeature JUNIPER = WWPlacementUtils.register("juniper");
	//JUNGLE
	public static final FrozenPlacedFeature FALLEN_JUNGLE_CHECKED = WWPlacementUtils.register("fallen_jungle_checked");
	public static final FrozenPlacedFeature SNAPPED_JUNGLE_CHECKED = WWPlacementUtils.register("snapped_jungle_checked");
	public static final FrozenPlacedFeature LARGE_FALLEN_JUNGLE_CHECKED = WWPlacementUtils.register("large_fallen_jungle_checked");
	public static final FrozenPlacedFeature LARGE_SNAPPED_JUNGLE_CHECKED = WWPlacementUtils.register("large_snapped_jungle_checked");
	//ACACIA
	public static final FrozenPlacedFeature FALLEN_ACACIA_CHECKED = WWPlacementUtils.register("fallen_acacia_checked");
	public static final FrozenPlacedFeature SNAPPED_ACACIA_CHECKED = WWPlacementUtils.register("snapped_acacia_checked");
	//MANGROVE
	public static final FrozenPlacedFeature FALLEN_MANGROVE_CHECKED = WWPlacementUtils.register("fallen_mangrove_checked");
	//TREE ON GRASS
	public static final FrozenPlacedFeature PALM_CHECKED_DIRT = WWPlacementUtils.register("palm_checked_dirt");
	public static final FrozenPlacedFeature TALL_PALM_CHECKED_DIRT = WWPlacementUtils.register("tall_palm_checked_dirt");
	public static final FrozenPlacedFeature TALL_WINE_PALM_CHECKED_DIRT = WWPlacementUtils.register("tall_wine_palm_checked_dirt");
	public static final FrozenPlacedFeature SMALL_WINE_PALM_CHECKED_DIRT = WWPlacementUtils.register("small_wine_palm_checked_dirt");

	private WWTreePlaced() {
		throw new UnsupportedOperationException("WilderTreePlaced contains only static declarations.");
	}

	public static void registerTreePlaced() {
		WWConstants.logWithModId("Registering WilderTreePlaced for", true);

		// BIRCH

		BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.BIRCH_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		BIRCH_BEES_0004.makeAndSetHolder(WWTreeConfigured.BIRCH_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		BIRCH_BEES_025.makeAndSetHolder(WWTreeConfigured.BIRCH_BEES_025.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		DYING_BIRCH.makeAndSetHolder(WWTreeConfigured.DYING_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		SHORT_BIRCH.makeAndSetHolder(WWTreeConfigured.SHORT_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		DYING_SHORT_BIRCH.makeAndSetHolder(WWTreeConfigured.SHORT_DYING_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		SHORT_BIRCH_BEES_0004.makeAndSetHolder(WWTreeConfigured.SHORT_BIRCH_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		MEDIUM_BIRCH.makeAndSetHolder(WWTreeConfigured.MEDIUM_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		DYING_MEDIUM_BIRCH.makeAndSetHolder(WWTreeConfigured.MEDIUM_DYING_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		MEDIUM_BIRCH_BEES_0004.makeAndSetHolder(WWTreeConfigured.MEDIUM_BIRCH_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		DYING_SUPER_BIRCH.makeAndSetHolder(WWTreeConfigured.DYING_SUPER_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		SUPER_BIRCH_BEES_0004.makeAndSetHolder(WWTreeConfigured.SUPER_BIRCH_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		SUPER_BIRCH_BEES.makeAndSetHolder(WWTreeConfigured.SUPER_BIRCH_BEES.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		SUPER_BIRCH.makeAndSetHolder(WWTreeConfigured.SUPER_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		FALLEN_BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_BIRCH_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		MOSSY_FALLEN_BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_BIRCH_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_BIRCH_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		DEAD_BIRCH.makeAndSetHolder(WWTreeConfigured.DEAD_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		DEAD_MEDIUM_BIRCH.makeAndSetHolder(WWTreeConfigured.DEAD_MEDIUM_BIRCH.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
		);

		// CHERRY

		CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.CHERRY_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
		);

		DYING_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_CHERRY_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
		);

		CHERRY_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.CHERRY_BEES_025.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
		);

		TALL_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_CHERRY_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
		);

		TALL_DYING_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_DYING_CHERRY_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
		);

		TALL_CHERRY_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_CHERRY_BEES_025.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
		);

		FALLEN_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_CHERRY_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		MOSSY_FALLEN_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_CHERRY_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_CHERRY_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_CHERRY_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		// MAPLE

		YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.YELLOW_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		DYING_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_YELLOW_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		YELLOW_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.YELLOW_MAPLE_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_YELLOW_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_DYING_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_YELLOW_DYING_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_YELLOW_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_YELLOW_MAPLE_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		SHORT_YELLOW_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_YELLOW_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.ORANGE_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		DYING_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_ORANGE_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		ORANGE_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.ORANGE_MAPLE_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_ORANGE_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_DYING_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_ORANGE_DYING_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_ORANGE_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_ORANGE_MAPLE_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		SHORT_ORANGE_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_ORANGE_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.RED_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		DYING_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_RED_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		RED_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.RED_MAPLE_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_RED_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_DYING_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_RED_DYING_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		TALL_RED_MAPLE_BEES_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_RED_MAPLE_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);

		SHORT_RED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_RED_MAPLE_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING)
		);


		FALLEN_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_MAPLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		MOSSY_FALLEN_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_MAPLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_MAPLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_MAPLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		// OAK

		OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		DYING_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		OAK_BEES_0004.makeAndSetHolder(WWTreeConfigured.OAK_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		SHORT_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FANCY_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		DYING_FANCY_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_DYING_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		DYING_FANCY_OAK_BEES_0004.makeAndSetHolder(WWTreeConfigured.FANCY_DYING_OAK_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FANCY_OAK_BEES_0004.makeAndSetHolder(WWTreeConfigured.FANCY_OAK_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		DYING_FANCY_OAK_BEES_025.makeAndSetHolder(WWTreeConfigured.FANCY_DYING_OAK_BEES_025.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FANCY_OAK_BEES_025.makeAndSetHolder(WWTreeConfigured.FANCY_OAK_BEES_025.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FANCY_OAK_BEES.makeAndSetHolder(WWTreeConfigured.FANCY_OAK_BEES.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FALLEN_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_OAK_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		FALLEN_OAK_NO_MOSS_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_OAK_TREE_NO_MOSS.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		MOSSY_FALLEN_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_OAK_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		MOSSY_FALLEN_STRAIGHT_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_STRAIGHT_OAK_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		MOSSY_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.MOSSY_FALLEN_SPRUCE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		CLEAN_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.CLEAN_FALLEN_SPRUCE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		OLD_DYING_FANCY_OAK_BEES_0004.makeAndSetHolder(WWTreeConfigured.OLD_FANCY_DYING_OAK_BEES_0004.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		SNAPPED_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FANCY_DEAD_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_DEAD_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		FANCY_SEMI_DEAD_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_SEMI_DEAD_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		SMALL_FANCY_DEAD_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.SMALL_FANCY_DEAD_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.SMALL_FANCY_SEMI_DEAD_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		DEAD_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.DEAD_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		DEAD_OAK_BRANCHES_CHECKED.makeAndSetHolder(WWTreeConfigured.DEAD_OAK_BRANCHES.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		// DARK OAK

		TALL_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);

		FANCY_TALL_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.FANCY_TALL_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);
		DYING_TALL_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_TALL_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);
		DYING_FANCY_TALL_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_FANCY_TALL_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);

		DYING_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);

		COBWEB_TALL_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.COBWEB_TALL_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);

		COBWEB_FANCY_TALL_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.COBWEB_FANCY_TALL_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);

		LARGE_FALLEN_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_FALLEN_DARK_OAK.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		LARGE_SNAPPED_DARK_OAK_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_DARK_OAK.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
		);

		// SWAMP TREE

		SWAMP_TREE_CHECKED.makeAndSetHolder(WWTreeConfigured.SWAMP_TREE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
		);

		// SPRUCE

		SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		SPRUCE_SHORT_CHECKED.makeAndSetHolder(WWTreeConfigured.SPRUCE_SHORT.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		FUNGUS_PINE_CHECKED.makeAndSetHolder(WWTreeConfigured.FUNGUS_PINE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		DYING_FUNGUS_PINE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_FUNGUS_PINE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		FUNGUS_PINE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.FUNGUS_PINE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		MEGA_FUNGUS_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.MEGA_FUNGUS_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		MEGA_FUNGUS_PINE_CHECKED.makeAndSetHolder(WWTreeConfigured.MEGA_FUNGUS_PINE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		DYING_MEGA_FUNGUS_PINE_CHECKED.makeAndSetHolder(WWTreeConfigured.DYING_MEGA_FUNGUS_PINE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_SPRUCE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SHORT_MEGA_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		SHORT_MEGA_FUNGUS_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_FUNGUS_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_DYING_FUNGUS_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		SHORT_MEGA_DYING_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_DYING_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_FUNGUS_SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_DYING_FUNGUS_SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		SHORT_MEGA_DYING_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SHORT_MEGA_DYING_SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		SNAPPED_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		SNAPPED_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeConfigured.SNAPPED_SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		LARGE_SNAPPED_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_SPRUCE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
		);

		LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_SPRUCE.getHolder(),
			SNOW_TREE_FILTER_DECORATOR
		);

		DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.DECORATED_LARGE_FALLEN_SPRUCE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.makeAndSetHolder(WWTreeConfigured.CLEAN_LARGE_FALLEN_SPRUCE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		// BAOBAB

		BAOBAB.makeAndSetHolder(WWTreeConfigured.BAOBAB.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT)
		);

		BAOBAB_TALL.makeAndSetHolder(WWTreeConfigured.BAOBAB_TALL.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT)
		);

		// CYPRESS

		CYPRESS.makeAndSetHolder(WWTreeConfigured.CYPRESS.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		FUNGUS_CYPRESS.makeAndSetHolder(WWTreeConfigured.FUNGUS_CYPRESS.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		SHORT_CYPRESS.makeAndSetHolder(WWTreeConfigured.SHORT_CYPRESS.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		SHORT_FUNGUS_CYPRESS.makeAndSetHolder(WWTreeConfigured.SHORT_FUNGUS_CYPRESS.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		SWAMP_CYPRESS.makeAndSetHolder(WWTreeConfigured.SWAMP_CYPRESS.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		FALLEN_CYPRESS_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_CYPRESS_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_CYPRESS_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_CYPRESS.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		// SHRUB

		BIG_SHRUB_COARSE_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_SHRUB_COARSE.getHolder(),
			SAND_TREE_FILTER_DECORATOR
		);

		BIG_SHRUB_COARSE_GRASS_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_SHRUB_COARSE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		BIG_SHRUB_CHECKED.makeAndSetHolder(WWTreeConfigured.BIG_SHRUB.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		SHRUB_CHECKED.makeAndSetHolder(WWTreeConfigured.SHRUB.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		// PALM

		PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.COCONUT)
		);

		TALL_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.COCONUT)
		);

		TALL_WINDMILL_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.TALL_WINDMILL_PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.COCONUT)
		);

		SMALL_WINDMILL_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.SHORT_WINDMILL_PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.COCONUT)
		);

		FALLEN_PALM_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_PALM.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		// JUNIPER

		JUNIPER.makeAndSetHolder(WWTreeConfigured.JUNIPER.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		//JUNGLE

		FALLEN_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_JUNGLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_JUNGLE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING)
		);

		LARGE_FALLEN_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_FALLEN_JUNGLE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		LARGE_SNAPPED_JUNGLE_CHECKED.makeAndSetHolder(WWTreeConfigured.LARGE_SNAPPED_JUNGLE.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING)
		);

		//ACACIA

		FALLEN_ACACIA_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_ACACIA_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		SNAPPED_ACACIA_CHECKED.makeAndSetHolder(WWTreeConfigured.SNAPPED_ACACIA.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING)
		);

		//MANGROVE

		FALLEN_MANGROVE_CHECKED.makeAndSetHolder(WWTreeConfigured.FALLEN_MANGROVE_TREE.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE))
		);

		// TREE ON GRASS

		PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		TALL_PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.TALL_PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		TALL_WINE_PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.TALL_WINDMILL_PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);

		SMALL_WINE_PALM_CHECKED_DIRT.makeAndSetHolder(WWTreeConfigured.SHORT_WINDMILL_PALM.getHolder(),
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
		);
	}
}
