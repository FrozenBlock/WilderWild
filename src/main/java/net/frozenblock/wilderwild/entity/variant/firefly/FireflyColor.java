/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.variant.firefly;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.ChatFormatting;
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
import org.jetbrains.annotations.NotNull;

public final class FireflyColor implements TooltipProvider, PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<FireflyColor> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.DEFAULT_FIELD_CODEC.forGetter(FireflyColor::assetInfo),
			SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(FireflyColor::spawnConditions),
			Codec.STRING.fieldOf("name").forGetter(FireflyColor::name)
		).apply(instance, FireflyColor::new)
	);
	public static final Codec<FireflyColor> NETWORK_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.DEFAULT_FIELD_CODEC.forGetter(FireflyColor::assetInfo),
			Codec.STRING.fieldOf("name").forGetter(FireflyColor::name)
		).apply(instance, FireflyColor::new)
	);
	public static final Codec<Holder<FireflyColor>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.FIREFLY_COLOR);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FireflyColor>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.FIREFLY_COLOR);

	private final ClientAsset clientAsset;
	private final SpawnPrioritySelectors spawnConditions;
	private final String name;

	public FireflyColor(ClientAsset clientAsset, SpawnPrioritySelectors spawnConditions, String name) {
		this.clientAsset = clientAsset;
		this.spawnConditions = spawnConditions;
		this.name = name;
	}

	private FireflyColor(ClientAsset clientAsset, String name) {
		this(clientAsset, SpawnPrioritySelectors.EMPTY, name);
	}

	@NotNull
	public ClientAsset assetInfo() {
		return this.clientAsset;
	}

	public SpawnPrioritySelectors spawnConditions() {
		return this.spawnConditions;
	}

	public String name() {
		return this.name;
	}

	@Override
	public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
		if (this.name.equals("on")) return;

		ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
		consumer.accept(Component.translatable("entity.wilderwild.firefly.color." + this.name).withStyle(chatFormattings));
	}

	@Override
	public @NotNull List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
