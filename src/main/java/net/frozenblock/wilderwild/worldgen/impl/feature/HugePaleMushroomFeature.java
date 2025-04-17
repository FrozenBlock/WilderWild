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
import org.jetbrains.annotations.NotNull;

public class HugePaleMushroomFeature extends AbstractHugeMushroomFeature {

	public HugePaleMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	protected int getTreeHeight(@NotNull RandomSource randomSource) {
		return randomSource.nextInt(3) + 4;
	}

	@Override
	protected void makeCap(
		LevelAccessor levelAccessor,
		RandomSource randomSource,
		BlockPos blockPos,
		int height,
		BlockPos.MutableBlockPos mutableBlockPos,
		HugeMushroomFeatureConfiguration hugeMushroomFeatureConfiguration
	) {
		int top = height + 1;
		int bottom = height - 2;
		for (int j = bottom; j <= top; j++) {
			int radius = j < top ? hugeMushroomFeatureConfiguration.foliageRadius : hugeMushroomFeatureConfiguration.foliageRadius - 1;
			int withinRadius = hugeMushroomFeatureConfiguration.foliageRadius - 2;

			for (int m = -radius; m <= radius; m++) {
				for (int n = -radius; n <= radius; n++) {
					boolean onNegX = m == -radius;
					boolean onPosX = m == radius;
					boolean onNegZ = n == -radius;
					boolean onPosZ = n == radius;
					boolean onX = onNegX || onPosX;
					boolean onZ = onNegZ || onPosZ;
					boolean onCorner = onX && onZ;
					boolean onEdge = onX || onZ;
					if (j >= top || ((onX != onZ) || (j == height && !onCorner))) {
						if ((j != bottom || randomSource.nextFloat() <= 0.25F)) {
							mutableBlockPos.setWithOffset(blockPos, m, j, n);
							if (!levelAccessor.getBlockState(mutableBlockPos).isSolidRender()) {
								BlockState blockState = hugeMushroomFeatureConfiguration.capProvider.getState(randomSource, blockPos);
								if (blockState.hasProperty(HugeMushroomBlock.WEST)
									&& blockState.hasProperty(HugeMushroomBlock.EAST)
									&& blockState.hasProperty(HugeMushroomBlock.NORTH)
									&& blockState.hasProperty(HugeMushroomBlock.SOUTH)
									&& blockState.hasProperty(HugeMushroomBlock.UP)
								) {
									boolean hasUpState = j >= top || (onEdge && j == height);
									blockState = blockState
										.setValue(HugeMushroomBlock.UP, hasUpState)
										.setValue(HugeMushroomBlock.WEST, m < -withinRadius)
										.setValue(HugeMushroomBlock.EAST, m > withinRadius)
										.setValue(HugeMushroomBlock.NORTH, n < -withinRadius)
										.setValue(HugeMushroomBlock.SOUTH, n > withinRadius);
								}

								this.setBlock(levelAccessor, mutableBlockPos, blockState);
							}
						}
					}
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
