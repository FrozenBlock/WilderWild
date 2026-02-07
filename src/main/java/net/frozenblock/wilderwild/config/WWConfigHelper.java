package net.frozenblock.wilderwild.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;

public final class WWConfigHelper {

	public static IntegerSliderEntry zeroToFiveHundredEntry(ConfigEntryBuilder builder, String key, ConfigEntry<Integer> configEntry) {
		return intSliderEntry(builder, key, configEntry, 0, 500);
	}

	public static IntegerSliderEntry oneToFiveHundredEntry(ConfigEntryBuilder builder, String key, ConfigEntry<Integer> configEntry) {
		return intSliderEntry(builder, key, configEntry, 1, 500);
	}

	public static IntegerSliderEntry zeroToOneThousandEntry(ConfigEntryBuilder builder, String key, ConfigEntry<Integer> configEntry) {
		return intSliderEntry(builder, key, configEntry, 0, 1000);
	}

	public static IntegerSliderEntry oneToOneThousandEntry(ConfigEntryBuilder builder, String key, ConfigEntry<Integer> configEntry) {
		return intSliderEntry(builder, key, configEntry, 1, 1000);
	}

	public static IntegerSliderEntry intSliderEntry(ConfigEntryBuilder builder, String key, ConfigEntry<Integer> configEntry, int min, int max) {
		return FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text(key), configEntry.get(), min, max).setTooltip(tooltip(key)),
			configEntry
		);
	}

	public static BooleanListEntry booleanEntry(ConfigEntryBuilder builder, String key, ConfigEntry<Boolean> configEntry) {
		return FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text(key), configEntry.get()).setTooltip(tooltip(key)),
			configEntry
		);
	}
}
