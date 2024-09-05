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

package net.frozenblock.wilderwild.entity.impl;

import net.minecraft.world.entity.vehicle.Boat;

<<<<<<<< HEAD:src/main/java/net/frozenblock/wilderwild/entity/impl/WWBoatTypes.java
public final class WWBoatTypes {
========
public final class WWEnumValues {
>>>>>>>> eff76f04a (rebase from 2.5):src/main/java/net/frozenblock/wilderwild/WWEnumValues.java
	public static Boat.Type BAOBAB;
	public static Boat.Type CYPRESS;
	public static Boat.Type PALM;
	public static Boat.Type MAPLE;

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB
	static {
		Boat.Type.values();
	}

}
