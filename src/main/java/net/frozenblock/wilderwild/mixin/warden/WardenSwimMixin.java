/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.warden;

import java.util.Optional;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.ai.warden.WardenLookControl;
import net.frozenblock.wilderwild.entity.ai.warden.WardenMoveControl;
import net.frozenblock.wilderwild.entity.ai.warden.WardenNavigation;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenInterface;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
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

	@Unique
	private float wilderWild$leaningPitch;
	@Unique
	private float wilderWild$lastLeaningPitch;
	@Unique
	private boolean wilderWild$newSwimming;

	private WardenSwimMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Unique
	private static boolean touchingWaterOrLava(@NotNull Entity entity) {
		return entity.isInWaterOrBubble() || entity.isInLava() || entity.isVisuallySwimming();
	}

	@Unique
	private static boolean submergedInWaterOrLava(@NotNull Entity entity) {
		return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
	}

	@Shadow
	public abstract boolean isDiggingOrEmerging();

	@Shadow
	public abstract Brain<Warden> getBrain();

	@Inject(method = "tick", at = @At("TAIL"))
	private void wilderWild$tick(CallbackInfo ci) {
		this.wilderWild$updateSwimAmount();
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putBoolean("newSwimming", this.wilderWild$newSwimming);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		this.wilderWild$newSwimming = nbt.getBoolean("newSwimming");
	}

	@Unique
	private void wilderWild$updateSwimAmount() {
		this.wilderWild$lastLeaningPitch = this.wilderWild$leaningPitch;
		if (this.isVisuallySwimming()) {
			this.wilderWild$leaningPitch = Math.min(1F, this.wilderWild$leaningPitch + 0.09F);
		} else {
			this.wilderWild$leaningPitch = Math.max(0F, this.wilderWild$leaningPitch - 0.09F);
		}
	}

	@Override
	public boolean isVisuallySwimming() {
		return this.wilderWild$newSwimming || super.isVisuallySwimming();
	}

	@Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
	public void wilderWild$createNavigation(Level level, CallbackInfoReturnable<PathNavigation> info) {
		info.setReturnValue(new WardenNavigation(Warden.class.cast(this), level));
	}

	@Override
	public void travel(@NotNull Vec3 travelVector) {
		Warden warden = Warden.class.cast(this);
		if (this.isEffectiveAi() && this.wilderWild$isTouchingWaterOrLava() && EntityConfig.get().warden.wardenSwims) {
			this.moveRelative(this.getSpeed(), travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (!this.isDiggingOrEmerging() && !warden.hasPose(Pose.SNIFFING) && !warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
				if (this.wilderWild$isSubmergedInWaterOrLava()) {
					warden.setPose(Pose.SWIMMING);
				} else {
					warden.setPose(Pose.STANDING);
				}
			}

			this.wilderWild$newSwimming = this.getFluidHeight(FluidTags.WATER) >= this.getEyeHeight(this.getPose()) * 0.75 || this.getFluidHeight(FluidTags.LAVA) >= this.getEyeHeight(this.getPose()) * 0.75;
		} else {
			super.travel(travelVector);
			this.wilderWild$newSwimming = false;
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$wardenEntity(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
		Warden wardenEntity = Warden.class.cast(this);
		wardenEntity.setPathfindingMalus(PathType.WATER, 0F);
		wardenEntity.setPathfindingMalus(PathType.POWDER_SNOW, -1F);
		wardenEntity.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1F);
		this.moveControl = new WardenMoveControl(wardenEntity, 0.05F, 80F, 0.13F, 1F);
		this.lookControl = new WardenLookControl(wardenEntity, 10);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return EntityConfig.get().warden.wardenSwims;
	}

	@Override
	public boolean isPushedByFluid() {
		return !EntityConfig.get().warden.wardenSwims;
	}

	@Override
	@NotNull
	public SoundEvent getSwimSound() {
		return EntityConfig.get().warden.wardenSwims ? RegisterSounds.ENTITY_WARDEN_SWIM : super.getSwimSound();
	}

	@Override
	public void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
		if (EntityConfig.get().warden.wardenSwims && (this.getBrain().hasMemoryValue(MemoryModuleType.ROAR_TARGET) || this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET))) {
			Optional<LivingEntity> ATTACK_TARGET = this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
			Optional<LivingEntity> ROAR_TARGET = this.getBrain().getMemory(MemoryModuleType.ROAR_TARGET);
			LivingEntity target = ATTACK_TARGET.orElseGet(() -> ROAR_TARGET.orElse(null));

			if (target != null) {
				if ((!touchingWaterOrLava(target) || !submergedInWaterOrLava(this)) && target.getY() > this.getY()) {
					super.jumpInLiquid(fluid);
				}
			}
		} else {
			super.jumpInLiquid(fluid);
		}
	}

	@Unique
	@Override
	public float getSwimAmount(float tickDelta) {
		return Mth.lerp(tickDelta, this.wilderWild$lastLeaningPitch, this.wilderWild$leaningPitch);
	}

	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		if (EntityConfig.get().warden.wardenSwims) {
			Warden warden = Warden.class.cast(this);
			this.fluidHeight.clear();
			warden.updateInWaterStateAndDoWaterCurrentPushing();
			boolean bl = warden.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 0.1D);
			return this.wilderWild$isTouchingWaterOrLava() || bl;
		}
		return super.updateInWaterStateAndDoFluidPushing();
	}

	@Inject(method = "getDefaultDimensions", at = @At("RETURN"), cancellable = true)
	public void modifySwimmingDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
		if (!this.isDiggingOrEmerging() && this.isVisuallySwimming()) {
			info.setReturnValue(EntityDimensions.scalable(this.getType().getWidth(), 0.85F));
		}
	}

	@Unique
	@Override
	public boolean wilderWild$isTouchingWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isInWaterOrBubble() || warden.isInLava();
	}

	@Unique
	@Override
	public boolean wilderWild$isSubmergedInWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
	}
}
