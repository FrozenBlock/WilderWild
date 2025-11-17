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

package net.frozenblock.wilderwild.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.WWConstants;

public final class WWModIntegrations {
	public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
	public static final ModIntegration SIMPLE_COPPER_PIPES_INTEGRATION = registerAndGet(SimpleCopperPipesIntegration::new, "simple_copper_pipes");
	public static final ModIntegration BIOLITH_INTEGRATION = registerAndGet(BiolithIntegration::new, "biolith");

	private WWModIntegrations() {
		throw new UnsupportedOperationException("WWModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WWConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, WWConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ModIntegrations.register(integration, WWConstants.MOD_ID, modID).getIntegration();
	}
}
