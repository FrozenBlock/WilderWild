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

package net.frozenblock.wilderwild.worldgen.impl.trunk.branch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.worldgen.impl.util.TrunkPlacerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public class TrunkBranchPlacement {
	public static final MapCodec<TrunkBranchPlacement> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			Codec.floatRange(0F, 1F).fieldOf("branch_placement_chance").forGetter(trunkPlacer -> trunkPlacer.branchChance),
			IntProvider.NON_NEGATIVE_CODEC.lenientOptionalFieldOf("max_branch_count", ConstantInt.ZERO).forGetter(trunkPlacer -> trunkPlacer.maxBranchCount),
			IntProvider.NON_NEGATIVE_CODEC.lenientOptionalFieldOf("branch_cutoff_from_top", ConstantInt.ZERO).forGetter(trunkPlacer -> trunkPlacer.branchCutoffFromTop),
			IntProvider.NON_NEGATIVE_CODEC.fieldOf("branch_length").forGetter(trunkPlacer -> trunkPlacer.branchLength),
			Codec.FLOAT.fieldOf("offset_last_log_chance").forGetter(trunkPlacer -> trunkPlacer.offsetLastLogChance),
			Codec.intRange(0, 16).fieldOf("minimum_branch_length_for_offset").forGetter(trunkPlacer -> trunkPlacer.minBranchLengthForOffset),
			Codec.FLOAT.fieldOf("foliage_placement_chance").forGetter(trunkPlacer -> trunkPlacer.foliagePlacementChance),
			Codec.intRange(0, 3).lenientOptionalFieldOf("foliage_radius_shrink", 0).forGetter(trunkPlacer -> trunkPlacer.foliageRadiusShrink)
		).apply(instance, TrunkBranchPlacement::new)
	);

	private final float branchChance;
	private final IntProvider maxBranchCount;
	private final IntProvider branchCutoffFromTop;
	private final IntProvider branchLength;
	private final float offsetLastLogChance;
	private final int minBranchLengthForOffset;
	private final float foliagePlacementChance;
	private final int foliageRadiusShrink;

	public TrunkBranchPlacement(
		float branchChance,
		@NotNull IntProvider maxBranchCount,
		@NotNull IntProvider branchCutoffFromTop,
		@NotNull IntProvider branchLength,
		float offsetLastLogChance,
		int minBranchLengthForOffset,
		float foliagePlacementChance,
		int foliageRadiusShrink
	) {
		this.branchChance = branchChance;
		this.maxBranchCount = maxBranchCount;
		this.branchCutoffFromTop = branchCutoffFromTop;
		this.branchLength = branchLength;
		this.offsetLastLogChance = offsetLastLogChance;
		this.minBranchLengthForOffset = minBranchLengthForOffset;
		this.foliagePlacementChance = foliagePlacementChance;
		this.foliageRadiusShrink = foliageRadiusShrink;
	}

	public boolean canPlaceBranch(@NotNull RandomSource random) {
		return random.nextFloat() <= this.branchChance;
	}

	public int getMaxBranchCount(RandomSource random) {
		return this.maxBranchCount.sample(random);
	}

	public IntProvider getBranchCutoffFromTop() {
		return this.branchCutoffFromTop;
	}

	public int getBranchLength(RandomSource random) {
		return this.branchLength.sample(random);
	}

	public boolean canOffsetLastLog(@NotNull RandomSource random, int length) {
		return random.nextFloat() <= this.offsetLastLogChance && length > this.minBranchLengthForOffset;
	}

	public boolean canPlaceFoliage(@NotNull RandomSource random) {
		return random.nextFloat() <= this.foliagePlacementChance;
	}

	public int getFoliageRadiusShrink() {
		return this.foliageRadiusShrink;
	}

	public void generateExtraBranchForFallenLog(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockStateProvider blockStateProvider,
		@NotNull BlockPos startPos,
		@NotNull Direction direction,
		@NotNull Direction trunkDirection
	) {
		BlockPos.MutableBlockPos branchPos = startPos.mutable();
		int totalLength = this.getBranchLength(random);
		boolean hasPassedConfigCheck = false;

		for (int length = 1; length <= totalLength; length++) {
			branchPos.setWithOffset(startPos, direction.getStepX() * length, direction.getStepY() * length, direction.getStepZ() * length);

			boolean canOffsetLog = length == totalLength && this.canOffsetLastLog(random, length);
			if (canOffsetLog) branchPos.move(trunkDirection);

			if (TreeFeature.validTreePos(level, branchPos)) {
				BlockState logState = TrunkPlacerHelper.getLogBlockState(level, blockStateProvider, branchPos, canOffsetLog ? trunkDirection : direction, random);
				if (hasPassedConfigCheck || verifyBranchMatchesConfig(logState)) {
					hasPassedConfigCheck = true;
					replacer.accept(branchPos, logState);
				} else {
					return;
				}
			}
		}
	}

	public void generateExtraBranch(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockStateProvider blockStateProvider,
		@NotNull BlockPos startPos,
		@NotNull Direction direction,
		List<FoliagePlacer.FoliageAttachment> foliageAttachments
	) {
		BlockPos.MutableBlockPos branchPos = startPos.mutable();
		int totalLength = this.getBranchLength(random);
		boolean hasPassedConfigCheck = false;

		for (int length = 1; length <= totalLength; length++) {
			branchPos.setWithOffset(startPos, direction.getStepX() * length, 0, direction.getStepZ() * length);

			boolean isLastLog = length == totalLength;
			boolean placeUpwards = isLastLog && this.canOffsetLastLog(random, length);
			if (placeUpwards) branchPos.move(Direction.UP);

			if (TreeFeature.validTreePos(level, branchPos)) {
				BlockState logState = TrunkPlacerHelper.getLogBlockState(level, blockStateProvider, branchPos, placeUpwards ? Direction.UP : direction, random);
				if (hasPassedConfigCheck || verifyBranchMatchesConfig(logState)) {
					hasPassedConfigCheck = true;
					replacer.accept(branchPos, logState);
					if (isLastLog && this.canPlaceFoliage(random)) {
						foliageAttachments.add(new FoliagePlacer.FoliageAttachment(branchPos.move(Direction.UP).immutable(), -this.getFoliageRadiusShrink(), false));
					}
				} else {
					return;
				}
			}
		}
	}

	private static boolean verifyBranchMatchesConfig(BlockState logState) {
		if (!WWWorldgenConfig.BIRCH_BRANCHES && logState.is(BlockTags.BIRCH_LOGS)) return false;
		if (!WWWorldgenConfig.OAK_BRANCHES && logState.is(BlockTags.OAK_LOGS)) return false;
		if (!WWWorldgenConfig.DARK_OAK_BRANCHES && logState.is(BlockTags.DARK_OAK_LOGS)) return false;
		return true;
	}

	public static class Builder {
		private float branchChance = 0F;
		private IntProvider maxBranchCount = ConstantInt.ZERO;
		private IntProvider branchCutoffFromTop = ConstantInt.ZERO;
		private IntProvider branchLength = ConstantInt.ZERO;
		private float offsetLastLogChance = 0F;
		private int minBranchLengthForOffset = 1;
		private float foliagePlacementChance = 0F;
		private int foliageRadiusShrink = 0;

		public Builder() {}

		public Builder branchChance(float branchChance) {
			this.branchChance = branchChance;
			return this;
		}

		public Builder maxBranchCount(int maxBranchCount) {
			this.maxBranchCount = ConstantInt.of(maxBranchCount);
			return this;
		}

		public Builder maxBranchCount(IntProvider maxBranchCount) {
			this.maxBranchCount = maxBranchCount;
			return this;
		}

		public Builder branchCutoffFromTop(IntProvider branchCutoffFromTop) {
			this.branchCutoffFromTop = branchCutoffFromTop;
			return this;
		}

		public Builder branchLength(IntProvider branchLength) {
			this.branchLength = branchLength;
			return this;
		}

		public Builder offsetLastLogChance(Float offsetLastLogChance) {
			this.offsetLastLogChance = offsetLastLogChance;
			return this;
		}

		public Builder foliagePlacementChance(Float foliagePlacementChance) {
			this.foliagePlacementChance = foliagePlacementChance;
			return this;
		}

		public Builder minBranchLengthForOffset(int minBranchLengthForOffset) {
			this.minBranchLengthForOffset = minBranchLengthForOffset;
			return this;
		}

		public Builder foliageRadiusShrink(int foliageRadiusShrink) {
			this.foliageRadiusShrink = foliageRadiusShrink;
			return this;
		}

		public TrunkBranchPlacement build() {
			return new TrunkBranchPlacement(
				this.branchChance,
				this.maxBranchCount,
				this.branchCutoffFromTop,
				this.branchLength,
				this.offsetLastLogChance,
				this.minBranchLengthForOffset,
				this.foliagePlacementChance,
				this.foliageRadiusShrink
			);
		}
	}
}
