/*
 * Copyright 2024 FrozenBlock
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
