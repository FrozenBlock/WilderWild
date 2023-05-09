/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.server.warden;

import net.frozenblock.wilderwild.entity.ai.warden.WardenLookControl;
import net.frozenblock.wilderwild.entity.ai.warden.WardenMoveControl;
import net.frozenblock.wilderwild.entity.ai.warden.WardenNavigation;
import net.frozenblock.wilderwild.misc.interfaces.SwimmingWardenInterface;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Warden.class, priority = 1001)
public abstract class WardenSwimMixin extends Monster implements SwimmingWardenInterface {

	private WardenSwimMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow
	public abstract boolean isDiggingOrEmerging();

	@Unique
	private float wilderWild$leaningPitch;
	@Unique
	private float wilderWild$lastLeaningPitch;
	@Unique
	private boolean wilderWild$pogSwimming;

	@Inject(method = "tick", at = @At("TAIL"))
	private void wilderWild$tick(CallbackInfo ci) {
		this.wilderWild$updateSwimAmount();
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putBoolean("pogSwimming", this.wilderWild$pogSwimming);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		this.wilderWild$pogSwimming = nbt.getBoolean("pogSwimming");
	}

	@Unique
	private void wilderWild$updateSwimAmount() {
		this.wilderWild$lastLeaningPitch = this.wilderWild$leaningPitch;
		if (this.isVisuallySwimming()) {
			this.wilderWild$leaningPitch = Math.min(1.0F, this.wilderWild$leaningPitch + 0.09F);
		} else {
			this.wilderWild$leaningPitch = Math.max(0.0F, this.wilderWild$leaningPitch - 0.09F);
		}
	}

	@Override
	public boolean isVisuallySwimming() {
		return this.wilderWild$pogSwimming || super.isVisuallySwimming();
	}

	@Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
	public void wilderWild$createNavigation(Level level, CallbackInfoReturnable<PathNavigation> info) {
		info.setReturnValue(new WardenNavigation(Warden.class.cast(this), level));
	}

	@Override
	public void travel(@NotNull Vec3 movementInput) {
		Warden warden = Warden.class.cast(this);
		if (this.isEffectiveAi() && this.wilderWild$isTouchingWaterOrLava()) {
			this.moveRelative(this.getSpeed(), movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (!this.isDiggingOrEmerging() && !warden.hasPose(Pose.SNIFFING) && !warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
				if (this.wilderWild$isSubmergedInWaterOrLava()) {
					warden.setPose(Pose.SWIMMING);
				} else {
					warden.setPose(Pose.STANDING);
				}
			}

			this.wilderWild$pogSwimming = this.getFluidHeight(FluidTags.WATER) >= this.getEyeHeight(this.getPose()) * 0.75 || this.getFluidHeight(FluidTags.LAVA) >= this.getEyeHeight(this.getPose()) * 0.75;
		} else {
			super.travel(movementInput);
			this.wilderWild$pogSwimming = false;
		}

	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$wardenEntity(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
		Warden wardenEntity = Warden.class.cast(this);
		wardenEntity.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		wardenEntity.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
		wardenEntity.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
		this.moveControl = new WardenMoveControl(wardenEntity, 0.05F, 80.0F, 0.13F, 1.0F, true);
		this.lookControl = new WardenLookControl(wardenEntity, 10);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	@NotNull
	public SoundEvent getSwimSound() {
		return RegisterSounds.ENTITY_WARDEN_SWIM;
	}

	@Override
	public void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
	}

	@Unique
	public float getSwimAmount(float tickDelta) {
		return Mth.lerp(tickDelta, this.wilderWild$lastLeaningPitch, this.wilderWild$leaningPitch);
	}

	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		Warden warden = Warden.class.cast(this);
		this.fluidHeight.clear();
		warden.updateInWaterStateAndDoWaterCurrentPushing();
		boolean bl = warden.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 0.1D);
		return this.wilderWild$isTouchingWaterOrLava() || bl;
	}

	@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
		Warden warden = Warden.class.cast(this);
		if (warden.isVisuallySwimming()) {
			info.setReturnValue(EntityDimensions.scalable(warden.getType().getWidth(), 0.85F));
		}
	}

	@Override
	public boolean wilderWild$isTouchingWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isInWaterOrBubble() || warden.isInLava();
	}

	@Override
	public boolean wilderWild$isSubmergedInWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
	}
}
