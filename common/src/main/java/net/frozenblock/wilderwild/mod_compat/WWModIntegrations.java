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

package net.frozenblock.wilderwild.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;

public final class WWModIntegrations {
	private static final FrozenDeferredRegister<ModIntegrationSupplier<?>> REGISTER = FrozenDeferredRegister.create(
		FrozenLibRegistries.MOD_INTEGRATION_REGISTRY,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<ModIntegrationSupplier<?>, ModIntegrationSupplier<BiolithIntegration>> BIOLITH_INTEGRATION = REGISTER.register(
		"biolith",
		() -> new ModIntegrationSupplier(
			() -> new BiolithIntegration(),
			"biolith"
		)
	);

	public static void init() {}

	static {
		REGISTER.register();
	}

	public static boolean isBiolithRegisteredAndLoaded() {
		return BIOLITH_INTEGRATION.isBound() && BIOLITH_INTEGRATION.get().modLoaded();
	}
}
