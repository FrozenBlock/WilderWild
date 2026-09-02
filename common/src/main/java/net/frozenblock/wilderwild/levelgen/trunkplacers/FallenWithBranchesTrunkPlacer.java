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
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.levelgen.trunkplacers.branch.BranchPlacement;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FallenWithBranchesTrunkPlacer extends TrunkPlacer {
	private static final IntProvider STUMP_DISTANCE_FROM_TRUNK = UniformInt.of(1, 3);
	private static final float TWO_TALL_STUMP_CHANCE = 0.2F;
	private static final int STUMP_MAX_SEARCH_POSITIVE_Y = 8;
	private static final int STUMP_MAX_SEARCH_NEGATIVE_Y = 2;
	public static final MapCodec<FallenWithBranchesTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(Codec.floatRange(0F, 1F).fieldOf("water_success_probability").forGetter(trunkPlacer -> trunkPlacer.successInWaterChance))
			.and(BlockStateProvider.CODEC.fieldOf("hollow_state").forGetter(trunkPlacer -> trunkPlacer.hollowedTrunkProvider))
			.and(Codec.floatRange(0F, 1F).fieldOf("hollow_probability").forGetter(trunkPlacer -> trunkPlacer.hollowedLogChance))
			.and(BranchPlacement.CODEC.optionalFieldOf("branch_placement", BranchPlacement.EMPTY).forGetter(trunkPlacer -> trunkPlacer.branchPlacement))
			.and(Codec.floatRange(0F, 1F).fieldOf("stump_probability").forGetter(trunkPlacer -> trunkPlacer.stumpProbability))
			.apply(instance, FallenWithBranchesTrunkPlacer::new));

	public final float successInWaterChance;
	public final Holder<BlockStateProvider> hollowedTrunkProvider;
	public final float hollowedLogChance;
	public final BranchPlacement branchPlacement;
	public final float stumpProbability;

	public FallenWithBranchesTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		float successInWaterChance,
		Holder<BlockStateProvider> hollowedTrunkProvider,
		float hollowedLogProbability,
		BranchPlacement branchPlacement,
		float stumpProbability
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.successInWaterChance = successInWaterChance;
		this.hollowedTrunkProvider = hollowedTrunkProvider;
		this.hollowedLogChance = hollowedLogProbability;
		this.branchPlacement = branchPlacement;
		this.stumpProbability = stumpProbability;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FALLEN_WITH_BRANCHES_TRUNK_PLACER.get();
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
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final BlockStateProvider stateProvider = (WWWorldgenConfig.HOLLOWED_FALLEN_TREE_GENERATION.get() && random.nextFloat() <= this.hollowedLogChance)
			? this.hollowedTrunkProvider.value()
			: tree.trunkProvider().value();
		final int maxBranches = this.branchPlacement.getMaxBranchCount(random);
		final Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		int generatedBranches = 0;

		if (TrunkPlacerHelper.isWaterAt(level, origin) && random.nextFloat() >= this.successInWaterChance) return foliageAttachments;

		final BlockPos endPos = origin.relative(trunkDirection, treeHeight);
		final BlockPos secondToEndPos = endPos.relative(trunkDirection.getOpposite());
		int aboveSolidAmount = 0;
		boolean isEndAboveSolid = false;
		final Iterable<BlockPos> poses = BlockPos.betweenClosed(origin, endPos);
		for (BlockPos blockPos : poses) {
			mutable.set(blockPos);
			if (TreeFeature.validTreePos(level, mutable)) {
				if (isPosSolidGround(level, mutable.move(Direction.DOWN))) {
					aboveSolidAmount += 1;
					mutable.move(Direction.UP);
					if (mutable.equals(endPos) || mutable.equals(secondToEndPos)) isEndAboveSolid = true;
				} else {
					mutable.move(Direction.UP);
					if (mutable.equals(origin)) return foliageAttachments;
				}
			} else {
				return foliageAttachments;
			}
		}

		if (isEndAboveSolid || ((double) aboveSolidAmount / (double) treeHeight) > 0.5D) {
			for (BlockPos blockPos : poses) {
				mutable.set(blockPos);
				this.placeLog(level, trunkSetter, random, stateProvider, mutable, trunkDirection);
				if (this.branchPlacement.canPlaceBranch(random) && generatedBranches < maxBranches) {
					final Direction branchDirection = random.nextFloat() <= 0.66F ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : Direction.Plane.VERTICAL.getRandomDirection(random);
					if (trunkDirection.getAxis() != branchDirection.getAxis()) {
						this.branchPlacement.generateExtraBranchForFallenLog(
							level,
							trunkSetter,
							random,
							stateProvider,
							mutable,
							branchDirection,
							trunkDirection
						);
					}
					generatedBranches += 1;
				}
			}
		}

		if (random.nextFloat() <= this.stumpProbability) {
			final Optional<BlockPos.MutableBlockPos> optionalStumpPos = this.findStumpPos(level, random, origin, trunkDirection);
			if (optionalStumpPos.isPresent()) {
				final BlockPos.MutableBlockPos stumpPos = optionalStumpPos.get();
				this.placeLog(level, trunkSetter, random, stumpPos, tree);
				if (random.nextFloat() <= TWO_TALL_STUMP_CHANCE) this.placeLog(level, trunkSetter, random, stumpPos.move(Direction.UP), tree);
			}
		}

		return foliageAttachments;
	}

	private Optional<BlockPos.MutableBlockPos> findStumpPos(
		WorldGenLevel level,
		RandomSource random,
		BlockPos pos,
		Direction trunkDirection
	) {
		final int distance = STUMP_DISTANCE_FROM_TRUNK.sample(random);
		final Direction stumpSearchDirection = trunkDirection.getOpposite();
		pos = pos.relative(stumpSearchDirection, 1 + distance);
		final BlockPos.MutableBlockPos stumpPos = pos.mutable();
		final BlockPos.MutableBlockPos belowStumpPos = stumpPos.mutable().move(Direction.DOWN);
		final BlockPos.MutableBlockPos aboveStumpPos = stumpPos.mutable().move(Direction.UP);

		if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos, aboveStumpPos)) return Optional.of(stumpPos);

		for (int step = 1; step <= STUMP_MAX_SEARCH_POSITIVE_Y; step++) {
			stumpPos.move(Direction.UP);
			setBelowAndAbovePoses(stumpPos, belowStumpPos, aboveStumpPos);
			if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos, aboveStumpPos)) return Optional.of(stumpPos);
		}

		stumpPos.set(pos);
		for (int step = 1; step <= STUMP_MAX_SEARCH_NEGATIVE_Y; step++) {
			stumpPos.move(Direction.DOWN);
			setBelowAndAbovePoses(stumpPos, belowStumpPos, aboveStumpPos);
			if (canPlaceStumpAtPos(level, belowStumpPos, stumpPos, aboveStumpPos)) return Optional.of(stumpPos);
		}

		return Optional.empty();
	}

	private static boolean canPlaceStumpAtPos(WorldGenLevel level, BlockPos floorPos, BlockPos pos, BlockPos abovePos) {
		return isPosSolidGround(level, floorPos)
			&& level.isStateAtPosition(floorPos, blockState -> blockState.is(WWBlockTags.FALLEN_TREE_STUMP_PLACEABLE_ON))
			&& TreeFeature.validTreePos(level, pos)
			&& TreeFeature.validTreePos(level, abovePos);
	}

	private static boolean isPosSolidGround(WorldGenLevel level, BlockPos pos) {
		return !TreeFeature.validTreePos(level, pos) && !TreeFeature.isAirOrLeaves(level, pos);
	}

	private void placeLog(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		BlockStateProvider stateProvider,
		BlockPos.MutableBlockPos pos,
		Direction trunkDirection
	) {
		final BlockState placementState = TrunkPlacerHelper.getLogBlockState(level, stateProvider, pos, trunkDirection, random);
		trunkSetter.accept(pos, placementState);
	}

	private static void setBelowAndAbovePoses(BlockPos pos, BlockPos.MutableBlockPos belowPos, BlockPos.MutableBlockPos abovePos) {
		belowPos.setWithOffset(pos, Direction.DOWN);
		abovePos.setWithOffset(pos, Direction.UP);
	}

}
