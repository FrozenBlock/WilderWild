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
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class WillowTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<WillowTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		trunkPlacerParts(instance)
			.and(
				instance.group(
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("trunk_split_height").forGetter((trunkPlacer) -> trunkPlacer.trunkSplitHeight),
					Codec.floatRange(0F, 1F).fieldOf("branch_chance").forGetter((trunkPlacer) -> trunkPlacer.branchChance),
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> trunkPlacer.branchLength)
				)
			).apply(instance, WillowTrunkPlacer::new));

	private final IntProvider trunkSplitHeight;
	private final float branchChance;
	private final IntProvider branchLength;


	public WillowTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		@NotNull IntProvider trunkSplitHeight,
		float branchChance,
		@NotNull IntProvider branchLength
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.trunkSplitHeight = trunkSplitHeight;
		this.branchChance = branchChance;
		this.branchLength = branchLength;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.WILLOW_TRUNK_PLACER;
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
		setDirtAt(level, replacer, random, startPos.below(), config);
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos branchMutable = new BlockPos.MutableBlockPos();
		int splitHeight = this.trunkSplitHeight.sample(random);

		int xOffset = 0;
		int zOffset = 0;
		for (int i = 0; i < height; ++i) {
			mutable.setWithOffset(startPos, xOffset, i, zOffset);
			if (i == splitHeight) {
				Direction splitDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				xOffset += splitDirection.getStepX();
				zOffset += splitDirection.getStepZ();
				mutable.setWithOffset(startPos, xOffset, i, zOffset);

				for (Direction branchDirection : Util.shuffledCopy(Direction.values(), random)) {
					if (random.nextFloat() <= this.branchChance) {
						branchMutable.set(mutable);
						this.generateExtraBranch(
							list,
							level,
							replacer,
							random,
							config,
							startPos,
							branchMutable,
							i,
							branchDirection,
							this.branchLength.sample(random)
						);
					}
				}
			}

			this.placeLog(level, replacer, random, mutable, config);

			if (i == height - 1) {
				list.add(new FoliagePlacer.FoliageAttachment(mutable.setWithOffset(startPos, xOffset, i + 1, zOffset), 0, false));
			}
		}

		return list;
	}

	private void generateExtraBranch(
		List<FoliagePlacer.FoliageAttachment> list,
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos startPos,
		@NotNull BlockPos.MutableBlockPos mutable,
		int yOffset,
		@NotNull Direction direction,
		int length
	) {
		for (int l = 1; l <= length; ++l) {
			mutable.setWithOffset(startPos, direction.getStepX() * l, yOffset, direction.getStepZ() * l);
			if (TreeFeature.validTreePos(level, mutable)) {
				if (l == length) {
					this.placeLog(level, replacer, random, mutable.move(Direction.UP), config);
					list.add(new FoliagePlacer.FoliageAttachment(mutable.move(Direction.UP).immutable(), -1, false));
					return;
				} else {
					if (config.trunkProvider.getState(random, mutable).hasProperty(BlockStateProperties.AXIS)) {
						Direction.Axis axis = direction.getStepX() != 0 ? Direction.Axis.X : Direction.Axis.Z;
						replacer.accept(mutable, config.trunkProvider.getState(random, mutable).setValue(BlockStateProperties.AXIS, axis));
					} else {
						this.placeLog(level, replacer, random, mutable, config);
					}
				}
			}
		}
	}
}
