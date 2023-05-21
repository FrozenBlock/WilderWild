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

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import org.jetbrains.annotations.NotNull;

public class ClientMethodInteractionHandler {
	private ClientMethodInteractionHandler() {
		throw new UnsupportedOperationException("ClientMethodInteractionHandler contains only static declarations.");
	}

	public static void playClientEnderManSound(@NotNull EnderMan enderMan) {
		ClientMethods.playClientEnderManSound(enderMan);
	}

	public static void playClientPlayerSoundIfSamePlayer(@NotNull SoundEvent sound, float volume, float pitch, @NotNull Entity compareTo) {
		ClientMethods.playClientPlayerSoundIfSamePlayer(sound, volume, pitch, compareTo);
	}
}
