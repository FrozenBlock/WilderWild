/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PenguinStandUp<E extends Penguin> extends Behavior<E> {

	public PenguinStandUp(int duration) {
		super(
			ImmutableMap.of(
				WWMemoryModuleTypes.LAYING_DOWN, MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.VALUE_ABSENT
			),
			duration
		);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, E penguin, long gameTime) {
		return true;
	}

	@Override
	protected void start(ServerLevel level, E penguin, long gameTime) {
		final boolean swimming = penguin.isSwimming();
		penguin.setPose(swimming ? Pose.STANDING : Pose.EMERGING);
		if (!swimming) penguin.stopInPlace();
	}

	@Override
	protected void stop(ServerLevel level, E penguin, long gameTime) {
		if (penguin.hasPose(Pose.EMERGING)) penguin.setPose(Pose.STANDING);
	}
}
