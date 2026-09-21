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

import net.frozenblock.lib.menu.api.SplashTextEvents;
import net.frozenblock.lib.renderer.special.SpecialModelRendererRegistry;
import net.frozenblock.wilderwild.client.WWBlockColors;
import net.frozenblock.wilderwild.client.WWBuiltInBlockModels;
import net.frozenblock.wilderwild.client.WWClientMusicImpl;
import net.frozenblock.wilderwild.client.WWEasterEggs;
import net.frozenblock.wilderwild.client.WWItemProperties;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.WWParticleEngine;
import net.frozenblock.wilderwild.client.WWRenderStateDataKeys;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.registry.WWClientResources;
import net.frozenblock.wilderwild.wind.client.AmbientWindParticleSpawner;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WilderWildClient {

	public static void init() {
		SplashTextEvents.ADD_SOURCE_FILES.register(sourceFiles -> sourceFiles.add(WWConstants.id("texts/splashes.txt")));
		WWEasterEggs.hatchEasterEggs();

		WWClientResources.init();
		WWModelLayers.init();
		WWItemProperties.init();
		WWClientMusicImpl.init();
		WWRenderStateDataKeys.init();
		WWParticleEngine.init();
		AmbientWindParticleSpawner.init();
		WWBuiltInBlockModels.init();
		WWBlockColors.init();

		SpecialModelRendererRegistry.register(WWConstants.id("stone_chest"), StoneChestSpecialRenderer.Unbaked.MAP_CODEC);
	}

	public static void setup() {}

	private WilderWildClient() {}
}
