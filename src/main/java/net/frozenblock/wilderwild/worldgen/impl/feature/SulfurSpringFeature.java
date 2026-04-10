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

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.SulfurSpringFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;

public class SulfurSpringFeature extends Feature<SulfurSpringFeatureConfig> {
	private static final Block DUMMY_BLOCK = Blocks.AIR;
	private static final int BELOW_HEIGHT = -3;

	public SulfurSpringFeature(Codec<SulfurSpringFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SulfurSpringFeatureConfig> context) {
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();
		final RandomSource random = context.random();

		final SulfurSpringFeatureConfig config = context.config();
		final BlockStateProvider state = config.state();
		final BlockStateProvider waterState = config.waterState();
		final HolderSet<Block> replaceable = config.replaceable();
		final HolderSet<Block> cannotReplace = config.cannotReplace();
		final Predicate<BlockState> safeToReplace = statex -> !statex.is(cannotReplace) && statex.is(replaceable);

		final BlockPos.MutableBlockPos originMutable = origin.mutable();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int attempt = 0; attempt < 10; attempt++) {
			final int height = config.height().sample(random);
			final double curveDistance = config.curveDistance().sample(random);
			final double curveFactorX = random.nextGaussian();
			final int xWidth = config.width().sample(random);
			final double curveFactorZ = random.nextGaussian();
			final int zWidth = config.width().sample(random);
			final float bluntness = 1F - config.bluntness().sample(random);

			final BlockSetters setters = new BlockSetters(config.cribBlockChance(), config.cribExtraBlockChance(), random);
			final BlockSetter setter = setters.setter();
			final BlockSetter cribSetter = setters.cribSetter();
			final BlockSetter cribExtraSetter = setters.cribExtraSetter();
			originMutable.setWithOffset(origin, 0, -height - 1, 0);

			for (int i = 0; i < height; i++) {
				mutable.setWithOffset(originMutable, 0, i, 0);
				placeInSquare(
					level,
					mutable,
					state,
					((double) i / height) * bluntness,
					curveDistance,
					curveFactorX,
					xWidth,
					curveFactorZ,
					zWidth,
					setter,
					random
				);
			}

			for (int i = BELOW_HEIGHT; i < 0; i++) {
				mutable.setWithOffset(originMutable, 0, i, 0);
				placeInSquare(
					level,
					mutable,
					state,
					((double) i / BELOW_HEIGHT) * bluntness,
					curveDistance,
					curveFactorX,
					xWidth,
					curveFactorZ,
					zWidth,
					setter,
					random
				);
			}

			mutable.setWithOffset(originMutable, 0, height, 0);
			placeInSquare(
				level,
				mutable,
				state,
				bluntness,
				curveDistance,
				curveFactorX,
				xWidth + 1,
				curveFactorZ,
				zWidth + 1,
				setter,
				random
			);

			for (BlockPos topPos : setters.positionsAtHeight(originMutable.getY() + height)) {
				int sideCount = 0;
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					final BlockPos relativeTopPos = topPos.relative(direction);
					if (setters.isSet(relativeTopPos)) sideCount += 1;
				}
				if (sideCount >= 4) setters.addWaterPosition(topPos);
			}

			if (!setters.hasWaterPositions()) continue;

			mutable.setWithOffset(originMutable, 0, height - 1, 0);
			placeInSquare(
				level,
				mutable,
				state,
				bluntness,
				curveDistance,
				curveFactorX,
				xWidth + 1,
				curveFactorZ,
				zWidth + 1,
				setter,
				random
			);

			mutable.setWithOffset(originMutable, 0, height + 1, 0);
			placeInSquare(
				level,
				mutable,
				state,
				bluntness,
				curveDistance,
				curveFactorX,
				xWidth + 1,
				curveFactorZ,
				zWidth + 1,
				cribSetter,
				random
			);

			mutable.setWithOffset(originMutable, 0, height + 2, 0);
			placeInSquare(
				level,
				mutable,
				state,
				bluntness,
				curveDistance,
				curveFactorX,
				xWidth + 1,
				curveFactorZ,
				zWidth + 1,
				cribExtraSetter,
				random
			);

			final List<BlockPos> waterPositions = setters.waterPositions();
			for (BlockPos waterPos : waterPositions) {
				mutable.set(waterPos);
				setter.set(waterPos, waterState.getState(level, random, waterPos));
				setter.set(mutable.move(Direction.DOWN).immutable(), state.getState(level, random, mutable));
			}

			setters.posToStateMap().forEach((posx, statex) -> {
				if (statex.is(DUMMY_BLOCK)) return;
				safeSetBlock(level, posx, statex, safeToReplace);
			});

