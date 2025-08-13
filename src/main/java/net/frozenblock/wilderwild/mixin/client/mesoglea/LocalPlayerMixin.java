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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.resources.sounds.MesogleaAmbientSoundInstance;
import net.frozenblock.wilderwild.entity.impl.PlayerInMesogleaInterface;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

	@Inject(method = "updateIsUnderwater", at = @At("HEAD"))
	public void wilderWild$getMesogleaValuesA(
		CallbackInfoReturnable<Boolean> info,
		@Share("wilderWild$wasInMesoglea") LocalBooleanRef wasInMesoglea
	) {
		if (!(LocalPlayer.class.cast(this) instanceof PlayerInMesogleaInterface playerInMesogleaInterface)) return;
		wasInMesoglea.set(playerInMesogleaInterface.wilderWild$wasPlayerInMesoglea());
	}

	@Inject(
		method = "updateIsUnderwater",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/player/AbstractClientPlayer;updateIsUnderwater()Z",
			shift = At.Shift.AFTER
		)
	)
	public void wilderWild$getMesogleaValuesB(
		CallbackInfoReturnable<Boolean> info,
		@Share("wilderWild$isInMesoglea") LocalBooleanRef isInMesoglea
	) {
		if (!(LocalPlayer.class.cast(this) instanceof PlayerInMesogleaInterface playerInMesogleaInterface)) return;
		isInMesoglea.set(playerInMesogleaInterface.wilderWild$wasPlayerInMesoglea());
	}

	@ModifyExpressionValue(
		method = "updateIsUnderwater",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/sounds/SoundEvents;AMBIENT_UNDERWATER_ENTER:Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$replaceWaterEnterSoundIfInMesoglea(
		SoundEvent original,
		@Share("wilderWild$isInMesoglea") LocalBooleanRef isInMesoglea
	) {
		if (isInMesoglea.get()) return WWSounds.AMBIENT_MESOGLEA_ENTER;
		return original;
	}

	@WrapOperation(
		method = "updateIsUnderwater",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V"
		)
	)
	public void wilderWild$replaceLoopSoundIfInMesoglea(
		SoundManager instance, SoundInstance soundInstance, Operation<Void> original,
		@Share("wilderWild$isInMesoglea") LocalBooleanRef isInMesoglea
	) {
		if (isInMesoglea.get()) soundInstance = new MesogleaAmbientSoundInstance(LocalPlayer.class.cast(this));
		original.call(instance, soundInstance);
	}

	@ModifyExpressionValue(
		method = "updateIsUnderwater",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/sounds/SoundEvents;AMBIENT_UNDERWATER_EXIT:Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$replaceWaterExitSoundIfInMesoglea(
		SoundEvent original,
		@Share("wilderWild$wasInMesoglea") LocalBooleanRef wasInMesoglea
	) {
		if (wasInMesoglea.get()) return WWSounds.AMBIENT_MESOGLEA_EXIT;
		return original;
	}

}
