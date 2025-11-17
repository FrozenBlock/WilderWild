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
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.ShelfFungiFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ShelfFungiFeature extends Feature<ShelfFungiFeatureConfig> {

	public ShelfFungiFeature(Codec<ShelfFungiFeatureConfig> codec) {
		super(codec);
	}

	public static boolean generate(
		WorldGenLevel level,
		BlockPos pos,
		ShelfFungiFeatureConfig config,
		RandomSource random
	) {
		MutableBlockPos mutable = pos.mutable();

		Direction placementDirection = null;
		for (Direction direction : Direction.values()) {
			BlockState state = level.getBlockState(mutable.setWithOffset(pos, direction));
			if (!state.is(config.canPlaceOn)) continue;
			if (direction.getAxis() == Direction.Axis.Y) {
				placementDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
			} else {
				placementDirection = direction.getOpposite();
			}
			break;
		}

		if (placementDirection != null) {
			level.setBlock(pos, config.fungus.defaultBlockState()
				.setValue(ShelfFungiBlock.FACING, placementDirection)
				.setValue(ShelfFungiBlock.FACE, ShelfFungiBlock.getFace(placementDirection.getOpposite()))
				.setValue(ShelfFungiBlock.STAGE, random.nextInt(3) + 1), Block.UPDATE_ALL);
			level.getChunk(pos).markPosForPostprocessing(pos);
			return true;
		}

		return false;
	}

	private static boolean isAirOrWater(BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}

	@Override
	public boolean place(FeaturePlaceContext<ShelfFungiFeatureConfig> context) {
		final WorldGenLevel level = context.level();
		final RandomSource random = context.random();
		final BlockPos pos = context.origin().above(random.nextInt(0, 4));
		final ShelfFungiFeatureConfig config = context.config();

		if (!isAirOrWater(level.getBlockState(pos))) return false;

		final List<Direction> list = config.shuffleDirections(random);
		if (generate(level, pos, config, random)) return true;

		final MutableBlockPos mutable = pos.mutable();
		for (Direction direction : list) {
			for (int i = 0; i < config.searchRange; ++i) {
				mutable.setWithOffset(pos, direction);
				final BlockState state = level.getBlockState(mutable);
				if (!isAirOrWater(state) && !state.is(config.fungus)) break;
				if (generate(level, mutable, config, random)) return true;
			}
		}
		return false;
	}

}
