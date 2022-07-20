package net.frozenblock.wilderwild.entity.ai;

import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class FireflyHidingGoal extends MoveToBlockGoal {
    public FireflyHidingGoal(Firefly mob, double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
    }

    @Override
    public boolean canUse() {
        if (!((Firefly) this.mob).shouldHide()) return false;

        return super.canUse();
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            this.mob.discard();
        }

        super.tick();
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos).is(WilderBlockTags.FIREFLY_HIDEABLE_BLOCKS);
    }

    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }
}
