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
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.NotNull;

public class WWActivities {
	public static final Activity LAY_DOWN = register("lay_down");
	public static final Activity STAND_UP = register("stand_up");
	public static final Activity IDLE_LAYING_DOWN = register("idle_laying_down");
	public static final Activity SEARCH = register("search");
	public static final Activity ESCAPE = register("escape");

	private WWActivities() {
		throw new UnsupportedOperationException("WWActivities contains only static declarations.");
	}

	public static void init() {
	}

	private static @NotNull Activity register(String string) {
		return Registry.register(BuiltInRegistries.ACTIVITY, string, new Activity(WWConstants.safeString(string)));
	}
}
