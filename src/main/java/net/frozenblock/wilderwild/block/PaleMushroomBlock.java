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

import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class PaleMushroomBlock extends MushroomBlock {
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -3;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 3;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -1;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = 3;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 3;

	public PaleMushroomBlock(ResourceKey<ConfiguredFeature<?, ?>> resourceKey, Properties properties) {
		super(resourceKey, properties);
	}

	public static boolean isActive(@NotNull Level level, BlockPos pos) {
		return level.environmentAttributes().getValue(WWEnvironmentAttributes.PALE_MUSHROOM_ACTIVE, pos);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!isActive(level, pos)) return;

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
			if (random.nextFloat() >= 0.25F) continue;
			mutable.setWithOffset(
				pos,
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
			);
			final BlockState insideState = level.getBlockState(mutable);
			if (insideState.isCollisionShapeFullBlock(level, mutable) || level.isRainingAt(mutable)) continue;
			level.addParticle(
				WWParticleTypes.PALE_SPORE,
				mutable.getX() + random.nextDouble(),
				mutable.getY() + random.nextDouble(),
				mutable.getZ() + random.nextDouble(),
				0D, 0D, 0D
			);
		}
	}

}
