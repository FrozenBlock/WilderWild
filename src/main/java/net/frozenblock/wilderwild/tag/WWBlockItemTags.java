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
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;

public final class WWBlockItemTags {
	public static final BlockItemTagId MESOGLEA = bind("mesoglea");
	public static final BlockItemTagId NEMATOCYSTS = bind("nematocysts");
	public static final BlockItemTagId PEARLESCENT_NEMATOCYSTS = bind("pearlescent_nematocysts");
	public static final BlockItemTagId FROGLIGHT_GOOP = bind("froglight_goop");
	public static final BlockItemTagId LEAF_LITTERS = bind("leaf_litters");
	public static final BlockItemTagId BAOBAB_LOGS = bind("baobab_logs");
	public static final BlockItemTagId WILLOW_LOGS = bind("willow_logs");
	public static final BlockItemTagId CYPRESS_LOGS = bind("cypress_logs");
	public static final BlockItemTagId PALM_LOGS = bind("palm_logs");
	public static final BlockItemTagId MAPLE_LOGS = bind("maple_logs");
	public static final BlockItemTagId HOLLOWED_LOGS = bind("hollowed_logs");
	public static final BlockItemTagId HOLLOWED_LOGS_DONT_BURN = bind("hollowed_logs_dont_burn");
	public static final BlockItemTagId HOLLOWED_LOGS_THAT_BURN = bind("hollowed_logs_that_burn");
	public static final BlockItemTagId STRIPPED_HOLLOWED_LOGS = bind("stripped_hollowed_logs");
	public static final BlockItemTagId STRIPPED_HOLLOWED_LOGS_DONT_BURN = bind("stripped_hollowed_logs_dont_burn");
	public static final BlockItemTagId STRIPPED_HOLLOWED_LOGS_THAT_BURN = bind("stripped_hollowed_logs_that_burn");
	public static final BlockItemTagId HOLLOWED_ACACIA_LOGS = bind("hollowed_acacia_logs");
	public static final BlockItemTagId HOLLOWED_BAOBAB_LOGS = bind("hollowed_baobab_logs");
	public static final BlockItemTagId HOLLOWED_BIRCH_LOGS = bind("hollowed_birch_logs");
	public static final BlockItemTagId HOLLOWED_CHERRY_LOGS = bind("hollowed_cherry_logs");
	public static final BlockItemTagId HOLLOWED_CRIMSON_STEMS = bind("hollowed_crimson_stems");
	public static final BlockItemTagId HOLLOWED_CYPRESS_LOGS = bind("hollowed_cypress_logs");
	public static final BlockItemTagId HOLLOWED_DARK_OAK_LOGS = bind("hollowed_dark_oak_logs");
	public static final BlockItemTagId HOLLOWED_JUNGLE_LOGS = bind("hollowed_jungle_logs");
	public static final BlockItemTagId HOLLOWED_MANGROVE_LOGS = bind("hollowed_mangrove_logs");
	public static final BlockItemTagId HOLLOWED_MAPLE_LOGS = bind("hollowed_maple_logs");
	public static final BlockItemTagId HOLLOWED_OAK_LOGS = bind("hollowed_oak_logs");
	public static final BlockItemTagId HOLLOWED_PALM_LOGS = bind("hollowed_palm_logs");
	public static final BlockItemTagId HOLLOWED_SPRUCE_LOGS = bind("hollowed_spruce_logs");
	public static final BlockItemTagId HOLLOWED_PALE_OAK_LOGS = bind("hollowed_pale_oak_logs");
	public static final BlockItemTagId HOLLOWED_WARPED_STEMS = bind("hollowed_warped_stems");
	public static final BlockItemTagId HOLLOWED_WILLOW_LOGS = bind("hollowed_willow_logs");

	private static BlockItemTagId bind(String name) {
		final Identifier id = WWConstants.id(name);
		return BlockItemTagId.create(id, id);
	}
}
