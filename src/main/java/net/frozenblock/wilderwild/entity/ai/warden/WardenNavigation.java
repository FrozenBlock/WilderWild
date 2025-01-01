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

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WardenNavigation extends GroundPathNavigation {

	private final Warden mob;

	public WardenNavigation(@NotNull Warden mob, @NotNull Level level) {
		super(mob, level);
		this.mob = mob;
	}

	@Override
	@NotNull
	public PathFinder createPathFinder(int range) {
		this.nodeEvaluator = new WardenNodeEvaluator(false);
		this.nodeEvaluator.setCanPassDoors(true);
		return new PathFinder(this.nodeEvaluator, range) {
			private static boolean entitySubmergedInWaterOrLava(@NotNull Entity entity) {
				return entity.isUnderWater() || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
			}

			@Override
			public float distance(@NotNull Node a, @NotNull Node b) {
				return WWEntityConfig.WARDEN_SWIMS && this.entitySubmergedInWaterOrLava(mob) ? a.distanceTo(b) : a.distanceToXZ(b);
			}
		};
	}

	@Override
	@NotNull
	protected Vec3 getTempMobPos() {
		return WWEntityConfig.WARDEN_SWIMS && this.isInLiquid() ? new Vec3(this.mob.getX(), this.mob.getY(0.5), this.mob.getZ()) : super.getTempMobPos();
	}

	@Override
	protected double getGroundY(@NotNull Vec3 pos) {
		BlockPos blockPos = BlockPos.containing(pos);
		return WWEntityConfig.WARDEN_SWIMS && (this.isInLiquid() || this.level.getBlockState(blockPos.below()).isAir()) ? pos.y : WardenNodeEvaluator.getFloorLevel(this.level, blockPos);
	}

	@Override
	protected boolean canMoveDirectly(@NotNull Vec3 origin, @NotNull Vec3 target) {
		return WWEntityConfig.WARDEN_SWIMS && this.isInLiquid() ? isClearForMovementBetween(this.mob, origin, target, false) : super.canMoveDirectly(origin, target);
	}

	@Override
	protected boolean hasValidPathType(@NotNull PathType pathType) {
		return WWEntityConfig.WARDEN_SWIMS ? pathType != PathType.OPEN : super.hasValidPathType(pathType);
	}

	@Override
	protected boolean canUpdatePath() {
		return super.canUpdatePath() || (this.mob.isVisuallySwimming() && WWEntityConfig.WARDEN_SWIMS);
	}

	public boolean isInLiquid() {
		return this.mob.isInLiquid() || (this.mob.isVisuallySwimming() && WWEntityConfig.WARDEN_SWIMS);
	}
}
