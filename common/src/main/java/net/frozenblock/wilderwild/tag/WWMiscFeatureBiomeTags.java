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

import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

@UtilityClass
public final class WWMiscFeatureBiomeTags {
	// TERMITE MOUND
	public static final TagKey<Biome> TERMITE_MOUND = bind("termite_mound");
	// COARSE DIRT
	public static final TagKey<Biome> COARSE_DIRT_BLOB_AND_PATCH = bind("coarse_dirt_blob_and_patch");
	public static final TagKey<Biome> COARSE_DIRT_BLOB_AND_PATCH_RARE = bind("coarse_dirt_blob_and_patch_rare");
	// PODZOL
	public static final TagKey<Biome> PODZOL_BASIN = bind("podzol_basin");
	// MUD
	public static final TagKey<Biome> MUD_BASIN = bind("mud_basin");
	public static final TagKey<Biome> MUD_LAKE = bind("mud_lake");
	public static final TagKey<Biome> MUD_TRANSITION = bind("mud_transition");
	// MOSS
	public static final TagKey<Biome> MOSS_BLOB = bind("moss_blob");
	public static final TagKey<Biome> MOSS_BASIN = bind("moss_basin");
	public static final TagKey<Biome> MOSS_LAKE = bind("moss_lake");
	public static final TagKey<Biome> MOSS_LAKE_RARE = bind("moss_lake_rare");
	// PALE MOSS
	public static final TagKey<Biome> PALE_MOSS_BLOB = bind("pale_moss_blob");
	// GRAVEl
	public static final TagKey<Biome> GRAVEL_TRANSITION = bind("gravel_transition");
	public static final TagKey<Biome> BETA_BEACH_GRAVEL_TRANSITION = bind("beta_beach_gravel_transition");
	// STONE
	public static final TagKey<Biome> TAIGA_FOREST_ROCK_SPARSE = bind("taiga_forest_rock_sparse");
	public static final TagKey<Biome> STONE_TRANSITION = bind("stone_transition");
	// SAND
	public static final TagKey<Biome> SAND_TRANSITION = bind("sand_transition");
	public static final TagKey<Biome> SMALL_SAND_TRANSITION = bind("small_sand_transition");
	public static final TagKey<Biome> BETA_BEACH_SAND_TRANSITION = bind("beta_beach_sand_transition");
	// RED SAND
	public static final TagKey<Biome> RED_SAND_TRANSITION = bind("red_sand_transition");
	// SNOW
	public static final TagKey<Biome> SNOW_BLOB = bind("snow_blob");
	// ICE
	public static final TagKey<Biome> SURFACE_BRITTLE_ICE = bind("surface_brittle_ice");
	public static final TagKey<Biome> SURFACE_ICICLE = bind("surface_icicle");
	// GEOTHERMAL VENT
	public static final TagKey<Biome> NETHER_GEOTHERMAL_VENT = bind("nether_geothermal_vent");
	public static final TagKey<Biome> NETHER_LAVA_GEOTHERMAL_VENT = bind("nether_lava_geothermal_vent");

	private static TagKey<Biome> bind(String name) {
		return TagKey.create(Registries.BIOME, WWConstants.id("feature/misc/" + name));
	}
}
