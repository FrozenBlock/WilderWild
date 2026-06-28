/*
 * Copyright 2025-2026 FrozenBlock
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
import static net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper.booleanEntry;
import static net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper.createSubCategory;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.lib.platform.api.ClientOnly;
import net.frozenblock.wilderwild.config.WWItemConfig;

@ClientOnly
public final class WWItemConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "restrict_instrument_sound", WWItemConfig.RESTRICT_INSTRUMENT_SOUNDS));
		category.addEntry(booleanEntry(builder, "projectile_break_particles", WWItemConfig.PROJECTILE_BREAK_PARTICLES));

		// PROJECTILE LANDING SOUNDS
		createSubCategory(builder, category, text("projectile_landing_sounds"), tooltip("projectile_landing_sounds"),
			booleanEntry(builder, "snowball_landing_sounds", WWItemConfig.SNOWBALL_LANDING_SOUNDS),
			booleanEntry(builder, "egg_landing_sounds", WWItemConfig.EGG_LANDING_SOUNDS),
			booleanEntry(builder, "ender_pearl_landing_sounds", WWItemConfig.ENDER_PEARL_LANDING_SOUNDS),
			booleanEntry(builder, "potion_landing_sounds", WWItemConfig.POTION_LANDING_SOUNDS)
		);
	}
}
