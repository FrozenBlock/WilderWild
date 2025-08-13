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

package net.frozenblock.wilderwild.mixin.projectile;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.lib.sound.impl.networking.FrozenLibSoundPackets;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
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
			target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V",
			ordinal = 0
		)
	)
	public void wilderWild$onHitWithServerPlayer(
		HitResult result, CallbackInfo info,
		@Local(ordinal = 0) ServerLevel level, @Local(ordinal = 0) Entity entity
	) {
		if (WWItemConfig.get().projectileLandingSounds.enderPearlLandingSounds && entity instanceof ServerPlayer owner) {
			ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
			if (!pearl.isSilent()) {
				float pitch = 0.9F + (level.random.nextFloat() * 0.2F);
				level.playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), WWSounds.ITEM_ENDER_PEARL_LAND, owner.getSoundSource(), 0.6F, pitch);
				FrozenLibSoundPackets.createAndSendLocalPlayerSound(
					owner,
					BuiltInRegistries.SOUND_EVENT.getHolder(WWSounds.ITEM_ENDER_PEARL_LAND.getLocation()).orElseThrow(),
					0.6F,
					pitch
				);
			}
		}
	}

	@Inject(
		method = "onHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V",
			ordinal = 1
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
		}
	}

}
