/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.particle.options.LeafClusterSeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;

@ClientOnly
public class LeafClusterSeedParticle extends NoRenderParticle {
	private final FallingLeafData fallingLeafData;
	private final BlockPos pos;

	LeafClusterSeedParticle(ClientLevel level, double x, double y, double z, FallingLeafData fallingLeafData) {
		super(level, x, y, z, 0D, 0D, 0D);
		this.fallingLeafData = fallingLeafData;
		this.pos = BlockPos.containing(x, y, z);
		this.lifetime = 5;
	}

	@Override
	public void tick() {
		if (!this.level.getBlockState(this.pos).is(this.fallingLeafData.leavesBlock())) {
			this.remove();
			return;
		}

		final Optional<FallingLeafData.ParticleData> particleData = this.fallingLeafData.leafLitterParticleData().or(this.fallingLeafData::leafParticleData);
		if (particleData.isEmpty()) {
			this.remove();
			return;
		}

		final int leafCount = this.random.nextInt(4) + 1;
		for (int i = 0; i < leafCount; i++) {
			final WWFallingLeavesParticleOptions options = WWFallingLeavesParticleOptions.createFastFalling(
				particleData.get().particle(),
				particleData.get().originBlock(),
				particleData.get().textureSize()
			);
			ParticleUtils.spawnParticleBelow(this.level, this.pos, this.random, options);
		}

		this.age++;
		if (this.age == this.lifetime) this.remove();
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<LeafClusterSeedParticleOptions> {
		@Override
		public Particle createParticle(
			LeafClusterSeedParticleOptions options,
			ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new LeafClusterSeedParticle(level, x, y, z, options.fallingLeafData().value());
		}
	}
}
