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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import static net.frozenblock.wilderwild.config.gui.WWConfigGuiHelper.*;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

@Environment(EnvType.CLIENT)
public final class WWWorldgenConfigGui {

	private WWWorldgenConfigGui() {
		throw new UnsupportedOperationException("WWWorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "beta_beaches", WWWorldgenConfig.BETA_BEACHES));
		category.addEntry(booleanEntry(builder, "snow_under_mountains", WWWorldgenConfig.SNOW_UNDER_MOUNTAINS));

		// BIOME GENERATION
		var cypressWetlands = biomeGenerationBooleanEntry(builder, WWBiomes.CYPRESS_WETLANDS, WWWorldgenConfig.CYPRESS_WETLANDS_GENERATION);
		var mesogleaCaves = biomeGenerationBooleanEntry(builder, WWBiomes.MESOGLEA_CAVES, WWWorldgenConfig.MESOGLEA_CAVES_GENERATION);
		var mixedForest = biomeGenerationBooleanEntry(builder, WWBiomes.MIXED_FOREST, WWWorldgenConfig.MIXED_FOREST_GENERATION);
		var oasis = biomeGenerationBooleanEntry(builder, WWBiomes.OASIS, WWWorldgenConfig.OASIS_GENERATION);
		var warmRiver = biomeGenerationBooleanEntry(builder, WWBiomes.WARM_RIVER, WWWorldgenConfig.WARM_RIVER_GENERATION);
		var warmBeach = biomeGenerationBooleanEntry(builder, WWBiomes.WARM_BEACH, WWWorldgenConfig.WARM_BEACH_GENERATION);
		var birchTaiga = biomeGenerationBooleanEntry(builder, WWBiomes.BIRCH_TAIGA, WWWorldgenConfig.BIRCH_TAIGA_GENERATION);
		var oldGrowthBirchTaiga = biomeGenerationBooleanEntry(builder, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, WWWorldgenConfig.OLD_GROWTH_BIRCH_TAIGA_GENERATION);
		var flowerField = biomeGenerationBooleanEntry(builder, WWBiomes.FLOWER_FIELD, WWWorldgenConfig.FLOWER_FIELD_GENERATION);
		var aridSavanna = biomeGenerationBooleanEntry(builder, WWBiomes.ARID_SAVANNA, WWWorldgenConfig.ARID_SAVANNA_GENERATION);
		var parchedForest = biomeGenerationBooleanEntry(builder, WWBiomes.PARCHED_FOREST, WWWorldgenConfig.PARCHED_FOREST_GENERATION);
		var aridForest = biomeGenerationBooleanEntry(builder, WWBiomes.ARID_FOREST, WWWorldgenConfig.ARID_FOREST_GENERATION);
		var snowyOldGrowthPineTaiga = biomeGenerationBooleanEntry(builder, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, WWWorldgenConfig.SNOWY_OLD_GROWTH_PINE_TAIGA_GENERATION);
		var birchJungle = biomeGenerationBooleanEntry(builder, WWBiomes.BIRCH_JUNGLE, WWWorldgenConfig.BIRCH_JUNGLE_GENERATION);
		var sparseBirchJungle = biomeGenerationBooleanEntry(builder, WWBiomes.SPARSE_BIRCH_JUNGLE, WWWorldgenConfig.SPARSE_BIRCH_JUNGLE_GENERATION);
		var oldGrowthDarkForest = biomeGenerationBooleanEntry(builder, WWBiomes.OLD_GROWTH_DARK_FOREST, WWWorldgenConfig.OLD_GROWTH_DARK_FOREST_GENERATION);
		var darkBirchForest = biomeGenerationBooleanEntry(builder, WWBiomes.DARK_BIRCH_FOREST, WWWorldgenConfig.DARK_BIRCH_FOREST_GENERATION);
		var semiBirchForest = biomeGenerationBooleanEntry(builder, WWBiomes.SEMI_BIRCH_FOREST, WWWorldgenConfig.SEMI_BIRCH_FOREST_GENERATION);
		var sparseForest = biomeGenerationBooleanEntry(builder, WWBiomes.SPARSE_FOREST, WWWorldgenConfig.SPARSE_FOREST_GENERATION);
		var temperateRainforest = biomeGenerationBooleanEntry(builder, WWBiomes.TEMPERATE_RAINFOREST, WWWorldgenConfig.TEMPERATE_RAINFOREST_GENERATION);
		var rainforest = biomeGenerationBooleanEntry(builder, WWBiomes.RAINFOREST, WWWorldgenConfig.RAINFOREST_GENERATION);
		var darkTaiga = biomeGenerationBooleanEntry(builder, WWBiomes.DARK_TAIGA, WWWorldgenConfig.DARK_TAIGA_GENERATION);
		var dyingForest = biomeGenerationBooleanEntry(builder, WWBiomes.DYING_FOREST, WWWorldgenConfig.DYING_FOREST_GENERATION);
		var snowyDyingForest = biomeGenerationBooleanEntry(builder, WWBiomes.SNOWY_DYING_FOREST, WWWorldgenConfig.SNOWY_DYING_FOREST_GENERATION);
		var dyingMixedForest = biomeGenerationBooleanEntry(builder, WWBiomes.DYING_MIXED_FOREST, WWWorldgenConfig.DYING_MIXED_FOREST_GENERATION);
		var snowyDyingMixedForest = biomeGenerationBooleanEntry(builder, WWBiomes.SNOWY_DYING_MIXED_FOREST, WWWorldgenConfig.SNOWY_DYING_MIXED_FOREST_GENERATION);
		var magmaticCaves = biomeGenerationBooleanEntry(builder, WWBiomes.MAGMATIC_CAVES, WWWorldgenConfig.MAGMATIC_CAVES_GENERATION);
		var frozenCaves = biomeGenerationBooleanEntry(builder, WWBiomes.FROZEN_CAVES, WWWorldgenConfig.FROZEN_CAVES_GENERATION);
		var mapleForest = biomeGenerationBooleanEntry(builder, WWBiomes.MAPLE_FOREST, WWWorldgenConfig.MAPLE_FOREST_GENERATION);
		var tundra = biomeGenerationBooleanEntry(builder, WWBiomes.TUNDRA, WWWorldgenConfig.TUNDRA_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("biome_generation"),
			false,
			tooltip("biome_generation"),
			aridForest, aridSavanna, birchJungle, birchTaiga, cypressWetlands, darkBirchForest, darkTaiga, dyingForest, dyingMixedForest, flowerField,
			frozenCaves, magmaticCaves, mapleForest, mesogleaCaves, mixedForest, oasis, oldGrowthBirchTaiga, oldGrowthDarkForest, parchedForest, rainforest,
			semiBirchForest, snowyDyingForest, snowyDyingMixedForest, snowyOldGrowthPineTaiga, sparseBirchJungle, sparseForest, temperateRainforest, tundra,
			warmBeach, warmRiver
		);

		// BIOME PLACEMENT
		var cherryGrovePlacement = biomePlacementBooleanEntry(builder, Biomes.CHERRY_GROVE, WWWorldgenConfig.CHERRY_GROVE_MODIFIED_PLACEMENT);
		var junglePlacement = biomePlacementBooleanEntry(builder, Biomes.JUNGLE, WWWorldgenConfig.JUNGLE_MODIFIED_PLACEMENT);
		var mangroveSwampPlacement = biomePlacementBooleanEntry(builder, Biomes.MANGROVE_SWAMP, WWWorldgenConfig.MANGROVE_SWAMP_MODIFIED_PLACEMENT);
		var stonyShorePlacement = biomePlacementBooleanEntry(builder, Biomes.STONY_SHORE, WWWorldgenConfig.STONY_SHORE_MODIFIED_PLACEMENT);
		var tundraPlacement = biomePlacementBooleanEntry(builder, WWBiomes.TUNDRA, WWWorldgenConfig.TUNDRA_MODIFIED_PLACEMENT);
		var swampPlacement = biomePlacementBooleanEntry(builder, Biomes.SWAMP, WWWorldgenConfig.SWAMP_MODIFIED_PLACEMENT);
		var windsweptSavannaPlacement = biomePlacementBooleanEntry(builder, Biomes.WINDSWEPT_SAVANNA, WWWorldgenConfig.WINDSWEPT_SAVANNA_MODIFIED_PLACEMENT);

		FrozenClothConfig.createSubCategory(builder, category, text("biome_placement"),
			false,
			tooltip("biome_placement"),
			cherryGrovePlacement, junglePlacement, mangroveSwampPlacement, stonyShorePlacement, swampPlacement, windsweptSavannaPlacement, tundraPlacement
		);

		// TREE GENERATION
		var treeGeneration = booleanEntry(builder, "tree_generation", WWWorldgenConfig.TREE_GENERATION);
		var fallenTrees = booleanEntry(builder, "fallen_trees", WWWorldgenConfig.FALLEN_TREE_GENERATION);
		var hollowedFallenTrees = booleanEntry(builder, "hollowed_fallen_trees", WWWorldgenConfig.HOLLOWED_FALLEN_TREE_GENERATION);
		var snappedTrees = booleanEntry(builder, "snapped_trees", WWWorldgenConfig.SNAPPED_TREE_GENERATION);
		var baobabTrees = booleanEntry(builder, "baobab_generation", WWWorldgenConfig.BAOBAB_TREE_GENERATION);
		var palmTrees = booleanEntry(builder, "palm_generation", WWWorldgenConfig.PALM_TREE_GENERATION);
		var willowTrees = booleanEntry(builder, "willow_generation", WWWorldgenConfig.WILLOW_TREE_GENERATION);
		var newMapleTrees = booleanEntry(builder, "new_maples", WWWorldgenConfig.NEW_MAPLE_TREE_GENERATION);
		var birchBranches = booleanEntry(builder, "birch_branches", WWWorldgenConfig.BIRCH_BRANCH_GENERATION);
		var oakBranches = booleanEntry(builder, "oak_branches", WWWorldgenConfig.OAK_BRANCH_GENERATION);
		var darkOakBranches = booleanEntry(builder, "dark_oak_branches", WWWorldgenConfig.DARK_OAK_BRANCH_GENERATION);
		var paleOakBranches = booleanEntry(builder, "pale_oak_branches", WWWorldgenConfig.PALE_OAK_BRANCH_GENERATION);
		var acaciaLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.ACACIA_LEAF_LITTER, WWWorldgenConfig.ACACIA_LITTER_GENERATION);
		var azaleaLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.AZALEA_LEAF_LITTER, WWWorldgenConfig.AZALEA_LITTER_GENERATION);
		var baobabLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.BAOBAB_LEAF_LITTER, WWWorldgenConfig.BAOBAB_LITTER_GENERATION);
		var birchLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.BIRCH_LEAF_LITTER, WWWorldgenConfig.BIRCH_LITTER_GENERATION);
		var cherryLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.CHERRY_LEAF_LITTER, WWWorldgenConfig.CHERRY_LITTER_GENERATION);
		var cypressLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.CYPRESS_LEAF_LITTER, WWWorldgenConfig.CYPRESS_LITTER_GENERATION);
		var darkOakLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.DARK_OAK_LEAF_LITTER, WWWorldgenConfig.DARK_OAK_LITTER_GENERATION);
		var jungleLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.JUNGLE_LEAF_LITTER, WWWorldgenConfig.JUNGLE_LITTER_GENERATION);
		var mangroveLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.MANGROVE_LEAF_LITTER, WWWorldgenConfig.MANGROVE_LITTER_GENERATION);
		var oakLeafLitter = litterBlockGenerationBooleanEntry(builder, Blocks.LEAF_LITTER, WWWorldgenConfig.OAK_LITTER_GENERATION);
		var paleOakLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.PALE_OAK_LEAF_LITTER, WWWorldgenConfig.PALE_OAK_LITTER_GENERATION);
		var palmFrondLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.PALM_FROND_LITTER, WWWorldgenConfig.PALM_LITTER_GENERATION);
		var spruceLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.SPRUCE_LEAF_LITTER, WWWorldgenConfig.SPRUCE_LITTER_GENERATION);
		var willowLeafLitter = litterBlockGenerationBooleanEntry(builder, WWBlocks.WILLOW_LEAF_LITTER, WWWorldgenConfig.WILLOW_LITTER_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("tree_generation_category"),
			false,
			tooltip("tree_generation_category"),
			treeGeneration, fallenTrees, hollowedFallenTrees, snappedTrees,
			baobabTrees, palmTrees, willowTrees,
			newMapleTrees,
			birchBranches, oakBranches, darkOakBranches, paleOakBranches,
			acaciaLeafLitter, azaleaLeafLitter, baobabLeafLitter, birchLeafLitter, cherryLeafLitter, cypressLeafLitter, darkOakLeafLitter, jungleLeafLitter,
			mangroveLeafLitter, oakLeafLitter, paleOakLeafLitter, palmFrondLitter, spruceLeafLitter, willowLeafLitter
		);

		// VEGETATION GENERATION
		var shrubGeneration = booleanEntry(builder, "shrub_generation", WWWorldgenConfig.SHRUB_GENERATION);
		var cactusGeneration = booleanEntry(builder, "cactus_generation", WWWorldgenConfig.CACTUS_GENERATION);
		var flowerGeneration = booleanEntry(builder, "flower_generation", WWWorldgenConfig.FLOWER_GENERATION);
		var grassGeneration = booleanEntry(builder, "grass_generation", WWWorldgenConfig.GRASS_GENERATION);
		var dryGrassGeneration = booleanEntry(builder, "dry_grass_generation", WWWorldgenConfig.DRY_GRASS_GENERATION);
		var shelfFungiGeneration = booleanEntry(builder, "shelf_fungi_generation", WWWorldgenConfig.SHELF_FUNGI_GENERATION);
		var mushroomGeneration = booleanEntry(builder, "mushroom_generation", WWWorldgenConfig.MUSHROOM_GENERATION);
		var paleMushroomGeneration = booleanEntry(builder, "pale_mushroom_generation", WWWorldgenConfig.PALE_MUSHROOM_GENERATION);
		var pollen = booleanEntry(builder, "pollen_generation", WWWorldgenConfig.POLLEN_GENERATION);
		var tumbleweed = booleanEntry(builder, "tumbleweed_generation", WWWorldgenConfig.TUMBLEWEED_GENERATION);
		var fireflyBushGen = booleanEntry(builder, "firefly_bush_generation", WWWorldgenConfig.FIREFLY_BUSH_GENERATION);
		var pumpkin = booleanEntry(builder, "pumpkin_generation", WWWorldgenConfig.PUMPKIN_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("vegetation"),
			false,
			tooltip("vegetation"),
			grassGeneration, dryGrassGeneration, flowerGeneration, shrubGeneration, cactusGeneration, shelfFungiGeneration, mushroomGeneration, paleMushroomGeneration,
			fireflyBushGen, pollen, pumpkin, tumbleweed
		);

		// SURFACE DECORATION
		var coarseDecoration = booleanEntry(builder, "coarse_decoration", WWWorldgenConfig.COARSE_DIRT_DECORATION);
		var gravelDecoration = booleanEntry(builder, "gravel_decoration", WWWorldgenConfig.GRAVEL_DECORATION);
		var mudDecoration = booleanEntry(builder, "mud_decoration", WWWorldgenConfig.MUD_DECORATION);
		var packedMudDecoration = booleanEntry(builder, "packed_mud_decoration", WWWorldgenConfig.PACKED_MUD_DECORATION);
		var stoneDecoration = booleanEntry(builder, "stone_decoration", WWWorldgenConfig.STONE_DECORATION);
		var mossDecoration = booleanEntry(builder, "moss_decoration", WWWorldgenConfig.MOSS_DECORATION);
		var auburnMossDecoration = booleanEntry(builder, "auburn_moss_generation", WWWorldgenConfig.AUBURN_MOSS_DECORATION);
		var paleMossDecoration = booleanEntry(builder, "pale_moss_decoration", WWWorldgenConfig.PALE_MOSS_DECORATION);
		var scorchedSandDecoration = booleanEntry(builder, "scorched_sand_decoration", WWWorldgenConfig.SCORCHED_SAND_DECORATION);
		var scorchedRedSandDecoration = booleanEntry(builder, "scorched_red_sand_decoration", WWWorldgenConfig.SCORCHED_RED_SAND_DECORATION);
		var sandstoneDecoration = booleanEntry(builder, "sandstone_decoration", WWWorldgenConfig.SANDSTONE_DECORATION);
		var clayDecoration = booleanEntry(builder, "clay_decoration", WWWorldgenConfig.CLAY_DECORATION);
		var clearingDecoration = booleanEntry(builder, "clearing_decoration", WWWorldgenConfig.CLEARING_DECORATION);
		var snowPileDecoration = booleanEntry(builder, "snow_piles", WWWorldgenConfig.SNOW_PILE_DECORATION);
		var fragileIceDecoration = booleanEntry(builder, "fragile_ice_decoration", WWWorldgenConfig.FRAGILE_ICE_DECORATION);
		var icicleDecoration = booleanEntry(builder, "icicle_decoration", WWWorldgenConfig.ICICLE_DECORATION);
		var taigaBoulderDecoration = booleanEntry(builder, "taiga_boulders", WWWorldgenConfig.TAIGA_BOULDER_DECORATION);
		var lakeDecoration = booleanEntry(builder, "lake_generation", WWWorldgenConfig.LAKE_DECORATION);
		var basinDecoration = booleanEntry(builder, "basin_generation", WWWorldgenConfig.BASIN_DECORATION);

		FrozenClothConfig.createSubCategory(builder, category, text("surface_decoration"),
			false,
			tooltip("surface_decoration"),
			coarseDecoration, gravelDecoration, mudDecoration, packedMudDecoration, stoneDecoration, mossDecoration, auburnMossDecoration, paleMossDecoration,
			scorchedSandDecoration, scorchedRedSandDecoration, sandstoneDecoration, clayDecoration, clearingDecoration, taigaBoulderDecoration,
			snowPileDecoration, fragileIceDecoration, icicleDecoration, lakeDecoration, basinDecoration
		);

		category.addEntry(booleanEntry(builder, "termite_generation", WWWorldgenConfig.TERMITE_GENERATION));
		category.addEntry(booleanEntry(builder, "nether_geyser_generation", WWWorldgenConfig.NETHER_GEYSER_GENERATION));
		category.addEntry(booleanEntry(builder, "snow_below_trees", WWWorldgenConfig.SNOW_BELOW_TREES));

		// AQUATIC GENERATION
		var riverPoolGeneration = booleanEntry(builder, "river_pool", WWWorldgenConfig.RIVER_POOL_GENERATION);
		var algaeGeneration = booleanEntry(builder, "algae_generation", WWWorldgenConfig.ALGAE_GENERATION);
		var planktonGeneration = booleanEntry(builder, "plankton_generation", WWWorldgenConfig.PLANKTON_GENERATION);
		var seagrassGeneration = booleanEntry(builder, "seagrass_generation", WWWorldgenConfig.SEAGRASS_GENERATION);
		var spongeBudGeneration = booleanEntry(builder, "sponge_bud_generation", WWWorldgenConfig.SPONGE_BUD_GENERATION);
		var barnaclesGeneration = booleanEntry(builder, "barnacle_generation", WWWorldgenConfig.BARNACLES_GENERATION);
		var cattailGeneration = booleanEntry(builder, "cattail_generation", WWWorldgenConfig.CATTAIL_GENERATION);
		var seaAnemoneGeneration = booleanEntry(builder, "sea_anemone_generation", WWWorldgenConfig.SEA_ANEMONE_GENERATION);
		var seaWhipGeneration = booleanEntry(builder, "sea_whip_generation", WWWorldgenConfig.SEA_WHIP_GENERATION);
		var tubeWormsGeneration = booleanEntry(builder, "tube_worm_generation", WWWorldgenConfig.TUBE_WORMS_GENERATION);
		var hydrothermalVentGeneration = booleanEntry(builder, "hydrothermal_vent_generation", WWWorldgenConfig.HYDROTHERMAL_VENT_GENERATION);
		var oceanMossGeneration = booleanEntry(builder, "ocean_moss_generation", WWWorldgenConfig.OCEAN_MOSS_GENERATION);
		var oceanAuburnMossGeneration = booleanEntry(builder, "ocean_auburn_moss_generation", WWWorldgenConfig.OCEAN_AUBURN_MOSS_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("aquatic_generation"),
			false,
			tooltip("aquatic_generation"),
			riverPoolGeneration, algaeGeneration, planktonGeneration, seagrassGeneration, spongeBudGeneration, barnaclesGeneration, cattailGeneration,
			seaAnemoneGeneration, seaWhipGeneration, tubeWormsGeneration, hydrothermalVentGeneration, oceanMossGeneration, oceanAuburnMossGeneration
		);

		// SURFACE TRANSITIONS
		var sandTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.SAND, WWWorldgenConfig.SAND_TRANSITION_GENERATION);
		var redSandTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.RED_SAND, WWWorldgenConfig.RED_SAND_TRANSITION_GENERATION);
		var coarseTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.COARSE_DIRT, WWWorldgenConfig.COARSE_DIRT_TRANSITION_GENERATION);
		var gravelTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.GRAVEL, WWWorldgenConfig.GRAVEL_TRANSITION_GENERATION);
		var mudTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.MUD, WWWorldgenConfig.MUD_TRANSITION_GENERATION);
		var stoneTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.STONE, WWWorldgenConfig.STONE_TRANSITION_GENERATION);
		var snowTransitions = surfaceTransitionGenerationBooleanEntry(builder, Blocks.SNOW, WWWorldgenConfig.SNOW_TRANSITION_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("transition_generation"),
			false,
			tooltip("transition_generation"),
			sandTransitions, redSandTransitions, coarseTransitions, gravelTransitions, mudTransitions, stoneTransitions, snowTransitions
		);

		// STRUCTURE GENERATION
		var decayTrailRuins = booleanEntry(builder, "decay_trail_ruins", WWWorldgenConfig.DECAYING_TRAIL_RUINS_GENERATION);
		var newDesertVillages = booleanEntry(builder, "new_desert_villages", WWWorldgenConfig.NEW_DESERT_VILLAGE_GENERATION);
		var newWitchHuts = booleanEntry(builder, "new_witch_huts", WWWorldgenConfig.NEW_WITCH_HUT_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("structure_generation"),
			false,
			tooltip("structure_generation"),
			decayTrailRuins, newDesertVillages, newWitchHuts
		);
	}
}
