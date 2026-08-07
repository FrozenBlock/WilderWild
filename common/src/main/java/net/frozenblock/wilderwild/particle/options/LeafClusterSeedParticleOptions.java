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

package net.frozenblock.wilderwild.particle.options;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;

public record LeafClusterSeedParticleOptions(Holder<FallingLeafData> fallingLeafData) implements ParticleOptions {
	public static final MapCodec<LeafClusterSeedParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		RegistryFixedCodec.create(WilderWildRegistries.FALLING_LEAF).fieldOf("falling_leaf").forGetter(LeafClusterSeedParticleOptions::fallingLeafData)
	).apply(instance, LeafClusterSeedParticleOptions::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, LeafClusterSeedParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.holderRegistry(WilderWildRegistries.FALLING_LEAF), LeafClusterSeedParticleOptions::fallingLeafData,
		LeafClusterSeedParticleOptions::new
	);

	@Override
	public ParticleType<?> getType() {
		return WWParticleTypes.LEAF_CLUSTER_SPAWNER.get();
	}
}
