/*
 * Copyright 2025 FrozenBlock
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
 * You should have received a copy of the FrozenBlock Modding oasis License
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin extends Block {

	public BubbleColumnBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "getColumnState", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$getColumnState(BlockState blockState, CallbackInfoReturnable<BlockState> info) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			Optional<Direction> dragDirection = MesogleaBlock.getDragDirection(blockState);
			dragDirection.ifPresent(direction -> info.setReturnValue(
				Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, direction == Direction.DOWN)
			));
		}
	}

	@ModifyExpressionValue(
		method = "updateColumn(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BubbleColumnBlock;canExistIn(Lnet/minecraft/world/level/block/state/BlockState;)Z",
			ordinal = 1
		)
	)
	private static boolean wilderWild$updateColumnBooleanTweak(boolean original) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			return true;
		}
		return original;
	}

	@Inject(
		method = "updateColumn(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
			ordinal = 1,
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private static void wilderWild$updateColumnIfStatement(
		LevelAccessor level,
		BlockPos pos,
		BlockState fluid,
		BlockState state,
		CallbackInfo info,
		@Local(ordinal = 0) BlockPos.MutableBlockPos mutableBlockPos
	) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			BlockState mutableState = level.getBlockState(mutableBlockPos);
			if (!canExistIn(mutableState)) {
				MesogleaBlock.updateColumn(level, mutableBlockPos, state);
				info.cancel();
			}
		}
	}

	@Shadow
	private static boolean canExistIn(BlockState blockState) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BubbleColumnBlockMixin.");
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void wilderWild$tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			MesogleaBlock.updateColumn(level, pos.above(), state);
		}
	}

	@Inject(
		method = "updateShape",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
			shift = At.Shift.BEFORE
		)
	)
	public void wilderWild$updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			if (MesogleaBlock.hasBubbleColumn(neighborState)) {
				level.scheduleTick(pos, BubbleColumnBlock.class.cast(this), 5);
			}
		}
	}

}
