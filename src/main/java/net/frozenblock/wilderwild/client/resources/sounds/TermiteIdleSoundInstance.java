/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.resources.sounds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.block.termite.TermiteManager;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;

@Environment(EnvType.CLIENT)
public class TermiteIdleSoundInstance<T extends TermiteMoundBlockEntity> extends TermiteSoundInstance<T> {

	public TermiteIdleSoundInstance(T mound, int termiteID) {
		super(mound, termiteID, WWSounds.BLOCK_TERMITE_MOUND_TERMITE_IDLE, SoundSource.BLOCKS);
	}

	@Override
	protected AbstractTickableSoundInstance getAlternativeSoundInstance() {
		return new TermiteEatingSoundInstance<>(this.mound, this.termiteID);
	}

	@Override
	protected boolean shouldSwitchSounds() {
		TermiteManager.Termite termite = this.getTermite();
		if (termite != null) return termite.getEating();
		return false;
	}
}
