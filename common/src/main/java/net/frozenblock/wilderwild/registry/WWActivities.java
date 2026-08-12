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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.FrozenLibConstants;
import net.frozenblock.lib.platform.RegistryHelper;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;

public final class WWActivities {
	private static final FrozenDeferredRegister<Activity> REGISTER = RegistryHelper.createDeferredRegister(Registries.ACTIVITY, FrozenLibConstants.MOD_ID);
	public static final FrozenHolder<Activity, Activity> STAND_UP = register("stand_up");
	public static final FrozenHolder<Activity, Activity> PRE_SEARCH = register("pre_search");
	public static final FrozenHolder<Activity, Activity> SEARCH = register("search");
	public static final FrozenHolder<Activity, Activity> ESCAPE = register("escape");
	public static final FrozenHolder<Activity, Activity> POST_ESCAPE = register("post_escape");
	public static final FrozenHolder<Activity, Activity> CHASE = register("chase");
	public static final FrozenHolder<Activity, Activity> CALL = register("call");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static FrozenHolder<Activity, Activity> register(String name) {
		return WWActivities.REGISTER.register(name, () -> new Activity(WWConstants.safeString(name)));
	}
}
