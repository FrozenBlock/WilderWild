/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.mod_compat.client;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.client.ClientModIntegration;
import net.frozenblock.lib.integration.api.client.ClientModIntegrationSupplier;
import net.frozenblock.lib.integration.api.client.ClientModIntegrations;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class WilderClientModIntegrations {

	public static final ClientModIntegration CLIENT_FROZENLIB_INTEGRATION = registerAndGet(ClientFrozenLibIntegration::new, "frozenlib");

	private WilderClientModIntegrations() {
		throw new UnsupportedOperationException("WilderModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ClientModIntegrationSupplier<? extends ClientModIntegration> register(Supplier<? extends ClientModIntegration> integration, String modID) {
		return ClientModIntegrations.register(integration, WilderSharedConstants.MOD_ID, modID);
	}

	public static <T extends ClientModIntegration> ClientModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ClientModIntegrations.register(integration, unloadedIntegration, WilderSharedConstants.MOD_ID, modID);
	}

	public static <T extends ClientModIntegration> ClientModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ClientModIntegrations.register(integration, WilderSharedConstants.MOD_ID, modID).getIntegration();
	}
}
