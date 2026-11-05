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

package net.frozenblock.wilderwild.mixin.warden;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.material.FluidIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityFluidInteraction.class)
public class EntityFluidInteractionMixin {

	@ModifyExpressionValue(
		method = "applyCurrentsTo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/EntityFluidInteraction$Tracker;getCurrentAccumulator()Lnet/minecraft/world/entity/EntityFluidInteraction$CurrentAccumulator;"
		)
	)
	public EntityFluidInteraction.CurrentAccumulator wilderWild$stopWaterFromPushingWardens(
		EntityFluidInteraction.CurrentAccumulator original,
		@Local(argsOnly = true) Entity entity,
		@Local(name = "fluidTracker") EntityFluidInteraction.Tracker fluidTracker
	) {
		if (entity.is(EntityTypes.WARDEN) && WWEntityConfig.WARDEN_SWIMS.get() && fluidTracker.fluidType.is(FluidIds.WATER)) return null;
		return original;
	}
}
