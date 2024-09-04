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

package net.frozenblock.wilderwild.mixin.block.dripleaf;

import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public final class BlockMixin {

	@Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
	public void wilderWild$getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info) {
		if (BlockBehaviour.class.cast(this) instanceof BigDripleafStemBlock bigDripleafStemBlock && WWBlockConfig.get().dripleafPowering) {
			info.setReturnValue(bigDripleafStemBlock.defaultBlockState().setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())));
		} else if (BlockBehaviour.class.cast(this) instanceof BigDripleafBlock bigDripleafBlock) {
			info.setReturnValue(bigDripleafBlock.defaultBlockState().setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())));
		}
	}

}
