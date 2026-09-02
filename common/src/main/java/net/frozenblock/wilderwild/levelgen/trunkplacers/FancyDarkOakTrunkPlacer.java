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
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.branch.BranchPlacement;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FancyDarkOakTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<FancyDarkOakTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(BranchPlacement.CODEC.optionalFieldOf("branch_placement", BranchPlacement.EMPTY).forGetter(trunkPlacer -> trunkPlacer.branchPlacement))
			.apply(instance, FancyDarkOakTrunkPlacer::new)
	);

	private final BranchPlacement branchPlacement;

	public FancyDarkOakTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BranchPlacement branchPlacement) {
		super(baseHeight, heightRandA, heightRandB);
		this.branchPlacement = branchPlacement;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FANCY_DARK_OAK_TRUNK_PLACER.get();
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
		final ArrayList<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();
		final BlockPos below = origin.below();
		DarkOakTrunkPlacer.placeBelowTrunkBlock(level, trunkSetter, random, below, tree);
		DarkOakTrunkPlacer.placeBelowTrunkBlock(level, trunkSetter, random, below.east(), tree);
		DarkOakTrunkPlacer.placeBelowTrunkBlock(level, trunkSetter, random, below.south(), tree);
		DarkOakTrunkPlacer.placeBelowTrunkBlock(level, trunkSetter, random, below.south().east(), tree);

		final Direction leanDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		final int maxBranchCount = this.branchPlacement.getMaxBranchCount(random);
		int extraLogs = 0;
		final int leanHeight = treeHeight - random.nextInt(4);
		int leanSteps = 2 - random.nextInt(3);
		final int x = origin.getX();
		final int y = origin.getY();
		final int z = origin.getZ();
		int tx = x;
		int tz = z;
		final int ey = y + treeHeight - 1;
		for (int dy = 0; dy < treeHeight; ++dy) {
			if (dy >= leanHeight && leanSteps > 0) {
				tx += leanDirection.getStepX();
				tz += leanDirection.getStepZ();
				--leanSteps;
			}

			final int yy = y + dy;
			final BlockPos blockPos = new BlockPos(tx, yy, tz);
			if (!TreeFeature.isAirOrLeaves(level, blockPos)) continue;

			final boolean placedWest = this.placeLog(level, trunkSetter, random, blockPos, tree);
			final boolean placedEast = this.placeLog(level, trunkSetter, random, blockPos.east(), tree);
			final boolean placedSouth = this.placeLog(level, trunkSetter, random, blockPos.south(), tree);
			final boolean placedSouthEast = this.placeLog(level, trunkSetter, random, blockPos.east().south(), tree);
			if (extraLogs < maxBranchCount && this.branchPlacement.canPlaceBranch(random) && (dy * 3) > treeHeight) {
				final Direction branchDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				final BlockPos.MutableBlockPos branchPos = blockPos.mutable();
				final ArrayList<BlockPos> possibleBranchPositions = new ArrayList<>();
				if (branchDirection == Direction.NORTH) {
					if (placedWest) possibleBranchPositions.add(blockPos);
					if (placedEast) possibleBranchPositions.add(blockPos.east());
				} else if (branchDirection == Direction.EAST) {
					if (placedEast) possibleBranchPositions.add(blockPos.east());
					if (placedSouthEast) possibleBranchPositions.add(blockPos.east().south());
				} else if (branchDirection == Direction.SOUTH) {
					if (placedSouth) possibleBranchPositions.add(blockPos.south());
					if (placedSouthEast) possibleBranchPositions.add(blockPos.east().south());
				} else if (branchDirection == Direction.WEST) {
					if (placedWest) possibleBranchPositions.add(blockPos);
					if (placedSouth) possibleBranchPositions.add(blockPos.south());
				}

				if (!possibleBranchPositions.isEmpty()) {
					branchPos.set(Util.getRandom(possibleBranchPositions, random));
					this.branchPlacement.generateExtraBranch(
						level,
						trunkSetter,
						random,
						tree.trunkProvider().value(),
						branchPos,
						branchDirection,
						attachments
					);
					extraLogs += 1;
				}
			}
		}

		attachments.add(new FoliagePlacer.FoliageAttachment(new BlockPos(tx, ey, tz), 0, true));

		for (int ox = -1; ox <= 2; ++ox) {
			for (int oz = -1; oz <= 2; ++oz) {
				if (ox >= 0 && ox <= 1 && oz >= 0 && oz <= 1 || random.nextInt(3) > 0) continue;
				final int length = random.nextInt(3) + 2;
				for (int branchY = 0; branchY < length; ++branchY) {
					this.placeLog(level, trunkSetter, random, new BlockPos(x + ox, ey - branchY - 1, z + oz), tree);
				}
				attachments.add(new FoliagePlacer.FoliageAttachment(new BlockPos(x + ox, ey, z + oz), 0, false));
			}
		}

		return attachments;
	}
}
