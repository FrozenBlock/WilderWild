package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenLookControl extends LookControl {

	private final int maxYRotFromCenter;

	public WardenLookControl(Warden warden, int maxYRotFromCenter) {
		super(warden);
		this.maxYRotFromCenter = maxYRotFromCenter;
	}

	@Override
	public void tick() {
		if (!entityTouchingWaterOrLava(this.mob)) {
			super.tick();
		} else {
			if (this.lookAtCooldown > 0) {
				--this.lookAtCooldown;
				this.getYRotD().ifPresent((float_) -> {
					this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, float_ + 20.0F, this.yMaxRotSpeed);
				});
				this.getXRotD().ifPresent((float_) -> {
					this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), float_ + 10.0F, this.xMaxRotAngle));
				});
			} else {
				if (this.mob.getNavigation().isDone()) {
					this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
				}

				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
			}

			float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
			Mob var10000;
			if (f < (float) (-this.maxYRotFromCenter)) {
				var10000 = this.mob;
				var10000.yBodyRot -= 4.0F;
			} else if (f > (float) this.maxYRotFromCenter) {
				var10000 = this.mob;
				var10000.yBodyRot += 4.0F;
			}
		}
	}

	private static boolean entityTouchingWaterOrLava(Entity entity) {
		return entity.isInWaterOrBubble() || entity.isInLava() || entity.isVisuallySwimming();
	}
}
