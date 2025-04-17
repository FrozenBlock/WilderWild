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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.IcicleConfig;
import net.frozenblock.wilderwild.worldgen.impl.util.IcicleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;

public class IcicleFeature extends Feature<IcicleConfig> {

	public IcicleFeature(Codec<IcicleConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<IcicleConfig> featurePlaceContext) {
		LevelAccessor levelAccessor = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		RandomSource randomSource = featurePlaceContext.random();
		IcicleConfig icicleConfig = featurePlaceContext.config();
		Optional<Direction> optional = getTipDirection(levelAccessor, blockPos, randomSource);
		if (optional.isEmpty()) {
			return false;
		} else {
			BlockPos blockPos2 = blockPos.relative(optional.get().getOpposite());
			if (icicleConfig.placeIceBlocks) {
				createPatchOfIceBlocks(levelAccessor, randomSource, blockPos2, icicleConfig);
			}
			int i = randomSource.nextFloat() < icicleConfig.chanceOfTallerIcicle
				&& DripstoneUtils.isEmptyOrWater(levelAccessor.getBlockState(blockPos.relative(optional.get())))
				? 2
				: 1;
			IcicleUtils.growIcicle(levelAccessor, blockPos, optional.get(), i, false);
			return true;
		}
	}

	private static Optional<Direction> getTipDirection(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, RandomSource randomSource) {
		boolean bl = IcicleUtils.isIcicleBase(levelAccessor.getBlockState(blockPos.above()));
		boolean bl2 = IcicleUtils.isIcicleBase(levelAccessor.getBlockState(blockPos.below()));
		if (bl && bl2) {
			return Optional.of(randomSource.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (bl) {
			return Optional.of(Direction.DOWN);
		} else {
			return bl2 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private static void createPatchOfIceBlocks(
		LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, IcicleConfig icicleConfig
	) {
		IcicleUtils.placeIceBlockIfPossible(levelAccessor, blockPos);

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(randomSource.nextFloat() > icicleConfig.chanceOfDirectionalSpread)) {
				BlockPos blockPos2 = blockPos.relative(direction);
				IcicleUtils.placeIceBlockIfPossible(levelAccessor, blockPos2);
				if (!(randomSource.nextFloat() > icicleConfig.chanceOfSpreadRadius2)) {
					BlockPos blockPos3 = blockPos2.relative(Direction.getRandom(randomSource));
					IcicleUtils.placeIceBlockIfPossible(levelAccessor, blockPos3);
					if (!(randomSource.nextFloat() > icicleConfig.chanceOfSpreadRadius3)) {
						BlockPos blockPos4 = blockPos3.relative(Direction.getRandom(randomSource));
						IcicleUtils.placeIceBlockIfPossible(levelAccessor, blockPos4);
					}
				}
			}
		}
	}
}
