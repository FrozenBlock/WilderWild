package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.minecraft.world.flag.FeatureFlag;

public class WilderFeatureFlags {

	public static final FeatureFlag UPDATE_1_20_ADDITIONS = FrozenFeatureFlags.builder.create(WilderSharedConstants.id("update_1_20_additions"));

	public static void init() {
	}
}
