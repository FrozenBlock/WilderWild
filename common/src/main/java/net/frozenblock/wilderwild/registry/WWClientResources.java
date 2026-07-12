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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.platform.api.resource.FrozenLibResourceLoader;
import net.frozenblock.lib.platform.api.resource.PackActivationType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

@ClientOnly
public final class WWClientResources {

	public static void init() {
		FrozenLibResourceLoader.registerBuiltinPack(
			WWConstants.id("mc_live_tendrils"),
			WWConstants.MOD_ID,
			Component.translatable("pack.wilderwild.minecraft_live_tendrils"),
			PackActivationType.NORMAL
		);

		FrozenLibResourceLoader.registerBuiltinPack(
			WWConstants.id("original_firefly"),
			WWConstants.MOD_ID,
			Component.literal("Original Fireflies"),
			PackActivationType.DEFAULT_ENABLED
		);

		FrozenLibResourceLoader.registerBuiltinPack(
			WWConstants.id("mojang_crabs"),
			WWConstants.MOD_ID,
			Component.translatable("pack.wilderwild.mojang_crabs"),
			PackActivationType.DEFAULT_ENABLED
		);

		if (WWAmbienceAndMiscConfig.WILDER_EXTRA_MUSIC.get()) {
			FrozenLibResourceLoader.registerBuiltinPack(
				WWConstants.id("wilder_extra_music"),
				WWConstants.MOD_ID,
				Component.translatable("pack.wilderwild.wilder_extra_music"),
				PackActivationType.ALWAYS_ENABLED
			);
		}

		FrozenLibResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
			WWConstants.id("resource_pack_value_setters"),
			(ResourceManagerReloadListener) resourceManager -> {
				WWConstants.MC_LIVE_TENDRILS = resourceManager.listPacks().anyMatch(packResources -> {
					if (packResources.knownPackInfo().isPresent()) return packResources.knownPackInfo().get().id().equals(WWConstants.string("mc_live_tendrils"));
					return false;
				});
				WWConstants.MOJANG_CRABS = resourceManager.listPacks().anyMatch(packResources -> {
					if (packResources.knownPackInfo().isPresent()) return packResources.knownPackInfo().get().id().equals(WWConstants.string("mojang_crabs"));
					return false;
				});
			}
		);
	}
}
