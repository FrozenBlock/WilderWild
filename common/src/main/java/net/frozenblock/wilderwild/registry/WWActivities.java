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
import net.frozenblock.lib.platform.api.registry.DeferredActivity;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;

public final class WWActivities {
	private static final DeferredRegister.Activities REGISTER = RegistryHelper.createDeferredActivitiesRegister(FrozenLibConstants.MOD_ID);

	public static final DeferredActivity STAND_UP = register("stand_up");
	public static final DeferredActivity PRE_SEARCH = register("pre_search");
	public static final DeferredActivity SEARCH = register("search");
	public static final DeferredActivity ESCAPE = register("escape");
	public static final DeferredActivity POST_ESCAPE = register("post_escape");
	public static final DeferredActivity CHASE = register("chase");
	public static final DeferredActivity CALL = register("call");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static DeferredActivity register(String name) {
		return REGISTER.register(name);
	}

	private WWActivities() {}
}
