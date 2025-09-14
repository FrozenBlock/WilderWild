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

package net.frozenblock.wilderwild.particle.provider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WWParticleProviders {
	public static class ScorchingEffectFlameProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public ScorchingEffectFlameProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			FlameParticle flameParticle = new FlameParticle(level, x, y, z, xd, yd, zd, this.spriteSet.get(random));
			flameParticle.yd = yd * 0.05D;
			flameParticle.xd = (0.5D - level.random.nextDouble()) * 0.05D;
			flameParticle.zd = (0.5D - level.random.nextDouble()) * 0.05D;
			flameParticle.setLifetime((int)(8D / (Math.random() * 0.8D + 0.2D)));
			return flameParticle;
		}
	}

	public static class UnderwaterAshProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public UnderwaterAshProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			SuspendedParticle suspendedParticle = new SuspendedParticle(level, x, y, z, this.spriteSet.get(random));
			final float color = level.random.nextFloat() * 0.3F;
			suspendedParticle.setColor(color, color, color);
			suspendedParticle.scale(3F);
			suspendedParticle.xd = 0D;
			suspendedParticle.yd = -0.025D;
			suspendedParticle.zd = 0D;
			suspendedParticle.friction = 0.98F;
			return suspendedParticle;
		}
	}
}
