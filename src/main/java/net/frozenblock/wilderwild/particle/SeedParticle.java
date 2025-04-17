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
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
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

	SeedParticle(@NotNull ClientLevel level, @NotNull SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
		this.setSize(0.01F, 0.02F);
		this.pickSprite(spriteProvider);
		this.quadSize *= (this.random.nextFloat() * 0.6F + 0.6F) * 0.5F;
		this.hasPhysics = true;
		this.friction = 1F;
		this.gravity = 0F;
	}

	private double windIntensity = 0.5D;

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
			this.windIntensity = 0.125D;
			this.age += 2;
			return;
		}
		double multXZ = (this.onGround ? 0.00025D : 0.0035D) * this.windIntensity;
		double multY = (this.onGround ? 0.00025D : 0.00175D) * this.windIntensity;
		Vec3 wind = ClientWindManager.getWindMovement(this.level,new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
		this.xd += wind.x() * multXZ;
		this.yd += Math.max(((wind.y() * 0.4D) + 0.1D), 0.1D) * multY;
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
			SeedParticle seedParticle = new SeedParticle(level, this.spriteProvider, x, y, z, windex, 0.3D, windZ);
			seedParticle.lifetime = Mth.randomBetweenInclusive(random, 200, 500);
			seedParticle.gravity = 0.01F;
			seedParticle.xd = (windex + random.triangle(0D, 0.8D)) / 17D;
			seedParticle.zd = (windZ + random.triangle(0D, 0.8D)) / 17D;
			seedParticle.yd = options.isControlled() ? controlledVelocity.y / 17D : 0D;
			seedParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			return seedParticle;
		}
	}

}
