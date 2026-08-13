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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PenguinPreSearch<E extends Penguin> extends Behavior<E> {

	public PenguinPreSearch() {
		super(ImmutableMap.of(WWMemoryModuleTypes.STARTING_SEARCH.get(), MemoryStatus.REGISTERED), 1);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, E body) {
		return !body.isTouchingWaterOrSwimming() && PenguinAi.hasNearbyPenguins(body);
	}

	@Override
	protected void start(ServerLevel level, E body, long gameTime) {
		PenguinAi.addCallMemoryIfPenguinsClose(body);
		body.stopInPlace();
		body.getBrain().setMemory(WWMemoryModuleTypes.STARTING_SEARCH.get(), Unit.INSTANCE);
	}
}
