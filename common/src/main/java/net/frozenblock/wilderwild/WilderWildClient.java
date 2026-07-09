package net.frozenblock.wilderwild;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.WWBuiltInBlockModels;
import net.frozenblock.wilderwild.client.WWClientMusicImpl;
import net.frozenblock.wilderwild.client.WWEasterEggs;
import net.frozenblock.wilderwild.client.WWItemProperties;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.WWParticleEngine;
import net.frozenblock.wilderwild.client.WWRenderStateDataKeys;
import net.frozenblock.wilderwild.client.WWTints;
import net.frozenblock.wilderwild.registry.WWClientResources;
import net.frozenblock.wilderwild.wind.client.AmbientWindParticleSpawner;

@Environment(EnvType.CLIENT)
public final class WilderWildClient {

	public static void init() {
		WWEasterEggs.hatchEasterEggs();

		WWClientResources.register();
		WWModelLayers.init();
		WWItemProperties.init();
		WWClientMusicImpl.init();
		WWRenderStateDataKeys.init();
		WWParticleEngine.init();
		AmbientWindParticleSpawner.init();

		WWBuiltInBlockModels.setup();
		WWTints.init();
	}
}
