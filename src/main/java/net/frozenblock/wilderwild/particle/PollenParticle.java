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
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PollenParticle extends TextureSheetParticle {
	public static final double WIND_INTENSITY = 0.2D;
	private float prevScale = 0F;
	private float scale = 0F;
	private float targetScale = 0F;

	PollenParticle(@NotNull ClientLevel level, @NotNull SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
		this.setSize(0.01F, 0.02F);
		this.pickSprite(spriteProvider);
		this.quadSize *= (this.random.nextFloat() * 0.6F + 0.6F) * 0.5F;
		this.lifetime = (int) (16D / (AdvancedMath.random().nextDouble() * 0.8D + 0.2D));
		this.hasPhysics = true;
		this.friction = 1F;
		this.gravity = 0F;
	}

	@Override
	public void tick() {
		if (WWBlockConfig.Client.POLLEN_ENABLED) {
			BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
			boolean rain = this.level.isRainingAt(blockPos);
			if (rain) {
				this.gravity = 0.06F;
			}
			this.xo = this.x;
			this.yo = this.y;
			this.zo = this.z;
			this.yd -= 0.04 * (double) this.gravity;
			this.move(this.xd, this.yd, this.zd);
			if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
				this.xd *= 1.1D;
				this.zd *= 1.1D;
			}
			this.xd *= this.friction;
			this.yd *= this.friction;
			this.zd *= this.friction;
			if (this.onGround) {
				this.xd *= 0.7D;
				this.zd *= 0.7D;
			}
			this.prevScale = this.scale;
			this.scale += (this.targetScale - this.scale) * 0.15F;
			FluidState fluidState = this.level.getFluidState(blockPos);
			if (blockPos.getY() + fluidState.getHeight(this.level, blockPos) >= this.y) {
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
				if (this.x == this.xo && this.y == this.yo && this.z == this.zo) {
					this.age += 5;
				}
			}
			boolean onGround = this.onGround;
			if (!rain) {
				double multXZ = (onGround ? 0.00025D : 0.0035D) * WIND_INTENSITY;
				double multY = (onGround ? 0.00025D : 0.00175D) * WIND_INTENSITY;
				Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
					.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
				this.xd += wind.x() * multXZ;
				this.yd += (wind.y() + 0.1D) * multY;
				this.zd += wind.z() * multXZ;
			}
		} else {
			this.remove();
		}
	}

	@Override
	public float getQuadSize(float partialTicks) {
		return this.quadSize * Mth.lerp(partialTicks, this.prevScale, this.scale);
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Environment(EnvType.CLIENT)
	public record PollenFactory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
			PollenParticle pollenParticle = new PollenParticle(clientLevel, this.spriteProvider, x, y, z, 0D, -0.800000011920929D, 0D);
			pollenParticle.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
			pollenParticle.gravity = 0.01F;
			pollenParticle.setColor(250F / 255F, 171F / 255F, 28F / 255F);
			return pollenParticle;
		}
	}
}
