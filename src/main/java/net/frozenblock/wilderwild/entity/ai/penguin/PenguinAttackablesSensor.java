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

import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class PenguinAttackablesSensor extends NearestVisibleLivingEntitySensor {

	public PenguinAttackablesSensor() {
	}

	@Override
	protected boolean isMatchingEntity(ServerLevel serverLevel, @NotNull LivingEntity attacker, @NotNull LivingEntity target) {
		return this.isClose(attacker, target) && this.isHuntTarget(attacker, target) && Sensor.isEntityAttackable(serverLevel, attacker, target);
	}

	private boolean isHuntTarget(@NotNull LivingEntity attacker, LivingEntity target) {
		return !attacker.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN) && target.getType().is(WWEntityTags.PENGUIN_HUNT_TARGETS);
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
