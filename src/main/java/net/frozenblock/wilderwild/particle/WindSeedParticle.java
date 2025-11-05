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
import net.frozenblock.wilderwild.particle.options.WindSeedParticleOptions;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WindSeedParticle extends NoRenderParticle {
	private final BlockPos pos;
	private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
	private final int timeBetweenSpawns;

	WindSeedParticle(ClientLevel level, double x, double y, double z, int timeBetweenSpawns, int spawnAttempts) {
		super(level, x, y, z, 0D, 0D, 0D);
		this.pos = BlockPos.containing(x, y, z);
		this.timeBetweenSpawns = timeBetweenSpawns;
		this.lifetime = timeBetweenSpawns * spawnAttempts;
	}

	@Override
	public void tick() {
		this.age++;
		if (this.age >= this.lifetime) this.remove();

		if ((this.age & this.timeBetweenSpawns) == 0) {
			WWClientWindManager.spawnAmbientWindParticles(
				this.level,
				this.pos.getX(), this.pos.getY(), this.pos.getZ(),
				2,
				this.random,
				mutablePos,
				0.75F,
				false
			);
		}
	}

	public record Provider(@NotNull SpriteSet spriteProvider) implements ParticleProvider<WindSeedParticleOptions> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull WindSeedParticleOptions options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new WindSeedParticle(level, x, y, z, options.timeBetweenSpawns(), options.spawnAttempts());
		}
	}
}
