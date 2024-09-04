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

package net.frozenblock.wilderwild.mixin.block.leaves;

import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

	@Shadow
	protected abstract BlockState asState();

	@Shadow
	public abstract Block getBlock();

	@Inject(method = "randomTick", at = @At("HEAD"))
	public void wilderWild$randomTick(ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (this.getBlock() instanceof LeavesBlock) {
			FallingLeafUtil.onRandomTick(this.asState(), level, pos, random);
		}
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (this.getBlock() instanceof LeavesBlock) {
			FallingLeafUtil.createLitterOnScheduledTick(this.asState(), level, pos, random);
		}
	}
}
