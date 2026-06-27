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

import net.frozenblock.lib.wind.disturbance.WindDisturbance;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceResult;
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractGeothermalVentWindDisturbance implements WindDisturbance<GeothermalVentBlockEntity> {

	@Override
	public Vec3 origin(GeothermalVentBlockEntity source, Level level) {
		return Vec3.atCenterOf(source.getBlockPos());
	}

	@Override
	public WindDisturbanceResult get(GeothermalVentBlockEntity source, Level level, Vec3 origin, AABB area, Vec3 target, double scale) {
		if (!source.isErupting()) return WindDisturbanceResult.PASS;

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
			movement.scale(intensity * this.windIntensity()).scale(30D)
		);
	}

	abstract double windIntensity();

	@Override
	public boolean expired(GeothermalVentBlockEntity source, Level level) {
		return source.isRemoved() || !source.isErupting();
	}
}
