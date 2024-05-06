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

import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BigDripleafStemBlock.class)
public abstract class BigDripleafStemBlockMixin extends HorizontalDirectionalBlock {

	private BigDripleafStemBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	public void wilderWild$init(CallbackInfo info) {
		if (BlockConfig.get().isDripleafPoweringEnabled()) {
			BigDripleafStemBlock bigDripleafStemBlock = BigDripleafStemBlock.class.cast(this);
			bigDripleafStemBlock.registerDefaultState(bigDripleafStemBlock.defaultBlockState().setValue(BlockStateProperties.POWERED, false));
		}
	}

	@Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (BlockConfig.get().isDripleafPoweringEnabled()) {
			builder.add(BlockStateProperties.POWERED);
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$bigDripleafStemBlock(BlockBehaviour.Properties setting, CallbackInfo info) {
		if (BlockConfig.get().isDripleafPoweringEnabled()) {
			BigDripleafStemBlock stem = BigDripleafStemBlock.class.cast(this);
			stem.registerDefaultState(stem.defaultBlockState().setValue(BlockStateProperties.POWERED, false));
		}
	}

}
