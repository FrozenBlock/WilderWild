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

package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.particle.api.ParticleSpawner;
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class PaleMushroomBlock extends MushroomBlock {
	public static final ParticleSpawner PARTICLE_SPAWNER = new ParticleSpawner(
		0.25F,
		7,
		3,
		-3,
		3
	) {
		@Override
		public boolean canSpawnAtPos(Level level, BlockPos pos) {
			return !level.isRainingAt(pos);
		}

		@Override
		public ParticleOptions selectParticleOptions(Level level, BlockPos pos, RandomSource random) {
			return WWParticleTypes.PALE_SPORE.get();
		}
	};

	public PaleMushroomBlock(ResourceKey<Feature> feature, Properties properties) {
		super(feature, properties);
	}

	public static boolean isActive(Level level, BlockPos pos) {
		return level.environmentAttributes().getValue(WWEnvironmentAttributes.PALE_MUSHROOM_ACTIVE.get(), pos);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!isActive(level, pos)) return;
		PARTICLE_SPAWNER.tick(level, pos, random);
	}
}
