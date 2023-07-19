package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.MiscConfig;
import net.frozenblock.wilderwild.config.defaults.DefaultMiscConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class MiscConfigGui {
	private MiscConfigGui() {
		throw new UnsupportedOperationException("MiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = MiscConfig.get();
		var biomeAmbience = config.biomeAmbience;
		var biomeMusic = config.biomeMusic;
		category.setBackground(WilderSharedConstants.id("textures/config/misc.png"));

		var cloudMovement = category.addEntry(entryBuilder.startBooleanToggle(text("cloud_movement"), config.cloudMovement)
			.setDefaultValue(DefaultMiscConfig.CLOUD_MOVEMENT)
			.setSaveConsumer(newValue -> config.cloudMovement = newValue)
			.setTooltip(tooltip("cloud_movement"))
			.build()
		);

		var particleWindMovement = category.addEntry(entryBuilder.startIntSlider(text("particle_wind_movement"), config.particleWindMovement, 0, 500)
			.setDefaultValue(DefaultMiscConfig.PARTICLE_WIND_MOVEMENT)
			.setSaveConsumer(newValue -> config.particleWindMovement = newValue)
			.setTooltip(tooltip("particle_wind_movement"))
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

		var biomeAmbienceCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_ambience"),
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
