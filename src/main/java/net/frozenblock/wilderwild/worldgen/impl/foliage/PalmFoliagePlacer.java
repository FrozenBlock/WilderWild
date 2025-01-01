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

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PalmFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		palmCodec(instance).apply(instance, PalmFoliagePlacer::new)
	);

	protected final IntProvider frondLength;

	public PalmFoliagePlacer(@NotNull IntProvider radius, @NotNull IntProvider offset, @NotNull IntProvider frondLength) {
		super(radius, offset);
		this.frondLength = frondLength;
	}

	@Contract("_ -> new")
	protected static <P extends PalmFoliagePlacer> Products.@NotNull P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, IntProvider> palmCodec(
		RecordCodecBuilder.@NotNull Instance<P> instance
	) {
		return foliagePlacerParts(instance)
			.and(IntProvider.codec(0, 16).fieldOf("frond_length").forGetter(placer -> placer.frondLength));
	}

	@Override
	@NotNull
	protected FoliagePlacerType<?> type() {
		return WWFeatures.PALM_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader world,
		FoliagePlacer.FoliageSetter placer,
		RandomSource random,
		TreeConfiguration config,
		int trunkHeight,
		FoliagePlacer.@NotNull FoliageAttachment node,
		int foliageHeight,
		int radius,
		int offset
	) {
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		boolean isSmall = radius <= 1;

		BlockPos topLayerPos = node.pos().above(offset);
		Vec3 topLayerCenter = Vec3.atCenterOf(topLayerPos);
		if (isSmall) {
			tryPlaceLeaf(world, placer, random, config, topLayerPos);
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				mutablePos.setWithOffset(topLayerPos, direction);
				tryPlaceLeaf(world, placer, random, config, mutablePos);
			}
		} else {
			for (int xOff = -radius; xOff <= radius; ++xOff) {
				for (int zOff = -radius; zOff <= radius; ++zOff) {
					mutablePos.setWithOffset(topLayerPos, xOff, 0, zOff);
					Vec3 placePosCenter = Vec3.atCenterOf(mutablePos);
					if (placePosCenter.closerThan(topLayerCenter, radius + 0.5D)) {
						tryPlaceLeaf(world, placer, random, config, mutablePos);
					}
				}
			}
		}

		BlockPos middleLayerPos = topLayerPos.below();
		List<BlockPos> frondPoses = new ArrayList<>();

		for (int xOff = -radius; xOff <= radius; ++xOff) {
			for (int zOff = -radius; zOff <= radius; ++zOff) {
				mutablePos.setWithOffset(middleLayerPos, xOff, 0, zOff);
				tryPlaceLeaf(world, placer, random, config, mutablePos);
				if (isCorner(xOff, zOff, radius)) {
					if (!isSmall) {
						BlockPos frondPos = mutablePos.immutable();
						frondPoses.add(frondPos);
					}
				} else if (isEdge(xOff, zOff, radius)) {
					Direction offsetDir = Direction.getApproximateNearest(xOff, 0, zOff);
					mutablePos.move(offsetDir.getUnitVec3i());
					tryPlaceLeaf(world, placer, random, config, mutablePos);
					frondPoses.add(mutablePos.immutable());
				}
			}
		}

		for (BlockPos frondPos : frondPoses) {
			int frondLength;
			frondLength = this.frondLength.sample(random);
			for (int i = 0; i < frondLength; ++i) {
				mutablePos.setWithOffset(frondPos, 0, -i, 0);
				tryPlaceLeaf(world, placer, random, config, mutablePos);
			}
		}
	}

	private static boolean isCorner(int xOff, int zOff, int radius) {
		return Math.abs(xOff) == radius && Math.abs(zOff) == radius;
	}

	private static boolean isEdge(int xOff, int zOff, int radius) {
		return (Math.abs(xOff) == radius && zOff == 0) || (Math.abs(zOff) == radius && xOff == 0);
	}

	@Override
	public int foliageHeight(@NotNull RandomSource randomSource, int i, @NotNull TreeConfiguration treeConfiguration) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}
