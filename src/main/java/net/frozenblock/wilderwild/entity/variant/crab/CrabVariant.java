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

package net.frozenblock.wilderwild.entity.variant.crab;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class CrabVariant {
	public static final Codec<CrabVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("texture").forGetter(butterflyVariant -> butterflyVariant.texture),
			RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(CrabVariant::biomes)
		).apply(instance, CrabVariant::new)
	);

	private final ResourceLocation texture;
	private final ResourceLocation textureFull;
	private final HolderSet<Biome> biomes;

	public CrabVariant(@NotNull ResourceLocation texture, HolderSet<Biome> biomes) {
		this.texture = texture;
		this.textureFull = fullTextureId(texture);
		this.biomes = biomes;
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

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else {
			return object instanceof CrabVariant crabVariant && Objects.equals(this.texture, crabVariant.texture)
				&& Objects.equals(this.biomes, crabVariant.biomes);
		}
	}

	@Override
	public int hashCode() {
		int i = 1;
		i = 31 * i + this.texture.hashCode();
		return 31 * i + this.biomes.hashCode();
	}

}
