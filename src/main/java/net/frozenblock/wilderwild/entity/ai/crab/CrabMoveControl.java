package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.ai.control.MoveControl;

public class CrabMoveControl extends MoveControl {

	private final Crab crab;

	public CrabMoveControl(Crab crab) {
		super(crab);
		this.crab = crab;
	}

	@Override
	public void tick() {
		if (!this.crab.cancelMovementToDescend && !this.crab.isDiggingOrEmerging()) {
			super.tick();
		}
	}

}
