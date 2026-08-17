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

package net.frozenblock.wilderwild.mod_compat.simplecopperpipes;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.lunade.copper.SimpleCopperPipes;
import net.lunade.copper.registry.CopperPipeDispenseBehaviors;
import net.lunade.copper.registry.PipeMovementRestrictions;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

// TODO: ml
public final class SimpleCopperPipesIntegration {

	public static void setup() {
		if (SimpleCopperPipes.getCompatID() != 4) {
			WWConstants.log("Could not initiate compat with Wilder Wild and Simple Copper Pipes. SCP compat id is not 4 (minimum SCP is 2.0.)", true);
			return;
		}

		WWConstants.log("Initiated Wilder Wild & Simple Copper Pipes compat!", true);

		CopperPipeDispenseBehaviors.register(WWItems.TUMBLEWEED.get(),
			(level, stack, i, direction, position, state, pos, pipe) -> {
			final Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED.get(), level);
			tumbleweed.setDeltaMovement(getVelocity(level.getRandom(), direction, 5D, i).scale(0.2D));
			tumbleweed.setPos(getOutputPosition(position, direction));
			level.addFreshEntity(tumbleweed);
		});

		PipeMovementRestrictions.register(WWConstants.id("stone_chest"),
			((level, pos, blockState, pipe, blockEntity) -> false),
			((level, pos, blockState, pipe, blockEntity) -> false)
		);
	}

	public static Vec3 getOutputPosition(Position position, Direction direction) {
		return new Vec3(
			position.x(),
			position.y() - (direction.getAxis() == Direction.Axis.Y ? 0.125D : 0.15625D),
			position.z()
		);
	}

	public static Vec3 getVelocity(RandomSource random, Direction direction, double randomRange, int i) {
		final double xzRandom = random.nextDouble() * (randomRange * 2D) - randomRange;
		final double yRandom = random.nextDouble() * (randomRange * 2D) - randomRange;

		final Direction.Axis axis = direction.getAxis();
		final double velX = axis == Direction.Axis.X ? (i * direction.getStepX() * 2D) : (axis == Direction.Axis.Z ? (yRandom * 0.1D) : (xzRandom * 0.1D));
		final double velY = axis == Direction.Axis.Y ? (i * direction.getStepY() * 2D) : (xzRandom * 0.1D);
		final double velZ = axis == Direction.Axis.Z ? (i * direction.getStepZ() * 2D) : (yRandom * 0.1D);
		return new Vec3(velX, velY, velZ);
	}

	private SimpleCopperPipesIntegration() {}
}
