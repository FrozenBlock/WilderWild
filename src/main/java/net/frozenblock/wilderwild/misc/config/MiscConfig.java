package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "misc")
public final class MiscConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	public static class BiomeAmbienceConfig {
		public boolean deepDarkAmbience = DefaultMiscConfig.BiomeAmbienceConfig.deepDarkAmbience;
		public boolean dripstoneCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.dripstoneCavesAmbience;
		public boolean lushCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.lushCavesAmbience;
	}

	public static class BiomeMusicConfig {
		public boolean birchForestMusic = DefaultMiscConfig.BiomeMusicConfig.birchForestMusic;
		public boolean flowerForestMusic = DefaultMiscConfig.BiomeMusicConfig.flowerForestMusic;
	}

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().misc;
		var biomeAmbience = config.biomeAmbience;
		var biomeMusic = config.biomeMusic;
		category.setBackground(WilderSharedConstants.id("textures/config/misc.png"));

		var deepDarkAmbience = entryBuilder.startBooleanToggle(text("deep_dark_ambience"), biomeAmbience.deepDarkAmbience)
				.setDefaultValue(DefaultMiscConfig.BiomeAmbienceConfig.deepDarkAmbience)
				.setSaveConsumer(newValue -> biomeAmbience.deepDarkAmbience = newValue)
				.setTooltip(tooltip("deep_dark_ambience"))
				.requireRestart()
				.build();

		var dripstoneCavesAmbience = entryBuilder.startBooleanToggle(text("dripstone_caves_ambience"), biomeAmbience.dripstoneCavesAmbience)
				.setDefaultValue(DefaultMiscConfig.BiomeAmbienceConfig.dripstoneCavesAmbience)
				.setSaveConsumer(newValue -> biomeAmbience.dripstoneCavesAmbience = newValue)
				.setTooltip(tooltip("dripstone_caves_ambience"))
				.requireRestart()
				.build();

		var lushCavesAmbience = entryBuilder.startBooleanToggle(text("lush_caves_ambience"), biomeAmbience.lushCavesAmbience)
				.setDefaultValue(DefaultMiscConfig.BiomeAmbienceConfig.lushCavesAmbience)
				.setSaveConsumer(newValue -> biomeAmbience.lushCavesAmbience = newValue)
				.setTooltip(tooltip("lush_caves_ambience"))
				.requireRestart()
				.build();

		var biomeAmbienceCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_ambience"),
				false,
				tooltip("biome_ambience"),
				deepDarkAmbience, dripstoneCavesAmbience, lushCavesAmbience
		);

		var birchForestMusic = entryBuilder.startBooleanToggle(text("birch_forest_music"), biomeMusic.birchForestMusic)
				.setDefaultValue(DefaultMiscConfig.BiomeMusicConfig.birchForestMusic)
				.setSaveConsumer(newValue -> biomeMusic.birchForestMusic = newValue)
				.setTooltip(tooltip("birch_forest_music"))
				.requireRestart()
				.build();

		var flowerForestMusic = entryBuilder.startBooleanToggle(text("flower_forest_music"), biomeMusic.flowerForestMusic)
				.setDefaultValue(DefaultMiscConfig.BiomeMusicConfig.flowerForestMusic)
				.setSaveConsumer(newValue -> biomeMusic.flowerForestMusic = newValue)
				.setTooltip(tooltip("flower_forest_music"))
				.requireRestart()
				.build();

		var biomeMusicCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_music"),
				false,
				tooltip("biome_music"),
				birchForestMusic, flowerForestMusic
		);
	}
}
