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
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class PenguinCall<E extends Penguin> extends Behavior<E> {

	public PenguinCall(int duration) {
		super(
			ImmutableMap.of(
				WWMemoryModuleTypes.NEARBY_PENGUINS, MemoryStatus.VALUE_PRESENT,
				WWMemoryModuleTypes.CALL_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.WANTS_TO_CALL, MemoryStatus.VALUE_PRESENT,
				WWMemoryModuleTypes.CALLING, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT
			),
			duration
		);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return true;
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		penguin.stopInPlace();
		penguin.setPose(Pose.ROARING);
		penguin.playSound(penguin.isLinux() ? WWSounds.ENTITY_LINUX_CALL : WWSounds.ENTITY_PENGUIN_CALL, 1.2F, 0.9F + penguin.getRandom().nextFloat() * 0.2F);

		penguin.getBrain().setMemory(WWMemoryModuleTypes.CALLING, Unit.INSTANCE);
		PenguinAi.addCallerMemoryToNearbyPenguins(penguin);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		if (penguin.hasPose(Pose.ROARING)) {
			penguin.setPose(Pose.STANDING);
		}
		penguin.getBrain().setMemory(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS, 2400);
		penguin.getBrain().eraseMemory(WWMemoryModuleTypes.CALLING);
	}
}
