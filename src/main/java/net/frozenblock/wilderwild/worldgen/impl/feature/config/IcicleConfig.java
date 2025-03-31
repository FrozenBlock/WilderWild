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

package net.frozenblock.wilderwild.worldgen.impl.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class IcicleConfig implements FeatureConfiguration {
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
			)
			.apply(instance, IcicleConfig::new)
	);
	public final float chanceOfTallerIcicle;
	public final float chanceOfDirectionalSpread;
	public final float chanceOfSpreadRadius2;
	public final float chanceOfSpreadRadius3;
	public final boolean placeIceBlocks;

	public IcicleConfig(
		float chanceOfTallerIcicle,
		float chanceOfDirectionalSpread,
		float chanceOfSpreadRadius2,
		float chanceOfSpreadRadius3,
		boolean placeIceBlocks
	) {
		this.chanceOfTallerIcicle = chanceOfTallerIcicle;
		this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
		this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
		this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
		this.placeIceBlocks = placeIceBlocks;
	}
}
