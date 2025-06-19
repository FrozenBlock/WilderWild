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

package net.frozenblock.wilderwild.worldgen.impl.rootplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public class WillowRootPlacer extends RootPlacer {
	public static final MapCodec<WillowRootPlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> rootPlacerParts(instance)
			.and(WillowRootPlacement.CODEC.fieldOf("willow_root_placement").forGetter(willowRootPlacer -> willowRootPlacer.willowRootPlacement))
			.apply(instance, WillowRootPlacer::new)
	);
	private final WillowRootPlacement willowRootPlacement;

	public WillowRootPlacer(
		IntProvider intProvider, BlockStateProvider blockStateProvider, Optional<AboveRootPlacement> optional, WillowRootPlacement mangroveRootPlacement
	) {
		super(intProvider, blockStateProvider, optional);
		this.willowRootPlacement = mangroveRootPlacement;
	}

	@Override
	public boolean placeRoots(
		LevelSimulatedReader levelSimulatedReader,
		BiConsumer<BlockPos, BlockState> biConsumer,
		RandomSource randomSource,
		@NotNull BlockPos blockPos,
		@NotNull BlockPos blockPos2,
		TreeConfiguration treeConfiguration
	) {
		List<BlockPos> list = Lists.newArrayList();
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

		while (mutableBlockPos.getY() < blockPos2.getY()) {
			if (!this.canPlaceRoot(levelSimulatedReader, mutableBlockPos)) return false;
			mutableBlockPos.move(Direction.UP);
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockPos3 = blockPos2.relative(direction);
			List<BlockPos> list2 = Lists.newArrayList();
			if (!this.simulateRoots(levelSimulatedReader, randomSource, blockPos3, direction, blockPos2, list2, 0)) return false;

			list.addAll(list2);
			list.add(blockPos2.relative(direction));
		}

		List<BlockPos> columnPoses = Lists.newArrayList();
		for (BlockPos rootPoses : list) columnPoses.addAll(this.potentialColumnRootPositions(levelSimulatedReader, rootPoses));
		list.addAll(columnPoses);

		for (BlockPos blockPos4 : list) this.placeRoot(levelSimulatedReader, biConsumer, randomSource, blockPos4, treeConfiguration);
		return true;
	}

	private boolean simulateRoots(
		LevelSimulatedReader levelSimulatedReader,
		RandomSource randomSource,
		BlockPos blockPos,
		Direction direction,
		BlockPos blockPos2,
		List<BlockPos> list,
		int i
	) {
		int maxLength = this.willowRootPlacement.maxRootLength();
		if (i != maxLength && list.size() <= maxLength) {
			for (BlockPos blockPos3 : this.potentialRootPositions(blockPos, direction, randomSource, blockPos2)) {
				if (!this.canPlaceRoot(levelSimulatedReader, blockPos3)) continue;
				list.add(blockPos3);
				if (!this.simulateRoots(levelSimulatedReader, randomSource, blockPos3, direction, blockPos2, list, i + 1)) return false;
			}

			return true;
		}
		return false;
	}

	protected List<BlockPos> potentialColumnRootPositions(
		LevelSimulatedReader levelSimulatedReader,
		@NotNull BlockPos blockPos
	) {
		ArrayList<BlockPos> positions = new ArrayList<>();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		mutableBlockPos.set(blockPos);
		while (this.canPlaceRoot(levelSimulatedReader, mutableBlockPos.move(Direction.DOWN))) positions.add(mutableBlockPos.immutable());
		return positions;
	}

	protected List<BlockPos> potentialRootPositions(
		@NotNull BlockPos blockPos,
		Direction direction,
		RandomSource randomSource,
		BlockPos blockPos2
	) {
		BlockPos belowPos = blockPos.below();
		BlockPos offsetPos = blockPos.relative(direction);
		int i = blockPos.distManhattan(blockPos2);
		int j = this.willowRootPlacement.maxRootWidth();
		float f = this.willowRootPlacement.randomSkewChance();

		if (i > j - 3 && i <= j) return randomSource.nextFloat() < f ? List.of(belowPos, offsetPos.below()) : List.of(belowPos);
		if (i > j) return List.of(belowPos);
		if (randomSource.nextFloat() < f) return List.of(belowPos);
		return randomSource.nextBoolean() ? List.of(offsetPos) : List.of(belowPos);
	}

	@Override
	protected boolean canPlaceRoot(LevelSimulatedReader levelSimulatedReader, BlockPos blockPos) {
		return super.canPlaceRoot(levelSimulatedReader, blockPos)
			|| levelSimulatedReader.isStateAtPosition(blockPos, blockState -> blockState.is(this.willowRootPlacement.canGrowThrough()));
	}

	@Override
	protected @NotNull RootPlacerType<?> type() {
		return WWFeatures.WILLOW_ROOT_PLACER;
	}
}
