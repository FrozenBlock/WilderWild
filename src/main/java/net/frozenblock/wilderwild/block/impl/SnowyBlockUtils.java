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

package net.frozenblock.wilderwild.block.impl;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SnowyBlockUtils {
	public static final Map<Block, Block> SNOWY_BLOCK_MAP = ImmutableMap.<Block, Block>builder()
		.put(Blocks.SHORT_GRASS, WWBlocks.FROZEN_SHORT_GRASS)
		.put(Blocks.FERN, WWBlocks.FROZEN_FERN)
		.build();

	@Contract("_ -> param1")
	@NotNull
	public static BlockState getWorldgenSnowyEquivalent(@NotNull BlockState state) {
		Block block = state.getBlock();
		if (SNOWY_BLOCK_MAP.containsKey(block)) return SNOWY_BLOCK_MAP.get(block).withPropertiesOf(state);
		return state;
	}

	public static @NotNull BlockState replaceWithWorldgenSnowyEquivalent(WorldGenLevel level, @NotNull BlockState state, BlockPos pos) {
		BlockState snowyEquivalent = getWorldgenSnowyEquivalent(state);
		if (!state.equals(snowyEquivalent)) level.setBlock(pos, snowyEquivalent, Block.UPDATE_CLIENTS);
		return snowyEquivalent;
	}
}
