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

package net.frozenblock.wilderwild.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.WWConstants;
import org.jetbrains.annotations.NotNull;

public final class WWModIntegrations {
	public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
	public static final ModIntegration BETTEREND_INTEGRATION = registerAndGet(BetterEndIntegration::new, "betterend");
	public static final ModIntegration BETTERNETHER_INTEGRATION = registerAndGet(BetterNetherIntegration::new, "betternether");
	public static final ModIntegration NATURES_SPIRIT_INTEGRATION = registerAndGet(NaturesSpiritIntegration::new, "natures_spirit");
	public static final ModIntegration BIOMES_O_PLENTY_INTEGRATION = registerAndGet(BiomesOPlentyIntegration::new, "biomesoplenty");
	public static final ModIntegration TERRESTRIA_INTEGRATION = registerAndGet(TerrestriaIntegration::new, "terrestria");
	public static final ModIntegration REGIONS_UNEXPLORED_INTEGRATION = registerAndGet(RegionsUnexploredIntegration::new, "regions_unexplored");
	public static final ModIntegration SIMPLE_COPPER_PIPES_INTEGRATION = registerAndGet(SimpleCopperPipesIntegration::new, "simple_copper_pipes");

	private WWModIntegrations() {
		throw new UnsupportedOperationException("WWModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static @NotNull ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WWConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> @NotNull ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, WWConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ModIntegrations.register(integration, WWConstants.MOD_ID, modID).getIntegration();
	}
}
