/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.sound.api.instances.RestrictedMovingSound;
import net.frozenblock.lib.sound.api.instances.RestrictedMovingSoundLoop;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.sound.TermiteSoundInstance;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ClientMethods {
	private ClientMethods() {
		throw new UnsupportedOperationException("ClientMethods contains only static declarations.");
	}

	public static void playClientEnderManSound(@NotNull EnderMan enderMan) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null && enderMan.isAlive()) {
			client.getSoundManager().play(new RestrictedMovingSound<>(enderMan, SoundEvents.ENDERMAN_STARE, SoundSource.HOSTILE, 2.5F, 1F, SoundPredicate.notSilentAndAlive(), true));
		}
	}

	public static void playClientEnderManLoop(@NotNull EnderMan enderMan) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null && enderMan.isAlive()) {
			Minecraft.getInstance().getSoundManager().play(new RestrictedMovingSoundLoop<>(enderMan, RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP, SoundSource.HOSTILE, 1.0F, 0.9F, SoundPredicate.getPredicate(WilderSharedConstants.id("enderman_anger")), true));
		}
	}

	public static void playClientPlayerSoundIfSamePlayer(@NotNull SoundEvent sound, float volume, float pitch, @NotNull Entity compareTo) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null && compareTo == client.player) {
			client.getSoundManager().play(new EntityBoundSoundInstance(sound, SoundSource.PLAYERS, volume, pitch, client.player, client.level.random.nextLong()));
		}
	}

	public static void addTermiteSound(TermiteMoundBlockEntity mound, int termiteID, boolean eating) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null) {
			if (eating) {
				client.getSoundManager().play(new TermiteSoundInstance<>(mound, termiteID, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW, SoundSource.BLOCKS, 0.2F, 1F, true));
			} else {
				client.getSoundManager().play(new TermiteSoundInstance<>(mound, termiteID, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_IDLE, SoundSource.BLOCKS, 0.2F, 1F, false));
			}
		}
	}

}
