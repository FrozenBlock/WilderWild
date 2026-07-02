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

import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gameevent.GameEvent;

public final class WWGameEvents {
	private static final FrozenDeferredRegister<GameEvent> REGISTER = FrozenDeferredRegister.create(
		Registries.GAME_EVENT,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<GameEvent, GameEvent> BIG_FALL = register("big_fall");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static FrozenHolder<GameEvent, GameEvent> register(String name) {
		return register(name, 16);
	}

	private static FrozenHolder<GameEvent, GameEvent> register(String name, int notificationRadius) {
		return REGISTER.register(name, () -> new GameEvent(notificationRadius));
	}
}
