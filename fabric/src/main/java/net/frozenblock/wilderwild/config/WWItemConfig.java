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

package net.frozenblock.wilderwild.config;

import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;

public final class WWItemConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(WWConstants.id("item")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> PROJECTILE_BREAK_PARTICLES = CONFIG.entry("projectileBreakParticles", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> RESTRICT_INSTRUMENT_SOUNDS = CONFIG.entry("restrictInstrumentSound", EntryType.BOOL, true);

	// PROJECTILE LANDING SOUNDS
	public static final ConfigEntry<Boolean> SNOWBALL_LANDING_SOUNDS = CONFIG.entry("projectileLandingSounds/snowballLandingSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> EGG_LANDING_SOUNDS = CONFIG.entry("projectileLandingSounds/eggLandingSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> ENDER_PEARL_LANDING_SOUNDS = CONFIG.entry("projectileLandingSounds/enderPearlLandingSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POTION_LANDING_SOUNDS = CONFIG.entry("projectileLandingSounds/potionLandingSounds", EntryType.BOOL, true);
}
