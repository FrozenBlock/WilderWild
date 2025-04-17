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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
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
