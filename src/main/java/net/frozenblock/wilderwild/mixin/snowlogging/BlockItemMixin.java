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

package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
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
	public SoundType wilderWild$place(BlockState instance, Operation<SoundType> original) {
		return (WWBlockConfig.canSnowlog() && (instance.hasProperty(SnowloggingUtils.SNOW_LAYERS) && instance.getValue(SnowloggingUtils.SNOW_LAYERS) > 0)) ?
			original.call(SnowloggingUtils.getSnowEquivalent(instance)) : original.call(instance);
	}

	@Inject(method = "getPlaceSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getPlaceSound(BlockState state, CallbackInfoReturnable<SoundEvent> info) {
		if (!WWBlockConfig.canSnowlog()) return;
		if (state.hasProperty(SnowloggingUtils.SNOW_LAYERS) && state.getValue(SnowloggingUtils.SNOW_LAYERS) > 0) {
			info.setReturnValue(SnowloggingUtils.getSnowEquivalent(state).getSoundType().getPlaceSound());
		}
	}

}
