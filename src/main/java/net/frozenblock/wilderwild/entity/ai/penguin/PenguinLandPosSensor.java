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

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class PenguinLandPosSensor extends Sensor<LivingEntity> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(WWMemoryModuleTypes.LAND_POS, MemoryModuleType.IS_IN_WATER, WWMemoryModuleTypes.DIVE_TICKS);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
		Brain<?> brain = entity.getBrain();
		if (brain.checkMemory(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT)
			&& brain.checkMemory(WWMemoryModuleTypes.DIVE_TICKS, MemoryStatus.VALUE_ABSENT)
			&& brain.checkMemory(WWMemoryModuleTypes.LAND_POS, MemoryStatus.VALUE_ABSENT)
		) {
			if (entity.onGround()) {
				brain.setMemory(WWMemoryModuleTypes.LAND_POS, GlobalPos.of(level.dimension(), entity.blockPosition()));
			}
		}
	}
}
