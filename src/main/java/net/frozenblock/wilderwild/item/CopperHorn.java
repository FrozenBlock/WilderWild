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

package net.frozenblock.wilderwild.item;

import java.util.Optional;
import net.frozenblock.lib.sound.impl.networking.FrozenLibSoundPackets;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.mod_compat.FrozenLibIntegration;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CopperHorn extends InstrumentItem {

	public CopperHorn(@NotNull Properties settings) {
		super(settings);
	}

	private static void playSound(@NotNull Instrument instrument, @NotNull Player user, @NotNull Level level) {
		SoundEvent soundEvent = instrument.soundEvent().value();
		float range = instrument.range() / 16F;
		int note = (int) ((-user.getXRot() + 90) / 7.5D);

		if (!level.isClientSide) {
			float soundPitch = !user.isShiftKeyDown() ?
				(float) Math.pow(2D, (note - 12F) / 12D) :
				(float) Math.pow(2D, 0.01111F * -user.getXRot());
			FrozenLibSoundPackets.createAndSendMovingRestrictionLoopingSound(
				level,
				user,
				BuiltInRegistries.SOUND_EVENT.get(soundEvent.location()).orElseThrow(),
				SoundSource.RECORDS,
				range,
				soundPitch,
				FrozenLibIntegration.INSTRUMENT_SOUND_PREDICATE,
				true
			);
		}
		level.gameEvent(GameEvent.INSTRUMENT_PLAY, user.position(), GameEvent.Context.of(user));
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand interactionHand) {
		ItemStack itemStack = user.getItemInHand(interactionHand);
		Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemStack, level.registryAccess());
		if (optional.isPresent()) {
			user.startUsingItem(interactionHand);
			playSound(optional.get().value(), user, level);
			return InteractionResult.CONSUME;
		} else {
			WWConstants.printStackTrace("Copper Horn use failed!", true);
			return InteractionResult.FAIL;
		}
	}
}
