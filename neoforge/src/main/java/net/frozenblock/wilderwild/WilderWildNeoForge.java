package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(WWPreLoadConstants.MOD_ID)
public final class WilderWildNeoForge {

	public WilderWildNeoForge(IEventBus modBus) {
		WilderWild.init();

		NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
			SpreadSculkCommand.register(event.getDispatcher());
		});

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			WWSoundTypes.init();
		});
	}
}
