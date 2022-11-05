package net.frozenblock.wilderwild.world.feature;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import java.util.ArrayList;

public final class WilderConfiguredFeatureBootstrap {
	private WilderConfiguredFeatureBootstrap() {
		throw new UnsupportedOperationException("WilderConfiguredFeatureBootstrap contains only static declarations.");
	}

	public static final ArrayList<FrozenConfiguredFeature> FROZEN_CONFIGURED_FEATURES = new ArrayList<>();

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderTreeConfigured.registerTreeConfigured();
		addEntries(entries);
		WilderMiscConfigured.init();
		addEntries(entries);
		WilderTreePlaced.registerTreePlaced();
		addEntries(entries);
		WilderConfiguredFeatures.registerConfiguredFeatures();
	}

	private static void addEntries(FabricWorldgenProvider.Entries entries) {
		for (FrozenConfiguredFeature feature : FROZEN_CONFIGURED_FEATURES) {
			entries.add(feature.getResourceKey(), feature.getConfiguredFeature());
		}
	}
}
