/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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

public class PenguinLayDown<E extends Penguin> extends Behavior<E> {

	public PenguinLayDown(int duration) {
		super(
			ImmutableMap.of(
				WWMemoryModuleTypes.LOWERING_TO_LAY_DOWN, MemoryStatus.VALUE_PRESENT,
				WWMemoryModuleTypes.LAYING_DOWN, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT
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
		penguin.setPose(Pose.SLIDING);
		penguin.playSound(WWSounds.ENTITY_CRAB_EMERGE, 0.5F, 1F);
		penguin.stopInPlace();
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		if (penguin.hasPose(Pose.SLIDING)) {
			if (penguin.isSwimming()) {
				penguin.setPose(Pose.SWIMMING);
			} else {
				penguin.getBrain().setMemoryWithExpiry(WWMemoryModuleTypes.LAYING_DOWN, Unit.INSTANCE, PenguinAi.getRandomLayingDownLength(penguin));
			}
		}
	}
}
