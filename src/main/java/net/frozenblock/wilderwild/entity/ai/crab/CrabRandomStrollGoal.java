package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class CrabRandomStrollGoal extends RandomStrollGoal {

	private final Crab mob;
	public CrabRandomStrollGoal(Crab mob, double speedModifier) {
		super(mob, speedModifier);
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return !this.mob.isDiggingOrEmerging() && super.canUse();
	}

	@Override
	public boolean canContinueToUse() {
		return !this.mob.isDiggingOrEmerging() && super.canContinueToUse();
	}

}
