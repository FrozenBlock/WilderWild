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

package net.frozenblock.wilderwild.block.state.properties;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Contract;

public enum FroglightType implements StringRepresentable {
	PEARLESCENT("pearlescent", () -> WWBlocks.PEARLESCENT_FROGLIGHT_GOOP_BODY, () -> WWBlocks.PEARLESCENT_FROGLIGHT_GOOP),
	VERDANT("verdant", () -> WWBlocks.VERDANT_FROGLIGHT_GOOP_BODY, () -> WWBlocks.VERDANT_FROGLIGHT_GOOP),
	OCHRE("ochre", () -> WWBlocks.OCHRE_FROGLIGHT_GOOP_BODY, () -> WWBlocks.OCHRE_FROGLIGHT_GOOP);
	public static final Codec<FroglightType> CODEC = StringRepresentable.fromEnum(FroglightType::values);

	private final String name;
	private final Supplier<Block> bodyBlock;
	private final Supplier<Block> headBlock;

	FroglightType(String name, Supplier<Block> bodyBlock, Supplier<Block> headBlock) {
		this.name = name;
		this.bodyBlock = bodyBlock;
		this.headBlock = headBlock;
	}

	public Block getBodyBlock() {
		return this.bodyBlock.get();
	}

	public Block getHeadBlock() {
		return this.headBlock.get();
	}

	public static Optional<FroglightType> getFromBaseBlock(Block froglight) {
		if (froglight == Blocks.PEARLESCENT_FROGLIGHT) return Optional.of(FroglightType.PEARLESCENT);
		if (froglight == Blocks.VERDANT_FROGLIGHT)  return Optional.of(FroglightType.VERDANT);
		if (froglight == Blocks.OCHRE_FROGLIGHT) return Optional.of(FroglightType.OCHRE);
		return Optional.empty();
	}

	@Contract(pure = true)
	@Override
	public String getSerializedName() {
		return this.name;
	}
}
