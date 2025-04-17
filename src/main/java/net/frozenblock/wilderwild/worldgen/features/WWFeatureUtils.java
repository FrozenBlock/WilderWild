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

package net.frozenblock.wilderwild.worldgen.features;

import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public final class WWFeatureUtils {
	private WWFeatureUtils() {
		throw new UnsupportedOperationException("WWFeatureUtils contains only static declarations.");
	}

	@NotNull
	public static FrozenLibConfiguredFeature<NoneFeatureConfiguration, ConfiguredFeature<NoneFeatureConfiguration, ?>> register(@NotNull String id, @NotNull Feature<NoneFeatureConfiguration> feature) {
		return register(id, feature, FeatureConfiguration.NONE);
	}

	@NotNull
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> FrozenLibConfiguredFeature<FC, ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
		var key = WWConstants.id(id);
		FrozenLibConfiguredFeature<FC, ConfiguredFeature<FC, ?>> frozen = new FrozenLibConfiguredFeature<>(key);
		frozen.makeAndSetHolder(feature, config);
		return frozen;
	}

	@NotNull
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> FrozenLibConfiguredFeature<FC, ConfiguredFeature<FC, ?>> register(@NotNull String id) {
		var key = WWConstants.id(id);
		return new FrozenLibConfiguredFeature<>(key);
	}
}
