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

package net.frozenblock.wilderwild.block.state.properties;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum BubbleDirection implements StringRepresentable {
	NONE("none", Optional.empty()),
	UP("up", Optional.of(Direction.UP)),
	DOWN("down", Optional.of(Direction.DOWN));

	public final Optional<Direction> direction;
	private final String name;

	BubbleDirection(String name, Optional<Direction> direction) {
		this.name = name;
		this.direction = direction;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
