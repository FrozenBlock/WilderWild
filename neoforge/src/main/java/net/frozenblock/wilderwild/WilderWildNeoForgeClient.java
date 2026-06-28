package net.frozenblock.wilderwild;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = WWPreLoadConstants.MOD_ID, dist = Dist.CLIENT)
public final class WilderWildNeoForgeClient {

	static void init(IEventBus modBus) {
		WilderWildClient.init();
	}
}
