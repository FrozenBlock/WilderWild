/*
 * Copyright 2023-2025 FrozenBlock
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
			container, Component.literal("Minecraft Live Tendrils"),
			ResourcePackActivationType.NORMAL
		);
	}
}
