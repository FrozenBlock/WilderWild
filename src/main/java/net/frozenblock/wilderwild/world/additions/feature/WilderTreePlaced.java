/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.additions.feature;

import java.util.List;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.string;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WilderTreePlaced {
	private WilderTreePlaced() {
		throw new UnsupportedOperationException("WilderTreePlaced contains only static declarations.");
	}

	public static final List<PlacementModifier> SNOW_TREE_FILTER_DECORATOR = List.of(
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW))
	);

    //BIRCH
    public static final ResourceKey<PlacedFeature> BIRCH_CHECKED = key("birch_checked");
    public static final ResourceKey<PlacedFeature> BIRCH_BEES_0004 = key("birch_bees_0004");
	public static final ResourceKey<PlacedFeature> BIRCH_BEES_025 = key("birch_bees_025");
    public static final ResourceKey<PlacedFeature> DYING_BIRCH = key("dying_birch");
    public static final ResourceKey<PlacedFeature> SHORT_BIRCH = key("short_birch");
    public static final ResourceKey<PlacedFeature> DYING_SHORT_BIRCH = key("dying_short_birch");
    public static final ResourceKey<PlacedFeature> SHORT_BIRCH_BEES_0004 = key("short_birch_bees_0004");
    public static final ResourceKey<PlacedFeature> DYING_SUPER_BIRCH = key("dying_super_birch");
    public static final ResourceKey<PlacedFeature> SUPER_BIRCH_BEES_0004 = key("super_birch_bees_0004");
    public static final ResourceKey<PlacedFeature> SUPER_BIRCH_BEES = key("super_birch_bees");
	public static final ResourceKey<PlacedFeature> SUPER_BIRCH = key("super_birch");
    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_CHECKED = key("fallen_birch_checked");
	public static final ResourceKey<PlacedFeature> MOSSY_FALLEN_BIRCH_CHECKED = register("mossy_fallen_birch_checked", WilderTreeConfigured.MOSSY_FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final ResourceKey<PlacedFeature> OAK_CHECKED = key("oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_OAK_CHECKED = key("dying_oak_checked");
    public static final ResourceKey<PlacedFeature> OAK_BEES_0004 = key("oak_bees_00004");
    public static final ResourceKey<PlacedFeature> SHORT_OAK_CHECKED = key("short_oak_checked");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_CHECKED = key("fancy_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_FANCY_OAK_CHECKED = key("dying_fancy_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_FANCY_OAK_BEES_0004 = key("dying_fancy_oak_bees_0004");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_0004 = key("fancy_oak_bees_0004");
	public static final ResourceKey<PlacedFeature> DYING_FANCY_OAK_BEES_025 = key("dying_fancy_oak_bees_025");
	public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_025 = key("fancy_oak_bees_025");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES = key("fancy_oak_bees");
    public static final ResourceKey<PlacedFeature> FALLEN_OAK_CHECKED = key("fallen_oak_checked");

    //DARK OAK
    public static final ResourceKey<PlacedFeature> TALL_DARK_OAK_CHECKED = key("tall_dark_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_TALL_DARK_OAK_CHECKED = key("dying_tall_dark_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_DARK_OAK_CHECKED = key("dying_dark_oak_checked");
	public static final ResourceKey<PlacedFeature> COBWEB_TALL_DARK_OAK_CHECKED = key("cobweb_tall_dark_oak_checked");

    //SWAMP TREE
    public static final ResourceKey<PlacedFeature> SWAMP_TREE_CHECKED = key("swamp_tree_checked");

    //SPRUCE
    public static final ResourceKey<PlacedFeature> SPRUCE_CHECKED = key("spruce_checked");
    public static final ResourceKey<PlacedFeature> SPRUCE_ON_SNOW = key("spruce_on_snow");
    public static final ResourceKey<PlacedFeature> SPRUCE_SHORT_CHECKED = key("spruce_short_checked");
    public static final ResourceKey<PlacedFeature> FUNGUS_PINE_CHECKED = key("fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> DYING_FUNGUS_PINE_CHECKED = key("dying_fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> FUNGUS_PINE_ON_SNOW = key("fungus_pine_on_snow");
    public static final ResourceKey<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = key("mega_fungus_spruce_checked");
    public static final ResourceKey<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = key("mega_fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> DYING_MEGA_FUNGUS_PINE_CHECKED = key("dying_mega_fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> FALLEN_SPRUCE_CHECKED = key("fallen_spruce_checked");

    //BAOBAB
    public static final ResourceKey<PlacedFeature> BAOBAB = key("baobab");
    public static final ResourceKey<PlacedFeature> BAOBAB_TALL = key("baobab_tall");

    //CYPRESS
    public static final ResourceKey<PlacedFeature> CYPRESS = key("cypress");
    public static final ResourceKey<PlacedFeature> FUNGUS_CYPRESS = key("fungus_cypress");
    public static final ResourceKey<PlacedFeature> SHORT_CYPRESS = key("short_cypress");
    public static final ResourceKey<PlacedFeature> SHORT_FUNGUS_CYPRESS = key("short_fungus_cypress");
    public static final ResourceKey<PlacedFeature> SWAMP_CYPRESS = key("swamp_cypress");
    public static final ResourceKey<PlacedFeature> FALLEN_CYPRESS_CHECKED = key("fallen_cypress_checked");

    public static final Holder<PlacedFeature> BIRCH_CHECKED = register("birch_checked", WilderTreeConfigured.BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> BIRCH_BEES_0004 = register("birch_bees_0004", WilderTreeConfigured.BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> BIRCH_BEES_025 = register("birch_bees_025", WilderTreeConfigured.BIRCH_BEES_025, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> DYING_BIRCH = register("dying_birch", WilderTreeConfigured.DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH = register("short_birch", WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SHORT_BIRCH = register("dying_short_birch", WilderTreeConfigured.SHORT_DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH_BEES_0004 = register("short_birch_bees_0004", WilderTreeConfigured.SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SUPER_BIRCH = register("dying_super_birch", WilderTreeConfigured.DYING_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SUPER_BIRCH_BEES_0004 = register("super_birch_bees_0004", WilderTreeConfigured.SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SUPER_BIRCH_BEES = register("super_birch_bees", WilderTreeConfigured.SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> SUPER_BIRCH = register("super_birch", WilderTreeConfigured.SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> FALLEN_BIRCH_CHECKED = register("fallen_birch_checked", WilderTreeConfigured.FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final Holder<PlacedFeature> OAK_CHECKED = register("oak_checked", WilderTreeConfigured.OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_OAK_CHECKED = register("dying_oak_checked", WilderTreeConfigured.DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> OAK_BEES_0004 = register("oak_bees_00004", WilderTreeConfigured.OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> SHORT_OAK_CHECKED = register("short_oak_checked", WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FANCY_OAK_CHECKED = register("fancy_oak_checked", WilderTreeConfigured.FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_CHECKED = register("dying_fancy_oak_checked", WilderTreeConfigured.FANCY_DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_BEES_0004 = register("dying_fancy_oak_bees_0004", WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FANCY_OAK_BEES_0004 = register("fancy_oak_bees_0004", WilderTreeConfigured.FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> DYING_FANCY_OAK_BEES_025 = register("dying_fancy_oak_bees_025", WilderTreeConfigured.FANCY_DYING_OAK_BEES_025, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> FANCY_OAK_BEES_025 = register("fancy_oak_bees_025", WilderTreeConfigured.FANCY_OAK_BEES_025, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> FANCY_OAK_BEES = register("fancy_oak_bees", WilderTreeConfigured.FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_OAK_CHECKED = register("fallen_oak_checked", WilderTreeConfigured.FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> MOSSY_FALLEN_OAK_CHECKED = register("mossy_fallen_oak_checked", WilderTreeConfigured.MOSSY_FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> MOSSY_FALLEN_SPRUCE_CHECKED = register("mossy_fallen_spruce_checked", WilderTreeConfigured.MOSSY_FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> OLD_DYING_FANCY_OAK_BEES_0004 = register("old_dying_fancy_oak_bees_0004", WilderTreeConfigured.OLD_FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

	//DARK OAK
    public static final Holder<PlacedFeature> TALL_DARK_OAK_CHECKED = register("tall_dark_oak_checked", WilderTreeConfigured.TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_TALL_DARK_OAK_CHECKED = register("dying_tall_dark_oak_checked", WilderTreeConfigured.DYING_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_DARK_OAK_CHECKED = register("dying_dark_oak_checked", WilderTreeConfigured.DYING_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
	public static final Holder<PlacedFeature> COBWEB_TALL_DARK_OAK_CHECKED = register("cobweb_tall_dark_oak_checked", WilderTreeConfigured.COBWEB_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

    //SWAMP TREE
    public static final Holder<PlacedFeature> SWAMP_TREE_CHECKED = register("swamp_tree_checked", WilderTreeConfigured.SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

    //SPRUCE
    public static final Holder<PlacedFeature> SPRUCE_CHECKED = register("spruce_checked", WilderTreeConfigured.SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> SPRUCE_ON_SNOW = register("spruce_on_snow", WilderTreeConfigured.SPRUCE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> SPRUCE_SHORT_CHECKED = register("spruce_short_checked", WilderTreeConfigured.SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_CHECKED = register("fungus_pine_checked", WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_FUNGUS_PINE_CHECKED = register("dying_fungus_pine_checked", WilderTreeConfigured.DYING_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_ON_SNOW = register("fungus_pine_on_snow", WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = register("mega_fungus_spruce_checked", WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = register("mega_fungus_pine_checked", WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_MEGA_FUNGUS_PINE_CHECKED = register("dying_mega_fungus_pine_checked", WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_SPRUCE_CHECKED = register("fallen_spruce_checked", WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> SHORT_MEGA_SPRUCE_CHECKED = register("short_mega_spruce_checked", WilderTreeConfigured.SHORT_MEGA_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> SHORT_MEGA_FUNGUS_SPRUCE_CHECKED = register("short_mega_fungus_spruce_checked", WilderTreeConfigured.SHORT_MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED = register("short_mega_dying_fungus_spruce_checked", WilderTreeConfigured.SHORT_MEGA_DYING_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> SHORT_MEGA_DYING_SPRUCE_CHECKED = register("short_mega_dying_spruce_checked", WilderTreeConfigured.SHORT_MEGA_DYING_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> SHORT_MEGA_SPRUCE_ON_SNOW = register("short_mega_spruce_on_snow", WilderTreeConfigured.SHORT_MEGA_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
	public static final Holder<PlacedFeature> SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW = register("short_mega_fungus_spruce_on_snow", WilderTreeConfigured.SHORT_MEGA_FUNGUS_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
	public static final Holder<PlacedFeature> SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW = register("short_mega_dying_fungus_spruce_on_snow", WilderTreeConfigured.SHORT_MEGA_DYING_FUNGUS_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
	public static final Holder<PlacedFeature> SHORT_MEGA_DYING_SPRUCE_ON_SNOW = register("short_mega_dying_spruce_on_snow", WilderTreeConfigured.SHORT_MEGA_DYING_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
	//BAOBAB
    public static final Holder<PlacedFeature> BAOBAB = register("baobab", WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
    public static final Holder<PlacedFeature> BAOBAB_TALL = register("baobab_tall", WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));

    //CYPRESS
    public static final Holder<PlacedFeature> CYPRESS = register("cypress", WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_CYPRESS = register("fungus_cypress", WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_CYPRESS = register("short_cypress", WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_FUNGUS_CYPRESS = register("short_fungus_cypress", WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SWAMP_CYPRESS = register("swamp_cypress", WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_CYPRESS_CHECKED = register("fallen_cypress_checked", WilderTreeConfigured.FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));

	//TREE ON SAND
	public static final BlockPredicate SAND_GRASS_TREE_PREDICATE = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.RED_SAND, Blocks.SAND, Blocks.GRASS);
	public static final List<PlacementModifier> SAND_TREE_FILTER_DECORATOR = List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SANDSTONE)), 8), BlockPredicateFilter.forPredicate(SAND_GRASS_TREE_PREDICATE));

	//SHRUB
	public static final ResourceKey<PlacedFeature> BIG_SHRUB_CHECKED = key("big_shrub_checked");

	//PALM
	public static final ResourceKey<PlacedFeature> PALM_CHECKED = key("palm_checked");
	public static final ResourceKey<PlacedFeature> TALL_PALM_CHECKED = key("tall_palm_checked");
	public static final ResourceKey<PlacedFeature> TALL_WINE_PALM_CHECKED = key("tall_wine_palm_checked");
	public static final ResourceKey<PlacedFeature> SMALL_WINE_PALM_CHECKED = key("small_wine_palm_checked");
	public static final ResourceKey<PlacedFeature> PALM_CHECKED_DIRT = key("palm_checked_dirt");
	public static final ResourceKey<PlacedFeature> TALL_PALM_CHECKED_DIRT = key("tall_palm_checked_dirt");
	public static final ResourceKey<PlacedFeature> TALL_WINE_PALM_CHECKED_DIRT = key("tall_wine_palm_checked_dirt");
	public static final ResourceKey<PlacedFeature> SMALL_WINE_PALM_CHECKED_DIRT = key("small_wine_palm_checked_dirt");

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, WilderSharedConstants.id(path));
	}

=======
	public static final Holder<PlacedFeature> BIG_SHRUB_CHECKED = register("big_shrub_checked", WilderTreeConfigured.BIG_SHRUB, SAND_TREE_FILTER_DECORATOR);
	public static final Holder<PlacedFeature> BIG_SHRUB_GRASS_CHECKED = register("big_shrub_grass_checked", WilderTreeConfigured.BIG_SHRUB, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	//PALM
	public static final Holder<PlacedFeature> PALM_CHECKED = register("palm_checked", WilderTreeConfigured.PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	public static final Holder<PlacedFeature> TALL_PALM_CHECKED = register("tall_palm_checked", WilderTreeConfigured.TALL_PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	public static final Holder<PlacedFeature> TALL_WINE_PALM_CHECKED = register("tall_wine_palm_checked", WilderTreeConfigured.TALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	public static final Holder<PlacedFeature> SMALL_WINE_PALM_CHECKED = register("small_wine_palm_checked", WilderTreeConfigured.SMALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	//JUNIPER
	public static final Holder<PlacedFeature> JUNIPER = register("juniper", WilderTreeConfigured.JUNIPER, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	//TREE ON GRASS
	public static final Holder<PlacedFeature> PALM_CHECKED_DIRT = register("palm_checked_dirt", WilderTreeConfigured.PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> TALL_PALM_CHECKED_DIRT = register("tall_palm_checked_dirt", WilderTreeConfigured.TALL_PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> TALL_WINE_PALM_CHECKED_DIRT = register("tall_wine_palm_checked_dirt", WilderTreeConfigured.TALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> SMALL_WINE_PALM_CHECKED_DIRT = register("small_wine_palm_checked_dirt", WilderTreeConfigured.SMALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

	private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placement) {
		return PlacementUtils.register(string(name), feature, placement);
	}

	private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placement) {
		return PlacementUtils.register(string(name), feature, placement);
	}
>>>>>>> dev

}
