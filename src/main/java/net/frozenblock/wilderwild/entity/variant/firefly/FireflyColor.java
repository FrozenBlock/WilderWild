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
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class FireflyColor {
	public static final Codec<FireflyColor> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("texture").forGetter(fireflyColor -> fireflyColor.texture),
			DyeColor.CODEC.optionalFieldOf("dye_color").forGetter(fireflyColor -> fireflyColor.dyeColor),
			RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(FireflyColor::biomes),
			Codec.FLOAT.fieldOf("bottle_model_predicate_value").forGetter(FireflyColor::modelPredicate)
		).apply(instance, FireflyColor::new)
	);

	private final ResourceLocation texture;
	private final ResourceLocation textureFull;
	private final Optional<DyeColor> dyeColor;
	private final HolderSet<Biome> biomes;
	private final float modelPredicate;

	public FireflyColor(@NotNull ResourceLocation texture, Optional<DyeColor> dyeColor, HolderSet<Biome> biomes, float modelPredicate) {
		this.texture = texture;
		this.textureFull = fullTextureId(texture);
		this.dyeColor = dyeColor;
		this.biomes = biomes;
		this.modelPredicate = modelPredicate;
	}

	private static @NotNull ResourceLocation fullTextureId(@NotNull ResourceLocation resourceLocation) {
		return resourceLocation.withPath(string -> "textures/" + string + ".png");
	}

	@NotNull
	public ResourceLocation texture() {
		return this.textureFull;
	}

	public Optional<DyeColor> dyeColor() {
		return this.dyeColor;
	}

	public HolderSet<Biome> biomes() {
		return this.biomes;
	}

	public float modelPredicate() {
		return this.modelPredicate;
	}

	public static Optional<FireflyColor> getByDyeColor(RegistryAccess registryAccess, @NotNull DyeColor dyeColor) {
		return registryAccess.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR)
			.stream()
			.filter(fireflyColor -> fireflyColor.dyeColor().orElse(null) == dyeColor)
			.findAny();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) return true;
		return object instanceof FireflyColor fireflyColor
			&& Objects.equals(this.texture, fireflyColor.texture)
			&& Objects.equals(this.dyeColor, fireflyColor.dyeColor)
			&& Objects.equals(this.biomes, fireflyColor.biomes)
			&& Objects.equals(this.modelPredicate, fireflyColor.modelPredicate);
	}

	@Override
	public int hashCode() {
		int i = 1;
		i = 31 * i + this.texture.hashCode();
		i = 31 * i + this.dyeColor.hashCode();
		i = 31 * i + this.biomes.hashCode();
		return 31 * i + Double.hashCode(this.modelPredicate);
	}
}
