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

public class WardenMoveControl extends MoveControl {
	private final Warden warden;
	private final float pitchChange;
	private final float yawChange;
	private final float speedInWater;
	private final float speedInAir;

	public WardenMoveControl(Warden warden, float pitchChange, float yawChange, float speedInWater, float speedInAir) {
		super(warden);
		this.warden = warden;
		this.pitchChange = pitchChange;
		this.yawChange = yawChange;
		this.speedInWater = speedInWater;
		this.speedInAir = speedInAir;
	}

	@Override
	public void tick() {
		if (this.warden.isDiggingOrEmerging()) return;
		if (!WWEntityConfig.WARDEN_SWIMS || !this.touchingWaterOrLava(this.warden)) {
			super.tick();
			return;
		}

		if (this.operation == MoveControl.Operation.MOVE_TO && !this.warden.getNavigation().isDone()) {
			final double xDiff = this.wantedX - this.warden.getX();
			final double yDiff = this.wantedY - this.warden.getY();
			final double zDiff = this.wantedZ - this.warden.getZ();
			final double lengthSquared = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
			if (lengthSquared < 2.5000003E-7F) {
				this.warden.setZza(0F);
			} else {
				final float yRot = (float) (Mth.atan2(zDiff, xDiff) * Mth.RAD_TO_DEG) - 90F;
				this.warden.setYRot(this.rotlerp(this.warden.getYRot(), yRot, this.yawChange));
				this.warden.yBodyRot = this.warden.getYRot();
				this.warden.yHeadRot = this.warden.getYRot();
				float movementSpeed = (float) (this.speedModifier * this.warden.getAttributeValue(Attributes.MOVEMENT_SPEED));
				if (this.touchingWaterOrLava(warden)) {
					this.warden.setSpeed(movementSpeed * this.speedInWater);
					final double horizontalLengthSquared = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
					if (Math.abs(yDiff) > 1.0E-5F || Math.abs(horizontalLengthSquared) > 1.0E-5F) {
						float k = -((float) (Mth.atan2(yDiff, horizontalLengthSquared) * Mth.RAD_TO_DEG));
						k = Mth.clamp(Mth.wrapDegrees(k), -this.pitchChange, this.pitchChange);
						this.warden.setXRot(this.rotlerp(this.warden.getXRot(), k, 5F));
					}
					this.warden.zza = Mth.cos(this.warden.getXRot() * Mth.DEG_TO_RAD) * movementSpeed;
					this.warden.yya = -Mth.sin(this.warden.getXRot() * Mth.DEG_TO_RAD) * movementSpeed;
				} else {
					this.warden.setSpeed(movementSpeed * this.speedInAir);
				}
			}
		} else {
			this.warden.setSpeed(0F);
			this.warden.setXxa(0F);
			this.warden.setYya(0F);
			this.warden.setZza(0F);
		}
	}

	private boolean touchingWaterOrLava(Entity entity) {
		return entity.isInWater() || entity.isInLava() || entity.isVisuallySwimming();
	}
}
