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

package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class AdditionalParticleFactories {
	public static class ScorchingEffectFlameFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public ScorchingEffectFlameFactory(SpriteSet sprites) {
			this.sprite = sprites;
		}

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
}
