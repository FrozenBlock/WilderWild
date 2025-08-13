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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.resources.sounds.MesogleaAmbientSoundInstance;
import net.frozenblock.wilderwild.entity.impl.PlayerInMesogleaInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class UnderwaterAmbientSoundInstancesMixin {

	@Mixin(UnderwaterAmbientSoundInstances.SubSound.class)
	public static class SubSoundMixin {
		@Shadow
		@Final
		private LocalPlayer player;

		@ModifyExpressionValue(
			method = "tick",
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"
			)
		)
		public boolean wilderWild$stopAdditionsIfInMesoglea(boolean original) {
			if (this.player instanceof PlayerInMesogleaInterface playerInMesoglea && playerInMesoglea.wilderWild$wasPlayerInMesoglea()) return false;
			return original;
		}
	}

	@Mixin(UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance.class)
	public static class UnderwaterAmbientSoundInstanceMixin {
		@Shadow
		@Final
		private LocalPlayer player;

		@Unique
		private final List<AbstractTickableSoundInstance> wilderWild$altSounds = new ArrayList<>();

		@ModifyExpressionValue(
			method = "tick",
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"
			)
		)
		public boolean wilderWild$stopAdditionsIfInMesoglea(boolean original) {
			if (this.player instanceof PlayerInMesogleaInterface playerInMesoglea && playerInMesoglea.wilderWild$wasPlayerInMesoglea()) {
				if (this.wilderWild$altSounds.isEmpty() || this.wilderWild$altSounds.stream().allMatch(AbstractTickableSoundInstance::isStopped)) {
					AbstractTickableSoundInstance mesogleaSound = new MesogleaAmbientSoundInstance(this.player);
					Minecraft.getInstance().getSoundManager().playDelayed(mesogleaSound, 1);
					this.wilderWild$altSounds.add(mesogleaSound);
				}
				return false;
			}
			return original;
		}
	}

}
