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

package net.frozenblock.wilderwild.entity.variant.butterfly;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.ChatFormatting;

public record ButterflyVariant(
	ClientAsset.ResourceTexture resourceTexture, SpawnPrioritySelectors spawnConditions, String name
) implements TooltipProvider, PriorityProvider<SpawnContext, SpawnCondition> {
	private static final ChatFormatting[] CHAT_FORMATTINGS = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
	public static final Codec<ButterflyVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(ButterflyVariant::resourceTexture),
			SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(ButterflyVariant::spawnConditions),
			Codec.STRING.fieldOf("name").forGetter(ButterflyVariant::name)
		).apply(instance, ButterflyVariant::new)
	);
	public static final Codec<ButterflyVariant> NETWORK_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(ButterflyVariant::resourceTexture),
			Codec.STRING.fieldOf("name").forGetter(ButterflyVariant::name)
		).apply(instance, ButterflyVariant::new)
	);
	public static final Codec<Holder<ButterflyVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.BUTTERFLY_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ButterflyVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.BUTTERFLY_VARIANT);

	public ButterflyVariant(ClientAsset.ResourceTexture resourceTexture, SpawnPrioritySelectors spawnConditions, String name) {
		this.resourceTexture = resourceTexture;
		this.spawnConditions = spawnConditions;
		this.name = name;
	}

	private ButterflyVariant(ClientAsset.ResourceTexture texture, String name) {
		this(texture, SpawnPrioritySelectors.EMPTY, name);
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter getter) {
		consumer.accept(Component.translatable("entity.wilderwild.butterfly.variant." + this.name).withStyle(CHAT_FORMATTINGS));
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
