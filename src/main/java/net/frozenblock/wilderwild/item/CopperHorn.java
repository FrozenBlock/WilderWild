/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.item;

import java.util.Optional;
import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.mod_compat.FrozenLibIntegration;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CopperHorn extends InstrumentItem {

	public CopperHorn(@NotNull Properties settings, @NotNull TagKey<Instrument> instruments) {
		super(settings, instruments);
	}

	private static void playSound(@NotNull Instrument instrument, @NotNull Player user, @NotNull Level level) {
		SoundEvent soundEvent = instrument.soundEvent().value();
		float range = instrument.range() / 16F;
		int note = (int) ((-user.getXRot() + 90) / 7.5D);

		if (!level.isClientSide) {
			float soundPitch = !user.isShiftKeyDown() ?
				(float) Math.pow(2.0D, (note - 12.0F) / 12.0D) :
				(float) Math.pow(2.0D, 0.01111F * -user.getXRot());
			FrozenSoundPackets.createMovingRestrictionLoopingSound(
				level,
				user,
				soundEvent,
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
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand interactionHand) {
		ItemStack itemStack = user.getItemInHand(interactionHand);
		Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemStack);
		if (optional.isPresent()) {
			user.startUsingItem(interactionHand);
			playSound(optional.get().value(), user, level);
			return InteractionResultHolder.consume(itemStack);
		} else {
			WilderSharedConstants.printStackTrace("Copper Horn use failed!", true);
			return InteractionResultHolder.fail(itemStack);
		}
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
		Optional<? extends Holder<Instrument>> optional = this.getInstrument(stack);
		return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
	}

}
