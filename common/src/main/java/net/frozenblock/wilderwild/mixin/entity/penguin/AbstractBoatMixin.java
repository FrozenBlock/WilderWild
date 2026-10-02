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

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinBoostBoat;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin extends VehicleEntity {

	public AbstractBoatMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		if (this.level().isClientSide()) return;
		if (!WWAttachmentTypes.BOAT_BOOST_TICKS.has(this)) return;

		final int newBoostTicks = Math.max(WWAttachmentTypes.BOAT_BOOST_TICKS.getAttachedOrElse(this, 0) - 1, 0);
		WWAttachmentTypes.BOAT_BOOST_TICKS.set(this, newBoostTicks);
		if (newBoostTicks <= 0) WWAttachmentTypes.BOAT_BOOST_TICKS.remove(this);
	}

	@Inject(
		method = "controlBoat",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;addDeltaMovement(DDD)V"
		)
	)
	private void wilderWild$applySpeedBoost(
		CallbackInfo info,
		@Local(name = "acceleration")LocalFloatRef acceleration
	) {
		if (PenguinBoostBoat.isBoosted(AbstractBoat.class.cast(this))) acceleration.set(acceleration.get() * Penguin.BOAT_BOOST_SPEED);
	}
}
