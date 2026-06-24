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
		int firstRandomHeight,
		int secondRandomHeight,
		IntProvider branchCount,
		IntProvider branchHorizontalLength,
		UniformInt branchStartOffsetFromTop,
		IntProvider branchEndOffsetFromTop
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
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
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		int freeTreeHeight,
		BlockPos pos,
		TreeFeature tree
	) {
		placeBelowTrunkBlock(level, replacer, random, pos.below(), tree);
		int i = Math.max(0, freeTreeHeight - 1 + this.branchStartOffsetFromTop.sample(random));
		int j = Math.max(0, freeTreeHeight - 1 + this.secondBranchStartOffsetFromTop.sample(random));
		if (j >= i) ++j;

		final int branchCount = this.branchCount.sample(random);
		final boolean isThreeBranches = branchCount == 3;
		final boolean moreThanOneBranch = branchCount >= 2;
		final int l = isThreeBranches ? freeTreeHeight : (moreThanOneBranch ? Math.max(i, j) + 1 : i + 1);
		for (int m = 0; m < l; ++m) this.placeLog(level, replacer, random, pos.above(m), tree);

		final ArrayList<FoliagePlacer.FoliageAttachment> foliageAttachments = new ArrayList<>();
		if (isThreeBranches) foliageAttachments.add(new FoliagePlacer.FoliageAttachment(pos.above(l), 0, false));

		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Function<BlockState, BlockState> function = state -> (BlockState) state.setValue(RotatedPillarBlock.AXIS, direction.getAxis());
		foliageAttachments.add(this.generateBranch(level, replacer, random, freeTreeHeight, pos, tree, function, direction, i, i < l - 1, mutable));

		final ArrayList<Direction> allDirsMF = new ArrayList<>();
		for (Direction d : Direction.Plane.HORIZONTAL) if (d != direction) allDirsMF.add(d);

		final Direction secondDir = allDirsMF.get((int) (Math.random() * 2));
		if (moreThanOneBranch) {
			function = state -> (BlockState) state.setValue(RotatedPillarBlock.AXIS, secondDir.getAxis());
			foliageAttachments.add(this.generateBranch(level, replacer, random, freeTreeHeight, pos, tree, function, secondDir, j, j < l - 1, mutable));
		}
		return foliageAttachments;
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

		Direction verticalDirection = branchEndPos.getY() > logPos.getY() ? Direction.UP : Direction.DOWN;

		while ((distance = logPos.distManhattan(branchEndPos)) != 0) {
			float chanceToGrowVertically = (float) Math.abs(branchEndPos.getY() - logPos.getY()) / (float) distance;
			boolean growVertically = random.nextFloat() < chanceToGrowVertically;
			logPos.move(growVertically ? verticalDirection : branchDirection);
			this.placeLog(level, trunkSetter, random, logPos, tree, growVertically ? Function.identity() : sidewaysStateModifier);
		}
		return new FoliagePlacer.FoliageAttachment(branchEndPos.above(), 0, false);
	}
}
