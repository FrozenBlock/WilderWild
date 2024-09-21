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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class WWGameEvents {
	public static final GameEvent SCULK_SENSOR_ACTIVATE = register("sculk_sensor_activate", 16);
	public static final GameEvent TENDRIL_EXTRACT_XP = register("hanging_tendril_extract_xp", 16);

	private WWGameEvents() {
		throw new UnsupportedOperationException("WWGameEvents contains only static declarations.");
	}

	public static void registerEvents() {
		WWConstants.logWithModId("Registering GameEvents for", WWConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static GameEvent register(@NotNull String path, int notificationRadius) {
		var key = WWConstants.string(path);
		return Registry.register(BuiltInRegistries.GAME_EVENT, key, new GameEvent(key, notificationRadius));
	}
}
