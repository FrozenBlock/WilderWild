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

import java.util.Optional;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public final class WWWindDisturbances {
	public static final Identifier GEYSER_EFFECTIVE_WIND_DISTURBANCE = WWConstants.id("geyser_effective");
	public static final Identifier GEYSER_BASE_WIND_DISTURBANCE = WWConstants.id("geyser");

	public static void init() {
		WindDisturbanceLogic.register(
			GEYSER_EFFECTIVE_WIND_DISTURBANCE,
			(source, level, windOrigin, affectedArea, windTarget) -> {
				return calculateDisturbanceResult(source, level, windOrigin, windTarget, GeyserBlockEntity.EFFECTIVE_ADDITIONAL_WIND_INTENSITY);
			}
		);

		WindDisturbanceLogic.register(
			GEYSER_BASE_WIND_DISTURBANCE,
			(source, level, windOrigin, affectedArea, windTarget) -> {
				return calculateDisturbanceResult(source, level, windOrigin, windTarget, GeyserBlockEntity.BASE_WIND_INTENSITY);
			}
		);
	}

	private static WindDisturbance.DisturbanceResult calculateDisturbanceResult(Optional<Object> source, Level level, Vec3 windOrigin, Vec3 windTarget, double scale) {
		if (source.isEmpty() || !(source.get() instanceof GeyserBlockEntity geyser)) return null;

		final BlockState state = level.getBlockState(geyser.getBlockPos());
		if (!state.hasProperty(BlockStateProperties.FACING)) return null;

		final Direction direction = state.getValue(BlockStateProperties.FACING);
		final Vec3 movement = Vec3.atLowerCornerOf(direction.getUnitVec3i());
		final double strength = GeyserBlockEntity.ERUPTION_DISTANCE - Math.min(windTarget.distanceTo(windOrigin), GeyserBlockEntity.ERUPTION_DISTANCE);
		final double intensity = strength / GeyserBlockEntity.ERUPTION_DISTANCE;
		return new WindDisturbance.DisturbanceResult(
			Mth.clamp(intensity * 2D, 0D, 1D),
			strength * 2D,
			movement.scale(intensity * scale).scale(30D)
		);
	}
}
