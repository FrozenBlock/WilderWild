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
import java.util.function.Predicate;
import net.frozenblock.wilderwild.levelgen.feature.configuration.SulfurSpringDecorationFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SulfurSpringDecorationFeature extends Feature<SulfurSpringDecorationFeatureConfig> {

	public SulfurSpringDecorationFeature(Codec<SulfurSpringDecorationFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SulfurSpringDecorationFeatureConfig> context) {
		final RandomSource random = context.random();
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();

		final SulfurSpringDecorationFeatureConfig config = context.config();
		final BlockStateProvider state = config.state();
		final Predicate<BlockState> safeToReplace = statex -> !statex.is(config.cannotReplace()) && statex.is(config.replaceable());

		final BlockPos.MutableBlockPos mutable = origin.mutable();
		final BlockState topState = config.topState().getState(level, random, mutable);
		safeSetBlock(level, mutable, topState, safeToReplace);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			safeSetBlock(level, mutable.setWithOffset(origin, direction), state.getState(level, random, mutable), safeToReplace);
		}

		mutable.setWithOffset(origin, Direction.DOWN);
		safeSetBlock(level, mutable, config.bottomState().getState(level, random, mutable), safeToReplace);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			safeSetBlock(level, mutable.setWithOffset(origin, Direction.DOWN).move(direction), state.getState(level, random, mutable), safeToReplace);
		}

		safeSetBlock(level, mutable.setWithOffset(origin, Direction.DOWN).move(Direction.DOWN), state.getState(level, random, mutable), safeToReplace);

		return true;
	}

}
