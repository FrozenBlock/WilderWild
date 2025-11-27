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
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;

public class NematocystFeature extends MultifaceGrowthFeature {

	public NematocystFeature(Codec<MultifaceGrowthConfiguration> codec) {
		super(codec);
	}

	public static boolean placeGrowthIfPossible(
		WorldGenLevel level,
		BlockPos pos,
		BlockState state,
		MultifaceGrowthConfiguration config,
		RandomSource random,
		List<Direction> directions
	) {
		BlockPos.MutableBlockPos mutable = pos.mutable();

		for (Direction direction : directions) {
			BlockState offsetState = level.getBlockState(mutable.setWithOffset(pos, direction));
			if (!offsetState.is(config.canBePlacedOn)) continue;

			BlockState placementState = config.placeBlock.getStateForPlacement(state, level, pos, direction);
			if (placementState == null) return false;

			level.setBlock(pos, placementState, Block.UPDATE_ALL);
			level.getChunk(pos).markPosForPostprocessing(pos);
			var optional = config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(placementState, level, pos, direction, random, true);
			for (int i = 0; i < random.nextInt(2) + 3; ++i) {
				if (optional.isPresent()) {
					var spreadPos = optional.get();
					optional = config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(placementState, level, spreadPos.pos(), spreadPos.face(), random, true);
				} else {
					break;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean place(FeaturePlaceContext<MultifaceGrowthConfiguration> context) {
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();
		final RandomSource random = context.random();
		final MultifaceGrowthConfiguration config = context.config();

		if (!isAirOrWater(level.getBlockState(origin))) return false;

		final List<Direction> list = config.getShuffledDirections(random);
		if (placeGrowthIfPossible(level, origin, level.getBlockState(origin), config, random, list)) return true;

		BlockPos.MutableBlockPos mutable = origin.mutable();
		for (Direction direction : list) {
			mutable.set(origin);
			final List<Direction> list2 = config.getShuffledDirectionsExcept(random, direction.getOpposite());
			for (int i = 0; i < config.searchRange; ++i) {
				mutable.setWithOffset(origin, direction);
				BlockState state = level.getBlockState(mutable);
				if (!isAirOrWater(state) && !state.is(config.placeBlock)) break;
				if (placeGrowthIfPossible(level, mutable, state, config, random, list2)) return true;
			}
		}
		return false;
	}

}
