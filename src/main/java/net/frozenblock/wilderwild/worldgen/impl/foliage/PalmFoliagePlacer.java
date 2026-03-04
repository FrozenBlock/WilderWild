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

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;

public class PalmFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		foliagePlacerParts(instance)
			.and(IntProvider.codec(0, 16).fieldOf("frond_length").forGetter(placer -> placer.frondLength))
			.apply(instance, PalmFoliagePlacer::new)
	);

	protected final IntProvider frondLength;

	public PalmFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider frondLength) {
		super(radius, offset);
		this.frondLength = frondLength;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return WWFeatures.PALM_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		WorldGenLevel level,
		FoliagePlacer.FoliageSetter foliageSetter,
		RandomSource random,
		TreeConfiguration config,
		int treeHeight,
		FoliagePlacer.FoliageAttachment foliageAttachment,
		int foliageHeight,
		int leafRadius,
		int offset
	) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final boolean isSmall = leafRadius <= 1;

		final BlockPos topLayerPos = foliageAttachment.pos().above(offset);
		final Vec3 topLayerCenter = Vec3.atCenterOf(topLayerPos);
		if (isSmall) {
			tryPlaceLeaf(level, foliageSetter, random, config, topLayerPos);
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				tryPlaceLeaf(level, foliageSetter, random, config, mutable.setWithOffset(topLayerPos, direction));
			}
		} else {
			for (int xOff = -leafRadius; xOff <= leafRadius; ++xOff) {
				for (int zOff = -leafRadius; zOff <= leafRadius; ++zOff) {
					mutable.setWithOffset(topLayerPos, xOff, 0, zOff);
					final Vec3 placePosCenter = Vec3.atCenterOf(mutable);
					if (!placePosCenter.closerThan(topLayerCenter, leafRadius + 0.5D)) continue;
					tryPlaceLeaf(level, foliageSetter, random, config, mutable);
				}
			}
		}

		final BlockPos middleLayerPos = topLayerPos.below();
		final List<BlockPos> frondPoses = new ArrayList<>();

		for (int xOff = -leafRadius; xOff <= leafRadius; ++xOff) {
			for (int zOff = -leafRadius; zOff <= leafRadius; ++zOff) {
				mutable.setWithOffset(middleLayerPos, xOff, 0, zOff);
				tryPlaceLeaf(level, foliageSetter, random, config, mutable);
				if (isCorner(xOff, zOff, leafRadius)) {
					if (isSmall) continue;
					final BlockPos frondPos = mutable.immutable();
					frondPoses.add(frondPos);
				} else if (isEdge(xOff, zOff, leafRadius)) {
					final Direction offsetDir = Direction.getApproximateNearest(xOff, 0, zOff);
					mutable.move(offsetDir.getUnitVec3i());
					tryPlaceLeaf(level, foliageSetter, random, config, mutable);
					frondPoses.add(mutable.immutable());
				}
			}
		}

		for (BlockPos frondPos : frondPoses) {
			final int frondLength = this.frondLength.sample(random);
			for (int i = 0; i < frondLength; ++i) {
				tryPlaceLeaf(level, foliageSetter, random, config, mutable.setWithOffset(frondPos, 0, -i, 0));
			}
		}
	}

	private static boolean isCorner(int xOffset, int zOffset, int radius) {
		return Math.abs(xOffset) == radius && Math.abs(zOffset) == radius;
	}

	private static boolean isEdge(int xOffset, int zOffset, int radius) {
		return (Math.abs(xOffset) == radius && zOffset == 0) || (Math.abs(zOffset) == radius && xOffset == 0);
	}

	@Override
	public int foliageHeight(RandomSource random, int treeHeight, TreeConfiguration config) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}
