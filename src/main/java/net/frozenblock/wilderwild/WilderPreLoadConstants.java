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

import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was created to fix issue #289: <a href="https://github.com/FrozenBlock/WilderWild/issues/289">...</a>.
 */
public class WilderPreLoadConstants {
	public static final String PROJECT_ID = "Wilder Wild";
	public static final String MOD_ID = "wilderwild";
	public static final Logger LOGGER = LoggerFactory.getLogger(PROJECT_ID);
	public static final boolean IS_DATAGEN = isDatagen();

	private static boolean isDatagen() {
		boolean isDatagen = false;
		try {
			Class.forName("net.frozenblock.wilderwild.datagen.WWDataGenerator", false, WilderPreLoadConstants.class.getClassLoader());
			isDatagen = true;
		} catch (ClassNotFoundException ignored) {}

		return isDatagen;
	}

	public static Path configPath(String name, boolean json5) {
		return Path.of("./config/" + MOD_ID + "/" + name + "." + (json5 ? "json5" : "json"));
	}
}
