/*
 * Copyright 2024 FrozenBlock
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
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MapleLeavesBlock extends LeavesBlock {
	public static final MapCodec<MapleLeavesBlock> CODEC = simpleCodec(MapleLeavesBlock::new);

	@Override
	public @NotNull MapCodec<MapleLeavesBlock> codec() {
		return CODEC;
	}

	public MapleLeavesBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
		if (random.nextInt(80) == 0) {
			BlockPos blockPos = pos.below();
			BlockState blockState = world.getBlockState(blockPos);
			if (!isFaceFull(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
				ParticleUtils.spawnParticleBelow(world, pos, random, RegisterParticles.MAPLE_LEAVES);
			}
		}
	}
}
