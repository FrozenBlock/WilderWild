/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class WWGameEvents {
	public static final Reference<GameEvent> SCULK_SENSOR_ACTIVATE = register("sculk_sensor_activate");
	public static final Reference<GameEvent> BIG_FALL = register("big_fall");

	private WWGameEvents() {
		throw new UnsupportedOperationException("WWGameEvents contains only static declarations.");
	}

	public static void registerEvents() {
		WWConstants.logWithModId("Registering GameEvents for", WWConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static Reference<GameEvent> register(@NotNull String path) {
		return register(path, 16);
	}

	@NotNull
	private static Reference<GameEvent> register(@NotNull String path, int notificationRadius) {
		ResourceLocation key = WWConstants.id(path);
		return Registry.registerForHolder(BuiltInRegistries.GAME_EVENT, key, new GameEvent(notificationRadius));
	}
}
