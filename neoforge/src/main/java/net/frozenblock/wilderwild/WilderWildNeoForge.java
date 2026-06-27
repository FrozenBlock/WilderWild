package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(WWPreLoadConstants.MOD_ID)
public final class WilderWildNeoForge {

	public WilderWildNeoForge(IEventBus modBus) {
		WilderWild.init();

		NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
			SpreadSculkCommand.register(event.getDispatcher());
		});

		if (FMLEnvironment.getDist().isClient()) {
			WilderWildNeoForgeClient.init(modBus);
		}
	}
}
