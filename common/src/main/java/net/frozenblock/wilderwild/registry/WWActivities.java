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
import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;

public final class WWActivities {
	public static final FrozenHolder<Activity, Activity> STAND_UP;
	public static final FrozenHolder<Activity, Activity> PRE_SEARCH;
	public static final FrozenHolder<Activity, Activity> SEARCH;
	public static final FrozenHolder<Activity, Activity> ESCAPE;
	public static final FrozenHolder<Activity, Activity> POST_ESCAPE;
	public static final FrozenHolder<Activity, Activity> CHASE;
	public static final FrozenHolder<Activity, Activity> CALL;

	static {
		var register = FrozenLibInitPlatformUtils.REGISTRY.createDeferredRegister(Registries.ACTIVITY, FrozenLibConstants.MOD_ID);
		STAND_UP = register(register, "stand_up");
		PRE_SEARCH = register(register, "pre_search");
		SEARCH = register(register, "search");
		ESCAPE = register(register, "escape");
		POST_ESCAPE = register(register, "post_escape");
		CHASE = register(register, "chase");
		CALL = register(register, "call");

		register.register();
	}

	public static void init() {}

	private static FrozenHolder<Activity, Activity> register(FrozenDeferredRegister<Activity> register, String name) {
		return register.register(name, () -> new Activity(WWConstants.safeString(name)));
	}
}
