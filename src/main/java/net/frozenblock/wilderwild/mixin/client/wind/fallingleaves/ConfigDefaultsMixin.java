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

package net.frozenblock.wilderwild.mixin.client.wind.fallingleaves;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import randommcsomethin.fallingleaves.config.ConfigDefaults;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(ConfigDefaults.class)
public class ConfigDefaultsMixin {

	@Inject(
		method = "isConifer",
		at = @At("HEAD"),
		cancellable = true,
		require = 0
	)
	private static void wilderWild$forceAddCompatBecauseTheyDidnt(ResourceLocation blockId, CallbackInfoReturnable<Boolean> info) {
		if (blockId.toString().equals(WWConstants.string("cypress_leaves"))
			|| blockId.toString().equals(WWConstants.string("palm_fronds"))
		) {
			info.setReturnValue(true);
		}
	}

	@Inject(
		method = "spawnRateFactor",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$forceAddCompatBecauseTheyDidntAgain(ResourceLocation blockId, CallbackInfoReturnable<Double> info) {
		String blockName = blockId.toString();
		if (blockName.equals(WWConstants.string("palm_fronds"))
			|| blockName.equals(WWConstants.string("yellow_maple_leaves"))
			|| blockName.equals(WWConstants.string("orange_maple_leaves"))
			|| blockName.equals(WWConstants.string("red_maple_leaves"))
		) {
			info.setReturnValue(0D);
		}
	}

}
