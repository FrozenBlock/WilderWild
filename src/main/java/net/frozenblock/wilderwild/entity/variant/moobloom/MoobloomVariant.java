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
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.level.block.state.BlockState;

public final class MoobloomVariant {
	public static final Codec<MoobloomVariant> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ClientAsset.DEFAULT_FIELD_CODEC.forGetter(MoobloomVariant::assetInfo),
			BlockState.CODEC.fieldOf("flower_block_state").forGetter(MoobloomVariant::getFlowerBlockState),
			BlockState.CODEC.optionalFieldOf("top_flower_block_state").forGetter(MoobloomVariant::getTopFlowerBlockState)
		).apply(instance, MoobloomVariant::new)
	);
	public static final Codec<Holder<MoobloomVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.MOOBLOOM_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<MoobloomVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.MOOBLOOM_VARIANT);

	private final ClientAsset clientAsset;
	private final BlockState flowerBlockState;
	private final Optional<BlockState> topFlowerBlockState;
	private final boolean isDoubleBlock;

	public MoobloomVariant(ClientAsset clientAsset, BlockState flowerBlockState, Optional<BlockState> topFlowerBlockState) {
		this.clientAsset = clientAsset;
		this.flowerBlockState = flowerBlockState;
		this.topFlowerBlockState = topFlowerBlockState;
		this.isDoubleBlock = topFlowerBlockState.isPresent();
	}

	public ClientAsset assetInfo() {
		return this.clientAsset;
	}

	public BlockState getFlowerBlockState() {
		return this.flowerBlockState;
	}

	public Optional<BlockState> getTopFlowerBlockState() {
		return this.topFlowerBlockState;
	}

	public boolean isDoubleBlock() {
		return this.isDoubleBlock;
	}
}
