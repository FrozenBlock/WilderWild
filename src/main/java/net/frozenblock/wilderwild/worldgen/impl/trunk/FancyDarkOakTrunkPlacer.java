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
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.worldgen.impl.trunk.branch.TrunkBranchPlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class FancyDarkOakTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<FancyDarkOakTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		trunkPlacerParts(instance)
			.and(TrunkBranchPlacement.CODEC.fieldOf("trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.trunkBranchPlacement))
			.apply(instance, FancyDarkOakTrunkPlacer::new)
	);

	private final TrunkBranchPlacement trunkBranchPlacement;

	public FancyDarkOakTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, TrunkBranchPlacement trunkBranchPlacement) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.trunkBranchPlacement = trunkBranchPlacement;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FANCY_DARK_OAK_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		int freeTreeHeight,
		@NotNull BlockPos pos,
		@NotNull TreeConfiguration config
	) {
		int r;
		int q;
		ArrayList<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		BlockPos blockPos = pos.below();
		DarkOakTrunkPlacer.setDirtAt(level, replacer, random, blockPos, config);
		DarkOakTrunkPlacer.setDirtAt(level, replacer, random, blockPos.east(), config);
		DarkOakTrunkPlacer.setDirtAt(level, replacer, random, blockPos.south(), config);
		DarkOakTrunkPlacer.setDirtAt(level, replacer, random, blockPos.south().east(), config);
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		int maxBranchCount = this.trunkBranchPlacement.getMaxBranchCount(random);
		int extraLogs = 0;
		int i = freeTreeHeight - random.nextInt(4);
		int j = 2 - random.nextInt(3);
		int k = pos.getX();
		int l = pos.getY();
		int m = pos.getZ();
		int n = k;
		int o = m;
		int p = l + freeTreeHeight - 1;
		for (q = 0; q < freeTreeHeight; ++q) {
			BlockPos blockPos2;
			if (q >= i && j > 0) {
				n += direction.getStepX();
				o += direction.getStepZ();
				--j;
			}
			if (!TreeFeature.isAirOrLeaves(level, blockPos2 = new BlockPos(n, l + q, o))) continue;
			boolean placedWest = this.placeLog(level, replacer, random, blockPos2, config);
			boolean placedEast = this.placeLog(level, replacer, random, blockPos2.east(), config);
			boolean placedSouth = this.placeLog(level, replacer, random, blockPos2.south(), config);
			boolean placedSouthEast = this.placeLog(level, replacer, random, blockPos2.east().south(), config);
			if (extraLogs < maxBranchCount && this.trunkBranchPlacement.canPlaceBranch(random) && (q * 3) > freeTreeHeight) {
				Direction branchDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				BlockPos.MutableBlockPos branchPos = blockPos2.mutable();
				ArrayList<BlockPos> possiblePoses = new ArrayList<>();
				if (branchDirection == Direction.NORTH) {
					if (placedWest) possiblePoses.add(blockPos2);
					if (placedEast) possiblePoses.add(blockPos2.east());
				} else if (branchDirection == Direction.EAST) {
					if (placedEast) possiblePoses.add(blockPos2.east());
					if (placedSouthEast) possiblePoses.add(blockPos2.east().south());
				} else if (branchDirection == Direction.SOUTH) {
					if (placedSouth) possiblePoses.add(blockPos2.south());
					if (placedSouthEast) possiblePoses.add(blockPos2.east().south());
				} else if (branchDirection == Direction.WEST) {
					if (placedWest) possiblePoses.add(blockPos2);
					if (placedSouth) possiblePoses.add(blockPos2.south());
				}
				if (!possiblePoses.isEmpty()) {
					branchPos.set(possiblePoses.get((int) (random.nextDouble() * possiblePoses.size())));
					this.trunkBranchPlacement.generateExtraBranch(
						level,
						replacer,
						random,
						config.trunkProvider,
						branchPos,
						branchDirection,
						list
					);
					extraLogs += 1;
				}
			}
		}
		list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(n, p, o), 0, true));
		for (q = -1; q <= 2; ++q) {
			for (r = -1; r <= 2; ++r) {
				if (q >= 0 && q <= 1 && r >= 0 && r <= 1 || random.nextInt(3) > 0) continue;
				int s = random.nextInt(3) + 2;
				for (int t = 0; t < s; ++t) {
					this.placeLog(level, replacer, random, new BlockPos(k + q, p - t - 1, m + r), config);
				}
				list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(n + q, p, o + r), 0, false));
			}
		}

		return list;
	}
}
