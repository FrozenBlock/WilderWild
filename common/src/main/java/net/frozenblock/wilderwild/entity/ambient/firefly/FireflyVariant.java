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

package net.frozenblock.wilderwild.entity.ambient.firefly;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.codec.RegistryFixedCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.ChatFormatting;

public record FireflyVariant(
	ClientAsset.ResourceTexture resourceTexture,
	SpawnPrioritySelectors spawnConditions,
	String name,
	Optional<DyeColor> dyeColor
) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<FireflyVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(FireflyVariant::resourceTexture),
		SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(FireflyVariant::spawnConditions),
		Codec.STRING.fieldOf("name").forGetter(FireflyVariant::name),
		DyeColor.CODEC.optionalFieldOf("dye_color").forGetter(fireflyColor -> fireflyColor.dyeColor)
	).apply(instance, FireflyVariant::new));
	public static final Codec<FireflyVariant> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(FireflyVariant::resourceTexture),
		Codec.STRING.fieldOf("name").forGetter(FireflyVariant::name),
		DyeColor.CODEC.optionalFieldOf("dye_color").forGetter(fireflyColor -> fireflyColor.dyeColor)
	).apply(instance, FireflyVariant::new));

	public static final Codec<Holder<FireflyVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.FIREFLY_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FireflyVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.FIREFLY_VARIANT);

	private static final ChatFormatting[] CHAT_FORMATTINGS = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
	public static final TooltipProvider.Getter<Holder<FireflyVariant>> TOOLTIP = component ->
		(context, consumer, flag, getter) -> {
			if (component.value().name.equals("on")) return;
			consumer.accept(Component.translatable("entity.wilderwild.firefly.variant." + component.value().name).withStyle(CHAT_FORMATTINGS));
		};

	private FireflyVariant(ClientAsset.ResourceTexture resourceTexture, String name, Optional<DyeColor> dyeColor) {
		this(resourceTexture, SpawnPrioritySelectors.EMPTY, name, dyeColor);
	}

	public static Optional<Holder<FireflyVariant>> getByDyeColor(RegistryAccess registryAccess, DyeColor dyeColor) {
		for (Holder<FireflyVariant> fireflyColor : registryAccess.lookupOrThrow(WilderWildRegistries.FIREFLY_VARIANT).holders()) {
			if (fireflyColor.value().dyeColor().orElse(null) == dyeColor) return Optional.of(fireflyColor);
		}

		return Optional.empty();
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
