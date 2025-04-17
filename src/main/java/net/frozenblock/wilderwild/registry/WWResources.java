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

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.chat.Component;

public final class WWResources {
	private WWResources() {
		throw new UnsupportedOperationException("WWResources contains only static declarations.");
	}

	public static void register(ModContainer container) {
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
	}
}
