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
import static net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper.booleanEntry;
import static net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper.createSubCategory;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import static net.frozenblock.wilderwild.config.gui.WWClothConfigGuiHelper.*;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

@ClientOnly
public final class WWWorldgenConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "beta_beaches", WWWorldgenConfig.BETA_BEACHES));
		category.addEntry(booleanEntry(builder, "snow_under_mountains", WWWorldgenConfig.SNOW_UNDER_MOUNTAINS));

		// BIOME GENERATION
		createSubCategory(builder, category, text("biome_generation"), tooltip("biome_generation"),
			biomeGenerationBooleanEntry(builder, WWBiomes.ARID_FOREST, WWWorldgenConfig.ARID_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.ARID_SAVANNA, WWWorldgenConfig.ARID_SAVANNA_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.BIRCH_JUNGLE, WWWorldgenConfig.BIRCH_JUNGLE_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.BIRCH_TAIGA, WWWorldgenConfig.BIRCH_TAIGA_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.CYPRESS_WETLANDS, WWWorldgenConfig.CYPRESS_WETLANDS_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.DARK_BIRCH_FOREST, WWWorldgenConfig.DARK_BIRCH_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.DARK_TAIGA, WWWorldgenConfig.DARK_TAIGA_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.DYING_FOREST, WWWorldgenConfig.DYING_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.DYING_MIXED_FOREST, WWWorldgenConfig.DYING_MIXED_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.FLOWER_FIELD, WWWorldgenConfig.FLOWER_FIELD_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.FROZEN_CAVES, WWWorldgenConfig.FROZEN_CAVES_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.MAGMATIC_CAVES, WWWorldgenConfig.MAGMATIC_CAVES_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.MAPLE_FOREST, WWWorldgenConfig.MAPLE_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.MESOGLEA_CAVES, WWWorldgenConfig.MESOGLEA_CAVES_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.MIXED_FOREST, WWWorldgenConfig.MIXED_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.OASIS, WWWorldgenConfig.OASIS_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, WWWorldgenConfig.OLD_GROWTH_BIRCH_TAIGA_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.OLD_GROWTH_DARK_FOREST, WWWorldgenConfig.OLD_GROWTH_DARK_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.PARCHED_FOREST, WWWorldgenConfig.PARCHED_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.RAINFOREST, WWWorldgenConfig.RAINFOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.SEMI_BIRCH_FOREST, WWWorldgenConfig.SEMI_BIRCH_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.SNOWY_DYING_FOREST, WWWorldgenConfig.SNOWY_DYING_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.SNOWY_DYING_MIXED_FOREST, WWWorldgenConfig.SNOWY_DYING_MIXED_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, WWWorldgenConfig.SNOWY_OLD_GROWTH_PINE_TAIGA_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.SPARSE_BIRCH_JUNGLE, WWWorldgenConfig.SPARSE_BIRCH_JUNGLE_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.SPARSE_FOREST, WWWorldgenConfig.SPARSE_FOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.TEMPERATE_RAINFOREST, WWWorldgenConfig.TEMPERATE_RAINFOREST_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.TUNDRA, WWWorldgenConfig.TUNDRA_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.WARM_BEACH, WWWorldgenConfig.WARM_BEACH_GENERATION),
			biomeGenerationBooleanEntry(builder, WWBiomes.WARM_RIVER, WWWorldgenConfig.WARM_RIVER_GENERATION)
		);

		// BIOME PLACEMENT
		createSubCategory(builder, category, text("biome_placement"), tooltip("biome_placement"),
			biomePlacementBooleanEntry(builder, Biomes.CHERRY_GROVE, WWWorldgenConfig.CHERRY_GROVE_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, Biomes.JUNGLE, WWWorldgenConfig.JUNGLE_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, Biomes.MANGROVE_SWAMP, WWWorldgenConfig.MANGROVE_SWAMP_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, Biomes.STONY_SHORE, WWWorldgenConfig.STONY_SHORE_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, Biomes.SWAMP, WWWorldgenConfig.SWAMP_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, Biomes.WINDSWEPT_SAVANNA, WWWorldgenConfig.WINDSWEPT_SAVANNA_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, WWBiomes.TUNDRA, WWWorldgenConfig.TUNDRA_MODIFIED_PLACEMENT),
			biomePlacementBooleanEntry(builder, Biomes.DAPPLED_FOREST, WWWorldgenConfig.DAPPLED_FOREST_REMOVAL)
		);

		// TREE GENERATION
		createSubCategory(builder, category, text("tree_generation_category"), tooltip("tree_generation_category"),
			booleanEntry(builder, "tree_generation", WWWorldgenConfig.TREE_GENERATION),
			booleanEntry(builder, "fallen_trees", WWWorldgenConfig.FALLEN_TREE_GENERATION),
			booleanEntry(builder, "hollowed_fallen_trees", WWWorldgenConfig.HOLLOWED_FALLEN_TREE_GENERATION),
			booleanEntry(builder, "snapped_trees", WWWorldgenConfig.SNAPPED_TREE_GENERATION),
			booleanEntry(builder, "baobab_generation", WWWorldgenConfig.BAOBAB_TREE_GENERATION),
			booleanEntry(builder, "palm_generation", WWWorldgenConfig.PALM_TREE_GENERATION),
			booleanEntry(builder, "willow_generation", WWWorldgenConfig.WILLOW_TREE_GENERATION),
			booleanEntry(builder, "new_maples", WWWorldgenConfig.NEW_MAPLE_TREE_GENERATION),
			booleanEntry(builder, "birch_branches", WWWorldgenConfig.BIRCH_BRANCH_GENERATION),
			booleanEntry(builder, "oak_branches", WWWorldgenConfig.OAK_BRANCH_GENERATION),
			booleanEntry(builder, "dark_oak_branches", WWWorldgenConfig.DARK_OAK_BRANCH_GENERATION),
			booleanEntry(builder, "pale_oak_branches", WWWorldgenConfig.PALE_OAK_BRANCH_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "acacia", WWWorldgenConfig.ACACIA_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "birch", WWWorldgenConfig.BIRCH_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "cherry", WWWorldgenConfig.CHERRY_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "cypress", WWWorldgenConfig.CYPRESS_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "jungle", WWWorldgenConfig.JUNGLE_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "mangrove", WWWorldgenConfig.MANGROVE_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "oak", WWWorldgenConfig.OAK_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "dark_oak", WWWorldgenConfig.DARK_OAK_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "pale_oak", WWWorldgenConfig.PALE_OAK_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "spruce", WWWorldgenConfig.SPRUCE_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "willow", WWWorldgenConfig.WILLOW_SHELF_MUSHROOM_GENERATION),
			shelfMushroomGenerationBooleanEntry(builder, "maple", WWWorldgenConfig.MAPLE_SHELF_MUSHROOM_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.ACACIA_LEAF_LITTER.get(), WWWorldgenConfig.ACACIA_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.AZALEA_LEAF_LITTER.get(), WWWorldgenConfig.AZALEA_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.BAOBAB_LEAF_LITTER.get(), WWWorldgenConfig.BAOBAB_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.BIRCH_LEAF_LITTER.get(), WWWorldgenConfig.BIRCH_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.CHERRY_LEAF_LITTER.get(), WWWorldgenConfig.CHERRY_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.CYPRESS_LEAF_LITTER.get(), WWWorldgenConfig.CYPRESS_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.DARK_OAK_LEAF_LITTER.get(), WWWorldgenConfig.DARK_OAK_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.JUNGLE_LEAF_LITTER.get(), WWWorldgenConfig.JUNGLE_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.MANGROVE_LEAF_LITTER.get(), WWWorldgenConfig.MANGROVE_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, Blocks.LEAF_LITTER, WWWorldgenConfig.OAK_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.PALE_OAK_LEAF_LITTER.get(), WWWorldgenConfig.PALE_OAK_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.PALM_FROND_LITTER.get(), WWWorldgenConfig.PALM_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.SPRUCE_LEAF_LITTER.get(), WWWorldgenConfig.SPRUCE_LITTER_GENERATION),
			litterBlockGenerationBooleanEntry(builder, WWBlocks.WILLOW_LEAF_LITTER.get(), WWWorldgenConfig.WILLOW_LITTER_GENERATION)
		);

		// VEGETATION GENERATION
		createSubCategory(builder, category, text("vegetation"), tooltip("vegetation"),
			booleanEntry(builder, "grass_generation", WWWorldgenConfig.GRASS_GENERATION),
			booleanEntry(builder, "dry_grass_generation", WWWorldgenConfig.DRY_GRASS_GENERATION),
			booleanEntry(builder, "flower_generation", WWWorldgenConfig.FLOWER_GENERATION),
			booleanEntry(builder, "shrub_generation", WWWorldgenConfig.SHRUB_GENERATION),
			booleanEntry(builder, "cactus_generation", WWWorldgenConfig.CACTUS_GENERATION),
			booleanEntry(builder, "mushroom_generation", WWWorldgenConfig.MUSHROOM_GENERATION),
			booleanEntry(builder, "pale_mushroom_generation", WWWorldgenConfig.PALE_MUSHROOM_GENERATION),
			booleanEntry(builder, "firefly_bush_generation", WWWorldgenConfig.FIREFLY_BUSH_GENERATION),
			booleanEntry(builder, "pollen_generation", WWWorldgenConfig.POLLEN_GENERATION),
			booleanEntry(builder, "pumpkin_generation", WWWorldgenConfig.PUMPKIN_GENERATION),
			booleanEntry(builder, "tumbleweed_generation", WWWorldgenConfig.TUMBLEWEED_GENERATION)
		);

		// SURFACE DECORATION
		createSubCategory(builder, category, text("surface_decoration"), tooltip("surface_decoration"),
			booleanEntry(builder, "coarse_decoration", WWWorldgenConfig.COARSE_DIRT_DECORATION),
			booleanEntry(builder, "gravel_decoration", WWWorldgenConfig.GRAVEL_DECORATION),
			booleanEntry(builder, "mud_decoration", WWWorldgenConfig.MUD_DECORATION),
			booleanEntry(builder, "packed_mud_decoration", WWWorldgenConfig.PACKED_MUD_DECORATION),
			booleanEntry(builder, "stone_decoration", WWWorldgenConfig.STONE_DECORATION),
			booleanEntry(builder, "moss_decoration", WWWorldgenConfig.MOSS_DECORATION),
			booleanEntry(builder, "auburn_moss_generation", WWWorldgenConfig.AUBURN_MOSS_DECORATION),
			booleanEntry(builder, "pale_moss_decoration", WWWorldgenConfig.PALE_MOSS_DECORATION),
			booleanEntry(builder, "scorched_sand_decoration", WWWorldgenConfig.SCORCHED_SAND_DECORATION),
			booleanEntry(builder, "scorched_red_sand_decoration", WWWorldgenConfig.SCORCHED_RED_SAND_DECORATION),
			booleanEntry(builder, "sandstone_decoration", WWWorldgenConfig.SANDSTONE_DECORATION),
			booleanEntry(builder, "clay_decoration", WWWorldgenConfig.CLAY_DECORATION),
			booleanEntry(builder, "clearing_decoration", WWWorldgenConfig.CLEARING_DECORATION),
			booleanEntry(builder, "snow_piles", WWWorldgenConfig.SNOW_PILE_DECORATION),
			booleanEntry(builder, "fragile_ice_decoration", WWWorldgenConfig.FRAGILE_ICE_DECORATION),
			booleanEntry(builder, "icicle_decoration", WWWorldgenConfig.ICICLE_DECORATION),
			booleanEntry(builder, "taiga_boulders", WWWorldgenConfig.TAIGA_BOULDER_DECORATION),
			booleanEntry(builder, "lake_generation", WWWorldgenConfig.LAKE_DECORATION),
			booleanEntry(builder, "basin_generation", WWWorldgenConfig.BASIN_DECORATION)
		);

		category.addEntry(booleanEntry(builder, "termite_generation", WWWorldgenConfig.TERMITE_GENERATION));
		category.addEntry(booleanEntry(builder, "nether_geothermal_vent_generation", WWWorldgenConfig.NETHER_GEOTHERMAL_VENT_GENERATION));
		category.addEntry(booleanEntry(builder, "snow_below_trees", WWWorldgenConfig.SNOW_BELOW_TREES));

		// AQUATIC GENERATION
		createSubCategory(builder, category, text("aquatic_generation"), tooltip("aquatic_generation"),
			booleanEntry(builder, "river_pool", WWWorldgenConfig.RIVER_POOL_GENERATION),
			booleanEntry(builder, "algae_generation", WWWorldgenConfig.ALGAE_GENERATION),
			booleanEntry(builder, "plankton_generation", WWWorldgenConfig.PLANKTON_GENERATION),
			booleanEntry(builder, "seagrass_generation", WWWorldgenConfig.SEAGRASS_GENERATION),
			booleanEntry(builder, "sponge_bud_generation", WWWorldgenConfig.SPONGE_BUD_GENERATION),
			booleanEntry(builder, "barnacle_generation", WWWorldgenConfig.BARNACLES_GENERATION),
			booleanEntry(builder, "cattail_generation", WWWorldgenConfig.CATTAIL_GENERATION),
			booleanEntry(builder, "sea_anemone_generation", WWWorldgenConfig.SEA_ANEMONE_GENERATION),
			booleanEntry(builder, "sea_whip_generation", WWWorldgenConfig.SEA_WHIP_GENERATION),
			booleanEntry(builder, "tube_worm_generation", WWWorldgenConfig.TUBE_WORMS_GENERATION),
			booleanEntry(builder, "hydrothermal_vent_generation", WWWorldgenConfig.HYDROTHERMAL_VENT_GENERATION),
			booleanEntry(builder, "ocean_moss_generation", WWWorldgenConfig.OCEAN_MOSS_GENERATION),
			booleanEntry(builder, "ocean_auburn_moss_generation", WWWorldgenConfig.OCEAN_AUBURN_MOSS_GENERATION)
		);

		// SURFACE TRANSITIONS
		createSubCategory(builder, category, text("transition_generation"), tooltip("transition_generation"),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.SAND, WWWorldgenConfig.SAND_TRANSITION_GENERATION),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.RED_SAND, WWWorldgenConfig.RED_SAND_TRANSITION_GENERATION),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.COARSE_DIRT, WWWorldgenConfig.COARSE_DIRT_TRANSITION_GENERATION),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.GRAVEL, WWWorldgenConfig.GRAVEL_TRANSITION_GENERATION),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.MUD, WWWorldgenConfig.MUD_TRANSITION_GENERATION),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.STONE, WWWorldgenConfig.STONE_TRANSITION_GENERATION),
			surfaceTransitionGenerationBooleanEntry(builder, Blocks.SNOW, WWWorldgenConfig.SNOW_TRANSITION_GENERATION)
		);

		// STRUCTURE GENERATION
		createSubCategory(builder, category, text("structure_generation"), tooltip("structure_generation"),
			booleanEntry(builder, "decay_trail_ruins", WWWorldgenConfig.DECAYING_TRAIL_RUINS_GENERATION),
			booleanEntry(builder, "new_desert_villages", WWWorldgenConfig.NEW_DESERT_VILLAGE_GENERATION),
			booleanEntry(builder, "new_abandoned_camps", WWWorldgenConfig.NEW_ABANDONED_CAMP_GENERATION),
			booleanEntry(builder, "new_witch_huts", WWWorldgenConfig.NEW_WITCH_HUT_GENERATION)
		);
	}
}
