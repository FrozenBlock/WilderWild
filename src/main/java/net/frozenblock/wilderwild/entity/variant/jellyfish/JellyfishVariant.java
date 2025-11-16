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

package net.frozenblock.wilderwild.entity.variant.jellyfish;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.Item;

public record JellyfishVariant(
	ClientAsset.ResourceTexture resourceTexture, boolean pearlescent, SpawnPrioritySelectors spawnConditions, HolderSet<Item> reproductionFood
) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<JellyfishVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(JellyfishVariant::resourceTexture),
			Codec.BOOL.fieldOf("pearlescent").forGetter(JellyfishVariant::pearlescent),
			SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(JellyfishVariant::spawnConditions),
			RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("reproduction_food").forGetter(JellyfishVariant::reproductionFood)
		).apply(instance, JellyfishVariant::new)
	);
	public static final Codec<JellyfishVariant> NETWORK_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(JellyfishVariant::resourceTexture),
			Codec.BOOL.fieldOf("pearlescent").forGetter(JellyfishVariant::pearlescent),
			RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("reproduction_food").forGetter(JellyfishVariant::reproductionFood)
		).apply(instance, JellyfishVariant::new)
	);
	public static final Codec<Holder<JellyfishVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.JELLYFISH_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<JellyfishVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.JELLYFISH_VARIANT);

	private JellyfishVariant(ClientAsset.ResourceTexture resourceTexture, boolean pearlescent, HolderSet<Item> reproductionFood) {
		this(resourceTexture, pearlescent, SpawnPrioritySelectors.EMPTY, reproductionFood);
	}

	@Override
	public ClientAsset.ResourceTexture resourceTexture() {
		return this.resourceTexture;
	}


	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
