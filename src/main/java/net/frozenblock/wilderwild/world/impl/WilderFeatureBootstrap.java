/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.impl;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.world.feature.configured.WilderCaveConfigured;
import net.frozenblock.wilderwild.world.feature.configured.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.configured.WilderMiscConfigured;
import net.frozenblock.wilderwild.world.feature.configured.WilderTreeConfigured;
import net.frozenblock.wilderwild.world.feature.placed.WilderCavePlaced;
import net.frozenblock.wilderwild.world.feature.placed.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.placed.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.feature.placed.WilderTreePlaced;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class WilderFeatureBootstrap {

	public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		FrozenFeatureUtils.BOOTSTRAP_CONTEXT = (BootstrapContext) entries;

		WilderTreeConfigured.registerTreeConfigured();
		WilderMiscConfigured.registerMiscConfigured();
		WilderCaveConfigured.registerCaveConfigured(entries);
		WilderConfiguredFeatures.registerConfiguredFeatures(entries);
	}

	public static void bootstrapPlaced(BootstrapContext<PlacedFeature> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		FrozenFeatureUtils.BOOTSTRAP_CONTEXT = (BootstrapContext) entries;

		WilderTreePlaced.registerTreePlaced();
		WilderMiscPlaced.registerMiscPlaced(entries);
		WilderCavePlaced.registerCavePlaced(entries);
		WilderPlacedFeatures.registerPlacedFeatures(entries);
	}

	public static void bootstrap(FabricDynamicRegistryProvider.Entries entries) {
		final var configuredFeatures = asLookup(entries.getLookup(Registries.CONFIGURED_FEATURE));
		final var placedFeatures = asLookup(entries.placedFeatures());
		final var biomes = asLookup(entries.getLookup(Registries.BIOME));
		final var noises = asLookup(entries.getLookup(Registries.NOISE));
		final var processorLists = asLookup(entries.getLookup(Registries.PROCESSOR_LIST));
		final var templatePools = asLookup(entries.getLookup(Registries.TEMPLATE_POOL));
		final var structures = asLookup(entries.getLookup(Registries.STRUCTURE));
		final var structureSets = asLookup(entries.getLookup(Registries.STRUCTURE_SET));

		WilderConstants.log("Adding finalized configured features to datagen", true);
		entries.addAll(configuredFeatures);
		WilderConstants.log("Adding finalized placed features to datagen", true);
		entries.addAll(placedFeatures);
		WilderConstants.log("Adding finalized biomes to datagen", true);
		entries.addAll(biomes);
		WilderConstants.log("Adding finalized noises to datagen", true);
		entries.addAll(noises);
		WilderConstants.log("Adding finalized processor lists to datagen", true);
		entries.addAll(processorLists);
		WilderConstants.log("Adding finalized template pools to datagen", true);
		entries.addAll(templatePools);
		WilderConstants.log("Adding finalized structures to datagen", true);
		entries.addAll(structures);
		WilderConstants.log("Adding finalized structure sets to datagen", true);
		entries.addAll(structureSets);
	}

	/**
	 * @param configuredResourceKey MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static Holder<PlacedFeature> register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, PlacementModifier... modifiers) {
		return register(entries, resourceKey, configuredResourceKey, Arrays.asList(modifiers));
	}

	/**
	 * @param configuredResourceKey MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static Holder<PlacedFeature> register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, List<PlacementModifier> modifiers) {
		return FrozenPlacementUtils.register(entries, resourceKey, entries.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredResourceKey), modifiers);
	}


	public static Holder<PlacedFeature> register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, PlacementModifier... modifiers) {
		return register(entries, resourceKey, configuredHolder, Arrays.asList(modifiers));
	}

	private static Holder<PlacedFeature> register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, List<PlacementModifier> modifiers) {
		return FrozenPlacementUtils.register(entries, resourceKey, configuredHolder, modifiers);
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(BootstrapContext<ConfiguredFeature<?, ?>> entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
		return FrozenConfiguredFeatureUtils.register(entries, resourceKey, feature, featureConfiguration);
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}
}
