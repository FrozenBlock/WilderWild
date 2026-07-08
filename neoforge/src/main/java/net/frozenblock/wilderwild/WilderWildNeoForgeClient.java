package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.config.gui.WWMainConfigGui;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = WWPreLoadConstants.MOD_ID, dist = Dist.CLIENT)
public final class WilderWildNeoForgeClient {

	public WilderWildNeoForgeClient(IEventBus modBus) {
		WilderWildClient.init();

		// AFTER register event
		modBus.addListener(FMLClientSetupEvent.class, event -> {
			WWModelLayers.setup();
		});

		modBus.addListener(RegisterSpecialModelRendererEvent.class, event -> {
			event.register(WWConstants.id("stone_chest"), StoneChestSpecialRenderer.Unbaked.MAP_CODEC);
		});

		ModLoadingContext.get().registerExtensionPoint(
			IConfigScreenFactory.class,
			() -> (container, parent) ->
				WWMainConfigGui.buildScreen(parent)
		);
	}
}
