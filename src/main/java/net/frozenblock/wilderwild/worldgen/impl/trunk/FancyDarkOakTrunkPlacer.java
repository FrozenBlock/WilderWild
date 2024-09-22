/*
 * Copyright 2023-2024 FrozenBlock
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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class FancyDarkOakTrunkPlacer extends TrunkPlacer {
	public static final Codec<FancyDarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
		trunkPlacerParts(instance)
			.and(
				instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("branch_chance").forGetter((trunkPlacer) -> trunkPlacer.branchChance),
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_branch_count").forGetter((trunkPlacer) -> trunkPlacer.maxLogCount),
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("branch_length").forGetter((trunkPlacer) -> trunkPlacer.branchLength)
				)
			).apply(instance, FancyDarkOakTrunkPlacer::new));

	private final float branchChance;
	private final IntProvider maxLogCount;
	private final IntProvider branchLength;

	public FancyDarkOakTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, float branchChance, @NotNull IntProvider maxLogCount, @NotNull IntProvider branchLength) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.branchChance = branchChance;
		this.maxLogCount = maxLogCount;
		this.branchLength = branchLength;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FANCY_DARK_OAK_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, int freeTreeHeight, @NotNull BlockPos pos, @NotNull TreeConfiguration config) {
		int r;
		int q;
		ArrayList<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		BlockPos blockPos = pos.below();
		DarkOakTrunkPlacer.setDirtAt(level, blockSetter, random, blockPos, config);
		DarkOakTrunkPlacer.setDirtAt(level, blockSetter, random, blockPos.east(), config);
		DarkOakTrunkPlacer.setDirtAt(level, blockSetter, random, blockPos.south(), config);
		DarkOakTrunkPlacer.setDirtAt(level, blockSetter, random, blockPos.south().east(), config);
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		int maxLogs = this.maxLogCount.sample(random);
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
			boolean placedWest = this.placeLog(level, blockSetter, random, blockPos2, config);
			boolean placedEast = this.placeLog(level, blockSetter, random, blockPos2.east(), config);
			boolean placedSouth = this.placeLog(level, blockSetter, random, blockPos2.south(), config);
			boolean placedSouthEast = this.placeLog(level, blockSetter, random, blockPos2.east().south(), config);
			if (extraLogs < maxLogs && random.nextFloat() < this.branchChance && (q * 3) > freeTreeHeight) {
				Direction chosenRandomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				int length = this.branchLength.sample(random);
				BlockPos.MutableBlockPos extraPos = blockPos2.mutable();
				ArrayList<BlockPos> possiblePoses = new ArrayList<>();
				if (chosenRandomDirection == Direction.NORTH) {
					if (placedWest) {
						possiblePoses.add(blockPos2);
					}
					if (placedEast) {
						possiblePoses.add(blockPos2.east());
					}
				} else if (chosenRandomDirection == Direction.EAST) {
					if (placedEast) {
						possiblePoses.add(blockPos2.east());
					}
					if (placedSouthEast) {
						possiblePoses.add(blockPos2.east().south());
					}
				} else if (chosenRandomDirection == Direction.SOUTH) {
					if (placedSouth) {
						possiblePoses.add(blockPos2.south());
					}
					if (placedSouthEast) {
						possiblePoses.add(blockPos2.east().south());
					}
				} else if (chosenRandomDirection == Direction.WEST) {
					if (placedWest) {
						possiblePoses.add(blockPos2);
					}
					if (placedSouth) {
						possiblePoses.add(blockPos2.south());
					}
				}
				if (!possiblePoses.isEmpty()) {
					extraPos.set(possiblePoses.get((int) (random.nextDouble() * possiblePoses.size())));
					this.generateExtraBranch(level, blockSetter, random, config, extraPos, chosenRandomDirection, length, list);
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
					this.placeLog(level, blockSetter, random, new BlockPos(k + q, p - t - 1, m + r), config);
				}
				list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(n + q, p, o + r), 0, false));
			}
		}

		return list;
	}

	private void generateExtraBranch(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos pos, @NotNull Direction direction, int length, @NotNull List<FoliagePlacer.FoliageAttachment> foliageAttachments) {
		int j = pos.getX();
		int k = pos.getZ();
		int y = pos.getY();
		int placedLength = length - 1;
		for (int l = 0; l < length; ++l) {
			boolean lastOne = l == placedLength;
			if (lastOne) {
				y += 1;
			}
			j += direction.getStepX();
			k += direction.getStepZ();
			if (TreeFeature.validTreePos(level, pos.set(j, y, k))) {
				if (config.trunkProvider.getState(random, pos.set(j, y, k)).hasProperty(BlockStateProperties.AXIS)) {
					Direction.Axis axis = direction.getStepX() != 0 ? Direction.Axis.X : Direction.Axis.Z;
					replacer.accept(pos.set(j, y, k), config.trunkProvider.getState(random, pos.set(j, y, k)).setValue(BlockStateProperties.AXIS, axis));
					if (lastOne) {
						foliageAttachments.add(new FoliagePlacer.FoliageAttachment(pos.move(Direction.UP), 0, false));
					}
				}
			}
		}
	}
}
