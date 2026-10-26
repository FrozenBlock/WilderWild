/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.entity.monster.skeleton;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.core.registries.codec.RegistryFixedCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public record SunkenVariant(
	ClientAsset.ResourceTexture texture,
	Optional<Holder<SunkenVariant>> driedVariant,
	Optional<Holder<SunkenVariant>> shearedVariant,
	SpawnPrioritySelectors spawnConditions
) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<SunkenVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(SunkenVariant::texture),
		RegistryCodecs.holder(WilderWildRegistries.SUNKEN_VARIANT).optionalFieldOf("dried_variant").forGetter(SunkenVariant::driedVariant),
		RegistryCodecs.holder(WilderWildRegistries.SUNKEN_VARIANT).optionalFieldOf("sheared_variant").forGetter(SunkenVariant::shearedVariant),
		SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(SunkenVariant::spawnConditions)
	).apply(instance, SunkenVariant::new));
	// For now I'm keeping shearedVariant, as I'm pretty sure omitting it will cause arm swing desyncs between client & server.
	public static final Codec<SunkenVariant> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(SunkenVariant::texture),
		RegistryCodecs.holder(WilderWildRegistries.SUNKEN_VARIANT).optionalFieldOf("sheared_variant").forGetter(SunkenVariant::shearedVariant)
	).apply(instance, SunkenVariant::new));

	public static final Codec<Holder<SunkenVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.SUNKEN_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SunkenVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.SUNKEN_VARIANT);

	private SunkenVariant(ClientAsset.ResourceTexture texture, Optional<Holder<SunkenVariant>> shearedVariant) {
		this(texture, Optional.empty(), shearedVariant, SpawnPrioritySelectors.EMPTY);
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
