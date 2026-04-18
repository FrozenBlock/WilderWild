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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class WWItemTags {
	public static final TagKey<Item> JELLYFISH_FOOD = bind("jellyfish_food");
	public static final TagKey<Item> PEARLESCENT_JELLYFISH_FOOD = bind("pearlescent_jellyfish_food");
	public static final TagKey<Item> CRAB_FOOD = bind("crab_food");
	public static final TagKey<Item> OSTRICH_FOOD = bind("ostrich_food");
	public static final TagKey<Item> ZOMBIE_OSTRICH_FOOD = bind("zombie_ostrich_food");
	public static final TagKey<Item> PENGUIN_FOOD = bind("penguin_food");
	public static final TagKey<Item> TUMBLEWEED_COMMON = bind("tumbleweed_common");
	public static final TagKey<Item> TUMBLEWEED_MEDIUM = bind("tumbleweed_medium");
	public static final TagKey<Item> TUMBLEWEED_RARE = bind("tumbleweed_rare");
	public static final TagKey<Item> BROWN_MUSHROOM_STEW_INGREDIENTS = bind("brown_mushroom_stew_ingredients");
	public static final TagKey<Item> RED_MUSHROOM_STEW_INGREDIENTS = bind("red_mushroom_stew_ingredients");

	private static TagKey<Item> bind(String name) {
		return TagKey.create(Registries.ITEM, WWConstants.id(name));
	}
}
