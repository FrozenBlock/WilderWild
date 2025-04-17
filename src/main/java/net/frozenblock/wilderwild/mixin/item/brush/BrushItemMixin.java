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

package net.frozenblock.wilderwild.mixin.item.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class BrushItemMixin {

	@ModifyVariable(method = "onUseTick", at = @At("STORE"), ordinal = 0)
	public BlockHitResult wilderWild$captureBlockHitResult(
		BlockHitResult blockHitResult,
		@Share("wilderWild$hitResultRef") LocalRef<BlockHitResult> hitResultRef
	) {
		hitResultRef.set(blockHitResult);
		return blockHitResult;
	}

	@ModifyExpressionValue(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 0
		)
	)
	public BlockState wilderWild$captureBlockState(
		BlockState blockState,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		blockStateRef.set(blockState);
		return blockState;
	}

	@WrapOperation(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;)V"
		)
	)
	public void wilderWild$playBrushSound(
		Level instance, Player player, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, Operation<Void> original,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		if (blockStateRef.get().getBlock() instanceof ScorchedBlock scorchedBlock && scorchedBlock.canBrush) {
			soundEvent = scorchedBlock.brushSound;
		}
		original.call(instance, player, blockPos, soundEvent, soundSource);
	}

	@Inject(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void wilderWild$brushScorchedBlocks(
		Level level, LivingEntity livingEntity2, ItemStack itemStack, int i, CallbackInfo info,
		@Share("wilderWild$hitResultRef") LocalRef<BlockHitResult> hitResultRef,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		if (this.wilderWild$brushScorchedBlocks(level, livingEntity2, itemStack, hitResultRef.get(), blockStateRef.get())) {
			info.cancel();
		}
	}

	@Unique
	private boolean wilderWild$brushScorchedBlocks(
		@NotNull Level level, LivingEntity livingEntity, @NotNull ItemStack stack, BlockHitResult hitResult, BlockState blockState
	) {
		if (!level.isClientSide() && livingEntity instanceof Player player) {
			BlockPos blockPos = hitResult.getBlockPos();
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			if (blockEntity instanceof ScorchedBlockEntity scorchedBlockEntity && blockState.getBlock() instanceof ScorchedBlock scorchedBlock && scorchedBlock.canBrush) {
				boolean shouldDegrade = scorchedBlockEntity.brush(level.getGameTime());
				if (shouldDegrade) {
					EquipmentSlot equipmentSlot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
					stack.hurtAndBreak(1, livingEntity, equipmentSlot);
				}
				return true;
			}
		}
		return false;
	}

}
