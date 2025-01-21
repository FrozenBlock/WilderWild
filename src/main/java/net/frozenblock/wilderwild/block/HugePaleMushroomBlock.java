/*
 * Copyright 2025 FrozenBlock
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

import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HugePaleMushroomBlock extends HugeMushroomBlock {
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -2;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 2;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -2;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = -1;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 7;

	public HugePaleMushroomBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (PaleMushroomBlock.isNight(level)) {
			if (level.getBlockState(pos.below()).is(Blocks.MUSHROOM_STEM)) {
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
				for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
					mutable.set(
						i + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
						j + Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
						k + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
					);
					BlockState blockState = level.getBlockState(mutable);
					if (!blockState.isCollisionShapeFullBlock(level, mutable) && random.nextFloat() <= 0.285F) {
						level.addParticle(
							random.nextFloat() <= 0.65F ? WWParticleTypes.PALE_FOG_SMALL : WWParticleTypes.PALE_FOG,
							mutable.getX() + random.nextDouble(),
							mutable.getY() + random.nextDouble(),
							mutable.getZ() + random.nextDouble(),
							0D,
							0D,
							0D
						);
					}
				}
			}
		}
	}
}
