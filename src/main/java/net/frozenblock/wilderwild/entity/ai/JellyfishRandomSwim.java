package net.frozenblock.wilderwild.entity.ai;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import org.jetbrains.annotations.NotNull;

public class JellyfishRandomSwim extends RandomSwim {
	public JellyfishRandomSwim(float speedModifier) {
		super(speedModifier);
	}

	/*@Nullable
	@Override
	protected Vec3 getTargetPos(@NotNull PathfinderMob pathfinder) {
		return BehaviorUtils.getRandomSwimmablePos(pathfinder, 10, 7);
	}*/

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PathfinderMob pathfinder) {
		if (!(pathfinder instanceof Jellyfish jellyfish)) {
			throw new RuntimeException("IMAGINE USING JELLYFISH TASK FOR SOMETHING ELSE");
		} else {
			return jellyfish.getTarget() == null && jellyfish.canRandomSwim() && super.checkExtraStartConditions(level, jellyfish);
		}
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, PathfinderMob pathfinder, long gameTime) {
		return pathfinder.getTarget() == null && !pathfinder.getNavigation().isDone() && !pathfinder.isVehicle();
	}
}
