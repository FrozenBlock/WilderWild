/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.impl.trunk;

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
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class LargeSnappedTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<LargeSnappedTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(UniformInt.CODEC.fieldOf("additional_height").forGetter(trunkPlacer -> trunkPlacer.additionalHeight))
			.apply(instance, LargeSnappedTrunkPlacer::new)
	);

	public final UniformInt additionalHeight;

	public LargeSnappedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, UniformInt additionalHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.additionalHeight = additionalHeight;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.LARGE_SNAPPED_TRUNK_PLACER;
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
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 0, -1, 0), height);

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.EAST).move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 1, -1, 0), height);

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.SOUTH).move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 0, -1, 1), height);

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.SOUTH).move(Direction.EAST).move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 1, -1, 1), height);

		return Lists.newArrayList();
	}

	private void placeQuarter(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos.MutableBlockPos pos,
		int height
	) {
		int newHeight = height + this.additionalHeight.sample(random);
		for (int i = 0; i < newHeight; ++i) {
			this.placeLog(level, replacer, random, config, pos.move(Direction.UP));
		}
	}

	private void placeLog(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> blockSetter,
		@NotNull RandomSource random,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos.MutableBlockPos pos
	) {
		this.placeLogIfFree(level, blockSetter, random, pos, config);
	}
}
