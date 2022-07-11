package net.frozenblock.wilderwild.entity.ai;

import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

public class FireflyHidingGoal extends MoveToTargetPosGoal {
    public FireflyHidingGoal(FireflyEntity mob, double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
    }

    @Override
    public boolean canStart() {
        if (!((FireflyEntity) this.mob).shouldHide()) return false;

        return super.canStart();
    }

    @Override
    public void tick() {
        if (this.hasReached()) {
            this.mob.discard();
        }

        super.tick();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.GRASS) || world.getBlockState(pos).isOf(Blocks.TALL_GRASS);
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }
}
