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

import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class LeafClusterSeedParticle extends NoRenderParticle {
	private final BlockPos pos;

	LeafClusterSeedParticle(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z, 0D, 0D, 0D);
		this.lifetime = 5;
		this.pos = BlockPos.containing(x, y, z);
	}

	@Override
	public void tick() {
		final Optional<FallingLeafUtil.FallingLeafData> fallingLeafData = FallingLeafUtil.getFallingLeafData(this.level.getBlockState(this.pos).getBlock());
		if (fallingLeafData.isEmpty()) {
			this.remove();
			return;
		}

		final ParticleType<WWFallingLeavesParticleOptions> particle = fallingLeafData.get().particle();
		final int leafCount = this.random.nextInt(4) + 1;
		for (int i = 0; i < leafCount; i++) {
			final FallingLeafUtil.LeafParticleData leafParticleData = FallingLeafUtil.getLitterOrLeafParticleData(particle);
			final WWFallingLeavesParticleOptions options = WWFallingLeavesParticleOptions.createFastFalling(particle, leafParticleData.textureSize(), true);
			ParticleUtils.spawnParticleBelow(this.level, this.pos, this.random, options);
		}

		this.age++;
		if (this.age == this.lifetime) this.remove();
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			SimpleParticleType options,
			ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new LeafClusterSeedParticle(level, x, y, z);
		}
	}
}
