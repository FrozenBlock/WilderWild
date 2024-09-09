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
import net.frozenblock.lib.FrozenSharedConstants;
import net.frozenblock.lib.item.impl.CooldownInterface;
import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.entity.AncientHornVibration;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AncientHorn extends InstrumentItem {
	public static final int MIN_BUBBLES = 10;
	public static final int MAX_BUBBLES = 25;

	public AncientHorn(@NotNull Properties settings, @NotNull TagKey<Instrument> instruments) {
		super(settings, instruments);
	}

	public static int getCooldown(@Nullable Entity entity, int cooldown) {
		if (entity instanceof Player player && player.isCreative()) {
			return WWItemConfig.get().ancientHorn.ancientHornCreativeCooldown;
		}
		return cooldown;
	}

	private static void play(@NotNull Level level, @NotNull Player player, @NotNull Instrument instrument) {
		SoundEvent soundEvent = instrument.soundEvent().value();
		float range = instrument.range() / 16F;
		level.playSound(player, player, soundEvent, SoundSource.RECORDS, range, 1.0F);
	}

	@Override
	@NotNull
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemStack);
		if (optional.isPresent()) {
			user.startUsingItem(hand);
			play(level, user, optional.get().value());
			user.getCooldowns().addCooldown(WWItems.ANCIENT_HORN, getCooldown(user, WWItemConfig.get().ancientHorn.ancientHornCooldown));
			if (level instanceof ServerLevel server) {
				AncientHornVibration projectileEntity = new AncientHornVibration(level, user.getX(), user.getEyeY(), user.getZ());
				projectileEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0F, 1F, 0F);
				projectileEntity.setShotByPlayer(true);
				server.addFreshEntity(projectileEntity);
				FrozenSoundPackets.createMovingRestrictionLoopingSound(
					server,
					projectileEntity,
					BuiltInRegistries.SOUND_EVENT.getHolder(WWSounds.ENTITY_ANCIENT_HORN_VIBRATION_LOOP.getLocation()).orElseThrow(),
					SoundSource.NEUTRAL,
					1F,
					1F,
					FrozenSharedConstants.id("default"),
					true
				);
				ItemStack mainHand = user.getItemInHand(InteractionHand.MAIN_HAND);
				ItemStack offHand = user.getItemInHand(InteractionHand.OFF_HAND);
				if (mainHand.is(Items.WATER_BUCKET) || mainHand.is(Items.POTION) || offHand.is(Items.WATER_BUCKET) || offHand.is(Items.POTION)) {
					projectileEntity.setBubbles(level.random.nextIntBetweenInclusive(MIN_BUBBLES, MAX_BUBBLES));
				}
			}
			return InteractionResultHolder.consume(itemStack);
		} else {
			WWConstants.printStackTrace("Ancient Horn use failed!", true);
			return InteractionResultHolder.fail(itemStack);
		}
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
		Optional<? extends Holder<Instrument>> optional = this.getInstrument(stack);
		return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
	}

}
