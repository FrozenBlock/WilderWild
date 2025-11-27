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
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LegacyMapleFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<LegacyMapleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		foliagePlacerParts(instance)
			.and(IntProvider.codec(0, 24).fieldOf("length").forGetter(placer -> placer.length))
			.apply(instance, LegacyMapleFoliagePlacer::new)
	);

	protected final IntProvider length;

	public LegacyMapleFoliagePlacer(
		IntProvider radius,
		IntProvider offset,
		IntProvider foliageHeight
	) {
		super(radius, offset);
		this.length = foliageHeight;
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return WWFeatures.LEGACY_MAPLE_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader level,
		FoliagePlacer.FoliageSetter placer,
		@NotNull RandomSource random,
		TreeConfiguration config,
		int trunkHeight,
		FoliagePlacer.@NotNull FoliageAttachment attachment,
		int foliageHeight,
		int radius,
		int offset
	) {
		final BlockPos pos = attachment.pos();
		int totalHeight = offset + foliageHeight;
		int currentHeight = totalHeight;

		for (int l = offset; l >= -foliageHeight; l--) {
			this.placeLeavesInCircle(level, placer, random, config, pos, radius, l, attachment.doubleTrunk(), totalHeight, currentHeight, foliageHeight);
			currentHeight -= 1;
		}
	}

	protected void placeLeavesInCircle(
		LevelSimulatedReader level,
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
		double radius = providedRadius + ((random.nextDouble() - this.getRandomRadiusShrink()) * 0.4D);
		double increment = radius * 0.05D;

		for (double j = -radius; j <= radius; j += increment) {
			for (double k = -radius; k <= radius; k += increment) {
				if (!this.shouldSkipMapleLocationSigned(j, k, radius, giantTrunk, totalHeight, currentHeight, trunkHeight)) {
					mutableBlockPos.setWithOffset(centerPos, (int) j, y, (int) k);
					tryPlaceLeaf(level, placer, random, config, mutableBlockPos);
				}
			}
		}
	}

	protected double getRandomRadiusShrink() {
		return -0.5D;
	}

	protected boolean shouldSkipMapleLocationSigned(
		double dx, double dz, double radius, boolean giantTrunk, int totalHeight, int currentHeight, int trunkHeight
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

		return this.shouldSkipMapleLocation(i, j, radius, totalHeight, currentHeight, trunkHeight);
	}

	protected boolean shouldSkipMapleLocation(
		 double xDistance, double zDistance, double radius, int totalHeight, int currentHeight, int trunkHeight
	) {
		double mapleFunction = this.getMapleFoliageFunction(totalHeight, currentHeight, radius, currentHeight <= trunkHeight);
		double distance = new Vec3(xDistance, 0, zDistance).horizontalDistance();
		return distance > mapleFunction && distance > 0.4D;
	}

	protected double getMapleFoliageFunction(
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
		double min = hot ? 1.2D : 0D;

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
