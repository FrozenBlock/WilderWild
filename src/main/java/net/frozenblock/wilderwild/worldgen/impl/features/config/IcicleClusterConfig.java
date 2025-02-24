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

package net.frozenblock.wilderwild.worldgen.impl.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class IcicleClusterConfig implements FeatureConfiguration {
	public static final Codec<IcicleClusterConfig> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.intRange(1, 512)
					.fieldOf("floor_to_ceiling_search_range")
					.forGetter(icicleClusterConfig -> icicleClusterConfig.floorToCeilingSearchRange),
				IntProvider.codec(1, 128).fieldOf("height").forGetter(icicleClusterConfig -> icicleClusterConfig.height),
				IntProvider.codec(1, 128).fieldOf("radius").forGetter(icicleClusterConfig -> icicleClusterConfig.radius),
				Codec.intRange(0, 64)
					.fieldOf("max_icicle_height_diff")
					.forGetter(icicleClusterConfig -> icicleClusterConfig.maxIcicleHeightDiff),
				Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(icicleClusterConfig -> icicleClusterConfig.heightDeviation),
				IntProvider.codec(0, 128)
					.fieldOf("ice_layer_thickness")
					.forGetter(icicleClusterConfig -> icicleClusterConfig.iceLayerThickness),
				FloatProvider.codec(0F, 2F).fieldOf("density").forGetter(icicleClusterConfig -> icicleClusterConfig.density),
				Codec.floatRange(0F, 1F)
					.fieldOf("chance_of_icicle_at_max_distance_from_center")
					.forGetter(icicleClusterConfig -> icicleClusterConfig.chanceOfIcicleAtMaxDistanceFromCenter),
				Codec.intRange(1, 64)
					.fieldOf("max_distance_from_edge_affecting_chance_of_icicle")
					.forGetter(icicleClusterConfig -> icicleClusterConfig.maxDistanceFromEdgeAffectingChanceOfIcicle),
				Codec.intRange(1, 64)
					.fieldOf("max_distance_from_center_affecting_height_bias")
					.forGetter(icicleClusterConfig -> icicleClusterConfig.maxDistanceFromCenterAffectingHeightBias)
			)
			.apply(instance, IcicleClusterConfig::new)
	);
	public final int floorToCeilingSearchRange;
	public final IntProvider height;
	public final IntProvider radius;
	public final int maxIcicleHeightDiff;
	public final int heightDeviation;
	public final IntProvider iceLayerThickness;
	public final FloatProvider density;
	public final float chanceOfIcicleAtMaxDistanceFromCenter;
	public final int maxDistanceFromEdgeAffectingChanceOfIcicle;
	public final int maxDistanceFromCenterAffectingHeightBias;

	public IcicleClusterConfig(
		int floorToCeilingSearchRange,
		IntProvider height,
		IntProvider radius,
		int maxIcicleHeightDiff,
		int heightDeviation,
		IntProvider iceLayerThickness,
		FloatProvider density,
		float chanceOfIcicleAtMaxDistanceFromCenter,
		int maxDistanceFromEdgeAffectingChanceOfIcicle,
		int maxDistanceFromCenterAffectingHeightBias
	) {
		this.floorToCeilingSearchRange = floorToCeilingSearchRange;
		this.height = height;
		this.radius = radius;
		this.maxIcicleHeightDiff = maxIcicleHeightDiff;
		this.heightDeviation = heightDeviation;
		this.iceLayerThickness = iceLayerThickness;
		this.density = density;
		this.chanceOfIcicleAtMaxDistanceFromCenter = chanceOfIcicleAtMaxDistanceFromCenter;
		this.maxDistanceFromEdgeAffectingChanceOfIcicle = maxDistanceFromEdgeAffectingChanceOfIcicle;
		this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
	}
}
