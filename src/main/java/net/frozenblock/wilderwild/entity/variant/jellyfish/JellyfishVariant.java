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

package net.frozenblock.wilderwild.entity.variant.jellyfish;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class JellyfishVariant {
	public static final Codec<JellyfishVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("texture").forGetter(jellyfishVariant -> jellyfishVariant.texture),
			Codec.BOOL.fieldOf("pearlescent").forGetter(jellyfishVariant -> jellyfishVariant.pearlescent),
			RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(JellyfishVariant::biomes),
			RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("reproduction_food").forGetter(JellyfishVariant::getReproductionFood)
		).apply(instance, JellyfishVariant::new)
	);

	private final ResourceLocation texture;
	private final ResourceLocation textureFull;
	private final boolean pearlescent;
	private final HolderSet<Biome> biomes;
	private final HolderSet<Item> reproductionFood;

	public JellyfishVariant(@NotNull ResourceLocation texture, boolean pearlescent, HolderSet<Biome> biomes, HolderSet<Item> reproductionFood) {
		this.texture = texture;
		this.textureFull = fullTextureId(texture);
		this.pearlescent = pearlescent;
		this.biomes = biomes;
		this.reproductionFood = reproductionFood;
	}

	private static @NotNull ResourceLocation fullTextureId(@NotNull ResourceLocation resourceLocation) {
		return resourceLocation.withPath(string -> "textures/" + string + ".png");
	}

	@NotNull
	public ResourceLocation texture() {
		return this.textureFull;
	}

	public HolderSet<Biome> biomes() {
		return this.biomes;
	}

	public boolean isPearlescent() {
		return this.pearlescent;
	}

	public HolderSet<Item> getReproductionFood() {
		return this.reproductionFood;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else {
			return object instanceof JellyfishVariant jellyfishVariant && Objects.equals(this.texture, jellyfishVariant.texture)
				&& this.pearlescent == jellyfishVariant.pearlescent
				&& Objects.equals(this.biomes, jellyfishVariant.biomes)
				&& Objects.equals(this.reproductionFood, jellyfishVariant.reproductionFood);
		}
	}

	@Override
	public int hashCode() {
		int i = 1;
		i = 31 * i + this.texture.hashCode();
		i = 31 * i + Boolean.hashCode(this.pearlescent);
		i = 31 * i + this.biomes.hashCode();
		return 31 * i + this.reproductionFood.hashCode();
	}
}
