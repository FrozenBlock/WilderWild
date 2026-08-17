/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.wind.disturbance.GeothermalVentBaseWindDisturbance;
import net.frozenblock.wilderwild.wind.disturbance.GeothermalVentEffectiveWindDisturbance;
import net.frozenblock.wilderwild.wind.disturbance.GeyserWindDisturbance;

public final class WWWindDisturbances {
	public static final WindDisturbanceType<GeothermalVentEffectiveWindDisturbance> GEOTHERMAL_VENT_EFFECTIVE = WindDisturbanceType.register(
		WWConstants.id("geothermal_vent_effective"),
		GeothermalVentEffectiveWindDisturbance.CODEC,
		GeothermalVentEffectiveWindDisturbance.STREAM_CODEC
	);
	public static final WindDisturbanceType<GeothermalVentBaseWindDisturbance> GEOTHERMAL_VENT_BASE = WindDisturbanceType.register(
		WWConstants.id("geothermal_vent_base"),
		GeothermalVentBaseWindDisturbance.CODEC,
		GeothermalVentBaseWindDisturbance.STREAM_CODEC
	);
	public static final WindDisturbanceType<GeyserWindDisturbance> GEYSER = WindDisturbanceType.register(
		WWConstants.id("geyser"),
		GeyserWindDisturbance.CODEC,
		GeyserWindDisturbance.STREAM_CODEC
	);

	public static void init() {}

	private WWWindDisturbances() {}
}
