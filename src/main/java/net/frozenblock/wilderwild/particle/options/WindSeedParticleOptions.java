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

package net.frozenblock.wilderwild.particle.options;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

public class WindSeedParticleOptions implements ParticleOptions {
	public static final MapCodec<WindSeedParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			ExtraCodecs.POSITIVE_INT.fieldOf("time_between_spawns").forGetter(WindSeedParticleOptions::timeBetweenSpawns),
			ExtraCodecs.POSITIVE_INT.fieldOf("spawn_attempts").forGetter(WindSeedParticleOptions::spawnAttempts)
		).apply(instance, WindSeedParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, WindSeedParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.VAR_INT, WindSeedParticleOptions::timeBetweenSpawns,
		ByteBufCodecs.VAR_INT, WindSeedParticleOptions::spawnAttempts,
		WindSeedParticleOptions::new
	);

	private final int timeBetweenSpawns;
	private final int spawnAttempts;

	public WindSeedParticleOptions(int timeBetweenSpawns, int spawnAttempts) {
		this.timeBetweenSpawns = timeBetweenSpawns;
		this.spawnAttempts = spawnAttempts;
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return WWParticleTypes.WIND_SPAWNER;
	}

	public int timeBetweenSpawns() {
		return this.timeBetweenSpawns;
	}

	public int spawnAttempts() {
		return this.spawnAttempts;
	}

}
