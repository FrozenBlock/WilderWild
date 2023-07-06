/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CattailFeatureConfig(IntProvider width, IntProvider placementAttempts, boolean inWater,
								   TagKey<Block> placeableBlocks) implements FeatureConfiguration {
	public static final Codec<CattailFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			IntProvider.CODEC.fieldOf("width").forGetter(config -> config.width),
			IntProvider.CODEC.fieldOf("placementAttempts").forGetter(config -> config.placementAttempts),
			Codec.BOOL.fieldOf("water").forGetter(config -> config.inWater),
			TagKey.codec(Registries.BLOCK).fieldOf("placeableBlocks").forGetter(config -> config.placeableBlocks)
		).apply(instance, CattailFeatureConfig::new)
	);
}
