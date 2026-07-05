package net.frozenblock.wilderwild;

import net.frozenblock.lib.platform.FrozenLibEarlyPlatformUtils;
import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.lib.platform.networking.NeoNetworkingHelper;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.levelgen.modification.WWWorldgen;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(WWPreLoadConstants.MOD_ID)
public final class WilderWildNeoForge {

	public WilderWildNeoForge(IEventBus modBus) {
		WilderWild.init();

		NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
			SpreadSculkCommand.register(event.getDispatcher());
		});

		modBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
			WWNetworking.init();
			if (FrozenLibEarlyPlatformUtils.LOADER.isClient()) {
				WWClientNetworking.registerPacketReceivers();
			}

			final NeoNetworkingHelper neoNetworking = (NeoNetworkingHelper) FrozenLibInitPlatformUtils.NETWORKING;
			final PayloadRegistrar registrar = event.registrar(WWPreLoadConstants.MOD_ID);
			neoNetworking.flush(registrar);
		});

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			WWSoundTypes.init();
			WWItems.init();
			WWItems.setup();
			WWBlocks.registerBlockProperties();
			WWCreativeInventorySorting.init();
			WWParticleTypes.linkLeafParticles();
			WWWorldgen.setup();
		});
	}
}
