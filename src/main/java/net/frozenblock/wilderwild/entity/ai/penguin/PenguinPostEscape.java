/*
 * Copyright 2023-2025 FrozenBlock
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

import java.util.Map;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class PenguinPostEscape<E extends Penguin> extends Behavior<E> {

	public PenguinPostEscape() {
		super(
			Map.of(),
			1
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel serverLevel, @NotNull E penguin) {
		return true;
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return true;
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		if (!penguin.isBaby()) PenguinAi.addCallMemoryIfPenguinsClose(penguin);
		penguin.stopInPlace();

		Brain<Penguin> brain = penguin.getBrain();
		brain.setMemory(WWMemoryModuleTypes.IDLE_TIME, PenguinAi.IDLE_TIME.sample(penguin.getRandom()));
		brain.eraseMemory(WWMemoryModuleTypes.ESCAPING);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
	}
}
