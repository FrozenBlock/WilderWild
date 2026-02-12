/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin extends VehicleEntity implements BoatBoostInterface {

	public AbstractBoatMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		if (this.level().isClientSide()) return;
		if (!this.hasAttached(WWAttachmentTypes.BOAT_BOOST_TICKS)) return;

		final int newBoostTicks = Math.max(this.getAttachedOrElse(WWAttachmentTypes.BOAT_BOOST_TICKS, 0) - 1, 0);
		this.setAttached(WWAttachmentTypes.BOAT_BOOST_TICKS, newBoostTicks);
		if (newBoostTicks <= 0) this.removeAttached(WWAttachmentTypes.BOAT_BOOST_TICKS);
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
		this.setAttached(WWAttachmentTypes.BOAT_BOOST_TICKS, Math.max(this.getAttachedOrElse(WWAttachmentTypes.BOAT_BOOST_TICKS, 0), ticks));
	}

	@Unique
	@Override
	public boolean wilderWild$isBoatBoosted() {
		return this.getAttachedOrElse(WWAttachmentTypes.BOAT_BOOST_TICKS, 0) > 0;
	}
}
