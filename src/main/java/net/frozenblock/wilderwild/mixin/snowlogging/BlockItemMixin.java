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
import net.frozenblock.wilderwild.registry.RegisterProperties;
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

	@WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getSoundType()Lnet/minecraft/world/level/block/SoundType;"))
	public SoundType wilderWild$place(BlockState instance, Operation<SoundType> original) {
		return (instance.hasProperty(RegisterProperties.SNOW_LAYERS) && instance.getValue(RegisterProperties.SNOW_LAYERS) > 0) ?
			original.call(RegisterProperties.getSnowEquivalent(instance)) : original.call(instance);
	}

	@Inject(method = "getPlaceSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getPlaceSound(BlockState state, CallbackInfoReturnable<SoundEvent> info) {
		if (state.hasProperty(RegisterProperties.SNOW_LAYERS) && state.getValue(RegisterProperties.SNOW_LAYERS) > 0) {
			info.setReturnValue(RegisterProperties.getSnowEquivalent(state).getSoundType().getPlaceSound());
		}
	}

}
