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
import net.minecraft.world.level.block.Block;

public final class WWBlockIds {
	// SAPLINGS
	public static final ResourceKey<Block> POTTED_BAOBAB_NUT = create("potted_baobab_nut");
	public static final ResourceKey<Block> POTTED_WILLOW_SAPLING = create("potted_willow_sapling");
	public static final ResourceKey<Block> POTTED_CYPRESS_SAPLING = create("potted_cypress_sapling");
	public static final ResourceKey<Block> POTTED_COCONUT = create("potted_coconut");
	public static final ResourceKey<Block> POTTED_YELLOW_MAPLE_SAPLING = create("potted_yellow_maple_sapling");
	public static final ResourceKey<Block> POTTED_ORANGE_MAPLE_SAPLING = create("potted_orange_maple_sapling");
	public static final ResourceKey<Block> POTTED_RED_MAPLE_SAPLING = create("potted_red_maple_sapling");

	// FLOWERS
	public static final ResourceKey<Block> POTTED_CACTUS_FLOWER = create("potted_cactus_flower");
	public static final ResourceKey<Block> POTTED_SEEDING_DANDELION = create("potted_seeding_dandelion");
	public static final ResourceKey<Block> POTTED_CARNATION = create("potted_carnation");
	public static final ResourceKey<Block> POTTED_MARIGOLD = create("potted_marigold");
	public static final ResourceKey<Block> POTTED_PASQUEFLOWER = create("potted_pasqueflower");
	public static final ResourceKey<Block> POTTED_RED_HIBISCUS = create("potted_red_hibiscus");
	public static final ResourceKey<Block> POTTED_YELLOW_HIBISCUS = create("potted_yellow_hibiscus");
	public static final ResourceKey<Block> POTTED_WHITE_HIBISCUS = create("potted_white_hibiscus");
	public static final ResourceKey<Block> POTTED_PINK_HIBISCUS = create("potted_pink_hibiscus");
	public static final ResourceKey<Block> POTTED_PURPLE_HIBISCUS = create("potted_purple_hibiscus");

	// FLOWERBEDS
	public static final ResourceKey<Block> POTTED_PINK_PETALS = create("potted_pink_petals");
	public static final ResourceKey<Block> POTTED_WILDFLOWERS = create("potted_wildflowers");
	public static final ResourceKey<Block> POTTED_PHLOX = create("potted_phlox");
	public static final ResourceKey<Block> POTTED_LANTANAS = create("potted_lantanas");
	public static final ResourceKey<Block> POTTED_CLOVERS = create("potted_clovers");

	// VEGETATION
	public static final ResourceKey<Block> POTTED_SHORT_GRASS = create("potted_short_grass");
	public static final ResourceKey<Block> POTTED_BUSH = create("potted_bush");
	public static final ResourceKey<Block> POTTED_FIREFLY_BUSH = create("potted_firefly_bush");
	public static final ResourceKey<Block> POTTED_SHORT_DRY_GRASS = create("potted_short_dry_grass");
	public static final ResourceKey<Block> POTTED_TALL_DRY_GRASS = create("potted_tall_dry_grass");
	public static final ResourceKey<Block> POTTED_BIG_DRIPLEAF = create("potted_big_dripleaf");
	public static final ResourceKey<Block> POTTED_SMALL_DRIPLEAF = create("potted_small_dripleaf");
	public static final ResourceKey<Block> POTTED_PRICKLY_PEAR = create("potted_prickly_pear");
	public static final ResourceKey<Block> POTTED_SHRUB = create("potted_shrub");
	public static final ResourceKey<Block> POTTED_TUMBLEWEED_PLANT = create("potted_tumbleweed_plant");
	public static final ResourceKey<Block> POTTED_TUMBLEWEED = create("potted_tumbleweed");
	public static final ResourceKey<Block> POTTED_MYCELIUM_GROWTH = create("potted_mycelium_growth");
	public static final ResourceKey<Block> POTTED_FROZEN_SHORT_GRASS = create("potted_frozen_short_grass");
	public static final ResourceKey<Block> POTTED_FROZEN_FERN = create("potted_frozen_fern");
	public static final ResourceKey<Block> POTTED_FROZEN_BUSH = create("potted_frozen_bush");

	// MUSHROOMS
	public static final ResourceKey<Block> POTTED_PALE_MUSHROOM = create("potted_pale_mushroom");

	// BAOBAB
	public static final ResourceKey<Block> BAOBAB_WALL_SIGN = create("baobab_wall_sign");
	public static final ResourceKey<Block> BAOBAB_WALL_HANGING_SIGN = create("baobab_wall_hanging_sign");

	// WILLOW
	public static final ResourceKey<Block> WILLOW_WALL_SIGN = create("willow_wall_sign");
	public static final ResourceKey<Block> WILLOW_WALL_HANGING_SIGN = create("willow_wall_hanging_sign");

	// CYPRESS
	public static final ResourceKey<Block> CYPRESS_WALL_SIGN = create("cypress_wall_sign");
	public static final ResourceKey<Block> CYPRESS_WALL_HANGING_SIGN = create("cypress_wall_hanging_sign");

	// PALM
	public static final ResourceKey<Block> PALM_WALL_SIGN = create("palm_wall_sign");
	public static final ResourceKey<Block> PALM_WALL_HANGING_SIGN = create("palm_wall_hanging_sign");

	// MAPLE
	public static final ResourceKey<Block> MAPLE_WALL_SIGN = create("maple_wall_sign");
	public static final ResourceKey<Block> MAPLE_WALL_HANGING_SIGN = create("maple_wall_hanging_sign");

	// FROGLIGHT GOOP
	public static final ResourceKey<Block> OCHRE_FROGLIGHT_GOOP_BODY = create("ochre_froglight_goop_body");
	public static final ResourceKey<Block> VERDANT_FROGLIGHT_GOOP_BODY = create("verdant_froglight_goop_body");
	public static final ResourceKey<Block> PEARLESCENT_FROGLIGHT_GOOP_BODY = create("pearlescent_froglight_goop_body");

	private static ResourceKey<Block> create(String name) {
		return ResourceKey.create(Registries.BLOCK, WWConstants.id(name));
	}

}
