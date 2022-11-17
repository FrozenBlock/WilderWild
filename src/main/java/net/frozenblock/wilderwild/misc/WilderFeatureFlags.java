package net.frozenblock.wilderwild.misc;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;

public class WilderFeatureFlags {

	public static final FeatureFlag EXPERIMENTAL;

	static {
		FeatureFlagRegistry.Builder wilderFlag = new FeatureFlagRegistry.Builder(WilderSharedConstants.MOD_ID);
		EXPERIMENTAL = wilderFlag.create(WilderSharedConstants.id("experimental"));
		wilderFlag.build();
	}
}
