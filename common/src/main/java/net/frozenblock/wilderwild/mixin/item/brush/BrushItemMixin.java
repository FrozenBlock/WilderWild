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

package net.frozenblock.wilderwild.mixin.item.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrushItem.class)
public class BrushItemMixin {

	@ModifyExpressionValue(
		method = "onUseTick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/sounds/SoundEvents;BRUSH_GENERIC:Lnet/minecraft/sounds/SoundEvent;",
			opcode = Opcodes.GETSTATIC
		)
	)
	public SoundEvent wilderWild$playBrushSound(
		SoundEvent original,
		@Local(name = "state") BlockState state
	) {
		if (state.getBlock() instanceof ScorchedBlock scorchedBlock && scorchedBlock.canBrush) return scorchedBlock.brushSound;
		return original;
	}

	@ModifyExpressionValue(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"
		)
	)
	public BlockEntity wilderWild$brushScorchedBlocks(
		BlockEntity blockEntity,
		Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining,
		@Local(name = "state") BlockState state
	) {
		this.wilderWild$brushScorchedBlocks(level, livingEntity, itemStack, blockEntity, state);
		return blockEntity;
	}

	@Unique
	private void wilderWild$brushScorchedBlocks(Level level, LivingEntity entity, ItemStack stack, BlockEntity blockEntity, BlockState state) {
		if (level.isClientSide() || !(entity instanceof Player player)) return;
		if (!(blockEntity instanceof ScorchedBlockEntity scorchedBlockEntity)) return;
		if (!(state.getBlock() instanceof ScorchedBlock scorchedBlock) || !scorchedBlock.canBrush) return;
		if (!scorchedBlockEntity.brush(level.getGameTime())) return;

		final EquipmentSlot slot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
		stack.hurtAndBreak(1, entity, slot);
	}

}
