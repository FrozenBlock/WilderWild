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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

	@WrapOperation(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getSoundType()Lnet/minecraft/world/level/block/SoundType;"
		)
	)
	public SoundType wilderWild$placeSound(BlockState instance, Operation<SoundType> original) {
		return (BlockConfig.canSnowlog() && SnowloggingUtils.isSnowlogged(instance)) ?
			original.call(SnowloggingUtils.getSnowEquivalent(instance)) : original.call(instance);
	}

	@Inject(method = "getPlaceSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getPlaceSound(BlockState state, CallbackInfoReturnable<SoundEvent> info) {
		if (!BlockConfig.canSnowlog()) return;
		if (state.hasProperty(SnowloggingUtils.SNOW_LAYERS) && state.getValue(SnowloggingUtils.SNOW_LAYERS) > 0) {
			info.setReturnValue(SnowloggingUtils.getSnowEquivalent(state).getSoundType().getPlaceSound());
		}
	}

	@WrapOperation(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$placeIsSnowlogged(
		Level instance, BlockPos pos, Operation<BlockState> original,
		@Local ItemStack itemStack, @Share("wilderWild$isAddingSnow") LocalRef<Boolean> isAddingSnow
	) {
		BlockState state = original.call(instance, pos);
		isAddingSnow.set(SnowloggingUtils.isSnowlogged(state) && SnowloggingUtils.isItemSnow(itemStack));
		return state;
	}

	@WrapOperation(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/BlockItem;updateCustomBlockEntityTag(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	public boolean wilderWild$placeUpdateCustomBlockEntityTag(
		BlockItem instance, BlockPos pos, Level world, Player player, ItemStack stack, BlockState state,
		Operation<Boolean> original, @Share("wilderWild$isAddingSnow") LocalRef<Boolean> isAddingSnow
	) {
		if (isAddingSnow.get()) return false;
		return original.call(instance, pos, world, player, stack, state);
	}

	@WrapOperation(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/BlockItem;updateBlockEntityComponents(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"
		)
	)
	public void wilderWild$placeUpdateBlockEntityComponents(
		Level world, BlockPos pos, ItemStack stack, Operation<Void> original,
		@Share("wilderWild$isAddingSnow") LocalRef<Boolean> isAddingSnow
	) {
		if (isAddingSnow.get()) return;
		original.call(world, pos, stack);
	}
}
