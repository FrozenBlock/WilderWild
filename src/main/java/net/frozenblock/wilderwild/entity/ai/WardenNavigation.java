package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WardenNavigation extends MobNavigation {

    public WardenNavigation(MobEntity warden, World world) {
        super(warden, world);
    }

    @Override
    public PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new WardenPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range) {
            public float getDistance(PathNode a, PathNode b) {
                return a.getHorizontalDistance(b);
            }

            private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
                return entity.isSubmergedIn(FluidTags.WATER) || entity.isSubmergedIn(FluidTags.LAVA);
            }
        };
    }

    @Override
    protected boolean isAtValidPosition() {
        return this.isEntityTouchingWaterOrLava(this.entity) || super.isAtValidPosition();
    }

    @Override
    protected Vec3d getPos() {
        return this.isEntityTouchingWaterOrLava(this.entity) ? new Vec3d(this.entity.getX(), this.entity.getBodyY(0.5), this.entity.getZ()) : super.getPos();
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
