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

package net.frozenblock.wilderwild.mixin.item.brush;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.minecraft.core.BlockPos;
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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BrushItem.class)
public class BrushItemMixin {

	@ModifyArgs(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;)V"
		)
	)
	public void wilderWild$playBrushSound(
		Args args,
		@Local(ordinal = 0) BlockState blockState
	) {
		if (blockState.getBlock() instanceof ScorchedBlock scorchedBlock && scorchedBlock.canBrush) {
			args.set(2, scorchedBlock.brushSound);
		}
	}

	@Inject(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;isClientSide()Z",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void wilderWild$brushScorchedBlocks(
		Level level, LivingEntity livingEntity2, ItemStack itemStack, int i, CallbackInfo info,
		@Local(ordinal = 0) BlockHitResult blockHitResult,
		@Local(ordinal = 0) BlockState blockState
	) {
		if (this.wilderWild$brushScorchedBlocks(level, livingEntity2, itemStack, blockHitResult, blockState)) {
			info.cancel();
		}
	}

	@Unique
	private boolean wilderWild$brushScorchedBlocks(
		@NotNull Level level,
		LivingEntity livingEntity,
		@NotNull ItemStack stack,
		BlockHitResult blockHitResult,
		@NotNull BlockState blockState
	) {
		if (!level.isClientSide() && blockHitResult != null && livingEntity instanceof Player player) {
			BlockPos blockPos = blockHitResult.getBlockPos();
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
