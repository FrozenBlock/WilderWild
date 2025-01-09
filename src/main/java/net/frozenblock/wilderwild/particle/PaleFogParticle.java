/*
 * Copyright 2023-2025 FrozenBlock
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
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PaleFogParticle extends TextureSheetParticle {

	PaleFogParticle(
		@NotNull ClientLevel level,
		@NotNull SpriteSet spriteProvider,
		double x,
		double y,
		double z,
		double velocityX,
		double velocityY,
		double velocityZ,
		boolean large
	) {
		super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
		this.xd = 0D;
		this.yd = -0.0025D;
		this.zd = 0D;
		float width = large ? 0.1F : 0.01F;
		float height = large ? 0.2F : 0.02F;
		this.setSize(width, height);
		this.pickSprite(spriteProvider);
		this.lifetime = (int) (16D / (AdvancedMath.random().nextDouble() * 0.8D + 0.2D));
		this.hasPhysics = true;
		this.friction = 1F;
		this.gravity = 0F;
		this.quadSize *= 8F;
	}

	private double windIntensity = 0.025D;

	@Override
	public void tick() {
		super.tick();
		if (this.x == this.xo && this.y == this.yo && this.z == this.zo) {
			this.age += 5;
		}
		BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
		FluidState fluidState = this.level.getBlockState(blockPos).getFluidState();
		if (!fluidState.isEmpty() && (fluidState.getHeight(this.level, blockPos) + (float) blockPos.getY()) >= this.y) {
			this.yd = Math.clamp(this.yd + 0.003D, -0.01D, 0.025D);
			this.xd *= 0.7D;
			this.zd *= 0.7D;
			this.gravity = 0.05F;
			this.windIntensity = 0.025D;
			this.age += 2;
			return;
		}
		double multXZ = (this.onGround ? 0.00025D : 0.0035D) * this.windIntensity;
		Vec3 wind = ClientWindManager.getWindMovement(this.level,new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
		this.xd += wind.x() * multXZ;
		this.zd += wind.z() * multXZ;
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public record LargeFactory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(
			@NotNull SimpleParticleType options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
		) {
			RandomSource random = level.getRandom();
			PaleFogParticle seedParticle = new PaleFogParticle(level, this.spriteProvider, x, y, z, 0D, 0D, 0D, true);
			seedParticle.lifetime = Mth.randomBetweenInclusive(random, 500, 1000);
			seedParticle.gravity = 0.005F;
			return seedParticle;
		}
	}

}
