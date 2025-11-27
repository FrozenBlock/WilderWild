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

package net.frozenblock.wilderwild.mixin.entity.easter;

import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.ChatFormatting;

@Mixin(Frog.class)
public final class FrogMixin {

	@Inject(at = @At("RETURN"), method = "getDeathSound", cancellable = true)
	public void wilderWild$newDeath(CallbackInfoReturnable<SoundEvent> info) {
		final String string = ChatFormatting.stripFormatting(Frog.class.cast(this).getName().getString());
		if (string.equalsIgnoreCase("Xfrtrex") || string.equalsIgnoreCase("BluePhoenixLOL")) info.setReturnValue(WWSounds.ENTITY_FROG_SUS_DEATH);
	}

}
