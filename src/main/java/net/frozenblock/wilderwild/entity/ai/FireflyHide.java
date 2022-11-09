package net.frozenblock.wilderwild.entity.ai;

import jdk.jfr.Experimental;
import net.frozenblock.lib.entities.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.Set;

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
	public void start(@NotNull ServerLevel level, @NotNull Firefly entity, long gameTime) {
		super.start(level, entity, gameTime);
		//stopOtherBehaviors(level, entity, gameTime);
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull Firefly owner, long gameTime) {
		if (this.isReachedTarget()) {
			owner.playSound(SoundEvents.BEEHIVE_ENTER, 1.0F, 1.3F);
			owner.discard();
		}
		//stopOtherBehaviors(level, owner, gameTime);

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

	private static void stopOtherBehaviors(@NotNull ServerLevel level, @NotNull Firefly owner, long gameTime) {
		for(Map<Activity, Set<Behavior<? super Firefly>>> map : owner.getBrain().availableBehaviorsByPriority.values()) {
			for(Set<Behavior<? super Firefly>> set : map.values()) {
				for(Behavior<? super Firefly> behavior : set) {
					if (!(behavior instanceof MoveToBlockBehavior)) {
						if (behavior.getStatus() == Status.RUNNING) {
							behavior.doStop(level, owner, gameTime);
						}
					}
				}
			}
		}
	}
}
