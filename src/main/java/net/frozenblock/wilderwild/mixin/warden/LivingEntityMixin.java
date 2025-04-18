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

package net.frozenblock.wilderwild.mixin.warden;

import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenInterface;
import net.frozenblock.wilderwild.registry.WWSounds;
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
			info.setReturnValue(((WilderWarden) this).wilderWild$getDeathTicks() <= 0 && !LivingEntity.class.cast(this).isRemoved());
		}
	}

	@Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
	public void wilderWild$tickDeath(CallbackInfo info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			Warden warden = Warden.class.cast(this);
			WilderWarden wilderWarden = (WilderWarden) warden;
			Level level = warden.level();
			int deathTicks = wilderWarden.wilderWild$getDeathTicks() + 1;
			wilderWarden.wilderWild$setDeathTicks(deathTicks);
			if (!level.isClientSide) {
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

	@Inject(
		method = "die",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"
		)
	)
	public void wilderWild$die(DamageSource damageSource, CallbackInfo info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			Warden warden = Warden.class.cast(this);
			warden.getBrain().removeAllBehaviors();
			warden.setNoAi(true);
			WilderWarden wilderWarden = (WilderWarden) warden;
			if (!wilderWarden.wilderWild$isStella()) {
				if (!(warden instanceof SwimmingWardenInterface swimmingWardenInterface) || !swimmingWardenInterface.wilderWild$isSubmergedInWaterOrLava()) {
					warden.playSound(WWSounds.ENTITY_WARDEN_DYING, 5F, 1F);
				} else {
					warden.playSound(WWSounds.ENTITY_WARDEN_UNDERWATER_DYING, 5F, warden.getVoicePitch());
				}
			}
		}
	}

	@Unique
	public boolean wilderWild$isWardenWithDeathAnimation() {
		return LivingEntity.class.cast(this) instanceof WilderWarden wilderWarden && (WWEntityConfig.get().warden.wardenDeathAnimation || wilderWarden.wilderWild$isStella());
	}

}
