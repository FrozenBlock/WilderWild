/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClientOnly
@Mixin(FluidState.class)
public class FluidStateMixin {

	@Inject(method = "shouldRenderBackwardUpFace", at = @At("HEAD"))
	public void wilderWild$captureMesoglea(
		BlockGetter level, BlockPos above, CallbackInfoReturnable<Boolean> info,
		@Share("wilderWild$mesogleaBlock") LocalRef<Block> mesogleaBlock
	) {
		if (WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID.get() && level.getBlockState(above.below()).getBlock() instanceof MesogleaBlock mesoglea) mesogleaBlock.set(mesoglea);
	}

	@ModifyExpressionValue(
		method = "shouldRenderBackwardUpFace",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/material/FluidState;is(Ljava/lang/Object;)Z"
		)
	)
	public boolean wilderWild$checkIfDifferingMesogleaType(
		boolean original,
		BlockGetter level,
		@Local(name = "offset") BlockPos offset,
		@Share("wilderWild$mesogleaBlock") LocalRef<Block> mesogleaBlock
	) {
		if (!original || mesogleaBlock == null || mesogleaBlock.get() == null) return original;

		final BlockState offsetState = level.getBlockState(offset);
		if (offsetState.getBlock() instanceof MesogleaBlock mesoglea) return mesoglea == mesogleaBlock.get();

		return false;
	}
}
