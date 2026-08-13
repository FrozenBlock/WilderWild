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

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PenguinCall<E extends Penguin> extends Behavior<E> {

	public PenguinCall(int duration) {
		super(
			ImmutableMap.of(
				WWMemoryModuleTypes.NEARBY_PENGUINS.get(), MemoryStatus.VALUE_PRESENT,
				WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get(), MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.WANTS_TO_CALL.get(), MemoryStatus.VALUE_PRESENT,
				WWMemoryModuleTypes.CALLING.get(), MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT
			),
			duration
		);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, E body, long timestamp) {
		return true;
	}

	@Override
	protected void start(ServerLevel level, E body, long timestamp) {
		body.stopInPlace();
		body.setPose(Pose.ROARING);
		body.playSound(body.isLinux() ? WWSounds.ENTITY_LINUX_CALL.get() : WWSounds.ENTITY_PENGUIN_CALL.get(), 1.2F, 0.9F + body.getRandom().nextFloat() * 0.2F);

		body.getBrain().setMemory(WWMemoryModuleTypes.CALLING.get(), Unit.INSTANCE);
		PenguinAi.addCallerMemoryToNearbyPenguins(body);
	}

	@Override
	protected void stop(ServerLevel level, E body, long timestamp) {
		if (body.hasPose(Pose.ROARING)) body.setPose(Pose.STANDING);
		body.getBrain().setMemory(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get(), 2400);
		body.getBrain().eraseMemory(WWMemoryModuleTypes.CALLING.get());
	}
}
