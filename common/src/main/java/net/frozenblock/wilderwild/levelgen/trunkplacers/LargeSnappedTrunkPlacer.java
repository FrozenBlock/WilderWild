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
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class LargeSnappedTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<LargeSnappedTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(UniformInt.MAP_CODEC.fieldOf("additional_height").forGetter(trunkPlacer -> trunkPlacer.additionalHeight))
			.apply(instance, LargeSnappedTrunkPlacer::new)
	);

	public final UniformInt additionalHeight;

	public LargeSnappedTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, UniformInt additionalHeight) {
		super(baseHeight, heightRandA, heightRandB);
		this.additionalHeight = additionalHeight;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.LARGE_SNAPPED_TRUNK_PLACER.get();
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
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		placeBelowTrunkBlock(level, trunkSetter, random, mutable.setWithOffset(origin, Direction.DOWN), tree);
		placeQuarter(level, trunkSetter, random, tree, mutable.setWithOffset(origin, 0, -1, 0), treeHeight);

		placeBelowTrunkBlock(level, trunkSetter, random, mutable.setWithOffset(origin, Direction.EAST).move(Direction.DOWN), tree);
		placeQuarter(level, trunkSetter, random, tree, mutable.setWithOffset(origin, 1, -1, 0), treeHeight);

		placeBelowTrunkBlock(level, trunkSetter, random, mutable.setWithOffset(origin, Direction.SOUTH).move(Direction.DOWN), tree);
		placeQuarter(level, trunkSetter, random, tree, mutable.setWithOffset(origin, 0, -1, 1), treeHeight);

		placeBelowTrunkBlock(level, trunkSetter, random, mutable.setWithOffset(origin, Direction.SOUTH).move(Direction.EAST).move(Direction.DOWN), tree);
		placeQuarter(level, trunkSetter, random, tree, mutable.setWithOffset(origin, 1, -1, 1), treeHeight);

		return Lists.newArrayList();
	}

	private void placeQuarter(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		TreeFeature tree,
		BlockPos.MutableBlockPos mutable,
		int treeHeight
	) {
		final int newHeight = treeHeight + this.additionalHeight.sample(random);
		for (int i = 0; i < newHeight; ++i) this.placeLog(level, trunkSetter, random, tree, mutable.move(Direction.UP));
	}

	private void placeLog(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		TreeFeature tree,
		BlockPos.MutableBlockPos mutable
	) {
		this.placeLogIfFree(level, trunkSetter, random, mutable, tree);
	}
}
