package net.frozenblock.wilderwild.misc.config;
/*
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "misc")
public final class MiscConfig {

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	public static class BiomeAmbienceConfig {
		public boolean deepDarkAmbience = true;
		public boolean dripstoneCavesAmbience = true;
		public boolean lushCavesAmbience = true;
		public boolean jellyfishCavesAmbience = true;
	}

	public static class BiomeMusicConfig {
		public boolean birchForestMusic = true;
		public boolean flowerForestMusic = true;
	}

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().misc;
		var biomeAmbience = config.biomeAmbience;
		var biomeMusic = config.biomeMusic;
		category.setBackground(WilderWild.id("textures/config/misc.png"));

		var deepDarkAmbience = entryBuilder.startBooleanToggle(text("deep_dark_ambience"), biomeAmbience.deepDarkAmbience)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> biomeAmbience.deepDarkAmbience = newValue)
				.setTooltip(tooltip("deep_dark_ambience"))
				.requireRestart()
				.build();

		var dripstoneCavesAmbience = entryBuilder.startBooleanToggle(text("dripstone_caves_ambience"), biomeAmbience.dripstoneCavesAmbience)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> biomeAmbience.dripstoneCavesAmbience = newValue)
				.setTooltip(tooltip("dripstone_caves_ambience"))
				.requireRestart()
				.build();

		var biomeAmbienceCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_ambience"),
				false,
				tooltip("biome_ambience"),
				deepDarkAmbience, dripstoneCavesAmbience
		);
	}
}*/
