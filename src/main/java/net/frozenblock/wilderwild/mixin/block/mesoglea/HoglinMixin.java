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
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Hoglin.class)
public class HoglinMixin {

	@Inject(method = "getSwimSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimSound(CallbackInfoReturnable<SoundEvent> info) {
		if (!(Hoglin.class.cast(this) instanceof InMesogleaInterface inMesogleaInterface)) return;
		if (inMesogleaInterface.wilderWild$wasInMesoglea()) info.setReturnValue(WWSounds.ENTITY_HOSTILE_SWIM_MESOGLEA);
	}

	@Inject(method = "getSwimSplashSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimSplashSound(CallbackInfoReturnable<SoundEvent> info) {
		if (!(Hoglin.class.cast(this) instanceof InMesogleaInterface inMesogleaInterface)) return;
		if (inMesogleaInterface.wilderWild$wasTouchingMesoglea()) info.setReturnValue(WWSounds.ENTITY_HOSTILE_SPLASH_MESOGLEA);
	}
}
