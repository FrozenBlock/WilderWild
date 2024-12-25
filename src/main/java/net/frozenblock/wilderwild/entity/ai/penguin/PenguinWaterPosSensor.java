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

public class PenguinWaterPosSensor extends Sensor<LivingEntity> {

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
