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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import net.frozenblock.wilderwild.entity.impl.InMesogleaInterface;
import net.frozenblock.wilderwild.entity.impl.PlayerInMesogleaInterface;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin implements PlayerInMesogleaInterface {

	@Unique
	private boolean wilderWild$wasPlayerInMesoglea;

	@Inject(method = "updateIsUnderwater", at = @At(value = "HEAD"))
	public void wilderwild$updateIsInMesolgea(CallbackInfoReturnable<Boolean> info) {
		if (!(Player.class.cast(this) instanceof InMesogleaInterface mesogleaInterface)) return;
		this.wilderWild$wasPlayerInMesoglea = mesogleaInterface.wilderWild$wasInMesoglea();
	}

	@Override
	public void wilderWild$setPlayerInMesoglea(boolean inMesoglea) {
		this.wilderWild$wasPlayerInMesoglea = inMesoglea;
	}

	@Unique
	@Override
	public boolean wilderWild$wasPlayerInMesoglea() {
		return this.wilderWild$wasPlayerInMesoglea;
	}

	@Inject(method = "getSwimSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimSound(CallbackInfoReturnable<SoundEvent> info) {
		if (!(Player.class.cast(this) instanceof InMesogleaInterface inMesogleaInterface)) return;
		if (inMesogleaInterface.wilderWild$isTouchingMesogleaOrUnderWaterAndMesoglea()) info.setReturnValue(WWSounds.ENTITY_PLAYER_SWIM_MESOGLEA);
	}

	@Inject(method = "getSwimSplashSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimSplashSound(CallbackInfoReturnable<SoundEvent> info) {
		if (!(Player.class.cast(this) instanceof InMesogleaInterface inMesogleaInterface)) return;
		if (inMesogleaInterface.wilderWild$wasTouchingMesoglea()) info.setReturnValue(WWSounds.ENTITY_PLAYER_SPLASH_MESOGLEA);
	}

	@Inject(method = "getSwimHighSpeedSplashSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimHighSpeedSplashSound(CallbackInfoReturnable<SoundEvent> info) {
		if (!(Player.class.cast(this) instanceof InMesogleaInterface inMesogleaInterface)) return;
		if (inMesogleaInterface.wilderWild$wasTouchingMesoglea()) info.setReturnValue(WWSounds.ENTITY_PLAYER_SPLASH_HIGH_SPEED_MESOGLEA);
	}
}
