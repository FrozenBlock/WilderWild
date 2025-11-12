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
		if (!PaleMushroomBlock.isActive(level, pos)) return;
		if (!level.getBlockState(pos.below()).is(Blocks.MUSHROOM_STEM)) return;

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
			mutable.setWithOffset(
				pos,
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
			);
			final BlockState insideState = level.getBlockState(mutable);
			if (insideState.isCollisionShapeFullBlock(level, mutable) || random.nextFloat() > 0.285F) continue;
			level.addParticle(
				random.nextFloat() <= 0.65F ? WWParticleTypes.PALE_FOG_SMALL : WWParticleTypes.PALE_FOG,
				mutable.getX() + random.nextDouble(),
				mutable.getY() + random.nextDouble(),
				mutable.getZ() + random.nextDouble(),
				0D, 0D, 0D
			);
		}
	}
}
