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

import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.config.ItemConfig;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

	@Inject(
		method = "onHit",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V", ordinal = 0),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void wilderWild$onHitWithServerPlayer(HitResult result, CallbackInfo info, ServerLevel level, Entity entity) {
		if (ItemConfig.get().projectileLandingSounds.enderPearlLandingSounds && entity instanceof ServerPlayer owner) {
			ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
			if (!pearl.isSilent()) {
				float pitch = 0.9F + (level.random.nextFloat() * 0.2F);
				pearl.level().playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), RegisterSounds.ITEM_ENDER_PEARL_LAND, owner.getSoundSource(), 0.6F, pitch);
				FrozenSoundPackets.createLocalPlayerSound(
					owner,
					BuiltInRegistries.SOUND_EVENT.getHolder(RegisterSounds.ITEM_ENDER_PEARL_LAND.getLocation()).orElseThrow(),
					0.6F,
					pitch
				);
			}
			if (!owner.isSilent()) {
				float pitch = 0.9F + (level.random.nextFloat() * 0.2F);
				level.playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, owner.getSoundSource(), 0.4F, pitch);
				FrozenSoundPackets.createLocalPlayerSound(
					owner,
					BuiltInRegistries.SOUND_EVENT.getHolder(SoundEvents.CHORUS_FRUIT_TELEPORT.getLocation()).orElseThrow(),
					0.4F,
					pitch)
				;
			}
		}
	}

	@Inject(
		method = "onHit",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V", ordinal = 1),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void wilderWild$onHitWithoutServerPlayer(HitResult result, CallbackInfo info, ServerLevel level, Entity owner) {
		if (ItemConfig.get().projectileLandingSounds.enderPearlLandingSounds) {
			ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
			if (!pearl.isSilent()) {
				level.playSound(null, pearl.getX(), pearl.getY(), pearl.getZ(), RegisterSounds.ITEM_ENDER_PEARL_LAND, owner.getSoundSource(), 0.6F, 0.85F + (pearl.level().random.nextFloat() * 0.2F));
			}
			if (owner != null && !owner.isSilent()) {
				level.playSound(null, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, owner.getSoundSource(), 0.4F, 0.85F + (pearl.level().random.nextFloat() * 0.2F));
			}
		}
	}

}
