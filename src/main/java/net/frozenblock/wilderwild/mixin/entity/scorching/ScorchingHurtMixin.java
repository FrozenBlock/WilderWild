package net.frozenblock.wilderwild.mixin.entity.scorching;

import net.frozenblock.wilderwild.entity.effect.ScorchingMobEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class ScorchingHurtMixin {

	@Shadow
	public abstract Collection<MobEffectInstance> getActiveEffects();

	@Inject(method = "hurt", at = @At("TAIL"))
	private void scorchAttacker(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		for (MobEffectInstance effectInstance : this.getActiveEffects()) {
			if (effectInstance.getEffect() instanceof ScorchingMobEffect effect) {
				effect.onMobHurt(LivingEntity.class.cast(this), effectInstance.getAmplifier(), source, amount);
			}
		}
	}
}
