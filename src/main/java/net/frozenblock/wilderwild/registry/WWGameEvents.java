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
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class WWGameEvents {
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
		Identifier key = WWConstants.id(path);
		return Registry.registerForHolder(BuiltInRegistries.GAME_EVENT, key, new GameEvent(notificationRadius));
	}
}
