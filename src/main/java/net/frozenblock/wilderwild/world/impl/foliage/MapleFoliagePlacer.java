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

package net.frozenblock.wilderwild.world.impl.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MapleFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<MapleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		(instance) -> foliagePlacerParts(instance)
			.and(IntProvider.codec(0, 24).fieldOf("length").forGetter((placer) -> placer.length))
			.apply(instance, MapleFoliagePlacer::new)
	);
	private final IntProvider length;

	public MapleFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight) {
		super(radius, offset);
		this.length = foliageHeight;
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return RegisterFeatures.MAPLE_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader world,
		FoliagePlacer.FoliageSetter placer,
		@NotNull RandomSource random,
		TreeConfiguration config,
		int trunkHeight,
		FoliagePlacer.@NotNull FoliageAttachment node,
		int foliageHeight,
		int radius,
		int offset
	) {
		BlockPos blockPos = node.pos();
		int totalHeight = offset + foliageHeight;
		int currentHeight = totalHeight;

		for (int l = offset; l >= -foliageHeight; l--) {
			this.placeLeavesInCircle(world, placer, random, config, blockPos, radius, l, node.doubleTrunk(), totalHeight, currentHeight, foliageHeight);
			currentHeight -= 1;
		}
	}

	protected void placeLeavesInCircle(
		LevelSimulatedReader world,
		FoliagePlacer.FoliageSetter placer,
		@NotNull RandomSource random,
		TreeConfiguration config,
		BlockPos centerPos,
		int providedRadius,
		int y,
		boolean giantTrunk,
		int totalHeight,
		int currentHeight,
		int trunkHeight
	) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		double radius = providedRadius + ((random.nextDouble() - 0.5D) * 0.6D);

		for (double j = -radius + 0.6D; j <= radius - 0.6D; j += 0.2D) {
			for (double k = -radius + 0.6D; k <= radius - 0.6D; k += 0.2D) {
				if (!this.shouldSkipMapleLocationSigned(random, j, y, k, radius, giantTrunk, totalHeight, currentHeight, trunkHeight)) {
					mutableBlockPos.setWithOffset(centerPos, (int) j, y, (int) k);
					tryPlaceLeaf(world, placer, random, config, mutableBlockPos);
				}
			}
		}
	}

	protected boolean shouldSkipMapleLocationSigned(
		RandomSource random, double dx, int y, double dz, double radius, boolean giantTrunk, int totalHeight, int currentHeight, int trunkHeight
	) {
		double i;
		double j;
		if (giantTrunk) {
			i = Math.min(Math.abs(dx), Math.abs(dx - 1));
			j = Math.min(Math.abs(dz), Math.abs(dz - 1));
		} else {
			i = Math.abs(dx);
			j = Math.abs(dz);
		}

		return this.shouldSkipMapleLocation(random, i, y, j, radius, giantTrunk, totalHeight, currentHeight, trunkHeight);
	}

	protected boolean shouldSkipMapleLocation(
		RandomSource random, double xDistance, int y, double zDistance, double radius, boolean giantTrunk, int totalHeight, int currentHeight, int trunkHeight
	) {
		double mapleFunction = this.getMapleFoliageFunction(totalHeight, currentHeight, radius, currentHeight <= trunkHeight);
		Vec3 vec3 = new Vec3(xDistance, 0, zDistance);
		double distance = Vec3.ZERO.distanceTo(vec3);
		return distance > mapleFunction && distance > 0.4D;
	}

	private double getMapleFoliageFunction(
		double totalHeight,
		double height,
		double radius,
		boolean hot
	) {
		double flippedHeight = totalHeight - height;
		double functionHeight = totalHeight - 1D;

		double functionInput = flippedHeight + (height / functionHeight);
		double functionNumerator = (functionInput * functionInput) * Math.PI;
		double functionDenominator = (functionHeight * functionHeight) + (functionHeight * 2.5D) + (totalHeight * radius);
		double function = Math.sin(functionNumerator / functionDenominator);
		double finalFunction = function * (radius * 0.75D) + 0.45D;
		double min = 0D;
		if (hot) {
			min = 1.2D;
		}

		return Math.max(finalFunction, min);
	}

	@Override
	public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
		return Math.max(5, trunkHeight - this.length.sample(random));
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
		return false;
	}
}
