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

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
import static net.frozenblock.wilderwild.registry.WWBlocks.STONE_CHEST;

public final class WWFabricBlocks {

	public static void init() {
		registerInventories();
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks(
			(level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(),
			STONE_CHEST.get()
		);
	}

	private WWFabricBlocks() {}
}
