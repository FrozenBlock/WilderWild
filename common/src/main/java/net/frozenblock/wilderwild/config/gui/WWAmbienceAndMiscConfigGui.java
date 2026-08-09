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
import static net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper.*;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WWAmbienceAndMiscConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "modify_advancements", WWAmbienceAndMiscConfig.MODIFY_ADVANCEMENTS));

		// WIND
		createSubCategory(builder, category, text("wind"), tooltip("wind"),
			booleanEntry(builder, "cloud_movement", WWAmbienceAndMiscConfig.CLOUD_MOVEMENT),
			booleanEntry(builder, "wind_particles", WWAmbienceAndMiscConfig.WIND_PARTICLES),
			oneToFiveHundredEntry(builder, "wind_particle_frequency", WWAmbienceAndMiscConfig.WIND_PARTICLE_FREQUENCY),
			oneToOneThousandEntry(builder, "wind_particle_spawn_attempts", WWAmbienceAndMiscConfig.WIND_PARTICLE_SPAWN_ATTEMPTS),
			booleanEntry(builder, "wind_disturbance_particles", WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLES),
			oneToFiveHundredEntry(builder, "wind_disturbance_particle_frequency", WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLE_FREQUENCY),
			oneToOneThousandEntry(builder, "wind_disturbance_particle_spawn_attempts", WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS),
			booleanEntry(builder, "wind_clusters", WWAmbienceAndMiscConfig.WIND_CLUSTERS),
			intSliderEntry(builder, "wind_cluster_max_spawn_attempts", WWAmbienceAndMiscConfig.WIND_CLUSTER_MAX_SPAWN_ATTEMPTS, 1, 10),
			oneToFiveHundredEntry(builder, "wind_cluster_frequency", WWAmbienceAndMiscConfig.WIND_CLUSTER_FREQUENCY),
			zeroToFiveHundredEntry(builder, "particle_wind_movement", WWAmbienceAndMiscConfig.PARTICLE_WIND_MOVEMENT),
			zeroToFiveHundredEntry(builder, "firework_wind_movement", WWAmbienceAndMiscConfig.FIREWORK_WIND_MOVEMENT)
		);

		// FALLING LEAVES
		createSubCategory(builder, category, text("leaf_particles"), tooltip("leaf_particles"),
			booleanEntry(builder, "wilder_wild_falling_leaves", WWAmbienceAndMiscConfig.USE_WILDER_WILD_FALLING_LEAVES),
			booleanEntry(builder, "leaf_walking_particles", WWAmbienceAndMiscConfig.LEAF_WALKING_PARTICLES),
			booleanEntry(builder, "breaking_leaf_particles", WWAmbienceAndMiscConfig.BREAKING_LEAF_PARTICLES),
			booleanEntry(builder, "leaf_litter_walking_particles", WWAmbienceAndMiscConfig.LEAF_LITTER_WALKING_PARTICLES),
			booleanEntry(builder, "breaking_leaf_litter_particles", WWAmbienceAndMiscConfig.BREAKING_LEAF_LITTER_PARTICLES),
			booleanEntry(builder, "leaf_explosion_particles", WWAmbienceAndMiscConfig.LEAF_EXPLOSION_PARTICLES),
			zeroToFiveHundredEntry(builder, "leaf_explosion_velocity", WWAmbienceAndMiscConfig.LEAF_EXPLOSION_VELOCITY),
			zeroToFiveHundredEntry(builder, "oak_leaf_particles", WWAmbienceAndMiscConfig.OAK_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "spruce_leaf_particles", WWAmbienceAndMiscConfig.SPRUCE_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "birch_leaf_particles", WWAmbienceAndMiscConfig.BIRCH_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "jungle_leaf_particles", WWAmbienceAndMiscConfig.JUNGLE_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "acacia_leaf_particles", WWAmbienceAndMiscConfig.ACACIA_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "dark_oak_leaf_particles", WWAmbienceAndMiscConfig.DARK_OAK_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "pale_oak_leaf_particles", WWAmbienceAndMiscConfig.PALE_OAK_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "mangrove_leaf_particles", WWAmbienceAndMiscConfig.MANGROVE_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "cherry_leaf_particles", WWAmbienceAndMiscConfig.CHERRY_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "azalea_leaf_particles", WWAmbienceAndMiscConfig.AZALEA_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "baobab_leaf_particles", WWAmbienceAndMiscConfig.BAOBAB_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "cypress_leaf_particles", WWAmbienceAndMiscConfig.CYPRESS_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "palm_frond_particles", WWAmbienceAndMiscConfig.PALM_FROND_FREQUENCY),
			zeroToFiveHundredEntry(builder, "maple_leaf_particles", WWAmbienceAndMiscConfig.MAPLE_LEAF_FREQUENCY),
			zeroToFiveHundredEntry(builder, "willow_leaf_particles", WWAmbienceAndMiscConfig.WILLOW_LEAF_FREQUENCY)
		);

		// BIOME AMBIENCE
		createSubCategory(builder, category, text("biome_ambience"), tooltip("biome_ambience"),
			booleanEntry(builder, "deep_dark_ambience", WWAmbienceAndMiscConfig.DEEP_DARK_AMBIENCE),
			booleanEntry(builder, "deep_dark_fog", WWAmbienceAndMiscConfig.DEEP_DARK_FOG),
			booleanEntry(builder, "dripstone_caves_ambience", WWAmbienceAndMiscConfig.DRIPSTONE_CAVES_AMBIENCE),
			booleanEntry(builder, "dripstone_caves_fog", WWAmbienceAndMiscConfig.DRIPSTONE_CAVES_FOG),
			booleanEntry(builder, "lush_caves_ambience", WWAmbienceAndMiscConfig.LUSH_CAVES_AMBIENCE),
			booleanEntry(builder, "lush_caves_fog", WWAmbienceAndMiscConfig.LUSH_CAVES_FOG),
			booleanEntry(builder, "sulfur_caves_ambience", WWAmbienceAndMiscConfig.SULFUR_CAVES_AMBIENCE),
			booleanEntry(builder, "sulfur_caves_fog", WWAmbienceAndMiscConfig.SULFUR_CAVES_FOG),
			booleanEntry(builder, "frozen_caves_ambience", WWAmbienceAndMiscConfig.FROZEN_CAVES_AMBIENCE),
			booleanEntry(builder, "frozen_caves_fog", WWAmbienceAndMiscConfig.FROZEN_CAVES_FOG),
			booleanEntry(builder, "magmatic_caves_ambience", WWAmbienceAndMiscConfig.MAGMATIC_CAVES_AMBIENCE),
			booleanEntry(builder, "magmatic_caves_fog", WWAmbienceAndMiscConfig.MAGMATIC_CAVES_FOG),
			booleanEntry(builder, "magmatic_caves_particles", WWAmbienceAndMiscConfig.MAGMATIC_CAVES_PARTICLES),
			booleanEntry(builder, "mesoglea_caves_ambience", WWAmbienceAndMiscConfig.MESOGLEA_CAVES_AMBIENCE),
			booleanEntry(builder, "mesoglea_caves_fog", WWAmbienceAndMiscConfig.MESOGLEA_CAVES_FOG)
		);

		// WATER COLORS
		createSubCategory(builder, category, text("water_colors"), tooltip("water_colors"),
			booleanEntry(builder, "hot_water", WWAmbienceAndMiscConfig.MODIFY_HOT_WATER),
			booleanEntry(builder, "lukewarm_water", WWAmbienceAndMiscConfig.MODIFY_LUKEWARM_WATER),
			booleanEntry(builder, "snowy_water", WWAmbienceAndMiscConfig.MODIFY_SNOWY_WATER),
			booleanEntry(builder, "frozen_water", WWAmbienceAndMiscConfig.MODIFY_FROZEN_WATER)
		);

		// VEGETATION COLORS
		createSubCategory(builder, category, text("vegetation_colors"), tooltip("vegetation_colors"),
			booleanEntry(builder, "badlands_foliage", WWAmbienceAndMiscConfig.BADLANDS_FOLIAGE_COLOR)
		);

		// MUSIC
		createSubCategory(builder, category, text("music"), tooltip("music"),
			booleanEntry(builder, "wilder_forest_music", WWAmbienceAndMiscConfig.WILDER_FOREST_MUSIC),
			booleanEntry(builder, "wilder_taiga_music", WWAmbienceAndMiscConfig.WILDER_TAIGA_MUSIC),
			booleanEntry(builder, "wilder_cherry_grove_music", WWAmbienceAndMiscConfig.WILDER_CHERRY_GROVE_MUSIC),
			booleanEntry(builder, "wilder_grove_music", WWAmbienceAndMiscConfig.WILDER_GROVE_MUSIC),
			booleanEntry(builder, "wilder_jungle_music", WWAmbienceAndMiscConfig.WILDER_JUNGLE_MUSIC),
			booleanEntry(builder, "wilder_badlands_music", WWAmbienceAndMiscConfig.WILDER_BADLANDS_MUSIC),
			booleanEntry(builder, "wilder_desert_music", WWAmbienceAndMiscConfig.WILDER_DESERT_MUSIC),
			booleanEntry(builder, "wilder_snowy_music", WWAmbienceAndMiscConfig.WILDER_SNOWY_MUSIC),
			booleanEntry(builder, "wilder_ocean_music", WWAmbienceAndMiscConfig.WILDER_OCEAN_MUSIC),
			booleanEntry(builder, "wilder_lush_caves_music", WWAmbienceAndMiscConfig.WILDER_LUSH_CAVES_MUSIC),
			booleanEntry(builder, "wilder_dripstone_caves_music", WWAmbienceAndMiscConfig.WILDER_DRIPSTONE_CAVES_MUSIC),
			booleanEntry(builder, "wilder_extra_music", WWAmbienceAndMiscConfig.WILDER_EXTRA_MUSIC),
			booleanEntry(builder, "dan_music", WWAmbienceAndMiscConfig.DAN_MUSIC),
			booleanEntry(builder, "ancient_city_music", WWAmbienceAndMiscConfig.ANCIENT_CITY_MUSIC),
			booleanEntry(builder, "distorted_dying_forest_music", WWAmbienceAndMiscConfig.DISTORTED_DYING_FOREST_MUSIC)
		);
	}
}
