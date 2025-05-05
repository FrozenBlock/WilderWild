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

package net.frozenblock.wilderwild.mixin.entity.tumbleweed;

import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "push", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$push(Entity entity, CallbackInfo info) {
		this.wilderWild$cancelIfEntityIsTumbleweed(entity, info);
	}

	@Inject(method = "doPush", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$doPush(Entity entity, CallbackInfo info) {
		this.wilderWild$cancelIfEntityIsTumbleweed(entity, info);
	}

	@Unique
	private void wilderWild$cancelIfEntityIsTumbleweed(Entity entity, CallbackInfo info) {
		if (entity instanceof Tumbleweed && Entity.class.cast(this).getType().is(WWEntityTags.TUMBLEWEED_PASSES_THROUGH)) {
			info.cancel();
			return;
		}
		if (Entity.class.cast(this) instanceof Tumbleweed && entity.getType().is(WWEntityTags.TUMBLEWEED_PASSES_THROUGH)) info.cancel();
	}

}
