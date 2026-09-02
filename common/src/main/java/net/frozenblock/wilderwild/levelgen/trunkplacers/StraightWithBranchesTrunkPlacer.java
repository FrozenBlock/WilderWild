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
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.branch.BranchPlacement;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class StraightWithBranchesTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<StraightWithBranchesTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(BranchPlacement.CODEC.optionalFieldOf("branch_placement", BranchPlacement.EMPTY).forGetter(trunkPlacer -> trunkPlacer.branchPlacement))
			.apply(instance, StraightWithBranchesTrunkPlacer::new));
	private final BranchPlacement branchPlacement;

	public StraightWithBranchesTrunkPlacer(
		int baseHeight,
		int heightRandA,
		int heightRandB,
		BranchPlacement branchPlacement
	) {
		super(baseHeight, heightRandA, heightRandB);
		this.branchPlacement = branchPlacement;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.STRAIGHT_WITH_BRANCHES_TRUNK_PLACER.get();
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
		placeBelowTrunkBlock(level, trunkSetter, random, origin.below(), tree);

		final List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final int maxBranchCount = this.branchPlacement.getMaxBranchCount(random);
		final int branchCutoffFromTop = this.branchPlacement.cutoffFromTop().sample(random);

		int extraLogs = 0;
		for (int y = 0; y < treeHeight; ++y) {
			final int dy = origin.getY() + y;
			if (this.placeLog(level, trunkSetter, random, mutable.set(origin.getX(), dy, origin.getZ()), tree)
				&& y < treeHeight - 1
				&& this.branchPlacement.canPlaceBranch(random)
				&& extraLogs < maxBranchCount
				&& (treeHeight - 4) - y <= branchCutoffFromTop
			) {
				this.branchPlacement.generateExtraBranch(
					level,
					trunkSetter,
					random,
					tree.trunkProvider().value(),
					mutable.immutable(),
					Direction.Plane.HORIZONTAL.getRandomDirection(random),
					foliageAttachments
				);
				++extraLogs;
			}
			if (y == treeHeight - 1) foliageAttachments.add(new FoliagePlacer.FoliageAttachment(mutable.set(origin.getX(), dy + 1, origin.getZ()), 0, false));
		}

		return foliageAttachments;
	}
}
