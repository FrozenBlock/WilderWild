package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.config.gui.WWNeoMainConfigGui;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = WWPreLoadConstants.MOD_ID, dist = Dist.CLIENT)
public final class WilderWildNeoForgeClient {

	public WilderWildNeoForgeClient(IEventBus modBus) {
		WilderWildClient.init();

		// AFTER register event
		modBus.addListener(FMLClientSetupEvent.class, event -> {
			WWModelLayers.setupInit();
		});

		ModLoadingContext.get().registerExtensionPoint(
			IConfigScreenFactory.class,
			() -> (container, parent) ->
				WWNeoMainConfigGui.buildScreen(parent)
		);
	}
}
