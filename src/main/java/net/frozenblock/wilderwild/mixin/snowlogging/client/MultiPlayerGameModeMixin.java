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

package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

	@Unique
	private boolean wilderWild$isBreakingOriginal = true;

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyExpressionValue(
		method = "destroyBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;playerWillDestroy(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$destroyBlockA(
		BlockState original,
		@Share("wilderWild$destroyedState") LocalRef<BlockState> destroyedState
	) {
		destroyedState.set(original);
		return original;
	}

	@WrapOperation(
		method = "destroyBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	public boolean wilderWild$destroyBlockB(
		Level level, BlockPos pos, BlockState newState, int flags, Operation<Boolean> original,
		@Share("wilderWild$destroyedState") LocalRef<BlockState> destroyedState
	) {
		if (SnowloggingUtils.isSnowlogged(destroyedState.get())) {
			assert minecraft.player != null;
			level.setBlock(pos, SnowloggingUtils.getUnhitState(destroyedState.get(), pos, level, minecraft.player), flags);
			return true;
		}
		return original.call(level, pos, newState, flags);
	}

	@ModifyReturnValue(method = "sameDestroyTarget", at = @At("RETURN"))
	public boolean wilderWild$sameDestoryTarget(boolean original, BlockPos pos) {
		// Done this way to avoid checking the blockstate when unnecessary.
		if (!original) return false;
		Level level = minecraft.level;
		BlockState state = level.getBlockState(pos);
		return wilderWild$sameDestroyPart(!SnowloggingUtils.shouldHitSnow(state, pos, level, minecraft.player));
	}

	@Unique
	private boolean wilderWild$sameDestroyPart(boolean isBreakingOriginal) {
		return isBreakingOriginal == wilderWild$isBreakingOriginal;
	}

	@Inject(method = "method_41930", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyBlockPos:Lnet/minecraft/core/BlockPos;"))
	public void wilderWild$startDestroyBlock(BlockState state, BlockPos pos, Direction direction, int actionIndex, CallbackInfoReturnable<Packet> cir) {
		wilderWild$isBreakingOriginal = !SnowloggingUtils.shouldHitSnow(state, pos, minecraft.level, minecraft.player);
	}
}
