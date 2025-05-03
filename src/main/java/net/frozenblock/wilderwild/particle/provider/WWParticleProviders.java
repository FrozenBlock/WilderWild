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
import net.frozenblock.lib.math.api.AdvancedMath;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class WWParticleProviders {
	public static class ScorchingEffectFlameProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public ScorchingEffectFlameProvider(SpriteSet sprites) {
			this.sprite = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			RandomSource random = AdvancedMath.random();
			FlameParticle flameParticle = new FlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			flameParticle.pickSprite(this.sprite);
			flameParticle.yd = ySpeed * 0.05D;
			flameParticle.xd = (0.5D - random.nextDouble()) * 0.05D;
			flameParticle.zd = (0.5D - random.nextDouble()) * 0.05D;
			flameParticle.setLifetime((int)(8D / (Math.random() * 0.8D + 0.2D)));
			return flameParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class UnderwaterAshProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public UnderwaterAshProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SuspendedParticle suspendedParticle = new SuspendedParticle(level, this.sprite, x, y, z);
			float color = level.random.nextFloat() * 0.3F;
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
