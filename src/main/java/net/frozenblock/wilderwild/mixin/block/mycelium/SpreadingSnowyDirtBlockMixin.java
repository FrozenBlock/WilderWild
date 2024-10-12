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

package net.frozenblock.wilderwild.mixin.block.mycelium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SpreadingSnowyDirtBlock;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 0
		)
	)
	public BlockState wilderWild$captureIsMycelium(
		BlockState original,
		@Share("wilderWild$isMycelium") LocalRef<Boolean> isMycelium
	) {
		isMycelium.set(original.is(Blocks.MYCELIUM));
		return original;
	}

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 1
		)
	)
	public BlockState wilderWild$captureAboveState(
		BlockState original,
		@Share("wilderWild$aboveState") LocalRef<BlockState> aboveStateRef
	) {
		aboveStateRef.set(original);
		return original;
	}

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;above()Lnet/minecraft/core/BlockPos;",
			ordinal = 1
		)
	)
	public BlockPos wilderWild$captureAboveState(
		BlockPos original,
		@Share("wilderWild$abovePos") LocalRef<BlockPos> abovePosRef
	) {
		abovePosRef.set(original);
		return original;
	}

	@Inject(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z",
			ordinal = 1,
			shift = At.Shift.AFTER
		)
	)
	public void wilderWild$captureAboveState(
		BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo info,
		@Share("wilderWild$aboveState") LocalRef<BlockState> aboveStateRef,
		@Share("wilderWild$abovePos") LocalRef<BlockPos> abovePosRef,
		@Share("wilderWild$isMycelium") LocalRef<Boolean> isMycelium
	) {
		if (isMycelium.get()) {
			BlockState aboveState = aboveStateRef.get();
			BlockPos abovePos = abovePosRef.get();
			if (aboveState != null && abovePos != null && aboveState.is(WWBlockTags.MYCELIUM_GROWTH_REPLACEABLE)) {
				world.setBlockAndUpdate(abovePos, WWBlocks.MYCELIUM_GROWTH.defaultBlockState());
			}
		}
	}
}
