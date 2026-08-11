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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public final class WWNumberProviders {
	public static final ResourceKey<NumberProvider> COMPOSTABLE_MILKWEED_POD = createKey("compostable/milkweed_pod");

	public static void bootstrap(BootstrapContext<NumberProvider> context) {
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		context.register(COMPOSTABLE_MILKWEED_POD, NumberProviders.compostable(blocks, 25));
	}

	private static ResourceKey<NumberProvider> createKey(String name) {
		return ResourceKey.create(Registries.NUMBER_PROVIDER, WWConstants.id(name));
	}
}
