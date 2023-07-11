/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
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

public class LargeSnappedTrunkPlacer extends TrunkPlacer {
	public static final Codec<LargeSnappedTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
		largeSnappedTrunkCodec(instance).apply(instance, LargeSnappedTrunkPlacer::new));

	public final float minimumHeightPercent;
	private final float heightDifferenceFromMax;

	public LargeSnappedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, float minimumHeightPercent) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.minimumHeightPercent = minimumHeightPercent;
		this.heightDifferenceFromMax = 1F - minimumHeightPercent;
	}

	protected static <P extends LargeSnappedTrunkPlacer> Products.P4<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, Float> largeSnappedTrunkCodec(RecordCodecBuilder.Instance<P> builder) {
		return trunkPlacerParts(builder)
			.and(Codec.floatRange(0.0F, 1.0F).fieldOf("minimum_height_percentage").forGetter((trunkPlacer) -> trunkPlacer.minimumHeightPercent));
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return RegisterFeatures.LARGE_SNAPPED_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int percentedHeight = (int) ((float) height * this.minimumHeightPercent);
		int differenceInHeight = height - percentedHeight;

		setDirtAt(level, replacer, random, mutable.move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 1, -1, 0), differenceInHeight, percentedHeight);

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.EAST).move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 1, -1, 0), differenceInHeight, percentedHeight);

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.SOUTH).move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 0, -1, 1), differenceInHeight, percentedHeight);

		setDirtAt(level, replacer, random, mutable.setWithOffset(startPos, Direction.SOUTH).move(Direction.EAST).move(Direction.DOWN), config);
		placeQuarter(level, replacer, random, config, mutable.setWithOffset(startPos, 1, -1, 1), differenceInHeight, percentedHeight);

		return Lists.newArrayList();
	}

	private void placeQuarter(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos pos, int differenceInHeight, int percentedHeight) {
		int newHeight = percentedHeight + (int) ((float) differenceInHeight * this.heightDifferenceFromMax);
		for (int i = 0; i < newHeight; ++i) {
			this.placeLog(level, replacer, random, config, pos.move(Direction.UP));
		}
	}

	private void placeLog(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos pos) {
		this.placeLogIfFree(level, blockSetter, random, pos, config);
	}
}
