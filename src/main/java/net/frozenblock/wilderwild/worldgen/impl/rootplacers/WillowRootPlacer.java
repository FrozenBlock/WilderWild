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

public class WillowRootPlacer extends RootPlacer {
	public static final MapCodec<WillowRootPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		rootPlacerParts(instance)
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
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> placer,
		RandomSource random,
		BlockPos pos1,
		BlockPos pos2,
		TreeConfiguration treeConfiguration
	) {
		final List<BlockPos> list = Lists.newArrayList();
		final BlockPos.MutableBlockPos mutable = pos1.mutable();

		while (mutable.getY() < pos2.getY()) {
			if (!this.canPlaceRoot(level, mutable)) return false;
			mutable.move(Direction.UP);
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			final BlockPos offsetPos = pos2.relative(direction);
			final List<BlockPos> list2 = Lists.newArrayList();
			if (!this.simulateRoots(level, random, offsetPos, direction, pos2, list2, 0)) return false;

			list.addAll(list2);
			list.add(pos2.relative(direction));
		}

		final List<BlockPos> columnPoses = Lists.newArrayList();
		for (BlockPos rootPoses : list) columnPoses.addAll(this.potentialColumnRootPositions(level, rootPoses));
		list.addAll(columnPoses);

		for (BlockPos blockPos4 : list) this.placeRoot(level, placer, random, blockPos4, treeConfiguration);
		return true;
	}

	private boolean simulateRoots(
		LevelSimulatedReader level,
		RandomSource random,
		BlockPos pos1,
		Direction direction,
		BlockPos pos2,
		List<BlockPos> list,
		int i
	) {
		final int maxLength = this.willowRootPlacement.maxRootLength();
		if (i != maxLength && list.size() <= maxLength) {
			for (BlockPos blockPos3 : this.potentialRootPositions(pos1, direction, random, pos2)) {
				if (!this.canPlaceRoot(level, blockPos3)) continue;
				list.add(blockPos3);
				if (!this.simulateRoots(level, random, blockPos3, direction, pos2, list, i + 1)) return false;
			}

			return true;
		}
		return false;
	}

	protected List<BlockPos> potentialColumnRootPositions(LevelSimulatedReader level, BlockPos pos) {
		final ArrayList<BlockPos> poses = new ArrayList<>();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		mutable.set(pos);
		while (this.canPlaceRoot(level, mutable.move(Direction.DOWN))) poses.add(mutable.immutable());
		return poses;
	}

	protected List<BlockPos> potentialRootPositions(
		BlockPos pos,
		Direction direction,
		RandomSource random,
		BlockPos pos2
	) {
		final BlockPos belowPos = pos.below();
		final BlockPos offsetPos = pos.relative(direction);
		final int distance = pos.distManhattan(pos2);
		final int maxWidth = this.willowRootPlacement.maxRootWidth();
		final float skewChance = this.willowRootPlacement.randomSkewChance();

		if (distance > maxWidth - 3 && distance <= maxWidth) return random.nextFloat() < skewChance ? List.of(belowPos, offsetPos.below()) : List.of(belowPos);
		if (distance > maxWidth) return List.of(belowPos);
		if (random.nextFloat() < skewChance) return List.of(belowPos);
		return random.nextBoolean() ? List.of(offsetPos) : List.of(belowPos);
	}

	@Override
	protected boolean canPlaceRoot(LevelSimulatedReader level, BlockPos pos) {
		return super.canPlaceRoot(level, pos) || level.isStateAtPosition(pos, state -> state.is(this.willowRootPlacement.canGrowThrough()));
	}

	@Override
	protected RootPlacerType<?> type() {
		return WWFeatures.WILLOW_ROOT_PLACER;
	}
}
