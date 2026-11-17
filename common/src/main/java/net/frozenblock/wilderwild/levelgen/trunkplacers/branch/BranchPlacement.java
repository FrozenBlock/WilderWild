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

package net.frozenblock.wilderwild.levelgen.trunkplacers.branch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.wilderwild.levelgen.trunkplacers.TrunkPlacerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.Nullable;

public record BranchPlacement(
	Optional<Holder<ConfigPredicate>> condition,
	float probability,
	IntProvider maxCount,
	IntProvider cutoffFromTop,
	IntProvider length,
	float offsetLastLogProbability,
	int minLengthForOffset,
	float foliageProbability,
	IntProvider foliageRadiusOffset
) {
	public static final BranchPlacement EMPTY = new BranchPlacement(
		Optional.empty(),
		0F,
		ConstantInt.ZERO,
		ConstantInt.ZERO,
		ConstantInt.ZERO,
		0F,
		1,
		0F,
		ConstantInt.ZERO
	);
	public static final Codec<BranchPlacement> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ConfigPredicate.HOLDER_CODEC.optionalFieldOf("condition").forGetter(BranchPlacement::condition),
			Codec.floatRange(0F, 1F).fieldOf("probability").forGetter(BranchPlacement::probability),
			IntProviders.NON_NEGATIVE_CODEC.lenientOptionalFieldOf("max_count", ConstantInt.ZERO).forGetter(BranchPlacement::maxCount),
			IntProviders.NON_NEGATIVE_CODEC.lenientOptionalFieldOf("cutoff_from_top", ConstantInt.ZERO).forGetter(BranchPlacement::cutoffFromTop),
			IntProviders.NON_NEGATIVE_CODEC.fieldOf("length").forGetter(BranchPlacement::length),
			Codec.FLOAT.lenientOptionalFieldOf("offset_last_log_probability", 0F).forGetter(BranchPlacement::offsetLastLogProbability),
			Codec.intRange(0, 16).lenientOptionalFieldOf("minimum_length_for_offset", 1).forGetter(BranchPlacement::minLengthForOffset),
			Codec.FLOAT.lenientOptionalFieldOf("foliage_probability", 0F).forGetter(BranchPlacement::foliageProbability),
			IntProviders.CODEC.lenientOptionalFieldOf("foliage_radius_offset", ConstantInt.ZERO).forGetter(BranchPlacement::foliageRadiusOffset)
		).apply(instance, BranchPlacement::new)
	);

	public static Builder builder() {
		return new Builder();
	}

	public boolean canPlaceBranch(RandomSource random) {
		return random.nextFloat() <= this.probability;
	}

	public int getMaxBranchCount(RandomSource random) {
		return this.maxCount.sample(random);
	}

	public int getBranchLength(RandomSource random) {
		return this.length.sample(random);
	}

	public boolean canOffsetLastLog(RandomSource random, int length) {
		return random.nextFloat() <= this.offsetLastLogProbability && length > this.minLengthForOffset;
	}

	public boolean canPlaceFoliage(RandomSource random) {
		return random.nextFloat() <= this.foliageProbability;
	}

	public IntProvider getFoliageRadiusOffset() {
		return this.foliageRadiusOffset;
	}

	public int getFoliageRadiusOffset(RandomSource random) {
		return this.foliageRadiusOffset.sample(random);
	}

	public void generateExtraBranchForFallenLog(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		BlockStateProvider stateProvider,
		BlockPos origin,
		Direction direction,
		Direction trunkDirection
	) {
		if (!this.conditionMet()) return;

		final BlockPos.MutableBlockPos branchPos = origin.mutable();
		final int totalLength = this.getBranchLength(random);
		for (int length = 1; length <= totalLength; length++) {
			branchPos.setWithOffset(origin, direction.getStepX() * length, direction.getStepY() * length, direction.getStepZ() * length);

			final boolean canOffsetLog = length == totalLength && this.canOffsetLastLog(random, length);
			if (canOffsetLog) branchPos.move(trunkDirection);

			if (!TreeFeature.validTreePos(level, branchPos)) continue;

			final BlockState logState = TrunkPlacerHelper.getLogBlockState(level, stateProvider, branchPos, canOffsetLog ? trunkDirection : direction, random);
			trunkSetter.accept(branchPos, logState);
		}
	}

	public void generateExtraBranch(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		BlockStateProvider stateProvider,
		BlockPos origin,
		Direction direction,
		List<FoliagePlacer.FoliageAttachment> foliageAttachments
	) {
		if (!this.conditionMet()) return;

		final BlockPos.MutableBlockPos branchPos = origin.mutable();
		final int totalLength = this.getBranchLength(random);
		for (int length = 1; length <= totalLength; length++) {
			branchPos.setWithOffset(origin, direction.getStepX() * length, 0, direction.getStepZ() * length);

			final boolean isLastLog = length == totalLength;
			final boolean placeUpwards = isLastLog && this.canOffsetLastLog(random, length);
			if (placeUpwards) branchPos.move(Direction.UP);

			if (!TreeFeature.validTreePos(level, branchPos)) continue;

			final BlockState logState = TrunkPlacerHelper.getLogBlockState(level, stateProvider, branchPos, placeUpwards ? Direction.UP : direction, random);
			trunkSetter.accept(branchPos, logState);

			if (isLastLog && this.canPlaceFoliage(random)) {
				foliageAttachments.add(
					new FoliagePlacer.FoliageAttachment(
						branchPos.move(Direction.UP).immutable(),
						this.getFoliageRadiusOffset(random),
						false
					)
				);
			}
		}
	}

	private boolean conditionMet() {
		return this.condition.isEmpty() || this.condition.get().value().test();
	}

	public static class Builder {
		private @Nullable Holder<ConfigPredicate> condition;
		private float branchChance = 0F;
		private IntProvider maxBranchCount = ConstantInt.ZERO;
		private IntProvider branchCutoffFromTop = ConstantInt.ZERO;
		private IntProvider branchLength = ConstantInt.ZERO;
		private float offsetLastLogChance = 0F;
		private int minBranchLengthForOffset = 1;
		private float foliagePlacementChance = 0F;
		private IntProvider foliageRadiusOffset = ConstantInt.ZERO;

		public Builder() {}

		public Builder condition(Holder<ConfigPredicate> condition) {
			this.condition = condition;
			return this;
		}

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

		public Builder foliageRadiusOffset(IntProvider foliageRadiusOffset) {
			this.foliageRadiusOffset = foliageRadiusOffset;
			return this;
		}

		public BranchPlacement build() {
			return new BranchPlacement(
				Optional.ofNullable(this.condition),
				this.branchChance,
				this.maxBranchCount,
				this.branchCutoffFromTop,
				this.branchLength,
				this.offsetLastLogChance,
				this.minBranchLengthForOffset,
				this.foliagePlacementChance,
				this.foliageRadiusOffset
			);
		}
	}
}
