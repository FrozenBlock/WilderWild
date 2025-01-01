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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class WindmillPalmFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<WindmillPalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		winePalmCodec(instance).apply(instance, WindmillPalmFoliagePlacer::new)
	);

	public WindmillPalmFoliagePlacer(@NotNull IntProvider radius) {
		super(radius, ConstantInt.of(0));
	}

	@Contract("_ -> new")
	protected static <P extends WindmillPalmFoliagePlacer> Products.@NotNull P1<RecordCodecBuilder.Mu<P>, IntProvider> winePalmCodec(RecordCodecBuilder.@NotNull Instance<P> instance) {
		return instance.group(IntProvider.codec(0, 16).fieldOf("radius").forGetter(placer -> placer.radius));
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return WWFeatures.WINDMILL_PALM_FOLIAGE_PLACER;
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
		BlockPos bottomPos = node.pos().below(radius);
		int totalHeight = (radius * 2) + 1;

		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		for (int currentHeight = 0; currentHeight < totalHeight; currentHeight++) {
			double currentRadius = (Math.abs(Math.sin((currentHeight * Math.PI) / totalHeight)) * (radius - 0.5D)) + 1D;
			BlockPos centerPos = bottomPos.above(currentHeight);
			Vec3 center = Vec3.atCenterOf(centerPos);
			if (currentRadius <= 1D) {
				tryPlaceLeaf(world, placer, random, config, centerPos);
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					mutablePos.setWithOffset(centerPos, direction);
					tryPlaceLeaf(world, placer, random, config, mutablePos);
				}
			} else {
				for (int xOff = -radius; xOff <= radius; ++xOff) {
					for (int zOff = -radius; zOff <= radius; ++zOff) {
						mutablePos.setWithOffset(centerPos, xOff, 0, zOff);
						Vec3 placePosCenter = Vec3.atCenterOf(mutablePos);
						if (placePosCenter.closerThan(center, currentRadius)) {
							tryPlaceLeaf(world, placer, random, config, mutablePos);
						}
					}
				}
			}
		}
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
