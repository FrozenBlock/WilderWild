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

import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.ChatFormatting;

@Mixin(Goat.class)
public class GoatMixin {

	@Unique
	private boolean wilderWild$isTreetrain1() {
		String string = ChatFormatting.stripFormatting(Goat.class.cast(this).getName().getString());
		return string.equalsIgnoreCase("Treetrain1") || string.equalsIgnoreCase("Treetrain");
	}

	@Inject(method = "isScreamingGoat", at = @At("RETURN"), cancellable = true)
	private void wilderWild$isScreamingGoat(CallbackInfoReturnable<Boolean> info) {
		if (this.wilderWild$isTreetrain1()) info.setReturnValue(true);
	}

}
