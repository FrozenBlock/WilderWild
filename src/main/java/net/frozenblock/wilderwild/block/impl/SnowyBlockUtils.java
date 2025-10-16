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

package net.frozenblock.wilderwild.block.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SnowyBlockUtils {
	public static final BiMap<Block, Block> SNOWY_BLOCK_MAP = ImmutableBiMap.<Block, Block>builder()
		.put(Blocks.SHORT_GRASS, WWBlocks.FROZEN_SHORT_GRASS)
		.put(Blocks.TALL_GRASS, WWBlocks.FROZEN_TALL_GRASS)
		.put(Blocks.FERN, WWBlocks.FROZEN_FERN)
		.put(Blocks.LARGE_FERN, WWBlocks.FROZEN_LARGE_FERN)
		.put(Blocks.BUSH, WWBlocks.FROZEN_BUSH)
		.build();
	public static final BiMap<Block, Block> NON_SNOWY_BLOCK_MAP = SNOWY_BLOCK_MAP.inverse();

	@Contract("_ -> param1")
	@NotNull
	public static BlockState getWorldgenSnowyEquivalent(@NotNull BlockState state) {
		final Block block = state.getBlock();
		if (SNOWY_BLOCK_MAP.containsKey(block)) return SNOWY_BLOCK_MAP.get(block).withPropertiesOf(state);
		return state;
	}

	@Contract("_ -> param1")
	@NotNull
	public static BlockState getNonSnowyEquivalent(@NotNull BlockState state) {
		final Block block = state.getBlock();
		if (NON_SNOWY_BLOCK_MAP.containsKey(block)) return NON_SNOWY_BLOCK_MAP.get(block).withPropertiesOf(state);
		return state;
	}

	public static @NotNull BlockState replaceWithWorldgenSnowyEquivalent(WorldGenLevel level, @NotNull BlockState state, BlockPos pos) {
		final BlockState snowyEquivalent = getWorldgenSnowyEquivalent(state);
		if (!state.equals(snowyEquivalent)) {
			if (state.getBlock() instanceof DoublePlantBlock) {
				DoublePlantBlock.placeAt(level, snowyEquivalent, pos, Block.UPDATE_CLIENTS);
			} else {
				level.setBlock(pos, snowyEquivalent, Block.UPDATE_CLIENTS);
			}
		}
		return snowyEquivalent;
	}

	public static @NotNull BlockState replaceWithNonSnowyEquivalent(WorldGenLevel level, @NotNull BlockState state, BlockPos pos) {
		final BlockState nonSnowyEquivalent = getNonSnowyEquivalent(state);
		if (!state.equals(nonSnowyEquivalent)) {
			if (state.getBlock() instanceof DoublePlantBlock) {
				DoublePlantBlock.placeAt(level, nonSnowyEquivalent, pos, Block.UPDATE_CLIENTS);
			} else {
				level.setBlock(pos, nonSnowyEquivalent, Block.UPDATE_CLIENTS);
			}
		}
		return nonSnowyEquivalent;
	}
}
