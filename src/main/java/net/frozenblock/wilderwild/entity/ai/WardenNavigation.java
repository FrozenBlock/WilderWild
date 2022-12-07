package net.frozenblock.wilderwild.entity.ai;

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

    public WardenNavigation(@NotNull Warden warden, Level level) {
        super(warden, level);
        this.entity = warden;
    }

    @Override
    public PathFinder createPathFinder(int range) {
        this.nodeEvaluator = new WardenPathEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, range) {
			@Override
            public float distance(Node a, Node b) {
                return this.entitySubmergedInWaterOrLava(entity) ? a.distanceTo(b) : a.distanceToXZ(b);
            }

            private static boolean entitySubmergedInWaterOrLava(Entity entity) {
                return entity.isUnderWater() || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
            }
        };
    }

    @Override
    protected Vec3 getTempMobPos() {
        return this.isInLiquid() ? new Vec3(this.entity.getX(), this.entity.getY(0.5), this.entity.getZ()) : super.getTempMobPos();
    }

    @Override
    protected double getGroundY(@NotNull Vec3 pos) {
        BlockPos blockPos = new BlockPos(pos);
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
