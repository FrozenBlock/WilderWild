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

package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class CrabAttackablesSensor extends NearestVisibleLivingEntitySensor {

	public CrabAttackablesSensor() {
	}

	@Override
	protected boolean isMatchingEntity(ServerLevel level, @NotNull LivingEntity attacker, @NotNull LivingEntity target) {
		return this.isClose(attacker, target) && this.isHuntTarget(attacker, target) && Sensor.isEntityAttackable(level, attacker, target);
	}

	private boolean isHuntTarget(@NotNull LivingEntity attacker, LivingEntity target) {
		return !attacker.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN) && target.getType().is(WWEntityTags.CRAB_HUNT_TARGETS);
	}

	private boolean isClose(LivingEntity attacker, @NotNull LivingEntity target) {
		return target.distanceToSqr(attacker) <= 64D;
	}

	@Override
	@NotNull
	protected MemoryModuleType<LivingEntity> getMemory() {
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}
}
