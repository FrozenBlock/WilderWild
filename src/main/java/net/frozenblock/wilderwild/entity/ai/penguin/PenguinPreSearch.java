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
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class PenguinPreSearch<E extends Penguin> extends Behavior<E> {

	public PenguinPreSearch() {
		super(ImmutableMap.of(WWMemoryModuleTypes.STARTING_SEARCH, MemoryStatus.REGISTERED), 1);
	}

	@Override
	protected boolean checkExtraStartConditions(@NotNull ServerLevel serverLevel, @NotNull E penguin) {
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
