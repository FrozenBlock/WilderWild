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
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SulfurSpringDecorationFeatureConfig(
	BlockStateProvider state,
	BlockStateProvider topState,
	BlockStateProvider bottomState,
	HolderSet<Block> replaceable,
	HolderSet<Block> cannotReplace
) implements FeatureConfiguration {
	public static final Codec<SulfurSpringDecorationFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			BlockStateProvider.CODEC.fieldOf("spring_state").forGetter(config -> config.state),
			BlockStateProvider.CODEC.fieldOf("top_state").forGetter(config -> config.topState),
			BlockStateProvider.CODEC.fieldOf("bottom_state").forGetter(config -> config.bottomState),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable").forGetter(config -> config.replaceable),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("cannot_replace").forGetter(config -> config.cannotReplace)
		).apply(instance, SulfurSpringDecorationFeatureConfig::new)
	);
}
