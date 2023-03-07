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

public class DefaultEntityConfig {

	public static class AllayConfig {
		public static final boolean KEYFRAME_ALLAY_DANCE = true;
	}

	public static class EnderManConfig {
		public static final boolean ANGER_LOOP_SOUND = true;
		public static final boolean MOVING_STARE_SOUND = true;
	}

	public static class FireflyConfig {
		public static final int FIREFLY_SPAWN_CAP = 56;
	}

	public static class JellyfishConfig {
		public static final int JELLYFISH_SPAWN_CAP = 25;
	}

	public static class TumbleweedConfig {
		public static final int TUMBLEWEED_SPAWN_CAP = 8;
		public static final boolean LEASHED_TUMBLEWEED = false;
	}

	public static class WardenConfig {
		public static final boolean WARDEN_ATTACKS_IMMEDIATELY = true;
		public static final boolean WARDEN_CUSTOM_TENDRILS = true;
		public static final boolean WARDEN_BEDROCK_SNIFF = true;
		public static final boolean WARDEN_DYING_ANIMATION = true;
		public static final boolean WARDEN_EMERGES_FROM_COMMAND = false;
		public static final boolean WARDEN_EMERGES_FROM_EGG = false;
		public static final boolean WARDEN_SWIM_ANIMATION = true;
	}

	public static final boolean UNPASSABLE_RAIL = false;
}
