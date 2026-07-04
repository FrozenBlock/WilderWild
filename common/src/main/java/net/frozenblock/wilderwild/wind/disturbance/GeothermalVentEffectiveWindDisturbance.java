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

package net.frozenblock.wilderwild.wind.disturbance;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GeothermalVentEffectiveWindDisturbance extends AbstractGeothermalVentWindDisturbance {
	public static final double INTENSITY_SCALE = 0.5D;
	public static final GeothermalVentEffectiveWindDisturbance INSTANCE = new GeothermalVentEffectiveWindDisturbance();
	public static final MapCodec<GeothermalVentEffectiveWindDisturbance> CODEC = MapCodec.unit(() -> INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, GeothermalVentEffectiveWindDisturbance> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public AABB area(GeothermalVentBlockEntity source, Level level, Vec3 origin, Vec3 target, double scale) {
		return source.computeEffectiveEruptionArea(level);
	}

	@Override
	double windIntensity() {
		return INTENSITY_SCALE;
	}

	@Override
	public WindDisturbanceType<?> type() {
		return WWWindDisturbances.GEOTHERMAL_VENT_EFFECTIVE;
	}
}
