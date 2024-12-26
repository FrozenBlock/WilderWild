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

import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PenguinTryToLayDown {

	@Contract(" -> new")
	public static @NotNull BehaviorControl<Penguin> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.absent(WWMemoryModuleTypes.LOWERING_TO_LAY_DOWN),
			instance.absent(WWMemoryModuleTypes.LAYING_DOWN),
			instance.absent(WWMemoryModuleTypes.IDLE_TIME),
			instance.absent(WWMemoryModuleTypes.SEARCHING_FOR_WATER),
			instance.absent(WWMemoryModuleTypes.DIVE_TICKS),
			instance.absent(MemoryModuleType.IS_IN_WATER),
			instance.absent(MemoryModuleType.IS_PANICKING),
			instance.absent(MemoryModuleType.ATTACK_TARGET),
			instance.registered(MemoryModuleType.WALK_TARGET),
			instance.registered(MemoryModuleType.LOOK_TARGET)
		).apply(instance,
			(
				loweringToLayDown,
				layingDown,
				idleTime,
				searchingForWater,
				diveTicks,
				isInWater,
				isPanicking,
				attackTarget,
				walkTarget,
				lookTarget
			) -> (world, penguin, l) -> {
			if (penguin.onGround()) {
				loweringToLayDown.setWithExpiry(Unit.INSTANCE, PenguinAi.LAY_DOWN_DURATION);
				walkTarget.erase();
				lookTarget.erase();
				return true;
			} else {
				idleTime.set(80);
			}
			return false;
		}));
	}
}
