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

package net.frozenblock.wilderwild.entity.variant;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public record ButterflyVariant(ResourceLocation key, ResourceLocation texture, TagKey<Biome> biomes) implements StringRepresentable {
	public static final Codec<ButterflyVariant> CODEC = WilderWildRegistries.BUTTERFLY_VARIANT.byNameCodec();
	public static final StreamCodec<RegistryFriendlyByteBuf, ButterflyVariant> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static final ButterflyVariant MONARCH = register(
		WWConstants.id("monarch"),
		WWConstants.id("textures/entity/butterfly/monarch.png"),
		WWBiomeTags.BUTTERFLY_MONARCH
	);
	public static final ButterflyVariant RED_LACEWING = register(
		WWConstants.id("red_lacewing"),
		WWConstants.id("textures/entity/butterfly/red_lacewing.png"),
		WWBiomeTags.BUTTERFLY_RED_LACEWING
	);
	public static final ButterflyVariant MARBLED = register(
		WWConstants.id("marbled"),
		WWConstants.id("textures/entity/butterfly/marbled.png"),
		WWBiomeTags.BUTTERFLY_MARBLED
	);

	public ButterflyVariant(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, TagKey<Biome> biomes) {
		this.key = key;
		this.texture = texture;
		this.biomes = biomes;
	}

	@NotNull
	public static ButterflyVariant register(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, TagKey<Biome> biomes) {
		return Registry.register(WilderWildRegistries.BUTTERFLY_VARIANT, key, new ButterflyVariant(key, texture, biomes));
	}

	public static void init() {
	}

	public static List<ButterflyVariant> getVariantsForBiome(Holder<Biome> biome) {
		return WilderWildRegistries.BUTTERFLY_VARIANT.stream().filter(butterflyVariant -> biome.is(butterflyVariant.biomes())).toList();
	}

	public static @NotNull ButterflyVariant getRandomVariantForBiome(Holder<Biome> biome, RandomSource random) {
		List<ButterflyVariant> variants = getVariantsForBiome(biome);
		if (variants.isEmpty()) return MONARCH;
		return Util.getRandom(variants, random);
	}

	@NotNull
	public ResourceLocation key() {
		return this.key;
	}

	@NotNull
	public ResourceLocation texture() {
		return this.texture;
	}

	@Override
	@NotNull
	public String getSerializedName() {
		var key = WilderWildRegistries.BUTTERFLY_VARIANT.getKey(this);
		return key != null ? key.toString() : "null";
	}
}
