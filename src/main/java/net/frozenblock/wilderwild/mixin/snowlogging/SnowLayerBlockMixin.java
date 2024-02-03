/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {

	@Inject(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void wilderWild$getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info, BlockState blockState) {
		if (blockState.hasProperty(RegisterProperties.SNOWLOGGED)) {
			if (!blockState.getValue(RegisterProperties.SNOWLOGGED)) {
				info.setReturnValue(blockState.setValue(RegisterProperties.SNOWLOGGED, true));
			} else {
				info.setReturnValue(SnowLayerBlock.class.cast(this).defaultBlockState().setValue(BlockStateProperties.LAYERS, 2));
			}
		}
	}

}
