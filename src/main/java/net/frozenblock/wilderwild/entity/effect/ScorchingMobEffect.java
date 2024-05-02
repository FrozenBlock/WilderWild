/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.effect;

import java.util.function.ToIntFunction;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ScorchingMobEffect extends MobEffect {
	private final float chanceToScorch;
	private final ToIntFunction<RandomSource> fireDurationInSeconds;

	public ScorchingMobEffect(MobEffectCategory type, int color, float chanceToScorch, ToIntFunction<RandomSource> toIntFunction) {
		super(type, color);
		this.chanceToScorch = chanceToScorch;
		this.fireDurationInSeconds = toIntFunction;
	}

	// particle tick
	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		super.applyEffectTick(livingEntity, amplifier);

		int i = livingEntity.isInvisible() ? 15 : 4;
		int j = 1;
		if (livingEntity.getRandom().nextInt(i * j) == 0) {
			livingEntity.level().addParticle(RegisterParticles.SCORCHING_FLAME, RegisterParticles.SCORCHING_FLAME.getType().getOverrideLimiter(), livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 1, 1, 1);
		}
	}

	public void onMobHurt(@NotNull LivingEntity livingEntity, int amplifier, DamageSource damageSource, float f) {
		RandomSource random = livingEntity.getRandom();
		if (random.nextFloat() <= this.chanceToScorch && damageSource.getDirectEntity() != null) {
			int fireTicks = this.fireDurationInSeconds.applyAsInt(random);
			damageSource.getDirectEntity().setRemainingFireTicks(fireTicks);
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
