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

package net.frozenblock.wilderwild.worldgen.impl.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SnowAndIceDiskFeatureConfig(IntProvider radius, IntProvider iceRadius, float placementChance, float fadeStartDistancePercent) implements FeatureConfiguration {
	public static final Codec<SnowAndIceDiskFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			IntProvider.CODEC.fieldOf("radius").forGetter(config -> config.radius),
			IntProvider.CODEC.fieldOf("ice_radius").forGetter(config -> config.iceRadius),
			Codec.FLOAT.fieldOf("placement_chance").forGetter(config -> config.placementChance),
			Codec.FLOAT.fieldOf("fade_start_distance_percent").forGetter(config -> config.fadeStartDistancePercent)
		).apply(instance, SnowAndIceDiskFeatureConfig::new)
	);

}
