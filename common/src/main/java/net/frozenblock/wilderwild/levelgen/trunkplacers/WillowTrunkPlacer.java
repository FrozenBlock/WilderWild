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
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.branch.BranchPlacement;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class WillowTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<WillowTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> trunkPlacerParts(instance).and(
		instance.group(
			IntProviders.NON_NEGATIVE_CODEC.fieldOf("trunk_split_height").forGetter(trunkPlacer -> trunkPlacer.trunkSplitHeight),
			Codec.floatRange(0F, 1F).fieldOf("branch_split_gap_probability").forGetter(trunkPlacer -> trunkPlacer.branchSplitGapProbability),
			BranchPlacement.CODEC.optionalFieldOf("branch_placement", BranchPlacement.EMPTY).forGetter(trunkPlacer -> trunkPlacer.branchPlacement)
		)
	).apply(instance, WillowTrunkPlacer::new));
	private final IntProvider trunkSplitHeight;
	private final float branchSplitGapProbability;
	private final BranchPlacement branchPlacement;

	public WillowTrunkPlacer(
		int baseHeight,
		int heightRandA,
		int heightRandB,
		IntProvider trunkSplitHeight,
		float branchSplitGapChance,
		BranchPlacement branchPlacement
	) {
		super(baseHeight, heightRandA, heightRandB);
		this.trunkSplitHeight = trunkSplitHeight;
		this.branchSplitGapProbability = branchSplitGapChance;
		this.branchPlacement = branchPlacement;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.WILLOW_TRUNK_PLACER.get();
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
		final List<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final BlockPos.MutableBlockPos branchMutable = new BlockPos.MutableBlockPos();
		int splitHeight = this.trunkSplitHeight.sample(random);

		int xOffset = 0;
		int zOffset = 0;
		for (int y = 0; y < treeHeight; ++y) {
			mutable.setWithOffset(origin, xOffset, y, zOffset);
			if (y == splitHeight) {
				final Direction splitDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				final BlockPos splitPos = mutable.immutable();
				xOffset += splitDirection.getStepX();
				zOffset += splitDirection.getStepZ();
				mutable.setWithOffset(origin, xOffset, y, zOffset);

				for (Direction branchDirection : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
					if (!this.branchPlacement.canPlaceBranch(random)) continue;

					if (random.nextFloat() <= this.branchSplitGapProbability && splitDirection != branchDirection) {
						branchMutable.set(splitPos);
					} else {
						branchMutable.set(mutable);
					}
					this.branchPlacement.generateExtraBranch(
						level,
						trunkSetter,
						random,
						tree.trunkProvider(),
						branchMutable.immutable(),
						branchDirection,
						attachments
					);
				}
			}

			this.placeLog(level, trunkSetter, random, mutable, tree);
			if (y == treeHeight - 1) attachments.add(new FoliagePlacer.FoliageAttachment(mutable.setWithOffset(origin, xOffset, y + 1, zOffset), 0, false));
		}
		return attachments;
	}
}
