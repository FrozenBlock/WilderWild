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

package net.frozenblock.wilderwild.mixin.snowlogging.snowlogged;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.snowlogging.SnowloggingUtil;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin {

	@Inject(method = "setPlacedBy", at = @At("HEAD"), cancellable = true)
	public void wilderWild$setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity by, ItemStack itemStack, CallbackInfo info) {
		if (SnowloggingUtil.isItemSnow(itemStack) && WWBlockConfig.canSnowlog()) info.cancel();
	}

	@WrapOperation(
		method = "preventDropFromBottomPart",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	private static boolean wilderWild$preventDropFromBottomPart(
		Level instance, BlockPos pos, BlockState blockState, int updateFlags, Operation<Boolean> original,
		@Local(name = "bottomState") BlockState bottomState
	) {
		if (SnowloggingUtil.isSnowlogged(bottomState) && blockState.isAir() && blockState.getFluidState().isEmpty()) {
			blockState = SnowloggingUtil.getSnowEquivalent(bottomState);
		}
		return original.call(instance, pos, blockState, updateFlags);
	}
}
