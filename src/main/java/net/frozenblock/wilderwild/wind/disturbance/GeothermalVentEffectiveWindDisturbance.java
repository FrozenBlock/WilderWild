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
import net.frozenblock.lib.wind.disturbance.WindDisturbance;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceResult;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GeothermalVentEffectiveWindDisturbance implements WindDisturbance<GeothermalVentBlockEntity> {
	public static final GeothermalVentEffectiveWindDisturbance INSTANCE = new GeothermalVentEffectiveWindDisturbance();
	public static final MapCodec<GeothermalVentEffectiveWindDisturbance> CODEC = MapCodec.unit(() -> INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, GeothermalVentEffectiveWindDisturbance> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public Vec3 origin(GeothermalVentBlockEntity source, Level level) {
		return Vec3.atCenterOf(source.getBlockPos());
	}

	@Override
	public AABB area(GeothermalVentBlockEntity source, Level level, Vec3 origin, Vec3 target, double scale) {
		return source.computeEffectiveEruptionArea(level);
	}

	@Override
	public WindDisturbanceResult get(GeothermalVentBlockEntity source, Level level, Vec3 target) {
		if (!source.isErupting()) return WindDisturbanceResult.PASS;
		return WindDisturbance.super.get(source, level, target);
	}

	@Override
	public WindDisturbanceResult get(GeothermalVentBlockEntity source, Level level, Vec3 origin, AABB area, Vec3 target, double scale) {
		final BlockState state = level.getBlockState(source.getBlockPos());
		if (!state.hasProperty(BlockStateProperties.FACING)) return WindDisturbanceResult.PASS;

		final Direction direction = state.getValue(BlockStateProperties.FACING);
		final Vec3 movement = Vec3.atLowerCornerOf(direction.getUnitVec3i());
		final double strength = GeothermalVentBlockEntity.ERUPTION_DISTANCE - Math.min(target.distanceTo(origin), GeothermalVentBlockEntity.ERUPTION_DISTANCE);
		final double intensity = strength / GeothermalVentBlockEntity.ERUPTION_DISTANCE;
		final double resultStrength = Mth.clamp(intensity * 2D, 0D, 1D);
		final double weight = strength * 2D;
		if (resultStrength <= 0D || weight <= 0D) return WindDisturbanceResult.PASS;

		return WindDisturbanceResult.success(
			resultStrength,
			weight,
			movement.scale(intensity * GeothermalVentBlockEntity.EFFECTIVE_ADDITIONAL_WIND_INTENSITY).scale(30D)
		);
	}

	@Override
	public boolean expired(GeothermalVentBlockEntity source, Level level) {
		return source.isRemoved();
	}

	@Override
	public WindDisturbanceType<?> type() {
		return WWWindDisturbances.GEOTHERMAL_VENT_EFFECTIVE;
	}
}
