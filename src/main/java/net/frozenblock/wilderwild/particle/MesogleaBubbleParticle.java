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
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class MesogleaBubbleParticle extends SingleQuadParticle {
	private final ParticleOptions popParticle;

	protected MesogleaBubbleParticle(
		ClientLevel level,
		double x, double y, double z,
		double xd, double yd, double zd,
		ParticleOptions popParticle,
		TextureAtlasSprite sprite
	) {
		super(level, x, y, z, xd, yd, zd, sprite);
		this.popParticle = popParticle;
		this.gravity = -0.125F;
		this.friction = 0.85F;
		this.setSize(0.02F, 0.02F);
		this.quadSize = this.quadSize * (this.random.nextFloat() * 0.6F + 0.2F);
		this.xd = xd * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.yd = yd * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.zd = zd * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.lifetime = (int) (40D / (Math.random() * 0.8 + 0.2) * (this.yd < 0 ? 0.5D : 1D));
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.removed && !this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) this.age += 1;
	}

	@Override
	public void remove() {
		this.level.playLocalSound(
			this.x, this.y, this.z,
			WWSounds.PARTICLE_MESOGLEA_BUBBLE_POP,
			SoundSource.NEUTRAL,
			0.05F,
			this.random.nextFloat() * 0.2F + 0.8F,
			false
		);
		this.level.addParticle(
			this.popParticle,
			this.x,
			this.y,
			this.z,
			this.xd,
			this.yd,
			this.zd
		);
		super.remove();
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	public record Provider(SpriteSet spriteSet, ParticleOptions popParticle) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			SimpleParticleType options,
			ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new MesogleaBubbleParticle(level, x, y, z, xd, yd, zd, this.popParticle, this.spriteSet.get(random));
		}
	}
}
