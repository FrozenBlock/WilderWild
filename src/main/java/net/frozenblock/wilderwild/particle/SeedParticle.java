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
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SeedParticle extends TextureSheetParticle {
	public double windIntensity = 0.5D;

	SeedParticle(@NotNull ClientLevel level, @NotNull SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
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
		super.tick();
		BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
		FluidState fluidState = this.level.getBlockState(blockPos).getFluidState();
		if (!fluidState.isEmpty() && (fluidState.getHeight(this.level, blockPos) + (float) blockPos.getY()) >= this.y) {
			return;
		}
		double multXZ = (this.onGround ? 0.00025D : 0.0035D) * this.windIntensity;
		double multY = (this.onGround ? 0.00025D : 0.00175D) * this.windIntensity;
		Vec3 wind = ClientWindManager.getWindMovement(this.level,new Vec3(this.x, this.y, this.z), 1D, 7D, 5D).scale(AmbienceAndMiscConfig.get().getParticleWindIntensity());
		this.xd += wind.x() * multXZ;
		this.yd += (wind.y() + 0.1D) * multY;
		this.zd += wind.z() * multXZ;
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Environment(EnvType.CLIENT)
	public record Factory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SeedParticleOptions> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SeedParticleOptions options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			RandomSource random = level.getRandom();
			Vec3 controlledVelocity = options.getVelocity();
			double windex = options.isControlled() ? controlledVelocity.x * 1.1D : ClientWindManager.getWindX(1F) * 1.1D;
			double windZ = options.isControlled() ? controlledVelocity.z * 1.1D : ClientWindManager.getWindZ(1F) * 1.1D;
			SeedParticle seedParticle = new SeedParticle(level, this.spriteProvider, x, y, z, windex, -0.800000011920929D, windZ);
			seedParticle.lifetime = Mth.randomBetweenInclusive(random, 500, 1000);
			seedParticle.gravity = 0.01F;
			seedParticle.xd = (windex + random.triangle(0D, 0.8D)) / 17D;
			seedParticle.zd = (windZ + random.triangle(0D, 0.8D)) / 17D;
			seedParticle.yd = options.isControlled() ? controlledVelocity.y / 17D : seedParticle.yd;
			seedParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			return seedParticle;
		}
	}

}
