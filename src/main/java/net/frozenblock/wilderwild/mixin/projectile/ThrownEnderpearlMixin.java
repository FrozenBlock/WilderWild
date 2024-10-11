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

package net.frozenblock.wilderwild.mixin.projectile;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

	@Inject(
		method = "onHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;resetFallDistance()V"
		)
	)
	public void wilderWild$onHitWithServerPlayer
		(HitResult result, CallbackInfo info,
		 @Local(ordinal = 0) ServerLevel level, @Local(ordinal = 0) ServerPlayer owner
		) {
		if (WWItemConfig.get().projectileLandingSounds.enderPearlLandingSounds) {
			ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
			if (!pearl.isSilent()) {
				float pitch = 0.9F + (level.random.nextFloat() * 0.2F);
				level.playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), WWSounds.ITEM_ENDER_PEARL_LAND, owner.getSoundSource(), 0.6F, pitch);
				FrozenSoundPackets.createLocalPlayerSound(
					owner,
					BuiltInRegistries.SOUND_EVENT.get(WWSounds.ITEM_ENDER_PEARL_LAND.location()).orElseThrow(),
					0.6F,
					pitch
				);
			}
			if (!owner.isSilent()) {
				float pitch = 0.9F + (level.random.nextFloat() * 0.2F);
				level.playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, owner.getSoundSource(), 0.4F, pitch);
				FrozenSoundPackets.createLocalPlayerSound(
					owner,
					BuiltInRegistries.SOUND_EVENT.get(SoundEvents.CHORUS_FRUIT_TELEPORT.location()).orElseThrow(),
					0.4F,
					pitch
				);
			}
		}
	}

	@Inject(
		method = "onHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V"
		)
	)
	public void wilderWild$onHitWithoutServerPlayer(
		HitResult result, CallbackInfo info,
		@Local(ordinal = 0) ServerLevel level, @Local(ordinal = 0) Entity owner
	) {
		if (WWItemConfig.get().projectileLandingSounds.enderPearlLandingSounds) {
			ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
			if (!pearl.isSilent()) {
				level.playSound(
					null,
					pearl.getX(),
					pearl.getY(),
					pearl.getZ(),
					WWSounds.ITEM_ENDER_PEARL_LAND,
					owner.getSoundSource(),
					0.6F,
					0.85F + (level.random.nextFloat() * 0.2F)
				);
			}
			if (owner != null && !owner.isSilent()) {
				level.playSound(
					null,
					pearl.getX(),
					pearl.getY(),
					pearl.getZ(),
					SoundEvents.CHORUS_FRUIT_TELEPORT,
					owner.getSoundSource(),
					0.4F,
					0.85F + (level.random.nextFloat() * 0.2F)
				);
			}
		}
	}

}
