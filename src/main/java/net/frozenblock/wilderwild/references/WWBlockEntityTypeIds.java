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

package net.frozenblock.wilderwild.references;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class WWBlockEntityTypeIds {
	public static final ResourceKey<BlockEntityType<?>> HANGING_TENDRIL = create("hanging_tendril");
	public static final ResourceKey<BlockEntityType<?>> TERMITE_MOUND = create("termite_mound");
	public static final ResourceKey<BlockEntityType<?>> DISPLAY_LANTERN = create("display_lantern");
	public static final ResourceKey<BlockEntityType<?>> STONE_CHEST = create("stone_chest");
	public static final ResourceKey<BlockEntityType<?>> SCORCHED_BLOCK = create("scorched_block");
	public static final ResourceKey<BlockEntityType<?>> GEYSER = create("geyser");
	public static final ResourceKey<BlockEntityType<?>> ICICLE = create("icicle");

	private static ResourceKey<BlockEntityType<?>> create(String name) {
		return ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, WWConstants.id(name));
	}
}
