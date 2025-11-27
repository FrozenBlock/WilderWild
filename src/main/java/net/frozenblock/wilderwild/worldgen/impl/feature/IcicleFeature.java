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

public class IcicleFeature extends Feature<IcicleConfig> {

	public IcicleFeature(Codec<IcicleConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<IcicleConfig> featurePlaceContext) {
		final LevelAccessor level = featurePlaceContext.level();
		final BlockPos origin = featurePlaceContext.origin();
		final RandomSource random = featurePlaceContext.random();
		final IcicleConfig config = featurePlaceContext.config();

		final Optional<Direction> tipDirection = getTipDirection(level, origin, random);
		if (tipDirection.isEmpty()) return false;

		final BlockPos offsetPos = origin.relative(tipDirection.get().getOpposite());
		if (config.placeIceBlocks()) createPatchOfIceBlocks(level, random, offsetPos, config);

		int i = random.nextFloat() < config.chanceOfTallerIcicle() && DripstoneUtils.isEmptyOrWater(level.getBlockState(origin.relative(tipDirection.get())))
			? 2 : 1;
		IcicleUtils.growIcicle(level, origin, tipDirection.get(), i, false);
		return true;
	}

	private static Optional<Direction> getTipDirection(LevelAccessor level, BlockPos pos, RandomSource random) {
		final boolean isBaseAbove = IcicleUtils.isIcicleBase(level.getBlockState(pos.above()));
		final boolean isBaseBelow = IcicleUtils.isIcicleBase(level.getBlockState(pos.below()));
		if (isBaseAbove && isBaseBelow) return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
		if (isBaseAbove) return Optional.of(Direction.DOWN);
		return isBaseBelow ? Optional.of(Direction.UP) : Optional.empty();
	}

	private static void createPatchOfIceBlocks(LevelAccessor level, RandomSource random, BlockPos pos, IcicleConfig config) {
		IcicleUtils.placeIceBlockIfPossible(level, pos);

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (random.nextFloat() > config.chanceOfDirectionalSpread()) continue;

			final BlockPos offsetPos = pos.relative(direction);
			IcicleUtils.placeIceBlockIfPossible(level, offsetPos);

			if (random.nextFloat() > config.chanceOfSpreadRadius2()) continue;

			final BlockPos randomPos = offsetPos.relative(Direction.getRandom(random));
			IcicleUtils.placeIceBlockIfPossible(level, randomPos);
			if (random.nextFloat() > config.chanceOfSpreadRadius3()) continue;

			final BlockPos moreRandomPos = randomPos.relative(Direction.getRandom(random));
			IcicleUtils.placeIceBlockIfPossible(level, moreRandomPos);
		}
	}
}
