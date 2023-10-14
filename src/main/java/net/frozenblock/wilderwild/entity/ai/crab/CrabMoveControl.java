package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.ai.control.MoveControl;

public class CrabMoveControl extends MoveControl {

	private final Crab mob;

	public CrabMoveControl(Crab mob) {
		super(mob);
		this.mob = mob;
	}

	@Override
	public void tick() {
		if (!this.mob.isDiggingOrEmerging() && this.mob.getNavigation().isInProgress()) {
			super.tick();
		}
	}

}

