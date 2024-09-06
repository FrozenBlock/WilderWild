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

package net.frozenblock.wilderwild.particle.options;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LeafClusterParticleOptions implements ParticleOptions {
	public static final MapCodec<LeafClusterParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
				ResourceLocation.CODEC.fieldOf("spawned_particle").forGetter(LeafClusterParticleOptions::getParticleId)
			)
			.apply(instance, LeafClusterParticleOptions::createCodecParticleOptions)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, LeafClusterParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, LeafClusterParticleOptions::getParticleId,
		LeafClusterParticleOptions::createCodecParticleOptions
	);

	private final ParticleType<LeafParticleOptions> particleType;
	private final ResourceLocation particleId;

	@NotNull
	public static LeafClusterParticleOptions create(ParticleType<LeafParticleOptions> type) {
		return new LeafClusterParticleOptions(type);
	}

	private @NotNull static LeafClusterParticleOptions createCodecParticleOptions(
		ResourceLocation particleId
	) {
		ParticleType<LeafParticleOptions> particleType;
		if (BuiltInRegistries.PARTICLE_TYPE.containsKey(particleId)) {
			particleType = (ParticleType<LeafParticleOptions>) BuiltInRegistries.PARTICLE_TYPE.get(particleId);
		} else {
			particleType = WWParticleTypes.OAK_LEAVES;
		}
		return new LeafClusterParticleOptions(particleType);
	}

	private LeafClusterParticleOptions(
		ParticleType<LeafParticleOptions> type
	) {
		this.particleType = type;
		this.particleId = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return WWParticleTypes.LEAF_CLUSTER_SPAWNER;
	}

	public ParticleType<LeafParticleOptions> getSpawnedParticleType() {
		return this.particleType;
	}

	public ResourceLocation getParticleId() {
		return this.particleId;
	}
}
