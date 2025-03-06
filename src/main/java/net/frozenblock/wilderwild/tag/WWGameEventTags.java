/*
 * Copyright 2023-2025 FrozenBlock
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
		throw new UnsupportedOperationException("WilderGameEventTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<GameEvent> bind(@NotNull String path) {
		return TagKey.create(Registries.GAME_EVENT, WWConstants.id(path));
	}
}
