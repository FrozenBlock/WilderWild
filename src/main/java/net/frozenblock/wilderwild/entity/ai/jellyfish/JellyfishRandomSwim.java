/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.jellyfish;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import org.jetbrains.annotations.NotNull;

public class JellyfishRandomSwim extends RandomSwim {
	public JellyfishRandomSwim(float speedModifier) {
		super(speedModifier);
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PathfinderMob pathfinder) {
		if (!(pathfinder instanceof Jellyfish jellyfish)) {
			throw new IllegalStateException("Jellyfish random swim is being used on a non-jellyfish entity!");
		} else {
			return jellyfish.getTarget() == null && jellyfish.canRandomSwim() && super.checkExtraStartConditions(level, jellyfish);
		}
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, PathfinderMob pathfinder, long gameTime) {
		return pathfinder.getTarget() == null && !pathfinder.getNavigation().isDone() && !pathfinder.isVehicle();
	}
}
