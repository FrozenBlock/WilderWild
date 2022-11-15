package net.frozenblock.wilderwild.entity.ai;

import jdk.jfr.Experimental;
import net.frozenblock.lib.entity.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;

@Experimental
public class FireflyHide extends MoveToBlockBehavior<Firefly> {

	public FireflyHide(Firefly mob, double speedModifier, int searchRange, int verticalSearchRange) {
		super(mob, speedModifier, searchRange, verticalSearchRange);
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Firefly owner) {
		return owner.shouldHide() && super.checkExtraStartConditions(level, owner);
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, @NotNull Firefly entity, long gameTime) {
		return entity.shouldHide() && super.canStillUse(level, entity, gameTime);
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull Firefly owner, long gameTime) {
		if (this.isReachedTarget()) {
			owner.playSound(RegisterSounds.ENTITY_FIREFLY_HIDE, 1.0F, 1.2F);
			owner.discard();
		}

		super.tick(level, owner, gameTime);
	}

	@Override
	public boolean isValidTarget(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).is(WilderBlockTags.FIREFLY_HIDEABLE_BLOCKS);
	}

	@Override
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
