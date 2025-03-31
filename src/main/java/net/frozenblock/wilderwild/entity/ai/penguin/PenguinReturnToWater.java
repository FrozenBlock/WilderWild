/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.ai.penguin;

import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.apache.commons.lang3.mutable.MutableLong;
import org.jetbrains.annotations.NotNull;

public class PenguinReturnToWater {
	public static @NotNull BehaviorControl<PathfinderMob> create(float speedModifier) {
		MutableLong returnTimer = new MutableLong(0L);
		return BehaviorBuilder.create(
			instance -> instance.group(
					instance.present(WWMemoryModuleTypes.WATER_POS),
					instance.absent(MemoryModuleType.ATTACK_TARGET),
					instance.absent(MemoryModuleType.WALK_TARGET),
					instance.registered(MemoryModuleType.LOOK_TARGET)
				)
				.apply(
					instance,
					(
						waterPos,
						memoryAccessor,
						walkTarget,
						lookTarget
					) -> (serverLevel, pathfinderMob, l) -> {
						if (pathfinderMob.isInWater()) {
							return false;
						} else if (l < returnTimer.getValue()) {
							returnTimer.setValue(l + 60L);
							return true;
						} else {
							GlobalPos globalPos = instance.get(waterPos);

							if (!globalPos.dimension().equals(serverLevel.dimension())) {
								waterPos.erase();
								return false;
							}

							PathNavigation pathNavigation = pathfinderMob.getNavigation();
							BlockPos pos = globalPos.pos();

							lookTarget.set(new BlockPosTracker(pos));
							walkTarget.set(new WalkTarget(new BlockPosTracker(pos), speedModifier, 1));

							if (pathNavigation.isStuck()) {
								waterPos.erase();
								return false;
							}

							returnTimer.setValue(l + 60L);
							return true;
						}
					})
		);
	}
}
