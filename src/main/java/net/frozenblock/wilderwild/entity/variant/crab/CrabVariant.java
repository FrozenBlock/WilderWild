/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import org.jetbrains.annotations.NotNull;

public final class CrabVariant implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<CrabVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.DEFAULT_FIELD_CODEC.forGetter(CrabVariant::assetInfo),
			SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(CrabVariant::spawnConditions)
		).apply(instance, CrabVariant::new)
	);
	public static final Codec<CrabVariant> NETWORK_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.DEFAULT_FIELD_CODEC.forGetter(CrabVariant::assetInfo)
		).apply(instance, CrabVariant::new)
	);
	public static final Codec<Holder<CrabVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.CRAB_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CrabVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.CRAB_VARIANT);

	private final ClientAsset clientAsset;
	private final SpawnPrioritySelectors spawnConditions;


	public CrabVariant(ClientAsset clientAsset, SpawnPrioritySelectors spawnConditions) {
		this.clientAsset = clientAsset;
		this.spawnConditions = spawnConditions;
	}

	private CrabVariant(ClientAsset clientAsset) {
		this(clientAsset, SpawnPrioritySelectors.EMPTY);
	}

	@NotNull
	public ClientAsset assetInfo() {
		return this.clientAsset;
	}

	public SpawnPrioritySelectors spawnConditions() {
		return this.spawnConditions;
	}

	@Override
	public @NotNull List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}

}
