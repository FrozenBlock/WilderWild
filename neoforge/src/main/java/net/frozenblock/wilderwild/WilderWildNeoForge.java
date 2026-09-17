package net.frozenblock.wilderwild;

import net.frozenblock.lib.FrozenLibEarlyConstants;
import net.frozenblock.lib.networking.api.platform.NetworkingHelperImpl;
import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.levelgen.modification.WWWorldgen;
import net.frozenblock.wilderwild.mod_compat.WWTerraBlenderCompat;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.tooltip.TooltipAppender;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.RegisterTooltipAppendersEvent;
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
			WWNetworking.setup();

			if (ModLoader.isClient()) WWClientNetworking.setup();

			final PayloadRegistrar registrar = event.registrar(WWPreLoadConstants.MOD_ID);
			NetworkingHelperImpl.flush(registrar);
		});

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			WWSoundTypes.setup();
			WWItems.setup();
			WWBlocks.setupBlockProperties();
			WWCreativeInventorySorting.setup();
			WWWorldgen.setup();

			if (FrozenLibEarlyConstants.HAS_TERRABLENDER) WWTerraBlenderCompat.setup();
		});

		// TODO: Multiloader impl
		modBus.addListener(RegisterTooltipAppendersEvent.class, event -> {
			event.registerComponentAppenderBeforeAll(
				() -> WWDataComponents.FIREFLY_COLOR.get(),
				TooltipAppender.createComponentAppender(
					WWDataComponents.FIREFLY_COLOR.get(),
					variant -> variant.value().get(variant.value())
				)
			);
			event.registerComponentAppenderBeforeAll(
				() -> WWDataComponents.BUTTERFLY_VARIANT.get(),
				TooltipAppender.createComponentAppender(
					WWDataComponents.BUTTERFLY_VARIANT.get(),
					variant -> variant.value().get(variant.value())
				)
			);
		});
	}
}
