package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;

public final class WilderWild {

	public static void init() {
		WilderWildRegistries.init();
		WWFeatureFlags.init();

		WWActivities.init();
	}
}
