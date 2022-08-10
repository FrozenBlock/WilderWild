package net.frozenblock.wilderwild.entity.ai;

import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class FireflyHidingGoal extends MoveToTargetPosGoal {
    public FireflyHidingGoal(Firefly mob, double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
    }

    @Override
    public boolean canStart() {
        if (!((Firefly) this.mob).shouldHide()) return false;

        return super.canStart();
    }

    @Override
    public void start() {
        super.start();
        this.mob.getBrain().clear();
    }

    @Override
    public void tick() {
        if (this.hasReached()) {
            this.mob.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.3F);
            this.mob.discard();
        }

        super.tick();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isIn(WilderBlockTags.FIREFLY_HIDEABLE_BLOCKS);
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }
}
