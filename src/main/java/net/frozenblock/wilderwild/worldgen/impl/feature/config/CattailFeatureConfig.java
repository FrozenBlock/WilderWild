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
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CattailFeatureConfig(
	IntProvider width, IntProvider placementAttempts, boolean onlyPlaceInWater, TagKey<Block> canBePlacedOn
) implements FeatureConfiguration {
	public static final Codec<CattailFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			IntProvider.CODEC.fieldOf("width").forGetter(config -> config.width),
			IntProvider.CODEC.fieldOf("placement_attempts").forGetter(config -> config.placementAttempts),
			Codec.BOOL.fieldOf("only_place_in_water").forGetter(config -> config.onlyPlaceInWater),
			TagKey.codec(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(config -> config.canBePlacedOn)
		).apply(instance, CattailFeatureConfig::new)
	);
}
