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
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FallenLargeTrunkPlacer extends TrunkPlacer {
	public static final Codec<FallenLargeTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
		fallenTrunkCodec(instance).apply(instance, FallenLargeTrunkPlacer::new));

	public final float successInWaterChance;
	public final int minHeight;
	public final int maxHeight;

	public FallenLargeTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, float successInWaterChance) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.minHeight = baseHeight;
		this.maxHeight = baseHeight + firstRandomHeight + secondRandomHeight;
		this.successInWaterChance = successInWaterChance;
	}

	@Contract("_ -> new")
	protected static <P extends FallenLargeTrunkPlacer> Products.@NotNull P4<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, Float> fallenTrunkCodec(RecordCodecBuilder.Instance<P> builder) {
		return trunkPlacerParts(builder)
			.and(Codec.floatRange(0.0F, 1.0F).fieldOf("success_in_water_chance").forGetter((trunkPlacer) -> trunkPlacer.successInWaterChance));
	}

	private static boolean isWaterAt(@NotNull LevelSimulatedReader level, @NotNull BlockPos blockpos) {
		return level.isFluidAtPosition(blockpos, fluidState -> fluidState.is(FluidTags.WATER));
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FALLEN_LARGE_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		Direction logDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if (isWaterAt(level, startPos) && random.nextFloat() > this.successInWaterChance) {
			return list;
		}

		List<BlockPos> poses = this.getAllSectionPoses(level, startPos, random, logDir);
		for (BlockPos blockPos : poses) {
			this.placeLog(level, replacer, random, blockPos, config, (state) -> state.trySetValue(RotatedPillarBlock.AXIS, logDir.getAxis()));
		}

		return list;
	}

	@NotNull
	private List<BlockPos> getAllSectionPoses(@NotNull LevelSimulatedReader level, BlockPos startPos, RandomSource random, Direction logDir) {
		List<BlockPos> poses = Lists.newArrayList();
		List<BlockPos> firstPoses = this.getSectionPoses(true, level, startPos, random.nextIntBetweenInclusive(this.minHeight, this.maxHeight), logDir);
		if (!firstPoses.isEmpty()) {
			Direction.Axis axis = logDir.getAxis();
			if (axis == Direction.Axis.X) {
				axis = Direction.Axis.Z;
			} else if (axis == Direction.Axis.Z) {
				axis = Direction.Axis.X;
			}
			Direction secondOffset = AdvancedMath.randomDir(axis);
			List<BlockPos> secondPoses = this.getSectionPoses(true, level, startPos.relative(secondOffset), random.nextIntBetweenInclusive(this.minHeight, this.maxHeight), logDir);
			if (!secondPoses.isEmpty()) {
				List<BlockPos> thirdPoses = this.getSectionPoses(false, level, startPos.relative(Direction.UP), random.nextIntBetweenInclusive(this.minHeight, this.maxHeight), logDir);
				List<BlockPos> fourthPoses = this.getSectionPoses(false, level, startPos.relative(Direction.UP).relative(secondOffset), random.nextIntBetweenInclusive(this.minHeight, this.maxHeight), logDir);

                poses.addAll(firstPoses);
                poses.addAll(secondPoses);
                poses.addAll(thirdPoses);
                poses.addAll(fourthPoses);
			}
		}

		return poses;
	}

	@NotNull
	private List<BlockPos> getSectionPoses(boolean requiresUnderneath, @NotNull LevelSimulatedReader level, @NotNull BlockPos startPos, int height, @NotNull Direction logDir) {
		List<BlockPos> finalizedPoses = Lists.newArrayList();
		BlockPos endPos = startPos.relative(logDir, height);
		BlockPos secondToEndPos = endPos.relative(logDir.getOpposite());
		int aboveSolidAmount = 0;
		boolean isEndAboveSolid = false;
		Iterable<BlockPos> poses = BlockPos.betweenClosed(startPos, endPos);
		if (!requiresUnderneath) {
			poses.forEach(pos -> finalizedPoses.add(pos.immutable()));
			return finalizedPoses;
		}

		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (BlockPos blockPos : poses) {
			mutable.set(blockPos);
			if (TreeFeature.validTreePos(level, mutable)) {
				if (!TreeFeature.validTreePos(level, mutable.move(Direction.DOWN)) && !TreeFeature.isAirOrLeaves(level, mutable)) {
					aboveSolidAmount += 1;
					mutable.move(Direction.UP);
					if (mutable.equals(endPos) || mutable.equals(secondToEndPos)) {
						isEndAboveSolid = true;
					}
				} else {
					mutable.move(Direction.UP);
					if (mutable.equals(startPos)) {
						return List.of();
					}
				}
			} else {
				return List.of();
			}
		}
		if (isEndAboveSolid || ((double) aboveSolidAmount / (double) height) > 0.5D) {
			poses.forEach(pos -> finalizedPoses.add(pos.immutable()));
			return finalizedPoses;
		}

		return List.of();
	}

}
