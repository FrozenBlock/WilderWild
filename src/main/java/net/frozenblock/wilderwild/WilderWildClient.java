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

import java.util.Optional;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.wilderwild.client.WWClientMusicImpl;
import net.frozenblock.wilderwild.client.WWEasterEggs;
import net.frozenblock.wilderwild.client.WWFluidRendering;
import net.frozenblock.wilderwild.client.WWItemProperties;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.WWParticleEngine;
import net.frozenblock.wilderwild.client.WWTints;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.frozenblock.wilderwild.registry.WWClientResources;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

@Environment(EnvType.CLIENT)
public final class WilderWildClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		final Optional<ModContainer> container = FabricLoader.getInstance().getModContainer("wilderwild");
		WWClientResources.register(container.orElse(null));

		SplashTextAPI.addSplashLocation(WWConstants.id("texts/splashes.txt"));

		WWFluidRendering.init();
		WWItemProperties.init();
		WWModelLayers.init();
		WWParticleEngine.init();
		WWTints.applyTints();
		WWEasterEggs.hatchEasterEggs();
		WWClientMusicImpl.init();

		WWClientNetworking.registerPacketReceivers();

		SpecialModelRenderers.ID_MAPPER.put(WWConstants.id("stone_chest"), StoneChestSpecialRenderer.Unbaked.MAP_CODEC);
	}

}
