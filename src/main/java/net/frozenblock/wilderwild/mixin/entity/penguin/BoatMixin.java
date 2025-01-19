/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.penguin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.entity.impl.BoatBoostInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public abstract class BoatMixin extends VehicleEntity implements BoatBoostInterface {
	@Unique
	private static final EntityDataAccessor<Integer> WILDER_WILD$BOOST_TICKS = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

	public BoatMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	public void wilderWild$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo info) {
		builder.define(WILDER_WILD$BOOST_TICKS, 0);
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		int boostTicks = this.wilderWild$getBoatBoostTicks();
		if (boostTicks > 0) nbt.putInt("WilderWildBoatBoostTicks", boostTicks);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (nbt.contains("WilderWildBoatBoostTicks")) this.wilderWild$setBoatBoostTicks(nbt.getInt("WilderWildBoatBoostTicks"));
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		int boostTicks = this.wilderWild$getBoatBoostTicks();
		this.wilderWild$setBoatBoostTicks(Math.max(boostTicks - 1, 0));
	}

	@WrapOperation(
		method = "controlBoat",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	public Vec3 wilderWild$speedBoost(Vec3 instance, double x, double y, double z, Operation<Vec3> original) {
		double multiplier = (this.wilderWild$getBoatBoostTicks() > 0) ? 2D : 1D;
		return original.call(instance, x * multiplier, y * multiplier, z * multiplier);
	}

	@Unique
	@Override
	public void wilderWild$boostBoatForTicks(int ticks) {
		this.wilderWild$setBoatBoostTicks(Math.max(this.wilderWild$getBoatBoostTicks(), ticks));
	}

	@Override
	public void wilderWild$setBoatBoostTicks(int ticks) {
		this.entityData.set(WILDER_WILD$BOOST_TICKS, ticks);
	}

	@Unique
	@Override
	public int wilderWild$getBoatBoostTicks() {
		return this.entityData.get(WILDER_WILD$BOOST_TICKS);
	}
}
