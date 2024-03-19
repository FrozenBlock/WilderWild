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

package net.frozenblock.wilderwild.entity.ai.scorched;

import net.frozenblock.wilderwild.entity.Scorched;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;

public class ScorchedNavigation extends WallClimberNavigation {

	public ScorchedNavigation(@NotNull Scorched mob, @NotNull Level level) {
		super(mob, level);
	}

	@Override
	protected boolean hasValidPathType(BlockPathTypes pathType) {
		return pathType == BlockPathTypes.LAVA || pathType == BlockPathTypes.DAMAGE_FIRE || pathType == BlockPathTypes.DANGER_FIRE || super.hasValidPathType(pathType);
	}

	@Override
	public boolean isStableDestination(BlockPos pos) {
		return this.level.getBlockState(pos).is(Blocks.LAVA) || super.isStableDestination(pos);
	}
}
