/*
 * Copyright 2025 FrozenBlock
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
import org.jetbrains.annotations.NotNull;

public final class WWItemTags {
	public static final TagKey<Item> JELLYFISH_FOOD = bind("jellyfish_food");
	public static final TagKey<Item> PEARLESCENT_JELLYFISH_FOOD = bind("pearlescent_jellyfish_food");
	public static final TagKey<Item> CRAB_FOOD = bind("crab_food");
	public static final TagKey<Item> OSTRICH_FOOD = bind("ostrich_food");
	public static final TagKey<Item> PENGUIN_FOOD = bind("penguin_food");
	public static final TagKey<Item> MESOGLEA = bind("mesoglea");
	public static final TagKey<Item> NEMATOCYSTS = bind("nematocysts");
	public static final TagKey<Item> PEARLESCENT_NEMATOCYSTS = bind("pearlescent_nematocysts");
	public static final TagKey<Item> TUMBLEWEED_COMMON = bind("tumbleweed_common");
	public static final TagKey<Item> TUMBLEWEED_MEDIUM = bind("tumbleweed_medium");
	public static final TagKey<Item> TUMBLEWEED_RARE = bind("tumbleweed_rare");
	public static final TagKey<Item> BROWN_MUSHROOM_STEW_INGREDIENTS = bind("brown_mushroom_stew_ingredients");
	public static final TagKey<Item> RED_MUSHROOM_STEW_INGREDIENTS = bind("red_mushroom_stew_ingredients");
	public static final TagKey<Item> BAOBAB_LOGS = bind("baobab_logs");
	public static final TagKey<Item> WILLOW_LOGS = bind("willow_logs");
	public static final TagKey<Item> CYPRESS_LOGS = bind("cypress_logs");
	public static final TagKey<Item> PALM_LOGS = bind("palm_logs");
	public static final TagKey<Item> MAPLE_LOGS = bind("maple_logs");
	public static final TagKey<Item> HOLLOWED_LOGS = bind("hollowed_logs");
	public static final TagKey<Item> HOLLOWED_LOGS_DONT_BURN = bind("hollowed_logs_dont_burn");
	public static final TagKey<Item> HOLLOWED_LOGS_THAT_BURN = bind("hollowed_logs_that_burn");
	public static final TagKey<Item> STRIPPED_HOLLOWED_LOGS = bind("stripped_hollowed_logs");
	public static final TagKey<Item> STRIPPED_HOLLOWED_LOGS_DONT_BURN = bind("stripped_hollowed_logs_dont_burn");
	public static final TagKey<Item> STRIPPED_HOLLOWED_LOGS_THAT_BURN = bind("stripped_hollowed_logs_that_burn");
	public static final TagKey<Item> HOLLOWED_ACACIA_LOGS = bind("hollowed_acacia_logs");
	public static final TagKey<Item> HOLLOWED_BAOBAB_LOGS = bind("hollowed_baobab_logs");
	public static final TagKey<Item> HOLLOWED_BIRCH_LOGS = bind("hollowed_birch_logs");
	public static final TagKey<Item> HOLLOWED_CHERRY_LOGS = bind("hollowed_cherry_logs");
	public static final TagKey<Item> HOLLOWED_CRIMSON_STEMS = bind("hollowed_crimson_stems");
	public static final TagKey<Item> HOLLOWED_CYPRESS_LOGS = bind("hollowed_cypress_logs");
	public static final TagKey<Item> HOLLOWED_DARK_OAK_LOGS = bind("hollowed_dark_oak_logs");
	public static final TagKey<Item> HOLLOWED_JUNGLE_LOGS = bind("hollowed_jungle_logs");
	public static final TagKey<Item> HOLLOWED_MANGROVE_LOGS = bind("hollowed_mangrove_logs");
	public static final TagKey<Item> HOLLOWED_MAPLE_LOGS = bind("hollowed_maple_logs");
	public static final TagKey<Item> HOLLOWED_OAK_LOGS = bind("hollowed_oak_logs");
	public static final TagKey<Item> HOLLOWED_PALM_LOGS = bind("hollowed_palm_logs");
	public static final TagKey<Item> HOLLOWED_SPRUCE_LOGS = bind("hollowed_spruce_logs");
	public static final TagKey<Item> HOLLOWED_WARPED_STEMS = bind("hollowed_warped_stems");
	public static final TagKey<Item> HOLLOWED_WILLOW_LOGS = bind("hollowed_willow_logs");

	private WWItemTags() {
		throw new UnsupportedOperationException("WWItemTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Item> bind(@NotNull String path) {
		return TagKey.create(Registries.ITEM, WWConstants.id(path));
	}
}
