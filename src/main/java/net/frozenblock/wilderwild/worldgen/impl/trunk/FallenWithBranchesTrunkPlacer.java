/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.impl.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.worldgen.impl.trunk.branch.TrunkBranchPlacement;
import net.frozenblock.wilderwild.worldgen.impl.util.TrunkPlacerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class FallenWithBranchesTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<FallenWithBranchesTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(Codec.floatRange(0F, 1F).fieldOf("success_in_water_chance").forGetter(trunkPlacer -> trunkPlacer.successInWaterChance))
			.and(BlockStateProvider.CODEC.fieldOf("hollowed_trunk_provider").forGetter(trunkPlacer -> trunkPlacer.hollowedTrunkProvider))
			.and(Codec.floatRange(0F, 1F).fieldOf("hollowed_log_chance").forGetter(trunkPlacer -> trunkPlacer.hollowedLogChance))
			.and(TrunkBranchPlacement.CODEC.fieldOf("trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.trunkBranchPlacement))
			.apply(instance, FallenWithBranchesTrunkPlacer::new));

	public final float successInWaterChance;
	public final BlockStateProvider hollowedTrunkProvider;
	public final float hollowedLogChance;
	public final TrunkBranchPlacement trunkBranchPlacement;


	public FallenWithBranchesTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		float successInWaterChance,
		BlockStateProvider hollowedTrunkProvider,
		float hollowedLogProbability,
		TrunkBranchPlacement trunkBranchPlacement
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.successInWaterChance = successInWaterChance;
		this.hollowedTrunkProvider = hollowedTrunkProvider;
		this.hollowedLogChance = hollowedLogProbability;
		this.trunkBranchPlacement = trunkBranchPlacement;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FALLEN_WITH_BRANCHES_TRUNK_PLACER;
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
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockStateProvider blockStateProvider = random.nextFloat() <= this.hollowedLogChance ? this.hollowedTrunkProvider : config.trunkProvider;
		int maxBranches = this.trunkBranchPlacement.getMaxBranchCount(random);
		Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		int generatedBranches = 0;

		if (TrunkPlacerHelper.isWaterAt(level, startPos) && random.nextFloat() >= this.successInWaterChance) return foliageAttachments;

		BlockPos endPos = startPos.relative(trunkDirection, height);
		BlockPos secondToEndPos = endPos.relative(trunkDirection.getOpposite());
		int aboveSolidAmount = 0;
		boolean isEndAboveSolid = false;
		Iterable<BlockPos> poses = BlockPos.betweenClosed(startPos, endPos);
		for (BlockPos blockPos : poses) {
			mutable.set(blockPos);
			if (TreeFeature.validTreePos(level, mutable)) {
				if (!TreeFeature.validTreePos(level, mutable.move(Direction.DOWN)) && !TreeFeature.isAirOrLeaves(level, mutable)) {
					aboveSolidAmount += 1;
					mutable.move(Direction.UP);
					if (mutable.equals(endPos) || mutable.equals(secondToEndPos)) isEndAboveSolid = true;
				} else {
					mutable.move(Direction.UP);
					if (mutable.equals(startPos)) return foliageAttachments;
				}
			} else {
				return foliageAttachments;
			}
		}

		if (isEndAboveSolid || ((double) aboveSolidAmount / (double) height) > 0.5D) {
			for (BlockPos blockPos : poses) {
				mutable.set(blockPos);
				this.placeLog(level, replacer, random, blockStateProvider, mutable, trunkDirection);
				if (this.trunkBranchPlacement.canPlaceBranch(random) && generatedBranches < maxBranches) {
					Direction branchDirection = random.nextFloat() <= 0.66F ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : Direction.Plane.VERTICAL.getRandomDirection(random);
					if (trunkDirection.getAxis() != branchDirection.getAxis()) {
						this.trunkBranchPlacement.generateExtraBranchForFallenLog(
							level,
							replacer,
							random,
							blockStateProvider,
							mutable,
							branchDirection,
							trunkDirection
						);
					}
					generatedBranches += 1;
				}
			}
		}

		return foliageAttachments;
	}

	private void placeLog(
		LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockStateProvider blockStateProvider,
		@NotNull BlockPos.MutableBlockPos pos,
		@NotNull Direction trunkDirection
	) {
		BlockState placementState = TrunkPlacerHelper.getLogBlockState(level, blockStateProvider, pos, trunkDirection, random);
		replacer.accept(pos, placementState);
	}

}
