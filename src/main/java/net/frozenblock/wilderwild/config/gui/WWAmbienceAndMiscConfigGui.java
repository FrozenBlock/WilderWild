/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWAmbienceAndMiscConfigGui {
	private WWAmbienceAndMiscConfigGui() {
		throw new UnsupportedOperationException("AmbienceAndMiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WWAmbienceAndMiscConfig.get(true);
		var modifiedConfig = WWAmbienceAndMiscConfig.getWithSync();
		Class<? extends WWAmbienceAndMiscConfig> clazz = config.getClass();
		Config<? extends WWAmbienceAndMiscConfig> configInstance = WWAmbienceAndMiscConfig.INSTANCE;
		var defaultConfig = WWAmbienceAndMiscConfig.INSTANCE.defaultInstance();
		var biomeAmbience = config.biomeAmbience;
		var music = config.music;
		var waterColors = config.waterColors;
		var vegetationColors = config.vegetationColors;
		var leafParticles = config.leafParticles;
		var wind = config.wind;
		var modifiedWind = modifiedConfig.wind;
		Class<? extends WWAmbienceAndMiscConfig.Wind> windClazz = wind.getClass();

		var modifyAdvancements = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("modify_advancements"), modifiedConfig.modifyAdvancements)
					.setDefaultValue(defaultConfig.modifyAdvancements)
					.setSaveConsumer(newValue -> config.modifyAdvancements = newValue)
					.setTooltip(tooltip("modify_advancements"))
					.build(),
				clazz,
				"modifyAdvancements",
				configInstance
			)
		);

		var cloudMovement = entryBuilder.startBooleanToggle(text("cloud_movement"), wind.cloudMovement)
			.setDefaultValue(defaultConfig.wind.cloudMovement)
			.setSaveConsumer(newValue -> wind.cloudMovement = newValue)
			.setTooltip(tooltip("cloud_movement"))
			.build();

		var windParticles = entryBuilder.startBooleanToggle(text("wind_particles"), wind.windParticles)
			.setDefaultValue(defaultConfig.wind.windParticles)
			.setSaveConsumer(newValue -> wind.windParticles = newValue)
			.setTooltip(tooltip("wind_particles"))
			.build();

		var windParticleFrequency = entryBuilder.startIntSlider(text("wind_particle_frequency"), wind.windParticleFrequency, 1, 500)
			.setDefaultValue(defaultConfig.wind.windParticleFrequency)
			.setSaveConsumer(newValue -> wind.windParticleFrequency = newValue)
			.setTooltip(tooltip("wind_particle_frequency"))
			.build();

		var windDisturbanceParticles = entryBuilder.startBooleanToggle(text("wind_disturbance_particles"), wind.windDisturbanceParticles)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticles)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticles = newValue)
			.setTooltip(tooltip("wind_disturbance_particles"))
			.build();

		var windParticleSpawnAttempts = entryBuilder.startIntSlider(text("wind_particle_spawn_attempts"), wind.windParticleSpawnAttempts, 1, 1000)
			.setDefaultValue(defaultConfig.wind.windParticleSpawnAttempts)
			.setSaveConsumer(newValue -> wind.windParticleSpawnAttempts = newValue)
			.setTooltip(tooltip("wind_particle_spawn_attempts"))
			.build();

		var windDisturbanceParticleFrequency = entryBuilder.startIntSlider(text("wind_disturbance_particle_frequency"), wind.windDisturbanceParticleFrequency, 1, 500)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticleFrequency)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticleFrequency = newValue)
			.setTooltip(tooltip("wind_disturbance_particle_frequency"))
			.build();

		var windDisturbanceParticleSpawnAttempts = entryBuilder.startIntSlider(text("wind_disturbance_particle_spawn_attempts"), wind.windDisturbanceParticleSpawnAttempts, 1, 1000)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticleSpawnAttempts)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticleSpawnAttempts = newValue)
			.setTooltip(tooltip("wind_disturbance_particle_spawn_attempts"))
			.build();

		var particleWindMovement = entryBuilder.startIntSlider(text("particle_wind_movement"), wind.particleWindMovement, 0, 500)
			.setDefaultValue(defaultConfig.wind.particleWindMovement)
			.setSaveConsumer(newValue -> wind.particleWindMovement = newValue)
			.setTooltip(tooltip("particle_wind_movement"))
			.build();

		var fireworkWindMovement = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("firework_wind_movement"), modifiedWind.fireworkWindMovement, 0, 500)
				.setDefaultValue(defaultConfig.wind.fireworkWindMovement)
				.setSaveConsumer(newValue -> wind.fireworkWindMovement = newValue)
				.setTooltip(tooltip("firework_wind_movement"))
				.build(),
			windClazz,
			"fireworkWindMovement",
			configInstance
		);

		var windCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("wind"),
			false,
			tooltip("wind"),
			cloudMovement,
			windParticles, windParticleFrequency, windParticleSpawnAttempts,
			windDisturbanceParticles, windDisturbanceParticleFrequency, windDisturbanceParticleSpawnAttempts,
			particleWindMovement, fireworkWindMovement
		);

		var useWilderWildFallingLeaves = entryBuilder.startBooleanToggle(text("wilder_wild_falling_leaves"), leafParticles.useWilderWildFallingLeaves)
			.setDefaultValue(defaultConfig.leafParticles.useWilderWildFallingLeaves)
			.setSaveConsumer(newValue -> leafParticles.useWilderWildFallingLeaves = newValue)
			.setTooltip(tooltip("wilder_wild_falling_leaves"))
			.build();

		var oakLeafParticles = entryBuilder.startIntSlider(text("oak_leaf_particles"), leafParticles.oakFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.oakFrequency)
			.setSaveConsumer(newValue -> leafParticles.oakFrequency = newValue)
			.setTooltip(tooltip("oak_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var spruceLeafParticles = entryBuilder.startIntSlider(text("spruce_leaf_particles"), leafParticles.spruceFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.spruceFrequency)
			.setSaveConsumer(newValue -> leafParticles.spruceFrequency = newValue)
			.setTooltip(tooltip("spruce_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var birchLeafParticles = entryBuilder.startIntSlider(text("birch_leaf_particles"), leafParticles.birchFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.birchFrequency)
			.setSaveConsumer(newValue -> leafParticles.birchFrequency = newValue)
			.setTooltip(tooltip("birch_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var jungleLeafParticles = entryBuilder.startIntSlider(text("jungle_leaf_particles"), leafParticles.jungleFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.jungleFrequency)
			.setSaveConsumer(newValue -> leafParticles.jungleFrequency = newValue)
			.setTooltip(tooltip("jungle_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var acaciaLeafParticles = entryBuilder.startIntSlider(text("acacia_leaf_particles"), leafParticles.acaciaFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.acaciaFrequency)
			.setSaveConsumer(newValue -> leafParticles.acaciaFrequency = newValue)
			.setTooltip(tooltip("acacia_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var darkOakLeafParticles = entryBuilder.startIntSlider(text("dark_oak_leaf_particles"), leafParticles.darkOakFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.darkOakFrequency)
			.setSaveConsumer(newValue -> leafParticles.darkOakFrequency = newValue)
			.setTooltip(tooltip("dark_oak_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var  paleOakLeafParticles = entryBuilder.startIntSlider(text("pale_oak_leaf_particles"), leafParticles.paleOakFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.paleOakFrequency)
			.setSaveConsumer(newValue -> leafParticles.paleOakFrequency = newValue)
			.setTooltip(tooltip("pale_oak_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var mangroveLeafParticles = entryBuilder.startIntSlider(text("mangrove_leaf_particles"), leafParticles.mangroveFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.mangroveFrequency)
			.setSaveConsumer(newValue -> leafParticles.mangroveFrequency = newValue)
			.setTooltip(tooltip("mangrove_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var cherryLeafParticles = entryBuilder.startIntSlider(text("cherry_leaf_particles"), leafParticles.cherryFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.cherryFrequency)
			.setSaveConsumer(newValue -> leafParticles.cherryFrequency = newValue)
			.setTooltip(tooltip("cherry_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var azaleaLeafParticles = entryBuilder.startIntSlider(text("azalea_leaf_particles"), leafParticles.azaleaFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.azaleaFrequency)
			.setSaveConsumer(newValue -> leafParticles.azaleaFrequency = newValue)
			.setTooltip(tooltip("azalea_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var floweringAzaleaLeafParticles = entryBuilder.startIntSlider(text("flowering_azalea_leaf_particles"), leafParticles.floweringAzaleaFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.floweringAzaleaFrequency)
			.setSaveConsumer(newValue -> leafParticles.floweringAzaleaFrequency = newValue)
			.setTooltip(tooltip("flowering_azalea_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var baobabLeafParticles = entryBuilder.startIntSlider(text("baobab_leaf_particles"), leafParticles.baobabFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.baobabFrequency)
			.setSaveConsumer(newValue -> leafParticles.baobabFrequency = newValue)
			.setTooltip(tooltip("baobab_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var cypressLeafParticles = entryBuilder.startIntSlider(text("cypress_leaf_particles"), leafParticles.cypressFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.cypressFrequency)
			.setSaveConsumer(newValue -> leafParticles.cypressFrequency = newValue)
			.setTooltip(tooltip("cypress_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var palmFrondParticles = entryBuilder.startIntSlider(text("palm_frond_particles"), leafParticles.palmFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.palmFrequency)
			.setSaveConsumer(newValue -> leafParticles.palmFrequency = newValue)
			.setTooltip(tooltip("palm_frond_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var mapleLeafParticles = entryBuilder.startIntSlider(text("maple_leaf_particles"), leafParticles.mapleFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.mapleFrequency)
			.setSaveConsumer(newValue -> leafParticles.mapleFrequency = newValue)
			.setTooltip(tooltip("maple_leaf_particles"))
			.setDisplayRequirement(Requirement.isTrue(() -> WWAmbienceAndMiscConfig.get().leafParticles.useWilderWildFallingLeaves))
			.build();

		var willowLeafParticles = entryBuilder.startIntSlider(text("willow_leaf_particles"), leafParticles.willowFrequency, 0, 500)
			.setDefaultValue(defaultConfig.leafParticles.willowFrequency)
			.setSaveConsumer(newValue -> leafParticles.willowFrequency = newValue)
			.setTooltip(tooltip("willow_leaf_particles"))
			.build();

		var leafParticleCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("leaf_particles"),
			false,
			tooltip("leaf_particles"),
			useWilderWildFallingLeaves,
			oakLeafParticles, spruceLeafParticles, birchLeafParticles, jungleLeafParticles, acaciaLeafParticles, darkOakLeafParticles,
			paleOakLeafParticles, mangroveLeafParticles, cherryLeafParticles, azaleaLeafParticles, floweringAzaleaLeafParticles,
			baobabLeafParticles, cypressLeafParticles, palmFrondParticles, mapleLeafParticles, willowLeafParticles
		);

		var deepDarkAmbience = entryBuilder.startBooleanToggle(text("deep_dark_ambience"), biomeAmbience.deepDarkAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.deepDarkAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.deepDarkAmbience = newValue)
			.setTooltip(tooltip("deep_dark_ambience"))
			.requireRestart()
			.build();

		var deepDarkFog = entryBuilder.startBooleanToggle(text("deep_dark_fog"), biomeAmbience.deepDarkFog)
			.setDefaultValue(defaultConfig.biomeAmbience.deepDarkFog)
			.setSaveConsumer(newValue -> biomeAmbience.deepDarkFog = newValue)
			.setTooltip(tooltip("deep_dark_fog"))
			.requireRestart()
			.build();

		var dripstoneCavesAmbience = entryBuilder.startBooleanToggle(text("dripstone_caves_ambience"), biomeAmbience.dripstoneCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.dripstoneCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.dripstoneCavesAmbience = newValue)
			.setTooltip(tooltip("dripstone_caves_ambience"))
			.requireRestart()
			.build();

		var lushCavesAmbience = entryBuilder.startBooleanToggle(text("lush_caves_ambience"), biomeAmbience.lushCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.lushCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.lushCavesAmbience = newValue)
			.setTooltip(tooltip("lush_caves_ambience"))
			.requireRestart()
			.build();

		var frozenCavesAmbience = entryBuilder.startBooleanToggle(text("frozen_caves_ambience"), biomeAmbience.frozenCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.frozenCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.frozenCavesAmbience = newValue)
			.setTooltip(tooltip("frozen_caves_ambience"))
			.requireRestart()
			.build();

		var frozenCavesFog = entryBuilder.startBooleanToggle(text("frozen_caves_fog"), biomeAmbience.frozenCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.frozenCavesFog)
			.setSaveConsumer(newValue -> biomeAmbience.frozenCavesFog = newValue)
			.setTooltip(tooltip("frozen_caves_fog"))
			.requireRestart()
			.build();

		var mesogleaCavesAmbience = entryBuilder.startBooleanToggle(text("mesoglea_caves_ambience"), biomeAmbience.mesogleaCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.mesogleaCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.mesogleaCavesAmbience = newValue)
			.setTooltip(tooltip("mesoglea_caves_ambience"))
			.requireRestart()
			.build();

		var mesogleaCavesFog = entryBuilder.startBooleanToggle(text("mesoglea_caves_fog"), biomeAmbience.mesogleaCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.mesogleaCavesFog)
			.setSaveConsumer(newValue -> biomeAmbience.mesogleaCavesFog = newValue)
			.setTooltip(tooltip("mesoglea_caves_fog"))
			.requireRestart()
			.build();

		var magmaticCavesAmbience = entryBuilder.startBooleanToggle(text("magmatic_caves_ambience"), biomeAmbience.magmaticCavesAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.magmaticCavesAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.magmaticCavesAmbience = newValue)
			.setTooltip(tooltip("magmatic_caves_ambience"))
			.requireRestart()
			.build();

		var magmaticCavesFog = entryBuilder.startBooleanToggle(text("magmatic_caves_fog"), biomeAmbience.magmaticCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.magmaticCavesFog)
			.setSaveConsumer(newValue -> biomeAmbience.magmaticCavesFog = newValue)
			.setTooltip(tooltip("magmatic_caves_fog"))
			.requireRestart()
			.build();

		var magmaticCavesParticles = entryBuilder.startBooleanToggle(text("magmatic_caves_particles"), biomeAmbience.magmaticCavesFog)
			.setDefaultValue(defaultConfig.biomeAmbience.magmaticCavesParticles)
			.setSaveConsumer(newValue -> biomeAmbience.magmaticCavesParticles = newValue)
			.setTooltip(tooltip("magmatic_caves_particles"))
			.requireRestart()
			.build();

		var biomeAmbienceCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_ambience"),
			false,
			tooltip("biome_ambience"),
			deepDarkAmbience, deepDarkFog,
			dripstoneCavesAmbience,
			lushCavesAmbience,
			frozenCavesAmbience, frozenCavesFog,
			magmaticCavesAmbience, magmaticCavesFog, magmaticCavesParticles,
			mesogleaCavesAmbience, mesogleaCavesFog
		);

		var hotBiomes = entryBuilder.startBooleanToggle(text("hot_water"), waterColors.modifyHotWater)
			.setDefaultValue(defaultConfig.waterColors.modifyHotWater)
			.setSaveConsumer(newValue -> waterColors.modifyHotWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("hot_water"))
			.requireRestart()
			.build();

		var lukewarmBiomes = entryBuilder.startBooleanToggle(text("lukewarm_water"), waterColors.modifyLukewarmWater)
			.setDefaultValue(defaultConfig.waterColors.modifyLukewarmWater)
			.setSaveConsumer(newValue -> waterColors.modifyLukewarmWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("lukewarm_water"))
			.requireRestart()
			.build();

		var snowyBiomes = entryBuilder.startBooleanToggle(text("snowy_water"), waterColors.modifySnowyWater)
			.setDefaultValue(defaultConfig.waterColors.modifySnowyWater)
			.setSaveConsumer(newValue -> waterColors.modifySnowyWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("snowy_water"))
			.requireRestart()
			.build();

		var frozenBiomes = entryBuilder.startBooleanToggle(text("frozen_water"), waterColors.modifyFrozenWater)
			.setDefaultValue(defaultConfig.waterColors.modifyFrozenWater)
			.setSaveConsumer(newValue -> waterColors.modifyFrozenWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("frozen_water"))
			.requireRestart()
			.build();

		var waterColorCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("water_colors"),
			false,
			tooltip("water_colors"),
			hotBiomes, lukewarmBiomes, snowyBiomes, frozenBiomes
		);

		var badlandsFoliage = entryBuilder.startBooleanToggle(text("badlands_foliage"), vegetationColors.badlandsFoliage)
			.setDefaultValue(defaultConfig.vegetationColors.badlandsFoliage)
			.setSaveConsumer(newValue -> vegetationColors.badlandsFoliage = newValue)
			.setYesNoTextSupplier(bool -> text("vegetation_colors." + bool))
			.setTooltip(tooltip("badlands_foliage"))
			.requireRestart()
			.build();

		var vegetationColorCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("vegetation_colors"),
			false,
			tooltip("vegetation_colors"),
			badlandsFoliage
		);

		var wilderForestMusic = entryBuilder.startBooleanToggle(text("wilder_forest_music"), music.wilderForestMusic)
			.setDefaultValue(defaultConfig.music.wilderForestMusic)
			.setSaveConsumer(newValue -> music.wilderForestMusic = newValue)
			.setTooltip(tooltip("wilder_forest_music"))
			.requireRestart()
			.build();

		var wilderLushCavesMusic = entryBuilder.startBooleanToggle(text("wilder_lush_caves_music"), music.wilderLushCavesMusic)
			.setDefaultValue(defaultConfig.music.wilderLushCavesMusic)
			.setSaveConsumer(newValue -> music.wilderLushCavesMusic = newValue)
			.setTooltip(tooltip("wilder_lush_caves_music"))
			.requireRestart()
			.build();

		var wilderDripstoneCavesMusic = entryBuilder.startBooleanToggle(text("wilder_dripstone_caves_music"), music.wilderDripstoneCavesMusic)
			.setDefaultValue(defaultConfig.music.wilderDripstoneCavesMusic)
			.setSaveConsumer(newValue -> music.wilderDripstoneCavesMusic = newValue)
			.setTooltip(tooltip("wilder_dripstone_caves_music"))
			.requireRestart()
			.build();

		var wilderCherryGroveMusic = entryBuilder.startBooleanToggle(text("wilder_cherry_grove_music"), music.wilderCherryGroveMusic)
			.setDefaultValue(defaultConfig.music.wilderCherryGroveMusic)
			.setSaveConsumer(newValue -> music.wilderCherryGroveMusic = newValue)
			.setTooltip(tooltip("wilder_cherry_grove_music"))
			.requireRestart()
			.build();

		var wilderGroveMusic = entryBuilder.startBooleanToggle(text("wilder_grove_music"), music.wilderGroveMusic)
			.setDefaultValue(defaultConfig.music.wilderGroveMusic)
			.setSaveConsumer(newValue -> music.wilderGroveMusic = newValue)
			.setTooltip(tooltip("wilder_grove_music"))
			.requireRestart()
			.build();

		var wilderJungleMusic = entryBuilder.startBooleanToggle(text("wilder_jungle_music"), music.wilderJungleMusic)
			.setDefaultValue(defaultConfig.music.wilderJungleMusic)
			.setSaveConsumer(newValue -> music.wilderJungleMusic = newValue)
			.setTooltip(tooltip("wilder_jungle_music"))
			.requireRestart()
			.build();

		var wilderSnowyMusic = entryBuilder.startBooleanToggle(text("wilder_snowy_music"), music.wilderSnowyMusic)
			.setDefaultValue(defaultConfig.music.wilderSnowyMusic)
			.setSaveConsumer(newValue -> music.wilderSnowyMusic = newValue)
			.setTooltip(tooltip("wilder_snowy_music"))
			.requireRestart()
			.build();

		var wilderExtraMusic = entryBuilder.startBooleanToggle(text("wilder_extra_music"), music.wilderExtraMusic)
			.setDefaultValue(defaultConfig.music.wilderExtraMusic)
			.setSaveConsumer(newValue -> music.wilderExtraMusic = newValue)
			.setTooltip(tooltip("wilder_extra_music"))
			.requireRestart()
			.build();

		var distortedDyingForestMusic = entryBuilder.startBooleanToggle(text("distorted_dying_forest_music"), music.distortedDyingForestMusic)
			.setDefaultValue(defaultConfig.music.distortedDyingForestMusic)
			.setSaveConsumer(newValue -> music.distortedDyingForestMusic = newValue)
			.setTooltip(tooltip("distorted_dying_forest_music"))
			.requireRestart()
			.build();

		var musicCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("music"),
			false,
			tooltip("biome_music"),
			wilderForestMusic, wilderLushCavesMusic, wilderDripstoneCavesMusic, wilderCherryGroveMusic, wilderGroveMusic, wilderJungleMusic, wilderSnowyMusic, wilderExtraMusic, distortedDyingForestMusic
		);
	}
}
