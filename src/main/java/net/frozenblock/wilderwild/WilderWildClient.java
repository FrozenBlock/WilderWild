/*
 * Copyright 2025 FrozenBlock
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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild;

import java.util.Optional;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.debug.client.api.DebugRendererEvents;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.wilderwild.client.WWBlockRenderLayers;
import net.frozenblock.wilderwild.client.WWClientMusicImpl;
import net.frozenblock.wilderwild.client.WWFluidRendering;
import net.frozenblock.wilderwild.client.WWItemProperties;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.WWParticleEngine;
import net.frozenblock.wilderwild.client.WWTints;
import net.frozenblock.wilderwild.client.WWEasterEggs;
import net.frozenblock.wilderwild.client.renderer.debug.OstrichDebugRenderer;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WilderWildClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer("wilderwild");

		SplashTextAPI.addSplashLocation(WWConstants.id("texts/splashes.txt"));
		WWEasterEggs.hatchEasterEggs();

		WWBlockRenderLayers.init();
		WWFluidRendering.init();
		WWItemProperties.init();
		WWModelLayers.init();
		WWParticleEngine.init();
		WWTints.initBlocks();
		WWTints.initItems();
		WWClientMusicImpl.addMusicChanges();

		WWClientNetworking.registerPacketReceivers();

		if (WWAmbienceAndMiscConfig.get().music.wilderExtraMusic) {
			ResourceManagerHelper.registerBuiltinResourcePack(
				WWConstants.id("wilder_extra_music"), modContainer.get(),
				Component.translatable("pack.wilderwild.wilder_extra_music"),
				ResourcePackActivationType.ALWAYS_ENABLED
			);
		}

		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return WWConstants.id("minecraft_live_sculk_sensor");
			}

			@Override
			public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
				WWConstants.MC_LIVE_TENDRILS = resourceManager.listPacks().anyMatch(packResources -> {
					if (packResources.knownPackInfo().isPresent()) {
						return packResources.knownPackInfo().get().id().equals(WWConstants.string("mc_live_tendrils"));
					}
					return false;
				});
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

}
