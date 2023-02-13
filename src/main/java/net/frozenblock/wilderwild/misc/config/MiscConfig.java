/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig;

@Config(name = "misc")
public final class MiscConfig implements ConfigData {

	public boolean cloudMovement = DefaultMiscConfig.CLOUD_MOVEMENT;

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	public static class BiomeAmbienceConfig {
		public boolean deepDarkAmbience = DefaultMiscConfig.BiomeAmbienceConfig.DEEP_DARK_AMBIENCE;
		public boolean dripstoneCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
		public boolean lushCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.LUSH_CAVES_AMBIENCE;
	}

	public static class BiomeMusicConfig {
		public boolean wilderForestMusic = DefaultMiscConfig.BiomeMusicConfig.WILDER_FOREST_MUSIC;
	}

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().misc;
		var biomeAmbience = config.biomeAmbience;
		var biomeMusic = config.biomeMusic;
		category.setBackground(WilderSharedConstants.id("textures/config/misc.png"));

		var cloudMovement = category.addEntry(entryBuilder.startBooleanToggle(text("cloud_movement"), config.cloudMovement)
				.setDefaultValue(DefaultMiscConfig.CLOUD_MOVEMENT)
				.setSaveConsumer(newValue -> config.cloudMovement = newValue)
				.setTooltip(tooltip("cloud_movement"))
				.build()
		);

		var deepDarkAmbience = entryBuilder.startBooleanToggle(text("deep_dark_ambience"), biomeAmbience.deepDarkAmbience)
				.setDefaultValue(DefaultMiscConfig.BiomeAmbienceConfig.DEEP_DARK_AMBIENCE)
				.setSaveConsumer(newValue -> biomeAmbience.deepDarkAmbience = newValue)
				.setTooltip(tooltip("deep_dark_ambience"))
				.requireRestart()
				.build();

		var dripstoneCavesAmbience = entryBuilder.startBooleanToggle(text("dripstone_caves_ambience"), biomeAmbience.dripstoneCavesAmbience)
				.setDefaultValue(DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE)
				.setSaveConsumer(newValue -> biomeAmbience.dripstoneCavesAmbience = newValue)
				.setTooltip(tooltip("dripstone_caves_ambience"))
				.requireRestart()
				.build();

		var lushCavesAmbience = entryBuilder.startBooleanToggle(text("lush_caves_ambience"), biomeAmbience.lushCavesAmbience)
				.setDefaultValue(DefaultMiscConfig.BiomeAmbienceConfig.LUSH_CAVES_AMBIENCE)
				.setSaveConsumer(newValue -> biomeAmbience.lushCavesAmbience = newValue)
				.setTooltip(tooltip("lush_caves_ambience"))
				.requireRestart()
				.build();

		var biomeAmbienceCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_ambience"),
				false,
				tooltip("biome_ambience"),
				deepDarkAmbience, dripstoneCavesAmbience, lushCavesAmbience
		);

		var wilderForestMusic = entryBuilder.startBooleanToggle(text("wilder_forest_music"), biomeMusic.wilderForestMusic)
				.setDefaultValue(DefaultMiscConfig.BiomeMusicConfig.WILDER_FOREST_MUSIC)
				.setSaveConsumer(newValue -> biomeMusic.wilderForestMusic = newValue)
				.setTooltip(tooltip("wilder_forest_music"))
				.requireRestart()
				.build();
	}
}
