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

package net.frozenblock.wilderwild;

/**
 * This class was created to fix issue #289: <a href="https://github.com/FrozenBlock/WilderWild/issues/289">...</a>.
 */
public class WilderDatagenConstants {
	public static final boolean IS_DATAGEN = isDatagen();

	private static boolean isDatagen() {
		boolean isDatagen = false;
		try {
			Class.forName("net.frozenblock.wilderwild.datagen.WWDataGenerator");
			isDatagen = true;
		} catch (ClassNotFoundException ignored) {}

		return isDatagen;
	}
}
