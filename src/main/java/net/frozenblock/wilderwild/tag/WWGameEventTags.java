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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class WWGameEventTags {
	public static final TagKey<GameEvent> CRAB_CAN_DETECT = bind("crab_can_detect");
	public static final TagKey<GameEvent> CRAB_CAN_ALWAYS_DETECT = bind("crab_can_always_detect");
	public static final TagKey<GameEvent> MAKES_ICICLE_FALL = bind("makes_icicle_fall");

	private WWGameEventTags() {
		throw new UnsupportedOperationException("WWGameEventTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<GameEvent> bind(@NotNull String path) {
		return TagKey.create(Registries.GAME_EVENT, WWConstants.id(path));
	}
}
