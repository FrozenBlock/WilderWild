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
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.worldgen.impl.trunk.branch.TrunkBranchPlacement;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class MapleTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<MapleTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(TrunkBranchPlacement.CODEC.fieldOf("trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.trunkBranchPlacement))
			.and(TrunkBranchPlacement.CODEC.fieldOf("lower_trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.lowerTrunkBranchPlacement))
			.and(TrunkPlacer.CODEC.fieldOf("alt_trunk_placer").forGetter(trunkPlacer -> trunkPlacer.altTrunkPlacer))
			.apply(instance, MapleTrunkPlacer::new));

	private final TrunkBranchPlacement trunkBranchPlacement;
	private final TrunkBranchPlacement lowerTrunkBranchPlacement;
	private final TrunkPlacer altTrunkPlacer;

	public MapleTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		@NotNull TrunkBranchPlacement trunkBranchPlacement,
		@NotNull TrunkBranchPlacement lowerTrunkBranchPlacement,
		@NotNull TrunkPlacer altTrunkPlacer
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.trunkBranchPlacement = trunkBranchPlacement;
		this.lowerTrunkBranchPlacement = lowerTrunkBranchPlacement;
		this.altTrunkPlacer = altTrunkPlacer;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.MAPLE_TRUNK_PLACER;
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
		if (!WWWorldgenConfig.NEW_MAPLES) return this.altTrunkPlacer.placeTrunk(level, replacer, random, height, startPos, config);

		setDirtAt(level, replacer, random, startPos.below(), config);

		final List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final int maxBranchCount = this.trunkBranchPlacement.getMaxBranchCount(random);
		final int branchCutoffFromTop = this.trunkBranchPlacement.getBranchCutoffFromTop().sample(random);

		int extraLogs = 0;
		for (int currentHeight = 0; currentHeight < height; ++currentHeight) {
			int worldHeight = startPos.getY() + currentHeight;

			final TrunkBranchPlacement branchPlacement = currentHeight < (height / 2) ? this.lowerTrunkBranchPlacement : this.trunkBranchPlacement;

			if (this.placeLog(level, replacer, random, mutable.set(startPos.getX(), worldHeight, startPos.getZ()), config)
				&& currentHeight < height - 1
				&& extraLogs < maxBranchCount
				&& currentHeight > branchCutoffFromTop
			) {
				for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
					if (!branchPlacement.canPlaceBranch(random)) continue;
					if (extraLogs >= maxBranchCount) continue;

					branchPlacement.generateExtraBranch(
						level,
						replacer,
						random,
						config.trunkProvider,
						mutable.immutable(),
						direction,
						foliageAttachments
					);
					++extraLogs;
				}
			}
		}

		foliageAttachments.add(new FoliagePlacer.FoliageAttachment(startPos.above(height), 0, false));
		return foliageAttachments;
	}
}
