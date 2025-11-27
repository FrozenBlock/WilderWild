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

import java.util.Map;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PenguinLayDown<E extends Penguin> extends Behavior<E> {

	public PenguinLayDown() {
		super(
			Map.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.REGISTERED,
				MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT
			),
			400
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, E penguin) {
		return !penguin.isTouchingWaterOrSwimming();
	}

	@Override
	protected boolean canStillUse(ServerLevel level, E penguin, long gameTime) {
		final Brain<Penguin> brain = penguin.getBrain();
		return brain.checkMemory(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT)
			&& brain.checkMemory(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT)
			&& !penguin.isTouchingWaterOrSwimming();
	}

	@Override
	protected void start(ServerLevel level, E penguin, long gameTime) {
		final Brain<Penguin> brain = penguin.getBrain();
		brain.eraseMemory(WWMemoryModuleTypes.STARTING_SEARCH);
		brain.setMemoryWithExpiry(WWMemoryModuleTypes.SEARCHING_FOR_WATER, Unit.INSTANCE, 400L);
		brain.setMemory(WWMemoryModuleTypes.LAYING_DOWN, Unit.INSTANCE);
		penguin.setPose(Pose.SLIDING);
		penguin.stopInPlace();
	}

	@Override
	protected void stop(ServerLevel level, E penguin, long gameTime) {
		final Brain<Penguin> brain = penguin.getBrain();
		brain.eraseMemory(WWMemoryModuleTypes.SEARCHING_FOR_WATER);
		brain.eraseMemory(WWMemoryModuleTypes.LAYING_DOWN);

		if (!penguin.isTouchingWaterOrSwimming()) {
			brain.setMemory(WWMemoryModuleTypes.IDLE_TIME, PenguinAi.IDLE_TIME.sample(penguin.getRandom()));
			brain.setMemoryWithExpiry(WWMemoryModuleTypes.STANDING_UP, Unit.INSTANCE, PenguinAi.STAND_UP_DURATION);
		} else {
			brain.setMemory(WWMemoryModuleTypes.DIVE_TICKS, PenguinAi.DIVE_TIME.sample(penguin.getRandom()));
		}
	}
}
