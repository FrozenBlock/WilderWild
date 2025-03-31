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

package net.frozenblock.wilderwild.client.resources.sounds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.block.termite.TermiteManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public abstract class TermiteSoundInstance<T extends TermiteMoundBlockEntity> extends AbstractTickableSoundInstance {
	protected final T mound;
	protected final int termiteID;
	private boolean initialTermiteCheck;

	public TermiteSoundInstance(T mound, int termiteID, SoundEvent sound, SoundSource category) {
		super(sound, category, SoundInstance.createUnseededRandom());
		this.mound = mound;
		this.looping = true;
		this.delay = 0;
		this.volume = 0.2F;
		this.pitch = 1F;
		this.termiteID = termiteID;
	}

	@Nullable
	public TermiteManager.Termite getTermite() {
		if (this.mound != null && !this.mound.isRemoved()) {
			for (TermiteManager.Termite termite : this.mound.termiteManager.termites()) {
				if (termite.getID() == this.termiteID) {
					return termite;
				}
			}
		}
		return null;
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}

	@Override
	public void tick() {
		TermiteManager.Termite termite = this.getTermite();

		if (termite != null) {
			BlockPos pos = termite.getPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
		} else {
			if (!this.initialTermiteCheck) {
				this.initialTermiteCheck = true;
			} else {
				this.stop();
			}
			return;
		}

		if (this.shouldSwitchSounds() && !this.isStopped()) {
			Minecraft.getInstance().getSoundManager().queueTickingSound(this.getAlternativeSoundInstance());
			this.stop();
		}
	}

	protected abstract AbstractTickableSoundInstance getAlternativeSoundInstance();

	protected abstract boolean shouldSwitchSounds();
}
