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

import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class RegisterGameEvents {
	public static final GameEvent SCULK_SENSOR_ACTIVATE = register("sculk_sensor_activate", 16);
	public static final GameEvent TENDRIL_EXTRACT_XP = register("hanging_tendril_extract_xp", 16);

	private RegisterGameEvents() {
		throw new UnsupportedOperationException("RegisterGameEvents contains only static declarations.");
	}

	public static void registerEvents() {
		WilderConstants.logWithModId("Registering GameEvents for", WilderConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static GameEvent register(@NotNull String path, int notificationRadius) {
		var key = WilderConstants.string(path);
		return Registry.register(BuiltInRegistries.GAME_EVENT, key, new GameEvent(notificationRadius));
	}
}
