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

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;

public class WindmillPalmFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<WindmillPalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(IntProvider.codec(0, 16).fieldOf("radius").forGetter(placer -> placer.radius)).apply(instance, WindmillPalmFoliagePlacer::new)
	);

	public WindmillPalmFoliagePlacer(IntProvider radius) {
		super(radius, ConstantInt.of(0));
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return WWFeatures.WINDMILL_PALM_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader level,
		FoliagePlacer.FoliageSetter placer,
		RandomSource random,
		TreeConfiguration config,
		int trunkHeight,
		FoliagePlacer.FoliageAttachment node,
		int foliageHeight,
		int radius,
		int offset
	) {
		final BlockPos bottomPos = node.pos().below(radius);
		final int totalHeight = (radius * 2) + 1;
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int currentHeight = 0; currentHeight < totalHeight; currentHeight++) {
			double currentRadius = (Math.abs(Math.sin((currentHeight * Math.PI) / totalHeight)) * (radius - 0.5D)) + 1D;
			final BlockPos centerPos = bottomPos.above(currentHeight);
			final Vec3 center = Vec3.atCenterOf(centerPos);
			if (currentRadius <= 1D) {
				tryPlaceLeaf(level, placer, random, config, centerPos);
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					tryPlaceLeaf(level, placer, random, config, mutable.setWithOffset(centerPos, direction));
				}
			} else {
				for (int xOff = -radius; xOff <= radius; ++xOff) {
					for (int zOff = -radius; zOff <= radius; ++zOff) {
						mutable.setWithOffset(centerPos, xOff, 0, zOff);
						final Vec3 placePosCenter = Vec3.atCenterOf(mutable);
						if (!placePosCenter.closerThan(center, currentRadius)) continue;
						tryPlaceLeaf(level, placer, random, config, mutable);
					}
				}
			}
		}
	}

	@Override
	public int foliageHeight(RandomSource random, int i, TreeConfiguration config) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}
