package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffect.class)
public class MobEffectMixin {

    @Inject(at = @At("HEAD"), method = "isDurationEffectTick", cancellable = true)
    public void isDurationEffectTick(int i, int j, CallbackInfoReturnable<Boolean> info) {
        MobEffect effect = MobEffect.class.cast(this);
        if (effect == RegisterMobEffects.VENOMOUS_STING) {
            int k = 60 >> j;
            if (k > 0) {
                info.setReturnValue(i % k == 0);
                info.cancel();
            }
            info.setReturnValue(true);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "applyEffectTick", cancellable = true)
    public void applyEffectTick(LivingEntity livingEntity, int i, CallbackInfo info) {
        MobEffect effect = MobEffect.class.cast(this);
        if (effect == RegisterMobEffects.VENOMOUS_STING) {
            if (livingEntity.getHealth() > 2.0f) {
                livingEntity.hurt(DamageSource.MAGIC, 1.0f);
            }
            info.cancel();
        }
    }

}
