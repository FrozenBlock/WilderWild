/*
 * Copyright 2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild;

import net.frozenblock.lib.networking.api.platform.NetworkingHelperImpl;
import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.mod_compat.WWTerraBlenderCompat;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.frozenblock.wilderwild.networking.WWNetworking;
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
			WWNetworking.setup();

			if (ModLoader.isClient()) WWClientNetworking.setup();

			final PayloadRegistrar registrar = event.registrar(WWPreLoadConstants.MOD_ID);
			NetworkingHelperImpl.flush(registrar);
		});

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			WilderWild.setup();

			if (ModLoader.isModLoaded("terrablender")) WWTerraBlenderCompat.setup();
		});
	}
}
