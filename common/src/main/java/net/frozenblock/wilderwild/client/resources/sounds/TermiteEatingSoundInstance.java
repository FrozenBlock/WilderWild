/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.client.resources.sounds;

import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.block.termite.TermiteManager;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;

@ClientOnly
public class TermiteEatingSoundInstance<T extends TermiteMoundBlockEntity> extends AbstractTermiteSoundInstance<T> {

	public TermiteEatingSoundInstance(T mound) {
		super(mound, WWSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW.get(), SoundSource.BLOCKS);
	}

	public static void addTermiteSound(TermiteMoundBlockEntity mound, boolean eating) {
		final Minecraft client = Minecraft.getInstance();
		if (client.level == null) return;
		if (eating) {
			client.getSoundManager().play(new TermiteEatingSoundInstance<>(mound));
		} else {
			client.getSoundManager().play(new TermiteIdleSoundInstance<>(mound));
		}
	}

	@Override
	protected AbstractTermiteSoundInstance<T> getAlternativeSoundInstance() {
		return new TermiteIdleSoundInstance<>(this.mound);
	}

	@Override
	protected boolean shouldSwitchSounds() {
		final TermiteManager.Termite termite = this.getTermite();
		if (termite != null) return !termite.getEating();
		return false;
	}
}
