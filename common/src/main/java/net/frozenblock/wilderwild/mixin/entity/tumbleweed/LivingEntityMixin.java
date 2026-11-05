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

package net.frozenblock.wilderwild.mixin.entity.tumbleweed;

import net.frozenblock.wilderwild.entity.block.Tumbleweed;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "push", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$push(Entity entity, CallbackInfo info) {
		this.wilderWild$cancelPushIfEntityIsTumbleweed(entity, info);
	}

	@Inject(method = "doPush", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$doPush(Entity entity, CallbackInfo info) {
		this.wilderWild$cancelPushIfEntityIsTumbleweed(entity, info);
	}

	@Unique
	private void wilderWild$cancelPushIfEntityIsTumbleweed(Entity entity, CallbackInfo info) {
		if (entity instanceof Tumbleweed && Entity.class.cast(this).is(WWEntityTypeTags.TUMBLEWEED_PASSES_THROUGH)) {
			info.cancel();
			return;
		}
		if (Entity.class.cast(this) instanceof Tumbleweed && entity.is(WWEntityTypeTags.TUMBLEWEED_PASSES_THROUGH)) info.cancel();
	}

	@Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
	public void wilderWild$cancelMobEffectsOnTumbleweed(MobEffectInstance newEffect, CallbackInfoReturnable<Boolean> info) {
		if (LivingEntity.class.cast(this) instanceof Tumbleweed) info.setReturnValue(false);
	}
}
