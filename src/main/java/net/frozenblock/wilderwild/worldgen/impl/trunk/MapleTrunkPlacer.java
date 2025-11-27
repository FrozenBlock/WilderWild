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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
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

public class MapleTrunkPlacer extends TrunkPlacer {
	private static final List<Direction> EMPTY = ImmutableList.of();
	public static final MapCodec<MapleTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(TrunkBranchPlacement.CODEC.fieldOf("trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.trunkBranchPlacement))
			.and(TrunkBranchPlacement.CODEC.fieldOf("lower_trunk_branch_placement").forGetter(trunkPlacer -> trunkPlacer.lowerTrunkBranchPlacement))
			.and(TrunkPlacer.CODEC.fieldOf("alt_trunk_placer").forGetter(trunkPlacer -> trunkPlacer.altTrunkPlacer))
			.apply(instance, MapleTrunkPlacer::new)
	);
	private final TrunkBranchPlacement trunkBranchPlacement;
	private final TrunkBranchPlacement lowerTrunkBranchPlacement;
	private final TrunkPlacer altTrunkPlacer;

	public MapleTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		TrunkBranchPlacement trunkBranchPlacement,
		TrunkBranchPlacement lowerTrunkBranchPlacement,
		TrunkPlacer altTrunkPlacer
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.trunkBranchPlacement = trunkBranchPlacement;
		this.lowerTrunkBranchPlacement = lowerTrunkBranchPlacement;
		this.altTrunkPlacer = altTrunkPlacer;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.MAPLE_TRUNK_PLACER;
	}

	private static boolean containsNearbyBranchWithSameDirection(BlockPos pos, Direction direction, int distanceBelow, Map<BlockPos, List<Direction>> branches) {
		for (int i = 1; i <= distanceBelow; i++) if (branches.getOrDefault(pos.below(i), EMPTY).contains(direction)) return true;
		return false;
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
		if (!WWWorldgenConfig.NEW_MAPLES) return this.altTrunkPlacer.placeTrunk(level, replacer, random, height, startPos, config);

		setDirtAt(level, replacer, random, startPos.below(), config);

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final int branchCutoffFromTop = this.trunkBranchPlacement.branchCutoffFromTop().sample(random);
		final List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final Map<BlockPos, List<Direction>> branches = new Object2ObjectLinkedOpenHashMap<>();

		for (int currentHeight = 0; currentHeight < height; ++currentHeight) {
			int worldHeight = startPos.getY() + currentHeight;

			if (!this.placeLog(level, replacer, random, mutable.set(startPos.getX(), worldHeight, startPos.getZ()), config)
				|| currentHeight >= height - 1
				|| currentHeight <= branchCutoffFromTop
			) continue;

			final TrunkBranchPlacement branchPlacement = currentHeight < (height / 2) ? this.lowerTrunkBranchPlacement : this.trunkBranchPlacement;
			final BlockPos branchTrunkPos = mutable.immutable();
			for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
				if (!branchPlacement.canPlaceBranch(random)) continue;
				if (containsNearbyBranchWithSameDirection(branchTrunkPos, direction, 2, branches)) continue;

				branchPlacement.generateExtraBranch(level, replacer, random, config.trunkProvider, branchTrunkPos, direction, foliageAttachments);

				final List<Direction> branchDirections = branches.computeIfAbsent(branchTrunkPos, pos -> new ArrayList<>());
				branchDirections.add(direction);
				branches.put(branchTrunkPos, branchDirections);
			}
		}

		foliageAttachments.add(new FoliagePlacer.FoliageAttachment(startPos.above(height), 1, false));
		return foliageAttachments;
	}
}
