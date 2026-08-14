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

package net.frozenblock.wilderwild.levelgen.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.Unmodifiable;

public class FallenLargeTrunkPlacer extends TrunkPlacer {
	private static final int STUMP_GEN_ATTEMPTS = 3;
	private static final IntProvider STUMP_DISTANCE_FROM_TRUNK = UniformInt.of(2, 4);
	private static final int MAX_STUMP_HEIGHT = 3;
	private static final IntProvider STUMP_HEIGHT = UniformInt.of(1, MAX_STUMP_HEIGHT);
	private static final int STUMP_MAX_SEARCH_POSITIVE_Y = 10;
	private static final int STUMP_MAX_SEARCH_NEGATIVE_Y = 3;
	public static final MapCodec<FallenLargeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		trunkPlacerParts(instance)
			.and(Codec.floatRange(0F, 1F).fieldOf("water_success_probability").forGetter(trunkPlacer -> trunkPlacer.waterSuccessProbability))
			.and(Codec.floatRange(0F, 1F).fieldOf("stump_probability").forGetter(trunkPlacer -> trunkPlacer.stumpProbability))
			.apply(instance, FallenLargeTrunkPlacer::new));

	public final float waterSuccessProbability;
	public final int minHeight;
	public final int maxHeight;
	public final float stumpProbability;

	public FallenLargeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, float waterSuccessProbability, float stumpProbability) {
		super(baseHeight, heightRandA, heightRandB);
		this.minHeight = baseHeight;
		this.maxHeight = baseHeight + heightRandA + heightRandB;
		this.waterSuccessProbability = waterSuccessProbability;
		this.stumpProbability = stumpProbability;
	}

	private static boolean isWaterAt(LevelSimulatedReader level, BlockPos blockpos) {
		return level.isFluidAtPosition(blockpos, fluidState -> fluidState.is(FluidTags.WATER));
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FALLEN_LARGE_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		int treeHeight,
		BlockPos origin,
		TreeFeature tree
	) {
		final List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if (isWaterAt(level, origin) && this.waterSuccessProbability <= random.nextFloat()) return foliageAttachments;

		final Pair<List<BlockPos>, Optional<Direction>> positionsAndSideDirection = this.getAllPositionsAndSideDirection(level, origin, random, trunkDirection);
		for (BlockPos blockPos : positionsAndSideDirection.getFirst()) {
			this.placeLog(level, trunkSetter, random, blockPos, tree, (state) -> state.trySetValue(RotatedPillarBlock.AXIS, trunkDirection.getAxis()));
		}

		final Optional<Direction> optionalSideDirection = positionsAndSideDirection.getSecond();
		if (optionalSideDirection.isPresent() && this.stumpProbability <= random.nextFloat()) {
			final Direction sideDirection = optionalSideDirection.get();
			final List<BlockPos> stumpPoses = findStumpPositions(level, random, origin, trunkDirection, sideDirection);

			final BlockPos.MutableBlockPos stumpPos = new BlockPos.MutableBlockPos();
			for (BlockPos blockPos : stumpPoses) {
				final int stumpHeight = STUMP_HEIGHT.sample(random);
				for (int i = 0; i < stumpHeight; i++) {
					this.placeLog(level, trunkSetter, random, stumpPos.setWithOffset(blockPos, 0, i, 0), tree);
				}
			}
		}

		return foliageAttachments;
	}

	private Pair<List<BlockPos>, Optional<Direction>> getAllPositionsAndSideDirection(LevelSimulatedReader level, BlockPos origin, RandomSource random, Direction trunkDirection) {
		if (trunkDirection.getAxis().isVertical()) throw new IllegalArgumentException("trunkDirection cannot be vertical!");

		final List<BlockPos> positions = Lists.newArrayList();

		final BiFunction<Boolean, BlockPos, List<BlockPos>> getSectionPositions = (requiresUnderneath, sectionOrigin) ->
			getSectionPositions(requiresUnderneath, level, random, sectionOrigin, this.minHeight, this.maxHeight, trunkDirection);
		final List<BlockPos> startPositions = getSectionPositions.apply(true, origin);

		Optional<Direction> optionalSideDirection = Optional.empty();
		if (!startPositions.isEmpty()) {
			final Direction.Axis axis = trunkDirection.getClockWise().getAxis();
			final Direction sideDirection = random.nextBoolean() ? axis.getPositive() : axis.getNegative();
			final List<BlockPos> sidePositions = getSectionPositions.apply(true, origin.relative(sideDirection));
			if (!sidePositions.isEmpty()) {
				final List<BlockPos> topPositions = getSectionPositions.apply(false, origin.relative(Direction.UP));
				final List<BlockPos> topSidePositions = getSectionPositions.apply(false, origin.relative(Direction.UP).relative(sideDirection));

                positions.addAll(startPositions);
                positions.addAll(sidePositions);
                positions.addAll(topPositions);
                positions.addAll(topSidePositions);
				optionalSideDirection = Optional.of(sideDirection);
			}
		}

		return Pair.of(positions, optionalSideDirection);
	}

	private static List<BlockPos> getSectionPositions(
		boolean requiresUnderneath,
		LevelSimulatedReader level,
		RandomSource random,
		BlockPos origin,
		int minHeight,
		int maxHeight,
		Direction trunkDirection
	) {
		final List<BlockPos> finalizedPoses = Lists.newArrayList();
		final int height = random.nextIntBetweenInclusive(minHeight, maxHeight);
		final int differenceFromMaxHeight = maxHeight - height;
		final int directionOffset = differenceFromMaxHeight <= 0 ? 0 : random.nextIntBetweenInclusive(0, maxHeight - height);

		origin = origin.relative(trunkDirection, directionOffset);
		final BlockPos endPos = origin.relative(trunkDirection, height);
		final BlockPos secondToEndPos = endPos.relative(trunkDirection.getOpposite());

		final Iterable<BlockPos> poses = BlockPos.betweenClosed(origin, endPos);
		int aboveSolidAmount = 0;
		boolean isEndAboveSolid = false;

		if (!requiresUnderneath) {
			poses.forEach(pos -> finalizedPoses.add(pos.immutable()));
			return finalizedPoses;
		}

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (BlockPos blockPos : poses) {
			mutable.set(blockPos);
			if (TreeFeature.validTreePos(level, mutable)) {
				if (isPosSolidGround(level, mutable.move(Direction.DOWN))) {
					aboveSolidAmount += 1;
					mutable.move(Direction.UP);
					if (mutable.equals(endPos) || mutable.equals(secondToEndPos)) isEndAboveSolid = true;
				} else {
					mutable.move(Direction.UP);
					if (mutable.equals(origin)) return List.of();
				}
			} else {
				return List.of();
			}
		}

		if (isEndAboveSolid || ((double) aboveSolidAmount / (double) height) > 0.5D) {
			poses.forEach(pos -> finalizedPoses.add(pos.immutable()));
			return finalizedPoses;
		}

		return List.of();
	}

	private static List<BlockPos> findStumpPositions(
		LevelSimulatedReader level,
		RandomSource random,
		BlockPos origin,
		Direction trunkDirection,
		Direction sideDirection
	) {
		for (int i = 0; i < STUMP_GEN_ATTEMPTS; i++) {
			final int distance = STUMP_DISTANCE_FROM_TRUNK.sample(random);
			final Direction searchDirection = trunkDirection.getOpposite();
			final BlockPos searchStartPos = origin.relative(searchDirection, 1 + distance);
			final BlockPos.MutableBlockPos stumpPos = searchStartPos.mutable();

			final Supplier<List<BlockPos>> getStumpPositionsIfPossible = () -> getStumpPositionsIfPossible(level, stumpPos, searchDirection, sideDirection);
			final List<BlockPos> initialStumpPositions = getStumpPositionsIfPossible.get();
			if (!initialStumpPositions.isEmpty()) return initialStumpPositions;

			for (int step = 1; step <= STUMP_MAX_SEARCH_POSITIVE_Y; step++) {
				stumpPos.move(Direction.UP);
				final List<BlockPos> stumpPositions = getStumpPositionsIfPossible.get();
				if (!stumpPositions.isEmpty()) return stumpPositions;
			}

			stumpPos.set(searchStartPos);
			for (int step = 1; step <= STUMP_MAX_SEARCH_NEGATIVE_Y; step++) {
				stumpPos.move(Direction.DOWN);
				final List<BlockPos> stumpPositions = getStumpPositionsIfPossible.get();
				if (!stumpPositions.isEmpty()) return stumpPositions;
			}
		}

		return List.of();
	}

	@Unmodifiable
	private static List<BlockPos> getStumpPositionsIfPossible(
		LevelSimulatedReader level,
		BlockPos pos,
		Direction searchDirection,
		Direction sideDirection
	) {
		final List<Vec3i> offsets = List.of(Vec3i.ZERO, sideDirection.getStep(), searchDirection.getStep(), sideDirection.getStep().relative(searchDirection));
		final BlockPos.MutableBlockPos stumpPos = new BlockPos.MutableBlockPos();
		final BlockPos.MutableBlockPos belowStumpPos = new BlockPos.MutableBlockPos();

		final List<BlockPos> finalizedPositions = Lists.newArrayList();
		for (Vec3i offset : offsets) {
			if (!canPlaceStumpAtPos(level, stumpPos.setWithOffset(pos, offset), belowStumpPos.setWithOffset(stumpPos, Direction.DOWN))) return List.of();
			finalizedPositions.add(stumpPos.immutable());
		}

		return finalizedPositions;
	}

	private static boolean canPlaceStumpAtPos(LevelSimulatedReader level, BlockPos pos, BlockPos floorPos) {
		return isPosSolidGround(level, floorPos)
			&& level.isStateAtPosition(floorPos, blockState -> blockState.is(WWBlockTags.FALLEN_TREE_STUMP_PLACEABLE_ON))
			&& TreeFeature.validTreePos(level, pos)
			&& isFreeWithinStumpHeight(level, pos);
	}

	private static boolean isPosSolidGround(LevelSimulatedReader level, BlockPos pos) {
		return !TreeFeature.validTreePos(level, pos) && !TreeFeature.isAirOrLeaves(level, pos);
	}

	private static boolean isFreeWithinStumpHeight(LevelSimulatedReader level, BlockPos pos) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		for (int i = 0; i < STUMP_MAX_SEARCH_NEGATIVE_Y; i++) {
			if (!TreeFeature.validTreePos(level, mutable)) return false;
			mutable.move(Direction.UP);
		}
		return true;
	}
}
