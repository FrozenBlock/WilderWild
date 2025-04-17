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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.block.chest;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.block.impl.ChestUtil;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {

	@Inject(
		method = "useWithoutItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;",
			shift = At.Shift.BEFORE
		)
	)
	public void wilderWild$useBeforeOpenMenu(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest && sourceChest instanceof ChestBlockEntityInterface chestBlockEntityInterface) {
			if (
				sourceChest.lootTable != null
				&& state.getFluidState().is(Fluids.WATER)
				&& sourceChest.lootTable.location().getPath().toLowerCase().contains("shipwreck")
				&& level.random.nextInt(0, 3) == 1
			) {
				if (WWEntityConfig.get().jellyfish.spawnJellyfish) {
					Jellyfish.spawnFromChest(level, state, pos, true);
				}
			}
			chestBlockEntityInterface.wilderWild$bubble(level, pos, state);
		}
	}

	@ModifyReturnValue(method = "updateShape", at = @At(value = "RETURN"))
	public BlockState wilderWild$updateShape(
		BlockState original,
		BlockState oldState,
		Direction direction,
		BlockState neighborState,
		LevelAccessor level,
		BlockPos currentPos,
		BlockPos neighborPos
	) {
		ChestUtil.updateBubbles(oldState, oldState, level, currentPos);
		return original;
	}

	@ModifyReturnValue(method = "getStateForPlacement", at = @At(value = "RETURN"))
	public BlockState wilderWild$getStateForPlacement(
		BlockState original,
		BlockPlaceContext blockPlaceContext
	) {
		Level level = blockPlaceContext.getLevel();
		BlockPos pos = blockPlaceContext.getClickedPos();
		ChestUtil.getCoupledChestBlockEntity(level, pos, original).ifPresent(coupledChest -> {
			if (
				coupledChest instanceof ChestBlockEntityInterface coupledStoneChestInterface
					&& level.getBlockEntity(pos) instanceof ChestBlockEntity chest
					&& chest instanceof ChestBlockEntityInterface chestInterface
			) {
				chestInterface.wilderWild$setCanBubble(coupledStoneChestInterface.wilderWild$getCanBubble());
				chestInterface.wilderWild$syncBubble(chest, coupledChest);
			}
		});
		return original;
	}

	@Inject(
		method = "onRemove",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/Containers;dropContentsOnDestroy(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"
		)
	)
	public void wilderWild$onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo info) {
		if (!level.isClientSide && !state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof ChestBlockEntityInterface chestBlockEntityInterface) {
			chestBlockEntityInterface.wilderWild$bubbleBurst(state);
		}
	}

}
