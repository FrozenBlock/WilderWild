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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import org.jetbrains.annotations.NotNull;

public class PenguinPostEscape<E extends Penguin> extends Behavior<E> {

	public PenguinPostEscape() {
		super(ImmutableMap.of(), 1);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel serverLevel, @NotNull E penguin) {
		return true;
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return false;
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
