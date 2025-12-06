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
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class SeedParticle extends SingleQuadParticle {
	private double windIntensity = 0.5D;

	SeedParticle(
		ClientLevel level,
		double x, double y, double z,
		double xd, double yd, double zd,
		TextureAtlasSprite sprite
	) {
		super(level, x, y - 0.125D, z, xd, yd, zd, sprite);
		this.setSize(0.01F, 0.02F);
		this.quadSize *= (this.random.nextFloat() * 0.6F + 0.6F) * 0.5F;
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		this.hasPhysics = true;
		this.friction = 1F;
		this.gravity = 0.01F;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.x == this.xo && this.y == this.yo && this.z == this.zo) this.age += 5;
		final BlockPos pos = BlockPos.containing(this.x, this.y, this.z);
		final FluidState fluidState = this.level.getBlockState(pos).getFluidState();
		if (!fluidState.isEmpty() && (fluidState.getHeight(this.level, pos) + (float) pos.getY()) >= this.y) {
			this.yd = Math.clamp(this.yd + 0.003D, -0.01D, 0.025D);
			this.xd *= 0.7D;
			this.zd *= 0.7D;
			this.gravity = 0.05F;
			this.windIntensity = 0.125D;
			this.age += 2;
			return;
		}
		final double horizontalWindScale = (this.onGround ? 0.00025D : 0.0035D) * this.windIntensity;
		final double verticalWindScale = (this.onGround ? 0.00025D : 0.00175D) * this.windIntensity;
		final Vec3 wind = ClientWindManager.getWindMovement(this.level,new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
		this.xd += wind.x() * horizontalWindScale;
		this.yd += Math.max(((wind.y() * 0.4D) + 0.1D), 0.1D) * verticalWindScale;
		this.zd += wind.z() * horizontalWindScale;
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SeedParticleOptions> {
		@Override
		public Particle createParticle(
			SeedParticleOptions options,
			ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			final Vec3 controlledVelocity = options.velocity();
			final SeedParticle seedParticle = new SeedParticle(
				level,
				x, y, z,
				(((options.controlled() ? controlledVelocity.x : ClientWindManager.getWindX(1F)) * 1.1D) + random.triangle(0D, 0.8D)) / 17D,
				options.controlled() ? controlledVelocity.y / 17D : 0D,
				(((options.controlled() ? controlledVelocity.z : ClientWindManager.getWindZ(1F)) * 1.1D) + random.triangle(0D, 0.8D)) / 17D,
				this.spriteSet.get(random)
			);
			seedParticle.lifetime = Mth.randomBetweenInclusive(random, 200, 500);
			seedParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			return seedParticle;
		}
	}

}
