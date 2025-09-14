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

package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FallingParticle extends SingleQuadParticle {
	private final SpriteSet spriteSet;

	FallingParticle(@NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NotNull SpriteSet spriteSet) {
		this(level, x, y, z, spriteSet);
		this.xd *= 0.1D;
		this.yd *= 0.1D;
		this.zd *= 0.1D;
		this.xd += xSpeed;
		this.yd += ySpeed;
		this.zd += zSpeed;
		this.lifetime = level.random.nextInt(4, 8);
	}

	public FallingParticle(@NotNull ClientLevel level, double x, double y, double z, @NotNull SpriteSet spriteSet) {
		super(level, x, y, z, 0D, 0D, 0D, spriteSet.first());
		this.spriteSet = spriteSet;
		this.setSpriteFromAge(spriteSet);
		this.gravity = 1F;
		this.quadSize = 0.2F;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.onGround) {
			this.remove();
			return;
		}
		this.setSpriteFromAge(this.spriteSet);
	}

	@Override
	protected @NotNull Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(
			@NotNull SimpleParticleType particleOptions,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallingParticle(level, x, y, z, xd, yd, zd, this.spriteSet);
		}
	}
}

