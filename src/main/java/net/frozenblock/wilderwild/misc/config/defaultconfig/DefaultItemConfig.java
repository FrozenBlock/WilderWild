/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.config.defaultconfig;

import net.frozenblock.wilderwild.entity.AncientHornProjectile;

public final class DefaultItemConfig {

	public static class AncientHornConfig {
		public static final boolean ANCIENT_HORN_CAN_SUMMON_WARDEN = true;
		public static final int ANCIENT_HORN_LIFESPAN = AncientHornProjectile.DEFAULT_LIFESPAN;
		public static final int ANCIENT_HORN_MOB_DAMAGE = 22;
		public static final int ANCIENT_HORN_PLAYER_DAMAGE = 15;
		public static final boolean ANCIENT_HORN_SHATTERS_GLASS = false;
		public static final float ANCIENT_HORN_SIZE_MULTIPLIER = 0F;
	}

	public static final class ProjectileLandingSoundsConfig {
		public static final boolean SNOWBALL_LANDING_SOUNDS = true;
		public static final boolean EGG_LANDING_SOUNDS = true;
		public static final boolean ENDER_PEARL_LANDING_SOUNDS = true;
		public static final boolean POTION_LANDING_SOUNDS = true;
	}

	public static final boolean PROJECTILE_BREAK_PARTICLES = true;
	public static final boolean RESTRICT_INSTRUMENT_SOUND = true;
}
