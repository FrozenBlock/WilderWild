/*
 * Copyright 2022-2023 FrozenBlock
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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FallingParticle extends TextureSheetParticle {
	private final SpriteSet spriteProvider;

	FallingParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteProvider) {
		this(level, x, y, z, spriteProvider);
		this.xd *= 0.1f;
		this.yd *= 0.1f;
		this.zd *= 0.1f;
		this.xd += xSpeed;
		this.yd += ySpeed;
		this.zd += zSpeed;
		this.lifetime = level.random.nextInt(4, 8);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.onGround) {
			this.remove();
			return;
		}
		this.setSpriteFromAge(this.spriteProvider);
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public FallingParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteProvider) {
		super(level, x, y, z, 0.0, 0.0, 0.0);
		this.spriteProvider = spriteProvider;
		this.setSpriteFromAge(spriteProvider);
		this.gravity = 1.0f;
		this.quadSize = 0.2F;
	}

	@Environment(EnvType.CLIENT)
	public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(@NotNull SimpleParticleType termiteParticleOptions, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
			return new FallingParticle(clientLevel, x, y, z, g, h, i, this.spriteProvider);
		}
	}
}

