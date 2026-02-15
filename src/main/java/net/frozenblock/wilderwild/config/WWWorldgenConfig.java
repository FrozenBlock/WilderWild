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

package net.frozenblock.wilderwild.config;

import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.entry.property.VisibilityPredicate;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;

public final class WWWorldgenConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(WWConstants.id("worldgen")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> BETA_BEACHES = CONFIG.entry("betaBeaches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SNOW_UNDER_MOUNTAINS = CONFIG.entry("snowUnderMountains", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> SNOW_BELOW_TREES = CONFIG.entryBuilder("snowBelowTrees", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> TERMITE_GENERATION = CONFIG.entryBuilder("termiteGen", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> NETHER_GEYSER_GENERATION = CONFIG.entryBuilder("netherGeyserGen", EntryType.BOOL, true).requireRestart().build();

	// BIOME GENERATION
	public static final ConfigEntry<Boolean> CYPRESS_WETLANDS_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateCypressWetlands", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MESOGLEA_CAVES_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateMesogleaCaves", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MIXED_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateMixedForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> OASIS_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateOasis", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WARM_RIVER_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateWarmRiver", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WARM_BEACH_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateWarmBeach", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BIRCH_TAIGA_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateBirchTaiga", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> OLD_GROWTH_BIRCH_TAIGA_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateOldGrowthBirchTaiga", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FLOWER_FIELD_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateFlowerField", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> ARID_SAVANNA_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateAridSavanna", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PARCHED_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateParchedForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> ARID_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateAridForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNOWY_OLD_GROWTH_PINE_TAIGA_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateSnowyOldGrowthPineTaiga", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BIRCH_JUNGLE_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateBirchJungle", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SPARSE_BIRCH_JUNGLE_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateSparseBirchJungle", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> OLD_GROWTH_DARK_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateOldGrowthDarkForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DARK_BIRCH_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateDarkBirchForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SEMI_BIRCH_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateSemiBirchForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> TEMPERATE_RAINFOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateTemperateRainforest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> RAINFOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateRainforest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DARK_TAIGA_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateDarkTaiga", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DYING_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateDyingForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNOWY_DYING_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateSnowyDyingForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DYING_MIXED_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateDyingMixedForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNOWY_DYING_MIXED_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateSnowyDyingMixedForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MAGMATIC_CAVES_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateMagmaticCaves", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FROZEN_CAVES_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateFrozenCaves", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MAPLE_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateMapleForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SPARSE_FOREST_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateSparseForest", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> TUNDRA_GENERATION = CONFIG.entryBuilder("biomeGeneration/generateTundra", EntryType.BOOL, true).requireRestart().build();

	// BIOME PLACEMENT
	public static final ConfigEntry<Boolean> WINDSWEPT_SAVANNA_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifyWindsweptSavannaPlacement", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> JUNGLE_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifyJunglePlacement", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> SWAMP_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifySwampPlacement", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> MANGROVE_SWAMP_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifyMangroveSwampPlacement", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> CHERRY_GROVE_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifyCherryGrovePlacement", EntryType.BOOL, false)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> STONY_SHORE_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifyStonyShorePlacement", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> TUNDRA_MODIFIED_PLACEMENT = CONFIG.entryBuilder("biomePlacement/modifyTundraPlacement", EntryType.BOOL, false)
		.visibilityPredicate(VisibilityPredicate.of(WWWorldgenConfig.TUNDRA_GENERATION))
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("biome_placement.tundra." + bool))
		.build();

	// TREE GENERATION
	public static final ConfigEntry<Boolean> TREE_GENERATION = CONFIG.entryBuilder("treeGeneration/treeGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FALLEN_TREE_GENERATION = CONFIG.entryBuilder("treeGeneration/fallenTrees", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> HOLLOWED_FALLEN_TREE_GENERATION = CONFIG.entry("treeGeneration/hollowedFallenTrees", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SNAPPED_TREE_GENERATION = CONFIG.entryBuilder("treeGeneration/snappedTrees", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BAOBAB_TREE_GENERATION = CONFIG.entryBuilder("treeGeneration/baobab", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PALM_TREE_GENERATION = CONFIG.entryBuilder("treeGeneration/palm", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILLOW_TREE_GENERATION = CONFIG.entryBuilder("treeGeneration/willow", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> NEW_MAPLE_TREE_GENERATION = CONFIG.entry("treeGeneration/newMaples", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> BIRCH_BRANCH_GENERATION = CONFIG.entry("treeGeneration/birchBranches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> OAK_BRANCH_GENERATION = CONFIG.entry("treeGeneration/oakBranches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> DARK_OAK_BRANCH_GENERATION = CONFIG.entry("treeGeneration/darkOakBranches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> PALE_OAK_BRANCH_GENERATION = CONFIG.entry("treeGeneration/paleOakBranches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> ACACIA_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/acaciaLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> AZALEA_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/azaleaLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BAOBAB_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/baobabLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BIRCH_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/birchLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> CHERRY_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/cherryLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> CYPRESS_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/cypressLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> JUNGLE_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/jungleLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MANGROVE_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/mangroveLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> OAK_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/oakLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DARK_OAK_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/darkOakLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PALE_OAK_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/paleOakLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PALM_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/palmFrondLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SPRUCE_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/spruceLeafLitter", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILLOW_LITTER_GENERATION = CONFIG.entryBuilder("treeGeneration/willowLeafLitter", EntryType.BOOL, true).requireRestart().build();

	// VEGETATION GENERATION
	public static final ConfigEntry<Boolean> GRASS_GENERATION = CONFIG.entryBuilder("vegetation/grassGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DRY_GRASS_GENERATION = CONFIG.entryBuilder("vegetation/dryGrassGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FLOWER_GENERATION = CONFIG.entryBuilder("vegetation/flowerGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SHRUB_GENERATION = CONFIG.entryBuilder("vegetation/shrubGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> CACTUS_GENERATION = CONFIG.entryBuilder("vegetation/cactusGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SHELF_FUNGI_GENERATION = CONFIG.entryBuilder("vegetation/shelfFungiGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MUSHROOM_GENERATION = CONFIG.entryBuilder("vegetation/mushroomGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PALE_MUSHROOM_GENERATION = CONFIG.entryBuilder("vegetation/paleMushroomGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> POLLEN_GENERATION = CONFIG.entryBuilder("vegetation/pollen", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> TUMBLEWEED_GENERATION = CONFIG.entryBuilder("vegetation/tumbleweed", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FIREFLY_BUSH_GENERATION = CONFIG.entryBuilder("vegetation/fireflyBushGen", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PUMPKIN_GENERATION = CONFIG.entryBuilder("vegetation/pumpkin", EntryType.BOOL, true).requireRestart().build();

	// SURFACE DECORATION
	public static final ConfigEntry<Boolean> COARSE_DIRT_DECORATION = CONFIG.entryBuilder("surfaceDecoration/coarseDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> GRAVEL_DECORATION = CONFIG.entryBuilder("surfaceDecoration/gravelDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MUD_DECORATION = CONFIG.entryBuilder("surfaceDecoration/mudDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PACKED_MUD_DECORATION = CONFIG.entryBuilder("surfaceDecoration/packedMudDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> STONE_DECORATION = CONFIG.entryBuilder("surfaceDecoration/stoneDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MOSS_DECORATION = CONFIG.entryBuilder("surfaceDecoration/mossDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> AUBURN_MOSS_DECORATION = CONFIG.entryBuilder("surfaceDecoration/auburnMossDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PALE_MOSS_DECORATION = CONFIG.entryBuilder("surfaceDecoration/paleMossDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SCORCHED_SAND_DECORATION = CONFIG.entryBuilder("surfaceDecoration/scorchedSandDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SCORCHED_RED_SAND_DECORATION = CONFIG.entryBuilder("surfaceDecoration/scorchedRedSandDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SANDSTONE_DECORATION = CONFIG.entryBuilder("surfaceDecoration/sandstoneDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> CLAY_DECORATION = CONFIG.entryBuilder("surfaceDecoration/clayDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> CLEARING_DECORATION = CONFIG.entryBuilder("surfaceDecoration/clearingDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNOW_PILE_DECORATION = CONFIG.entryBuilder("surfaceDecoration/snowPiles", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FRAGILE_ICE_DECORATION = CONFIG.entryBuilder("surfaceDecoration/fragileIceDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> ICICLE_DECORATION = CONFIG.entryBuilder("surfaceDecoration/icicleDecoration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> TAIGA_BOULDER_DECORATION = CONFIG.entryBuilder("surfaceDecoration/taigaBoulders", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> LAKE_DECORATION = CONFIG.entryBuilder("surfaceDecoration/lakes", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BASIN_DECORATION = CONFIG.entryBuilder("surfaceDecoration/basins", EntryType.BOOL, true).requireRestart().build();

	// AQUATIC GENERATION
	public static final ConfigEntry<Boolean> RIVER_POOL_GENERATION = CONFIG.entryBuilder("aquaticGeneration/riverPool", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> CATTAIL_GENERATION = CONFIG.entryBuilder("aquaticGeneration/cattail", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> ALGAE_GENERATION = CONFIG.entryBuilder("aquaticGeneration/algae", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> PLANKTON_GENERATION = CONFIG.entryBuilder("aquaticGeneration/plankton", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SEAGRASS_GENERATION = CONFIG.entryBuilder("aquaticGeneration/seagrass", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SPONGE_BUD_GENERATION = CONFIG.entryBuilder("aquaticGeneration/spongeBud", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> BARNACLES_GENERATION = CONFIG.entryBuilder("aquaticGeneration/barnacle", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SEA_ANEMONE_GENERATION = CONFIG.entryBuilder("aquaticGeneration/seaAnemone", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SEA_WHIP_GENERATION = CONFIG.entryBuilder("aquaticGeneration/seaWhip", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> TUBE_WORMS_GENERATION = CONFIG.entryBuilder("aquaticGeneration/tubeWorm", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> HYDROTHERMAL_VENT_GENERATION = CONFIG.entryBuilder("aquaticGeneration/hydrothermalVent", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> OCEAN_MOSS_GENERATION = CONFIG.entryBuilder("aquaticGeneration/oceanMossGeneration", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> OCEAN_AUBURN_MOSS_GENERATION = CONFIG.entryBuilder("aquaticGeneration/oceanAuburnMossGeneration", EntryType.BOOL, true).requireRestart().build();

	// SURFACE TRANSITIONS
	public static final ConfigEntry<Boolean> SAND_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/sandTransitions", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> RED_SAND_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/redSandTransitions", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> COARSE_DIRT_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/coarseTransitions", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> GRAVEL_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/gravelTransitions", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MUD_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/mudTransitions", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> STONE_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/stoneTransitions", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNOW_TRANSITION_GENERATION = CONFIG.entryBuilder("transitionGeneration/snowTransitions", EntryType.BOOL, true).requireRestart().build();

	// STRUCTURE GENERATION
	public static final ConfigEntry<Boolean> NEW_WITCH_HUT_GENERATION = CONFIG.entryBuilder("structure/newWitchHuts", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DECAYING_TRAIL_RUINS_GENERATION = CONFIG.entryBuilder("structure/decayTrailRuins", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> NEW_DESERT_VILLAGE_GENERATION = CONFIG.entryBuilder("structure/newDesertVillages", EntryType.BOOL, true).requireRestart().build();
}
