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

package net.frozenblock.wilderwild.datagen;

import java.util.Arrays;
import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacementUtils;
import net.frozenblock.wilderwild.worldgen.features.configured.WWAquaticConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWCaveConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.worldgen.features.configured.WWMiscConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.features.placed.WWAquaticPlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWCavePlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
import net.frozenblock.wilderwild.worldgen.features.placed.WWTreePlaced;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.NotNull;

public final class WWFeatureBootstrap {

	public static void bootstrapConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		FrozenLibFeatureUtils.BOOTSTRAP_CONTEXT = (BootstrapContext) entries;

		WWTreeConfigured.registerTreeConfigured(entries);
		WWMiscConfigured.registerMiscConfigured();
		WWCaveConfigured.registerCaveConfigured(entries);
		WWAquaticConfigured.registerAquaticConfigured();
		WWConfiguredFeatures.registerConfiguredFeatures(entries);
	}

	public static void bootstrapPlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		FrozenLibFeatureUtils.BOOTSTRAP_CONTEXT = (BootstrapContext) entries;

		WWTreePlaced.registerTreePlaced();
		WWMiscPlaced.registerMiscPlaced(entries);
		WWCavePlaced.registerCavePlaced(entries);
		WWAquaticPlaced.registerAquaticPlaced(entries);
		WWPlacedFeatures.registerPlacedFeatures(entries);
	}

	/**
	 * @param configuredResourceKey MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static @NotNull Holder<PlacedFeature> register(
		BootstrapContext<PlacedFeature> entries,
		ResourceKey<PlacedFeature> resourceKey,
		ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey,
		PlacementModifier... modifiers
	) {
		return register(entries, resourceKey, configuredResourceKey, Arrays.asList(modifiers));
	}

	/**
	 * @param configuredResourceKey MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static @NotNull Holder<PlacedFeature> register(
		BootstrapContext<PlacedFeature> entries,
		ResourceKey<PlacedFeature> resourceKey,
		ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey,
		List<PlacementModifier> modifiers
	) {
		return FrozenLibPlacementUtils.register(entries, resourceKey, entries.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredResourceKey), modifiers);
	}


	public static @NotNull Holder<PlacedFeature> register(
		BootstrapContext<PlacedFeature> entries,
		ResourceKey<PlacedFeature> resourceKey,
		Holder<ConfiguredFeature<?, ?>> configuredHolder,
		PlacementModifier... modifiers
	) {
		return register(entries, resourceKey, configuredHolder, Arrays.asList(modifiers));
	}

	private static @NotNull Holder<PlacedFeature> register(
		BootstrapContext<PlacedFeature> entries,
		ResourceKey<PlacedFeature> resourceKey,
		Holder<ConfiguredFeature<?, ?>> configuredHolder,
		List<PlacementModifier> modifiers) {
		return FrozenLibPlacementUtils.register(entries, resourceKey, configuredHolder, modifiers);
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> @NotNull Holder<ConfiguredFeature<?, ?>> register(
		BootstrapContext<ConfiguredFeature<?, ?>> entries,
		ResourceKey<ConfiguredFeature<?, ?>> resourceKey,
		F feature, FC featureConfiguration
	) {
		return FrozenLibConfiguredFeatureUtils.register(entries, resourceKey, feature, featureConfiguration);
	}
}
