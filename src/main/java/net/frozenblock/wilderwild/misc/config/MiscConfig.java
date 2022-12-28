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
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "misc")
public final class MiscConfig implements ConfigData {

	public boolean cloudMovement = DefaultMiscConfig.CLOUD_MOVEMENT;

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	protected static class BiomeAmbienceConfig {
		public boolean deepDarkAmbience = DefaultMiscConfig.BiomeAmbienceConfig.DEEP_DARK_AMBIENCE;
		public boolean dripstoneCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
		public boolean lushCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.LUSH_CAVES_AMBIENCE;
	}

	protected static class BiomeMusicConfig {
		public boolean birchForestMusic = DefaultMiscConfig.BiomeMusicConfig.BIRCH_FOREST_MUSIC;
		public boolean flowerForestMusic = DefaultMiscConfig.BiomeMusicConfig.FLOWER_FOREST_MUSIC;
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

		var birchForestMusic = entryBuilder.startBooleanToggle(text("birch_forest_music"), biomeMusic.birchForestMusic)
				.setDefaultValue(DefaultMiscConfig.BiomeMusicConfig.BIRCH_FOREST_MUSIC)
				.setSaveConsumer(newValue -> biomeMusic.birchForestMusic = newValue)
				.setTooltip(tooltip("birch_forest_music"))
				.requireRestart()
				.build();

		var flowerForestMusic = entryBuilder.startBooleanToggle(text("flower_forest_music"), biomeMusic.flowerForestMusic)
				.setDefaultValue(DefaultMiscConfig.BiomeMusicConfig.FLOWER_FOREST_MUSIC)
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
