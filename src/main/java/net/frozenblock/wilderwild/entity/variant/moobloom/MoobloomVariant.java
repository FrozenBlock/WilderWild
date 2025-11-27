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

package net.frozenblock.wilderwild.entity.variant.moobloom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public final class MoobloomVariant {
	public static final Codec<MoobloomVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			BlockState.CODEC.fieldOf("flower_block_state").forGetter(MoobloomVariant::getFlowerBlockState),
			BlockState.CODEC.optionalFieldOf("top_flower_block_state").forGetter(MoobloomVariant::getTopFlowerBlockState),
			ResourceLocation.CODEC.fieldOf("texture").forGetter(moobloomVariant -> moobloomVariant.texture)
		).apply(instance, MoobloomVariant::new)
	);

	private final BlockState flowerBlockState;
	private final Optional<BlockState> topFlowerBlockState;
	private final ResourceLocation texture;
	private final ResourceLocation textureFull;
	private final boolean isDoubleBlock;

	public MoobloomVariant(BlockState flowerBlockState, Optional<BlockState> topFlowerBlockState, @NotNull ResourceLocation texture) {
		this.flowerBlockState = flowerBlockState;
		this.topFlowerBlockState = topFlowerBlockState;
		this.texture = texture;
		this.textureFull = fullTextureId(texture);
		this.isDoubleBlock = topFlowerBlockState.isPresent();
	}

	private static @NotNull ResourceLocation fullTextureId(@NotNull ResourceLocation resourceLocation) {
		return resourceLocation.withPath(string -> "textures/" + string + ".png");
	}

	public BlockState getFlowerBlockState() {
		return this.flowerBlockState;
	}

	public Optional<BlockState> getTopFlowerBlockState() {
		return this.topFlowerBlockState;
	}

	@NotNull
	public ResourceLocation texture() {
		return this.textureFull;
	}

	public boolean isDoubleBlock() {
		return this.isDoubleBlock;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else {
			return object instanceof MoobloomVariant moobloomVariant
				&& Objects.equals(this.texture, moobloomVariant.texture)
				&& Objects.equals(this.flowerBlockState, moobloomVariant.flowerBlockState)
				&& Objects.equals(this.topFlowerBlockState, moobloomVariant.topFlowerBlockState);
		}
	}

	@Override
	public int hashCode() {
		int i = 1;
		i = 31 * i + this.texture.hashCode();
		i = 31 * i + this.flowerBlockState.hashCode();
		return 31 * i + this.topFlowerBlockState.hashCode();
	}

}
