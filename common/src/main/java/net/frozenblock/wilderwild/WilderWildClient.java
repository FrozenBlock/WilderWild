package net.frozenblock.wilderwild;

import net.frozenblock.lib.platform.api.ClientOnly;
import net.frozenblock.wilderwild.client.WWEasterEggs;

@ClientOnly
public final class WilderWildClient {
	private WilderWildClient() {}

	public static void init() {
		WWEasterEggs.hatchEasterEggs();
	}
}