			config.decorationFeature().value().place(level, context.chunkGenerator(), random, Util.getRandom(waterPositions, random).below());
			return true;
		}

		return false;
	}

	protected static void placeInSquare(
		WorldGenLevel level,
		BlockPos pos,
		BlockStateProvider stateProvider,
		double heightProgress,
		double curveDistance,
		double curveFactorX,
		int xWidth,
		double curveFactorZ,
		int zWidth,
		BlockSetter setter,
		RandomSource random
	) {
		final double heightBasedCurveDistance = curveDistance * heightProgress;
		final double xCurve = curveFactorX * heightBasedCurveDistance;
		final double zCurve = curveFactorZ * heightBasedCurveDistance;
		final double inverseHeightProgress = (1D - heightProgress) + 0.4D;
		final double xWidthDistance = xWidth * inverseHeightProgress;
		final double zWidthDistance = zWidth * inverseHeightProgress;

		final Vec3 centerPos = pos.getCenter().add(xCurve, 0D, zCurve);
		for (double xOffset = -(xWidth + 0.5D); xOffset <= xWidth + 0.5D; xOffset += 0.1D) {
			for (double zOffset = -(zWidth + 0.5D); zOffset <= zWidth + 0.5D; zOffset += 0.1D) {
				Vec3 offsetPos = new Vec3(
					pos.getX() + xOffset + xCurve,
					pos.getY() + 0.5D,
					pos.getZ() + zOffset + zCurve
				);

				if (!centerPos.closerThan(offsetPos, xWidthDistance)) continue;
				if (!centerPos.closerThan(offsetPos, zWidthDistance)) continue;

				final BlockPos placementPos = BlockPos.containing(offsetPos);
				setter.set(placementPos, stateProvider.getState(level, random, placementPos));
			}
		}
	}

	private static class BlockSetters {
		final List<BlockPos> waterPositions = new ArrayList<>();
		final List<BlockPos> cribBasePositions = new ArrayList<>();
		final Map<BlockPos, BlockState> posToStateMap = new HashMap<>();
		private final BlockSetter setter;
		private final BlockSetter cribSetter;
		private final BlockSetter cribExtraSetter;

		private BlockSetters(float cribChanceA, float cribChanceB, RandomSource random) {
			this.setter = new BlockSetter() {
				@Override
				public void set(BlockPos pos, BlockState state) {
					BlockSetters.this.posToStateMap.put(pos, state);
				}

				@Override
				public boolean isSet(BlockPos pos) {
					return BlockSetters.this.isSet(pos);
				}
			};

			this.cribSetter = new BlockSetter() {
				@Override
				public void set(BlockPos pos, BlockState state) {
					if (this.isSet(pos)) return;
					if (BlockSetters.this.waterPositions.stream().anyMatch(posx -> posx.atY(0).equals(pos.atY(0))) || random.nextFloat() > cribChanceA) {
						state = DUMMY_BLOCK.defaultBlockState();
					}
					BlockSetters.this.posToStateMap.put(pos, state);
					if (!state.is(DUMMY_BLOCK)) BlockSetters.this.cribBasePositions.add(pos);
				}

				@Override
				public boolean isSet(BlockPos pos) {
					return BlockSetters.this.isSet(pos);
				}
			};

			this.cribExtraSetter = new BlockSetter() {
				@Override
				public void set(BlockPos pos, BlockState state) {
					if (this.isSet(pos)) return;
					if (BlockSetters.this.cribBasePositions.stream().noneMatch(posx -> posx.atY(0).equals(pos.atY(0))) || random.nextFloat() > cribChanceB) {
						state = DUMMY_BLOCK.defaultBlockState();
					}
					BlockSetters.this.posToStateMap.put(pos, state);
				}

				@Override
				public boolean isSet(BlockPos pos) {
					return BlockSetters.this.isSet(pos);
				}
			};
		}

		public BlockSetter setter() {
			return this.setter;
		}

		public BlockSetter cribSetter() {
			return this.cribSetter;
		}

		public BlockSetter cribExtraSetter() {
			return this.cribExtraSetter;
		}

		public void addWaterPosition(BlockPos pos) {
			this.waterPositions.add(pos);
		}

		public List<BlockPos> waterPositions() {
			return this.waterPositions;
		}

		public boolean hasWaterPositions() {
			return !this.waterPositions.isEmpty();
		}

		public Map<BlockPos, BlockState> posToStateMap() {
			return this.posToStateMap;
		}

		public boolean isSet(BlockPos pos) {
			return this.posToStateMap.containsKey(pos);
		}

		public List<BlockPos> positionsAtHeight(int y) {
			return posToStateMap.keySet().stream()
				.filter(posx -> posx.getY() == y)
				.toList();
		}
	}

	private interface BlockSetter {
		void set(BlockPos pos, BlockState state);

		boolean isSet(BlockPos pos);
	}
}
