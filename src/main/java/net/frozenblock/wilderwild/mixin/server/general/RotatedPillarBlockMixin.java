/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.general;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RotatedPillarBlock.class, priority = 990)
public class RotatedPillarBlockMixin {

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void wilderWild$addTermiteEdibleState(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		builder.add(RegisterProperties.TERMITE_EDIBLE);
    }

	@ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
	private BlockState wilderWild$getStateForPlacement(BlockState original) {
		if (original != null && original.hasProperty(RegisterProperties.TERMITE_EDIBLE)) {
			return original.setValue(RegisterProperties.TERMITE_EDIBLE, false);
		}
		return original;
	}

}
