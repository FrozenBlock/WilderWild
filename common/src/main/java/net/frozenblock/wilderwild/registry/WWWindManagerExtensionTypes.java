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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.lib.wind.extension.WindManagerExtensionType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.wind.WWWindManagerExtension;

public final class WWWindManagerExtensionTypes {
	private static final DeferredRegister<WindManagerExtensionType<?>> REGISTER = DeferredRegister.create(
		FrozenLibRegistries.WIND_MANAGER_EXTENSION_TYPE,
		WWConstants.MOD_ID
	);

	public static final DeferredHolder<WindManagerExtensionType<?>, WindManagerExtensionType<WWWindManagerExtension>> WILDER_WILD_EXTENSION = REGISTER.register(
		"wilderwild_extension",
		() -> WindManagerExtensionType.create(
			1000,
			WWWindManagerExtension::new,
			WWWindManagerExtension.CODEC,
			WWWindManagerExtension.STREAM_CODEC
		)
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private WWWindManagerExtensionTypes() {}
}
