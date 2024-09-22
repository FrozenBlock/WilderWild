/*
 * Copyright 2023-2024 FrozenBlock
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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
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

public class StraightWithBranchesTrunkPlacer extends TrunkPlacer {
	public static final Codec<StraightWithBranchesTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
		trunkPlacerParts(instance)
			.and(
				instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("branch_chance").forGetter((trunkPlacer) -> trunkPlacer.branchChance),
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_log_count").forGetter((trunkPlacer) -> trunkPlacer.maxLogCount),
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("branch_height_from_top").forGetter((trunkPlacer) -> trunkPlacer.branchHeightFromTop),
					IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> trunkPlacer.branchLength)
				)
			).apply(instance, StraightWithBranchesTrunkPlacer::new));

	private final float branchChance;
	private final IntProvider maxLogCount;
	private final IntProvider branchHeightFromTop;
	private final IntProvider branchLength;


	public StraightWithBranchesTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, float branchChance, @NotNull IntProvider maxLogCount, @NotNull IntProvider branchHeightFromTop, @NotNull IntProvider branchLength) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.branchChance = branchChance;
		this.maxLogCount = maxLogCount;
		this.branchHeightFromTop = branchHeightFromTop;
		this.branchLength = branchLength;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.STRAIGHT_WITH_LOGS_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
		setDirtAt(level, replacer, random, startPos.below(), config);
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int maxLogs = this.maxLogCount.sample(random);
		int branchHeightFromTop = this.branchHeightFromTop.sample(random);
		int extraLogs = 0;
		for (int i = 0; i < height; ++i) {
			int j = startPos.getY() + i;
			if (this.placeLog(level, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config)
				&& i < height - 1 && random.nextFloat() < this.branchChance && extraLogs < maxLogs && (height - 4) - i <= branchHeightFromTop) {
				Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				this.generateExtraBranch(level, replacer, random, config, mutable, j, direction, this.branchLength.sample(random));
				++extraLogs;
			}
			if (i == height - 1) {
				list.add(new FoliagePlacer.FoliageAttachment(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
			}
		}

		return list;
	}

	private void generateExtraBranch(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos pos, int yOffset, @NotNull Direction direction, int length) {
		int j = pos.getX();
		int k = pos.getZ();
		for (int l = 0; l < length; ++l) {
			j += direction.getStepX();
			k += direction.getStepZ();
			if (TreeFeature.validTreePos(level, pos.set(j, yOffset, k))) {
				if (config.trunkProvider.getState(random, pos.set(j, yOffset, k)).hasProperty(BlockStateProperties.AXIS)) {
					Direction.Axis axis = direction.getStepX() != 0 ? Direction.Axis.X : Direction.Axis.Z;
					replacer.accept(pos.set(j, yOffset, k), config.trunkProvider.getState(random, pos.set(j, yOffset, k)).setValue(BlockStateProperties.AXIS, axis));
				} else {
					this.placeLog(level, replacer, random, pos.set(j, yOffset, k), config);
				}
			}
		}
	}
}
