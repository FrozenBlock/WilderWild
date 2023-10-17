/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.warden;

import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
	public void wilderWild$isAlive(CallbackInfoReturnable<Boolean> info) {
		if (LivingEntity.class.cast(this) instanceof Warden warden) {
			if (EntityConfig.get().warden.wardenDyingAnimation || ((WilderWarden) warden).wilderWild$isStella()) {
				info.setReturnValue(((WilderWarden) warden).wilderWild$getDeathTicks() < 70 && !warden.isRemoved());
			}
		}
	}

	@Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
	public void wilderWild$tickDeath(CallbackInfo info) {
		if (LivingEntity.class.cast(this) instanceof Warden warden) {
			if (EntityConfig.get().warden.wardenDyingAnimation || ((WilderWarden) warden).wilderWild$isStella()) {
				((WilderWarden) warden).wilderWild$setDeathTicks(((WilderWarden) warden).wilderWild$getDeathTicks() + 1);

				if (((WilderWarden) warden).wilderWild$getDeathTicks() == 35 && !warden.level().isClientSide()) {
					warden.deathTime = 35;
				}

				if (((WilderWarden) warden).wilderWild$getDeathTicks() == 53 && !warden.level().isClientSide()) {
					warden.level().broadcastEntityEvent(warden, EntityEvent.POOF);
					warden.level().broadcastEntityEvent(warden, (byte) 69420);
				}

				if (((WilderWarden) warden).wilderWild$getDeathTicks() == 70 && !warden.level().isClientSide()) {
					warden.remove(Entity.RemovalReason.KILLED);
				}

				info.cancel();
			}
		}
	}

	@Inject(method = "die", at = @At("TAIL"))
	public void wilderWild$die(DamageSource damageSource, CallbackInfo info) {
		if (LivingEntity.class.cast(this) instanceof Warden warden) {
			if (EntityConfig.get().warden.wardenDyingAnimation || ((WilderWarden) warden).wilderWild$isStella()) {
				warden.getBrain().removeAllBehaviors();
				warden.setNoAi(true);
			}
		}
	}

}
