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

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class JuniperTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<JuniperTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and((IntProviders.codec(1, 3).fieldOf("branch_count")).forGetter(trunkPlacer -> trunkPlacer.branchCount))
			.and((IntProviders.codec(2, 16).fieldOf("branch_horizontal_length")).forGetter(trunkPlacer -> trunkPlacer.branchHorizontalLength))
			.and((UniformInt.MAP_CODEC.fieldOf("branch_start_offset_from_top")).forGetter(trunkPlacer -> trunkPlacer.branchStartOffsetFromTop))
			.and((IntProviders.codec(-16, 16).fieldOf("branch_end_offset_from_top")).forGetter(trunkPlacer -> trunkPlacer.branchEndOffsetFromTop))
			.apply(instance, JuniperTrunkPlacer::new)
	);
	public final IntProvider branchCount;
	public final IntProvider branchHorizontalLength;
	public final UniformInt branchStartOffsetFromTop;
	public final UniformInt secondBranchStartOffsetFromTop;
	public final IntProvider branchEndOffsetFromTop;

	public JuniperTrunkPlacer(
		int baseHeight,
		int heightRandA,
		int heightRandB,
		IntProvider branchCount,
		IntProvider branchHorizontalLength,
		UniformInt branchStartOffsetFromTop,
		IntProvider branchEndOffsetFromTop
	) {
		super(baseHeight, heightRandA, heightRandB);
		this.branchCount = branchCount;
		this.branchHorizontalLength = branchHorizontalLength;
		this.branchStartOffsetFromTop = branchStartOffsetFromTop;
		this.secondBranchStartOffsetFromTop = UniformInt.of(branchStartOffsetFromTop.minInclusive(), branchStartOffsetFromTop.maxInclusive() - 1);
		this.branchEndOffsetFromTop = branchEndOffsetFromTop;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.JUNIPER_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		int treeHeight,
		BlockPos pos,
		TreeFeature tree
	) {
		placeBelowTrunkBlock(level, trunkSetter, random, pos.below(), tree);
		int firstBranchOffsetFromOrigin = Math.max(0, treeHeight - 1 + this.branchStartOffsetFromTop.sample(random));
		int secondBranchOffsetFromOrigin = Math.max(0, treeHeight - 1 + this.secondBranchStartOffsetFromTop.sample(random));
		if (secondBranchOffsetFromOrigin >= firstBranchOffsetFromOrigin) ++secondBranchOffsetFromOrigin;

		final int branchCount = this.branchCount.sample(random);
		final boolean hasMiddleBranch = branchCount == 3;
		final boolean hasBothSideBranches = branchCount >= 2;
		final int trunkHeight = hasMiddleBranch
			? treeHeight
			: (hasBothSideBranches ? Math.max(firstBranchOffsetFromOrigin, secondBranchOffsetFromOrigin) + 1 : firstBranchOffsetFromOrigin + 1);
		for (int y = 0; y < trunkHeight; ++y) this.placeLog(level, trunkSetter, random, pos.above(y), tree);

		final ArrayList<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();
		if (hasMiddleBranch) attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(trunkHeight), 0, false));

		final BlockPos.MutableBlockPos logPos = new BlockPos.MutableBlockPos();
		final Direction treeDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Function<BlockState, BlockState> sidewaysStateModifier = state -> (BlockState) state.setValue(RotatedPillarBlock.AXIS, treeDirection.getAxis());
		attachments.add(
			this.generateBranch(
				level,
				trunkSetter,
				random,
				treeHeight,
				pos,
				tree,
				sidewaysStateModifier,
				treeDirection,
				firstBranchOffsetFromOrigin,
				firstBranchOffsetFromOrigin < trunkHeight - 1, logPos)
		);

		final Direction secondTreeDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		if (hasBothSideBranches) {
			sidewaysStateModifier = state -> (BlockState) state.setValue(RotatedPillarBlock.AXIS, secondTreeDirection.getAxis());
			attachments.add(
				this.generateBranch(
					level,
					trunkSetter,
					random,
					treeHeight,
					pos,
					tree,
					sidewaysStateModifier,
					secondTreeDirection,
					secondBranchOffsetFromOrigin,
					secondBranchOffsetFromOrigin < trunkHeight - 1, logPos)
			);
		}
		return attachments;
	}

	private FoliagePlacer.FoliageAttachment generateBranch(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		int treeHeight,
		BlockPos origin,
		TreeFeature tree,
		Function<BlockState, BlockState> sidewaysStateModifier,
		Direction branchDirection,
		int offsetFromOrigin,
		boolean middleContinuesUpwards,
		BlockPos.MutableBlockPos logPos
	) {
		int distance;
		logPos.set(origin).move(Direction.UP, offsetFromOrigin);
		int branchEndPosOffsetFromOrigin = treeHeight - 1 + this.branchEndOffsetFromTop.sample(random);
		boolean extendBranchAwayFromTrunk = middleContinuesUpwards || branchEndPosOffsetFromOrigin < offsetFromOrigin;
		int distanceToTrunk = this.branchHorizontalLength.sample(random) + (extendBranchAwayFromTrunk ? 1 : 0);
		BlockPos branchEndPos = origin.relative(branchDirection, distanceToTrunk).above(branchEndPosOffsetFromOrigin);
		int stepsHorizontally = extendBranchAwayFromTrunk ? 2 : 1;

		for (int i = 0; i < stepsHorizontally; ++i) {
			this.placeLog(level, trunkSetter, random, logPos.move(branchDirection), tree, sidewaysStateModifier);
		}

		final Direction verticalDirection = branchEndPos.getY() > logPos.getY() ? Direction.UP : Direction.DOWN;

		while ((distance = logPos.distManhattan(branchEndPos)) != 0) {
			float chanceToGrowVertically = (float) Math.abs(branchEndPos.getY() - logPos.getY()) / (float) distance;
			boolean growVertically = random.nextFloat() < chanceToGrowVertically;
			logPos.move(growVertically ? verticalDirection : branchDirection);
			this.placeLog(level, trunkSetter, random, logPos, tree, growVertically ? Function.identity() : sidewaysStateModifier);
		}
		return new FoliagePlacer.FoliageAttachment(branchEndPos.above(), 0, false);
	}
}
