package net.frozenblock.wilderwild.world.generation;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacedFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscConfigured;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreePlaced;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class WilderFeatureBootstrap {

	public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		FrozenConfiguredFeature.BOOTSTAP_CONTEXT = entries;

		WilderTreeConfigured.registerTreeConfigured();
		WilderMiscConfigured.registerMiscPlaced();
		WilderConfiguredFeatures.registerConfiguredFeatures();
	}

	public static void bootstrapPlaced(BootstapContext<PlacedFeature> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		FrozenPlacedFeature.BOOTSTAP_CONTEXT = entries;

		WilderTreePlaced.registerTreePlaced();
		WilderMiscPlaced.registerMiscPlaced();
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

		entries.addAll(configuredFeatures);
		entries.addAll(placedFeatures);
		entries.addAll(biomes);
		entries.addAll(noises);
		entries.addAll(processorLists);
		entries.addAll(templatePools);
		entries.addAll(structures);
		entries.addAll(structureSets);
	}

	/**
	 * @param configuredResourceKey	MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, PlacementModifier... modifiers) {
		return register(entries, resourceKey, configuredResourceKey, Arrays.asList(modifiers));
	}

	/**
	 * @param configuredResourceKey	MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, List<PlacementModifier> modifiers) {
		return FrozenPlacementUtils.register(entries, resourceKey, entries.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredResourceKey), modifiers);
	}


	public static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, PlacementModifier... modifiers) {
		return register(entries, resourceKey, configuredHolder, Arrays.asList(modifiers));
	}

	private static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, List<PlacementModifier> modifiers) {
		return FrozenPlacementUtils.register(entries, resourceKey, configuredHolder, modifiers);
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(BootstapContext<ConfiguredFeature<?, ?>> entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
		return FrozenConfiguredFeatureUtils.register(entries, resourceKey, feature, featureConfiguration);
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}
}
