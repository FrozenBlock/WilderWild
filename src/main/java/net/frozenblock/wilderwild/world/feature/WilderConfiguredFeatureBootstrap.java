package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import java.util.ArrayList;

public final class WilderConfiguredFeatureBootstrap {
	private WilderConfiguredFeatureBootstrap() {
		throw new UnsupportedOperationException("WilderConfiguredFeatureBootstrap contains only static declarations.");
	}

	public static final ArrayList<FrozenConfiguredFeature> FROZEN_CONFIGURED_FEATURES = new ArrayList<>();

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> bootstrapContext) {
		WilderTreeConfigured.registerTreeConfigured();
		for (FrozenConfiguredFeature feature : FROZEN_CONFIGURED_FEATURES) {
			bootstrapContext.register(feature.getResourceKey(), feature.getConfiguredFeature());
		}
		WilderMiscConfigured.init();
	}
}
