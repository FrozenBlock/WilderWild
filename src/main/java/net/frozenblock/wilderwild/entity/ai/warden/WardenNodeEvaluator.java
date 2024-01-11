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

package net.frozenblock.wilderwild.entity.ai.warden;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
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
		mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.oldWalkableCost = mob.getPathfindingMalus(BlockPathTypes.WALKABLE);
		mob.setPathfindingMalus(BlockPathTypes.WALKABLE, 0.0F);
		this.oldWaterBorderPenalty = mob.getPathfindingMalus(BlockPathTypes.WATER_BORDER);
		mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 4.0F);
	}

	@Override
	public void done() {
		this.mob.setPathfindingMalus(BlockPathTypes.WALKABLE, this.oldWalkableCost);
		this.mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, this.oldWaterBorderPenalty);
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
	public Target getGoal(double x, double y, double z) {
		return !this.isEntitySubmergedInWaterOrLava(this.mob)
			? super.getGoal(x, y, z)
			: this.getTargetFromNode(this.getNode(Mth.floor(x), Mth.floor(y + 0.5), Mth.floor(z)));
	}

	@Override
	public int getNeighbors(Node @NotNull [] successors, @NotNull Node node) {
		if (!isEntitySubmergedInWaterOrLava(this.mob)) {
			return super.getNeighbors(successors, node);
		} else {
			int i = super.getNeighbors(successors, node);
			BlockPathTypes blockPathTypes = this.getCachedBlockType(this.mob, node.x, node.y + 1, node.z);
			BlockPathTypes blockPathTypes2 = this.getCachedBlockType(this.mob, node.x, node.y, node.z);
			int j;
			if (this.mob.getPathfindingMalus(blockPathTypes) >= 0.0F && blockPathTypes2 != BlockPathTypes.STICKY_HONEY) {
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

			if (this.isVerticalNeighborValid(node3, node) && blockPathTypes2 != BlockPathTypes.TRAPDOOR) {
				successors[i++] = node3;
			}

			for (int k = 0; k < i; ++k) {
				Node node4 = successors[k];
				if (node4.type == BlockPathTypes.WATER && this.prefersShallowSwimming && node4.y < this.mob.level().getSeaLevel() - 10) {
					++node4.costMalus;
				}
			}

			return i;
		}
	}

	private boolean isVerticalNeighborValid(@Nullable Node node, @NotNull Node successor) {
		return this.isNeighborValid(node, successor) && (node != null && node.type == BlockPathTypes.WATER);
	}

	@Override
	protected boolean isAmphibious() {
		return true;
	}

	@Override
	@NotNull
	public BlockPathTypes getBlockPathType(@NotNull BlockGetter level, int x, int y, int z) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockPathTypes pathNodeType = getBlockPathTypeRaw(level, mutable.set(x, y, z));
		if (pathNodeType == BlockPathTypes.WATER || pathNodeType == BlockPathTypes.LAVA) {
			for (Direction direction : Direction.values()) {
				BlockPathTypes pathNodeType2 = getBlockPathTypeRaw(level, mutable.set(x, y, z).move(direction));
				if (pathNodeType2 == BlockPathTypes.BLOCKED) {
					return BlockPathTypes.WATER_BORDER;
				}
			}

			if (pathNodeType == BlockPathTypes.WATER) {
				return BlockPathTypes.WATER;
			} else {
				return BlockPathTypes.LAVA;
			}
		} else {
			return getBlockPathTypeStatic(level, mutable);
		}
	}

	private boolean isEntityTouchingWaterOrLava(@NotNull Entity entity) {
		return entity.isInWaterOrBubble() || entity.isInLava() || entity.isVisuallySwimming();
	}

	private boolean isEntitySubmergedInWaterOrLava(@NotNull Entity entity) {
		return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
	}
}

