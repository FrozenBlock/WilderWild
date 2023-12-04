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

package net.frozenblock.wilderwild.mixin.entity.warden;

import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
	public void wilderWild$isAlive(CallbackInfoReturnable<Boolean> info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			info.setReturnValue(((WilderWarden) this).wilderWild$getDeathTicks() < 70 && !LivingEntity.class.cast(this).isRemoved());
		}
	}

	@Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
	public void wilderWild$tickDeath(CallbackInfo info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			Warden warden = Warden.class.cast(this);
			Level level = warden.level();
			int deathTicks = ((WilderWarden) this).wilderWild$getDeathTicks() + 1;
			((WilderWarden) this).wilderWild$setDeathTicks(deathTicks);
			if (!level.isClientSide()) {
				if (deathTicks == 35) {
					warden.deathTime = 35;
				} else if (deathTicks == 53) {
					level.broadcastEntityEvent(warden, EntityEvent.POOF);
					level.broadcastEntityEvent(warden, (byte) 69420);
				} else if (deathTicks == 70) {
					warden.remove(Entity.RemovalReason.KILLED);
				}
			}
			info.cancel();
		}
	}

	@Inject(method = "die", at = @At("TAIL"))
	public void wilderWild$die(DamageSource damageSource, CallbackInfo info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			Warden.class.cast(this).getBrain().removeAllBehaviors();
			Warden.class.cast(this).setNoAi(true);
		}
	}

	@Unique
	public boolean wilderWild$isWardenWithDeathAnimation() {
		return LivingEntity.class.cast(this) instanceof Warden warden && (EntityConfig.get().warden.wardenDyingAnimation || ((WilderWarden) warden).wilderWild$isStella());
	}

}
