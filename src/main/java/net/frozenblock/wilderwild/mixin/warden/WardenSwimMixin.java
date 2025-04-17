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

package net.frozenblock.wilderwild.mixin.warden;

import java.util.Optional;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.warden.WardenLookControl;
import net.frozenblock.wilderwild.entity.ai.warden.WardenMoveControl;
import net.frozenblock.wilderwild.entity.ai.warden.WardenNavigation;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenInterface;
import net.frozenblock.wilderwild.registry.WWSounds;
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
	private boolean wilderWild$newSwimming;

	@Unique
	private float wilderWild$wadeAmount;
	@Unique private float wilderWild$previousWadeAmount;

	private WardenSwimMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Unique
	private static boolean wilderWild$touchingLiquidOrSwimming(@NotNull Entity entity) {
		return entity.isInWater() || entity.isInLava() || entity.isVisuallySwimming();
	}

	@Unique
	private static boolean wilderWild$submergedOrSwimming(@NotNull Entity entity) {
		return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
	}

	@Shadow
	public abstract boolean isDiggingOrEmerging();

	@Shadow
	public abstract Brain<Warden> getBrain();

	@Inject(method = "tick", at = @At("TAIL"))
	public void wilderWild$tickWading(CallbackInfo info) {
		Warden warden = Warden.class.cast(this);
		this.wilderWild$previousWadeAmount = this.wilderWild$wadeAmount;
		this.wilderWild$wadeAmount += ((wilderWild$touchingLiquidOrSwimming(warden) ? 1F : 0F) - this.wilderWild$wadeAmount) * 0.175F;
	}

	@Override
	public float wilderWild$getWadingProgress(float tickDelta) {
		return Mth.lerp(tickDelta, this.wilderWild$previousWadeAmount, this.wilderWild$wadeAmount);
	}

	@Override
	public boolean isVisuallySwimming() {
		return this.wilderWild$newSwimming || super.isVisuallySwimming();
	}

	@Inject(method = "createNavigation", at = @At("RETURN"), cancellable = true)
	public void wilderWild$createNavigation(Level level, CallbackInfoReturnable<PathNavigation> info) {
		info.setReturnValue(new WardenNavigation(Warden.class.cast(this), level));
	}

	@Override
	public void travel(@NotNull Vec3 travelVector) {
		Warden warden = Warden.class.cast(this);
		if (this.isEffectiveAi() && this.wilderWild$isTouchingWaterOrLava() && WWEntityConfig.WARDEN_SWIMS) {
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

			this.wilderWild$newSwimming = this.getFluidHeight(FluidTags.WATER) >= this.getEyeHeight(this.getPose()) * 0.75F || this.getFluidHeight(FluidTags.LAVA) >= this.getEyeHeight(this.getPose()) * 0.75F;
		} else {
			super.travel(travelVector);
			this.wilderWild$newSwimming = false;
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$wardenEntity(EntityType<? extends Monster> entityType, Level level, CallbackInfo info) {
		Warden wardenEntity = Warden.class.cast(this);
		wardenEntity.setPathfindingMalus(PathType.WATER, 0F);
		wardenEntity.setPathfindingMalus(PathType.POWDER_SNOW, -1F);
		wardenEntity.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1F);
		this.moveControl = new WardenMoveControl(wardenEntity, 0.05F, 80F, 0.13F, 1F);
		this.lookControl = new WardenLookControl(wardenEntity, 10);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return WWEntityConfig.WARDEN_SWIMS;
	}

	@Override
	public boolean isPushedByFluid() {
		return !WWEntityConfig.WARDEN_SWIMS;
	}

	@Override
	@NotNull
	public SoundEvent getSwimSound() {
		return WWEntityConfig.WARDEN_SWIMS ? WWSounds.ENTITY_WARDEN_SWIM : super.getSwimSound();
	}

	@Override
	public void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
		if (WWEntityConfig.WARDEN_SWIMS && (this.getBrain().hasMemoryValue(MemoryModuleType.ROAR_TARGET) || this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET))) {
			Optional<LivingEntity> ATTACK_TARGET = this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
			Optional<LivingEntity> ROAR_TARGET = this.getBrain().getMemory(MemoryModuleType.ROAR_TARGET);
			LivingEntity target = ATTACK_TARGET.orElseGet(() -> ROAR_TARGET.orElse(null));

			if (target != null) {
				if ((!wilderWild$touchingLiquidOrSwimming(target) || !wilderWild$submergedOrSwimming(this)) && target.getY() > this.getY()) {
					super.jumpInLiquid(fluid);
				}
			}
		} else {
			super.jumpInLiquid(fluid);
		}
	}

	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		if (WWEntityConfig.WARDEN_SWIMS) {
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

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putBoolean("newSwimming", this.wilderWild$newSwimming);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		this.wilderWild$newSwimming = nbt.getBooleanOr("newSwimming", false);
	}

	@Unique
	@Override
	public boolean wilderWild$isTouchingWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isInWater() || warden.isInLava();
	}

	@Unique
	@Override
	public boolean wilderWild$isSubmergedInWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
	}
}
