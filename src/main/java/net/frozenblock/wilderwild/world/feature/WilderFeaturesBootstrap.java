package net.frozenblock.wilderwild.world.feature;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.FrozenPlacedFeature;
import net.frozenblock.wilderwild.WilderWild;
import java.util.ArrayList;

public final class WilderFeaturesBootstrap {
	private WilderFeaturesBootstrap() {
		throw new UnsupportedOperationException("WilderFeaturesBootstrap contains only static declarations.");
	}

	public static final ArrayList<FrozenConfiguredFeature> FROZEN_CONFIGURED_FEATURES = new ArrayList<>();
	private static final ArrayList<FrozenConfiguredFeature> REGISTERED_CONFIGURED_FEATURES = new ArrayList<>();

	public static final ArrayList<FrozenPlacedFeature> FROZEN_PLACED_FEATURES = new ArrayList<>();
	private static final ArrayList<FrozenPlacedFeature> REGISTERED_PLACED_FEATURES = new ArrayList<>();

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderTreeConfigured.registerTreeConfigured();
		addEntries(entries);
		WilderMiscConfigured.Pre.init();
		addEntries(entries);
		WilderMiscConfigured.init();
		addEntries(entries);
		WilderTreePlaced.registerTreePlaced();
		addEntries(entries);
		WilderMiscPlaced.registerMiscPlaced();
		addEntries(entries);
		WilderConfiguredFeatures.registerConfiguredFeatures();
		addEntries(entries);
		WilderPlacedFeatures.init();
		addEntries(entries);
		//FROZEN_CONFIGURED_FEATURES.clear();
		//REGISTERED_CONFIGURED_FEATURES.clear();
		//FROZEN_PLACED_FEATURES.clear();
		//REGISTERED_PLACED_FEATURES.clear();
	}

	private static void addEntries(FabricWorldgenProvider.Entries entries) {
		for (FrozenConfiguredFeature<?, ?> feature : FROZEN_CONFIGURED_FEATURES) {
			if (!REGISTERED_CONFIGURED_FEATURES.contains(feature)) {
				REGISTERED_CONFIGURED_FEATURES.add(feature);
				WilderWild.log(feature.getResourceKey().toString(), true);
				entries.add(feature.getResourceKey(), feature.getConfiguredFeature());
			}
		}
		for (FrozenPlacedFeature feature : FROZEN_PLACED_FEATURES) {
			if (!REGISTERED_PLACED_FEATURES.contains(feature)) {
				REGISTERED_PLACED_FEATURES.add(feature);
				entries.add(feature.getResourceKey(), feature.getPlacedFeature());
			}
		}
	}
}
