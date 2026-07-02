package net.frozenblock.wilderwild;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.WWEasterEggs;
import net.frozenblock.wilderwild.registry.WWClientResources;

@Environment(EnvType.CLIENT)
public final class WilderWildClient {
	private WilderWildClient() {}

	public static void init() {
		WWEasterEggs.hatchEasterEggs();
	}
}
