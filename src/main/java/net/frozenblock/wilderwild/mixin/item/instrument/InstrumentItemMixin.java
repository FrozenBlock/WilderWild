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

package net.frozenblock.wilderwild.mixin.item.instrument;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.lib.sound.impl.networking.FrozenLibSoundPackets;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.mod_compat.FrozenLibIntegration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InstrumentItem.class)
public final class InstrumentItemMixin {

	@WrapOperation(
		method = "play",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
		)
	)
	private static void wilderWild$playRestrictionSound(
		Level level, Entity player, Entity entity, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, Operation<Void> original
	) {
		if (WWItemConfig.get().restrictInstrumentSound) {
			if (!level.isClientSide) {
				FrozenLibSoundPackets.createAndSendMovingRestrictionSound(
					level,
					player,
					BuiltInRegistries.SOUND_EVENT.get(soundEvent.location()).orElseThrow(),
					soundSource,
					volume,
					pitch,
					FrozenLibIntegration.INSTRUMENT_SOUND_PREDICATE,
					true
				);
			}
		} else {
			original.call(level, player, entity, soundEvent, soundSource, volume, pitch);
		}
	}

	@WrapWithCondition(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/ItemStack;I)V"
		)
	)
	private boolean wilderWild$bypassCooldown(ItemCooldowns instance, ItemStack itemStack, int i) {
		return !WWItemConfig.get().restrictInstrumentSound;
	}

}
