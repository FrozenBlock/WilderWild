/*
 * Copyright 2023-2024 FrozenBlock
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
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PollenBlock extends FlowerLichenBlock {
	public static final MapCodec<PollenBlock> CODEC = simpleCodec(PollenBlock::new);
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -10;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 10;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -10;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = 7;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 7;

	public PollenBlock(@NotNull Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	protected MapCodec<? extends PollenBlock> codec() {
		return CODEC;
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (WWBlockConfig.Client.POLLEN_ENABLED) {
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
				if (!blockState.isCollisionShapeFullBlock(level, mutable) && !level.isRainingAt(mutable)) {
					level.addParticle(
						WWParticleTypes.POLLEN,
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
