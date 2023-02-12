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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gameevent.GameEvent;

public final class RegisterGameEvents {
	private RegisterGameEvents() {
		throw new UnsupportedOperationException("RegisterGameEvents contains only static declarations.");
	}

    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);
    public static final GameEvent TENDRIL_EXTRACT_XP = new GameEvent("hanging_tendril_extract_xp", 16);


    public static void registerEvents() {
        WilderSharedConstants.logWild("Registering GameEvents for", WilderSharedConstants.UNSTABLE_LOGGING);
        Registry.register(BuiltInRegistries.GAME_EVENT, WilderSharedConstants.id("sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        Registry.register(BuiltInRegistries.GAME_EVENT, WilderSharedConstants.id("hanging_tendril_extract_xp"), TENDRIL_EXTRACT_XP);
    }
}
