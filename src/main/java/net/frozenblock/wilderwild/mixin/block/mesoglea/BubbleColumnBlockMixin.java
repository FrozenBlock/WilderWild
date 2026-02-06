/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {

	@Inject(method = "getColumnState", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$getColumnState(Block bubbleColumn, BlockState belowState, BlockState occupyState, CallbackInfoReturnable<BlockState> info) {
		if (!WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) return;
		final Optional<Direction> dragDirection = MesogleaBlock.getDragDirection(belowState);
		dragDirection.ifPresent(direction -> info.setReturnValue(
			bubbleColumn.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, direction == Direction.DOWN)
		));
	}

	@ModifyExpressionValue(
		method = "updateColumn(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BubbleColumnBlock;canOccupy(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/block/state/BlockState;)Z",
			ordinal = 1
		)
	)
	private static boolean wilderWild$transferToMesogleaBubbleColumn(
		boolean original,
		@Local(argsOnly = true) LevelAccessor level,
		@Local(name = "pos") BlockPos.MutableBlockPos pos
	) {
		if (!original && WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) MesogleaBlock.updateColumn(level, pos, level.getBlockState(pos.immutable().below()));
		return original;
	}

}
