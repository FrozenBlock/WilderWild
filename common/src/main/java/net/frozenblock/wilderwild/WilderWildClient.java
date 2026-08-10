package net.frozenblock.wilderwild;

import net.frozenblock.lib.menu.api.SplashTextAPI;
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
		SplashTextAPI.addSplashLocation(WWConstants.id("texts/splashes.txt"));
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
}
