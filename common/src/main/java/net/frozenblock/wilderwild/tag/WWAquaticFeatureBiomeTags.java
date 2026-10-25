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

public final class WWAquaticFeatureBiomeTags {
	// SURFACE
	// FLOWERING WATER LILY
	public static final TagKey<Biome> FLOWERING_WATER_LILY = bind("flowering_water_lily");
	// CATTAIL
	public static final TagKey<Biome> CATTAIL = bind("cattail");
	public static final TagKey<Biome> CATTAIL_COMMON = bind("cattail_common");
	public static final TagKey<Biome> CATTAIL_UNCOMMON = bind("cattail_uncommon");
	// ALGAE
	public static final TagKey<Biome> ALGAE = bind("algae");
	public static final TagKey<Biome> ALGAE_SMALL = bind("algae_small");
	// PLANKTON
	public static final TagKey<Biome> PLANKTON = bind("plankton");
	// POOL
	public static final TagKey<Biome> WATER_POOLS = bind("water_pools");

	// VEGETATION
	// SEAGRASS
	public static final TagKey<Biome> SEAGRASS_MEADOW = bind("seagrass_meadow");
	// BARNACLES
	public static final TagKey<Biome> BARNACLES = bind("barnacles");
	public static final TagKey<Biome> BARNACLES_COMMON = bind("barnacles_common");
	public static final TagKey<Biome> BARNACLES_SPARSE = bind("barnacles_sparse");
	public static final TagKey<Biome> BARNACLES_RARE = bind("barnacles_rare");
	public static final TagKey<Biome> BARNACLES_STRUCTURE = bind("barnacles_structure");
	// SEA ANEMONE
	public static final TagKey<Biome> SEA_ANEMONE = bind("sea_anemone");
	public static final TagKey<Biome> SEA_ANEMONE_SPARSE = bind("sea_anemone_sparse");
	public static final TagKey<Biome> SEA_ANEMONE_RARE = bind("sea_anemone_rare");
	// SEA WHIP
	public static final TagKey<Biome> SEA_WHIP = bind("sea_whip");
	public static final TagKey<Biome> SEA_WHIP_SPARSE = bind("sea_whip_sparse");
	public static final TagKey<Biome> SEA_WHIP_RARE = bind("sea_whip_rare");
	// SPONGE BUD
	public static final TagKey<Biome> SPONGE_BUD = bind("sponge_bud");
	public static final TagKey<Biome> SPONGE_BUD_RARE = bind("sponge_bud_rare");
	// TUBE WORMS
	public static final TagKey<Biome> TUBE_WORMS = bind("tube_worms");
	public static final TagKey<Biome> TUBE_WORMS_RARE = bind("tube_worms_rare");
	// MOSS
	public static final TagKey<Biome> AUBURN_CREEPING_MOSS_UNDERWATER = bind("auburn_creeping_moss_underwater");
	public static final TagKey<Biome> AUBURN_MOSS_UNDERWATER = bind("auburn_moss_underwater");
	public static final TagKey<Biome> AUBURN_MOSS_UNDERWATER_RARE = bind("auburn_moss_underwater_rare");
	// MISC
	public static final TagKey<Biome> HYDROTHERMAL_VENT = bind("hydrothermal_vent");
	public static final TagKey<Biome> HYDROTHERMAL_VENT_RARE = bind("hydrothermal_vent_rare");

	private static TagKey<Biome> bind(String name) {
		return TagKey.create(Registries.BIOME, WWConstants.id("feature/aquatic/" + name));
	}

	private WWAquaticFeatureBiomeTags() {}
}
