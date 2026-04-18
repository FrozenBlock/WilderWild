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
import net.minecraft.world.entity.EntityType;

public final class WWEntityTypeIds {
	public static final ResourceKey<EntityType<?>> FIREFLY = create("firefly");
	public static final ResourceKey<EntityType<?>> BUTTERFLY = create("butterfly");
	public static final ResourceKey<EntityType<?>> JELLYFISH = create("jellyfish");
	public static final ResourceKey<EntityType<?>> TUMBLEWEED = create("tumbleweed");
	public static final ResourceKey<EntityType<?>> CRAB = create("crab");
	public static final ResourceKey<EntityType<?>> OSTRICH = create("ostrich");
	public static final ResourceKey<EntityType<?>> ZOMBIE_OSTRICH = create("zombie_ostrich");
	public static final ResourceKey<EntityType<?>> SCORCHED = create("scorched");
	public static final ResourceKey<EntityType<?>> MOOBLOOM = create("moobloom");
	public static final ResourceKey<EntityType<?>> PENGUIN = create("penguin");
	public static final ResourceKey<EntityType<?>> COCONUT = create("coconut");
	public static final ResourceKey<EntityType<?>> FALLING_LEAVES = create("falling_leaves");

	public static final ResourceKey<EntityType<?>> BAOBAB_BOAT = create("baobab_boat");
	public static final ResourceKey<EntityType<?>> BAOBAB_CHEST_BOAT = create("baobab_chest_boat");
	public static final ResourceKey<EntityType<?>> WILLOW_BOAT = create("willow_boat");
	public static final ResourceKey<EntityType<?>> WILLOW_CHEST_BOAT = create("willow_chest_boat");
	public static final ResourceKey<EntityType<?>> CYPRESS_BOAT = create("cypress_boat");
	public static final ResourceKey<EntityType<?>> CYPRESS_CHEST_BOAT = create("cypress_chest_boat");
	public static final ResourceKey<EntityType<?>> PALM_BOAT = create("palm_boat");
	public static final ResourceKey<EntityType<?>> PALM_CHEST_BOAT = create("palm_chest_boat");
	public static final ResourceKey<EntityType<?>> MAPLE_BOAT = create("maple_boat");
	public static final ResourceKey<EntityType<?>> MAPLE_CHEST_BOAT = create("maple_chest_boat");

	private static ResourceKey<EntityType<?>> create(String name) {
		return ResourceKey.create(Registries.ENTITY_TYPE, WWConstants.id(name));
	}
}
