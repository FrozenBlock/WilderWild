/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class MesogleaSplashParticle extends WaterDropParticle {

	public MesogleaSplashParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
		super(clientLevel, d, e, f);
		this.gravity = 0.04F;
		if (h == 0D && (g != 0D || i != 0D)) {
			this.xd = g;
			this.yd = 0.1D;
			this.zd = i;
		}
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Environment(EnvType.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public Provider(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaSplashParticle mesogleaSplashParticle = new MesogleaSplashParticle(clientLevel, d, e, f, g, h, i);
			mesogleaSplashParticle.pickSprite(this.spriteProvider);
			return mesogleaSplashParticle;
		}
	}
}

