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

package net.frozenblock.wilderwild.entity.variant.firefly;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.ChatFormatting;

public record FireflyColor(
	ClientAsset.ResourceTexture resourceTexture, SpawnPrioritySelectors spawnConditions, String name, Optional<DyeColor> dyeColor
) implements TooltipProvider, PriorityProvider<SpawnContext, SpawnCondition> {
	private static final ChatFormatting[] CHAT_FORMATTINGS = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
	public static final Codec<FireflyColor> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(FireflyColor::resourceTexture),
			SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(FireflyColor::spawnConditions),
			Codec.STRING.fieldOf("name").forGetter(FireflyColor::name),
			DyeColor.CODEC.optionalFieldOf("dye_color").forGetter(fireflyColor -> fireflyColor.dyeColor)
		).apply(instance, FireflyColor::new)
	);
	public static final Codec<FireflyColor> NETWORK_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(FireflyColor::resourceTexture),
			Codec.STRING.fieldOf("name").forGetter(FireflyColor::name),
			DyeColor.CODEC.optionalFieldOf("dye_color").forGetter(fireflyColor -> fireflyColor.dyeColor)
		).apply(instance, FireflyColor::new)
	);
	public static final Codec<Holder<FireflyColor>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.FIREFLY_COLOR);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FireflyColor>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.FIREFLY_COLOR);

	private FireflyColor(ClientAsset.ResourceTexture resourceTexture, String name, Optional<DyeColor> dyeColor) {
		this(resourceTexture, SpawnPrioritySelectors.EMPTY, name, dyeColor);
	}

	@Override
	public ClientAsset.ResourceTexture resourceTexture() {
		return this.resourceTexture;
	}

	public static Optional<FireflyColor> getByDyeColor(RegistryAccess registryAccess, DyeColor dyeColor) {
		return registryAccess.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR)
			.stream()
			.filter(fireflyColor -> fireflyColor.dyeColor().orElse(null) == dyeColor)
			.findAny();
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter getter) {
		if (this.name.equals("on")) return;
		consumer.accept(Component.translatable("entity.wilderwild.firefly.color." + this.name).withStyle(CHAT_FORMATTINGS));
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}
}
