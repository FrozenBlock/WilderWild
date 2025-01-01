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

package net.frozenblock.wilderwild.entity.variant.moobloom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class MoobloomVariant {
	public static final Codec<MoobloomVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			BlockState.CODEC.fieldOf("flower_block_state").forGetter(MoobloomVariant::getFlowerBlockState),
			ResourceLocation.CODEC.fieldOf("texture").forGetter(moobloomVariant -> moobloomVariant.texture),
			RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(MoobloomVariant::biomes)
		).apply(instance, MoobloomVariant::new)
	);

	private final BlockState flowerBlockState;
	private final ResourceLocation texture;
	private final ResourceLocation textureFull;
	private final HolderSet<Biome> biomes;

	public MoobloomVariant(BlockState flowerBlockState, @NotNull ResourceLocation texture, HolderSet<Biome> biomes) {
		this.flowerBlockState = flowerBlockState;
		this.texture = texture;
		this.textureFull = fullTextureId(texture);
		this.biomes = biomes;
	}

	private static @NotNull ResourceLocation fullTextureId(@NotNull ResourceLocation resourceLocation) {
		return resourceLocation.withPath(string -> "textures/" + string + ".png");
	}

	public BlockState getFlowerBlockState() {
		return this.flowerBlockState;
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
			return object instanceof MoobloomVariant moobloomVariant && Objects.equals(this.texture, moobloomVariant.texture)
				&& Objects.equals(this.flowerBlockState, moobloomVariant.flowerBlockState)
				&& Objects.equals(this.biomes, moobloomVariant.biomes);
		}
	}

	@Override
	public int hashCode() {
		int i = 1;
		i = 31 * i + this.texture.hashCode();
		i = 31 * i + this.flowerBlockState.hashCode();
		return 31 * i + this.biomes.hashCode();
	}

}
