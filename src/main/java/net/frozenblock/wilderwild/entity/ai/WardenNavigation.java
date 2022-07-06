package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WardenNavigation extends MobNavigation {

    public WardenNavigation(MobEntity warden, World world) {
        super(warden, world);
    }

    public PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new AmphibiousPathNodeMaker(false);
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range) {
            public float getDistance(PathNode a, PathNode b) {
                if (!entity.isSwimming()) {
                    return a.getHorizontalDistance(b);
                } else {
                    return a.getDistance(b);
                }
            }
        };
    }

    protected boolean isAtValidPosition() {
        return true;
    }

    protected Vec3d getPos() {
        return new Vec3d(this.entity.getX(), this.entity.getBodyY(1.0D), this.entity.getZ());
    }

    protected double adjustTargetY(Vec3d pos) {
        return pos.y;
    }

    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return doesNotCollide(this.entity, origin, target);
    }

    public void setCanSwim(boolean canSwim) {
    }
}
