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

package net.frozenblock.wilderwild.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HugePaleMushroomFeature(
	Holder<BlockStateProvider> capProvider,
	Holder<BlockStateProvider> stemProvider,
	int foliageRadius,
	BlockPredicate canPlaceOn
) implements AbstractHugeMushroomFeature {
	public static final MapCodec<HugePaleMushroomFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(HugePaleMushroomFeature::capProvider),
		BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(HugePaleMushroomFeature::stemProvider),
		Codec.INT.optionalFieldOf("foliage_radius", 2).forGetter(HugePaleMushroomFeature::foliageRadius),
		BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(HugePaleMushroomFeature::canPlaceOn)
	).apply(instance, HugePaleMushroomFeature::new));

	@Override
	public MapCodec<? extends AbstractHugeMushroomFeature> codec() {
		return CODEC;
	}

	@Override
	public int getTreeHeight(RandomSource random) {
		return random.nextInt(3) + 4;
	}

	@Override
	public void makeCap(WorldGenLevel level, RandomSource random, BlockPos origin, int treeHeight, BlockPos.MutableBlockPos blockPos) {
		final int top = treeHeight + 1;
		final int bottom = treeHeight - 2;
		for (int y = bottom; y <= top; y++) {
			final int radius = y < top ? this.foliageRadius : this.foliageRadius - 1;
			final int withinRadius = this.foliageRadius - 2;

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

					if (!(y >= top || ((onX != onZ) || (y == treeHeight && !onCorner)))) continue;
					if (!(y != bottom || random.nextFloat() <= 0.25F)) continue;

					blockPos.setWithOffset(origin, x, y, z);
					if (level.getBlockState(blockPos).isSolidRender()) continue;

					BlockState state = this.capProvider.value().getState(level, random, origin);
					if (state.hasProperty(HugeMushroomBlock.WEST)
						&& state.hasProperty(HugeMushroomBlock.EAST)
						&& state.hasProperty(HugeMushroomBlock.NORTH)
						&& state.hasProperty(HugeMushroomBlock.SOUTH)
						&& state.hasProperty(HugeMushroomBlock.UP)
					) {
						final boolean hasUpState = y >= top || (onEdge && y == treeHeight);
						state = state
							.setValue(HugeMushroomBlock.UP, hasUpState)
							.setValue(HugeMushroomBlock.WEST, x < -withinRadius)
							.setValue(HugeMushroomBlock.EAST, x > withinRadius)
							.setValue(HugeMushroomBlock.NORTH, z < -withinRadius)
							.setValue(HugeMushroomBlock.SOUTH, z > withinRadius);
					}
					this.setBlock(level, blockPos, state);
				}
			}
		}
	}

	@Override
	public int getTreeRadiusForHeight(int trunkHeight, int treeHeight, int leafRadius, int yo) {
		int radius = 0;
		if (yo < treeHeight + 1 && yo >= treeHeight - 1) {
			radius = leafRadius;
		} else if (yo == treeHeight) {
			radius = leafRadius;
		}

		return radius;
	}
}
