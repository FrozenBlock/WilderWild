/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.enderman;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.sound.client.api.sounds.RestrictedMovingSound;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.EnderMan;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EnderMan.class)
public class EnderManMixin {

	@Shadow
	private int lastStareSound;

	@Inject(method = "playStareSound", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$playStareSound(CallbackInfo info) {
		if (WWEntityConfig.get().enderMan.movingStareSound) {
			info.cancel();
			EnderMan enderman = EnderMan.class.cast(this);
			if (enderman.tickCount >= this.lastStareSound + 400) {
				this.lastStareSound = enderman.tickCount;
				if (!enderman.isSilent()) {
					wilderWild$playClientEnderManSound(EnderMan.class.cast(this));
				}
			}
		}
	}

	@Unique
	private static void wilderWild$playClientEnderManSound(@NotNull EnderMan enderMan) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null && enderMan.isAlive()) {
			client.getSoundManager().play(
				new RestrictedMovingSound<>(
					enderMan,
					SoundEvents.ENDERMAN_STARE,
					SoundSource.HOSTILE,
					2.5F,
					1F,
					SoundPredicate.notSilentAndAlive(),
					true
				)
			);
		}
	}

}
