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

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class SunkenNodeEvaluator extends WalkNodeEvaluator {
	private float oldWalkableCost;
	private float oldWaterBorderCost;

	public SunkenNodeEvaluator() {
		super();
	}

	@Override
	public void prepare(PathNavigationRegion level, Mob entity) {
		super.prepare(level, entity);
		entity.setPathfindingMalus(PathType.WATER, 0F);
		this.oldWalkableCost = entity.getPathfindingMalus(PathType.WALKABLE);
		entity.setPathfindingMalus(PathType.WALKABLE, 6F);
		this.oldWaterBorderCost = entity.getPathfindingMalus(PathType.WATER_BORDER);
		entity.setPathfindingMalus(PathType.WATER_BORDER, 4F);
	}

	@Override
	public void done() {
		this.mob.setPathfindingMalus(PathType.WALKABLE, this.oldWalkableCost);
		this.mob.setPathfindingMalus(PathType.WATER_BORDER, this.oldWaterBorderCost);
		super.done();
	}

	@Override
	public Node getStart() {
		return !this.mob.isInWater()
			? super.getStart()
			: this.getStartNode(
				new BlockPos(
					Mth.floor(this.mob.getBoundingBox().minX),
					Mth.floor(this.mob.getBoundingBox().minY + 0.5D),
					Mth.floor(this.mob.getBoundingBox().minZ)
				)
		);
	}

	@Override
	public Target getTarget(double x, double y, double z) {
		return this.getTargetNodeAt(x, y + 0.5D, z);
	}

	@Override
	protected double getFloorLevel(BlockPos pos) {
		return getFloorLevel(this.currentContext.level(), pos);
	}

	@Override
	protected boolean isAmphibious() {
		return true;
	}

	@Override
	public PathType getPathType(PathfindingContext context, int x, int y, int z) {
		final PathType blockPathType = context.getPathTypeFromState(x, y, z);
		if (blockPathType == PathType.WATER) {
			final PathType pathTypeFromNeighbors = checkNeighbourBlocks(context, x, y, z, PathType.WATER);
			if (pathTypeFromNeighbors == PathType.WATER || pathTypeFromNeighbors == PathType.WATER_BORDER) {
				final PathType belowType = context.getPathTypeFromState(x, y - 1, z);
				if (belowType == PathType.WALKABLE || belowType == PathType.WALKABLE_DOOR) return belowType;
				if (belowType == PathType.BLOCKED) return PathType.WALKABLE;
			}
			return pathTypeFromNeighbors;
		}

		if (blockPathType == PathType.OPEN || blockPathType == PathType.DOOR_OPEN) {
			final PathType pathTypeFromNeighbors = checkNeighbourBlocks(context, x, y, z, PathType.WALKABLE);
			if (pathTypeFromNeighbors == PathType.WATER || pathTypeFromNeighbors == PathType.WATER_BORDER || pathTypeFromNeighbors == PathType.WALKABLE) {
				final PathType belowType = context.getPathTypeFromState(x, y - 1, z);
				if (belowType == PathType.WALKABLE || belowType == PathType.WALKABLE_DOOR) return belowType;
				if (belowType == PathType.BLOCKED) return PathType.WALKABLE;
				if (belowType == PathType.WATER || belowType == PathType.WATER_BORDER) return PathType.WALKABLE;
			}
			return pathTypeFromNeighbors;
		}

		return super.getPathType(context, x, y, z);
	}
}
