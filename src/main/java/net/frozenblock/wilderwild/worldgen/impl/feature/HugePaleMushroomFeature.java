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

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugePaleMushroomFeature extends AbstractHugeMushroomFeature {

	public HugePaleMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	protected int getTreeHeight(RandomSource random) {
		return random.nextInt(3) + 4;
	}

	@Override
	protected void makeCap(
		LevelAccessor level,
		RandomSource random,
		BlockPos pos,
		int height,
		BlockPos.MutableBlockPos mutable,
		HugeMushroomFeatureConfiguration config
	) {
		final int top = height + 1;
		final int bottom = height - 2;
		for (int y = bottom; y <= top; y++) {
			final int radius = y < top ? config.foliageRadius : config.foliageRadius - 1;
			final int withinRadius = config.foliageRadius - 2;

			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					final boolean onNegX = x == -radius;
					final boolean onPosX = x == radius;
					final boolean onNegZ = z == -radius;
					final boolean onPosZ = z == radius;
					final boolean onX = onNegX || onPosX;
					final boolean onZ = onNegZ || onPosZ;
					final boolean onCorner = onX && onZ;
					final boolean onEdge = onX || onZ;

					if (!(y >= top || ((onX != onZ) || (y == height && !onCorner)))) continue;
					if (!(y != bottom || random.nextFloat() <= 0.25F)) continue;

					mutable.setWithOffset(pos, x, y, z);
					if (level.getBlockState(mutable).isSolidRender()) continue;

					BlockState state = config.capProvider.getState(random, pos);
					if (state.hasProperty(HugeMushroomBlock.WEST)
						&& state.hasProperty(HugeMushroomBlock.EAST)
						&& state.hasProperty(HugeMushroomBlock.NORTH)
						&& state.hasProperty(HugeMushroomBlock.SOUTH)
						&& state.hasProperty(HugeMushroomBlock.UP)
					) {
						final boolean hasUpState = y >= top || (onEdge && y == height);
						state = state
							.setValue(HugeMushroomBlock.UP, hasUpState)
							.setValue(HugeMushroomBlock.WEST, x < -withinRadius)
							.setValue(HugeMushroomBlock.EAST, x > withinRadius)
							.setValue(HugeMushroomBlock.NORTH, z < -withinRadius)
							.setValue(HugeMushroomBlock.SOUTH, z > withinRadius);
					}
					this.setBlock(level, mutable, state);
				}
			}
		}
	}

	@Override
	protected int getTreeRadiusForHeight(int i, int j, int k, int l) {
		int m = 0;
		if (l < j + 1 && l >= j - 1) {
			m = k;
		} else if (l == j) {
			m = k;
		}

		return m;
	}
}
