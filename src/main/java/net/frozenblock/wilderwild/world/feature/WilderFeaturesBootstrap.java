package net.frozenblock.wilderwild.world.feature;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.WilderWild;
import java.util.ArrayList;

public final class WilderFeaturesBootstrap {
	private WilderFeaturesBootstrap() {
		throw new UnsupportedOperationException("WilderFeaturesBootstrap contains only static declarations.");
	}
	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderTreeConfigured.bootstrap(entries);
		WilderMiscConfigured.bootstrap(entries);
		WilderTreePlaced.bootstrap(entries);
		WilderMiscPlaced.bootstrap(entries);
		WilderConfiguredFeatures.bootstrap(entries);
		WilderPlacedFeatures.bootstrap(entries);
	}
}
