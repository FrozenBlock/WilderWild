package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class WardenNavigation extends MobNavigation {

    private final WardenEntity entity;

    public WardenNavigation(@NotNull WardenEntity warden, World world) {
        super(warden, world);
        this.entity = warden;
    }

    @Override
    public PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new WardenPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range) {
            public float getDistance(PathNode a, PathNode b) {
                return this.isEntitySubmergedInWaterOrLava(entity) ? a.getDistance(b) : a.getHorizontalDistance(b);
            }

            private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
                return entity.isSubmergedInWater() || entity.isSubmergedIn(FluidTags.LAVA);
            }
        };
    }

    @Override
    protected Vec3d getPos() {
        return this.isInLiquid() ? new Vec3d(this.entity.getX(), this.entity.getBodyY(0.5), this.entity.getZ()) : super.getPos();
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        BlockPos blockPos = new BlockPos(pos);
        return this.isInLiquid() || this.world.getBlockState(blockPos.down()).isAir() ? pos.y : WardenPathNodeMaker.getFeetY(this.world, blockPos);
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return this.isInLiquid() ? doesNotCollide(this.entity, origin, target) : super.canPathDirectlyThrough(origin, target);
    }

    @Override
    public void setCanSwim(boolean canSwim) {
    }

    @Override
    protected boolean canWalkOnPath(PathNodeType pathType) {
        return pathType != PathNodeType.OPEN;
    }
}
