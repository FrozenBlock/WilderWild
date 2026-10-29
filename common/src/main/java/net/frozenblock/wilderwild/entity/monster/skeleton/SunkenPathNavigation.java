/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.entity.monster.skeleton;

import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class SunkenPathNavigation extends GroundPathNavigation {

	public SunkenPathNavigation(Sunken mob, Level level) {
		super(mob, level);
	}

	@Override
	protected PathFinder createPathFinder(int maxVisitedNodes) {
		this.nodeEvaluator = new SunkenNodeEvaluator();
		return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
	}

	@Override
	protected Vec3 getTempMobPos() {
		return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
	}

	@Override
	protected double getGroundY(Vec3 target) {
		if (!this.mob.isInLiquid()) return super.getGroundY(target);

		final BlockPos blockPos = BlockPos.containing(target);
		final BlockPos belowPos = blockPos.below();
		return isWaterOrEmptyBlockState(this.level, this.mob, belowPos, this.level.getBlockState(belowPos))
			? target.y
			: SunkenNodeEvaluator.getFloorLevel(this.level, blockPos);
	}

	public static boolean isWaterOrEmptyBlockState(Level level, Entity entity, BlockPos pos, BlockState state) {
		return state.isAir()
			|| state.is(Blocks.WATER)
			|| state.is(Blocks.BUBBLE_COLUMN)
			|| state.is(WWBlockItemTags.MESOGLEA.block())
			|| state.getCollisionShape(level, pos, CollisionContext.of(entity, false)).isEmpty();
	}
}
