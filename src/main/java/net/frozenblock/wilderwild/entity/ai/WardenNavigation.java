package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlSwimNavigation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;

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
                if (entity.isSubmergedInWater()) {
                    return a.getDistance(b);
                } else {
                    return a.getHorizontalDistance(b);
                }
            }
        };
    }

    @Override
    protected boolean isAtValidPosition() {
        return true;
    }

    @Override
    protected Vec3d getPos() {
        return new Vec3d(this.entity.getX(), this.entity.getBodyY(1.0D), this.entity.getZ());
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
}
