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

package net.frozenblock.wilderwild.block.impl;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

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
	@NotNull
	public String getSerializedName() {
		return this.name;
	}
}
