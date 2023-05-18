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

package net.frozenblock.wilderwild.entity.ai.warden;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WardenNavigation extends GroundPathNavigation {

    private final Warden entity;

    public WardenNavigation(@NotNull Warden warden, @NotNull Level level) {
        super(warden, level);
        this.entity = warden;
    }

    @Override
	@NotNull
    public PathFinder createPathFinder(int range) {
        this.nodeEvaluator = new WardenPathEvaluator(false);
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, range) {
			@Override
            public float distance(@NotNull Node a, @NotNull Node b) {
                return this.entitySubmergedInWaterOrLava(entity) ? a.distanceTo(b) : a.distanceToXZ(b);
            }

            private static boolean entitySubmergedInWaterOrLava(Entity entity) {
                return entity.isUnderWater() || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
            }
        };
    }

    @Override
	@NotNull
    protected Vec3 getTempMobPos() {
        return this.isInLiquid() ? new Vec3(this.entity.getX(), this.entity.getY(0.5), this.entity.getZ()) : super.getTempMobPos();
    }

    @Override
    protected double getGroundY(@NotNull Vec3 pos) {
        BlockPos blockPos = BlockPos.containing(pos);
        return this.isInLiquid() || this.level.getBlockState(blockPos.below()).isAir() ? pos.y : WardenPathEvaluator.getFloorLevel(this.level, blockPos);
    }

    @Override
    protected boolean canMoveDirectly(@NotNull Vec3 origin, @NotNull Vec3 target) {
        return this.isInLiquid() ? isClearForMovementBetween(this.entity, origin, target, false) : super.canMoveDirectly(origin, target);
    }

    @Override
    public void setCanFloat(boolean canSwim) {
    }

    @Override
    protected boolean hasValidPathType(@NotNull BlockPathTypes pathType) {
        return pathType != BlockPathTypes.OPEN;
    }

    @Override
    public boolean isInLiquid() {
        return super.isInLiquid() || this.entity.isVisuallySwimming();
    }
}
