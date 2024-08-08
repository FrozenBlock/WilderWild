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

package net.frozenblock.wilderwild.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.mod_compat.simple_copper_pipes.AbstractSimpleCopperPipesIntegration;
import net.frozenblock.wilderwild.mod_compat.simple_copper_pipes.NoOpSimpleCopperPipesIntegration;
import net.frozenblock.wilderwild.mod_compat.simple_copper_pipes.SimpleCopperPipesIntegration;

public final class WilderModIntegrations {

	public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
	public static final ModIntegration BETTEREND_INTEGRATION = registerAndGet(BetterEndIntegration::new, "betterend");
	public static final ModIntegration BETTERNETHER_INTEGRATION = registerAndGet(BetterNetherIntegration::new, "betternether");
	public static final ModIntegration BLOCKUS_INTEGRATION = registerAndGet(BlockusIntegration::new, "blockus");
	public static final ModIntegration EDENRING_INTEGRATION = registerAndGet(EdenRingIntegration::new, "edenring");
	public static final ModIntegration TECHREBORN_INTEGRATION = registerAndGet(TechRebornIntegration::new, "techreborn");
	public static final ModIntegration TERRALITH_INTEGRATION = registerAndGet(TerralithModIntegration::new, "terralith");
	public static final ModIntegrationSupplier<AbstractSimpleCopperPipesIntegration> SIMPLE_COPPER_PIPES_INTEGRATION = register(SimpleCopperPipesIntegration::new, NoOpSimpleCopperPipesIntegration::new, "copper_pipe");
	private WilderModIntegrations() {
		throw new UnsupportedOperationException("WilderModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WilderConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, WilderConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ModIntegrations.register(integration, WilderConstants.MOD_ID, modID).getIntegration();
	}
}
