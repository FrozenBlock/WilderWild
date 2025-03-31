/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.impl;

import net.minecraft.world.entity.vehicle.Boat;

public final class WWBoatTypes {
	public static Boat.Type BAOBAB;
	public static Boat.Type WILLOW;
	public static Boat.Type CYPRESS;
	public static Boat.Type PALM;
	public static Boat.Type MAPLE;

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB
	static {
		Boat.Type.values();
	}

}
