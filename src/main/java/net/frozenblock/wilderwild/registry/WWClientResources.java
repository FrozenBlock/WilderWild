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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWClientResources {
	private WWClientResources() {
		throw new UnsupportedOperationException("WWResources contains only static declarations.");
	}

	public static void register(ModContainer container) {
		if (container == null) return;

		ResourceManagerHelper.registerBuiltinResourcePack(
			WWConstants.id("mc_live_tendrils"),
			container,
			Component.translatable("pack.wilderwild.minecraft_live_tendrils"),
			ResourcePackActivationType.NORMAL
		);

		ResourceManagerHelper.registerBuiltinResourcePack(
			WWConstants.id("original_firefly"),
			container, Component.literal("Original Fireflies"),
			ResourcePackActivationType.DEFAULT_ENABLED
		);

		if (WWAmbienceAndMiscConfig.get().music.wilderExtraMusic) {
			ResourceManagerHelper.registerBuiltinResourcePack(
				WWConstants.id("wilder_extra_music"), container,
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
	}
}
