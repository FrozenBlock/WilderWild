package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WardenNavigation extends MobNavigation {

    public WardenNavigation(MobEntity warden, World world) {
        super(warden, world);
    }

    @Override
    public PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new WardenPathNodeMaker(false);
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range) {
            public float getDistance(PathNode a, PathNode b) {
                if (this.isEntitySubmergedInWaterOrLava(entity)) {
                    return a.getDistance(b);
                } else {
                    return a.getHorizontalDistance(b);
                }
            }

            private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
                return entity.isSubmergedIn(FluidTags.WATER) || entity.isSubmergedIn(FluidTags.LAVA);
            }
        };
    }

    @Override
    protected boolean isAtValidPosition() {
        if (this.isEntityTouchingWaterOrLava(this.entity)) {
            return true;
        } else {
            return this.entity.isOnGround() || this.isInLiquid() || this.entity.hasVehicle();
        }
    }

    @Override
    protected Vec3d getPos() {
        return new Vec3d(this.entity.getX(), this.getPathfindingY(), this.entity.getZ());
    }

    private int getPathfindingY() {
        if (this.isEntityTouchingWaterOrLava(this.entity) && this.canSwim()) {
            int i = this.entity.getBlockY();
            BlockState blockState = this.world.getBlockState(new BlockPos(this.entity.getX(), i, this.entity.getZ()));
            int j = 0;

            while (blockState.getFluidState().isIn(FluidTags.WATER) || blockState.getFluidState().isIn(FluidTags.LAVA)) {
                blockState = this.world.getBlockState(new BlockPos(this.entity.getX(), ++i, this.entity.getZ()));
                if (++j > 16) {
                    return this.entity.getBlockY();
                }
            }

            return i;
        } else {
            return MathHelper.floor(this.entity.getY() + 0.5);
        }
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        return pos.y;
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return doesNotCollide(this.entity, origin, target);
    }

    @Override
    public void setCanSwim(boolean canSwim) {
    }

    @Override
    protected boolean canWalkOnPath(PathNodeType pathType) {
        return pathType != PathNodeType.OPEN;
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isInsideWaterOrBubbleColumn() || entity.isInLava();
    }
}
