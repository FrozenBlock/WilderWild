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

public final class WWVegetationFeatureBiomeTags {
	// LEAF LITTER
	public static final TagKey<Biome> LEAF_LITTER = bind("leaf_litter");
	public static final TagKey<Biome> LEAF_LITTER_UNCOMMON = bind("leaf_litter_uncommon");
	public static final TagKey<Biome> SPRUCE_LEAF_LITTER = bind("spruce_leaf_litter");
	public static final TagKey<Biome> DARK_OAK_LEAF_LITTER = bind("dark_oak_leaf_litter");
	public static final TagKey<Biome> PALE_OAK_LEAF_LITTER = bind("pale_oak_leaf_litter");
	// GRASS
	public static final TagKey<Biome> GRASS_RARE = bind("grass_rare");
	public static final TagKey<Biome> GRASS_FOREST = bind("grass_forest");
	public static final TagKey<Biome> GRASS_FOREST_LIGHT = bind("grass_forest_light");
	public static final TagKey<Biome> GRASS_BIRCH_FOREST = bind("grass_birch_forest");
	public static final TagKey<Biome> TALL_GRASS_DENSE = bind("tall_grass_dense");
	public static final TagKey<Biome> TALL_GRASS_FLOWER_FIELD = bind("tall_grass_flower_field");
	public static final TagKey<Biome> TALL_GRASS_SWAMP = bind("tall_grass_swamp");
	public static final TagKey<Biome> GRASS_WATER = bind("grass_water");
	public static final TagKey<Biome> LARGE_FERN_AND_GRASS = bind("large_fern_and_grass");
	public static final TagKey<Biome> LARGE_FERN_AND_GRASS_RARE = bind("large_fern_and_grass_rare");
	public static final TagKey<Biome> FERN_DENSE = bind("fern_dense");
	public static final TagKey<Biome> FERN_SWAMP = bind("fern_swamp");
	public static final TagKey<Biome> CLOVERS = bind("clovers");
	public static final TagKey<Biome> CLOVERS_SPARSE = bind("clovers_sparse");
	// DRY GRASS
	public static final TagKey<Biome> DRY_GRASS_DESERT = bind("dry_grass_desert");
	public static final TagKey<Biome> DRY_GRASS_BADLANDS = bind("dry_grass_badlands");
	public static final TagKey<Biome> DRY_GRASS_BEACH = bind("dry_grass_beach");
	public static final TagKey<Biome> DRY_GRASS_BETA_BEACH = bind("dry_grass_beta_beach");
	// FROZEN GRASS
	public static final TagKey<Biome> FROZEN_GRASS_PLAIN = bind("frozen_grass_plain");
	public static final TagKey<Biome> FROZEN_GRASS_FOREST = bind("frozen_grass_forest");
	public static final TagKey<Biome> FROZEN_GRASS_NORMAL = bind("frozen_grass_normal");
	public static final TagKey<Biome> FROZEN_GRASS_TAIGA = bind("frozen_grass_taiga");
	public static final TagKey<Biome> FROZEN_GRASS_TAIGA_2 = bind("frozen_grass_taiga_2");
	public static final TagKey<Biome> FROZEN_TALL_GRASS_PLAIN = bind("frozen_tall_grass_plain");
	public static final TagKey<Biome> FROZEN_BUSH = bind("frozen_bush");
	public static final TagKey<Biome> FROZEN_LARGE_FERN = bind("frozen_large_fern");
	// FIREFLY BUSH
	public static final TagKey<Biome> FIREFLY_BUSH = bind("firefly_bush");
	public static final TagKey<Biome> FIREFLY_BUSH_SWAMP = bind("firefly_bush_swamp");
	// SHRUB
	public static final TagKey<Biome> SHRUB = bind("shrub");
	public static final TagKey<Biome> SHRUB_SPARSE = bind("shrub_sparse");
	public static final TagKey<Biome> SHRUB_FLOWER_FIELD = bind("shrub_flower_field");
	public static final TagKey<Biome> SHRUB_JUNGLE = bind("shrub_jungle");
	public static final TagKey<Biome> SHRUB_OASIS = bind("shrub_oasis");
	public static final TagKey<Biome> SHRUB_DESERT = bind("shrub_desert");
	public static final TagKey<Biome> SHRUB_ARID = bind("shrub_arid");
	public static final TagKey<Biome> SHRUB_BADLANDS_SAND = bind("shrub_badlands_sand");
	public static final TagKey<Biome> SHRUB_BADLANDS_SAND_RARE = bind("shrub_badlands_sand_rare");
	public static final TagKey<Biome> SHRUB_BADLANDS_TERRACOTTA = bind("shrub_badlands_terracotta");
	public static final TagKey<Biome> SHRUB_WOODED_BADLANDS = bind("shrub_wooded_badlands");
	// MUSHROOM
	public static final TagKey<Biome> BROWN_MUSHROOM_COMMON = bind("brown_mushroom_common");
	public static final TagKey<Biome> RED_MUSHROOM_COMMON = bind("red_mushroom_common");
	public static final TagKey<Biome> PALE_MUSHROOM = bind("pale_mushroom");
	public static final TagKey<Biome> HUGE_PALE_MUSHROOM = bind("huge_pale_mushroom");
	public static final TagKey<Biome> PATCH_MUSHROOM_DARK_FOREST = bind("patch_mushroom_dark_forest");
	public static final TagKey<Biome> HUGE_BROWN_MUSHROOM = bind("huge_brown_mushroom");
	public static final TagKey<Biome> HUGE_RED_MUSHROOM = bind("huge_red_mushroom");
	public static final TagKey<Biome> HUGE_MUSHROOM = bind("huge_mushroom");
	public static final TagKey<Biome> HUGE_MUSHROOM_SWAMP = bind("huge_mushroom_swamp");
	public static final TagKey<Biome> HUGE_MUSHROOM_RARE = bind("huge_mushroom_rare");
	public static final TagKey<Biome> HUGE_MUSHROOM_MIXED_FOREST = bind("huge_mushroom_mixed_forest");
	public static final TagKey<Biome> HUGE_MUSHROOM_RAINFOREST = bind("huge_mushroom_rainforest");
	public static final TagKey<Biome> MYCELIUM_GROWTH = bind("mycelium_growth");
	// CACTUS
	public static final TagKey<Biome> CACTUS_TALL = bind("cactus_tall");
	public static final TagKey<Biome> CACTUS_TALL_BADLANDS = bind("cactus_tall_badlands");
	public static final TagKey<Biome> PRICKLY_PEAR = bind("prickly_pear");
	public static final TagKey<Biome> PRICKLY_PEAR_RARE = bind("prickly_pear_rare");
	// TUMBLEWEED
	public static final TagKey<Biome> TUMBLEWEED = bind("tumbleweed");
	// POLLEN
	public static final TagKey<Biome> POLLEN = bind("pollen");
	// MOSS
	public static final TagKey<Biome> PATH_MOSS = bind("path_moss");
	public static final TagKey<Biome> MOSS_CARPET = bind("moss_carpet");
	// AUBURN MOSS
	public static final TagKey<Biome> AUBURN_MOSS = bind("auburn_moss");
	public static final TagKey<Biome> AUBURN_CREEPING_MOSS = bind("auburn_creeping_moss");
	// BERRY
	public static final TagKey<Biome> BERRY_FOREST = bind("berry_forest");
	// MELON
	public static final TagKey<Biome> PUMPKIN_COMMON = bind("pumpkin_common");
	public static final TagKey<Biome> PUMPKIN_UNCOMMON = bind("pumpkin_uncommon");
	// MISC
	public static final TagKey<Biome> HAS_TERMITE_MOUND = bind("termite_mound");

	private static TagKey<Biome> bind(String name) {
		return TagKey.create(Registries.BIOME, WWConstants.id("feature/vegetation/" + name));
	}

	private WWVegetationFeatureBiomeTags() {}
}
