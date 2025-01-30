/*
 * Copyright 2023-2025 FrozenBlock
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WardenNodeEvaluator extends WalkNodeEvaluator {
	private final boolean prefersShallowSwimming;
	private float oldWalkableCost;
	private float oldWaterBorderPenalty;

	public WardenNodeEvaluator(boolean prefersShallowSwimming) {
		this.prefersShallowSwimming = prefersShallowSwimming;
	}

	@Override
	public void prepare(@NotNull PathNavigationRegion level, @NotNull Mob mob) {
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
	@NotNull
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
	@NotNull
	public Target getTarget(double x, double y, double z) {
		return !this.isEntitySubmergedInWaterOrLava(this.mob)
			? super.getTarget(x, y, z)
			: this.getTargetNodeAt(x, y + 0.5, z);
	}

	@Override
	public int getNeighbors(Node @NotNull [] successors, @NotNull Node node) {
		if (!isEntitySubmergedInWaterOrLava(this.mob)) {
			return super.getNeighbors(successors, node);
		} else {
			int i = super.getNeighbors(successors, node);
			PathType blockPathTypes = this.getCachedPathType(node.x, node.y + 1, node.z);
			PathType blockPathTypes2 = this.getCachedPathType(node.x, node.y, node.z);
			int j;
			if (this.mob.getPathfindingMalus(blockPathTypes) >= 0.0F && blockPathTypes2 != PathType.STICKY_HONEY) {
				j = Mth.floor(Math.max(1.0F, this.mob.maxUpStep()));
			} else {
				j = 0;
			}

			double d = this.getFloorLevel(new BlockPos(node.x, node.y, node.z));
			Node node2 = this.findAcceptedNode(node.x, node.y + 1, node.z, Math.max(0, j - 1), d, Direction.UP, blockPathTypes2);
			Node node3 = this.findAcceptedNode(node.x, node.y - 1, node.z, j, d, Direction.DOWN, blockPathTypes2);
			if (this.isVerticalNeighborValid(node2, node)) {
				successors[i++] = node2;
			}

			if (this.isVerticalNeighborValid(node3, node) && blockPathTypes2 != PathType.TRAPDOOR) {
				successors[i++] = node3;
			}

			for (int k = 0; k < i; ++k) {
				Node node4 = successors[k];
				if (node4.type == PathType.WATER && this.prefersShallowSwimming && node4.y < this.mob.level().getSeaLevel() - 10) {
					++node4.costMalus;
				}
			}

			return i;
		}
	}

	private boolean isVerticalNeighborValid(@Nullable Node node, @NotNull Node successor) {
		return this.isNeighborValid(node, successor) && (node != null && node.type == PathType.WATER);
	}

	@Override
	protected boolean isAmphibious() {
		return true;
	}

	@NotNull
	@Override
	public PathType getPathType(PathfindingContext context, int x, int y, int z) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		PathType pathNodeType = getPathTypeFromState(context.level(), mutable.set(x, y, z));
		if (pathNodeType == PathType.WATER || pathNodeType == PathType.LAVA) {
			for (Direction direction : Direction.values()) {
				PathType pathNodeType2 = getPathTypeFromState(context.level(), mutable.set(x, y, z).move(direction));
				if (pathNodeType2 == PathType.BLOCKED) {
					return PathType.WATER_BORDER;
				}
			}

			if (pathNodeType == PathType.WATER) {
				return PathType.WATER;
			} else {
				return PathType.LAVA;
			}
		} else {
			return getPathTypeStatic(context, mutable);
		}
	}

	private boolean isEntityTouchingWaterOrLava(@NotNull Entity entity) {
		return entity.isInWater() || entity.isInLava() || entity.isVisuallySwimming();
	}

	private boolean isEntitySubmergedInWaterOrLava(@NotNull Entity entity) {
		return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
	}
}

