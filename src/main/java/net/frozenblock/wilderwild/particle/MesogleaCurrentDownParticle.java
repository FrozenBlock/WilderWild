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
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class MesogleaCurrentDownParticle extends TextureSheetParticle {
	private final ParticleOptions popParticle;
	private float angle;

	protected MesogleaCurrentDownParticle(
		@NotNull ClientLevel clientLevel,
		double x,
		double y,
		double z,
		double xd,
		double yd,
		double zd,
		ParticleOptions popParticle
	) {
		super(clientLevel, x, y, z, xd, yd, zd);
		this.popParticle = popParticle;
		this.lifetime = (int)(Math.random() * 60.0) + 30;
		this.hasPhysics = false;
		this.xd = 0.0;
		this.yd = -0.05;
		this.zd = 0.0;
		this.setSize(0.02F, 0.02F);
		this.quadSize = this.quadSize * (this.random.nextFloat() * 0.6F + 0.2F);
		this.gravity = 0.002F;
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.xd = this.xd + (0.6D * Mth.cos(this.angle));
			this.zd = this.zd + (0.6D * Mth.sin(this.angle));
			this.xd *= 0.07;
			this.zd *= 0.07;
			this.move(this.xd, this.yd, this.zd);
			if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER) || this.onGround) {
				this.age += 1;
			}

			this.angle += 0.08F;
		}
	}

	@Override
	public void remove() {
		this.level.playSound(
			Minecraft.getInstance().player,
			this.x,
			this.y,
			this.z,
			WWSounds.PARTICLE_MESOGLEA_BUBBLE_POP,
			SoundSource.NEUTRAL,
			0.025F,
			this.random.nextFloat() * 0.2F + 0.8F
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

	@Environment(EnvType.CLIENT)
	public static class BubbleFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;
		private final ParticleOptions popParticle;

		public BubbleFactory(SpriteSet spriteProvider, ParticleOptions popParticle) {
			this.spriteProvider = spriteProvider;
			this.popParticle = popParticle;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel clientLevel,
			double x,
			double y,
			double z,
			double xd,
			double yd,
			double zd
		) {
			MesogleaCurrentDownParticle bubble = new MesogleaCurrentDownParticle(clientLevel, x, y, z, xd, yd, zd, this.popParticle);
			bubble.pickSprite(this.spriteProvider);
			return bubble;
		}
	}

}
