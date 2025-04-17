/*
 * Copyright 2025 FrozenBlock
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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class WillowTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<WillowTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		trunkPlacerParts(instance)
			.and(
				instance.group(
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("trunk_split_height").forGetter(trunkPlacer -> trunkPlacer.trunkSplitHeight),
					Codec.floatRange(0F, 1F).fieldOf("branch_split_gap_chance").forGetter(trunkPlacer -> trunkPlacer.branchSplitGapChance),
					TrunkBranchPlacement.CODEC.fieldOf("trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.trunkBranchPlacement)
				)
			).apply(instance, WillowTrunkPlacer::new));

	private final IntProvider trunkSplitHeight;
	private final float branchSplitGapChance;
	private final TrunkBranchPlacement trunkBranchPlacement;


	public WillowTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		@NotNull IntProvider trunkSplitHeight,
		float branchSplitGapChance,
		TrunkBranchPlacement trunkBranchPlacement
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.trunkSplitHeight = trunkSplitHeight;
		this.branchSplitGapChance = branchSplitGapChance;
		this.trunkBranchPlacement = trunkBranchPlacement;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.WILLOW_TRUNK_PLACER;
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
		setDirtAt(level, replacer, random, startPos.below(), config);
		List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos branchMutable = new BlockPos.MutableBlockPos();
		int splitHeight = this.trunkSplitHeight.sample(random);

		int xOffset = 0;
		int zOffset = 0;
		for (int i = 0; i < height; ++i) {
			mutable.setWithOffset(startPos, xOffset, i, zOffset);
			if (i == splitHeight) {
				Direction splitDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				BlockPos splitPos = mutable.immutable();
				xOffset += splitDirection.getStepX();
				zOffset += splitDirection.getStepZ();
				mutable.setWithOffset(startPos, xOffset, i, zOffset);

				for (Direction branchDirection : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
					if (this.trunkBranchPlacement.canPlaceBranch(random)) {
						if (random.nextFloat() <= this.branchSplitGapChance && splitDirection != branchDirection) {
							branchMutable.set(splitPos);
						} else {
							branchMutable.set(mutable);
						}
						this.trunkBranchPlacement.generateExtraBranch(
							level,
							replacer,
							random,
							config.trunkProvider,
							branchMutable.immutable(),
							branchDirection,
							foliageAttachments
						);
					}
				}
			}

			this.placeLog(level, replacer, random, mutable, config);
			if (i == height - 1) foliageAttachments.add(new FoliagePlacer.FoliageAttachment(mutable.setWithOffset(startPos, xOffset, i + 1, zOffset), 0, false));
		}
		return foliageAttachments;
	}
}
