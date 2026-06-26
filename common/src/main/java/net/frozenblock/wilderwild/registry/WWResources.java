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

import net.frozenblock.lib.platform.api.resource.FrozenResourceLoader;
import net.frozenblock.lib.platform.api.resource.PackActivationType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.minecraft.network.chat.Component;

public final class WWResources {

	public static void register() {
		var modId = WWConstants.MOD_ID;

		if (WWWorldgenConfig.NEW_ABANDONED_CAMP_GENERATION.get()) {
			FrozenResourceLoader.registerBuiltinPack(
				WWConstants.id("wilder_abandoned_camps"),
				modId,
				Component.translatable("pack.wilderwild.wilder_abandoned_camps"),
				PackActivationType.ALWAYS_ENABLED
			);
		}
	}
}
