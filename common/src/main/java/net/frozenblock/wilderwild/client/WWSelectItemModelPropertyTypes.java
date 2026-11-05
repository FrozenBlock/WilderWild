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

package net.frozenblock.wilderwild.client;

import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.client.ClientRegistryHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.renderer.item.properties.FireflyBottleColorProperty;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.core.registries.ClientRegistries;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;

@ClientOnly
public final class WWSelectItemModelPropertyTypes {

	static {
		final DeferredRegister<SelectItemModelProperty.Type<?, ?>> register = ClientRegistryHelper.createDeferredRegister(
			ClientRegistries.SELECT_ITEM_MODEL_PROPERTY_TYPE,
			WWConstants.MOD_ID
		);

		// TODO: rename to firefly variant once dev branch is merged
		register.register("firefly_bottle_color", () -> FireflyBottleColorProperty.TYPE);

		register.register();
	}

	public static void init() {}

	private WWSelectItemModelPropertyTypes() {}
}
