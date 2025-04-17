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

package net.frozenblock.wilderwild.mixin.entity.penguin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.entity.impl.BoatBoostInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin extends VehicleEntity implements BoatBoostInterface {
	@Unique
	private int wilderWild$boatBoostTicks;
	@Unique
	private static final EntityDataAccessor<Boolean> WILDER_WILD$BOOSTED = SynchedEntityData.defineId(AbstractBoat.class, EntityDataSerializers.BOOLEAN);

	public AbstractBoatMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	public void wilderWild$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo info) {
		builder.define(WILDER_WILD$BOOSTED, false);
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (this.wilderWild$boatBoostTicks > 0) nbt.putInt("WilderWildBoatBoostTicks", this.wilderWild$boatBoostTicks);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (nbt.contains("WilderWildBoatBoostTicks")) this.wilderWild$boatBoostTicks = nbt.getInt("WilderWildBoatBoostTicks");
		this.wilderWild$setBoatBoosted(this.wilderWild$boatBoostTicks > 0);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		if (!this.level().isClientSide) {
			int boostTicks = this.wilderWild$getBoatBoostTicks();
			this.wilderWild$setBoatBoostTicks(Math.max(boostTicks - 1, 0));
			this.wilderWild$setBoatBoosted(this.wilderWild$boatBoostTicks > 0);
		}
	}

	@WrapOperation(
		method = "controlBoat",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	public Vec3 wilderWild$speedBoost(Vec3 instance, double x, double y, double z, Operation<Vec3> original) {
		double multiplier = this.wilderWild$isBoatBoosted() ? Penguin.BOAT_BOOST_SPEED : 1D;
		return original.call(instance, x * multiplier, y * multiplier, z * multiplier);
	}

	@Unique
	@Override
	public void wilderWild$boostBoatForTicks(int ticks) {
		this.wilderWild$setBoatBoostTicks(Math.max(this.wilderWild$getBoatBoostTicks(), ticks));
	}

	@Override
	public void wilderWild$setBoatBoostTicks(int ticks) {
		this.wilderWild$boatBoostTicks = ticks;
		this.entityData.set(WILDER_WILD$BOOSTED, ticks > 0);
	}

	@Unique
	@Override
	public int wilderWild$getBoatBoostTicks() {
		return this.wilderWild$boatBoostTicks;
	}

	@Unique
	@Override
	public void wilderWild$setBoatBoosted(boolean boosted) {
		this.entityData.set(WILDER_WILD$BOOSTED, boosted);
	}

	@Unique
	@Override
	public boolean wilderWild$isBoatBoosted() {
		return this.entityData.get(WILDER_WILD$BOOSTED);
	}
}
