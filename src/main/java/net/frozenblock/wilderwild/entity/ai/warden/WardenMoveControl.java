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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;

public class WardenMoveControl extends MoveControl {
	private final Warden mob;
	private final float pitchChange;
	private final float yawChange;
	private final float speedInWater;
	private final float speedInAir;

	public WardenMoveControl(@NotNull Warden mob, float pitchChange, float yawChange, float speedInWater, float speedInAir) {
		super(mob);
		this.mob = mob;
		this.pitchChange = pitchChange;
		this.yawChange = yawChange;
		this.speedInWater = speedInWater;
		this.speedInAir = speedInAir;
	}

	@Override
	public void tick() {
		if (this.mob.isDiggingOrEmerging()) return;
		if (!WWEntityConfig.WARDEN_SWIMS || !this.touchingWaterOrLava(this.mob)) {
			super.tick();
			return;
		}

		if (this.operation == MoveControl.Operation.MOVE_TO && !this.mob.getNavigation().isDone()) {
			final double xDiff = this.wantedX - this.mob.getX();
			final double yDiff = this.wantedY - this.mob.getY();
			final double zDiff = this.wantedZ - this.mob.getZ();
			final double lengthSquared = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
			if (lengthSquared < 2.5000003E-7F) {
				this.mob.setZza(0F);
			} else {
				final float yRot = (float) (Mth.atan2(zDiff, xDiff) * Mth.RAD_TO_DEG) - 90F;
				this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yRot, this.yawChange));
				this.mob.yBodyRot = this.mob.getYRot();
				this.mob.yHeadRot = this.mob.getYRot();
				float movementSpeed = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
				if (this.touchingWaterOrLava(mob)) {
					this.mob.setSpeed(movementSpeed * this.speedInWater);
					final double horizontalLengthSquared = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
					if (Math.abs(yDiff) > 1.0E-5F || Math.abs(horizontalLengthSquared) > 1.0E-5F) {
						float k = -((float) (Mth.atan2(yDiff, horizontalLengthSquared) * Mth.RAD_TO_DEG));
						k = Mth.clamp(Mth.wrapDegrees(k), -this.pitchChange, this.pitchChange);
						this.mob.setXRot(this.rotlerp(this.mob.getXRot(), k, 5F));
					}
					this.mob.zza = Mth.cos(this.mob.getXRot() * Mth.DEG_TO_RAD) * movementSpeed;
					this.mob.yya = -Mth.sin(this.mob.getXRot() * Mth.DEG_TO_RAD) * movementSpeed;
				} else {
					this.mob.setSpeed(movementSpeed * this.speedInAir);
				}
			}
		} else {
			this.mob.setSpeed(0F);
			this.mob.setXxa(0F);
			this.mob.setYya(0F);
			this.mob.setZza(0F);
		}
	}

	private boolean touchingWaterOrLava(@NotNull Entity entity) {
		return entity.isInWater() || entity.isInLava() || entity.isVisuallySwimming();
	}
}
