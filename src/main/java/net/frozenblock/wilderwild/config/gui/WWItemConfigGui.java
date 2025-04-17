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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWItemConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWItemConfigGui {
	private WWItemConfigGui() {
		throw new UnsupportedOperationException("ItemConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WWItemConfig.get(true);
		Class<? extends WWItemConfig> clazz = config.getClass();
		Config<? extends WWItemConfig> configInstance = WWItemConfig.INSTANCE;
		var modifiedConfig = WWItemConfig.getWithSync();
		var defaultConfig = WWItemConfig.INSTANCE.defaultInstance();
		var projectileLandingSounds = config.projectileLandingSounds;
		var modifiedProjectileLandingSounds = modifiedConfig.projectileLandingSounds;

		var snowballLandingSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("snowball_landing_sounds"), modifiedProjectileLandingSounds.snowballLandingSounds)
				.setDefaultValue(defaultConfig.projectileLandingSounds.snowballLandingSounds)
				.setSaveConsumer(newValue -> projectileLandingSounds.snowballLandingSounds = newValue)
				.setTooltip(tooltip("snowball_landing_sounds"))
				.build(),
			config.projectileLandingSounds.getClass(),
			"snowballLandingSounds",
			configInstance
		);

		var eggLandingSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("egg_landing_sounds"), modifiedProjectileLandingSounds.eggLandingSounds)
				.setDefaultValue(defaultConfig.projectileLandingSounds.eggLandingSounds)
				.setSaveConsumer(newValue -> projectileLandingSounds.eggLandingSounds = newValue)
				.setTooltip(tooltip("egg_landing_sounds"))
				.build(),
			config.projectileLandingSounds.getClass(),
			"eggLandingSounds",
			configInstance
		);

		var enderPearlLandingSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("ender_pearl_landing_sounds"), modifiedProjectileLandingSounds.enderPearlLandingSounds)
				.setDefaultValue(defaultConfig.projectileLandingSounds.enderPearlLandingSounds)
				.setSaveConsumer(newValue -> projectileLandingSounds.enderPearlLandingSounds = newValue)
				.setTooltip(tooltip("ender_pearl_landing_sounds"))
				.build(),
			config.projectileLandingSounds.getClass(),
			"enderPearlLandingSounds",
			configInstance
		);

		var potionLandingSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("potion_landing_sounds"), modifiedProjectileLandingSounds.potionLandingSounds)
				.setDefaultValue(defaultConfig.projectileLandingSounds.potionLandingSounds)
				.setSaveConsumer(newValue -> projectileLandingSounds.potionLandingSounds = newValue)
				.setTooltip(tooltip("potion_landing_sounds"))
				.build(),
			config.projectileLandingSounds.getClass(),
			"potionLandingSounds",
			configInstance
		);

		var projectileLandingSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("projectile_landing_sounds"),
			false,
			tooltip("projectile_landing_sounds"),
			snowballLandingSounds, eggLandingSounds, enderPearlLandingSounds, potionLandingSounds
		);

		var projectileBreakParticles = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("projectile_break_particles"), modifiedConfig.projectileBreakParticles)
					.setDefaultValue(defaultConfig.projectileBreakParticles)
					.setSaveConsumer(newValue -> config.projectileBreakParticles = newValue)
					.setTooltip(tooltip("projectile_break_particles"))
					.build(),
				clazz,
				"projectileBreakParticles",
				configInstance
			)
		);

		var restrictInstrumentSound = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("restrict_instrument_sound"), modifiedConfig.restrictInstrumentSound)
					.setDefaultValue(defaultConfig.restrictInstrumentSound)
					.setSaveConsumer(newValue -> config.restrictInstrumentSound = newValue)
					.setTooltip(tooltip("restrict_instrument_sound"))
					.build(),
				clazz,
				"restrictInstrumentSound",
				configInstance
			)
		);
	}
}
