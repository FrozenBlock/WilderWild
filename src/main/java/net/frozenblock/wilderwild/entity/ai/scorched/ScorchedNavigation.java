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

package net.frozenblock.wilderwild.entity.ai.scorched;

import net.frozenblock.wilderwild.entity.Scorched;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;

public class ScorchedNavigation extends WallClimberNavigation {

	public ScorchedNavigation(Scorched scorched, Level level) {
		super(scorched, level);
	}

	@Override
	protected boolean hasValidPathType(PathType type) {
		return type == PathType.LAVA || type == PathType.DAMAGE_FIRE || type == PathType.DANGER_FIRE || super.hasValidPathType(type);
	}

	@Override
	public boolean isStableDestination(BlockPos pos) {
		return this.level.getBlockState(pos).is(Blocks.LAVA) || super.isStableDestination(pos);
	}
}
