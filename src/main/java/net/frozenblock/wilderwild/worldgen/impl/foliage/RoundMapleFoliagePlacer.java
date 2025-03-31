/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class RoundMapleFoliagePlacer extends MapleFoliagePlacer {
	public static final MapCodec<RoundMapleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		(instance) -> mapleFoliagePlacerParts(instance).apply(instance, RoundMapleFoliagePlacer::new)
	);

	public RoundMapleFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight) {
		super(radius, offset, foliageHeight);
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return WWFeatures.ROUND_MAPLE_FOLIAGE_PLACER;
	}

	@Override
	protected double getRandomRadiusShrink() {
		return -0.3D;
	}

	@Override
	protected double getMapleFoliageFunction(
		double totalHeight,
		double height,
		double radius,
		boolean hot
	) {
		double function = Math.sin(((height * Math.PI) + (totalHeight * 0.5F)) / totalHeight);
		double finalFunction = (function * (radius - 1)) + 1D;

		double min = 0D;
		if (hot) {
			min = 1.2D;
		}

		return Math.max(finalFunction, min);
	}
}
