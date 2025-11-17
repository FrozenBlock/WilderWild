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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;

public final class WWActivities {
	public static final Activity STAND_UP = register("stand_up");
	public static final Activity PRE_SEARCH = register("pre_search");
	public static final Activity SEARCH = register("search");
	public static final Activity ESCAPE = register("escape");
	public static final Activity POST_ESCAPE = register("post_escape");
	public static final Activity CHASE = register("chase");
	public static final Activity CALL = register("call");

	private WWActivities() {
		throw new UnsupportedOperationException("WWActivities contains only static declarations.");
	}

	public static void init() {
	}

	private static Activity register(String string) {
		return Registry.register(BuiltInRegistries.ACTIVITY, string, new Activity(WWConstants.safeString(string)));
	}
}
