package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;

public class WilderFeatureFlags implements FrozenFeatureFlags.FeatureFlagInitEntrypoint {

	public static FeatureFlag EXPERIMENTAL;

	@Override
	public void init(FeatureFlagRegistry.Builder builder) {
		EXPERIMENTAL = builder.create(WilderSharedConstants.id("experimental"));
	}
}
