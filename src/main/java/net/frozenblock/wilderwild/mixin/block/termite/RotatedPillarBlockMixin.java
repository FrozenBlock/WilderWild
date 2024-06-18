/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.termite;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RotatedPillarBlock.class, priority = 990)
public class RotatedPillarBlockMixin {

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	private void addTermiteEdibleState(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		builder.add(RegisterProperties.TERMITE_EDIBLE);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$appendFalseTermiteEdibleToState(BlockBehaviour.Properties properties, CallbackInfo info) {
		RotatedPillarBlock rotatedPillarBlock = RotatedPillarBlock.class.cast(this);
		rotatedPillarBlock.registerDefaultState(rotatedPillarBlock.defaultBlockState().setValue(RegisterProperties.TERMITE_EDIBLE, false));
	}
}
