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
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record IcicleConfig(
	float chanceOfTallerIcicle,
	float chanceOfDirectionalSpread,
	float chanceOfSpreadRadius2,
	float chanceOfSpreadRadius3,
	boolean placeIceBlocks
) implements FeatureConfiguration {
	public static final Codec<IcicleConfig> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.floatRange(0F, 1F)
				.fieldOf("chance_of_taller_icicle")
				.orElse(0.2F)
				.forGetter(icicleConfig -> icicleConfig.chanceOfTallerIcicle),
			Codec.floatRange(0F, 1F)
				.fieldOf("chance_of_directional_spread")
				.orElse(0.7F)
				.forGetter(icicleConfig -> icicleConfig.chanceOfDirectionalSpread),
			Codec.floatRange(0F, 1F)
				.fieldOf("chance_of_spread_radius2")
				.orElse(0.5F)
				.forGetter(icicleConfig -> icicleConfig.chanceOfSpreadRadius2),
			Codec.floatRange(0F, 1F)
				.fieldOf("chance_of_spread_radius3")
				.orElse(0.5F)
				.forGetter(icicleConfig -> icicleConfig.chanceOfSpreadRadius3),
			Codec.BOOL
				.fieldOf("place_ice_blocks")
				.forGetter(icicleConfig -> icicleConfig.placeIceBlocks)
		).apply(instance, IcicleConfig::new)
	);
}
