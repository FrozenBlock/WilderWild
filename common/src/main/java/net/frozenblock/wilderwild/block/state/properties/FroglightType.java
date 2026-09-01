/*
 * Copyright 2025-2026 FrozenBlock
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
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum FroglightType implements StringRepresentable {
	// Although DeferredBlock is a Supplier itself, for whatever reason not using the () -> DeferredBlock.get() format causes a crash.
	PEARLESCENT("pearlescent", () -> Blocks.PEARLESCENT_FROGLIGHT, () -> WWBlocks.PEARLESCENT_FROGLIGHT_GOOP_BODY.get(), () -> WWBlocks.PEARLESCENT_FROGLIGHT_GOOP.get()),
	VERDANT("verdant", () -> Blocks.VERDANT_FROGLIGHT, () -> WWBlocks.VERDANT_FROGLIGHT_GOOP_BODY.get(), () -> WWBlocks.VERDANT_FROGLIGHT_GOOP.get()),
	OCHRE("ochre", () -> Blocks.OCHRE_FROGLIGHT, () -> WWBlocks.OCHRE_FROGLIGHT_GOOP_BODY.get(), () -> WWBlocks.OCHRE_FROGLIGHT_GOOP.get());
	public static final Codec<FroglightType> CODEC = StringRepresentable.fromEnum(FroglightType::values);
	private final String name;
	private final Supplier<? extends Block> baseBlock;
	private final Supplier<? extends Block> bodyBlock;
	private final Supplier<? extends Block> headBlock;

	FroglightType(String name, Supplier<? extends Block> baseBlock, Supplier<? extends Block> bodyBlock, Supplier<? extends Block> headBlock) {
		this.name = name;
		this.baseBlock = baseBlock;
		this.bodyBlock = bodyBlock;
		this.headBlock = headBlock;
	}

	public Block getBaseBlock() {
		return this.baseBlock.get();
	}

	public Block getBodyBlock() {
		return this.bodyBlock.get();
	}

	public Block getHeadBlock() {
		return this.headBlock.get();
	}

	public static Optional<FroglightType> getFromBaseBlock(Block block) {
		return Arrays.stream(FroglightType.values())
			.filter(type -> type.getBaseBlock() == block)
			.findFirst();
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
