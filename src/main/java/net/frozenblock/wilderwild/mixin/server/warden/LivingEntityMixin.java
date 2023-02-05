package net.frozenblock.wilderwild.mixin.server.warden;

import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Shadow
	public int deathTime;

	@Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
	public void wilderWild$isAlive(CallbackInfoReturnable<Boolean> info) {
		if (LivingEntity.class.cast(this) instanceof Warden warden) {
			if (WilderSharedConstants.CONFIG().wardenDyingAnimation() || ((WilderWarden)warden).wilderWild$isStella()) {
				info.setReturnValue(((WilderWarden)warden).wilderWild$getDeathTicks() < 70 && !warden.isRemoved());
			}
		}
	}

	@Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
	public void wilderWild$tickDeath(CallbackInfo info) {
		if (LivingEntity.class.cast(this) instanceof Warden warden) {
			if (WilderSharedConstants.CONFIG().wardenDyingAnimation() || ((WilderWarden)warden).wilderWild$isStella()) {
				((WilderWarden)warden).wilderWild$setDeathTicks(((WilderWarden)warden).wilderWild$getDeathTicks() + 1);

				if (((WilderWarden)warden).wilderWild$getDeathTicks() == 35 && !warden.level.isClientSide()) {
					warden.deathTime = 35;
				}

				if (((WilderWarden)warden).wilderWild$getDeathTicks() == 53 && !warden.level.isClientSide()) {
					warden.level.broadcastEntityEvent(warden, EntityEvent.POOF);
					warden.level.broadcastEntityEvent(warden, (byte) 69420);
				}

				if (((WilderWarden)warden).wilderWild$getDeathTicks() == 70 && !warden.level.isClientSide()) {
					warden.remove(Entity.RemovalReason.KILLED);
				}

				info.cancel();
			}
		}
	}

	@Inject(method = "die", at = @At("TAIL"))
	public void wilderWild$die(DamageSource damageSource, CallbackInfo info) {
		if (LivingEntity.class.cast(this) instanceof Warden warden) {
			if (WilderSharedConstants.CONFIG().wardenDyingAnimation() || ((WilderWarden)warden).wilderWild$isStella()) {
				warden.getBrain().removeAllBehaviors();
				warden.setNoAi(true);
			}
		}
	}

}
