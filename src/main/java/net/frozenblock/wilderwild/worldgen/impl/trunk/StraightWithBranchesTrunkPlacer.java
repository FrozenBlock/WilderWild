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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.impl.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.worldgen.impl.trunk.branch.TrunkBranchPlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class StraightWithBranchesTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<StraightWithBranchesTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(TrunkBranchPlacement.CODEC.fieldOf("trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.trunkBranchPlacement))
			.apply(instance, StraightWithBranchesTrunkPlacer::new));

	private final TrunkBranchPlacement trunkBranchPlacement;

	public StraightWithBranchesTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		TrunkBranchPlacement trunkBranchPlacement
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.trunkBranchPlacement = trunkBranchPlacement;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.STRAIGHT_WITH_BRANCHES_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		int height,
		BlockPos startPos,
		TreeConfiguration config
	) {
		setDirtAt(level, replacer, random, startPos.below(), config);

		final List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final int maxBranchCount = this.trunkBranchPlacement.getMaxBranchCount(random);
		final int branchCutoffFromTop = this.trunkBranchPlacement.branchCutoffFromTop().sample(random);

		int extraLogs = 0;
		for (int i = 0; i < height; ++i) {
			final int y = startPos.getY() + i;
			if (this.placeLog(level, replacer, random, mutable.set(startPos.getX(), y, startPos.getZ()), config)
				&& i < height - 1
				&& this.trunkBranchPlacement.canPlaceBranch(random)
				&& extraLogs < maxBranchCount
				&& (height - 4) - i <= branchCutoffFromTop
			) {
				this.trunkBranchPlacement.generateExtraBranch(
					level,
					replacer,
					random,
					config.trunkProvider,
					mutable.immutable(),
					Direction.Plane.HORIZONTAL.getRandomDirection(random),
					foliageAttachments
				);
				++extraLogs;
			}
			if (i == height - 1) foliageAttachments.add(new FoliagePlacer.FoliageAttachment(mutable.set(startPos.getX(), y + 1, startPos.getZ()), 0, false));
		}

		return foliageAttachments;
	}
}
