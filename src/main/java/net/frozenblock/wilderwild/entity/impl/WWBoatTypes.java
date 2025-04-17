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
