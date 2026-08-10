/*
 * Copyright 2025-2026 FrozenBlock
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

import net.fabricmc.api.ClientModInitializer;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.wilderwild.client.WWFluidRendering;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WilderWildFabricClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		WilderWildClient.init();

		SplashTextAPI.addSplashLocation(WWConstants.id("texts/splashes.txt"));
		WWFluidRendering.init();
		WWModelLayers.setup();

		WWClientNetworking.registerPacketReceivers();
	}
}
