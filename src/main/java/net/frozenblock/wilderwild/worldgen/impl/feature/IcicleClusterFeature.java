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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.IcicleClusterConfig;
import net.frozenblock.wilderwild.worldgen.impl.util.IcicleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;

public class IcicleClusterFeature extends Feature<IcicleClusterConfig> {

	public IcicleClusterFeature(@NotNull Codec<IcicleClusterConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<IcicleClusterConfig> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		IcicleClusterConfig config = context.config();
		RandomSource randomSource = context.random();
		if (!IcicleUtils.isEmptyOrWater(worldGenLevel, blockPos)) {
			return false;
		} else {
			int height = config.height.sample(randomSource);
			float g = config.density.sample(randomSource);
			int j = config.radius.sample(randomSource);
			int k = config.radius.sample(randomSource);

			for (int l = -j; l <= j; l++) {
				for (int m = -k; m <= k; m++) {
					double icicleChance = this.getChanceOfIcicle(j, k, l, m, config);
					BlockPos blockPos2 = blockPos.offset(l, 0, m);
					this.placeColumn(worldGenLevel, randomSource, blockPos2, l, m, icicleChance, height, g, config);
				}
			}

			return true;
		}
	}

	private void placeColumn(
		WorldGenLevel worldGenLevel,
		RandomSource randomSource,
		BlockPos blockPos,
		int i,
		int j,
		double icicleChance,
		int height,
		float g,
		@NotNull IcicleClusterConfig icicleClusterConfig
	) {
		Optional<Column> optional = Column.scan(
			worldGenLevel, blockPos, icicleClusterConfig.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater
		);
		if (optional.isPresent()) {
			OptionalInt optionalInt = optional.get().getCeiling();
			OptionalInt optionalInt2 = optional.get().getFloor();
			if (optionalInt.isPresent() || optionalInt2.isPresent()) {
				Column column = optional.get();

				OptionalInt optionalInt3 = column.getFloor();
				boolean canPlaceIcicleA = randomSource.nextDouble() < icicleChance;
				int o;
				if (optionalInt.isPresent() && canPlaceIcicleA && !this.isLava(worldGenLevel, blockPos.atY(optionalInt.getAsInt()))) {
					int m = icicleClusterConfig.iceLayerThickness.sample(randomSource);
					this.replaceBlocksWithIceBlocks(worldGenLevel, blockPos.atY(optionalInt.getAsInt()), m, Direction.UP);
					int n;
					if (optionalInt3.isPresent()) {
						n = Math.min(height, optionalInt.getAsInt() - optionalInt3.getAsInt());
					} else {
						n = height;
					}

					o = this.getIcicleHeight(randomSource, i, j, g, n, icicleClusterConfig);
				} else {
					o = 0;
				}

				int w;
				int p;
				if (optionalInt.isPresent() && optionalInt3.isPresent() && optionalInt.getAsInt() - o <= optionalInt3.getAsInt()) {
					int q = optionalInt3.getAsInt();
					int r = optionalInt.getAsInt();
					int s = Math.max(r - o, q + 1);
					int t = Math.min(q, r - 1);
					int u = Mth.randomBetweenInclusive(randomSource, s, t + 1);
					int v = u - 1;
					p = r - u;
					w = v - q;
				} else {
					p = o;
					w = 0;
				}

				boolean bl4 = randomSource.nextBoolean() && p > 0 && w > 0 && column.getHeight().isPresent() && p + w == column.getHeight().getAsInt();
				if (optionalInt.isPresent()) {
					IcicleUtils.growIcicle(worldGenLevel, blockPos.atY(optionalInt.getAsInt() - 1), Direction.DOWN, p, bl4);
				}

				if (optionalInt3.isPresent()) {
					IcicleUtils.growIcicle(worldGenLevel, blockPos.atY(optionalInt3.getAsInt() + 1), Direction.UP, w, bl4);
				}
			}
		}
	}

	private boolean isLava(@NotNull LevelReader levelReader, BlockPos blockPos) {
		return levelReader.getBlockState(blockPos).is(Blocks.LAVA);
	}

	private int getIcicleHeight(@NotNull RandomSource randomSource, int i, int j, float f, int k, IcicleClusterConfig icicleClusterConfig) {
		if (randomSource.nextFloat() > f) {
			return 0;
		} else {
			int l = Math.abs(i) + Math.abs(j);
			float g = Mth.clampedMap(l, 0F, icicleClusterConfig.maxDistanceFromCenterAffectingHeightBias, (float) k / 2F, 0F);
			return (int)randomBetweenBiased(randomSource, 0F, (float)k, g, (float)icicleClusterConfig.heightDeviation);
		}
	}

	private void replaceBlocksWithIceBlocks(WorldGenLevel worldGenLevel, @NotNull BlockPos blockPos, int i, Direction direction) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

		for (int j = 0; j < i; j++) {
			if (!IcicleUtils.placeIceBlockIfPossible(worldGenLevel, mutableBlockPos)) {
				return;
			}

			mutableBlockPos.move(direction);
		}
	}

	private double getChanceOfIcicle(int i, int j, int k, int l, @NotNull IcicleClusterConfig icicleClusterConfig) {
		return Mth.clampedMap(
			(float)Math.min(i - Math.abs(k), j - Math.abs(l)),
			0F,
			(float)icicleClusterConfig.maxDistanceFromEdgeAffectingChanceOfIcicle,
			icicleClusterConfig.chanceOfIcicleAtMaxDistanceFromCenter,
			1F
		);
	}

	private static float randomBetweenBiased(RandomSource randomSource, float f, float g, float h, float i) {
		return ClampedNormalFloat.sample(randomSource, h, i, f, g);
	}
}
