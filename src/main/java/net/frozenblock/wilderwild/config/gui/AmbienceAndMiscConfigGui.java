/*
 * Copyright 2023-2024 FrozenBlock
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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class AmbienceAndMiscConfigGui {
	private AmbienceAndMiscConfigGui() {
		throw new UnsupportedOperationException("AmbienceAndMiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = AmbienceAndMiscConfig.get(true);
		var modifiedConfig = AmbienceAndMiscConfig.getWithSync();
		Class<? extends AmbienceAndMiscConfig> clazz = config.getClass();
		Config<? extends AmbienceAndMiscConfig> configInstance = AmbienceAndMiscConfig.INSTANCE;
		var defaultConfig = AmbienceAndMiscConfig.INSTANCE.defaultInstance();
		var biomeAmbience = config.biomeAmbience;
		var biomeMusic = config.biomeMusic;
		var waterColors = config.waterColors;
		var wind = config.wind;
		var modifiedWind = modifiedConfig.wind;
		Class<? extends AmbienceAndMiscConfig.Wind> windClazz = wind.getClass();
		category.setBackground(WilderSharedConstants.id("textures/config/ambience_and_misc.png"));

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

		var windDisturbanceParticleFrequency = entryBuilder.startIntSlider(text("wind_disturbance_particle_frequency"), wind.windDisturbanceParticleFrequency, 1, 500)
			.setDefaultValue(defaultConfig.wind.windDisturbanceParticleFrequency)
			.setSaveConsumer(newValue -> wind.windDisturbanceParticleFrequency = newValue)
			.setTooltip(tooltip("wind_disturbance_particle_frequency"))
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
			cloudMovement, windParticles, windParticleFrequency, windDisturbanceParticles, windDisturbanceParticleFrequency,
			particleWindMovement, fireworkWindMovement
		);

		var deepDarkAmbience = entryBuilder.startBooleanToggle(text("deep_dark_ambience"), biomeAmbience.deepDarkAmbience)
			.setDefaultValue(defaultConfig.biomeAmbience.deepDarkAmbience)
			.setSaveConsumer(newValue -> biomeAmbience.deepDarkAmbience = newValue)
			.setTooltip(tooltip("deep_dark_ambience"))
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

		var biomeAmbienceCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_ambience"),
			false,
			tooltip("biome_ambience"),
			deepDarkAmbience, dripstoneCavesAmbience, lushCavesAmbience
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

		var wilderForestMusic = entryBuilder.startBooleanToggle(text("wilder_forest_music"), biomeMusic.wilderForestMusic)
			.setDefaultValue(defaultConfig.biomeMusic.wilderForestMusic)
			.setSaveConsumer(newValue -> biomeMusic.wilderForestMusic = newValue)
			.setTooltip(tooltip("wilder_forest_music"))
			.requireRestart()
			.build();

		var biomeMusicCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_music"),
			false,
			tooltip("biome_music"),
			wilderForestMusic
		);
	}
}
