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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DoublePlantBlock.class)
public abstract class DoublePlantBlockMixin extends BushBlock {

	public DoublePlantBlockMixin(Properties properties) {
		super(properties);
	}

	@ModifyExpressionValue(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BushBlock;getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$getStateForPlacement(BlockState original, BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(original, context);
	}

	@Inject(method = "setPlacedBy", at = @At("HEAD"), cancellable = true)
	public void wilderWild$setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo info) {
		if (SnowloggingUtils.isItemSnow(stack) && WWBlockConfig.canSnowlog()) {
			info.cancel();
		}
	}

	@ModifyExpressionValue(
		method = "playerWillDestroy",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/Level;isClientSide:Z"
		)
	)
	public boolean wilderWild$playerWillDestroy(boolean original, Level level, BlockPos pos, BlockState state, Player player) {
		boolean snowlogged = SnowloggingUtils.isSnowlogged(state);
		if (!original && !player.isCreative() && !snowlogged) {
			if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
				BlockPos belowPos = pos.below();
				BlockState belowState = level.getBlockState(belowPos);
				if (SnowloggingUtils.isSnowlogged(belowState)) {
					Block.dropResources(state.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), level, pos, null, player, player.getMainHandItem());
				}
			}
		}
		return original || snowlogged;
	}

	@ModifyExpressionValue(
		method = "preventCreativeDropFromBottomPart",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	private static BlockState wilderWild$preventDropFromBottomPartA(
		BlockState original,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockState
	) {
		blockState.set(original);
		return original;
	}

	@WrapOperation(
		method = "preventCreativeDropFromBottomPart",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	private static boolean wilderWild$preventDropFromBottomPartB(
		Level instance, BlockPos setPos, BlockState setState, int flags, Operation<Boolean> original,
		Level level, BlockPos paramPos, BlockState state, Player player,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockState
	) {
		if (SnowloggingUtils.isSnowlogged(blockState.get()) && setState.isAir() && setState.getFluidState().isEmpty()) {
			setState = SnowloggingUtils.getSnowEquivalent(blockState.get());
		}
		return original.call(instance, setPos, setState, flags);
	}


	@Inject(method = "playerDestroy", at = @At("HEAD"), cancellable = true)
	public void wilderWild$playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo info) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			info.cancel();
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (player.hasCorrectToolForDrops(snowEquivalent)) {
				super.playerDestroy(level, player, pos, snowEquivalent, blockEntity, stack);
			}
		}
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (!WWBlockConfig.get().snowlogging.snowlogging) return;
		builder.add(SnowloggingUtils.SNOW_LAYERS);
	}

}
