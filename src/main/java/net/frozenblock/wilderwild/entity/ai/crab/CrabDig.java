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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity.ai.crab;

import java.util.Map;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class CrabDig<E extends Crab> extends Behavior<E> {

	public CrabDig(int duration) {
		super(
			Map.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.IS_UNDERGROUND, MemoryStatus.REGISTERED
			),
			duration
		);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E entity, long gameTime) {
		return true;
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E crab, long gameTime) {
		crab.endNavigation();
		crab.setPose(Pose.DIGGING);
		crab.playSound(WWSounds.ENTITY_CRAB_DIG, 0.5F, 1.0F);
		crab.resetDiggingTicks();
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E crab, long gameTime) {
		if (crab.hasPose(Pose.DIGGING)) {
			crab.getBrain().setMemory(WWMemoryModuleTypes.IS_UNDERGROUND, true);
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomEmergeCooldown(crab));
		} else {
			crab.getBrain().eraseMemory(WWMemoryModuleTypes.IS_UNDERGROUND);
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 40L);
		}
	}
}
