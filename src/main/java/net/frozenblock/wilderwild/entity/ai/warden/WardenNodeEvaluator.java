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

package net.frozenblock.wilderwild.entity.ai.warden;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.Nullable;

public class WardenNodeEvaluator extends WalkNodeEvaluator {
	private final boolean prefersShallowSwimming;
	private float oldWalkableCost;
	private float oldWaterBorderPenalty;

	public WardenNodeEvaluator(boolean prefersShallowSwimming) {
		this.prefersShallowSwimming = prefersShallowSwimming;
	}

	@Override
	public void prepare(PathNavigationRegion level, Mob mob) {
		super.prepare(level, mob);
		mob.setPathfindingMalus(PathType.WATER, 0F);
		this.oldWalkableCost = mob.getPathfindingMalus(PathType.WALKABLE);
		mob.setPathfindingMalus(PathType.WALKABLE, 0F);
		this.oldWaterBorderPenalty = mob.getPathfindingMalus(PathType.WATER_BORDER);
		mob.setPathfindingMalus(PathType.WATER_BORDER, 4F);
	}

	@Override
	public void done() {
		this.mob.setPathfindingMalus(PathType.WALKABLE, this.oldWalkableCost);
		this.mob.setPathfindingMalus(PathType.WATER_BORDER, this.oldWaterBorderPenalty);
		super.done();
	}

	@Override
	public Node getStart() {
		return !this.isEntityTouchingWaterOrLava(this.mob)
			? super.getStart()
			: this.getStartNode(
				new BlockPos(
					Mth.floor(this.mob.getBoundingBox().minX),
					Mth.floor(this.mob.getBoundingBox().minY + 0.5),
					Mth.floor(this.mob.getBoundingBox().minZ)
				)
		);
	}

	@Override
	public Target getTarget(double x, double y, double z) {
		return !this.isEntitySubmergedInWaterOrLava(this.mob)
			? super.getTarget(x, y, z)
			: this.getTargetNodeAt(x, y + 0.5, z);
	}

	@Override
	public int getNeighbors(Node[] successors, Node node) {
		if (!isEntitySubmergedInWaterOrLava(this.mob)) return super.getNeighbors(successors, node);

		int neighbors = super.getNeighbors(successors, node);
		final PathType abovePathType = this.getCachedPathType(node.x, node.y + 1, node.z);
		final PathType pathType = this.getCachedPathType(node.x, node.y, node.z);
		int j;
		if (this.mob.getPathfindingMalus(abovePathType) >= 0F && pathType != PathType.STICKY_HONEY) {
			j = Mth.floor(Math.max(1F, this.mob.maxUpStep()));
		} else {
			j = 0;
		}

		final double floorLevel = this.getFloorLevel(new BlockPos(node.x, node.y, node.z));
		final Node aboveNode = this.findAcceptedNode(node.x, node.y + 1, node.z, Math.max(0, j - 1), floorLevel, Direction.UP, pathType);
		final Node belowNode = this.findAcceptedNode(node.x, node.y - 1, node.z, j, floorLevel, Direction.DOWN, pathType);

		if (this.isVerticalNeighborValid(aboveNode, node)) successors[neighbors++] = aboveNode;
		if (this.isVerticalNeighborValid(belowNode, node) && pathType != PathType.TRAPDOOR) successors[neighbors++] = belowNode;

		for (int i = 0; i < neighbors; ++i) {
			final Node successor = successors[i];
			if (successor.type == PathType.WATER && this.prefersShallowSwimming && successor.y < this.mob.level().getSeaLevel() - 10) {
				++successor.costMalus;
			}
		}
		return neighbors;
	}

	private boolean isVerticalNeighborValid(@Nullable Node node, Node successor) {
		return this.isNeighborValid(node, successor) && (node != null && node.type == PathType.WATER);
	}

	@Override
	protected boolean isAmphibious() {
		return true;
	}

	@Override
	public PathType getPathType(PathfindingContext context, int x, int y, int z) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final PathType pathNodeType = getPathTypeFromState(context.level(), mutable.set(x, y, z));
		if (pathNodeType == PathType.WATER || pathNodeType == PathType.LAVA) {
			for (Direction direction : Direction.values()) {
				final PathType offsetPathType = getPathTypeFromState(context.level(), mutable.set(x, y, z).move(direction));
				if (offsetPathType == PathType.BLOCKED) return PathType.WATER_BORDER;
			}
			if (pathNodeType == PathType.WATER) return PathType.WATER;
			return PathType.LAVA;
		}
		return getPathTypeStatic(context, mutable);
	}

	private boolean isEntityTouchingWaterOrLava(Entity entity) {
		return entity.isInWater() || entity.isInLava() || entity.isVisuallySwimming();
	}

	private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
		return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
	}
}

