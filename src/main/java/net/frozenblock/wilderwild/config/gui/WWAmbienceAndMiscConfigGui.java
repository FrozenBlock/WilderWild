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
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import static net.frozenblock.wilderwild.config.gui.WWConfigGuiHelper.*;

@Environment(EnvType.CLIENT)
public final class WWAmbienceAndMiscConfigGui {

	private WWAmbienceAndMiscConfigGui() {
		throw new UnsupportedOperationException("WWAmbienceAndMiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "modify_advancements", WWAmbienceAndMiscConfig.MODIFY_ADVANCEMENTS));

		// WIND
		var cloudMovement = booleanEntry(builder, "cloud_movement", WWAmbienceAndMiscConfig.CLOUD_MOVEMENT);

		var windParticles = booleanEntry(builder, "wind_particles", WWAmbienceAndMiscConfig.WIND_PARTICLES);
		var windParticleFrequency = oneToFiveHundredEntry(builder, "wind_particle_frequency", WWAmbienceAndMiscConfig.WIND_PARTICLE_FREQUENCY);
		var windParticleSpawnAttempts = oneToOneThousandEntry(builder, "wind_particle_spawn_attempts", WWAmbienceAndMiscConfig.WIND_PARTICLE_SPAWN_ATTEMPTS);

		var windDisturbanceParticles = booleanEntry(builder, "wind_disturbance_particles", WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLES);
		var windDisturbanceParticleFrequency = oneToFiveHundredEntry(builder, "wind_disturbance_particle_frequency", WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLE_FREQUENCY);
		var windDisturbanceParticleSpawnAttempts = oneToOneThousandEntry(builder, "wind_disturbance_particle_spawn_attempts", WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS);

		var windClusters = booleanEntry(builder, "wind_clusters", WWAmbienceAndMiscConfig.WIND_CLUSTERS);
		var windClusterFrequency = oneToFiveHundredEntry(builder, "wind_cluster_frequency", WWAmbienceAndMiscConfig.WIND_CLUSTER_FREQUENCY);
		var windClusterMaxSpawnAttempts = intSliderEntry(builder, "wind_cluster_max_spawn_attempts", WWAmbienceAndMiscConfig.WIND_CLUSTER_MAX_SPAWN_ATTEMPTS, 1, 10);

		var particleWindMovement = zeroToFiveHundredEntry(builder, "particle_wind_movement", WWAmbienceAndMiscConfig.PARTICLE_WIND_MOVEMENT);
		var fireworkWindMovement = zeroToFiveHundredEntry(builder, "firework_wind_movement", WWAmbienceAndMiscConfig.FIREWORK_WIND_MOVEMENT);

		FrozenClothConfig.createSubCategory(builder, category, text("wind"),
			false,
			tooltip("wind"),
			cloudMovement,
			windParticles, windParticleFrequency, windParticleSpawnAttempts,
			windDisturbanceParticles, windDisturbanceParticleFrequency, windDisturbanceParticleSpawnAttempts,
			windClusters, windClusterMaxSpawnAttempts, windClusterFrequency,
			particleWindMovement, fireworkWindMovement
		);

		// FALLING LEAVES
		var useWilderWildFallingLeaves = booleanEntry(builder, "wilder_wild_falling_leaves", WWAmbienceAndMiscConfig.USE_WILDER_WILD_FALLING_LEAVES);
		var leafWalkingParticles = booleanEntry(builder, "leaf_walking_particles", WWAmbienceAndMiscConfig.LEAF_WALKING_PARTICLES);
		var breakingLeafParticles = booleanEntry(builder, "breaking_leaf_particles", WWAmbienceAndMiscConfig.BREAKING_LEAF_PARTICLES);
		var leafLitterWalkingParticles = booleanEntry(builder, "leaf_litter_walking_particles", WWAmbienceAndMiscConfig.LEAF_LITTER_WALKING_PARTICLES);
		var breakingLeafLitterParticles = booleanEntry(builder, "breaking_leaf_litter_particles", WWAmbienceAndMiscConfig.BREAKING_LEAF_LITTER_PARTICLES);
		var leafExplosionParticles = booleanEntry(builder, "leaf_explosion_particles", WWAmbienceAndMiscConfig.LEAF_EXPLOSION_PARTICLES);
		var leafExplosionVelocity = zeroToFiveHundredEntry(builder, "leaf_explosion_velocity", WWAmbienceAndMiscConfig.LEAF_EXPLOSION_VELOCITY);

		var oakLeafParticles = zeroToFiveHundredEntry(builder, "oak_leaf_particles", WWAmbienceAndMiscConfig.OAK_LEAF_FREQUENCY);
		var spruceLeafParticles = zeroToFiveHundredEntry(builder, "spruce_leaf_particles", WWAmbienceAndMiscConfig.SPRUCE_LEAF_FREQUENCY);
		var birchLeafParticles = zeroToFiveHundredEntry(builder, "birch_leaf_particles", WWAmbienceAndMiscConfig.BIRCH_LEAF_FREQUENCY);
		var jungleLeafParticles = zeroToFiveHundredEntry(builder, "jungle_leaf_particles", WWAmbienceAndMiscConfig.JUNGLE_LEAF_FREQUENCY);
		var acaciaLeafParticles = zeroToFiveHundredEntry(builder, "acacia_leaf_particles", WWAmbienceAndMiscConfig.ACACIA_LEAF_FREQUENCY);
		var darkOakLeafParticles = zeroToFiveHundredEntry(builder, "dark_oak_leaf_particles", WWAmbienceAndMiscConfig.DARK_OAK_LEAF_FREQUENCY);
		var paleOakLeafParticles = zeroToFiveHundredEntry(builder, "pale_oak_leaf_particles", WWAmbienceAndMiscConfig.PALE_OAK_LEAF_FREQUENCY);
		var mangroveLeafParticles = zeroToFiveHundredEntry(builder, "mangrove_leaf_particles", WWAmbienceAndMiscConfig.MANGROVE_LEAF_FREQUENCY);
		var cherryLeafParticles = zeroToFiveHundredEntry(builder, "cherry_leaf_particles", WWAmbienceAndMiscConfig.CHERRY_LEAF_FREQUENCY);
		var azaleaLeafParticles = zeroToFiveHundredEntry(builder, "azalea_leaf_particles", WWAmbienceAndMiscConfig.AZALEA_LEAF_FREQUENCY);
		var baobabLeafParticles = zeroToFiveHundredEntry(builder, "baobab_leaf_particles", WWAmbienceAndMiscConfig.BAOBAB_LEAF_FREQUENCY);
		var cypressLeafParticles = zeroToFiveHundredEntry(builder, "cypress_leaf_particles", WWAmbienceAndMiscConfig.CYPRESS_LEAF_FREQUENCY);
		var palmFrondParticles = zeroToFiveHundredEntry(builder, "palm_frond_particles", WWAmbienceAndMiscConfig.PALM_FROND_FREQUENCY);
		var mapleLeafParticles = zeroToFiveHundredEntry(builder, "maple_leaf_particles", WWAmbienceAndMiscConfig.MAPLE_LEAF_FREQUENCY);
		var willowLeafParticles = zeroToFiveHundredEntry(builder, "willow_leaf_particles", WWAmbienceAndMiscConfig.WILLOW_LEAF_FREQUENCY);

		FrozenClothConfig.createSubCategory(builder, category, text("leaf_particles"),
			false,
			tooltip("leaf_particles"),
			useWilderWildFallingLeaves,
			leafWalkingParticles, breakingLeafParticles, leafLitterWalkingParticles, breakingLeafLitterParticles,
			leafExplosionParticles, leafExplosionVelocity,
			oakLeafParticles, spruceLeafParticles, birchLeafParticles, jungleLeafParticles, acaciaLeafParticles, darkOakLeafParticles,
			paleOakLeafParticles, mangroveLeafParticles, cherryLeafParticles, azaleaLeafParticles,
			baobabLeafParticles, cypressLeafParticles, palmFrondParticles, mapleLeafParticles, willowLeafParticles
		);

		// BIOME AMBIENCE
		var deepDarkAmbience = booleanEntry(builder, "deep_dark_ambience", WWAmbienceAndMiscConfig.DEEP_DARK_AMBIENCE);
		var deepDarkFog = booleanEntry(builder, "deep_dark_fog", WWAmbienceAndMiscConfig.DEEP_DARK_FOG);
		var dripstoneCavesAmbience = booleanEntry(builder, "dripstone_caves_ambience", WWAmbienceAndMiscConfig.DRIPSTONE_CAVES_AMBIENCE);
		var lushCavesAmbience = booleanEntry(builder, "lush_caves_ambience", WWAmbienceAndMiscConfig.LUSH_CAVES_AMBIENCE);
		var frozenCavesAmbience = booleanEntry(builder, "frozen_caves_ambience", WWAmbienceAndMiscConfig.FROZEN_CAVES_AMBIENCE);
		var frozenCavesFog = booleanEntry(builder, "frozen_caves_fog", WWAmbienceAndMiscConfig.FROZEN_CAVES_FOG);
		var mesogleaCavesAmbience = booleanEntry(builder, "mesoglea_caves_ambience", WWAmbienceAndMiscConfig.MESOGLEA_CAVES_AMBIENCE);
		var mesogleaCavesFog = booleanEntry(builder, "mesoglea_caves_fog", WWAmbienceAndMiscConfig.MESOGLEA_CAVES_FOG);
		var magmaticCavesAmbience = booleanEntry(builder, "magmatic_caves_ambience", WWAmbienceAndMiscConfig.MAGMATIC_CAVES_AMBIENCE);
		var magmaticCavesFog = booleanEntry(builder, "magmatic_caves_fog", WWAmbienceAndMiscConfig.MAGMATIC_CAVES_FOG);
		var magmaticCavesParticles = booleanEntry(builder, "magmatic_caves_particles", WWAmbienceAndMiscConfig.MAGMATIC_CAVES_PARTICLES);

		FrozenClothConfig.createSubCategory(builder, category, text("biome_ambience"),
			false,
			tooltip("biome_ambience"),
			deepDarkAmbience, deepDarkFog,
			dripstoneCavesAmbience,
			lushCavesAmbience,
			frozenCavesAmbience, frozenCavesFog,
			magmaticCavesAmbience, magmaticCavesFog, magmaticCavesParticles,
			mesogleaCavesAmbience, mesogleaCavesFog
		);

		// WATER COLORS
		var hotWater = booleanEntry(builder, "hot_water", WWAmbienceAndMiscConfig.MODIFY_HOT_WATER);
		var lukewarmWater = booleanEntry(builder, "lukewarm_water", WWAmbienceAndMiscConfig.MODIFY_LUKEWARM_WATER);
		var snowyWater = booleanEntry(builder, "snowy_water", WWAmbienceAndMiscConfig.MODIFY_SNOWY_WATER);
		var frozenWater = booleanEntry(builder, "frozen_water", WWAmbienceAndMiscConfig.MODIFY_FROZEN_WATER);

		FrozenClothConfig.createSubCategory(builder, category, text("water_colors"),
			false,
			tooltip("water_colors"),
			hotWater, lukewarmWater, snowyWater, frozenWater
		);

		// VEGETATION COLORS
		var badlandsFoliage = booleanEntry(builder, "badlands_foliage", WWAmbienceAndMiscConfig.BADLANDS_FOLIAGE_COLOR);

		FrozenClothConfig.createSubCategory(builder, category, text("vegetation_colors"),
			false,
			tooltip("vegetation_colors"),
			badlandsFoliage
		);

		// MUSIC
		var wilderForestMusic = booleanEntry(builder, "wilder_forest_music", WWAmbienceAndMiscConfig.WILDER_FOREST_MUSIC);
		var wilderLushCavesMusic = booleanEntry(builder, "wilder_lush_caves_music", WWAmbienceAndMiscConfig.WILDER_LUSH_CAVES_MUSIC);
		var wilderDripstoneCavesMusic = booleanEntry(builder, "wilder_dripstone_caves_music", WWAmbienceAndMiscConfig.WILDER_DRIPSTONE_CAVES_MUSIC);
		var wilderCherryGroveMusic = booleanEntry(builder, "wilder_cherry_grove_music", WWAmbienceAndMiscConfig.WILDER_CHERRY_GROVE_MUSIC);
		var wilderGroveMusic = booleanEntry(builder, "wilder_grove_music", WWAmbienceAndMiscConfig.WILDER_GROVE_MUSIC);
		var wilderJungleMusic = booleanEntry(builder, "wilder_jungle_music", WWAmbienceAndMiscConfig.WILDER_JUNGLE_MUSIC);
		var wilderBadlandsMusic = booleanEntry(builder, "wilder_badlands_music", WWAmbienceAndMiscConfig.WILDER_BADLANDS_MUSIC);
		var wilderDesertMusic = booleanEntry(builder, "wilder_desert_music", WWAmbienceAndMiscConfig.WILDER_DESERT_MUSIC);
		var wilderSnowyMusic = booleanEntry(builder, "wilder_snowy_music", WWAmbienceAndMiscConfig.WILDER_SNOWY_MUSIC);
		var wilderOceanMusic = booleanEntry(builder, "wilder_ocean_music", WWAmbienceAndMiscConfig.WILDER_OCEAN_MUSIC);
		var wilderExtraMusic = booleanEntry(builder, "wilder_extra_music", WWAmbienceAndMiscConfig.WILDER_EXTRA_MUSIC);
		var danMusic = booleanEntry(builder, "dan_music", WWAmbienceAndMiscConfig.DAN_MUSIC);
		var ancientCityMusic = booleanEntry(builder, "ancient_city_music", WWAmbienceAndMiscConfig.ANCIENT_CITY_MUSIC);
		var distortedDyingForestMusic = booleanEntry(builder, "distorted_dying_forest_music", WWAmbienceAndMiscConfig.DISTORTED_DYING_FOREST_MUSIC);

		FrozenClothConfig.createSubCategory(builder, category, text("music"),
			false,
			tooltip("music"),
			wilderForestMusic, wilderCherryGroveMusic, wilderGroveMusic, wilderJungleMusic, wilderBadlandsMusic, wilderDesertMusic, wilderSnowyMusic,
			wilderOceanMusic,
			wilderLushCavesMusic, wilderDripstoneCavesMusic,
			wilderExtraMusic,
			danMusic,
			ancientCityMusic,
			distortedDyingForestMusic
		);
	}
}
