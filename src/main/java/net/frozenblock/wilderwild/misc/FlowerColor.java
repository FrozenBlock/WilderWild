/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum FlowerColor implements StringRepresentable {
	NONE("none"),
	BLUE("blue"),
	WHITE("white"),
	PINK("pink"),
	PURPLE("purple");

	private final String name;

	FlowerColor(String name) {
		this.name = name;
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