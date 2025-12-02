/*
 * Copyright 2025 FrozenBlock
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
import me.shedaniel.clothconfig2.api.Requirement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;

@Environment(EnvType.CLIENT)
public final class WWAmbienceAndMiscConfigGui {

	private WWAmbienceAndMiscConfigGui() {
		throw new UnsupportedOperationException("WWAmbienceAndMiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		final var config = WWAmbienceAndMiscConfig.get(true);
		final var modifiedConfig = WWAmbienceAndMiscConfig.getWithSync();
		final Class<? extends WWAmbienceAndMiscConfig> clazz = config.getClass();
		final Config<? extends WWAmbienceAndMiscConfig> configInstance = WWAmbienceAndMiscConfig.INSTANCE;
		final var defaultConfig = WWAmbienceAndMiscConfig.INSTANCE.defaultInstance();
		final var biomeAmbience = config.biomeAmbience;
		final var music = config.music;
		final var waterColors = config.waterColors;
		final var vegetationColors = config.vegetationColors;
		final var leafParticles = config.leafParticles;
		final var modifiedLeafParticles = modifiedConfig.leafParticles;
		final Class<? extends WWAmbienceAndMiscConfig.LeafParticles> leafParticlesClazz = leafParticles.getClass();
		final var wind = config.wind;
		final var modifiedWind = modifiedConfig.wind;
		final Class<? extends WWAmbienceAndMiscConfig.Wind> windClazz = wind.getClass();

		var modifyAdvancements = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("modify_advancements"), modifiedConfig.modifyAdvancements)
					.setDefaultValue(defaultConfig.modifyAdvancements)
					.setSaveConsumer(newValue -> config.modifyAdvancements = newValue)
					.setTooltip(tooltip("modify_advancements"))
					.build(),
				clazz,
				"modifyAdvancements",
				configInstance
			)
		);

		var cloudMovement = builder.startBooleanToggle(text("cloud_movement"), wind.cloudMovement)
			.setDefaultValue(defaultConfig.wind.cloudMovement)
			.setSaveConsumer(newValue -> wind.cloudMovement = newValue)
			.setTooltip(tooltip("cloud_movement"))
			.build();

		var windParticles = builder.startBooleanToggle(text("wind_particles"), wind.windParticles)
			.setDefaultValue(defaultConfig.wind.windParticles)
			.setSaveConsumer(newValue -> wind.windParticles = newValue)
			.setTooltip(tooltip("wind_particles"))
			.build();

		var windParticleFrequency = builder.startIntSlider(text("wind_particle_frequency"), wind.windParticleFrequency, 1, 500)
			.setDefaultValue(defaultConfig.wind.windParticleFrequency)
			.setSaveConsumer(newValue -> wind.windParticleFrequency = newValue)
			.setTooltip(tooltip("wind_particle_frequency"))
			.build();

		var windDisturbanceParticles = builder.startBooleanToggle(text("wind_disturbance_particles"), wind.windDisturbanceParticles)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticles)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticles = newValue)
			.setTooltip(tooltip("wind_disturbance_particles"))
			.build();

		var windParticleSpawnAttempts = builder.startIntSlider(text("wind_particle_spawn_attempts"), wind.windParticleSpawnAttempts, 1, 1000)
			.setDefaultValue(defaultConfig.wind.windParticleSpawnAttempts)
			.setSaveConsumer(newValue -> wind.windParticleSpawnAttempts = newValue)
			.setTooltip(tooltip("wind_particle_spawn_attempts"))
			.build();

		var windDisturbanceParticleFrequency = builder.startIntSlider(text("wind_disturbance_particle_frequency"), wind.windDisturbanceParticleFrequency, 1, 500)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticleFrequency)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticleFrequency = newValue)
			.setTooltip(tooltip("wind_disturbance_particle_frequency"))
			.build();

		var windDisturbanceParticleSpawnAttempts = builder.startIntSlider(text("wind_disturbance_particle_spawn_attempts"), wind.windDisturbanceParticleSpawnAttempts, 1, 1000)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticleSpawnAttempts)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticleSpawnAttempts = newValue)
			.setTooltip(tooltip("wind_disturbance_particle_spawn_attempts"))
			.build();

		var windClusters = builder.startBooleanToggle(text("wind_clusters"), wind.windClusters)
			.setDefaultValue(defaultConfig.wind.windClusters)
			.setSaveConsumer(newValue -> wind.windClusters = newValue)
			.setTooltip(tooltip("wind_clusters"))
			.build();

		var windClusterMaxSpawnAttempts = builder.startIntSlider(text("wind_cluster_max_spawn_attempts"), wind.windClusterMaxSpawnAttempts, 1, 10)
			.setDefaultValue(defaultConfig.wind.windClusterMaxSpawnAttempts)
			.setSaveConsumer(newValue -> wind.windClusterMaxSpawnAttempts = newValue)
			.setTooltip(tooltip("wind_cluster_max_spawn_attempts"))
			.build();

		var windClusterFrequency = builder.startIntSlider(text("wind_cluster_frequency"), wind.windClusterFrequency, 1, 500)
			.setDefaultValue(defaultConfig.wind.windClusterFrequency)
			.setSaveConsumer(newValue -> wind.windClusterFrequency = newValue)
			.setTooltip(tooltip("wind_cluster_frequency"))
			.build();

		var particleWindMovement = builder.startIntSlider(text("particle_wind_movement"), wind.particleWindMovement, 0, 500)
			.setDefaultValue(defaultConfig.wind.particleWindMovement)
			.setSaveConsumer(newValue -> wind.particleWindMovement = newValue)
			.setTooltip(tooltip("particle_wind_movement"))
			.build();

		var fireworkWindMovement = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("firework_wind_movement"), modifiedWind.fireworkWindMovement, 0, 500)
				.setDefaultValue(defaultConfig.wind.fireworkWindMovement)
				.setSaveConsumer(newValue -> wind.fireworkWindMovement = newValue)
				.setTooltip(tooltip("firework_wind_movement"))
				.build(),
			windClazz,
			"fireworkWindMovement",
			configInstance
		);

		var windCategory = FrozenClothConfig.createSubCategory(builder, category, text("wind"),
			false,
			tooltip("wind"),
			cloudMovement,
			windParticles, windParticleFrequency, windParticleSpawnAttempts,
			windDisturbanceParticles, windDisturbanceParticleFrequency, windDisturbanceParticleSpawnAttempts,
			windClusters, windClusterMaxSpawnAttempts, windClusterFrequency,
			particleWindMovement, fireworkWindMovement
		);

		var useWilderWildFallingLeaves = builder.startBooleanToggle(text("wilder_wild_falling_leaves"), leafParticles.useWilderWildFallingLeaves)
			.setDefaultValue(defaultConfig.leafParticles.useWilderWildFallingLeaves)
			.setSaveConsumer(newValue -> leafParticles.useWilderWildFallingLeaves = newValue)
			.setTooltip(tooltip("wilder_wild_falling_leaves"))
			.build();

		var leafWalkingParticles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("leaf_walking_particles"), modifiedLeafParticles.leafWalkingParticles)
				.setDefaultValue(defaultConfig.leafParticles.leafWalkingParticles)
				.setSaveConsumer(newValue -> leafParticles.leafWalkingParticles = newValue)
				.setTooltip(tooltip("leaf_walking_particles"))
				.build(),
			leafParticlesClazz,
			"leafWalkingParticles",
			configInstance
		);

		var breakingLeafParticles = builder.startBooleanToggle(text("breaking_leaf_particles"), leafParticles.breakingLeafParticles)
			.setDefaultValue(defaultConfig.leafParticles.breakingLeafParticles)
			.setSaveConsumer(newValue -> leafParticles.breakingLeafParticles = newValue)
			.setTooltip(tooltip("breaking_leaf_particles"))
			.build();

		var leafLitterParticles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("leaf_litter_particles"), modifiedLeafParticles.leafLitterParticles)
				.setDefaultValue(defaultConfig.leafParticles.leafLitterParticles)
				.setSaveConsumer(newValue -> leafParticles.leafLitterParticles = newValue)
				.setTooltip(tooltip("leaf_litter_particles"))
				.build(),
			leafParticlesClazz,
			"leafLitterParticles",
			configInstance
		);

		var breakingLeafLitterParticles = builder.startBooleanToggle(text("breaking_leaf_litter_particles"), leafParticles.breakingLeafLitterParticles)
			.setDefaultValue(defaultConfig.leafParticles.breakingLeafLitterParticles)
			.setSaveConsumer(newValue -> leafParticles.breakingLeafLitterParticles = newValue)
			.setTooltip(tooltip("breaking_leaf_litter_particles"))
			.build();

		var leafExplosionParticles = builder.startBooleanToggle(text("leaf_explosion_particles"), leafParticles.leafExplosionParticles)
			.setDefaultValue(defaultConfig.leafParticles.leafExplosionParticles)
			.setSaveConsumer(newValue -> leafParticles.leafExplosionParticles = newValue)
			.setTooltip(tooltip("leaf_explosion_particles"))
			.build();

		var leafExplosionVelocity = builder.startIntSlider(text("leaf_explosion_velocity"), leafParticles.leafExplosionVelocity, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.leafExplosionVelocity)
			.setSaveConsumer(newValue -> leafParticles.leafExplosionVelocity = newValue)
			.setTooltip(tooltip("leaf_explosion_velocity"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.leafExplosionParticles))
			.build();

		var oakLeafParticles = builder.startIntSlider(text("oak_leaf_particles"), leafParticles.oakFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.oakFrequency)
			.setSaveConsumer(newValue -> leafParticles.oakFrequency = newValue)
			.setTooltip(tooltip("oak_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var spruceLeafParticles = builder.startIntSlider(text("spruce_leaf_particles"), leafParticles.spruceFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.spruceFrequency)
			.setSaveConsumer(newValue -> leafParticles.spruceFrequency = newValue)
			.setTooltip(tooltip("spruce_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var birchLeafParticles = builder.startIntSlider(text("birch_leaf_particles"), leafParticles.birchFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.birchFrequency)
			.setSaveConsumer(newValue -> leafParticles.birchFrequency = newValue)
			.setTooltip(tooltip("birch_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var jungleLeafParticles = builder.startIntSlider(text("jungle_leaf_particles"), leafParticles.jungleFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.jungleFrequency)
			.setSaveConsumer(newValue -> leafParticles.jungleFrequency = newValue)
			.setTooltip(tooltip("jungle_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var acaciaLeafParticles = builder.startIntSlider(text("acacia_leaf_particles"), leafParticles.acaciaFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.acaciaFrequency)
			.setSaveConsumer(newValue -> leafParticles.acaciaFrequency = newValue)
			.setTooltip(tooltip("acacia_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var darkOakLeafParticles = builder.startIntSlider(text("dark_oak_leaf_particles"), leafParticles.darkOakFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.darkOakFrequency)
			.setSaveConsumer(newValue -> leafParticles.darkOakFrequency = newValue)
			.setTooltip(tooltip("dark_oak_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var  paleOakLeafParticles = builder.startIntSlider(text("pale_oak_leaf_particles"), leafParticles.paleOakFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.paleOakFrequency)
			.setSaveConsumer(newValue -> leafParticles.paleOakFrequency = newValue)
			.setTooltip(tooltip("pale_oak_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var mangroveLeafParticles = builder.startIntSlider(text("mangrove_leaf_particles"), leafParticles.mangroveFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.mangroveFrequency)
			.setSaveConsumer(newValue -> leafParticles.mangroveFrequency = newValue)
			.setTooltip(tooltip("mangrove_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var cherryLeafParticles = builder.startIntSlider(text("cherry_leaf_particles"), leafParticles.cherryFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.cherryFrequency)
			.setSaveConsumer(newValue -> leafParticles.cherryFrequency = newValue)
			.setTooltip(tooltip("cherry_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var azaleaLeafParticles = builder.startIntSlider(text("azalea_leaf_particles"), leafParticles.azaleaFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.azaleaFrequency)
			.setSaveConsumer(newValue -> leafParticles.azaleaFrequency = newValue)
			.setTooltip(tooltip("azalea_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var floweringAzaleaLeafParticles = builder.startIntSlider(text("flowering_azalea_leaf_particles"), leafParticles.floweringAzaleaFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.floweringAzaleaFrequency)
			.setSaveConsumer(newValue -> leafParticles.floweringAzaleaFrequency = newValue)
			.setTooltip(tooltip("flowering_azalea_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var baobabLeafParticles = builder.startIntSlider(text("baobab_leaf_particles"), leafParticles.baobabFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.baobabFrequency)
			.setSaveConsumer(newValue -> leafParticles.baobabFrequency = newValue)
			.setTooltip(tooltip("baobab_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var cypressLeafParticles = builder.startIntSlider(text("cypress_leaf_particles"), leafParticles.cypressFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.cypressFrequency)
			.setSaveConsumer(newValue -> leafParticles.cypressFrequency = newValue)
			.setTooltip(tooltip("cypress_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var palmFrondParticles = builder.startIntSlider(text("palm_frond_particles"), leafParticles.palmFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.palmFrequency)
			.setSaveConsumer(newValue -> leafParticles.palmFrequency = newValue)
			.setTooltip(tooltip("palm_frond_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var mapleLeafParticles = builder.startIntSlider(text("maple_leaf_particles"), leafParticles.mapleFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.mapleFrequency)
			.setSaveConsumer(newValue -> leafParticles.mapleFrequency = newValue)
			.setTooltip(tooltip("maple_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var willowLeafParticles = builder.startIntSlider(text("willow_leaf_particles"), leafParticles.willowFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.willowFrequency)
			.setSaveConsumer(newValue -> leafParticles.willowFrequency = newValue)
			.setTooltip(tooltip("willow_leaf_particles"))
			.build();

		var leafParticleCategory = FrozenClothConfig.createSubCategory(builder, category, text("leaf_particles"),
			false,
			tooltip("leaf_particles"),
			useWilderWildFallingLeaves,
			leafWalkingParticles, breakingLeafParticles, leafLitterParticles, breakingLeafLitterParticles,
			leafExplosionParticles, leafExplosionVelocity,
			oakLeafParticles, spruceLeafParticles, birchLeafParticles, jungleLeafParticles, acaciaLeafParticles, darkOakLeafParticles,
			paleOakLeafParticles, mangroveLeafParticles, cherryLeafParticles, azaleaLeafParticles, floweringAzaleaLeafParticles,
			baobabLeafParticles, cypressLeafParticles, palmFrondParticles, mapleLeafParticles, willowLeafParticles
		);

		var deepDarkAmbience = builder.startBooleanToggle(text("deep_dark_ambience"), biomeAmbience.deepDarkAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.deepDarkAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.deepDarkAmbience = newValue)
			.setTooltip(tooltip("deep_dark_ambience"))
			.requireRestart()
			.build();

		var deepDarkFog = builder.startBooleanToggle(text("deep_dark_fog"), biomeAmbience.deepDarkFog)
			.setDefaultValue(defaultConfig.biomeAmbience.deepDarkFog)
			.setSaveConsumer(newValue -> biomeAmbience.deepDarkFog = newValue)
			.setTooltip(tooltip("deep_dark_fog"))
			.requireRestart()
			.build();

		var dripstoneCavesAmbience = builder.startBooleanToggle(text("dripstone_caves_ambience"), biomeAmbience.dripstoneCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.dripstoneCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.dripstoneCavesAmbience = newValue)
			.setTooltip(tooltip("dripstone_caves_ambience"))
			.requireRestart()
			.build();

		var lushCavesAmbience = builder.startBooleanToggle(text("lush_caves_ambience"), biomeAmbience.lushCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.lushCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.lushCavesAmbience = newValue)
			.setTooltip(tooltip("lush_caves_ambience"))
			.requireRestart()
			.build();

		var frozenCavesAmbience = builder.startBooleanToggle(text("frozen_caves_ambience"), biomeAmbience.frozenCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.frozenCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.frozenCavesAmbience = newValue)
			.setTooltip(tooltip("frozen_caves_ambience"))
			.requireRestart()
			.build();

		var frozenCavesFog = builder.startBooleanToggle(text("frozen_caves_fog"), biomeAmbience.frozenCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.frozenCavesFog)
			.setSaveConsumer(newValue -> biomeAmbience.frozenCavesFog = newValue)
			.setTooltip(tooltip("frozen_caves_fog"))
			.requireRestart()
			.build();

		var mesogleaCavesAmbience = builder.startBooleanToggle(text("mesoglea_caves_ambience"), biomeAmbience.mesogleaCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.mesogleaCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.mesogleaCavesAmbience = newValue)
			.setTooltip(tooltip("mesoglea_caves_ambience"))
			.requireRestart()
			.build();

		var mesogleaCavesFog = builder.startBooleanToggle(text("mesoglea_caves_fog"), biomeAmbience.mesogleaCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.mesogleaCavesFog)
			.setSaveConsumer(newValue -> biomeAmbience.mesogleaCavesFog = newValue)
			.setTooltip(tooltip("mesoglea_caves_fog"))
			.requireRestart()
			.build();

		var magmaticCavesAmbience = builder.startBooleanToggle(text("magmatic_caves_ambience"), biomeAmbience.magmaticCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.magmaticCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.magmaticCavesAmbience = newValue)
			.setTooltip(tooltip("magmatic_caves_ambience"))
			.requireRestart()
			.build();

		var magmaticCavesFog = builder.startBooleanToggle(text("magmatic_caves_fog"), biomeAmbience.magmaticCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.magmaticCavesFog)
			.setSaveConsumer(newValue -> biomeAmbience.magmaticCavesFog = newValue)
			.setTooltip(tooltip("magmatic_caves_fog"))
			.requireRestart()
			.build();

		var magmaticCavesParticles = builder.startBooleanToggle(text("magmatic_caves_particles"), biomeAmbience.magmaticCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.magmaticCavesParticles)
			.setSaveConsumer(newValue -> biomeAmbience.magmaticCavesParticles = newValue)
			.setTooltip(tooltip("magmatic_caves_particles"))
			.requireRestart()
			.build();

		var biomeAmbienceCategory = FrozenClothConfig.createSubCategory(builder, category, text("biome_ambience"),
			false,
			tooltip("biome_ambience"),
			deepDarkAmbience, deepDarkFog,
			dripstoneCavesAmbience,
			lushCavesAmbience,
			frozenCavesAmbience, frozenCavesFog,
			magmaticCavesAmbience, magmaticCavesFog, magmaticCavesParticles,
			mesogleaCavesAmbience, mesogleaCavesFog
		);

		var hotBiomes = builder.startBooleanToggle(text("hot_water"), waterColors.modifyHotWater)
			.setDefaultValue(defaultConfig.waterColors.modifyHotWater)
			.setSaveConsumer(newValue -> waterColors.modifyHotWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("hot_water"))
			.requireRestart()
			.build();

		var lukewarmBiomes = builder.startBooleanToggle(text("lukewarm_water"), waterColors.modifyLukewarmWater)
			.setDefaultValue(defaultConfig.waterColors.modifyLukewarmWater)
			.setSaveConsumer(newValue -> waterColors.modifyLukewarmWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("lukewarm_water"))
			.requireRestart()
			.build();

		var snowyBiomes = builder.startBooleanToggle(text("snowy_water"), waterColors.modifySnowyWater)
			.setDefaultValue(defaultConfig.waterColors.modifySnowyWater)
			.setSaveConsumer(newValue -> waterColors.modifySnowyWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("snowy_water"))
			.requireRestart()
			.build();

		var frozenBiomes = builder.startBooleanToggle(text("frozen_water"), waterColors.modifyFrozenWater)
			.setDefaultValue(defaultConfig.waterColors.modifyFrozenWater)
			.setSaveConsumer(newValue -> waterColors.modifyFrozenWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("frozen_water"))
			.requireRestart()
			.build();

		var waterColorCategory = FrozenClothConfig.createSubCategory(builder, category, text("water_colors"),
			false,
			tooltip("water_colors"),
			hotBiomes, lukewarmBiomes, snowyBiomes, frozenBiomes
		);

		var badlandsFoliage = builder.startBooleanToggle(text("badlands_foliage"), vegetationColors.badlandsFoliage)
			.setDefaultValue(defaultConfig.vegetationColors.badlandsFoliage)
			.setSaveConsumer(newValue -> vegetationColors.badlandsFoliage = newValue)
			.setYesNoTextSupplier(bool -> text("vegetation_colors." + bool))
			.setTooltip(tooltip("badlands_foliage"))
			.requireRestart()
			.build();

		var vegetationColorCategory = FrozenClothConfig.createSubCategory(builder, category, text("vegetation_colors"),
			false,
			tooltip("vegetation_colors"),
			badlandsFoliage
		);

		var wilderForestMusic = builder.startBooleanToggle(text("wilder_forest_music"), music.wilderForestMusic)
			.setDefaultValue(defaultConfig.music.wilderForestMusic)
			.setSaveConsumer(newValue -> music.wilderForestMusic = newValue)
			.setTooltip(tooltip("wilder_forest_music"))
			.requireRestart()
			.build();

		var wilderLushCavesMusic = builder.startBooleanToggle(text("wilder_lush_caves_music"), music.wilderLushCavesMusic)
			.setDefaultValue(defaultConfig.music.wilderLushCavesMusic)
			.setSaveConsumer(newValue -> music.wilderLushCavesMusic = newValue)
			.setTooltip(tooltip("wilder_lush_caves_music"))
			.requireRestart()
			.build();

		var wilderDripstoneCavesMusic = builder.startBooleanToggle(text("wilder_dripstone_caves_music"), music.wilderDripstoneCavesMusic)
			.setDefaultValue(defaultConfig.music.wilderDripstoneCavesMusic)
			.setSaveConsumer(newValue -> music.wilderDripstoneCavesMusic = newValue)
			.setTooltip(tooltip("wilder_dripstone_caves_music"))
			.requireRestart()
			.build();

		var wilderCherryGroveMusic = builder.startBooleanToggle(text("wilder_cherry_grove_music"), music.wilderCherryGroveMusic)
			.setDefaultValue(defaultConfig.music.wilderCherryGroveMusic)
			.setSaveConsumer(newValue -> music.wilderCherryGroveMusic = newValue)
			.setTooltip(tooltip("wilder_cherry_grove_music"))
			.requireRestart()
			.build();

		var wilderGroveMusic = builder.startBooleanToggle(text("wilder_grove_music"), music.wilderGroveMusic)
			.setDefaultValue(defaultConfig.music.wilderGroveMusic)
			.setSaveConsumer(newValue -> music.wilderGroveMusic = newValue)
			.setTooltip(tooltip("wilder_grove_music"))
			.requireRestart()
			.build();

		var wilderJungleMusic = builder.startBooleanToggle(text("wilder_jungle_music"), music.wilderJungleMusic)
			.setDefaultValue(defaultConfig.music.wilderJungleMusic)
			.setSaveConsumer(newValue -> music.wilderJungleMusic = newValue)
			.setTooltip(tooltip("wilder_jungle_music"))
			.requireRestart()
			.build();

		var wilderBadlandsMusic = builder.startBooleanToggle(text("wilder_badlands_music"), music.wilderBadlandsMusic)
			.setDefaultValue(defaultConfig.music.wilderBadlandsMusic)
			.setSaveConsumer(newValue -> music.wilderBadlandsMusic = newValue)
			.setTooltip(tooltip("wilder_badlands_music"))
			.requireRestart()
			.build();

		var wilderDesertMusic = builder.startBooleanToggle(text("wilder_desert_music"), music.wilderDesertMusic)
			.setDefaultValue(defaultConfig.music.wilderDesertMusic)
			.setSaveConsumer(newValue -> music.wilderDesertMusic = newValue)
			.setTooltip(tooltip("wilder_desert_music"))
			.requireRestart()
			.build();

		var wilderSnowyMusic = builder.startBooleanToggle(text("wilder_snowy_music"), music.wilderSnowyMusic)
			.setDefaultValue(defaultConfig.music.wilderSnowyMusic)
			.setSaveConsumer(newValue -> music.wilderSnowyMusic = newValue)
			.setTooltip(tooltip("wilder_snowy_music"))
			.requireRestart()
			.build();

		var wilderOceanMusic = builder.startBooleanToggle(text("wilder_ocean_music"), music.wilderOceanMusic)
			.setDefaultValue(defaultConfig.music.wilderOceanMusic)
			.setSaveConsumer(newValue -> music.wilderOceanMusic = newValue)
			.setTooltip(tooltip("wilder_ocean_music"))
			.requireRestart()
			.build();

		var wilderExtraMusic = builder.startBooleanToggle(text("wilder_extra_music"), music.wilderExtraMusic)
			.setDefaultValue(defaultConfig.music.wilderExtraMusic)
			.setSaveConsumer(newValue -> music.wilderExtraMusic = newValue)
			.setTooltip(tooltip("wilder_extra_music"))
			.requireRestart()
			.build();

		var danMusic = builder.startBooleanToggle(text("dan_music"), music.danMusic)
			.setDefaultValue(defaultConfig.music.danMusic)
			.setSaveConsumer(newValue -> music.danMusic = newValue)
			.setTooltip(tooltip("dan_music"))
			.requireRestart()
			.build();

		var ancientCityMusic = builder.startBooleanToggle(text("ancient_city_music"), music.ancientCityMusic)
			.setDefaultValue(defaultConfig.music.ancientCityMusic)
			.setSaveConsumer(newValue -> music.ancientCityMusic = newValue)
			.setTooltip(tooltip("ancient_city_music"))
			.requireRestart()
			.build();

		var distortedDyingForestMusic = builder.startBooleanToggle(text("distorted_dying_forest_music"), music.distortedDyingForestMusic)
			.setDefaultValue(defaultConfig.music.distortedDyingForestMusic)
			.setSaveConsumer(newValue -> music.distortedDyingForestMusic = newValue)
			.setTooltip(tooltip("distorted_dying_forest_music"))
			.build();

		var musicCategory = FrozenClothConfig.createSubCategory(builder, category, text("music"),
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
