/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.debug.client.api.DebugRendererEvents;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.lib.menu.api.Panoramas;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.lib.sound.api.FlyBySoundHub;
import net.frozenblock.wilderwild.client.rendering.WWBlockRenderLayers;
import net.frozenblock.wilderwild.client.rendering.WWFluidRendering;
import net.frozenblock.wilderwild.client.rendering.WWItemProperties;
import net.frozenblock.wilderwild.client.rendering.WWModelLayers;
import net.frozenblock.wilderwild.client.rendering.WWParticleEngine;
import net.frozenblock.wilderwild.client.rendering.WWTints;
import net.frozenblock.wilderwild.client.rendering.debug.OstrichDebugRenderer;
import net.frozenblock.wilderwild.entity.render.easter.WilderEasterEggs;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.frozenblock.wilderwild.registry.WWEntities;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WilderWildClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SplashTextAPI.addSplashLocation(WWConstants.id("texts/splashes.txt"));
		addPanorama("birch_valley");
		WilderEasterEggs.hatchEasterEggs();

		WWBlockRenderLayers.init();
		WWFluidRendering.init();
		WWItemProperties.init();
		WWModelLayers.init();
		WWParticleEngine.init();
		WWTints.initBlocks();
		WWTints.initItems();

		WWClientNetworking.registerPacketReceivers();

		FlyBySoundHub.AUTO_ENTITIES_AND_SOUNDS.put(WWEntities.ANCIENT_HORN_VIBRATION, new FlyBySoundHub.FlyBySound(1F, 0.5F, SoundSource.PLAYERS, WWSounds.ENTITY_ANCIENT_HORN_VIBRATION_FLYBY));

		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return WWConstants.id("minecraft_live_sculk_sensor");
			}

			@Override
			public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
				WWConstants.MC_LIVE_TENDRILS = resourceManager.getResource(WWConstants.id("textures/entity/sculk_sensor/new_tendril_enabler.png")).isPresent();
			}
		});

		DebugRendererEvents.DEBUG_RENDERERS_CREATED.register(client -> {
			OstrichDebugRenderer ostrichDebugRenderer = new OstrichDebugRenderer(client);

			ClientTickEvents.START_WORLD_TICK.register(clientLevel -> {
				if (FrozenLibConfig.IS_DEBUG) {
					ostrichDebugRenderer.tick();
				}
			});

			DebugRenderManager.addClearRunnable(ostrichDebugRenderer::clear);

			DebugRenderManager.registerRenderer(WWConstants.id("ostrich"), ostrichDebugRenderer::render);
		});
	}

	private static void addPanorama(String panoramaName) {
		ResourceLocation panoramaLocation = WWConstants.id("textures/gui/title/" + panoramaName + "/panorama");
		Panoramas.addPanorama(panoramaLocation);
	}

}
