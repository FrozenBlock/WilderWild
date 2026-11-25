/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.data.worldgen.biome.impl;

import com.mojang.serialization.Codec;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.EnumStreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public enum TundraGrassColorType implements StringRepresentable {
	DEFAULT("default", 0),
	CONTRAST("contrast", 1),
	BROWN("brown", 2);
	public static final Codec<TundraGrassColorType> CODEC = StringRepresentable.fromEnum(TundraGrassColorType::values);
	public static final EnumStreamCodec<TundraGrassColorType> STREAM_CODEC = ByteBufCodecs.enumCodec(
		TundraGrassColorType.class,
		TundraGrassColorType::id,
		ByIdMap.OutOfBoundsStrategy.CLAMP
	);
	public static final EntryType<TundraGrassColorType> CONFIG_ENTRY_TYPE = EntryType.create(CODEC, STREAM_CODEC);
	private final String name;
	private final int id;

	TundraGrassColorType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	private int id () {
		return this.id;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
