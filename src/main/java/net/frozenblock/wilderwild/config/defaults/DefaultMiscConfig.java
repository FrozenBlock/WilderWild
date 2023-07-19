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

package net.frozenblock.wilderwild.config.defaults;

public final class DefaultMiscConfig {

	public static final boolean CLOUD_MOVEMENT = true;
	public static final int PARTICLE_WIND_MOVEMENT = 100;

	public static final class BiomeAmbienceConfig {
		public static final boolean DEEP_DARK_AMBIENCE = true;
		public static final boolean DRIPSTONE_CAVES_AMBIENCE = true;
		public static final boolean LUSH_CAVES_AMBIENCE = true;
	}

	public static final class BiomeMusicConfig {
		public static final boolean WILDER_FOREST_MUSIC = true;
	}
}
