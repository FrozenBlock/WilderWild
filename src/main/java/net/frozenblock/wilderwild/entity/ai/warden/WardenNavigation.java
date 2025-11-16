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

public class WardenNavigation extends GroundPathNavigation {
	private final Warden warden;

	public WardenNavigation(Warden warden, Level level) {
		super(warden, level);
		this.warden = warden;
	}

	@Override
	public PathFinder createPathFinder(int range) {
		this.nodeEvaluator = new WardenNodeEvaluator(false);
		this.nodeEvaluator.setCanPassDoors(true);
		return new PathFinder(this.nodeEvaluator, range) {
			private static boolean entitySubmergedInWaterOrLava(Entity entity) {
				return entity.isUnderWater() || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
			}

			@Override
			public float distance(Node a, Node b) {
				return WWEntityConfig.WARDEN_SWIMS && this.entitySubmergedInWaterOrLava(warden) ? a.distanceTo(b) : a.distanceToXZ(b);
			}
		};
	}

	@Override
	protected Vec3 getTempMobPos() {
		return WWEntityConfig.WARDEN_SWIMS && this.isInLiquid() ? new Vec3(this.warden.getX(), this.warden.getY(0.5), this.warden.getZ()) : super.getTempMobPos();
	}

	@Override
	protected double getGroundY(Vec3 pos) {
		final BlockPos blockPos = BlockPos.containing(pos);
		return WWEntityConfig.WARDEN_SWIMS && (this.isInLiquid() || this.level.getBlockState(blockPos.below()).isAir()) ? pos.y : WardenNodeEvaluator.getFloorLevel(this.level, blockPos);
	}

	@Override
	protected boolean canMoveDirectly(Vec3 origin, Vec3 target) {
		return WWEntityConfig.WARDEN_SWIMS && this.isInLiquid() ? isClearForMovementBetween(this.warden, origin, target, false) : super.canMoveDirectly(origin, target);
	}

	@Override
	protected boolean hasValidPathType(PathType type) {
		return WWEntityConfig.WARDEN_SWIMS ? type != PathType.OPEN : super.hasValidPathType(type);
	}

	@Override
	protected boolean canUpdatePath() {
		return super.canUpdatePath() || (this.warden.isVisuallySwimming() && WWEntityConfig.WARDEN_SWIMS);
	}

	public boolean isInLiquid() {
		return this.warden.isInLiquid() || (this.warden.isVisuallySwimming() && WWEntityConfig.WARDEN_SWIMS);
	}
}
