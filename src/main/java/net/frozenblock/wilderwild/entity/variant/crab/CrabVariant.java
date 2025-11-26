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

package net.frozenblock.wilderwild.entity.variant.crab;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public record CrabVariant(
	ClientAsset.ResourceTexture resourceTexture, ClientAsset.ResourceTexture mojangResourceTexture, SpawnPrioritySelectors spawnConditions
) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<CrabVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(CrabVariant::resourceTexture),
			ClientAsset.ResourceTexture.CODEC.fieldOf("mojang_asset_id").forGetter(CrabVariant::mojangResourceTexture),
			SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(CrabVariant::spawnConditions)
		).apply(instance, CrabVariant::new)
	);
	public static final Codec<CrabVariant> NETWORK_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(CrabVariant::resourceTexture),
			ClientAsset.ResourceTexture.CODEC.fieldOf("mojang_asset_id").forGetter(CrabVariant::mojangResourceTexture)
		).apply(instance, CrabVariant::new)
	);
	public static final Codec<Holder<CrabVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.CRAB_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CrabVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.CRAB_VARIANT);

	private CrabVariant(ClientAsset.ResourceTexture resourceTexture, ClientAsset.ResourceTexture mojangResourceTexture) {
		this(resourceTexture, mojangResourceTexture, SpawnPrioritySelectors.EMPTY);
	}

	@Override
	public ClientAsset.ResourceTexture resourceTexture() {
		return this.resourceTexture;
	}

	@Override
	public ClientAsset.ResourceTexture mojangResourceTexture() {
		return this.mojangResourceTexture;
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
