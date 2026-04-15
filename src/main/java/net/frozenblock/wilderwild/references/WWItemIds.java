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
import net.minecraft.references.ItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class WWItemIds {
	// BOATS
	public static final ResourceKey<Item> BAOBAB_BOAT = create("baobab_boat");
	public static final ResourceKey<Item> BAOBAB_CHEST_BOAT = create("baobab_chest_boat");
	public static final ResourceKey<Item> WILLOW_BOAT = create("willow_boat");
	public static final ResourceKey<Item> WILLOW_CHEST_BOAT = create("willow_chest_boat");
	public static final ResourceKey<Item> CYPRESS_BOAT = create("cypress_boat");
	public static final ResourceKey<Item> CYPRESS_CHEST_BOAT = create("cypress_chest_boat");
	public static final ResourceKey<Item> PALM_BOAT = create("palm_boat");
	public static final ResourceKey<Item> PALM_CHEST_BOAT = create("palm_chest_boat");
	public static final ResourceKey<Item> MAPLE_BOAT = create("maple_boat");
	public static final ResourceKey<Item> MAPLE_CHEST_BOAT = create("maple_chest_boat");

	// ITEMS
	public static final ResourceKey<Item> MILKWEED_POD = create("milkweed_pod");
	public static final ResourceKey<Item> SPLIT_COCONUT = create("split_coconut");
	public static final ResourceKey<Item> FIREFLY_BOTTLE = create("firefly_bottle");
	public static final ResourceKey<Item> BUTTERFLY_BOTTLE = create("butterfly_bottle");

	// FOOD
	public static final ResourceKey<Item> PEELED_PRICKLY_PEAR = create("peeled_prickly_pear");
	public static final ResourceKey<Item> CRAB_CLAW = create("crab_claw");
	public static final ResourceKey<Item> COOKED_CRAB_CLAW = create("cooked_crab_claw");
	public static final ResourceKey<Item> SCORCHED_EYE = create("scorched_eye");
	public static final ResourceKey<Item> FERMENTED_SCORCHED_EYE = create("fermented_scorched_eye");

	// SPAWN EGGS & BUCKETS
	public static final ResourceKey<Item> JELLYFISH_BUCKET = create("jellyfish_bucket");
	public static final ResourceKey<Item> CRAB_BUCKET = create("crab_bucket");

	public static final ResourceKey<Item> FIREFLY_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.FIREFLY);
	public static final ResourceKey<Item> JELLYFISH_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.JELLYFISH);
	public static final ResourceKey<Item> CRAB_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.CRAB);
	public static final ResourceKey<Item> OSTRICH_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.OSTRICH);
	public static final ResourceKey<Item> ZOMBIE_OSTRICH_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.ZOMBIE_OSTRICH);
	public static final ResourceKey<Item> SCORCHED_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.SCORCHED);
	public static final ResourceKey<Item> BUTTERFLY_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.BUTTERFLY);
	public static final ResourceKey<Item> MOOBLOOM_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.MOOBLOOM);
	public static final ResourceKey<Item> PENGUIN_SPAWN_EGG = ItemIds.createSpawnEgg(WWEntityTypeIds.PENGUIN);

	private WWItemIds() {
		throw new UnsupportedOperationException("WWItemIds contains only static declarations.");
	}

	private static ResourceKey<Item> create(String name) {
		return ResourceKey.create(Registries.ITEM, WWConstants.id(name));
	}
}
