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

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WilderPlacedFeatures {
	private WilderPlacedFeatures() {
		throw new UnsupportedOperationException("ResourceKey<PlacedFeatures contains only static declarations.");
	}

    //FALLEN TREES
    public static final ResourceKey<PlacedFeature> FALLEN_TREES_MIXED_PLACED = key("fallen_trees_mixed_placed");
    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_SPRUCE_PLACED = key("fallen_oak_and_spruce_placed");
    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_BIRCH_PLACED = key("fallen_oak_and_birch_placed");
	public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_BIRCH_PLACED_2 = key("fallen_oak_and_birch_placed_2");
    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_CYPRESS_PLACED = key("fallen_oak_and_cypress_placed");
    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_PLACED = key("fallen_birch_placed");
    public static final ResourceKey<PlacedFeature> FALLEN_SPRUCE_PLACED = key("fallen_spruce_placed");
	public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_AND_SPRUCE_PLACED = key("fallen_birch_and_spruce_placed");
	public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_AND_SPRUCE_PLACED_2 = key("fallen_birch_and_spruce_placed_2");

    //TREES
    public static final ResourceKey<PlacedFeature> TREES_PLAINS = key("trees_plains");
    public static final ResourceKey<PlacedFeature> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");
	public static final ResourceKey<PlacedFeature> TREES_FLOWER_FIELD = key("flower_field_trees");
    public static final ResourceKey<PlacedFeature> TREES_FLOWER_FOREST = key("trees_flower_forest");
    public static final ResourceKey<PlacedFeature> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");
	public static final ResourceKey<PlacedFeature> OLD_GROWTH_DARK_FOREST_VEGETATION = key("old_growth_dark_forest_vegetation");
    public static final ResourceKey<PlacedFeature> TREES_BIRCH_PLACED = key("trees_birch");
    public static final ResourceKey<PlacedFeature> BIRCH_TALL_PLACED = key("birch_tall");
    public static final ResourceKey<PlacedFeature> SPRUCE_PLACED = key("spruce_placed");
    public static final ResourceKey<PlacedFeature> SHORT_SPRUCE_PLACED = key("short_spruce_placed");
    public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");
    public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");
    public static final ResourceKey<PlacedFeature> TREES_SNOWY = key("trees_snowy");
	public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = key("old_growth_snowy_pine_taiga_trees");
	public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_BIRCH_TAIGA = key("trees_old_growth_birch_taiga");
	public static final ResourceKey<PlacedFeature> DARK_BIRCH_FOREST_VEGETATION = key("dark_birch_forest_vegetation");
    public static final ResourceKey<PlacedFeature> TREES_GROVE = key("trees_grove");
    public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");
    public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_FOREST = key("trees_windswept_forest");
    public static final ResourceKey<PlacedFeature> TREES_MEADOW = key("trees_meadow");
    public static final ResourceKey<PlacedFeature> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");
    public static final ResourceKey<PlacedFeature> SAVANNA_TREES = key("savanna_trees");
    public static final ResourceKey<PlacedFeature> TREES_SWAMP = key("trees_swamp");
	public static final ResourceKey<PlacedFeature> TREES_ARID_SAVANNA = key("arid_savanna_trees");
    public static final ResourceKey<PlacedFeature> MIXED_TREES = key("mixed_trees");
	public static final ResourceKey<PlacedFeature> BIRCH_TAIGA_TREES = key("birch_taiga_trees");
    public static final ResourceKey<PlacedFeature> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");
    public static final ResourceKey<PlacedFeature> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");
	public static final ResourceKey<PlacedFeature> TREES_PARCHED_FOREST = key("parched_forest_trees");
	public static final ResourceKey<PlacedFeature> TREES_ARID_FOREST = key("arid_forest_trees");
	public static final ResourceKey<PlacedFeature> TREES_SEMI_BIRCH_AND_OAK = key("semi_birch_forest_trees");
	public static final ResourceKey<PlacedFeature> TREES_BIRCH_JUNGLE = key("birch_jungle_trees");
	public static final ResourceKey<PlacedFeature> TREES_SPARSE_BIRCH_JUNGLE = key("sparse_birch_jungle_trees");
	public static final ResourceKey<PlacedFeature> BIG_SHRUB = key("big_shrub");
	public static final ResourceKey<PlacedFeature> PALM = key("palm_placed");
	public static final ResourceKey<PlacedFeature> PALM_JUNGLE = key("palm_jungle");
	public static final ResourceKey<PlacedFeature> PALMS_OASIS = key("palms_oasis");
	public static final ResourceKey<PlacedFeature> PALM_RARE = key("palm_rare");

    //MUSHROOMS
    public static final ResourceKey<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = key("brown_shelf_fungus_placed");
    public static final ResourceKey<PlacedFeature> RED_SHELF_FUNGUS_PLACED = key("red_shelf_fungus_placed");
    public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_PLACED = key("brown_mushroom_placed");
    public static final ResourceKey<PlacedFeature> HUGE_RED_MUSHROOM_PLACED = key("huge_red_mushroom_placed");
    public static final ResourceKey<PlacedFeature> HUGE_MUSHROOMS_SWAMP = key("huge_mushrooms_swamp");
    public static final ResourceKey<PlacedFeature> MUSHROOM_PLACED = key("mushroom_placed");
    public static final ResourceKey<PlacedFeature> MIXED_MUSHROOMS_PLACED = key("mixed_mushroom_placed");

    //GRASS AND FERNS
	public static final ResourceKey<PlacedFeature> OASIS_GRASS_PLACED = key("oasis_grass_placed");
	public static final ResourceKey<PlacedFeature> OASIS_BUSH_PLACED = key("oasis_bush_placed");
	public static final ResourceKey<PlacedFeature> DESERT_BUSH_PLACED = key("desert_bush_placed");
	public static final ResourceKey<PlacedFeature> OASIS_CACTUS_PLACED = key("oasis_cactus_placed");
	public static final ResourceKey<PlacedFeature> FLOWER_FIELD_BUSH_PLACED = key("flower_field_bush_placed");
	public static final ResourceKey<PlacedFeature> ARID_BUSH_PLACED = key("arid_bush_placed");
	public static final ResourceKey<PlacedFeature> TALL_CACTUS_PLACED = key("tall_cactus_placed");
	public static final ResourceKey<PlacedFeature> ARID_CACTUS_PLACED = key("arid_cactus_placed");

	public static final ResourceKey<PlacedFeature> GRASS_PLACED = key("grass_placed");
    public static final ResourceKey<PlacedFeature> RARE_GRASS_PLACED = key("rare_grass_placed");
    public static final ResourceKey<PlacedFeature> TALL_GRASS = key("tall_grass");
    public static final ResourceKey<PlacedFeature> DENSE_TALL_GRASS_PLACED = key("dense_tall_grass_placed");
    public static final ResourceKey<PlacedFeature> DENSE_FERN_PLACED = key("dense_fern_placed");
    public static final ResourceKey<PlacedFeature> SEAGRASS_CYPRESS = key("seagrass_cypress");
    public static final ResourceKey<PlacedFeature> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
    public static final ResourceKey<PlacedFeature> LARGE_FERN_AND_GRASS_RARE = key("large_fern_and_grass_rare");
	public static final ResourceKey<PlacedFeature> FLOWER_FIELD_GRASS_PLACED = key("grass_flower_field_placed");
	public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS_FF = key("patch_tall_grass_ff");

    //FLOWERS
    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION = key("seeding_dandelion");
    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION_MIXED = key("seeding_dandelion_mixed");
    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION_CYPRESS = key("seeding_dandelion_cypress");
    public static final ResourceKey<PlacedFeature> CARNATION = key("carnation");
    public static final ResourceKey<PlacedFeature> DATURA_BIRCH = key("datura_birch");
    public static final ResourceKey<PlacedFeature> FLOWER_PLAINS = key("flower_plains");
    public static final ResourceKey<PlacedFeature> DENSE_FLOWER_PLACED = key("dense_flower_placed");
    public static final ResourceKey<PlacedFeature> FLOWER_FOREST_FLOWERS = key(
            "flower_forest_flowers"
    );
    public static final ResourceKey<PlacedFeature> MILKWEED = key("milkweed");
    public static final ResourceKey<PlacedFeature> MILKWEED_CYPRESS = key("milkweed_cypress");
    public static final ResourceKey<PlacedFeature> GLORY_OF_THE_SNOW = key("glory_of_the_snow");
	public static final ResourceKey<PlacedFeature> FLOWER_FLOWER_FIELD = key("flower_flower_field");
	public static final ResourceKey<PlacedFeature> TALL_FLOWER_FLOWER_FIELD = key("tall_flower_field_flowers");
	public static final ResourceKey<PlacedFeature> PRICKLY_PEAR = key("prickly_pear");

    //VEGETATION
    public static final ResourceKey<PlacedFeature> POLLEN = key("pollen");
    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL = key("cattail");
    public static final ResourceKey<PlacedFeature> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");
    public static final ResourceKey<PlacedFeature> PATCH_ALGAE = key("patch_algae");
    public static final ResourceKey<PlacedFeature> PATCH_ALGAE_5 = key("patch_algae_5");
	public static final ResourceKey<PlacedFeature> TUMBLEWEED = key("tumbleweed");
    public static final ResourceKey<PlacedFeature> PATCH_BERRY_FOREST = key("patch_berry_forest");
    public static final ResourceKey<PlacedFeature> TERMITE = key("termite");
	public static final ResourceKey<PlacedFeature> BADLANDS_TALL_CACTUS_PLACED = key("badlands_tall_cactus_placed");
	public static final ResourceKey<PlacedFeature> BADLANDS_BUSH_SAND_PLACED = key("badlands_bush_sand_placed");
	public static final ResourceKey<PlacedFeature> BADLANDS_BUSH_TERRACOTTA_PLACED = key("badlands_bush_terracotta_placed");

	//JELLYFISH CAVES
    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_BLUE_MESOGLEA = key("blue_mesoglea");
    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");
    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("purple_mesoglea");
    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA = key("upside_down_purple_mesoglea");
	public static final ResourceKey<PlacedFeature> MESOGLEA_CLUSTER_BLUE = key("mesoglea_cluster_blue");
	public static final ResourceKey<PlacedFeature> MESOGLEA_CLUSTER_PURPLE = key("mesoglea_cluster_purple");
	public static final ResourceKey<PlacedFeature> LARGE_MESOGLEA_BLUE = key("large_mesoglea_blue");
	public static final ResourceKey<PlacedFeature> LARGE_MESOGLEA_PURPLE = key("large_mesoglea_purple");
    public static final ResourceKey<PlacedFeature> NEMATOCYST_BLUE = key("nematocyst_blue");
    public static final ResourceKey<PlacedFeature> NEMATOCYST_PURPLE = key("nematocyst_purple");
	public static final ResourceKey<PlacedFeature> SMALL_SPONGES = key("small_sponges");
	public static final ResourceKey<PlacedFeature> SMALL_SPONGES_RARE = key("small_sponges_rare");

	public static void init() {
    }

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, WilderSharedConstants.id(path));
	}
}
