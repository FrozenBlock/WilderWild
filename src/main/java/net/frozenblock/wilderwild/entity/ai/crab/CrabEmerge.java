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

package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class CrabEmerge<E extends Crab> extends Behavior<E> {

	public CrabEmerge(int duration) {
		super(ImmutableMap.of(MemoryModuleType.IS_EMERGING, MemoryStatus.VALUE_PRESENT), duration);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E entity, long gameTime) {
		return true;
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E crab, long gameTime) {
		crab.setPose(Pose.EMERGING);
		crab.playSound(WWSounds.ENTITY_CRAB_EMERGE, 0.5F, 1F);
		crab.resetDiggingTicks();
		crab.stopInPlace();
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E crab, long gameTime) {
		if (crab.hasPose(Pose.EMERGING)) crab.setPose(Pose.STANDING);
		crab.resetDiggingTicks();
	}
}
