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

import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.particle.options.LeafParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ParticleUtils;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class LeafClusterSeedParticle extends NoRenderParticle {
	private final BlockPos pos;

	LeafClusterSeedParticle(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f, 0D, 0D, 0D);
		this.lifetime = 5;
		this.pos = BlockPos.containing(d, e, f);
	}

	@Override
	public void tick() {
		int leafCount = this.random.nextInt(4) + 1;
		Optional<FallingLeafUtil.FallingLeafData> fallingLeafData = FallingLeafUtil.getFallingLeafData(this.level.getBlockState(this.pos).getBlock());
		if (fallingLeafData.isPresent()) {
			ParticleType<LeafParticleOptions> particle = fallingLeafData.get().particle();
			for (int i = 0; i < leafCount; i++) {
				FallingLeafUtil.LeafParticleData leafParticleData = FallingLeafUtil.getLeafParticleData(particle);
				LeafParticleOptions leafParticleOptions = LeafParticleOptions.createFastFalling(particle, leafParticleData.textureSize());
				ParticleUtils.spawnParticleBelow(this.level, this.pos, this.random, leafParticleOptions);
			}
		} else {
			this.remove();
			return;
		}
		this.age++;
		if (this.age == this.lifetime) {
			this.remove();
		}
	}

	@Environment(EnvType.CLIENT)
	public record Factory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SimpleParticleType options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new LeafClusterSeedParticle(level, x, y, z);
		}
	}
}
