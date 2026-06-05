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

package net.frozenblock.wilderwild.levelgen.rootplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class WillowRootPlacer extends RootPlacer {
	public static final MapCodec<WillowRootPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		rootPlacerParts(instance)
			.and(WillowRootPlacement.CODEC.fieldOf("willow_root_placement").forGetter(willowRootPlacer -> willowRootPlacer.willowRootPlacement))
			.apply(instance, WillowRootPlacer::new)
	);
	private final WillowRootPlacement willowRootPlacement;

	public WillowRootPlacer(
		IntProvider intProvider, BlockStateProvider blockStateProvider, Optional<AboveRootPlacement> optional, WillowRootPlacement mangroveRootPlacement
	) {
		super(intProvider, blockStateProvider, optional);
		this.willowRootPlacement = mangroveRootPlacement;
	}

	@Override
	public boolean placeRoots(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> rootSetter,
		RandomSource random,
		BlockPos origin,
		BlockPos trunkOrigin,
		TreeConfiguration config
	) {
		final List<BlockPos> rootPositions = Lists.newArrayList();
		final BlockPos.MutableBlockPos columnPos = origin.mutable();

		while (columnPos.getY() < trunkOrigin.getY()) {
			if (!this.canPlaceRoot(level, columnPos)) return false;
			columnPos.move(Direction.UP);
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			final BlockPos pos = trunkOrigin.relative(direction);
			final List<BlockPos> positionsInDirection = Lists.newArrayList();
			if (!this.simulateRoots(level, random, pos, direction, trunkOrigin, positionsInDirection, 0)) return false;

			rootPositions.addAll(positionsInDirection);
			rootPositions.add(trunkOrigin.relative(direction));
		}

		final List<BlockPos> columnPositions = Lists.newArrayList();
		for (BlockPos rootPoses : rootPositions) columnPositions.addAll(this.potentialColumnRootPositions(level, rootPoses));
		rootPositions.addAll(columnPositions);

		for (BlockPos rootPos : rootPositions) this.placeRoot(level, rootSetter, random, rootPos, config);
		return true;
	}

	private boolean simulateRoots(
		LevelSimulatedReader level,
		RandomSource random,
		BlockPos rootPos,
		Direction dir,
		BlockPos rootOrigin,
		List<BlockPos> rootPositions,
		int layer
	) {
		final int maxLength = this.willowRootPlacement.maxRootLength();
		if (layer != maxLength && rootPositions.size() <= maxLength) {
			for (BlockPos blockPos3 : this.potentialRootPositions(rootPos, dir, random, rootOrigin)) {
				if (!this.canPlaceRoot(level, blockPos3)) continue;
				rootPositions.add(blockPos3);
				if (!this.simulateRoots(level, random, blockPos3, dir, rootOrigin, rootPositions, layer + 1)) return false;
			}

			return true;
		}
		return false;
	}

	protected List<BlockPos> potentialColumnRootPositions(LevelSimulatedReader level, BlockPos pos) {
		final ArrayList<BlockPos> positions = new ArrayList<>();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		mutable.set(pos);
		while (this.canPlaceRoot(level, mutable.move(Direction.DOWN))) positions.add(mutable.immutable());
		return positions;
	}

	protected List<BlockPos> potentialRootPositions(
		BlockPos pos,
		Direction prevDir,
		RandomSource random,
		BlockPos rootOrigin
	) {
		final BlockPos below = pos.below();
		final BlockPos nextTo = pos.relative(prevDir);
		final int width = pos.distManhattan(rootOrigin);
		final int maxRootWidth = this.willowRootPlacement.maxRootWidth();
		final float randomSkewChance = this.willowRootPlacement.randomSkewChance();

		if (width > maxRootWidth - 3 && width <= maxRootWidth) return random.nextFloat() < randomSkewChance ? List.of(below, nextTo.below()) : List.of(below);
		if (width > maxRootWidth) return List.of(below);
		if (random.nextFloat() < randomSkewChance) return List.of(below);
		return random.nextBoolean() ? List.of(nextTo) : List.of(below);
	}

	@Override
	protected boolean canPlaceRoot(LevelSimulatedReader level, BlockPos pos) {
		return super.canPlaceRoot(level, pos) || level.isStateAtPosition(pos, state -> state.is(this.willowRootPlacement.canGrowThrough()));
	}

	@Override
	protected RootPlacerType<?> type() {
		return WWFeatures.WILLOW_ROOT_PLACER;
	}
}
