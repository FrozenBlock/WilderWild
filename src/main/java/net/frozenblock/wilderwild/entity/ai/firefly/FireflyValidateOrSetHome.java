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

package net.frozenblock.wilderwild.entity.ai.firefly;

import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FireflyValidateOrSetHome {

	@Contract(" -> new")
	public static @NotNull BehaviorControl<Firefly> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(MemoryModuleType.HOME),
			instance.absent(WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN)
		).apply(instance, (homeMemory, homeValidateCooldown) -> (level, firefly, l) -> {
			homeValidateCooldown.set(200);
			BlockPos homePos = FireflyAi.getHome(firefly);
			if (homePos != null && FireflyAi.isInHomeDimension(firefly)) {
				if (!isValidHomePos(level, homePos)) {
					FireflyAi.rememberHome(firefly, firefly.blockPosition());
					return true;
				}
			}
			return false;
		}));
	}

	private static boolean isValidHomePos(@NotNull Level level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (!state.getFluidState().isEmpty()) return false;
		if (state.isRedstoneConductor(level, pos)) return false;
		return state.isAir() || (!state.blocksMotion() && !state.isSolid());
	}
}
