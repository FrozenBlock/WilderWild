/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.ai.penguin;

import java.util.Map;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class PenguinPreSearch<E extends Penguin> extends Behavior<E> {

	public PenguinPreSearch() {
		super(
			Map.of(
				WWMemoryModuleTypes.STARTING_SEARCH, MemoryStatus.REGISTERED
			),
			1
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel serverLevel, @NotNull E penguin) {
		return !penguin.isTouchingWaterOrSwimming() && PenguinAi.hasNearbyPenguins(penguin);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return false;
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		PenguinAi.addCallMemoryIfPenguinsClose(penguin);
		penguin.stopInPlace();
		penguin.getBrain().setMemory(WWMemoryModuleTypes.STARTING_SEARCH, Unit.INSTANCE);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
	}
}
