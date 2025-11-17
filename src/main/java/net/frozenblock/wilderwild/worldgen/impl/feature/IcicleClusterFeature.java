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

public class IcicleClusterFeature extends Feature<IcicleClusterConfig> {

	public IcicleClusterFeature(Codec<IcicleClusterConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<IcicleClusterConfig> context) {
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();
		final IcicleClusterConfig config = context.config();
		final RandomSource random = context.random();

		if (!IcicleUtils.isEmptyOrWater(level, origin)) return false;

		final int height = config.height().sample(random);
		final float density = config.density().sample(random);
		final int xRadius = config.radius().sample(random);
		final int zRadius = config.radius().sample(random);

		for (int x = -xRadius; x <= xRadius; x++) {
			for (int z = -zRadius; z <= zRadius; z++) {
				double icicleChance = this.getChanceOfIcicle(xRadius, zRadius, x, z, config);
				final BlockPos pos = origin.offset(x, 0, z);
				this.placeColumn(level, random, pos, x, z, icicleChance, height, density, config);
			}
		}
		return true;
	}

	private void placeColumn(WorldGenLevel level, RandomSource random, BlockPos pos, int x, int z, double icicleChance, int height, float density, IcicleClusterConfig config) {
		final Optional<Column> optionalColumn = Column.scan(level, pos, config.floorToCeilingSearchRange(), DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater);
		if (optionalColumn.isEmpty()) return;

		final Column column = optionalColumn.get();
		final OptionalInt ceiling = column.getCeiling();
		final OptionalInt floor = column.getFloor();
		if (ceiling.isEmpty() && floor.isEmpty()) return;

		final boolean canPlaceIcicleA = random.nextDouble() < icicleChance;
		int icicleHeight;
		if (ceiling.isPresent() && canPlaceIcicleA && !this.isLava(level, pos.atY(ceiling.getAsInt()))) {
			final int thickness = config.iceLayerThickness().sample(random);
			this.replaceBlocksWithIceBlocks(level, pos.atY(ceiling.getAsInt()), thickness, Direction.UP);
			int floorOrHeight = floor.isPresent() ? Math.min(height, ceiling.getAsInt() - floor.getAsInt()) : height;

			icicleHeight = this.getIcicleHeight(random, x, z, density, floorOrHeight, config);
		} else {
			icicleHeight = 0;
		}

		int w;
		int p;
		if (ceiling.isPresent() && floor.isPresent() && ceiling.getAsInt() - icicleHeight <= floor.getAsInt()) {
			final int floorHeight = floor.getAsInt();
			final int ceilingHeight = ceiling.getAsInt();
			final int s = Math.max(ceilingHeight - icicleHeight, floorHeight + 1);
			final int t = Math.min(floorHeight, ceilingHeight - 1);
			final int u = Mth.randomBetweenInclusive(random, s, t + 1);
			final int v = u - 1;
			p = ceilingHeight - u;
			w = v - floorHeight;
		} else {
			p = icicleHeight;
			w = 0;
		}

		boolean bl4 = random.nextBoolean() && p > 0 && w > 0 && column.getHeight().isPresent() && p + w == column.getHeight().getAsInt();

		if (ceiling.isPresent()) IcicleUtils.growIcicle(level, pos.atY(ceiling.getAsInt() - 1), Direction.DOWN, p, bl4);
		if (floor.isPresent()) IcicleUtils.growIcicle(level, pos.atY(floor.getAsInt() + 1), Direction.UP, w, bl4);
	}

	private boolean isLava(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).is(Blocks.LAVA);
	}

	private int getIcicleHeight(RandomSource random, int x, int z, float density, int height, IcicleClusterConfig config) {
		if (random.nextFloat() > density) return 0;
		final int distance = Math.abs(x) + Math.abs(z);
		final float clampedDistance = Mth.clampedMap(distance, 0F, config.maxDistanceFromCenterAffectingHeightBias(), (float) height / 2F, 0F);
		return (int)randomBetweenBiased(random, 0F, (float)height, clampedDistance, (float) config.heightDeviation());
	}

	private void replaceBlocksWithIceBlocks(WorldGenLevel level, BlockPos pos, int i, Direction direction) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		for (int j = 0; j < i; j++) {
			if (!IcicleUtils.placeIceBlockIfPossible(level, mutable)) return;
			mutable.move(direction);
		}
	}

	private double getChanceOfIcicle(int i, int j, int k, int l, IcicleClusterConfig config) {
		return Mth.clampedMap(
			(float)Math.min(i - Math.abs(k), j - Math.abs(l)),
			0F,
			(float) config.maxDistanceFromEdgeAffectingChanceOfIcicle(),
                config.chanceOfIcicleAtMaxDistanceFromCenter(),
			1F
		);
	}

	private static float randomBetweenBiased(RandomSource random, float f, float g, float h, float i) {
		return ClampedNormalFloat.sample(random, h, i, f, g);
	}
}
