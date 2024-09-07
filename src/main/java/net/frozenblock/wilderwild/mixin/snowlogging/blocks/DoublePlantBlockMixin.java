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

package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
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
		if (SnowloggingUtils.isItemSnow(stack) && BlockConfig.canSnowlog()) {
			info.cancel();
		}
	}

	@WrapOperation(method = "playerWillDestroy",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/DoublePlantBlock;preventDropFromBottomPart(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V"
		)
	)
	public void wilderWild$playerWillDestroyCreative(Level level, BlockPos pos, BlockState state, Player player, Operation<Void> original) {
		if (!SnowloggingUtils.shouldHitSnow(state, pos, level, player)) {
			original.call(level, pos, state, player);
		}
	}

	/**
	 * If the snow layer is getting broken, run the super method instead.
	 */
	@WrapOperation(method = "playerWillDestroy",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/DoublePlantBlock;dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V"
		)
	)
	public void wilderWild$playerWillDestroySurvival(
		BlockState state, Level level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack itemStack, Operation<Void> original
	) {
		Player player = (Player) entity;
		if (SnowloggingUtils.shouldHitSnow(state, pos, level, player)) {
			super.playerWillDestroy(level, pos, SnowloggingUtils.getSnowEquivalent(state), player);
			return;
		}
		original.call(state, level, pos, blockEntity, entity, itemStack);
	}

	/**
	 * The original method replaces the block with air. The method now only does this if the broken block isn't snow.
	 *
	 * @param blockState air
	 * @param paramState the original broken block. Is either the plant or snow layers.
	 */
	@WrapOperation(method = "playerDestroy",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BushBlock;playerDestroy(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/item/ItemStack;)V")
	)
	private void wilderWild$playerDestroy(
		DoublePlantBlock instance, Level level, Player player, BlockPos pos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack, Operation<Void> original,
		@Local(argsOnly = true) BlockState paramState
	) {
		if (paramState.is(Blocks.SNOW)) {
			original.call(instance, level, player, pos, paramState, blockEntity, itemStack);
		} else {
			original.call(instance, level, player, pos, blockState, blockEntity, itemStack);
		}
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		DoublePlantBlock thisPlant = (DoublePlantBlock) (Object) this;
		if (thisPlant instanceof TallSeagrassBlock) {
			return;
		}
		if (BlockConfig.get().snowlogging.snowlogging) builder.add(SnowloggingUtils.SNOW_LAYERS);
	}
}
