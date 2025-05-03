/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity.ai.warden;

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;

public class WardenLookControl extends LookControl {

	private final int maxYRotFromCenter;

	public WardenLookControl(@NotNull Warden warden, int maxYRotFromCenter) {
		super(warden);
		this.maxYRotFromCenter = maxYRotFromCenter;
	}

	private static boolean entityTouchingWaterOrLava(@NotNull Entity entity) {
		return entity.isInWater() || entity.isInLava() || entity.isVisuallySwimming();
	}

	@Override
	public void tick() {
		if (!WWEntityConfig.WARDEN_SWIMS || !entityTouchingWaterOrLava(this.mob)) {
			super.tick();
		} else {
			if (this.lookAtCooldown > 0) {
				--this.lookAtCooldown;
				this.getYRotD().ifPresent(rot -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, rot + 20F, this.yMaxRotSpeed));
				this.getXRotD().ifPresent(rot -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), rot + 10F, this.xMaxRotAngle)));
			} else {
				if (this.mob.getNavigation().isDone()) this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0F, 5F));
				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
			}

			float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
			if (f < (float) (-this.maxYRotFromCenter)) {
				this.mob.yBodyRot -= 4F;
			} else if (f > (float) this.maxYRotFromCenter) {
				this.mob.yBodyRot += 4F;
			}
		}
	}
}
