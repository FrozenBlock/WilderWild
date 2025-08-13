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

package net.frozenblock.wilderwild.client.resources.sounds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.impl.PlayerInMesogleaInterface;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
import net.minecraft.sounds.SoundSource;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MesogleaAmbientSoundInstance extends AbstractTickableSoundInstance {
	public static final int FADE_DURATION = 40;
	private final LocalPlayer player;
	private int fade;
	private final List<AbstractTickableSoundInstance> altSounds = new ArrayList<>();

	public MesogleaAmbientSoundInstance(LocalPlayer localPlayer) {
		super(WWSounds.AMBIENT_MESOGLEA_LOOP, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
		this.player = localPlayer;
		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
		this.relative = true;
	}

	@Override
	public void tick() {
		if (!this.player.isRemoved() && this.fade >= 0) {
			boolean isUnderwater = this.player.isUnderWater();
			boolean inMesoglea = this.player instanceof PlayerInMesogleaInterface mesogleaInterface && mesogleaInterface.wilderWild$wasPlayerInMesoglea();
			if (isUnderwater && inMesoglea) {
				this.fade++;
			} else {
				if (isUnderwater) {
					if (this.altSounds.isEmpty() || this.altSounds.stream().allMatch(AbstractTickableSoundInstance::isStopped)) {
						AbstractTickableSoundInstance underwaterSound = new UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance(this.player);
						Minecraft.getInstance().getSoundManager().playDelayed(underwaterSound, 1);
						this.altSounds.add(underwaterSound);
					}
				}
				this.fade -= 2;
			}

			this.fade = Math.min(this.fade, FADE_DURATION);
			this.volume = Math.max(0F, Math.min((float) this.fade / FADE_DURATION, 1F));
		} else {
			this.stop();
		}
	}
}
