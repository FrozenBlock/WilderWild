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
import net.frozenblock.lib.math.api.AdvancedMath;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PlanktonParticle extends TextureSheetParticle {
	private static final float MAX_R = 216F;
	private static final float MIN_R = 160F;
	private static final float MAX_G = 247F;
	private static final float MIN_G = 217F;
	private static final float MAX_B = 255F;
	private static final float MIN_B = 249F;
	private static final float MAX_R_GLOWING = 136F;
	private static final float MIN_R_GLOWING = 28F;
	private static final float MAX_G_GLOWING = 213F;
	private static final float MIN_G_GLOWING = 111F;
	private static final float B_GLOWING = 255F;

	private float prevScale = 0F;
	private float scale = 0F;
	private float targetScale = 0F;
	public float rotSpeed;
	private final float spinAcceleration;
	private boolean glowing = false;
	private float baseR = 0F;
	private float baseG = 0F;
	private float baseB = 0F;
	private float glowingR = 0F;
	private float glowingG = 0F;
	private float glowingB = 0F;

	PlanktonParticle(@NotNull ClientLevel level, @NotNull SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
		this.setSize(0.01F, 0.02F);
		this.pickSprite(spriteProvider);
		this.quadSize *= (this.random.nextFloat() * 0.6F + 0.6F) * 0.5F;
		this.lifetime = (int) (16D / (AdvancedMath.random().nextDouble() * 0.8D + 0.2D));
		this.gravity = 0F;
		this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30F : 30F);
		this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5F : 5F);
		this.roll = this.random.nextFloat() * Mth.TWO_PI;
		this.oRoll = this.roll;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.yd -= 0.04D * (double) this.gravity;
		this.move(this.xd, this.yd, this.zd);
		this.xd *= this.friction;
		this.yd *= this.friction;
		this.zd *= this.friction;
		if (this.onGround) {
			this.xd *= 0.7D;
			this.zd *= 0.7D;
		}

		this.prevScale = this.scale;
		this.scale += (this.targetScale - this.scale) * 0.1F;
		this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20F;
		this.oRoll = this.roll;
		this.roll = this.roll + this.rotSpeed / 20F;

		BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
		FluidState fluidState = this.level.getFluidState(blockPos);
		if (blockPos.getY() + fluidState.getHeight(this.level, blockPos) < this.y) {
			this.lifetime = this.age;
		}

		if (this.age++ >= this.lifetime) {
			if (this.prevScale == 0F) {
				this.remove();
			} else {
				this.targetScale = 0F;
				if (this.prevScale <= 0.04F) {
					this.scale = 0F;
				}
			}
		} else {
			this.targetScale = 1F;
		}
	}

	@Override
	public float getQuadSize(float partialTicks) {
		float scale = Mth.lerp(partialTicks, this.prevScale, this.scale);
		if (this.glowing) {
			this.rCol = Mth.lerp(scale, this.baseR, this.glowingR);
			this.gCol = Mth.lerp(scale, this.baseG, this.glowingG);
			this.bCol = Mth.lerp(scale, this.baseB, this.glowingB);
			this.alpha = scale;
		} else {
			this.alpha = 0.75F;
		}
		return this.quadSize * scale;
	}

	public void setBaseColors(float r, float g, float b) {
		this.baseR = r / 255F;
		this.baseG = g / 255F;
		this.baseB = b / 255F;
		this.rCol = this.baseR;
		this.gCol = this.baseG;
		this.bCol = this.baseB;
	}

	public void setGlowingColors(float r, float g, float b) {
		this.glowingR = r / 255F;
		this.glowingG = g / 255F;
		this.glowingB = b / 255F;
	}

	@Override
	protected int getLightColor(float partialTicks) {
		if (this.glowing) return (int) (Mth.lerp(partialTicks, this.prevScale, this.scale) * 240F);
		return super.getLightColor(partialTicks);
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public record PlanktonProvider(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
			PlanktonParticle planktonParticle = new PlanktonParticle(clientLevel, this.spriteProvider, x, y, z, 0D, 0D, 0D);
			RandomSource random = clientLevel.random;
			planktonParticle.xd = 0D;
			planktonParticle.yd = -0.02D;
			planktonParticle.zd = 0D;
			planktonParticle.lifetime = Mth.randomBetweenInclusive(random, 100, 400);
			planktonParticle.gravity = 0F;
			planktonParticle.setBaseColors(
				MIN_R + (random.nextFloat() * (MAX_R - MIN_R)),
				MIN_G + (random.nextFloat() * (MAX_G - MIN_G)),
				MIN_B + (random.nextFloat() * (MAX_B - MIN_B))
			);
			return planktonParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public record GlowingProvider(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
			PlanktonParticle planktonParticle = new PlanktonParticle(clientLevel, this.spriteProvider, x, y, z, 0D, 0D, 0D);
			RandomSource random = clientLevel.random;
			planktonParticle.xd = 0D;
			planktonParticle.yd = -0.02D;
			planktonParticle.zd = 0D;
			planktonParticle.lifetime = Mth.randomBetweenInclusive(random, 140, 400);
			planktonParticle.gravity = 0F;
			planktonParticle.setBaseColors(
				MIN_R + (random.nextFloat() * (MAX_R - MIN_R)),
				MIN_G + (random.nextFloat() * (MAX_G - MIN_G)),
				MIN_B + (random.nextFloat() * (MAX_B - MIN_B))
			);
			planktonParticle.setGlowingColors(
				MIN_R_GLOWING + (random.nextFloat() * (MAX_R_GLOWING - MIN_R_GLOWING)),
				MIN_G_GLOWING + (random.nextFloat() * (MAX_G_GLOWING - MIN_G_GLOWING)),
				B_GLOWING
			);
			planktonParticle.glowing = true;
			return planktonParticle;
		}
	}
}
