/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.impl.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
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
			.and(Codec.floatRange(0F, 1F).fieldOf("success_in_water_chance").forGetter((trunkPlacer) -> trunkPlacer.successInWaterChance))
			.and(Codec.floatRange(0F, 1F).fieldOf("stump_placement_chance").forGetter((trunkPlacer) -> trunkPlacer.stumpPlacementChance))
			.apply(instance, FallenLargeTrunkPlacer::new));

	public final float successInWaterChance;
	public final int minHeight;
	public final int maxHeight;
	public final float stumpPlacementChance;

	public FallenLargeTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, float successInWaterChance, float stumpPlacementChance) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.minHeight = baseHeight;
		this.maxHeight = baseHeight + firstRandomHeight + secondRandomHeight;
		this.successInWaterChance = successInWaterChance;
		this.stumpPlacementChance = stumpPlacementChance;
	}

	private static boolean isWaterAt(@NotNull LevelSimulatedReader level, @NotNull BlockPos blockpos) {
		return level.isFluidAtPosition(blockpos, fluidState -> fluidState.is(FluidTags.WATER));
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FALLEN_LARGE_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		int height,
		@NotNull BlockPos startPos,
		@NotNull TreeConfiguration config
	) {
		List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if (isWaterAt(level, startPos) && this.successInWaterChance <= random.nextFloat()) return foliageAttachments;

		Pair<List<BlockPos>, Optional<Direction>> posesAndOffset = this.getAllSectionPoses(level, startPos, random, trunkDirection);
		for (BlockPos blockPos : posesAndOffset.getFirst()) {
			this.placeLog(level, replacer, random, blockPos, config, (state) -> state.trySetValue(RotatedPillarBlock.AXIS, trunkDirection.getAxis()));
		}

		Optional<Direction> optionalOffsetDirection = posesAndOffset.getSecond();
		if (optionalOffsetDirection.isPresent() && this.stumpPlacementChance <= random.nextFloat()) {
			Direction offsetDirection = optionalOffsetDirection.get();
			List<BlockPos> stumpPoses = findStumpPoses(level, random, startPos, trunkDirection, offsetDirection);

			BlockPos.MutableBlockPos stumpPos = new BlockPos.MutableBlockPos();
			for (BlockPos blockPos : stumpPoses) {
				int stumpHeight = STUMP_HEIGHT.sample(random);
				for (int i = 0; i < stumpHeight; i++) {
					this.placeLog(level, replacer, random, stumpPos.setWithOffset(blockPos, 0, i, 0), config);
				}
			}
		}

		return foliageAttachments;
	}

	@NotNull
	private Pair<List<BlockPos>, Optional<Direction>> getAllSectionPoses(
		@NotNull LevelSimulatedReader level,
		BlockPos startPos,
		@NotNull RandomSource random,
		Direction trunkDirection
	) {
		List<BlockPos> poses = Lists.newArrayList();
		Optional<Direction> optionalOffsetDirection = Optional.empty();
		List<BlockPos> firstPoses = getSectionPoses(true, level, random, startPos, this.minHeight, this.maxHeight, trunkDirection);
		if (!firstPoses.isEmpty()) {
			Direction.Axis axis = trunkDirection.getAxis();
			if (axis == Direction.Axis.X) {
				axis = Direction.Axis.Z;
			} else if (axis == Direction.Axis.Z) {
				axis = Direction.Axis.X;
			}
			Direction secondOffset = AdvancedMath.randomDir(axis);
			List<BlockPos> secondPoses = getSectionPoses(true, level, random, startPos.relative(secondOffset), this.minHeight, this.maxHeight, trunkDirection);
			if (!secondPoses.isEmpty()) {
				List<BlockPos> thirdPoses = getSectionPoses(false, level, random, startPos.relative(Direction.UP), this.minHeight, this.maxHeight, trunkDirection);
				List<BlockPos> fourthPoses = getSectionPoses(false, level, random, startPos.relative(Direction.UP).relative(secondOffset), this.minHeight, this.maxHeight, trunkDirection);

                poses.addAll(firstPoses);
                poses.addAll(secondPoses);
                poses.addAll(thirdPoses);
                poses.addAll(fourthPoses);
				optionalOffsetDirection = Optional.of(secondOffset);
			}
		}

		return Pair.of(poses, optionalOffsetDirection);
	}

	@NotNull
	private static List<BlockPos> getSectionPoses(
		boolean requiresUnderneath,
		@NotNull LevelSimulatedReader level,
		@NotNull RandomSource random,
		@NotNull BlockPos startPos,
		int minHeight,
		int maxHeight,
		@NotNull Direction trunkDirection
	) {
		List<BlockPos> finalizedPoses = Lists.newArrayList();
		int height = random.nextIntBetweenInclusive(minHeight, maxHeight);
		int differenceFromMaxHeight = maxHeight - height;
		int directionOffset = differenceFromMaxHeight <= 0 ? 0 : random.nextIntBetweenInclusive(0, maxHeight - height);

		startPos = startPos.relative(trunkDirection, directionOffset);
		BlockPos endPos = startPos.relative(trunkDirection, height);
		BlockPos secondToEndPos = endPos.relative(trunkDirection.getOpposite());

		Iterable<BlockPos> poses = BlockPos.betweenClosed(startPos, endPos);
		int aboveSolidAmount = 0;
		boolean isEndAboveSolid = false;

		if (!requiresUnderneath) {
			poses.forEach(pos -> finalizedPoses.add(pos.immutable()));
			return finalizedPoses;
		}

		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (BlockPos blockPos : poses) {
			mutable.set(blockPos);
			if (TreeFeature.validTreePos(level, mutable)) {
				if (isPosSolidGround(level, mutable.move(Direction.DOWN))) {
					aboveSolidAmount += 1;
					mutable.move(Direction.UP);
					if (mutable.equals(endPos) || mutable.equals(secondToEndPos)) isEndAboveSolid = true;
				} else {
					mutable.move(Direction.UP);
					if (mutable.equals(startPos)) return List.of();
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

	private static @NotNull List<BlockPos> findStumpPoses(
		LevelSimulatedReader level,
		@NotNull RandomSource random,
		@NotNull BlockPos pos,
		@NotNull Direction trunkDirection,
		Direction offsetDirection
	) {
		for (int i = 0; i < STUMP_GEN_ATTEMPTS; i++) {
			int distance = STUMP_DISTANCE_FROM_TRUNK.sample(random);
			Direction stumpSearchDirection = trunkDirection.getOpposite();
			BlockPos searchStartPos = pos.relative(stumpSearchDirection, 1 + distance);
			BlockPos.MutableBlockPos stumpPos = searchStartPos.mutable();

			List<BlockPos> initialStumpPoses = getStumpPosesIfPossible(level, stumpPos, stumpSearchDirection, offsetDirection);
			if (!initialStumpPoses.isEmpty()) return initialStumpPoses;

			for (int step = 1; step <= STUMP_MAX_SEARCH_POSITIVE_Y; step++) {
				stumpPos.move(Direction.UP);
				List<BlockPos> stumpPoses = getStumpPosesIfPossible(level, stumpPos, stumpSearchDirection, offsetDirection);
				if (!stumpPoses.isEmpty()) return stumpPoses;
			}

			stumpPos.set(searchStartPos);
			for (int step = 1; step <= STUMP_MAX_SEARCH_NEGATIVE_Y; step++) {
				stumpPos.move(Direction.DOWN);
				List<BlockPos> stumpPoses = getStumpPosesIfPossible(level, stumpPos, stumpSearchDirection, offsetDirection);
				if (!stumpPoses.isEmpty()) return stumpPoses;
			}
		}

		return List.of();
	}

	@NotNull
	private static @Unmodifiable List<BlockPos> getStumpPosesIfPossible(
		@NotNull LevelSimulatedReader level,
		@NotNull BlockPos pos,
		@NotNull Direction stumpSearchDirection,
		Direction offsetDirection
	) {
		List<BlockPos> finalizedPoses = Lists.newArrayList();

		BlockPos.MutableBlockPos stumpPos = pos.mutable();
		BlockPos.MutableBlockPos belowStumpPos = stumpPos.mutable().move(Direction.DOWN);

		if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos)) {
			finalizedPoses.add(stumpPos.immutable());
		} else {
			return List.of();
		}

		stumpPos.setWithOffset(pos, offsetDirection);
		belowStumpPos.setWithOffset(stumpPos, Direction.DOWN);
		if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos)) {
			finalizedPoses.add(stumpPos.immutable());
		} else {
			return List.of();
		}

		stumpPos.setWithOffset(pos, stumpSearchDirection);
		belowStumpPos.setWithOffset(stumpPos, Direction.DOWN);
		if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos)) {
			finalizedPoses.add(stumpPos.immutable());
		} else {
			return List.of();
		}

		stumpPos.setWithOffset(pos, offsetDirection).move(stumpSearchDirection);
		belowStumpPos.setWithOffset(stumpPos, Direction.DOWN);
		if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos)) {
			finalizedPoses.add(stumpPos.immutable());
		} else {
			return List.of();
		}

		return finalizedPoses;
	}

	private static boolean canPlaceStumpAtPos(LevelSimulatedReader level, @NotNull BlockPos floorPos, BlockPos pos) {
		return isPosSolidGround(level, floorPos)
			&& level.isStateAtPosition(floorPos, blockState -> blockState.is(WWBlockTags.FALLEN_TREE_STUMP_PLACEABLE_ON))
			&& TreeFeature.validTreePos(level, pos)
			&& isFreeWithinStumpHeight(level, pos);
	}

	private static boolean isPosSolidGround(LevelSimulatedReader level, @NotNull BlockPos pos) {
		return !TreeFeature.validTreePos(level, pos) && !TreeFeature.isAirOrLeaves(level, pos);
	}

	private static boolean isFreeWithinStumpHeight(LevelSimulatedReader level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < STUMP_MAX_SEARCH_NEGATIVE_Y; i++) {
			if (!TreeFeature.validTreePos(level, mutablePos)) return false;
			mutablePos.move(Direction.UP);
		}
		return true;
	}

}
