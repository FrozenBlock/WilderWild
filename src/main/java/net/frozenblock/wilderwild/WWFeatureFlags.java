package net.frozenblock.wilderwild;

import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;

public class WWFeatureFlags {
	public static final FeatureFlag TRAILIER_TALES_COMPAT = FrozenFeatureFlags.builder.create(WWConstants.id("trailiertales"));
	public static final FeatureFlagSet TRAILIER_TALES_COMPAT_FLAG_SET = FeatureFlagSet.of(TRAILIER_TALES_COMPAT);

	public static void init() {
	}
}
