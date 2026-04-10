/*
 * Copyright 2025-2026 FrozenBlock
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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record SulfurSpringFeatureConfig(
	IntProvider height,
	IntProvider width,
	FloatProvider curveDistance,
	FloatProvider bluntness,
	float cribBlockChance,
	float cribExtraBlockChance,
	BlockStateProvider state,
	BlockStateProvider waterState,
	Holder<PlacedFeature> decorationFeature,
	HolderSet<Block> replaceable,
	HolderSet<Block> cannotReplace
) implements FeatureConfiguration {
	public static final Codec<SulfurSpringFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			IntProviders.POSITIVE_CODEC.fieldOf("height").forGetter(config -> config.height),
			IntProviders.POSITIVE_CODEC.fieldOf("width").forGetter(config -> config.width),
			FloatProviders.CODEC.fieldOf("curve_distance").forGetter(config -> config.curveDistance),
			FloatProviders.codec(0F, 1F).fieldOf("bluntness").forGetter(config -> config.bluntness),
			ExtraCodecs.floatRange(0F, 1F).fieldOf("crib_block_chance").forGetter(config -> config.cribBlockChance),
			ExtraCodecs.floatRange(0F, 1F).fieldOf("crib_extra_block_chance").forGetter(config -> config.cribExtraBlockChance),
			BlockStateProvider.CODEC.fieldOf("block_state").forGetter(config -> config.state),
			BlockStateProvider.CODEC.fieldOf("water_block_state").forGetter(config -> config.waterState),
			PlacedFeature.CODEC.fieldOf("decoration_feature").forGetter(config -> config.decorationFeature),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable").forGetter(config -> config.replaceable),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("cannot_replace").forGetter(config -> config.cannotReplace)
		).apply(instance, SulfurSpringFeatureConfig::new)
	);
}
