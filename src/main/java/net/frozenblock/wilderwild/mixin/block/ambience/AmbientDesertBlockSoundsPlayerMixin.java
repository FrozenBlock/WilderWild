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

package net.frozenblock.wilderwild.mixin.block.ambience;

import net.frozenblock.wilderwild.registry.WWBiomes;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.sounds.AmbientDesertBlockSoundsPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AmbientDesertBlockSoundsPlayer.class)
public final class AmbientDesertBlockSoundsPlayerMixin {

	@Inject(method = "isInAmbientSoundBiome", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$allowDesertAmbienceInWWBiomes(Holder<Biome> holder, CallbackInfoReturnable<Boolean> info) {
		if (holder.is(WWBiomes.OASIS)) info.setReturnValue(true);
	}

}
