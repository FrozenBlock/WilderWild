/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class WWTreeFeatureBiomeTags {
	public static final TagKey<Biome> TREE_PLAINS = bind("tree_plains");
	public static final TagKey<Biome> TREE_SNOWY = bind("tree_snowy");
	public static final TagKey<Biome> TREE_FOREST = bind("tree_forest");
	public static final TagKey<Biome> TREE_FLOWER_FOREST = bind("tree_flower_forest");
	public static final TagKey<Biome> TREE_BIRCH_FOREST = bind("tree_birch_forest");
	public static final TagKey<Biome> TREE_OLD_GROWTH_BIRCH_FOREST = bind("tree_old_growth_birch_forest");
	public static final TagKey<Biome> TREE_TAIGA = bind("tree_taiga");
	public static final TagKey<Biome> TREE_TAIGA_SNOWY = bind("tree_taiga_snowy");
	public static final TagKey<Biome> TREE_TALL_PINE_TAIGA = bind("tree_tall_pine_taiga");
	public static final TagKey<Biome> TREE_TALL_SPRUCE_TAIGA = bind("tree_tall_spruce_taiga");
	public static final TagKey<Biome> TREE_JUNGLE = bind("tree_jungle");
	public static final TagKey<Biome> TREE_SPARSE_JUNGLE = bind("tree_sparse_jungle");
	public static final TagKey<Biome> TREE_BAMBOO_JUNGLE = bind("tree_bamboo_jungle");
	public static final TagKey<Biome> TREE_SAVANNA = bind("tree_savanna");
	public static final TagKey<Biome> TREE_WINDSWEPT_SAVANNA = bind("tree_windswept_savanna");
	public static final TagKey<Biome> TREE_DARK_FOREST = bind("tree_dark_forest");
	public static final TagKey<Biome> TREE_PALE_GARDEN = bind("tree_pale_garden");
	public static final TagKey<Biome> TREE_SWAMP = bind("tree_swamp");
	public static final TagKey<Biome> TREE_MANGROVE_SWAMP = bind("tree_mangrove_swamp");
	public static final TagKey<Biome> TREE_WOODED_BADLANDS = bind("tree_wooded_badlands");
	public static final TagKey<Biome> TREE_CHERRY_GROVE = bind("tree_cherry_grove");
	public static final TagKey<Biome> TREE_DAPPLED_FOREST = bind("tree_dappled_forest");
	public static final TagKey<Biome> TREE_MEADOW = bind("tree_meadow");
	public static final TagKey<Biome> TREE_GROVE = bind("tree_grove");
	public static final TagKey<Biome> TREE_WINDSWEPT_FOREST = bind("tree_windswept_forest");
	public static final TagKey<Biome> TREE_WINDSWEPT_HILLS = bind("tree_windswept_hills");
	public static final TagKey<Biome> TREE_WATER = bind("tree_water");

	// ADDITIONS
	public static final TagKey<Biome> SHORT_SPRUCE = bind("short_spruce");
	public static final TagKey<Biome> SHORT_SPRUCE_SPARSE_LEAF_LITTER = bind("short_spruce_sparse_leaf_litter");
	public static final TagKey<Biome> SHORT_MEGA_SPRUCE = bind("short_mega_spruce");
	public static final TagKey<Biome> SHORT_MEGA_SPRUCE_ON_SNOW = bind("short_mega_spruce_on_snow");

	public static final TagKey<Biome> BIG_BUSH_FOREST = bind("big_bush_forest");
	public static final TagKey<Biome> BIG_BUSH_FOREST_COMMON = bind("big_bush_forest_common");
	public static final TagKey<Biome> BIG_BUSH = bind("big_bush");
	public static final TagKey<Biome> BIG_BUSH_ON_SAND = bind("big_bush_on_sand");

	// PALM
	public static final TagKey<Biome> PALM = bind("palm");
	public static final TagKey<Biome> PALM_JUNGLE = bind("palm_jungle");
	public static final TagKey<Biome> PALM_RARE = bind("palm_rare");
	public static final TagKey<Biome> PALM_WARM_BEACH = bind("palm_warm_beach");
	public static final TagKey<Biome> PALM_OASIS = bind("palm_oasis");

	// FALLEN
	public static final TagKey<Biome> MOSSY_FALLEN_OAK_AND_BIRCH = bind("mossy_fallen_oak_and_birch");
	public static final TagKey<Biome> FALLEN_OAK_AND_BIRCH_DARK_FOREST = bind("fallen_oak_and_birch_dark_forest");
	public static final TagKey<Biome> FALLEN_BIRCH = bind("fallen_birch");
	public static final TagKey<Biome> FALLEN_BIRCH_AND_OAK = bind("fallen_birch_and_oak");
	public static final TagKey<Biome> FALLEN_BIRCH_AND_OAK_SPARSE = bind("fallen_birch_and_oak_sparse");
	public static final TagKey<Biome> FALLEN_BIRCH_AND_SPRUCE = bind("fallen_birch_and_spruce");
	public static final TagKey<Biome> FALLEN_OAK_AND_BIRCH_AND_SPRUCE = bind("fallen_oak_and_birch_and_spruce");
	public static final TagKey<Biome> MOSSY_FALLEN_OAK_AND_BIRCH_AND_SPRUCE = bind("mossy_fallen_oak_and_birch_and_spruce");
	public static final TagKey<Biome> FALLEN_SPRUCE_AND_OAK = bind("fallen_spruce_and_oak");
	public static final TagKey<Biome> FALLEN_SPRUCE = bind("fallen_spruce");
	public static final TagKey<Biome> CLEAN_FALLEN_SPRUCE = bind("clean_fallen_spruce");
	public static final TagKey<Biome> LARGE_FALLEN_SPRUCE = bind("large_fallen_spruce");
	public static final TagKey<Biome> LARGE_FALLEN_SPRUCE_COMMON = bind("large_fallen_spruce_common");
	public static final TagKey<Biome> CLEAN_LARGE_FALLEN_SPRUCE = bind("clean_large_fallen_spruce");
	public static final TagKey<Biome> CLEAN_LARGE_FALLEN_SPRUCE_COMMON = bind("clean_large_fallen_spruce_common");
	public static final TagKey<Biome> FALLEN_DARK_OAK = bind("fallen_dark_oak");
	public static final TagKey<Biome> FALLEN_DARK_OAK_COMMON = bind("fallen_dark_oak_common");
	public static final TagKey<Biome> FALLEN_PALE_OAK = bind("fallen_pale_oak");
	public static final TagKey<Biome> LARGE_FALLEN_JUNGLE = bind("fallen_large_jungle");
	public static final TagKey<Biome> LARGE_FALLEN_JUNGLE_COMMON = bind("large_fallen_jungle_common");
	public static final TagKey<Biome> FALLEN_ACACIA_AND_OAK = bind("fallen_acacia_and_oak");
	public static final TagKey<Biome> FALLEN_SWAMP = bind("fallen_swamp");
	public static final TagKey<Biome> FALLEN_MANGROVE = bind("fallen_mangrove");
	public static final TagKey<Biome> FALLEN_CYPRESS_AND_OAK = bind("fallen_cypress_and_oak");
	public static final TagKey<Biome> FALLEN_PALM = bind("fallen_palm");
	public static final TagKey<Biome> FALLEN_PALM_RARE = bind("fallen_palm_rare");
	public static final TagKey<Biome> FALLEN_PALM_AND_JUNGLE_AND_OAK = bind("fallen_palm_and_jungle_and_oak");
	public static final TagKey<Biome> FALLEN_CHERRY = bind("fallen_cherry");
	public static final TagKey<Biome> FALLEN_MAPLE = bind("fallen_maple");
	public static final TagKey<Biome> FALLEN_POPLAR = bind("fallen_poplar");
	public static final TagKey<Biome> FALLEN_CRIMSON_FUNGI = bind("fallen_crimson_fungi");
	public static final TagKey<Biome> FALLEN_WARPED_FUNGI = bind("fallen_warped_fungi");

	// SNAPPED
	public static final TagKey<Biome> SNAPPED_OAK = bind("snapped_oak");
	public static final TagKey<Biome> SNAPPED_BIRCH = bind("snapped_birch");
	public static final TagKey<Biome> SNAPPED_BIRCH_AND_OAK = bind("snapped_birch_and_oak");
	public static final TagKey<Biome> SNAPPED_BIRCH_AND_SPRUCE = bind("snapped_birch_and_spruce");
	public static final TagKey<Biome> SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = bind("snapped_birch_and_oak_and_spruce");
	public static final TagKey<Biome> SNAPPED_BIRCH_AND_JUNGLE = bind("snapped_birch_and_jungle");
	public static final TagKey<Biome> SNAPPED_SPRUCE = bind("snapped_spruce");
	public static final TagKey<Biome> SNAPPED_SPRUCE_ON_SNOW = bind("snapped_spruce_on_snow");
	public static final TagKey<Biome> SNAPPED_LARGE_SPRUCE = bind("snapped_large_spruce");
	public static final TagKey<Biome> SNAPPED_LARGE_SPRUCE_COMMON = bind("snapped_large_spruce_common");
	public static final TagKey<Biome> SNAPPED_LARGE_SPRUCE_ON_SNOW = bind("snapped_large_spruce_on_snow");
	public static final TagKey<Biome> SNAPPED_DARK_OAK = bind("snapped_dark_oak");
	public static final TagKey<Biome> SNAPPED_PALE_OAK = bind("snapped_pale_oak");
	public static final TagKey<Biome> SNAPPED_JUNGLE = bind("snapped_jungle");
	public static final TagKey<Biome> SNAPPED_LARGE_JUNGLE = bind("snapped_large_jungle");
	public static final TagKey<Biome> SNAPPED_ACACIA = bind("snapped_acacia");
	public static final TagKey<Biome> SNAPPED_ACACIA_AND_OAK = bind("snapped_acacia_and_oak");
	public static final TagKey<Biome> SNAPPED_CYPRESS = bind("snapped_cypress");
	public static final TagKey<Biome> SNAPPED_CHERRY = bind("snapped_cherry");
	public static final TagKey<Biome> SNAPPED_MAPLE = bind("snapped_maple");
	public static final TagKey<Biome> SNAPPED_POPLAR = bind("snapped_poplar");
	public static final TagKey<Biome> SNAPPED_CRIMSON_FUNGI = bind("snapped_crimson_fungi");
	public static final TagKey<Biome> SNAPPED_WARPED_FUNGI = bind("snapped_warped_fungi");

	private static TagKey<Biome> bind(String name) {
		return TagKey.create(Registries.BIOME, WWConstants.id("feature/tree/" + name));
	}

	private WWTreeFeatureBiomeTags() {}
}
