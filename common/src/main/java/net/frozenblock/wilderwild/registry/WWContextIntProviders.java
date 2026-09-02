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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;

public final class WWContextIntProviders {
	public static final ResourceKey<ContextIntProvider> COMPOSTABLE_MILKWEED_POD = createKey("compostable/milkweed_pod");
	public static final ResourceKey<ContextIntProvider> COMPOSTABLE_POLLEN = createKey("compostable/pollen");

	private static ResourceKey<ContextIntProvider> createKey(String name) {
		return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, WWConstants.id(name));
	}

	private WWContextIntProviders() {}
}
