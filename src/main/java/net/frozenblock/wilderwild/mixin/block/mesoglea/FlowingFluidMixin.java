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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowingFluid.class)
public class FlowingFluidMixin {

	@Inject(
		method = "spread",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void wilderWild$stopMesogleaWaterSpread(
		Level level, BlockPos blockPos, FluidState fluidState, CallbackInfo info,
		@Local BlockState state
	) {
		if (state.getBlock() instanceof MesogleaBlock) {
			info.cancel();
		}
	}

}
