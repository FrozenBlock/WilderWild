package net.frozenblock.wilderwild;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(WWPreLoadConstants.MOD_ID)
public final class WilderWildNeoForge {

	public WilderWildNeoForge(IEventBus modBus) {
		WilderWildMain.init();

		NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event ->
			WilderWildNeoForge.registerCommands(event)
		);

		if (FMLEnvironment.getDist().isClient()) {
			WilderWildNeoForgeClient.init(modBus);
		}
	}

	private static void registerCommands(RegisterCommandsEvent event) {
		// SpreadSculkCommand.register(event.getDispatcher());
	}
}
