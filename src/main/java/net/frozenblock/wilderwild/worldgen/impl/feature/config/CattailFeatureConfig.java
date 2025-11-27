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
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CattailFeatureConfig(int width, IntProvider placementAttempts, TagKey<Block> canBePlacedOn) implements FeatureConfiguration {
	public static final Codec<CattailFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			Codec.INT.fieldOf("width").forGetter(config -> config.width),
			IntProvider.CODEC.fieldOf("placement_attempts").forGetter(config -> config.placementAttempts),
			TagKey.codec(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(config -> config.canBePlacedOn)
		).apply(instance, CattailFeatureConfig::new)
	);
}
