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

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LeavesWithLitterBlock extends LeavesBlock {
	public static final MapCodec<LeavesWithLitterBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(propertiesCodec())
			.apply(instance, LeavesWithLitterBlock::new)
	);

	public LeavesWithLitterBlock(Properties properties) {
		super(0F, properties);
	}

	@Override
	public @NotNull MapCodec<? extends LeavesWithLitterBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean isRandomlyTicking(@NotNull BlockState blockState) {
		return !blockState.getValue(PERSISTENT);
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		FallingLeafUtil.onRandomTick(blockState, serverLevel, blockPos, randomSource);
		super.randomTick(blockState, serverLevel, blockPos, randomSource);
	}

	@Override
	protected void spawnFallingLeavesParticle(Level level, BlockPos blockPos, RandomSource randomSource) {

	}
}
